<template>
	<view style="display: flex;flex-direction:column">
		<view :style="{'height':statusHeight+'px','width':'100%','top':0,'z-index':9999}">
			<view class="cityNavi" :style="{'height':statusHeight+'px','background':color,'position':'fixed','width':'100%','top':0}">
				<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
					@tap="back"></image>
				<text :style="{'margin-bottom':cityHeight+'px'}">常见问题</text>
			</view>
		</view>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="problem">1. 附近没有电桩怎么办？</view>
				<view class="divLine"></view>
				<view class="answer">可以用全城功能哦！</view>
			</view>
		</addcard>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="problem">2. 附近没有电桩怎么办？</view>
				<view class="divLine"></view>
				<view class="answer">可以用全城功能哦！</view>
			</view>
		</addcard>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="problem">3. 附近没有电桩怎么办？</view>
				<view class="divLine"></view>
				<view class="answer">可以用全城功能哦！</view>
			</view>
		</addcard>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="problem">4. 附近没有电桩怎么办？</view>
				<view class="divLine"></view>
				<view class="answer">可以用全城功能哦！</view>
			</view>
		</addcard>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="problem">5. 附近没有电桩怎么办？</view>
				<view class="divLine"></view>
				<view class="answer">可以用全城功能哦！</view>
			</view>
		</addcard>
		<addcard>
			<view style='width:100%' class='display'>
				<view class="problem">6. 附近没有电桩怎么办？</view>
				<view class="divLine"></view>
				<view class="answer">可以用全城功能哦！</view>
			</view>
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
		font-weight: 700;
		color: #555555;
	}
	.answer {
		margin:10upx;
		letter-spacing: 2upx;
		font-weight: 700;
	}
	
	.display {
		margin: 15upx;
	}
</style>
