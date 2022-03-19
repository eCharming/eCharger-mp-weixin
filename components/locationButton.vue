<template>
	<view>
		<view class="gps-view" :style="{'top':top+'px'}">
			<image src="@/static/image/location.png" mode="widthFix" style="width: 80px;" @click="relocate"></image>
		</view>
	</view>
</template>

<script>
	export default {
		name: "locationButton",
		data() {
			return {

			};
		},
		props:{
			top:{
				type:Number,
				default:-80
			}
		},
		methods:{
			relocate() {
				uni.getLocation({
					type: "gcj02",
					success: res => {
						this.$store.commit('setLocationRes',res);
					}
				});
			}
		}
	}
</script>

<style scoped>
	.gps-view {
		position: absolute;
		top: calc(-80px);
		left: calc(80%);
		transition-property:top;
		transition-duration: .5s;
	}
</style>
