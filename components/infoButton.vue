<template>
	<view>
		<view >
			<image :src="src" mode="widthFix" style="width: 80px;" @click="info"></image>
		</view>
	</view>
</template>

<script>
	export default {
		name: "infoButton",
		data() {
			return {
				src:"../static/image/person.png",
			};
		},
		methods:{
			info() {
				//TODO
			}
		},
		watch:{
			'$store.state.buttonSelected'(){
				if(this.$store.state.buttonSelected==1){
					this.src="../static/image/person.png";
				}	
				else{
					this.src="../static/image/person_blue.png";
				} 
			}
		}
	}
</script>

<style scoped>

</style>
