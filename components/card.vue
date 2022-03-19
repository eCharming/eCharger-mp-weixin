<template>
	<view class="card">
		<slot></slot>
	</view>
</template>

<script>
</script>

<style scoped lang="scss">
	.card{
		margin:15upx;
		padding: 15upx;
		background-color: #FFFFFF;
		border-radius: 70upx;
		display: flex;
		justify-content: center;
		box-shadow:0px 6px 12px 4px #e5e5e5 ;
	}
</style>
