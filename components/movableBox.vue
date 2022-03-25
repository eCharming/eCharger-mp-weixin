<template>
	<movable-area class="movable-area" :style="{'top':boxHeight+'px'}" @touchmove.prevent.stop>
		
		<movable-view class="main" direction="vertical" damping="30" out-of-bounds="true" :y="currentY"  @change="onchange($event)" @touchend="end()">
			<view>
				<view class="content">
					<view :class="isLow?'animationBtn':'stillBtn'">
						<locationbutton></locationbutton>
					</view>
		
					<view class="touchline">
						<view class="line"></view>
					</view>
					
					<destination :text="destination" :color="color"></destination>
					
					
					<card>
						<totalbutton :text1="'租电桩'" :text2="'电桩共享'" :selected="isSelected1" :percent="percent" @tap="tapButton1()" >
							<image class="image1" src="../static/image/car&charger_color.png"
								:style="{'filter':'grayscale('+imageFilter1+')','opacity':imageOpacity1}"
							></image>
						</totalbutton>
						<totalbutton :text1="'借电桩'" :text2="'出租电桩'" :selected="isSelected2" :percent="percent" @tap="tapButton2()" >
							<image class="image2" src="../static/image/park.png"
								:style="{'filter':'grayscale('+imageFilter2+')','opacity':imageOpacity2}"
							></image>
						</totalbutton>
					</card>
					
					
					<scroller @scrolltolower="scrolltolower()">
						<order v-if="isSelected1" v-for="(order,index) in orders" 
							:index="index" :key="index" 
							:location="order.location"
							:distance="order.distance"
							:price="order.price"
							:startTime="order.startTime"
							:endTime="order.endTime"
							:orderSelected="orderSelected" 
							@emit="tapOrder()"
						>
						</order>
						<charger v-if="isSelected2" v-for="(charger,index) in chargers"
							:index="index" :key="index" 
							:location="charger.location"
							:state="charger.state"
							:price="charger.price"
							:startTime="charger.startTime"
							:endTime="charger.endTime"
							:chargerSelected="chargerSelected" 
							@emit="tapCharger()"
						>
						</charger>
						<view class="scrollerview">
							<icon :type="icontype" color="rgb(102,205,170)"></icon>
							<text>{{icontext}}</text>
						</view>
					</scroller>
		
		
				</view>
			</view>
		</movable-view>
		
	</movable-area>
	
</template>

<script>
	import totalbutton from './totalbutton.vue'
	import card from './card.vue'
	import destination from './destination.vue'
	import locationbutton from './locationButton.vue'
	import scroller from './scroller.vue'
	import order from './order.vue'
	import charger from './myCharger.vue'

	export default {
		components: {
			totalbutton,
			card,
			destination,
			locationbutton,
			scroller,
			order,
			charger
		},
		data() {
			return {
				liveY:0,//实时movablebox的高度(实时位置)
				currentY:0,//当前movablebox的高度(非实时)
				percent:1,//movablebox移动到上下限的百分之多少
				boxHeight:300,//movablebox的最高高度
				windowHeight: 0, //本机的高度 单位px
				windowWidth: 0, //本机的宽度 单位px
				isLow: true, //滑动开始前上拉框处在低位则为真，在高位则为假
				
				imageFilter1:0,
				imageOpacity1:1,
				imageFilter2:1,
				imageOpacity2:0.3,
				
				destination:this.$store.state.destination,//目的地组件文字
				color:"rgb(0,0,0)",
				
				icontype:"warn",
				icontext:"暂无更多",
				
				orders:[],
				orderSelected:-1,
				orderIndex:0,//记录scroller刷新到哪个order
				isFull:false,//是否拿满
				
				chargers:[],
				chargerSelected:-1,

				isSelected1: true,
				isSelected2: false,
			}
		},
		props: {
			minHeight: {
				type: Number,
				default: 0.35
			},
			maxHeight: {
				type: Number,
				default: 0.9
			},
		},
		methods: {
			onchange(e){
				this.liveY=e.detail.y;
				this.percent=this.liveY/(this.windowHeight*(this.maxHeight-this.minHeight));
				// console.log(this.percent)
			},
			end(){
				
				if(this.isLow==true){	//初始在低位的情况
					if((1-this.percent)>=0.25){	//上拉超过上下限的25%则移向高位 因为位置改变了也即currentY改变组件可以监听变化所以不用nextTick
						this.isLow=false;
						this.currentY=0;
					}
					else{	//上拉未超过上下限的25%则回到低位 因为位置没有改变也即currentY没有改变组件无法监听变化所以使用nextTick
						if(Math.abs(this.liveY-this.windowHeight*(this.maxHeight-this.minHeight))>5){	//用于防止点击事件穿透触发touchend
							this.currentY=this.liveY;
							this.$nextTick(function(){
								this.currentY=this.windowHeight*(this.maxHeight-this.minHeight);
							})
						}
						
					}
				}else{	//初始在高位的情况
					if(this.percent>=0.25){	//下拉超过上下限的25%则移向低位 因为位置改变了也即currentY改变组件可以监听变化所以不用nextTick
						this.isLow=true;
						
						this.currentY=this.windowHeight*(this.maxHeight-this.minHeight);
					}
					else{	//下拉未超过上下限的25%则回到高位 因为位置没有改变也即currentY没有改变组件无法监听变化所以使用nextTick
						
						this.currentY=this.liveY;
						this.$nextTick(function(){
							this.currentY=0;
						})
					}
				}
			},
			
			tapButton1() {
				this.isSelected1 = true;
				this.isSelected2 = false;
				this.imageFilter1=0;
				this.imageOpacity1=1;
				this.imageFilter2=1;
				this.imageOpacity2=0.3;
				this.chargerSelected=-1;
				this.isLow=false;
				this.currentY=0;
			},
			tapButton2() {
				this.isSelected1 = false
				this.isSelected2 = true;
				this.imageFilter1=1;
				this.imageOpacity1=0.3;
				this.imageFilter2=0;
				this.imageOpacity2=1;
				this.orderSelected=-1;
				this.isLow=false;
				this.currentY=0;
			},
			tapOrder(data){
				this.orderSelected=data;
			},
			tapCharger(data){
				this.chargerSelected=data;
			},
			scrolltolower(){
				if(this.isSelected1==true&&this.isFull==false){
					if(this.$store.state.orders.length-1-this.orderIndex<=5){     //下拉刷新一次数量小于等于五
						for(var index=this.orderIndex+1;index<=this.$store.state.orders.length-1;index++){
							this.orders.push(this.$store.state.orders[index]);
						}
						this.orderIndex=this.$store.state.orders.length-1;
						this.isFull=true;
					}
					else{
						for(var index=this.orderIndex+1;index<=this.orderIndex+5;index++){   //此次拉取数量大于五
							this.orders.push(this.$store.state.orders[index]);
						}
						this.orderIndex+=5;
					}
				}else{
					this.icontext="暂无更多";
					this.icontype="warn";
				}
			},
		},
		computed: {
			reloationHeight() {
				return this.$store.state.moveBoxCurrentHeight - 80;
			}
		},
		mounted() {
			this.windowHeight = uni.getSystemInfoSync().windowHeight;
			this.windowWidth = uni.getSystemInfoSync().windowWidth;
			this.boxHeight=this.windowHeight*(1-this.maxHeight);
			this.currentY=this.windowHeight*(this.maxHeight-this.minHeight);
		},
		watch:{
			'$store.state.destination'(){
				this.destination=this.$store.state.destination;
				this.color="rgb(102,205,170)"
			},
			'$store.state.orders'(){
				this.icontext="上拉加载更多";
				this.icontype="download";
				this.isFull=false;
				this.orders.splice(0);
				if(this.$store.state.orders.length<=5){		//数量小于等于5
					for(var index in this.$store.state.orders){
						this.orders.push(this.$store.state.orders[index]);
					}
					this.orderIndex=this.$store.state.orders.length-1;
					this.isFull=true;
					this.icontext="暂无更多";
					this.icontype="warn";
				}
					
				else{
					for(var index=0;index<=4;index++){
						this.orders.push(this.$store.state.orders[index]);
					}
					this.orderIndex=4;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.movable-area{
		position: fixed;
		height:155vh;
		width:750upx;
		pointer-events: none;
	}
	
	.stillBtn {
		opacity: 0;
		transition-property: opacity;
		transition-duration: .5s;

	}

	.animationBtn {
		opacity: 1;
		transition-property: opacity;
		transition-duration: .5s;
	}

	.main {
		height: 100vh;
		width: 750upx;
		background-color: rgb(240, 245, 240);
		padding: 0 12px;
		border-top-left-radius: 40upx;
		border-top-right-radius: 40upx;
		pointer-events: auto;
	}
	
	.scrollerview{
		display: flex;
		justify-content: center;
	}

	.content {
		height: 100%;

	}

	.touchline {
		margin-top: 20rpx;
		display: flex;
		justify-content: center;
	}

	.line {
		margin: 0px;
		vertical-align: middle;
		border-bottom: 8rpx solid rgb(214, 215, 217);
		width: 60rpx;
		transform: scaleY(0.5);
		border-top-color: rgb(214, 215, 217);
		border-right-color: rgb(214, 215, 217);
		border-left-color: rgb(214, 215, 217);
	}
	
	.image1{
		// border: 2px solid red;
		height: 180upx;
		width: 180upx;
		position: relative;
		bottom:154upx;
		left:70upx;
		transition-property: opacity,filter;
		transition-duration: .3s;
	}
	
	.image2{
		// border: 2px solid red;
		height: 150upx;
		width: 150upx;
		position: relative;
		bottom:170upx;
		left:70upx;
		transition-property: opacity,filter;
		transition-duration: .3s;
	}
</style>
