<template>
	<card :margintop="margintop" :marginbottom="marginbottom">
		<scroll-view 
			scroll-y="true" 
			refresher-enabled="true" 
			:style="{'height':height+'px'}"
		>
			<slot></slot>
		</scroll-view>
	</card>
</template>

<script>
	import card from './card.vue'
	export default{
		components:{
			card
		},
		props:{
			margintop:{
				type:Number,
				default:20
			},
			marginbottom:{
				type:Number,
				default:20
			}
		},
		data(){
			return{
				height:0,
			}
		},
		mounted() {
			this.height=uni.getSystemInfoSync().windowHeight*0.9-uni.upx2px(590);
		}
	}
</script>

<style>
</style>
