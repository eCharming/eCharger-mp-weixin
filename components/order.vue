<template>
	<view class="card" 
		:style="{'border-left':borderleft,'border-right':borderright,'box-shadow':boxshadow}"
		@tap="emit"
		>
		<slot></slot>
	</view>
</template>

<script>
	export default{
		props:{
			index:{
				type:Number
			},
			orderSelected:{
				type:Number,
				default:-1
			}
		},
		data(){
			return{
				borderleft:"3px solid rgba(102,205,170,0.5)",
				borderright:"3px solid rgba(102,205,170,0.5)",
				boxshadow:"0px 61px 9px -60px rgba(102,205,170,0.5)",
				
			}
		},
		watch:{
			orderSelected(){
				if(this.orderSelected===this.index)
					this.tap();
				else this.untap();
			}
		},
		methods:{
			tap(){
				this.borderleft="5px solid rgba(102,205,170,1)";
				this.borderright="5px solid rgba(102,205,170,1)";
				// this.boxshadow="0px 12px 20px -10px rgba(102,205,170,1)";
				this.boxshadow="0px 13px 15px -10px rgba(102,205,170,0.5)";
			},
			untap(){
				this.borderleft="3px solid rgba(102,205,170,0.5)";
				this.borderright="3px solid rgba(102,205,170,0.5)";
				// this.boxshadow="0px 12px 20px -10px rgba(102,205,170,1)";
				this.boxshadow="0px 61px 9px -60px rgba(102,205,170,0.5)";
			},
			emit(){
				this.$emit('emit',this.index);
			}
		}
	}
</script>

<style scoped lang="scss">
	.card{
		margin:20upx;
		margin-bottom: 30upx;;
		padding: 15upx;
		background-color: rgba(253,255,253,1);
		// background-color: #FFFFFF;
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		// border-left:3px solid rgba(102,205,170,0.5);
		// border-right:3px solid rgba(102,205,170,0.5);
		// box-shadow:0px 61px 20px -60px rgba(102,205,170,0.5) ;
		transition-property: box-shadow,border;
		transition-duration: .5s;
	}
</style>

