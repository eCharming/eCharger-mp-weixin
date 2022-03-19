<template>
	<button 
		id="btn" 
		class="btn" 
		:plain="true"
		:style="{'border-color':bordercolor}"
	>
		<text class="text1">{{text1}}</text>
		<text class="text2">{{text2}}</text>
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
				type:Number,
				default:0
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

<style scoped lang="scss">
	.btn{
		height: 120upx;
		width : 270upx;
		border-radius: 10px;
		margin: 20upx;
		padding-left: 10upx;
		border: 3px solid;
		overflow: visible;
		// display: flex;
		// flex-direction: column;
		// justify-content: flex-start;
		// align-content:flex-start; /*把多余的行高放到两边*/
	}
	.text1{
		font-weight:700 ;
		// height:50upx
		font-size: 30upx;
		position: relative;
		bottom: 20upx;
		right:5upx
	
	}
	.text2{
		font-size: 25upx;
		color: rgba(0,0,0,0.3);
		position: relative;
		top: 20upx;
		right:90upx
		// height:40%

		
	}
</style>
