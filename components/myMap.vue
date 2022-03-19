<template>
	<map id="myMap" style="width: 100%; height: 100vh;" :latitude="latitude" :longitude="longitude"
		:markers="covers" :setting="mapSetting" :circles="circles">
	</map>
</template>

<script>
	import QQMapWX from '@/static/js/qqmap-wx-jssdk.min.js'
	export default{
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
		mounted() {
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
	}
	
</script>

<style>
</style>
