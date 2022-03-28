<template>
	<view >
		<map id="myMap" ref="map" style="width: 100%; height: 100vh;" :latitude="latitude" :longitude="longitude"
			:markers="covers" :setting="mapSetting" :circles="circles" :scale="scale" @markertap="markertap($event)">
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
				scale: 16,
				ordersCopy:[],
				url:'https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=',
				distance:[],
				markerSelected:-1,
			}
		},
		mounted() {
			this.mapContext = uni.createMapContext('myMap', this);
			this.openLocation();
		},
		methods: {
			MoveLocation(lat, lon) {
				let _this = this
				this.mapContext.moveToLocation({
					latitude: lat,
					longitude: lon,
					fail: e => {	//fail后500ms重新执行
						setTimeout(function() {
							_this.MoveLocation(lat, lon)
						}, 500)
					}
				});
			},
			getChargerLocation(lon, lat, tle) {
				wx.cloud.callFunction({
					name: 'getSurround',
					data: {
						longitude: lon,
						latitude: lat,
						Distance: 2000
					}
				}).then(
					res => {
						let chargerList = res.result.data;
						this.ordersCopy.splice(0);
						this.covers.splice(0, this.covers.length);
						this.markerSelected=-1;
						if (chargerList.length!=0) {
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
										content: charger.location,
										color: "#333333",
										fontSize: 13,
										borderRadius: 20,
										bgColor: "#e7ffed",
										textAlign: "center",
										padding: 10,
									}
								});
								var distance=(this.getFlatternDistance(lat,lon,charger.geoPoint.coordinates[1],charger.geoPoint.coordinates[0])/1000).toFixed(1);
								this.ordersCopy.push({        //为解决每次插入后界面都刷新使用了副本记录 最后一次性赋给store中的order
									location: charger.location,
									id: charger._id,
									latitude: charger.geoPoint.coordinates[1],
									longitude: charger.geoPoint.coordinates[0],
									price:charger.price,
									startTime:'08:00',
									endTime:'18:00',
									distance:distance,
									detail:false,
								});
							}
						}
						this.$store.commit('setOrders',this.ordersCopy);
						if (tle) {	//为了防止异步问题，所以放在这里
							this.covers.push({
								title: tle,
								id: 278,
								latitude: lat,
								longitude: lon,
								iconPath: "/static/image/landmarksolid.png",
								width: 50,
								height: 50,
								callout: {
									content: tle,
									color: "#333333",
									fontSize: 13,
									borderRadius: 20,
									bgColor: "#e7ffed",
									textAlign: "center",
									padding: 10,
								}
							});
							this.circles.splice(1,1,{
								latitude: lat,
								longitude: lon,
								fillColor: "#4162996A",
								color: "#b0daff",
								radius: 300,
								strokeWidth: 1,
							})
						}
					}
				)
			},
			openLocation() {
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
							this.$store.commit('setCurrentLocation',{
								latitude:this.latitude,
								longitude:this.longitude
							});
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
									.longitude) > 0.005)) { //两次定位距离过近时不调用云函数以减小负载
								this.getChargerLocation(this.longitude, this.latitude, null);
							}
							firstFlag = false;
						});
					},
					fail: (err) => {
						console.error("startLocationUpdate错误", err)
						reject(err)
					}
				})
			},
			getFlatternDistance(lat1,lng1,lat2,lng2){
				
				var EARTH_RADIUS = 6378137.0; //单位M
				var PI = Math.PI;


				var getRad=function(d){
					return d * PI / 180.0;
				}
				
			    var f = getRad((lat1 + lat2)/2);
			    var g = getRad((lat1 - lat2)/2);
			    var l = getRad((lng1 - lng2)/2);
			    
			    var sg = Math.sin(g);
			    var sl = Math.sin(l);
			    var sf = Math.sin(f);
			    
			    var s,c,w,r,d,h1,h2;
			    var a = EARTH_RADIUS;
			    var fl = 1/298.257;
			    
			    sg = sg*sg;
			    sl = sl*sl;
			    sf = sf*sf;
			    
			    s = sg*(1-sl) + (1-sf)*sl;
			    c = (1-sg)*(1-sl) + sf*sl;
			    
			    w = Math.atan(Math.sqrt(s/c));
			    r = Math.sqrt(s*c)/w;
			    d = 2*w*a;
			    h1 = (3*r -1)/2/c;
			    h2 = (3*r +1)/2/s;
			    
			    return d*(1 + fl*(h1*sf*(1-sg) - h2*(1-sf)*sg));
			},
			markertap(e){	//用于告知组件按下的marker编号
				var number;
				for(var index in this.covers){
					if(this.covers[index].id==e.detail.markerId){
						number=index;
					}
				}
				
				if(this.markerSelected==number){	//第二次点击再生效
					this.$store.commit('setMarkerSelected',number);
					this.MoveLocation(this.covers[number].latitude, this.covers[number].longitude);
				}
				this.markerSelected=number;
			}
		},
		watch: {
			'$store.state.destination'() {//监听destination变化 变化就在地图上加入标记点并且移动到该位置
				if(this.$store.state.destination!=null){//按下locationbutton重置回到自己位置，destination置为空
					this.$store.state.relocate=false;
					wx.stopLocationUpdate();
					var title = this.$store.state.destination.title;
					var latitude = this.$store.state.destination.location.lat;
					var longitude = this.$store.state.destination.location.lng;
					this.getChargerLocation(longitude, latitude, title);
					this.MoveLocation(latitude, longitude);
				};
			},
			'$store.state.relocate'(){
				if(this.$store.state.relocate==true)
					this.openLocation();
			},
			'$store.state.orderSelected'(){ //当选中一个order后在地图上移动到该标记点
				var latitude=this.covers[this.$store.state.orderSelected].latitude;
				var longitude=this.covers[this.$store.state.orderSelected].longitude;
				this.MoveLocation(latitude,longitude);
			},
			'$store.state.locationres'() {
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
				this.getChargerLocation(this.longitude, this.latitude, null);
				this.$store.commit('setLocationRes', null);
			}
		}
	}
</script>

<style scoped>
</style>
