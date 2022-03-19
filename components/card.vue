<template>
	<view class="card" :style="{'margin-top':margintop+'px','margin-bottom':marginbottom+'px'}">
		<slot></slot>
	</view>
</template>

<script>
	export default{
		props:{
			margintop:{
				type:Number,
				default:20
			},
			marginbottom:{
				type:Number,
				default:20
			}
		},
	}
</script>

<style scoped lang="scss">
	.card{
		margin:20upx;
		padding: 15upx;
		background-color: #FFFFFF;
		border-radius: 40upx;
		display: flex;
		justify-content: center;
		box-shadow:0px 6px 12px 4px #e5e5e5 ;
		transition-property: margin;
		transition-duration: .3s;
	}
</style>
