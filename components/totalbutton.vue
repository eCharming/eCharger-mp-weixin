<template>
	<button 
		id="btn" 
		class="btn" 
		:plain="true"
		:style="{'border-color':bordercolor}"
	>
		<view class="view1">
			<text class="text1" :style="{'top':top+'rpx'}">{{text1}}</text>
		</view>
		<view class="view2">
			<text class="text2" :style="{'opacity':opacity}">{{text2}}</text>
		</view>
		<view>
			<slot></slot>
		</view>
		
	</button>
</template>
 
<script>
	export default{
		data(){
			return {
				bordercolor:"rgba(0,0,0,0.3)",
				opacity:0,
				top:20,
			}
		},
		props:{
			type:{
				type:Number,
			},
			text1:{
				type:String
			},
			text2:{
				type:String
			},
			selected:{
				type:Boolean,
				default:false
			},
			isLow:{
				type:Boolean
			}
		},
		watch:{
			'selected':{ 
				immediate:true,
				handler:function(){
					if(this.selected)
						this.activate();
					else this.inactivate();
				}
			},
			'isLow'(){
				if(this.isLow){
					this.top=20;
					this.opacity=0;
				}else{
					this.top=5;
					this.opacity=1;
				}
			}
		},
		methods:{
			activate(){
				if(this.type==1)
					this.bordercolor="rgba(102,205,170,1)";
				else this.bordercolor=this.$store.state.color;
			},
			inactivate(){
				this.bordercolor="rgba(0,0,0,0.1)";
			} 
		}
	}
</script>

<style scoped>
	.btn{
		height: 120upx;
		width : 280upx;
		border-radius: 20upx;
		margin: 20upx;
		padding-left: 10upx;
		border: 6upx solid;
		overflow: visible;
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		// align-content:flex-start; /*把多余的行高放到两边*/
		transition-property: border;
		transition-duration: .3s;
	}
	.view1{
		font-weight:700 ;
		font-size: 30upx;

	}
	.view2{
		font-size: 22upx;
		color: rgba(0,0,0,0.3);

	}
	.text1{
		position: relative;
		right: 65upx;
		top:20upx;
		transition: all .3s;
		transition-timing-function: linear;
	}
	.text2{
		position: relative;
		right: 67upx;
		bottom: 25upx;
		transition: all .3s;
		transition-timing-function: linear;
		
	}
</style>
