<template>
	<view>
		<view 
			class="main" 
			:style="{'top':currentheight +'px'}"
			@touchmove="start($event)"
		>
			<view class="content">
				<view class="touchline">
					<view class="line"></view>
				</view>
				<slot></slot>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				currentheight:0,
				ismove:false,
				
			}
		},
		props: {
			minHeight: {
				type: Number,
				default: 0.3
			},
			maxHeight: {
				type: Number,
				default: 0.5
			},
		},
		methods:{
			start(event){

				this.currentheight=event.touches[0].clientY;
				
			}
		}
	}
</script>

<style lang="scss" scoped>
	.main{
		height:100%;
		width:750upx;
		position: fixed;
		background-color: #FFFFFF;
		padding: 0 12px;
		border-top-left-radius:50upx;
		border-top-right-radius:50upx;
	}
	
	.content{
		height:100%;
		
	}
	
	.touchline{
		margin-top: 20rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.line{
		margin: 0px;
		vertical-align: middle;
		border-bottom: 8rpx solid rgb(214, 215, 217);
		width: 60rpx;
		transform: scaleY(0.5);
		border-top-color: rgb(214, 215, 217);
		border-right-color: rgb(214, 215, 217);
		border-left-color: rgb(214, 215, 217);
	}
</style>
