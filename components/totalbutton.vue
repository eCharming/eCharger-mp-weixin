<template>
	<button 
		id="btn" 
		class="btn" 
		:plain="true"
		:style="{'border-color':bordercolor}"
	>
		<view class="view1">
			<text class="text1" :style="{'top':top+'px'}">{{text1}}</text>
		</view>
		<view class="view2">
			<text class="text2" :style="{'opacity':1-percent}">{{text2}}</text>
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
			}
		},
		props:{
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
			percent:{
				type:Number
			}
		},
		computed:{
			top(){
				return uni.upx2px(15*this.percent+5);
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
			}
			
		},
		methods:{
			activate(){
				this.bordercolor="rgba(102,205,170,1)";
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
		border-radius: 10px;
		margin: 20upx;
		padding-left: 10upx;
		border: 3px solid;
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
		// position: relative;
		// bottom: 13upx;
		// right:5upx

	}
	.view2{
		font-size: 22upx;
		color: rgba(0,0,0,0.3);
		// position: relative;
		// top: 30upx;
		// right:90upx
		// height:40%

	}
	.text1{
		position: relative;
		right: 65upx;
		top:20upx;
		transition-property: top;
		transition-duration: .1s;
		transition-timing-function: linear;
	}
	.text2{
		position: relative;
		right: 67upx;
		bottom: 25upx;
		transition-property: opacity;
		transition-duration: .1s;
		transition-timing-function: linear;
		
	}
</style>
