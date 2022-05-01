<template>
	<view style="position: relative;height: 300upx;margin:20upx;margin-bottom: 40upx;transform-style: preserve-3d; transition: .7s all;"
	:style="{'transform':'rotateY('+rotate+'deg)','height':height+'rpx'}">
		<view class="card"
			:style="{'border-left':borderleft,'border-right':borderright,'box-shadow':boxshadow}"
			@tap="checkMap()"
		>
			<view style="display: flex;flex-direction: column;">
				<view class="view1">
					<text class="location">{{address}}</text>
					<view>
						<image class="image" src="../static/image/arrow.png"></image>
						<text class="distance">{{distance}}km</text>
					</view>
					
				</view>
				<view style="display: flex;justify-content: space-between;position: relative;">
					<text class="text">{{location}}</text>
					<image class="image1" :style="{'opacity':checkOpacity,'right':checkRight+'rpx'}" src='../static/image/checkdetail.png' v-if="check" 
					@click.native.stop.prevent="checkDetail"></image>
				</view>
				
			</view>
			<view class="view2">
				<view class="priceview">
					<text>价格</text>
					<text class="yuan">￥</text>
					<text class="price">{{price}}</text>
				</view>
				<view class="timeview">
					<text>可用时间：</text>
					<text class="time">{{showTime}}</text>
				</view>
				
			</view>
		
		</view>
		<view style="position: absolute;transform: rotateY(90deg) translateZ(300upx);width: 100%;
		transition: .7s all;border-radius: 30upx;border-top: 10upx solid rgba(102,205,170,1);border-bottom: 10upx solid rgba(102,205,170,1);
		transform-style: preserve-3d;overflow: hidden;" :style="{'height':height+'rpx'}">
			<view style="position: relative;transition: .7s all;transform-style: preserve-3d;transform-origin: 50% 50% -150upx;"
			:style="{'transform':'rotateX('+buttonRotate+'deg)','height':height+'rpx'}">
				<view class="button" :style="{'opacity':buttonOpacitty}">
					<view style="position: absolute;right: 30upx;top: 20upx;">
						<text style="color:rgba(102,205,170,1) ;letter-spacing: 2upx;font-size: 28upx;" @tap="unCheckDetail()">
							返回
						</text>
					</view>
					<view class="smalldetailview" :animation="animationBook">
						<image src="../static/image/order.png" style="height: 125upx;width: 125upx;" @tap="book"></image>
						<text class="smalldetail">预约</text>
					</view>
					<view class="smalldetailview" :animation="animationContact">
						<image src="../static/image/connection.png" style="height: 125upx;width: 125upx;" @tap="chat"></image>
						<text class="smalldetail">联系</text>
					</view>
					<view class="smalldetailview" :animation="animationNavigate">
						<image src="../static/image/navigation.png" style="height: 125upx;width: 125upx;" @tap="navigate"></image>
						<text class="smalldetail">导航</text>
					</view>
					<view class="smalldetailview" :animation="animationDetail">
						<image src="../static/image/chargerdetail.png" style="height: 125upx;width: 125upx;" @tap="orderDetail"></image>
						<text class="smalldetail">详情</text>
					</view>
				</view>
				
				<view class="book" style="transform-origin: center;transform:rotateX(180deg);position: absolute;transform-style: preserve-3d;
				height: 100%;width: 100%;">
					<view style="position: relative;height: 100%;width: 100%;transform-style: preserve-3d;perspective: 500px;
					transform:translateZ(300upx);">
						<view style="height: 300upx;width: 100%;position: absolute;background-color: #edfdf6;
						display: flex;flex-direction: column;justify-content: space-between;">
							
							<view style="margin-right:30upx;margin-top: 20upx;display:flex;justify-content: flex-end;">
								<text style="color:rgba(102,205,170,1) ;letter-spacing: 2upx;font-size: 28upx;" @tap="unbook()">
									返回
								</text>
								
							</view>
							
							<view>
								<view style="margin:30upx;margin-top: 0;width: 550upx;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
									<text style="font-size: 30upx;font-weight: 700;
									">{{location}}</text>
								</view>
								<view style="margin-left:30upx;margin-right:30upx;margin-top: 10upx;display: flex;justify-content: space-between;">
									<view>
										<image class="image" src="../static/image/arrow.png"></image>
										<text style="color:rgba(102,205,170,1) ;font-size: 30upx;letter-spacing: 1upx;">距离您约{{distance}}km</text>
									</view>
									<view style="color:rgba(102,205,170,1) ;font-size: 30upx;letter-spacing: 1upx;" @tap="bookDetail()">
										详情
									</view>
								</view>
								
								
							</view>
							<view style="display: flex;justify-content: space-between;margin: 30upx;">
								<view class="priceview">
									<text>价格</text>
									<text class="yuan">￥</text>
									<text class="price">{{price}}</text>
								</view>
								<view class="timeview">
									<text>可用时间：</text>
									<text class="time">{{showTime}}</text>
								</view>
								
							</view>
							
						</view>
						
						<view style="height: 300upx;width: 100%;position: absolute;transform-origin: top;top: 300upx;
						border-top: 2px solid rgba(0,0,0,0.3);transition: 0.7s all;background-color: #edfdf6;
						display: flex;flex-direction: column;justify-content: space-between;"
						:style="{'transform':'rotateX('+bookRotate+'deg)'}">
							<view style="margin: 30upx;">
								<text>
									预约时间
								</text>
								
							</view>
							<view style="display: flex;justify-content: center;align-items: center;margin-bottom: 40upx;">
								<view style="width: 150rpx;height: 70upx;background-color: rgba(102,205,170,1);
								border-radius: 20upx;display: flex;justify-content: center;align-items: center;">
									<text  style="color: white;font-size: 33upx;font-weight: 700;letter-spacing: 10upx;">
										预约
									</text>
									
								</view>
							</view>
							
						</view>
					</view>
					
				</view>
			</view>
			
			
			
				
		</view>
	</view>
	
</template>

<script>
	export default{
		props:{
			index:{
				type:Number
			},
			cid:{
				type:Number
			},
			uid:{
				type:Number
			},
			address:{
				type:String
			},
			location:{
				type:String
			},
			distance:{
				type:Number
			},
			price:{
				type:String
			},
			time:{
				type:Array,
				default:()=>[]
			},
			detail:{
				type:Boolean
			}
		},
		data(){
			return{
				height:300,
				borderleft:"10rpx solid rgba(102,205,170,0.6)",
				borderright:"10rpx solid rgba(102,205,170,0.6)",
				boxshadow:"",
				check:false,
				checkOpacity:0,
				rotate:0,
				checkRight:100,
				animationBook:{},
				animationContact:{},
				animationNavigate:{},
				animationDetail:{},
				buttonRotate:0,
				buttonOpacitty:1,
				bookRotate:-90,
			}
		},
		computed:{
			showTime() {
				var tempDate = new Date();
				var days = tempDate.getDay();
				if(days==0) {
					days=7;
				}
				var showTime="";
				if(this.time[days-1]=="") {
					showTime="-"
				} else {
					showTime=this.time[days-1]
				}
				return showTime;
			}
		},
		methods:{
			tap(){
				this.borderleft="12rpx solid rgba(102,205,170,1)";
				this.borderright="12rpx solid rgba(102,205,170,1)";
				this.boxshadow="0rpx 60rpx 28rpx -60rpx rgba(102,205,170,0.5)";
				this.check=true;
				this.$nextTick(function(){
					this.checkOpacity=1;
					this.checkRight=10;
				});
				
			// 	var animation = uni.createAnimation({
			// 		duration: 100,
			// 		timingFunction: 'ease',
			// 	})	
			// 	animation.rotateX(25).step()
			
			// 	this.animationData = animation.export()
				
			// 	setTimeout(() => {
			// 		var animation2 = uni.createAnimation({
			// 			duration: 200,
			// 			timingFunction: 'ease',
			// 		})	
			// 		animation2.rotateX(-25).scale(1.1,1.1).step()
								
			// 		this.animationData = animation2.export()
			// 	}, 100)
			// 	setTimeout(() => {
			// 		var animation3 = uni.createAnimation({
			// 			duration: 100,
			// 			timingFunction: 'ease',
			// 		})	
			// 		animation3.rotateX(0).scale(1.1,1.1).step()
								
			// 		this.animationData = animation3.export()
			// 	}, 300)
			},
			untap(){
				this.borderleft="10rpx solid rgba(102,205,170,0.6)";
				this.borderright="10rpx solid rgba(102,205,170,0.6)";
				this.boxshadow="";
				this.check=false;
				this.checkOpacity=0;
				this.checkRight=100;
			},
			checkMap(){
				this.$emit('map');
			},
			checkDetail(){
				this.$emit('detail');
			},
			unCheckDetail(){
				this.$emit('undetail');
			},
			navigate(){
				var animation = uni.createAnimation({
					duration: 100,
					timingFunction: 'ease',
				})	
				animation.scale(0.8).step()
				this.animationNavigate = animation.export()
				setTimeout(() => {
					var animation2 = uni.createAnimation({
						duration: 100,
						timingFunction: 'ease',
					})	
					animation2.scale(1).step()
								
					this.animationNavigate = animation2.export();
					this.$store.commit('setNavigateSelected',this.index);
					this.$emit('toLow');
				}, 100)
				
				
			},
			book(){
				this.buttonRotate=180;
				this.buttonOpacitty=0;
				var animation = uni.createAnimation({
					duration: 100,
					timingFunction: 'ease',
				})	
				animation.scale(0.8).step()
				this.animationBook = animation.export()
				setTimeout(() => {
					var animation2 = uni.createAnimation({
						duration: 100,
						timingFunction: 'ease',
					})	
					animation2.scale(1).step()
								
					this.animationBook = animation2.export();
				}, 100)
				setTimeout(()=>{
					this.height=600;
				},200)
				
				setTimeout(()=>{
					this.bookRotate=0;
				},400)
						
			},
			unbook(){
				this.bookRotate=-90;
				setTimeout(()=>{
					this.height=300;
				},100)
				setTimeout(()=>{
					this.buttonRotate=0;
					this.buttonOpacitty=1;
				},400)		
			},
			orderDetail(){
				var animation = uni.createAnimation({
					duration: 100,
					timingFunction: 'ease',
				})	
				animation.scale(0.8).step()
				this.animationDetail = animation.export()
				setTimeout(() => {
					var animation2 = uni.createAnimation({
						duration: 100,
						timingFunction: 'ease',
					})	
					animation2.scale(1).step()
								
					this.animationDetail = animation2.export();
					uni.navigateTo({
						url: '../detail/detail?cid='+this.cid,
					})
				}, 100)
				
			},
			chat(){
				var animation = uni.createAnimation({
					duration: 100,
					timingFunction: 'ease',
				})	
				animation.scale(0.8).step()
				this.animationContact= animation.export()
				setTimeout(() => {
					var animation2 = uni.createAnimation({
						duration: 100,
						timingFunction: 'ease',
					})	
					animation2.scale(1).step()
								
					this.animationContact = animation2.export();
					uni.navigateTo({
						url:'../communication/chat?toUid='+this.uid,
					})
				}, 100)
			},
			bookDetail(){
				uni.navigateTo({
					url: '../detail/detail?cid='+this.cid,
				})
			}
		},
		watch:{
			'detail'(){
				console.log(1)
				if(this.detail==false){
					this.$nextTick(function(){
						this.rotate=0;
						this.bookRotate=-90;
						this.height=300;
						this.buttonRotate=0;
						this.buttonOpacitty=1;
					})
					
				}else if(this.detail==true){
					this.$nextTick(function(){
						this.rotate=-90;
					})
					
				}
			}
		}
	}
</script>

<style scoped>
	.card{
		width: 100%;
		height: 300upx;
		position: absolute;
		transform: translateZ(300upx);
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		transition: all .5s;
	}
	
	.view1{
		display: flex;
		justify-content: space-between;
	}
	
	.view2{
		display: flex;
		justify-content: space-between;
		margin: 15upx;

	}
	
	.location{
		margin:15upx;
		font-size: 30upx;
		font-weight: 700;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		width:350upx;
	}
	
	.distance{
		margin-right:15upx;
		color:rgba(102,205,170,1) ;
	}
	
	.yuan{
		font-weight: 700;
	}
	
	.price{
		font-size: 50upx;
		font-weight: 700;
	}
	
	.time{
		font-size: 35upx;
		font-weight: 700;
		color:rgba(102,205,170,1) ;
		letter-spacing: 3upx;
	}
	
	.timeview{
		margin-top: 15upx;
	}
	
	.image{
		width: 40upx;
		height: 40upx;
		position: relative;
		top:11upx;
		right:5upx;

	}
	
	.image1{
		position: absolute;
		width: 80upx;
		height: 80upx;
		transition: all .5s;
	}
	
	.text{
		margin-top: 10upx;
		margin-left: 15upx;
		opacity: 0.5;
		width: 450upx;
		overflow: hidden;
		-webkit-line-clamp: 3;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
	}
	
	.button{
		position: absolute;
		width: 100%;
		height: 300upx;
		display: flex;
		align-items: center;
		justify-content: space-around;
		transform:translateZ(5upx);
		background-color: rgba(250,255,250,1);
		transition: all .7s;
	}
	
	.smalldetail{
		font-size: 25upx;
		letter-spacing: 0.6upx;
		margin-left: 38upx;
		position: relative;
		bottom: 10upx;
	}
	.smalldetailview {
		height: 180upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		
	}
</style>

