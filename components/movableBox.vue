<template>
	<view>
		<navigator :isLow="isLow"></navigator>
		<movable-area class="movable-area" :style="{'top':boxHeight+'px','height':areaLength+'px'}" @touchmove.prevent.stop>
		
			<movable-view class="main" :style="{'height':windowHeight+'px'}" direction="vertical" damping="30" out-of-bounds="true" :y="currentY"
				@change="onchange($event)" @touchend="end()">
				<view>
					<view class="content">
						
						<view class="fixed-view">
							<view :class="[isLow?'animationBtn':'stillBtn','flex-img']">
								<infobutton></infobutton>
								<locationbutton></locationbutton>
							</view>
						</view>
						
						<view class="touchline">
							<view class="line"></view>
						</view>
		
						
						<hiddencard :percent="percent"></hiddencard>
						<hiddendetail v-if="hiddenDetail" :percent="percent" @tap="detail"></hiddendetail>
						
						<!-- <view style="height: 250upx;position: absolute;border: 2px solid red;top:-20upx"></view> -->
						<card>
							<totalbutton :text1="'租电桩'" :text2="'电桩共享'" :selected="isSelected1" :percent="percent" :type="1"
								@tap="tapButton1()">
								<image class="image1" src="../static/image/car&charger_color.png"
									:style="{'filter':'grayscale('+imageFilter1+')','opacity':imageOpacity1}"></image>
							</totalbutton>
							<totalbutton :text1="'借电桩'" :text2="'出租电桩'" :selected="isSelected2" :percent="percent" :type="2"
								@tap="tapButton2()">
								<image class="image2" src="../static/image/park.png"
									:style="{'filter':'grayscale('+imageFilter2+')','opacity':imageOpacity2}"></image>
							</totalbutton>
						</card>
						<!-- <view style="height: 200upx;position: absolute;border: 2px solid red;"></view> -->
						<destination></destination>
		
						<scroller @scrolltolower="scrolltolower()" :scrollTop="scrollTop">
							<order v-if="isSelected1" v-for="(order,index) in orders" :ref="'orderRef'+index" :key="index" :index="index"
								:location="order.location" :distance="order.distance" :price="order.price"
								:startTime="order.startTime" :endTime="order.endTime" :detail="order.detail"
								@tap="tapOrder(index)" @emit="detail()" @toLow="toLow">
							</order>
							<charger v-if="isSelected2" v-for="(charger,index) in chargers" :ref="'chargerRef'+index" :key="index"
								:location="charger.location" :state="charger.state" :price="charger.price"
								:startTime="charger.startTime" :endTime="charger.endTime"
								@tap="tapCharger(index)">
							</charger>
							<view class="scrollerview">
								<!-- <icon :type="icontype" color="rgb(102,205,170)"></icon> -->
								<image src="@/static/image/uparrow.png" style="width: 23px;height: 23px;" v-show="icontype=='download'"></image>
								<image src="@/static/image/warning.png" style="width: 23px;height: 23px;" v-show="icontype=='warn'"></image>
								<text>{{icontext}}</text>  
							</view>
								
							
							
						</scroller>
		
		
					</view>
				</view>
			</movable-view>
		
		</movable-area>
		
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
	import infobutton from './infoButton.vue'
	import hiddencard from './hiddenCard.vue'
	import hiddendetail from './hiddenDetail.vue'
	import navigator from './navigator.vue'

	export default {
		components: {
			totalbutton,
			card,
			destination,
			locationbutton,
			scroller,
			order,
			charger,
			infobutton,
			hiddencard,
			hiddendetail,
			navigator
		},
		data() {
			return {
				liveY: 0, //实时movablebox的高度(实时位置)
				currentY: 0, //当前movablebox的高度(非实时)
				percent: 1, //movablebox移动到上下限的百分之多少
				boxHeight: 300, //movablebox的最高高度
				windowHeight: 300, //本机的高度 单位px
				windowWidth: 0, //本机的宽度 单位px
				isLow: true, //滑动开始前上拉框处在低位则为真，在高位则为假
				scrollTop:0,
				areaLength:500,
				minHeight:0.33,
				maxHeight:0.9,

				imageFilter1: 0,
				imageOpacity1: 1,
				imageFilter2: 1,
				imageOpacity2: 0.3,

				icontype: "warn",
				icontext: "暂无更多",

				orders: [],
				orderSelected: -1,
				preOrder:-2,
				hiddenDetail:false,//记录是否显示订单详情气泡
				orderIndex: 0, //记录scroller刷新到哪个order
				isFull: false, //是否拿满

				chargers: [],
				chargerSelected: -1,
				preCharger:-2,

				isSelected1: true,
				isSelected2: false,
			}
		},
		methods: {
			onchange(e) {
				this.liveY = e.detail.y;
				this.percent = this.liveY / (this.windowHeight * (this.maxHeight - this.minHeight));
			},
			end() {

				if (this.isLow == true) { //初始在低位的情况
					if ((1 - this.percent) >= 0.25) { //上拉超过上下限的25%则移向高位 因为位置改变了也即currentY改变组件可以监听变化所以不用nextTick
						this.toHigh();
					} else { //上拉未超过上下限的25%则回到低位 因为位置没有改变也即currentY没有改变组件无法监听变化所以使用nextTick
						if (Math.abs(this.liveY - this.windowHeight * (this.maxHeight - this.minHeight)) >
							5) { //用于防止点击事件穿透触发touchend
							this.currentY = this.liveY;
							this.$nextTick(function() {
								this.currentY = this.windowHeight * (this.maxHeight - this.minHeight);
							})
						}
					}
				} else { //初始在高位的情况
					if (this.percent >= 0.25) { //下拉超过上下限的25%则移向低位 因为位置改变了也即currentY改变组件可以监听变化所以不用nextTick
						this.toLow();
					} else { //下拉未超过上下限的25%则回到高位 因为位置没有改变也即currentY没有改变组件无法监听变化所以使用nextTick
						this.currentY = this.liveY;
						this.$nextTick(function() {
							this.currentY = 0;
						})
					}
				}
			},

			tapButton1() {
				this.isSelected1 = true;
				this.isSelected2 = false;
				this.imageFilter1 = 0;
				this.imageOpacity1 = 1;
				this.imageFilter2 = 1;
				this.imageOpacity2 = 0.3;
				this.chargerSelected = -1;
				
				this.$store.commit('setButtonSelected',1);
				this.$nextTick(function(){
					this.toHigh();
				})
			},
			tapButton2() {  
				this.isSelected1 = false;
				this.isSelected2 = true;
				this.imageFilter1 = 1;
				this.imageOpacity1 = 0.3;
				this.imageFilter2 = 0;
				this.imageOpacity2 = 1;
				if(this.orderSelected!=-1){
					this.orders[this.orderSelected].detail=false;
					this.orderSelected = -1;
				}
				this.$store.commit('setButtonSelected',2);
				this.$nextTick(function(){
					this.toHigh();
				})
				
				
			},
			tapOrder(index) {  //用于触发点击order的事件
				if(index!=this.orderSelected){			//因为还没更新数值 index代表该次点击的序号 orderselected代表上一次点击的序号 前一次点击和这一次点击不同则更新界面
					this.preOrder=this.orderSelected;
					this.orderSelected = index;
					if(this.preOrder!=-1){
						this.orders[this.preOrder].detail=false;
						this.$refs[`orderRef${this.preOrder}`][0].untap();
					}
						
					this.$refs[`orderRef${index}`][0].tap();
				}else{		//前一次点击和这一次点击相同 则进入订单详情页 movablebox下拉置为低位 向vuex传递参数
					this.$store.commit('setOrderSelected',index);
					this.$nextTick(function(){
						this.toLow();
						this.hiddenDetail=true;
					})
				}
				
			},
			tapCharger(index) {  //用于触发点击charger的事件
				if(this.preCharger!=this.chargerSelected){
					this.preCharger=this.chargerSelected;
					this.chargerSelected = index;
					if(this.preCharger!=-1)
						this.$refs[`chargerRef${this.preCharger}`][0].untap();
					this.$refs[`chargerRef${index}`][0].tap();
				}
			},
			scrolltolower() { //用于下拉刷新加载order
				if (this.isSelected1 == true && this.isFull == false) {
					if (this.$store.state.orders.length - 1 - this.orderIndex <= 5) { //下拉刷新一次数量小于等于五
						for (var index = this.orderIndex + 1; index <= this.$store.state.orders.length - 1; index++) {
							this.orders.push(this.$store.state.orders[index]);
						}
						this.orderIndex = this.$store.state.orders.length - 1;
						this.isFull = true;
					} else {
						for (var index = this.orderIndex + 1; index <= this.orderIndex + 5; index++) { //此次拉取数量大于五
							this.orders.push(this.$store.state.orders[index]);
						}
						this.orderIndex += 5;
					}
				} else {
					this.icontext = "暂无更多";
					this.icontype = "warn";
				}
			},
			detail(){
				this.orders[this.orderSelected].detail=true;
				this.$nextTick(function(){
					this.scrollTop=uni.upx2px(this.orderSelected*338);
				})
				
				this.toHigh();
			},
			undetail(){
				this.orders[this.orderSelected].detail=false;
				this.$nextTick(function(){
					this.scrollTop=uni.upx2px(this.orderSelected*338);
				})
				

			},
			toLow(){
				this.isLow=true;
				this.currentY = this.windowHeight * (this.maxHeight - this.minHeight);
			},
			toHigh(){
				this.isLow=false;
				this.hiddenDetail=false;
				this.currentY = 0;
			}
		},
		mounted() {
			this.windowHeight = this.$store.state.windowHeight;
			this.minHeight=uni.upx2px(250)/this.windowHeight;
			console.log((1+(this.maxHeight-this.minHeight)))
			this.windowWidth = uni.getSystemInfoSync().windowWidth;
			this.boxHeight = this.windowHeight * (1 - this.maxHeight);
			this.areaLength=(1+(this.maxHeight-this.minHeight))*this.windowHeight;
			this.currentY = this.windowHeight * (this.maxHeight - this.minHeight);
		},
		watch: {
			'$store.state.orders'() { //用于加载order
				this.icontext = "上拉加载更多";
				this.icontype = "download";
				this.isFull = false;
				this.orders.splice(0);
				if(this.orderSelected!=-1){
					this.$refs[`orderRef${this.orderSelected}`][0].untap();//由于在更新dom时是延时更新，因此order样式并不会变 需要手动调回
				}
				this.orderSelected=-1;
				this.preOrder=-2;
				if (this.$store.state.orders.length <= 5) { //数量小于等于5
					for (var index in this.$store.state.orders) {
						this.orders.push(this.$store.state.orders[index]);
					}
					this.orderIndex = this.$store.state.orders.length - 1;
					this.isFull = true;
					this.icontext = "暂无更多";
					this.icontype = "warn";
				} else {
					for (var index = 0; index <= 4; index++) {
						this.orders.push(this.$store.state.orders[index]);
					}
					this.orderIndex = 4;
				}
			},
			'$store.state.markerSelected'(){//当在地图上选中标点时movablebox弹出并且选中标点对应的order
				if(this.isSelected1==true){
					for(;this.orderIndex<this.$store.state.markerSelected;){
						this.scrolltolower();
					};
					if(this.orderSelected!=this.$store.state.markerSelected){
						this.$nextTick(function(){
							this.tapOrder(this.$store.state.markerSelected);
						});
					};
					this.$nextTick(function(){
						this.scrollTop=uni.upx2px(this.$store.state.markerSelected*338);
						this.toHigh();
					})
					console.log(this.scrollTop)
					
				}
			},
			'$store.state.isLow'(){  //用于从search页面回到主页面时将movablebox移向低位
				this.toLow();
			}
		}
	}
</script>

<style scoped>
	.movable-area {
		position: relative;
		width: 100%;
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
		width: 750upx;
		background-color: rgb(240, 245, 240);
		/* background-color: #FFFFFF; */
		padding: 0 12px;
		border-top-left-radius: 40upx;
		border-top-right-radius: 40upx;
		pointer-events: auto;
	}

	.scrollerview {
		display: flex;
		justify-content: center;
	}

	.content {
		position: relative;
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

	.image1 {
		height: 180upx;
		width: 180upx;
		position: relative;
		bottom: 154upx;
		left: 70upx;
		transition-property: opacity, filter;
		transition-duration: .3s;
	}

	.image2 {
		height: 150upx;
		width: 150upx;
		position: relative;
		bottom: 170upx;
		left: 70upx;
		transition-property: opacity, filter;
		transition-duration: .3s;
	}

	.flex-img {
		display: flex;
		justify-content: space-between;
	}
	
	.fixed-view {
		position: fixed;
		left:0;
		right:0;
		top:-80px;
	}
</style>
