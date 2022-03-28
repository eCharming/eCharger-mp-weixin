<template>
	<view class="card" 
		:style="{'border-left':borderleft,'border-right':borderright,'box-shadow':boxshadow,'height':height}"
		>
		<view style="display: flex;flex-direction: column;">
			<view class="view1">
				<text class="location">{{location}}</text>
				<view>
					<image class="image" src="../static/image/arrow.png"></image>
					<text class="distance">{{distance}}km</text>
				</view>
				
			</view>
			<view style="display: flex;justify-content: space-between;position: relative;">
				<text class="text">{{location}}</text>
				<image class="image1" :style="{'opacity':checkOpacity,'right':checkRight}" src='../static/image/checkdetail.png' v-if="check" 
				@click.native.stop.prevent="checkDetail"></image>
			</view>
			
		</view>
		
		<view class="button" v-if="detail" :style="{'top':bottom,'opacity':opacity}">
			<view style="display: flex;flex-direction: column;justify-content: center;">
				<image src="../static/image/order.png" style="height: 125upx;width: 125upx;"></image>
				<text style="font-size: 25upx;letter-spacing: 0.3px;margin-left: 38upx;position: relative;bottom: 10upx;">预约</text>
			</view>
			<view style="display: flex;flex-direction: column;justify-content: center;">
				<image src="../static/image/connection.png" style="height: 125upx;width: 125upx;"></image>
				<text style="font-size: 25upx;letter-spacing: 0.3px;margin-left: 38upx;position: relative;bottom: 10upx;">联系</text>
			</view>
			<view style="display: flex;flex-direction: column;justify-content: center;">
				<image src="../static/image/navigation.png" style="height: 125upx;width: 125upx;"></image>
				<text style="font-size: 25upx;letter-spacing: 0.3px;margin-left: 38upx;position: relative;bottom: 10upx;">导航</text>
			</view>
			<view style="display: flex;flex-direction: column;justify-content: center;">
				<image src="../static/image/chargerdetail.png" style="height: 125upx;width: 125upx;"></image>
				<text style="font-size: 25upx;letter-spacing: 0.3px;margin-left: 38upx;position: relative;bottom: 10upx;">详情</text>
			</view>
		</view>
			
			
			
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
			},
			detail:{
				type:Boolean
			}
		},
		data(){
			return{
				borderleft:"5px solid rgba(102,205,170,0.6)",
				borderright:"5px solid rgba(102,205,170,0.6)",
				boxshadow:"",
				height:"",
				check:false,
				checkOpacity:0,
				checkRight:0,
				opacity:0,
				bottom:0,
			}
		},
		methods:{
			tap(){
				this.borderleft="6px solid rgba(102,205,170,1)";
				this.borderright="6px solid rgba(102,205,170,1)";
				this.boxshadow="0px 33px 15px -30px rgba(102,205,170,0.5)";
				this.check=true;
				this.$nextTick(function(){
					this.checkOpacity=1;
					this.checkRight=uni.upx2px(10)+'px';
				});
			},
			untap(){
				this.borderleft="5px solid rgba(102,205,170,0.6)";
				this.borderright="5px solid rgba(102,205,170,0.6)";
				this.boxshadow="";
				this.check=false;
				this.checkOpacity=0;
				this.checkRight=uni.upx2px(100)+'px';
			},
			checkDetail(){
				this.$emit('emit');
				this.check=false;
			}
		},
		watch:{
			'detail'(){
				if(this.detail==false){
					this.$nextTick(function(){
						this.height=uni.upx2px(300)+'px';
						this.opacity=0;
						this.bottom=uni.upx2px(200)+'px';
					})
					
				}else if(this.detail==true){
					this.$nextTick(function(){
						
						this.height=uni.upx2px(680)+'px';
						this.check=false;
						this.checkOpacity=0;
						this.checkRight=uni.upx2px(100)+'px';
						this.opacity=1;
						this.bottom=uni.upx2px(400)+'px';
					})
					
				}
			}
		},
		mounted(){
			this.height=uni.upx2px(300)+'px';
			this.bottom=uni.upx2px(200)+'px';
			this.checkRight=uni.upx2px(100)+'px';
		}
	}
</script>

<style scoped>
	.card{
		position: relative;
		margin:20upx;
		margin-bottom: 40upx; 
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		transition: all .5s;
	}
	
	.view1{
		display: flex;
		justify-content: space-between;
	}
	
	.view2{
		display: flex;
		justify-content: space-between;
		margin: 15upx;

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
	
	.image1{
		position: absolute;
		width: 80upx;
		height: 80upx;
		/* margin-right: 5upx; */
		transition: all .5s;
		/* border: 2px solid red; */
	}
	
	.text{
		margin-top: 10upx;
		margin-left: 15upx;
		opacity: 0.5;
		width: 450upx;
		/* border: 2px solid red; */
		/* white-space: nowrap; */
		overflow: hidden;
		-webkit-line-clamp: 3;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-box-orient: vertical;
	}
	
	.button{
		display: flex;
		justify-content: space-around;
		position: absolute;
		/* bottom: 150upx; */
		width: 550upx;
		transition: all .5s;
	}
</style>

