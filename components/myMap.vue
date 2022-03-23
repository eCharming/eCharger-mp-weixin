<template>
	<view>
		<map id="myMap" style="width: 100%; height: 100vh;" :latitude="latitude" :longitude="longitude"
			:markers="covers" :setting="mapSetting" :circles="circles" :scale="scale">
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
				covers: [],
				circles: [],
				mapContext: null,
				scale: 16
			}
		},
		mounted() {
			this.openLocation();
		},
		computed: {
			getRelocationRes() {
				let res = this.$store.state.locationres;
				if (res && res != {} && res.errMsg == "getLocation:ok") {
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
				if (this.mapContext) {
					this.mapContext.moveToLocation({
						latitude: this.latitude,
						longitude: this.longitude
					})
				}
				this.getChargerLocation(this.longitude, this.latitude);
				this.$store.commit('setLocationRes', null);
			}
		},
		methods: {
			getChargerLocation(lon, lat) {
				wx.cloud.callFunction({
					name: 'getSurround',
					data: {
						longitude: lon,
						latitude: lat,
						Distance: 2000
					}
				}).then(
					res => {
						let chargerList = res.result.data
						this.covers.splice(0, this.covers.length);
						if (chargerList) {
							for (let charger of chargerList) {
								this.covers.push({
									title: charger.location,
									id: charger._id,
									latitude: charger.geoPoint.coordinates[
										1],
									longitude: charger.geoPoint
										.coordinates[0],
									iconPath: "/static/image/charger.png",
									width: 40,
									height: 40,
									callout: {
										content: `${charger.location}`,
										color: "#333333",
										fontSize: 13,
										borderRadius: 20,
										bgColor: "#e7ffed",
										textAlign: "center",
										padding: 10,
									}
								});
							}
						}
					}
				)
			},
			openLocation() {
				this.mapContext = uni.createMapContext('myMap', this)
				wx.startLocationUpdate({
					success: (res) => {
						let oldlatitude = null;
						let oldlongtitude = null;
						let firstFlag = true;
						wx.onLocationChange((res) => { //调用实时获取定位
							oldlatitude = this.latitude;
							oldlongtitude = this.longitude;
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
							if (firstFlag || (oldlatitude && oldlongtitude && Math.abs(
									oldlatitude - this.latitude) + Math.abs(oldlongtitude - this
									.longtitude) > 0.005)) { //两次定位距离过近时不调用云函数以减小负载
								this.getChargerLocation(this.longitude, this.latitude);
							}
							/*
							ONLY FOR TESTING!!!
							// for (var i = 0; i < 20; i++) {
							// 	this.covers.splice(i,1,{
							// 		title: i,
							// 		id: i,
							// 		latitude: this.latitude + (Math.random() - 0.5) / 100,
							// 		longitude: this.longitude + (Math.random() - 0.5) / 100,
							// 		iconPath: "/static/image/charger.png",
							// 		width: 40,
							// 		height: 40
							// 	});
							// }
							*/
							firstFlag = false;
						});
					},
					fail: (err) => {
						console.error("startLocationUpdate错误", err)
						reject(err)
					}
				})
			}
		},
		watch:{
			'$store.state.destinationLocation'(){
				var title=this.$store.state.destination;
				var latitude=this.$store.state.destinationLocation.lat;
				var longitude=this.$store.state.destinationLocation.lng;
				this.getChargerLocation(longitude,latitude);
				this.covers.push({
					title: title,
					id: 278,
					latitude: latitude,
					longitude: longitude,
					iconPath:"/static/image/landmark.png",
					width: 40,
					height: 40,
					callout: {
						content: `${title}`,
						color: "#333333",
						fontSize: 13,
						borderRadius: 20,
						bgColor: "#e7ffed",
						textAlign: "center",
						padding: 10,
					}
				});
				this.mapContext.moveToLocation({
					latitude:latitude,
					longitude:longitude
				});
			}
		}
	}
</script>

<style scoped>
</style>
