<template>
	<view style="display: flex;flex-direction:column">
		<view :style="{'height':statusHeight+'px','width':'100%','top':0,'z-index':9999}">
			<view class="cityNavi" :style="{'height':statusHeight+'px','background':color,'position':'fixed','width':'100%','top':0}">
				<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
					@tap="back"></image>
				<text :style="{'margin-bottom':cityHeight+'px'}">关于我们</text>
			</view>
		</view>
		<view style='display: flex;justify-content: center;align-items: center;margin: 50upx;flex-direction: column;margin-top: 100upx;'>
			<image src="../../static/image/icon.png" style="width: 150upx;height:150upx;border-radius: 20upx;"></image>
			<view style='margin:10upx;letter-spacing: 2upx;font-weight: 700;'>易插 v1.0.0</view>
		</view>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="answer">我们的目标</view>
				<view class="problem">我们致力于打造一个节能减排，绿色可持续发展的新能源健康社会体系。</view>
				<view class="divLine"></view>
				<view class="answer">我们的目标</view>
				<view class="problem">我们致力于打造一个节能减排，绿色可持续发展的新能源健康社会体系。</view>
				<view class="divLine"></view>
				<view class="answer">我们的目标</view>
				<view class="problem">我们致力于打造一个节能减排，绿色可持续发展的新能源健康社会体系。</view>
			</view>
			<view></view>
		</addcard>
	</view>
</template>

<script>
	import addcard from '../../components/addCard.vue'
	export default{
		components: {
			addcard
		},
		data() {
			return{
				statusHeight: uni.getSystemInfoSync().statusBarHeight + 50,
				statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
				cityHeight:0,
				color: 'rgba(102,205,170,1)',
			}
		},
		methods:{
			back() {
				uni.navigateBack({})
			},
		},
		mounted() {
			this.cityHeight=(this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
		}
	}
</script>

<style scoped>
	.cityNavi{
		display: flex;
		justify-content: center;
		align-items: flex-end;
		color: #FFFFFF;
		font-size: 35upx;
		font-weight: 700;
		letter-spacing: 2upx;
	}
	.backimg {
		position: absolute;
		left: 8px;
		width: 25px;
		height: 25px;
	}
	
	.divLine {
		width: 100%;
		height: 3upx;
		background-color: #E0E3DA;
		margin-top: 18rpx;
		margin-bottom: 18rpx;
	}
	
	.problem {
		margin:10upx;
		letter-spacing: 2upx;
		/* font-weight: 700; */
		color: #555555;
	}
	.answer {
		margin:10upx;
		margin-bottom:30upx;
		margin-top:30upx;
		letter-spacing: 2upx;
		font-size: larger;
		font-weight: 700;
	}
</style>
