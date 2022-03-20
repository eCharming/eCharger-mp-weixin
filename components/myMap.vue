<template>
	<view>
		<map id="myMap" style="width: 100%; height: 100vh;" :latitude="latitude" :longitude="longitude"
			:markers="covers" :setting="mapSetting" :circles="circles">
		</map>
	</view>
</template>

<script>
	import QQMapWX from '@/static/js/qqmap-wx-jssdk.min.js'
	export default {
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
				covers: [
				],
				circles: []
			}
		},
		mounted() {
			this.openLocation();
		},
		computed: {
			getRelocationRes() {
				let res = this.$store.state.locationres;
				if (res) {
					this.latitude = res.latitude;
					this.longitude = res.longitude;
					this.circles.splice(0, 1, {
						latitude: this.latitude,
						longitude: this.longitude,
						fillColor: "#4162996A",
						color: "#b0daff",
						radius: 300,
						strokeWidth: 1,
					})
				}
				this.$store.commit('setLocationRes',null);
			}
		},
		methods: {
			openLocation() {
				wx.startLocationUpdate({
					success: (res) => {
						wx.onLocationChange((res) => { //调用实时获取定位
							this.latitude = res.latitude;
							this.longitude = res.longitude;
							this.circles.splice(0, 1, {
								latitude: this.latitude,
								longitude: this.longitude,
								fillColor: "#4162996A",
								color: "#b0daff",
								radius: 300,
								strokeWidth: 1,
							})
							/*
							ONLY FOR TESTING!!!
							*/
						   for (var i = 0; i < 20; i++) {
						   	this.covers.splice(i,1,{
						   		title: i,
						   		id: i,
						   		latitude: this.latitude + (Math.random() - 0.5) / 100,
						   		longitude: this.longitude + (Math.random() - 0.5) / 100,
						   		iconPath: "/static/image/charger.png",
						   		width: 40,
						   		height: 40
						   	});
						   }
						   /*
						   ONLY FOR TESTING!!!
						   */
						});
					},
					fail: (err) => {
						console.error("startLocationUpdate错误", err)
						reject(err)
					}
				})
			}
		}
	}
</script>

<style scoped>
</style>
