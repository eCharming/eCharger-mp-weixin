<template>
	<view style="position: relative;height: 300upx;margin:20upx;margin-bottom: 40upx;transform-style: preserve-3d; transition: .7s all;"
	:style="{'transform':'rotateY('+rotate+'deg)'}">
		<view class="card"
			:style="{'border-left':borderleft,'border-right':borderright,'box-shadow':boxshadow,'height':height + 'rpx'}"
		>
			<view style="display: flex;flex-direction: column;">
				<view class="view1">
					<text class="location">{{location}}</text>
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
		<view style="position: absolute;transform: rotateY(90deg) translateZ(300upx);
		height: 300upx;width: 100%;display:flex;justify-content: center;flex-direction: column;
		border-radius: 30upx;border-top: 9upx solid rgba(102,205,170,1);border-bottom: 9upx solid rgba(102,205,170,1);">
			<view class="button">
				<view class="smalldetailview">
					<image src="../static/image/order.png" style="height: 125upx;width: 125upx;"></image>
					<text class="smalldetail">预约</text>
				</view>
				<view class="smalldetailview">
					<image src="../static/image/connection.png" style="height: 125upx;width: 125upx;"></image>
					<text class="smalldetail">联系</text>
				</view>
				<view class="smalldetailview" @click.native.stop.prevent="navigate">
					<image src="../static/image/navigation.png" style="height: 125upx;width: 125upx;"></image>
					<text class="smalldetail">导航</text>
				</view>
				<view class="smalldetailview">
					<image src="../static/image/chargerdetail.png" style="height: 125upx;width: 125upx;"></image>
					<text class="smalldetail">详情</text>
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
				borderleft:"10rpx solid rgba(102,205,170,0.6)",
				borderright:"10rpx solid rgba(102,205,170,0.6)",
				boxshadow:"",
				check:false,
				checkOpacity:0,
				opacity:0,
				rotate:0,
				height:300,
				// bottom:200,可能无用
				checkRight:100,
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
			},
			untap(){
				this.borderleft="10rpx solid rgba(102,205,170,0.6)";
				this.borderright="10rpx solid rgba(102,205,170,0.6)";
				this.boxshadow="";
				this.check=false;
				this.checkOpacity=0;
				this.checkRight=100;
			},
			checkDetail(){
				this.$emit('emit');
				this.check=false;
			},
			navigate(){
				this.$store.commit('setNavigateSelected',this.index);
				this.$emit('toLow');
			}
		},
		watch:{
			'detail'(){
				if(this.detail==false){
					this.$nextTick(function(){
						this.height=300;
						this.opacity=0;
						// this.bottom=200;
						this.rotate=0;
					})
					
				}else if(this.detail==true){
					this.$nextTick(function(){
						this.check=false;
						this.checkOpacity=0;
						this.checkRight=100;
						this.opacity=1;
						// this.bottom=400;
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
		display: flex;
		justify-content: space-around;
	}
	
	.smalldetail{
		font-size: 25upx;
		letter-spacing: 0.6upx;
		margin-left: 38upx;
		position: relative;
		bottom: 10upx;
	}
	.smalldetailview {
		display: flex;
		flex-direction: column;
		justify-content: center;
	}
</style>

