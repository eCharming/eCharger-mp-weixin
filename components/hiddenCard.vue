<template>
	<view class="card" :style="{'top':top+'px','opacity':opacity}">
		<text class="text1">{{text1}}</text>
		<text class="text2" :style="{'color':color}">{{text2}}</text>
	</view>
</template>

<script>
	export default{
		props:{
			percent:{
				type:Number
			},
		},
		data(){
			return{
				text1:'在附近搜索电桩',
				text2:'',
				color:'rgb(102,205,170)',
			}
		},
		
		computed:{
			top(){
				return uni.upx2px(-30-(1-this.percent)*100)
			},
			opacity(){
				return (1-this.percent)*(1-this.percent)*(1-this.percent)
			}
		},
		watch:{
			'$store.state.destination'() {
				if(this.$store.state.destination!=null){
					this.text1='距离目的地';
					if(this.$store.state.buttonSelected==1)
						this.color='rgb(102,205,170)';
					else this.color='rgb(55,135,230)';
					this.text2='车程约'+this.$store.state.destination.distance+'km';
				}else{ //按下locationbutton重置回到自己位置，destination置为空
					this.text1="在附近搜索电桩";
					this.text2='';
				}
			},
			'$store.state.buttonSelected'(){
				if(this.$store.state.buttonSelected==1){
					this.color = "rgb(102,205,170)";
				}	
				else this.color="rgb(55,135,230)";
			}
		}
	}
</script>

<style scoped>
	.card{
		position: absolute;
		margin:20upx;
		padding: 15upx;
		padding-top: 20upx;
		background-color: rgba(250,255,250,1);
		border-radius: 55upx;
		display: flex;
		justify-content: center;
		box-shadow:0px 7px 10px -3px #a6a6a6 ;
		width: 550upx;
		height: 90upx;
		top:-30upx;
		left:70upx;
		
		font-size: 32upx;
		letter-spacing: 2upx;
		font-weight: 700;
		transition-property: top;
		transition-duration: .1s;
		transition-property: opacity;
		transition-duration: .1s;
		
	}
	.text1{
		margin: 20upx;
		margin-top: 10upx;
		margin-right: 10upx;
		// border: 2px solid red;
	}
	.text2{
		// border: 2px solid red;
		margin: 20upx;
		margin-top: 10upx;
		margin-left: 10upx;
		transition: all .3s;
	}
</style>