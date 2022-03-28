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
			<view style="display: flex;justify-content: space-between;">
				<text class="text">{{location}}</text>
				<image class="image1" :style="{'opacity':opacity}" src='../static/image/checkdetail.png' v-if="check" @click.native.stop.prevent="checkDetail"></image>
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
				opacity:0,
			}
		},
		methods:{
			tap(){
				this.borderleft="6px solid rgba(102,205,170,1)";
				this.borderright="6px solid rgba(102,205,170,1)";
				this.boxshadow="0px 33px 15px -30px rgba(102,205,170,0.5)";
				this.check=true;
				this.$nextTick(function(){
					this.opacity=1;
				});
			},
			untap(){
				this.borderleft="5px solid rgba(102,205,170,0.6)";
				this.borderright="5px solid rgba(102,205,170,0.6)";
				this.boxshadow="";
				this.check=false;
				this.opacity=0;
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
					})
					
				}else if(this.detail==true){
					this.$nextTick(function(){
						
						this.height=uni.upx2px(680)+'px';
						this.check=false;
						this.opacity=0;
					})
					
				}
			}
		},
		mounted(){
			this.height=uni.upx2px(300)+'px';
		}
	}
</script>

<style scoped>
	.card{
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
		width: 80upx;
		height: 80upx;
		margin-right: 5upx;
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
</style>

