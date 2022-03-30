<template>
	<view :style="{'background-color':isBinded?color:backColor}" style="transition: all .1s;">
		<view :style="{'height':statusHeight+'px'}"></view>
		<view style="height: 50px;display: flex;justify-content: center;align-items: flex-end;">
				<text style="color: #FFFFFF;margin-bottom: 15px;">易插</text>
			
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				statusHeight:0,
				backColor:'rgb(102,205,170)',
			}
		},
		props:{
			color:{
				type:String,
				default:'rgba(102,205,170,1)'
			},
			isBinded:{
				type:Boolean,
				default:false
			}
		},
		mounted() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight;

		},
		watch:{
			'$store.state.buttonSelected'(){
				if(this.isBinded==false){
					if(this.$store.state.buttonSelected==1){
						this.backColor = "rgb(102,205,170)";
					}	
					else this.backColor="rgb(55,135,230)";
				}
			}
		}
	}
</script>

<style>
</style>
