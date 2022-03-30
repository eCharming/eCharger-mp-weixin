<template>
	<view class="card">
		<scroll-view 
			scroll-y="true" 
			scroll-with-animation=true
			:scroll-top="scrollTop"
			:style="{'height':height+'px'}"
			@scrolltolower="emit"
		>
			<slot></slot>
		</scroll-view>
	</view>
</template>

<script>
	
	export default{
		data(){
			return{
				height:0,
			}
		},
		props:{
			scrollTop:{
				type:Number
			},
		},
		methods:{
			emit(){
				this.$emit('scrolltolower');
			}
		},
		mounted() {
			this.height=this.$store.state.windowHeight*0.9-uni.upx2px(590);
		}
	}
</script>

<style scoped>
	.card{
		margin:20upx;
		padding: 15upx;
		background-color: rgba(250,255,250,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		box-shadow:0px 6px 12px 4px #e5e5e5 ;
	}
</style>
