<template>
	<view>
		<view class="navigator" :style="{'height':statusHeight+'px'}">
			<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}" @tap="back"></image>
			<text :style="{'margin-bottom':myBottom+'px'}">我的</text>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				uid:-1,
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
		onLoad() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight+50;
			this.myBottom=(this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
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
		border: 2px solid red;
	}
	
	.backimg {
		position:absolute;
		left:8px;
		width:25px;
		height:25px;
	}
</style>
