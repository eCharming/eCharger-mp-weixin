<template>
	<view>
		<view 
			class="main" 
			
			:style="{'top':currentHeight+'px'}"
			:animation="animationData"
			@touchstart="start($event)"
			@touchmove.stop.prevent='isSuccess?move($event):still'
			@touchend="end" 
		>
			<view class="content">
				<view class="touchline">
					<view class="line"></view>
				</view>
				<slot></slot>
			</view>
		</view>
	</view>

</template>

<script>
	export default {
		data(){
			return{
				currentHeight:200,//当前高度 单位px
				originHeight:0,//滑动开始时的高度 单位px
				isMove:false,//是否正在滑动
				isSuccess:false,//若滑动开始时手指在顶端附近则开始滑动
				windowHeight:0,//本机的高度 单位px
				windowWidth:0,//本机的宽度 单位px
				isLow:true,//滑动开始前上拉框处在低位则为真，在高位则为假
				animationData:{}
			}
		},
		props: {
			minHeight: {
				type: Number,
				default: 0.2
			},
			maxHeight: {
				type: Number,
				default: 0.7
			},
		},
		methods:{
			start(event){
				this.isMove=true;
				if(this.isLow){
					console.log(this.currentHeight)
					this.originHeight=(1-this.minHeight)*this.windowHeight;
					this.currentHeight=(1-this.minHeight)*this.windowHeight;
				}else{
					this.originHeight=(1-this.maxHeight)*this.windowHeight;
					this.currentHeight=(1-this.maxHeight)*this.windowHeight;
					console.log(this.currentHeight)
				} 
				var firstTouch=event.touches[0].clientY;
				if(firstTouch-this.currentHeight<=this.windowHeight*0.1)
					this.isSuccess=true;
				else this.isSuccess=false;
			},
			move(event){
				console.log(this.currentHeight);
				var mediaVariable=event.touches[0].clientY;//运用中间变量
				if(mediaVariable>=(1-this.maxHeight)*this.windowHeight&&mediaVariable<=(1-this.minHeight)*this.windowHeight)//当超过上下界线时就不对当前高度赋值
					this.currentHeight=mediaVariable;
			},
			end(){
				this.isMove=false;
				this.isSuccess=false;
				var thisHeight=this.currentHeight;//因为动画滞后因而需要一个副本来记录currentHeight的值
				var animation=uni.createAnimation({
					duration: 500,
					timingFunction: "ease-out",
				});
				if(Math.abs(this.currentHeight-this.originHeight)>=0.4*(this.maxHeight-this.minHeight)*this.windowHeight){  //当滑动结束时若移动距离超过上下界线的40%则直接移动到另一端
					if(this.isLow){ //如果在低位则向高位移动
						animation.translateY((1-this.maxHeight)*this.windowHeight-thisHeight).step();
						this.animationData=animation.export();
						this.isLow=false;
					}else{
						animation.translateY((1-this.minHeight)*this.windowHeight-thisHeight).step();
						this.animationData=animation.export();
						this.isLow=true;
					}
				}else{
					if(this.isLow){ //如果在低位则向低位移动
						animation.translateY((1-this.minHeight)*this.windowHeight-thisHeight).step();
						this.animationData=animation.export();
					}else{
						animation.translateY((1-this.maxHeight)*this.windowHeight-thisHeight).step();
						this.animationData=animation.export();
					}
				}
				console.log(this.currentHeight)
				 
			},
			still(){}
		},
		mounted() {
			this.windowHeight = uni.getSystemInfoSync().windowHeight;
			this.windowWidth = uni.getSystemInfoSync().windowWidth;
			this.currentHeight=(1-this.minHeight)*this.windowHeight;
			console.log(this.windowHeight);
		}
	}
</script>

<style lang="scss" scoped>
	.main{
		height:100%;
		width:750upx;
		position: fixed;
		background-color: #FFFFFF;
		padding: 0 12px;
		border-top-left-radius:50upx;
		border-top-right-radius:50upx;
	}
	
	.content{
		height:100%;
		
	}
	
	.touchline{
		margin-top: 20rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.line{
		margin: 0px;
		vertical-align: middle;
		border-bottom: 8rpx solid rgb(214, 215, 217);
		width: 60rpx;
		transform: scaleY(0.5);
		border-top-color: rgb(214, 215, 217);
		border-right-color: rgb(214, 215, 217);
		border-left-color: rgb(214, 215, 217);
	}
</style>
