<template>
	<view class="card" 
		:style="{'border-left':borderleft,'border-right':borderright,'box-shadow':boxshadow}"
	@tap='editCharger'>
		<view class="view1">
			<text class="location">{{location}}</text>
			<text class="state1" v-if="state">空闲中</text>
			<text class="state2" v-if="!state">使用中</text>
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
</template>

<script>
	export default{
		props:{
			location:{
				type:String
			},
			state:{
				type:Boolean
			},
			price:{
				type:String
			},
			cid:{
				type:String
			},
			time:{
				type:Array,
				default:()=>[]
			}
			
		},
		data(){
			return{
				borderleft:"10rpx solid rgba(50,200,210,0.6)",
				borderright:"10rpx solid rgba(50,200,210,0.6)",
				boxshadow:"",
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
				this.borderleft="12rpx solid rgba(50,200,210,1)";
				this.borderright="12rpx solid rgba(50,200,210,1)";
				this.boxshadow="0rpx 60rpx 28rpx -60rpx rgba(50,200,210,0.5)";
			},
			untap(){
				this.borderleft="10rpx solid rgba(50,200,210,0.6)";
				this.borderright="10rpx solid rgba(50,200,210,0.6)";
				this.boxshadow="";
			},
			editCharger() {
				uni.navigateTo({
					url: '../editCharger/editCharger?cid='+this.cid,
				});
			}
		}
	}
</script>

<style scoped>
	.card{
		margin:20upx;
		margin-bottom: 40upx;;
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		transition-property: box-shadow,border;
		transition-duration: .5s;
	}
	
	.view1{
		display: flex;
		justify-content: space-between;
	}
	
	.view2{
		display: flex;
		justify-content: space-between;
		margin: 15upx;
		margin-top: 60upx;
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
	
	.state1{
		margin:15upx;
		margin-top: 20upx;
		color:rgb(50,200,210) ;
	}
	
	.state2{
		margin:15upx;
		margin-top: 20upx;
		color:rgb(255,99,71) ;
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
		color:rgba(50,200,210,1) ;
		letter-spacing: 3upx;
	}
	
	.timeview{
		margin-top: 15upx;
	}
</style>

