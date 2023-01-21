<template>
  <view>
    <map id="myMap" ref="map" style="width: 100%; height: 100vh;position: absolute;top: 0;" layer-style="1"
         :latitude="center_latitude" :longitude="center_longitude" :markers="covers" :setting="mapSetting"
         :circles="circles" :scale="scale" :polyline="polyline" @markertap="markertap($event)">
    </map>
  </view>
</template>

<script>
export default {
  data() {
    return {
      center_latitude: 39.909, //中心纬度
      center_longitude: 116.39742, //中心经度
      latitude: 39.909, //当前纬度
      longitude: 116.39742, //当前经度
      city_latitude: 39.909,//城市纬度，仅仅用于全城
      city_longitude: 116.39742,//城市经度，仅仅用于全城
      mapSetting: {
        "showCompass": true,
        "enableOverlooking": false,
        "enableSatellite": false,
        "enableTraffic": false,
        "enableZoom": true,
        "enableScroll": true,
        "enableRotate": true,
        "showLocation": true,
        "subkey": "ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL"
      },
      covers: [],
      polyline: [],
      circles: [],
      mapContext: null,
      scale: 16,
      ordersCopy: [],
      url: 'https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=',
      distance: [],
      markerSelected: -1,
      chargerList: [], //请求所获得的所有桩信息
    }
  },
  mounted() {
    setTimeout(() => {
      this.mapContext = uni.createMapContext('myMap', this);
      this.openLocation();
    }, 1500)

  },
  methods: {
    pickerHandler(lon, lat) {
      this.$store.commit('setIsNavigate', false);
      this.ordersCopy.splice(0);
      this.polyline.splice(0);
      this.markerSelected = -1;
      if (this.chargerList.length != 0) {
        var tempDate = new Date();
        var days = tempDate.getDay();
        if (days == 0) {
          days = 7;
        }
        var rawStartTime = this.$store.state.startTime.split(":");
        var rawEndTime = this.$store.state.endTime.split(":");
        var endTime = rawEndTime.length > 1 ? (Number(rawEndTime[0]) * 60 + Number(rawEndTime[1])) : -1;
        var startTime = rawStartTime.length > 1 ? (Number(rawStartTime[0]) * 60 + Number(rawStartTime[1])) : 1440;

        for (let charger of this.chargerList) {
          var timeStamp = charger.timeStamp;
          if (timeStamp[2 * days - 2] <= startTime && timeStamp[2 * days - 1] >= endTime) {
            this.covers.push({
              title: charger.address,
              id: charger._id,
              latitude: charger.geoPoint.coordinates[1],
              longitude: charger.geoPoint.coordinates[0],
              iconPath: "/static/image/charger.png",
              width: 40,
              height: 40,
              callout: {
                content: charger.address,
                color: "#333333",
                fontSize: 13,
                borderRadius: 20,
                bgColor: "#e7ffed",
                textAlign: "center",
                padding: 10,
              }
            });

            var distance = (charger.Distance / 1000).toFixed(1);
            this.ordersCopy.push({ //为解决每次插入后界面都刷新使用了副本记录 最后一次性赋给store中的order
              address: charger.address,
              location: charger.location,
              uid: charger.uid,
              id: charger._id,
              latitude: charger.geoPoint.coordinates[1],
              longitude: charger.geoPoint.coordinates[0],
              price: charger.price,
              time: charger.time,
              distance: distance,
              detail: false,
            });
          }
        }
      }
      this.$store.commit('setOrders', this.ordersCopy);
    },
    MoveLocation(lat, lon) {
      let _this = this
      this.mapContext.moveToLocation({
        latitude: lat,
        longitude: lon,
        fail: e => { //fail后500ms重新执行
          setTimeout(function () {
            _this.MoveLocation(lat, lon)
          }, 500)
        }
      });
    },
    getWholeCity(lon, lat) {
      this.$store.commit('setIsNavigate', false);
      this.center_latitude = lat
      this.center_longitude = lon
      this.scale = 0;
      this.$nextTick(() => {
        this.scale = 9;
      })
      wx.cloud.callFunction({
        name: 'wholeCity',
        data: {
          longitude: lon,
          latitude: lat,
        }
      }).then(res => {
        let chargerList = res.result;
        this.chargerList.splice(0);
        this.chargerList = res.result;

        if (this.$store.state.startTime == "" && this.$store.state.endTime == "") {
          this.ordersCopy.splice(0);
          this.polyline.splice(0);
          this.covers.splice(0);
          this.markerSelected = -1;
          if (chargerList.length != 0) {
            for (let charger of chargerList) {
              this.covers.push({
                title: charger.address,
                id: charger._id,
                latitude: charger.geoPoint.coordinates[1],
                longitude: charger.geoPoint.coordinates[0],
                iconPath: "/static/image/charger.png",
                width: 40,
                height: 40,
                callout: {
                  content: charger.address,
                  color: "#333333",
                  fontSize: 13,
                  borderRadius: 20,
                  bgColor: "#e7ffed",
                  textAlign: "center",
                  padding: 10,
                }
              });

              var distance = (charger.Distance / 1000).toFixed(1);
              this.ordersCopy.push({ //为解决每次插入后界面都刷新使用了副本记录 最后一次性赋给store中的order
                address: charger.address,
                location: charger.location,
                uid: charger.uid,
                id: charger._id,
                latitude: charger.geoPoint.coordinates[1],
                longitude: charger.geoPoint.coordinates[0],
                price: charger.price,
                time: charger.time,
                distance: distance,
                detail: false,
              });

            }
          }
          this.$store.commit('setOrders', this.ordersCopy);
        } else {
          this.covers.splice(0);
          this.pickerHandler(lon, lat)
        }
      })
      this.circles.splice(0, 1, {
        latitude: -1,
        longitude: -1,
        fillColor: "rgb(255, 255, 255)",
        color: "rgb(255, 255, 255)",
        radius: 1,
        strokeWidth: 1,
      })
    },
    getChargerLocation(lon, lat, tle) {
      this.$store.commit('setIsNavigate', false);
      wx.cloud.callFunction({
        name: 'getSurround',
        data: {
          longitude: lon,
          latitude: lat,
          Distance: 2000,
          rawLongitude: this.$store.state.currentLocation.longitude,
          rawLatitude: this.$store.state.currentLocation.latitude,
        }
      }).then(
          res => {
            let chargerList = res.result;
            this.chargerList.splice(0);
            this.chargerList = res.result;

            if (this.$store.state.startTime == "" && this.$store.state.endTime == "") {
              this.ordersCopy.splice(0);
              this.polyline.splice(0);
              this.covers.splice(0);
              this.markerSelected = -1;
              if (tle != null) { //为了防止异步问题，所以放在这里
                this.covers.push({
                  title: tle,
                  id: 0,
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
                this.circles.splice(0, 1, {
                  latitude: lat,
                  longitude: lon,
                  fillColor: "#4162996A",
                  color: "#b0daff",
                  radius: 300,
                  strokeWidth: 1,
                })
              }
              if (chargerList.length != 0) {
                for (let charger of chargerList) {
                  this.covers.push({
                    title: charger.address,
                    id: charger._id,
                    latitude: charger.geoPoint.coordinates[1],
                    longitude: charger.geoPoint.coordinates[0],
                    iconPath: "/static/image/charger.png",
                    width: 40,
                    height: 40,
                    callout: {
                      content: charger.address,
                      color: "#333333",
                      fontSize: 13,
                      borderRadius: 20,
                      bgColor: "#e7ffed",
                      textAlign: "center",
                      padding: 10,
                    }
                  });

                  var distance = (charger.Distance / 1000).toFixed(1);
                  this.ordersCopy.push({ //为解决每次插入后界面都刷新使用了副本记录 最后一次性赋给store中的order
                    address: charger.address,
                    location: charger.location,
                    uid: charger.uid,
                    id: charger._id,
                    latitude: charger.geoPoint.coordinates[1],
                    longitude: charger.geoPoint.coordinates[0],
                    price: charger.price,
                    time: charger.time,
                    distance: distance,
                    detail: false,
                  });

                }
              }
              this.$store.commit('setOrders', this.ordersCopy);
            } else {
              this.covers.splice(0);
              if (tle != null) { //为了防止异步问题，所以放在这里
                this.covers.push({
                  title: tle,
                  id: 0,
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
                this.circles.splice(0, 1, {
                  latitude: lat,
                  longitude: lon,
                  fillColor: "#4162996A",
                  color: "#b0daff",
                  radius: 300,
                  strokeWidth: 1,
                })
              }
              this.pickerHandler(lon, lat)
            }
          }
      )
    },
    openLocation() {
      wx.startLocationUpdate({
        success: (res) => {
          let oldlatitude = null;
          let oldlongitude = null;
          let firstFlag = true;
          wx.onLocationChange((res) => { //调用实时获取定位
            oldlatitude = this.latitude;
            oldlongitude = this.longitude;
            this.latitude = res.latitude;
            this.longitude = res.longitude;
            this.city_latitude = res.latitude;
            this.city_longitude = res.longitude;
            this.$store.commit('setCurrentLocation', {
              latitude: this.latitude,
              longitude: this.longitude
            });
            if (firstFlag) {
              this.center_latitude = this.latitude;
              this.center_longitude = this.longitude;
            }
            this.circles.splice(0, 1, {
              latitude: this.latitude,
              longitude: this.longitude,
              fillColor: "#4162996A",
              color: "#b0daff",
              radius: 300,
              strokeWidth: 1,
            })
            if (firstFlag || (oldlatitude && oldlongitude && Math.abs(
                oldlatitude - this.latitude) + Math.abs(oldlongitude - this
                .longitude) > 0.005)) { //两次定位距离过近时不调用云函数以减小负载
              this.$store.commit('setCurrentLocation', {
                latitude: this.latitude,
                longitude: this.longitude
              });
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
    markertap(e) { //用于告知组件按下的marker编号
      var number;
      for (var index in this.covers) {
        if (this.covers[index].id == e.detail.markerId) {
          number = index;
        }
      }
      if (this.covers[0].id == 0)
        number--;
      if (this.markerSelected == number) { //第二次点击再生效
        this.$store.commit('setMarkerSelected', number);
        this.MoveLocation(this.covers[number].latitude, this.covers[number].longitude);
      }
      this.markerSelected = number;
    },
    navigate(index) {
      var url = "https://apis.map.qq.com/ws/direction/v1/driving/?from=" +
          this.latitude + "," + this.longitude + "&to=" + this.covers[index].latitude + "," + this.covers[index]
              .longitude + "&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL";
      uni.request({
        url: url,
        success: (res) => {
          if (res.data.status == "0") {
            this.polyline.splice(0);
            this.polyline.push({
              points: [],
              width: 5,
              color: "#66CDAA"
            });
            var polyline = res.data.result.routes[0].polyline;
            this.polyline[0].points.push({
              latitude: polyline[0],
              longitude: polyline[1]
            })

            for (var i = 2; i < polyline.length; i++) {
              polyline[i] = polyline[i - 2] + polyline[i] / 1000000;
              if (i % 2 == 1) {
                this.polyline[0].points.push({
                  latitude: polyline[i - 1],
                  longitude: polyline[i]
                });

              }
            }
            ;

          }
          this.$store.commit('setIsNavigate', true)
        }
      });
      this.MoveLocation(this.covers[index].latitude, this.covers[index].longitude);
    }
  },
  watch: {
    '$store.state.refresh'() {	//仅仅用于更新电桩信息
      if (this.$store.state.destination != null) {
        var title = this.$store.state.destination.title;
        var latitude = this.$store.state.destination.location.lat;
        var longitude = this.$store.state.destination.location.lng;
        this.getChargerLocation(longitude, latitude, title);
      } else {
        this.getChargerLocation(this.longitude, this.latitude, null)
      }
    },
    '$store.state.destination'() { //监听destination变化 变化就在地图上加入标记点并且移动到该位置
      if (this.$store.state.destination != null) { //按下locationbutton重置回到自己位置，destination置为空
        this.$store.state.relocate = false;
        wx.stopLocationUpdate();
        var title = this.$store.state.destination.title;
        var latitude = this.$store.state.destination.location.lat;
        var longitude = this.$store.state.destination.location.lng;
        this.getChargerLocation(longitude, latitude, title);
        this.MoveLocation(latitude, longitude);
      }
      ;
    },
    '$store.state.relocate'() {
      if (this.$store.state.relocate == true)
        this.openLocation();
    },
    '$store.state.orderSelected'() { //当选中一个order后在地图上移动到该标记点
      if (this.$store.state.orderSelected != null) {
        var latitude = this.covers[this.$store.state.orderSelected].latitude;
        var longitude = this.covers[this.$store.state.orderSelected].longitude;
        this.MoveLocation(latitude, longitude);
        this.$nextTick(function () {
          this.$store.commit('setOrderSelected', null);
        })
      }

    },
    '$store.state.locationres'() {
      let res = this.$store.state.locationres;
      if (res && res != {} && res.errMsg == "getLocation:ok") {
        this.latitude = res.latitude;
        this.longitude = res.longitude;
        this.city_latitude = res.latitude;
        this.city_longitude = res.longitude;
        this.center_latitude = res.latitude;
        this.center_longitude = res.longitude;
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
      this.scale = 0;
      this.$nextTick(() => {
        this.scale = 16;
      })
    },
    '$store.state.navigateSelected'() {
      if (this.$store.state.navigateSelected != null) {
        this.navigate(this.$store.state.navigateSelected);
        this.$nextTick(function () {
          this.$store.commit('setNavigateSelected', null);
        })

      }
    },
    '$store.state.startTime'() {
      if (this.covers.length > 0 && this.covers[0].id == 1027368) {
        this.covers.splice(1);
      } else {
        this.covers.splice(0);
      }
      this.pickerHandler(null, null)
    },
    '$store.state.endTime'() {
      if (this.covers.length > 0 && this.covers[0].id == 1027368) {
        this.covers.splice(1);
      } else {
        this.covers.splice(0);
      }
      this.pickerHandler(null, null)
    },
    '$store.state.cityLocation': {
      deep: true, //必须深监听
      handler(res) {
        if (res.latitude != null && res.longitude != null) {
          wx.stopLocationUpdate();
          this.city_latitude = res.latitude
          this.city_longitude = res.longitude
          this.scale = 0;
          this.$nextTick(() => {
            this.scale = 16;
          })
          setTimeout(() => {
            if (this.mapContext) {
              this.mapContext.moveToLocation({
                latitude: res.latitude,
                longitude: res.longitude
              })
            }

            this.getChargerLocation(res.longitude, res.latitude, res.name);
          }, 500)
        }
      }
    },
    '$store.state.isWholeCity'() {
      this.getWholeCity(this.city_longitude, this.city_latitude)
    },
    '$store.state.refreshPolyline'() {
      this.polyline.splice(0);
    }

  }
}
</script>

<style scoped>
</style>
