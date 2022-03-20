<template>
	<view class="card">
		<slot></slot>
	</view>
</template>

<script>
	export default{
	}
</script>

<style scoped lang="scss">
	.card{
		margin:20upx;
		margin-bottom: 30upx;;
		padding: 15upx;
		// background-image: linear-gradient(to bottom right, #FFFFFF,rgba(102,205,170,0.05), #FFFFFF,rgba(102,205,170,0.05), #FFFFFF,rgba(102,205,170,0.05), #FFFFFF,);
		background-color: rgba(252,255,252,1);
		// background-color: #FFFFFF;
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		box-shadow:0px 6px 12px 2px #e5e5e5 ;
	}
</style>

