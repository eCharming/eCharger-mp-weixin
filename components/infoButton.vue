<template>
	<view>
		<view class="info-view" :style="{'top':top+'px'}">
			<image src="@/static/image/person.png" mode="widthFix" style="width: 80px;" @click="info"></image>
		</view>
	</view>
</template>

<script>
	export default {
		name: "infoButton",
		data() {
			return {
	
			};
		},
		methods:{
			info() {
				//TODO
			}
		}
	}
</script>

<style scoped>
	.info-view {
		position: absolute;
		top: calc(-80px);
		left: calc(0%);
		transition-property:top;
		transition-duration: .5s;
	}
</style>
