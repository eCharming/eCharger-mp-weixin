<template>
	<view class="card" :style="{'top':top+'px','opacity':opacity}">
		<text class="text1">订单详情</text>
	</view>
</template>

<script>
	export default{
		props:{
			percent:{
				type:Number
			},
		},
		data(){
			return{
			}
		},
		
		computed:{
			top(){
				return uni.upx2px(-30-this.percent*100)
			},
			opacity(){
				return this.percent*this.percent*this.percent
			}
		},
	}
</script>

<style scoped>
	.card{
		position: absolute;
		margin:20upx;
		padding: 15upx;
		padding-right: 20upx;
		padding-left: 20upx;
		background-color: rgba(250,255,250,1);
		border-radius: 55upx;
		display: flex;
		justify-content: center;
		box-shadow:0px 7px 10px -3px #a6a6a6 ;
		/* width: 550upx; */
		height: 90upx;
		top:-30upx;
		left:235upx;
		
		font-size: 32upx;
		letter-spacing: 2upx;
		font-weight: 700;
		transition-property: top;
		transition-duration: .1s;
		transition-property: opacity;
		transition-duration: .1s;
		
	}
	.text1{
		margin: 20upx;
		margin-top: 10upx;
		margin-right: 10upx;
		color:rgba(102,205,170,1) ;
		// border: 2px solid red;
	}
</style>