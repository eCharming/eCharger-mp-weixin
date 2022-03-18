<template>
	<view>
		<map id="myMap" style="width: 100%; height: 100vh;" :latitude="latitude" :longitude="longitude"
			:markers="covers" :setting="mapSetting" :circles="circles">
		</map>

		<babtouchbox maxHeight="0.7">
			<totalbutton :text="111"></totalbutton>
		</babtouchbox>

		<!-- <u-tabbar>
			<u-tabbar-item text="附近" :icon="nearicon"></u-tabbar-item>
			<u-tabbar-item text="租电桩" :icon="bicon"></u-tabbar-item>
			<u-tabbar-item text="我的" :icon="myicon"></u-tabbar-item>
		</u-tabbar> -->


	</view>
</template>

<script>
	import babtouchbox from '@/components/bab-Touchbox.vue'
	import totalbutton from '@/components/totalbutton.vue'
	import QQMapWX from '@/static/js/qqmap-wx-jssdk.min.js'
	export default {
		components: {
			babtouchbox,
			totalbutton
		},
		data() {
			return {
				latitude: 39.909,
				longitude: 116.39742,
				mapSetting: {
					"showCompass": true,
					"enableOverlooking": false,
					"enableSatellite": false,
					"enableTraffic": false,
					"enableZoom": true,
					"enableScroll": true,
					"enableRotate": true,
					"showLocation": true,
					"subkey": "HVTBZ-KOFW6-JDUSX-ESY54-6WWQK-LEF73"
				},
				covers: [],
				circles: []
			}
		},
		onLoad() {
			uni.getLocation({
				type: 'gcj02',
				success: res => {
					this.latitude = res.latitude;
					this.longitude = res.longitude;
					this.circles.push({
						latitude: this.latitude,
						longitude: this.longitude,
						fillColor: "#4162996A",
						color: "#b0daff",
						radius: 300,
						strokeWidth: 1,
					})
				}
			});
		},
		methods: {

		}
	}
</script>

<style>

</style>
