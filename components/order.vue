<template>
	<view class="card" 
		:style="{'border-left':borderleft,'border-right':borderright,'box-shadow':boxshadow}"
		>
		<view class="view1">
			<text class="location">{{location}}</text>
			<view>
				<image class="image" src="../static/image/arrow.png"></image>
				<text class="distance">{{distance}}km</text>
			</view>
			
		</view>
		<text class="text">{{location}}</text>
		
		<view class="view2">
			<view class="priceview">
				<text>价格</text>
				<text class="yuan">￥</text>
				<text class="price">{{price}}</text>
			</view>
			<view class="timeview">
				<text>可用时间：</text>
				<text class="time">{{startTime}}-{{endTime}}</text>
			</view>
			
		</view>
		
	</view>
</template>

<script>
	export default{
		props:{
			location:{
				type:String
			},
			distance:{
				type:Number
			},
			price:{
				type:String
			},
			startTime:{
				type:String
			},
			endTime:{
				type:String
			}
		},
		data(){
			return{
				borderleft:"5px solid rgba(102,205,170,0.6)",
				borderright:"5px solid rgba(102,205,170,0.6)",
				boxshadow:"",
				
			}
		},
		methods:{
			tap(){
				this.borderleft="6px solid rgba(102,205,170,1)";
				this.borderright="6px solid rgba(102,205,170,1)";
				this.boxshadow="0px 33px 15px -30px rgba(102,205,170,0.5)";
			},
			untap(){
				this.borderleft="5px solid rgba(102,205,170,0.6)";
				this.borderright="5px solid rgba(102,205,170,0.6)";
				this.boxshadow="";
			}
		}
	}
</script>

<style scoped>
	.card{
		margin:20upx;
		margin-bottom: 40upx;;
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		transition-property: box-shadow,border;
		transition-duration: .5s;
	}
	
	.view1{
		display: flex;
		justify-content: space-between;
	}
	
	.view2{
		display: flex;
		justify-content: space-between;
		margin: 15upx;
		margin-top: 60upx;
	}
	
	.location{
		margin:15upx;
		font-size: 30upx;
		font-weight: 700;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		width:350upx;
	}
	
	.distance{
		margin-right:15upx;
		color:rgba(102,205,170,1) ;
	}
	
	.yuan{
		font-weight: 700;
	}
	
	.price{
		font-size: 50upx;
		font-weight: 700;
	}
	
	.time{
		font-size: 35upx;
		font-weight: 700;
		color:rgba(102,205,170,1) ;
		letter-spacing: 3upx;
	}
	
	.timeview{
		margin-top: 15upx;
	}
	
	.image{
		width: 40upx;
		height: 40upx;
		position: relative;
		top:11upx;
		right:5upx;

	}
	
	.text{
		margin-top: 10upx;
		margin-left: 15upx;
		opacity: 0.5;
	}
</style>

