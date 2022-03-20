<template>
	<view class="card" :style="{'margin-top':margintop+'px','margin-bottom':marginbottom+'px'}">
		<view class="near">
			<text>在附近租电桩 ></text>
		</view>
		
		<view class="destination">
			<text>请输入你的目的地</text>
		</view>
	</view>
</template>

<script>
	export default{
		components:{
		},
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
		data(){
			return{
				
			}
		},
		methods:{
			
		}
	}
</script>

<style scoped lang="scss">
	.card{
		margin:20upx;
		padding: 15upx;
		// background-color: #FFFFFF;
		background-color: rgba(250,255,250,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		box-shadow:0px 6px 12px 4px #e5e5e5 ;
		transition-property: margin;
		transition-duration: .3s;
	} 
	
	.near{
		
		margin: 20upx;
		margin-left: 45upx;
		letter-spacing: 3upx;
		font-weight: 700;
		font-size: 30upx;
	}
	
	.destination{
		border-radius: 20upx;
		margin: 15upx;
		margin-left: 25upx;
		margin-right: 25upx;
		padding: 23upx;
		background-color: rgb(230,230,230);
		position: relative;
		font-weight: 700;
		letter-spacing: 2upx;
		font-size: 35upx;
	}
</style>
