<template>
	<view style="position: relative;">
		<view :style="{'height':statusHeight+'px','background-color':backColor,'transition': 'all .3s'}"></view>
		<view style="height: 50px;display: flex;position: relative;" :style="{'background-color':backColor}">
				<view style="color:#FFFFFF ;margin-left: 15upx;margin-top: 25upx;">成都</view>
				<view style="color: rgba(0,0,0,0.6);margin: 15upx;height: 35px;background-color: #FFFFFF;border-radius: 20upx;
					display: flex;flex-direction: column;justify-content: center;" :style="{'width':width+'px'}">
					<text style="margin-left: 10px;font-weight: 700;font-size: 32upx;">易插</text>
				</view>
		</view>
		<view style="height: 90upx;width: 100%;transition: all .3s;position: absolute;display: flex;justify-content: space-around;align-items: flex-end;" 
		:style="{'bottom':bottom+'px','background-color':backColor}">
			<view>
				<image src="../static/image/scan.png" class="image1"></image>
			</view>
			<view @click="connection()">
				<image src="../static/image/connection2.png" class="image1"></image>
			</view>
			<view>
				<image src="../static/image/city.png" class="image1"></image>
			</view>
			<view>
				<image src="../static/image/my.png" class="image1" :style="{'opacity':opacity}"></image>
			</view>
		</view>
		<view  
		style="height: 60upx;width: 100%;transition: all .3s;
		position: absolute;display: flex;justify-content: space-around;
		transform-style: preserve-3d;transform-origin: top;"
		:style="{'bottom':textBottom+'px','background-color':backColor,'transform':'rotateX('+rotate+'deg)'}">
			<view class="text1">
				<text>扫一扫</text>
			</view>
			<view class="text1" @click="connection()">
				<text>联系</text>
			</view>
			<view class="text1">
				<text>全城</text>
			</view>
			<view class="text1">
				<text>我的</text>
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
				textBottom:0,
				opacity:1,
				rotate:0,
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
		methods:{
			connection(){
				uni.navigateTo({
					url: '../communication/friends',
				});
			}
		},
		mounted() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight;
			this.width=uni.getMenuButtonBoundingClientRect().left-30-20;
			this.bottom=-uni.upx2px(90);
			this.textBottom=-uni.upx2px(150);
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
					this.bottom=-uni.upx2px(90);
					this.textBottom=-uni.upx2px(150);
					this.opacity=1;
					this.rotate=0;
				}else{ 
					this.bottom=-uni.upx2px(0);
					this.textBottom=-uni.upx2px(60);
					this.opacity=0;
					this.rotate=90;
				};
			}
		}
	}
</script>

<style scoped>
	.view1{
		display: flex;
		flex-direction: column;
		justify-content: flex-end;
		color: #FFFFFF;
		transition: all .3s;
	}
	
	.image1{
		width: 80upx;
		height: 80upx;
		margin-bottom: 5upx;
		transition: all .3s;
	}
	
	.text1{
		color: #FFFFFF;
		text-align: center;
		transition: all .3s;
		font-weight: 700;
		font-size: 30upx;
		width: 100upx;
		margin-top: 5upx;
	}
</style>
