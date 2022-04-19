<template>
	<view>
		<view class="navigator" :style="{'height':statusHeight+'px'}">
			<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}" @tap="back"></image>
			<text :style="{'margin-bottom':myBottom+'px'}">我的</text>
		</view>
		
		<view class="avatar-view" style="height: 220rpx;display: flex;justify-content: center;align-items: center;margin: 20upx;">
			<view style="display: flex;flex-direction: column;align-items: center;color: rgba(0,0,0,0.6);font-size: 35rpx;">
				<image :src="avatarUrl" style="height: 130rpx;width: 130rpx;border-radius: 50%;margin: 20rpx;"></image>
				<text>{{userName}}</text>
			</view>
		</view>
		
		<view class="function-view">
			<view>
				<text>历史订单</text>
			</view>
			<view>
				<text>我的电桩</text>
			</view>
			<view>
				<text>常见问题</text>
			</view>
		</view>
		
		<view class="other-view">
			<view>
				消息推送设置
			</view>
			<view>
				关于我们
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				uid:-1,
				avatarUrl:'',
				userName:'',
				myBottom:0,//联系人距离导航栏底部距离
				statusHeight:0,//导航栏高度
				statusBarHeight:uni.getSystemInfoSync().statusBarHeight,//状态栏高度
			}
		},
		methods:{
			back() {
				uni.navigateBack({
				})
			},
		},
		mounted() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight+50;
			this.myBottom=(this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
			this.avatarUrl=this.$store.state.avatarUrl;
			this.userName=this.$store.state.userName;
		}
	}
</script>

<style scoped>
	.navigator{
		background-color: rgba(102,205,170,1);
		display: flex;
		justify-content: center;
		align-items: flex-end;
		color: #FFFFFF;
		font-size: 35upx;
		font-weight: 700;
		letter-spacing: 2upx;
	}
	
	.backimg {
		position:absolute;
		left:8px;
		width:25px;
		height:25px;
	}
	
	.function-view{
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		border-radius: 40upx;
		height: 220rpx;
		display: flex;
		justify-content: space-around;
		margin-left: 40rpx;
		margin-right: 40rpx;
	}
	
	.other-view{
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		border-radius: 40upx;
		height: 420rpx;
		margin-top: 40rpx;
		margin-left: 40rpx;
		margin-right: 40rpx;
	}
</style>
