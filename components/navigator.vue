<template>
	<view style="transition: all .1s;position: relative;">
		<view :style="{'height':statusHeight+'px','background-color':backColor}"></view>
		<view style="height: 50px;display: flex;position: relative;" :style="{'background-color':backColor}">
				<view style="color:#FFFFFF ;margin-left: 15upx;margin-top: 25upx;">成都</view>
				<view style="color: rgba(0,0,0,0.6);margin: 15upx;height: 35px;background-color: #FFFFFF;border-radius: 20upx;
					display: flex;flex-direction: column;justify-content: center;" :style="{'width':width+'px'}">
					<text style="margin-left: 10px;font-weight: 700;font-size: 32upx;">易插</text>
				</view>
		</view>
		<view style="background-color:rgb(102,205,170) ;height: 150upx;width: 100%;transition: all .3s;
		position: absolute;display: flex;justify-content: space-around;align-items: flex-end;" 
		:style="{'bottom':bottom+'px','background-color':backColor}">
			<view class="view1" :style="{'margin-bottom':margin1+'px'}">
				<image src="../static/image/scan.png" class="image1"  :style="{'margin-bottom':margin2+'px'}"></image>
				<text class="text1" :style="{'opacity':opacity,'bottom':textBottom+'px'}">扫一扫</text>
			</view>
			<view class="view1" :style="{'margin-bottom':margin1+'px'}">
				<image src="../static/image/connection2.png" class="image1" :style="{'margin-bottom':margin2+'px'}"></image>
				<text class="text1" style="margin-left: 12upx;" :style="{'opacity':opacity,'bottom':textBottom+'px'}">联系</text>
			</view>
			<view class="view1" :style="{'margin-bottom':margin1+'px'}">
				<image src="../static/image/city.png" class="image1" :style="{'margin-bottom':margin2+'px'}" ></image>
				<text class="text1" style="margin-left: 12upx;" :style="{'opacity':opacity,'bottom':textBottom+'px'}">全城</text>
			</view>
			<view class="view1" :style="{'margin-bottom':margin1+'px'}">
				<image src="../static/image/my.png" class="image1" :style="{'margin-bottom':margin2+'px','opacity':opacity}"></image>
				<text class="text1" style="margin-left: 12upx;" :style="{'opacity':opacity,'bottom':textBottom+'px'}">我的</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				statusHeight:0,
				width:0,
				backColor:'rgb(102,205,170)',
				bottom:0,
				margin1:0,
				margin2:0,
				textBottom:0,
				opacity:1,
			}
		},
		props:{
			isLow:{
				type:Boolean,
			},
			buttonSelected:{
				type:Number,
			}
		},
		mounted() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight;
			this.width=uni.getMenuButtonBoundingClientRect().left-30-20;
			this.bottom=-uni.upx2px(150);
			this.margin1=uni.upx2px(50);
			this.margin2=uni.upx2px(10);
			this.textBottom=uni.upx2px(20);
		},
		watch:{
			'$store.state.buttonSelected'(){
				if(this.$store.state.buttonSelected==1){
					this.backColor = "rgb(102,205,170)";
				}	
				else this.backColor=this.$store.state.color;
			},
			'isLow'(){
				if(this.isLow){
					this.bottom=-uni.upx2px(150);
					this.margin1=uni.upx2px(50);
					this.margin2=uni.upx2px(10);
					this.opacity=1;
				}else{ 
					this.bottom=-uni.upx2px(30);
					this.margin1=uni.upx2px(20);
					this.margin2=uni.upx2px(0);
					this.opacity=0;
				};
			}
		}
	}
</script>

<style>
	.view1{
		display: flex;
		flex-direction: column;
		justify-content: flex-end;
		margin-bottom: 20upx;
		color: #FFFFFF;
		transition: all .3s;
	}
	
	.image1{
		width: 80upx;
		height: 80upx;
		margin-bottom: 10upx;
		transition: all .3s;
	}
	
	.text1{
		
		text-align: center;
		transition: all .3s;
		font-weight: 700;
		font-size: 30upx;
		position: absolute;
	}
</style>
