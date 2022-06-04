<template>
	<view style="display: flex;justify-content: center;align-items: center;height: 100vh;transform-style: preserve-3d;perspective: 1000upx;perspective-origin: 50% 10%;">
		
		<view style="background-color:rgba(0,185,139,1);height: 25vh;position: absolute;top: 0;width: 100%;transform-origin:left;transition: all 2s;
		display: flex;justify-content: center;align-items: center;"
		:style="{'transform':'rotateY('+backRotate1+'deg)'}"
		>
			<text style="color: white;font-size: 45upx;font-weight: 800;">易插</text>
		</view>
		
		<view style="background-color:rgba(0,185,159,1);height: 25vh;position: absolute;top: 25%;width: 100%;transform-origin:left;transition: all 2s;"
		:style="{'transform':'rotateY('+backRotate2+'deg)'}"></view>
		
		<view style="background-color:rgba(0,185,179,1);height: 25vh;position: absolute;top: 50%;width: 100%;transform-origin:left;transition: all 2s;"
		:style="{'transform':'rotateY('+backRotate3+'deg)'}"></view>
		
		<view style="background-color:rgba(0,185,199,1);height: 25vh;position: absolute;top: 75%;width: 100%;transform-origin:left;transition: all 2s;
		display: flex;justify-content: center;align-items: center;"
		:style="{'transform':'rotateY('+backRotate4+'deg)'}"
		>
			<text style="color: white;font-size: 45upx;font-weight: 800;">eCharger</text>
		</view>
		
		<view style="height: 700upx;width: 700upx;clip-path: polygon(25% 53%, 50% 0, 81% 0, 53% 40%, 77% 40%, 33% 100%, 52% 53%);position: relative;
		transition: all 1s cubic-bezier(.52,-0.46,.46,1.01);"
		:style="{'transform':'scale('+scale+')'}"
		>
			<view class="lightning" style="height: 750upx;width: 750upx;background-color: #fff121;"></view>
			<view class="view1" :style="{'bottom':bottom+'rpx','transform':'rotate('+rotate+'deg)'}"></view>
			<view class="view2" :style="{'bottom':bottom+'rpx','transform':'rotate('+rotate+'deg)'}"></view>
			
		</view>
		
		
		
	</view>
</template>

<script>
	export default{
		data(){
			return{
				rotate:0,
				bottom:0,
				backRotate1:0,
				backRotate2:0,
				backRotate3:0,
				backRotate4:0,
				isAvailable:true,
				scale:1,
			}
		},
		mounted() {
			setTimeout(()=>{
				this.rotate=360;
				this.bottom=750;
				
			},400)
			setTimeout(()=>{
				
				this.scale=0;
			},6000)
			setTimeout(()=>{
				
				this.backRotate4=-120;
			},6500)
			setTimeout(()=>{
				
				this.backRotate3=-120;
			},7000)
			setTimeout(()=>{
				
				this.backRotate2=-120;
			},7500)
			setTimeout(()=>{
				
				this.backRotate1=-120;
			},8000)


		}

	}
</script>

<style scoped>
	.lightning {
		
	}
	
	.view1{
		position: absolute;
		height: 750upx;
		width: 750upx;
		background-color: #fef466;
		border-radius: 52% 25% 62% 52% / 25% 38%;
		transition: 7s all;
	}
	
	.view2{
		position: absolute;
		height: 750upx;
		width: 750upx;
		background-color: #f1eb93;
		border-radius: 70% 30% 65% 35% / 37% 46% 54% 63%  ;
		transition: 7s all;
	}

</style>
