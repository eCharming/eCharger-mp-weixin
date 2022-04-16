<template>
	<view>
		<view>
			<image :src="src" mode="widthFix" style="width: 80px;" @click="relocate"></image>
		</view>
	</view>
</template>

<script>
	export default {
		name: "locationButton",
		data() {
			return {
				src:"../static/image/location.png"
			};
		},
		props:{
			isLow:{
				type:Boolean,
			},
		},
		methods:{
			relocate() {
				uni.getLocation({
					type: "gcj02",
					success: res => {
						this.$store.commit('setLocationRes',res);
						this.$store.commit('setDestination',null);
					},
					fail: () => {
						this.$store.commit('setLocationRes',{});
					},
				});
				this.$store.state.relocate=true;
			}
		},
		watch:{
			'$store.state.buttonSelected'(){
				if(this.$store.state.buttonSelected==1){
					this.src="../static/image/location.png";
				}	
				else{
					this.src="../static/image/location_blue.png";
				} 
			},
			'isLow'(){
				if(this.isLow){
					this.pointerEvents='auto';
				}else{
					this.pointerEvents='none';
				} 
			}
		}
	}
</script>

<style scoped>
</style>
