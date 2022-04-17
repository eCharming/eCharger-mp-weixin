<template>
	<view style="position: relative;perspective: 500upx;transform-style: preserve-3d;">
		<view :style="{'height':statusHeight+'px','background-color':backColor,'transition': 'all .3s'}"></view>
		<view style="height: 50px;display: flex;align-items: center;position: relative;transition:background-color .3s ;" 
		:style="{'background-color':backColor,'pointer-events':pointerEvents}">
				<view style="color:#FFFFFF ;margin-left: 15upx;margin-top: 25upx;height:60upx ;">成都</view>
				<view style="color: rgba(0,0,0,0.6);margin: 15upx;height: 60upx;background-color: #FFFFFF;border-radius: 20upx;
					display: flex;flex-direction: column;justify-content: center;" :style="{'width':width+'px'}">
					<text style="margin-left: 20upx;font-weight: 700;font-size: 32upx;">易插</text>
				</view>
		</view>
		
		
		
		<view style="height: 32upx;position: absolute;width: 100%;border-bottom-left-radius: 20upx;border-bottom-right-radius: 25upx;
		transition: all .3s;"
		:style="{'bottom':lastBottom+'px','background-color':secondColor}"></view>
		
		
		<view style="height: 92upx;width: 100%;transition: all .3s;position: absolute;" 
		:style="{'bottom':bottom+'px','background-color':backColor}">
			<view style="height: 70upx;width: 140upx;position: absolute;border-top-left-radius: 70upx;border-top-right-radius: 70upx;
			transition-property: left,background-color;transition-duration:.5s,.3s;bottom: 0;"
			:style="{'left':left+'px','background-color':secondColor}">
				<view style="width: 40upx;height: 24upx;position: absolute;bottom: -2upx;left: -35upx;transition: all .3s;"
				:style="{'background-color':secondColor}"></view>
				<view style="width: 70upx;height: 70upx;position: absolute;bottom: 0;left: -64upx;border-radius:50%;transition: all .3s;"
				:style="{'background-color':backColor}"></view>
				<view style="width: 40upx;height: 24upx;position: absolute;bottom: -2upx;right: -35upx;transition: all .3s;"
				:style="{'background-color':secondColor}"></view>
				<view style="width: 70upx;height: 70upx;position: absolute;bottom: 0;right: -64upx;border-radius:50%;transition: all .3s;"
				:style="{'background-color':backColor}"></view>
				<view style="height: 110upx;width: 110upx;position: absolute;border-radius:50%;left: 16upx;bottom: -55upx;
				display: flex;justify-content: center;align-items: center;" :style="{'background-color':backColor}"></view>
			</view>
			<view @click="tap(0)" style="position: absolute;left: 57upx;transition: all .5s;" :style="{'bottom':imageBottom[0]+'px'}">
				<image src="../static/image/scan.png" class="image1"></image>
			</view>
			<view @click="tap(1)" style="position: absolute;left: 242upx;transition: all .5s;" :style="{'bottom':imageBottom[1]+'px'}">
				<image src="../static/image/connection2.png" class="image1"></image>
			</view>
			<view @click="tap(2)" style="position: absolute;left: 430upx;transition: all .5s;" :style="{'bottom':imageBottom[2]+'px'}">
				<image src="../static/image/city.png" class="image1"></image>
			</view>
			<view @click="tap(3)" style="position: absolute;left: 621upx;transition: all .5s;" :style="{'bottom':imageBottom[3]+'px'}">
				<image src="../static/image/my.png" class="image1" :style="{'opacity':opacity}"></image>
			</view>
		</view>
		
		
		<view style="position: absolute;width: 100%;height: 30upx;transition: all .3s;" 
		:style="{'bottom':textBottom+'px','background-color':backColor}">
			<view :style="{'opacity':opacity}" style="transition: all .5s;">
				<text style="position: absolute;text-align: center;left: 52upx;color: #FFFFFF;font-size: 30upx;font-weight: 700;transition: all .5s;"
				:style="{'bottom':word[0].bottom+'px','opacity':word[0].opacity}">扫一扫</text>
				<text style="position: absolute;text-align: center;left: 250upx;color: #FFFFFF;font-size: 30upx;font-weight: 700;transition: all .5s;"
				:style="{'bottom':word[1].bottom+'px','opacity':word[1].opacity}">联系</text>
				<text style="position: absolute;text-align: center;left: 440upx;color: #FFFFFF;font-size: 30upx;font-weight: 700;transition: all .5s;"
				:style="{'bottom':word[2].bottom+'px','opacity':word[2].opacity}">全城</text>
				<text style="position: absolute;text-align: center;left: 630upx;color: #FFFFFF;font-size: 30upx;font-weight: 700;transition: all .5s;"
				:style="{'bottom':word[3].bottom+'px','opacity':word[3].opacity}">我的</text>
			</view>
			
		</view>
		
		<!-- <view  
		style="height: 62upx;width: 100%;transition: all .3s;
		position: absolute;display: flex;justify-content: space-around;
		transform-origin: top;"
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
		</view> -->
		
	</view>
</template>

<script>
	export default{
		data(){
			return{
				statusHeight:0,
				width:0,
				backColor:'rgb(102,205,170)',
				secondColor:'rgb(166,236,146)',
				bottom:0,
				lastBottom:0,
				textBottom:0,
				imageBottom:[0,0,0,0],
				word:[{
					bottom:0,
					opacity:1,
				},{
					bottom:0,
					opacity:0,
				},{
					bottom:0,
					opacity:0,
				},{
					bottom:0,
					opacity:0,
				},],
				opacity:1,
				rotate:0,
				selected:0,
				left:0,
				pointerEvents:'auto',
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
			tap(index){
				if(index==this.selected){
					if(index==0){

					}else if(index==1){
						uni.navigateTo({
							url: '../communication/friends',
						});
					}else if(index==2){

					}else if(index==3){

					}
				}else{
					if(index==0){
						this.left=uni.upx2px(25);
						this.word[0].bottom=-uni.upx2px(10);
						this.word[0].opacity=1;
						this.word[this.selected].bottom=uni.upx2px(35);
						this.word[this.selected].opacity=0;
						this.imageBottom[0]=-uni.upx2px(48);
						this.imageBottom[this.selected]=uni.upx2px(5);
						this.selected=0;
					}else if(index==1){
						this.left=uni.upx2px(210);
						this.word[1].bottom=-uni.upx2px(10);
						this.word[1].opacity=1;
						this.word[this.selected].bottom=uni.upx2px(35);
						this.word[this.selected].opacity=0;
						this.imageBottom[1]=-uni.upx2px(48);
						this.imageBottom[this.selected]=uni.upx2px(5);
						this.selected=1;
					}else if(index==2){
						this.left=uni.upx2px(400);
						this.word[2].bottom=-uni.upx2px(10);
						this.word[2].opacity=1;
						this.word[this.selected].bottom=uni.upx2px(35);
						this.word[this.selected].opacity=0;
						this.imageBottom[2]=-uni.upx2px(48);
						this.imageBottom[this.selected]=uni.upx2px(5);
						this.selected=2;
					}else if(index==3){
						this.left=uni.upx2px(590);
						this.word[3].bottom=-uni.upx2px(10);
						this.word[3].opacity=1;
						this.word[this.selected].bottom=uni.upx2px(35);
						this.word[this.selected].opacity=0;
						this.imageBottom[3]=-uni.upx2px(48);
						this.imageBottom[this.selected]=uni.upx2px(5);
						this.selected=3;
					}
				}
			}
		},
		mounted() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight;
			this.width=uni.getMenuButtonBoundingClientRect().left-30-20;
			this.textBottom=-uni.upx2px(30);
			this.bottom=-uni.upx2px(120);
			this.lastBottom=-uni.upx2px(150);
			this.left=uni.upx2px(25);
			this.imageBottom[0]=-uni.upx2px(45);
			this.imageBottom[1]=uni.upx2px(5);
			this.imageBottom[2]=uni.upx2px(5);
			this.imageBottom[3]=uni.upx2px(5);
			this.word[0].bottom=-uni.upx2px(10);
			this.word[1].bottom=uni.upx2px(35);
			this.word[2].bottom=uni.upx2px(35);
			this.word[3].bottom=uni.upx2px(35);
		},
		watch:{
			'$store.state.buttonSelected'(){
				if(this.$store.state.buttonSelected==1){
					this.backColor = "rgb(102,205,170)";
					this.secondColor='rgb(166,236,146)';
				}	
				else{
					this.backColor=this.$store.state.color;
					this.secondColor='rgb(152,245,255)';
				} 
			},
			'isLow'(){
				if(this.isLow){
					this.bottom=-uni.upx2px(120);
					this.textBottom=-uni.upx2px(30);
					this.lastBottom=-uni.upx2px(150);
					this.opacity=1;
					this.rotate=0;
					this.pointerEvents='auto';
				}else{ 
					this.bottom=-uni.upx2px(0);
					this.textBottom=uni.upx2px(90);
					this.lastBottom=-uni.upx2px(30);
					this.opacity=0;
					this.rotate=-90;
					this.pointerEvents='none';
					if(this.selected==3){
						this.tap(2);
					}
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
		margin-top: 7upx;
	}
</style>
