<template>
	<view  style="display: flex;flex-direction:column">
		<view :style="{'height':statusHeight+'px','width':'100%','top':0,'z-index':9999}">
			<view :style="{'height':statusHeight+'px','background':color,'position':'fixed','width':'100%','top':0}">
				<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
					@tap="back"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				statusHeight:uni.getSystemInfoSync().statusBarHeight+50,
				statusBarHeight:uni.getSystemInfoSync().statusBarHeight,
				color:'rgba(102,205,170,1)',
				
			}
		},
		methods: {
			back() {
				uni.navigateBack({
				})
			},
		}
	}
</script>

<style scoped>
.backimg {
		position:absolute;
		left:8px;
		width:25px;
		height:25px;
	}
</style>
