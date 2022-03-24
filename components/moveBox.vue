<template>
	<view :class="isMove?'main':'animation'" :style="{'top':currentHeight+'px'}" @touchstart="start($event)"
		@touchmove.stop.prevent='move($event)' @touchend="end">
		<view>
			<view class="content">
				<view :class="isLow?'animationBtn':'stillBtn'">
					<locationbutton :top="top"></locationbutton>
				</view>

				<view class="touchline">
					<view class="line"></view>
				</view>
				
				<destination :margintop="margin[0].margintop" :marginbottom="margin[0].marginbottom"
					:text="destination" :color="color"
				></destination>
				
				
				<card :margintop="margin[1].margintop" :marginbottom="margin[1].marginbottom">
					<totalbutton :text1="'租电桩'" :text2="'电桩共享'" :selected="isSelected1" @tap="tapButton1()">
						<image class="image1" src="../static/image/car&charger_color.png"
							:style="{'filter':'grayscale('+imageFilter1+')','opacity':image1Opacity}"
						></image>
					</totalbutton>
					<totalbutton :text1="'借电桩'" :text2="'出租电桩'" :selected="isSelected2" @tap="tapButton2()">
						<image class="image2" src="../static/image/park.png"
							:style="{'filter':'grayscale('+imageFilter2+')','opacity':image2Opacity}"
						></image>
					</totalbutton>
				</card>
				
				
				<scroller :margintop="margin[2].margintop" :marginbottom="margin[2].marginbottom" @scrolltolower="scrolltolower()">
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
	</view>
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
				currentHeight: 200, //当前高度 单位px
				lastHeight: 0,//手指最后触摸的高度 单位px
				originHeight: 0, //滑动开始时的高度 单位px
				originTouch: 0, //滑动开始时手指的位置 单位px
				currentTouch: 0, //当前手指高度 单位px
				isMove: false, //是否正在滑动
				windowHeight: 0, //本机的高度 单位px
				windowWidth: 0, //本机的宽度 单位px
				isLow: true, //滑动开始前上拉框处在低位则为真，在高位则为假
				
				imageFilter1:0,
				image1Opacity:1,
				imageFilter2:1,
				image2Opacity:0.3,
				
				destination:this.$store.state.destination,
				color:"rgb(0,0,0)",
				
				icontype:"warn",
				icontext:"暂无更多",
				
				orders:[],
				orderSelected:-1,
				orderIndex:0,//记录scroller刷新到哪个order
				isFull:false,//是否拿满
				
				chargers:[
					{
						location:'上海大学宝山校区',
						state:false,
						price:'1.9',
						startTime:'10:00',
						endTime:'12:00'
					},
					{
						location:'电子科技大学清水河校区',
						state:true,
						price:'1.1',
						startTime:'13:00',
						endTime:'17:00'
					},
					{
						location:'华东政法大学松江校区',
						state:false,
						price:'2.1',
						startTime:'08:00',
						endTime:'18:00'
					}
				],
				chargerSelected:-1,
				
				margin: [{
						margintop: uni.upx2px(20),
						marginbottom: uni.upx2px(20)
					},
					{
						margintop: uni.upx2px(20),
						marginbottom: uni.upx2px(20)
					},
					{
						margintop: uni.upx2px(20),
						marginbottom: uni.upx2px(20)
					}
				],
				top: -80,

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
			start(event) {
				this.isMove = true;
				this.originTouch = event.touches[0].clientY;
				if (this.isLow)
					this.originHeight = (1 - this.minHeight) * this.windowHeight;
				else
					this.originHeight = (1 - this.maxHeight) * this.windowHeight;
			},

			move(event) {

				this.lastHeight = this.currentHeight;
				this.currentTouch = event.touches[0].clientY;
				var mediaVariable = this.originHeight - this.originTouch + this.currentTouch; //运用中间变量
				if (mediaVariable >= (1 - this.maxHeight) * this.windowHeight && mediaVariable <= (1 - this.minHeight) *
					this.windowHeight) //当超过上下界线时就不对当前高度赋值
					this.currentHeight = mediaVariable;
				var gap = this.lastHeight - this.currentHeight;
				if (gap >= 1 && gap <= 5) {
					this.margin[0].margintop = uni.upx2px(35);
					this.margin[0].marginbottom = uni.upx2px(0);
					this.margin[1].margintop = uni.upx2px(35);
					this.margin[1].marginbottom = uni.upx2px(5);
					this.margin[2].margintop = uni.upx2px(35);
					this.margin[2].marginbottom = uni.upx2px(10);
					this.top = -70;
				} else if (gap >= 5) {
					this.margin[0].margintop = uni.upx2px(45);
					this.margin[0].marginbottom = uni.upx2px(0);
					this.margin[1].margintop = uni.upx2px(45);
					this.margin[1].marginbottom = uni.upx2px(5);
					this.margin[2].margintop = uni.upx2px(45);
					this.margin[2].marginbottom = uni.upx2px(0);
					this.top = -60;
				} else if (gap <= -1 && gap >= -5) {
					this.margin[0].margintop = uni.upx2px(10);
					this.margin[0].marginbottom = uni.upx2px(5);
					this.margin[1].margintop = uni.upx2px(10);
					this.margin[1].marginbottom = uni.upx2px(5);
					this.margin[2].margintop = uni.upx2px(10);
					this.margin[2].marginbottom = uni.upx2px(0);
					this.top = -90;
				} else if (gap <= -5) {
					this.margin[0].margintop = uni.upx2px(5);
					this.margin[0].marginbottom = uni.upx2px(5);
					this.margin[1].margintop = uni.upx2px(10);
					this.margin[1].marginbottom = uni.upx2px(5);
					this.margin[2].margintop = uni.upx2px(10);
					this.margin[2].marginbottom = uni.upx2px(0);
					this.top = -100;
				}


				// console.log(this.currentHeight);
			},

			end() {
				this.isMove = false;
				var thisHeight = this.currentHeight; //因为动画滞后因而需要一个副本来记录currentHeight的值
				if (Math.abs(this.currentHeight - this.originHeight) >= 0.25 * (this.maxHeight - this.minHeight) * this
					.windowHeight) { //当滑动结束时若移动距离超过上下界线的25%则直接移动到另一端
					if (this.isLow) { //如果在低位则向高位移动
						this.currentHeight = (1 - this.maxHeight) * this.windowHeight;
						this.isLow = false;
					} else {
						this.currentHeight = (1 - this.minHeight) * this.windowHeight;
						this.isLow = true;
					}
				} else {
					if (this.isLow) //如果在低位则向低位移动
						this.currentHeight = (1 - this.minHeight) * this.windowHeight;
					else
						this.currentHeight = (1 - this.maxHeight) * this.windowHeight;
				}
				this.margin[0].margintop = uni.upx2px(20);
				this.margin[0].marginbottom = uni.upx2px(20);
				this.margin[1].margintop = uni.upx2px(20);
				this.margin[1].marginbottom = uni.upx2px(20);
				this.margin[2].margintop = uni.upx2px(20);
				this.margin[2].marginbottom = uni.upx2px(20);
				this.top = -80;
			},

			tapButton1() {
				this.isSelected1 = true;
				this.isSelected2 = false;
				this.currentHeight = (1 - this.maxHeight) * this.windowHeight;
				this.imageFilter1=0;
				this.image1Opacity=1;
				this.imageFilter2=1;
				this.image2Opacity=0.3;
				this.chargerSelected=-1;
			},
			tapButton2() {
				this.isSelected1 = false
				this.isSelected2 = true;
				this.currentHeight = (1 - this.maxHeight) * this.windowHeight;
				this.imageFilter1=1;
				this.image1Opacity=0.3;
				this.imageFilter2=0;
				this.image2Opacity=1;
				this.orderSelected=-1;
			},
			tapOrder(data){
				this.orderSelected=data;
			},
			tapCharger(data){
				this.chargerSelected=data;
			},
			scrolltolower(){
				if(this.isSelected1==true&&this.isFull==false){
					var url='https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from='
						+this.$store.state.currentLocation.latitude+','+this.$store.state.currentLocation.longitude+'&to=';
					var orderCopy=[];
					if(this.$store.state.orders.length-1-this.orderIndex<=5){     //下拉刷新一次数量小于等于五
						for(var index=this.orderIndex+1;index<=this.$store.state.orders.length-1;index++){
							orderCopy.push(this.$store.state.orders[index]);
							if(index!=this.$store.state.orders.length-1){
								url+=this.$store.state.orders[index].latitude+','+this.$store.state.orders[index].longitude+';';
							}else{
								url+=this.$store.state.orders[index].latitude+','+this.$store.state.orders[index].longitude;
							}
						}
						url+='&key=HVTBZ-KOFW6-JDUSX-ESY54-6WWQK-LEF73';
						uni.request({
							url:url,
							method:'GET',
							success: (res) => {
								setTimeout(()=>{
									var distance=res.data.result.rows[0].elements;
									
									for(var index in distance){
										orderCopy[index].distance=distance[index].distance/1000;
										this.orders.push(orderCopy[index]);
									}
								},500);
							},
						});	
						this.orderIndex=this.$store.state.orders.length-1;
						this.isFull=true;
					}
						
					else{
						for(var index=this.orderIndex+1;index<=this.orderIndex+5;index++){   //此次拉取数量大于五
							orderCopy.push(this.$store.state.orders[index]);
							if(index!=this.orderIndex+5){
								url+=this.$store.state.orders[index].latitude+','+this.$store.state.orders[index].longitude+';';
							}else{
								url+=this.$store.state.orders[index].latitude+','+this.$store.state.orders[index].longitude;
							}
						}
						url+='&key=HVTBZ-KOFW6-JDUSX-ESY54-6WWQK-LEF73';
						uni.request({
							url:url,
							method:'GET',
							success: (res) => {
								setTimeout(()=>{
									var distance=res.data.result.rows[0].elements;
									for(var index in distance){
										orderCopy[index].distance=distance[index].distance/1000;
										this.orders.push(orderCopy[index]);
									}
								} ,500)
								
							},
						});	
						this.orderIndex+=5;
					}
				}else{
					this.icontext="暂无更多";
					this.icontype="warn";
				}
			}
		},
		computed: {
			reloationHeight() {
				return this.$store.state.moveBoxCurrentHeight - 80;
			}
		},
		mounted() {
			this.windowHeight = uni.getSystemInfoSync().windowHeight;
			this.windowWidth = uni.getSystemInfoSync().windowWidth;
			this.currentHeight = (1 - this.minHeight) * this.windowHeight;
			this.lastHeight = this.currentHeight;
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
					this.orders=this.$store.state.orders;
					this.orderIndex=this.$store.state.orders.length-1;
					this.isFull=true;
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
		height: 100%;
		width: 750upx;
		position: fixed;
		background-color: rgb(240, 245, 240);
		padding: 0 12px;
		border-top-left-radius: 40upx;
		border-top-right-radius: 40upx;
	}

	.animation {
		height: 100%;
		width: 750upx;
		position: fixed;
		background-color: rgb(240, 245, 240);
		padding: 0 12px;
		border-top-left-radius: 40upx;
		border-top-right-radius: 40upx;
		transition-property: top;
		transition-duration: .5s;
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
