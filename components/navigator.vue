<template>
  <view style="position: relative;">
    <view :style="{'height':statusHeight+'px','background-color':backColor,'transition': 'all .3s'}"></view>
    <view class="search-box" :style="{'background-color':backColor,'pointer-events':pointerEvents}">
      <view class="position" @click="tap(4)">
        <view style="display: flex;justify-content: space-between;align-items: center;">
          <text style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">{{ position }}</text>
          <image src="../static/image/select.png" style='width:20upx;height:20upx;flex-shrink:0;'></image>
        </view>
        <view style="font-weight: 500;font-size: 20upx;">
          <text>{{ weather }}</text>
        </view>

      </view>
      <view class="search-view" :style="{'width':width+'px'}" @tap="navigate()">
        <text class="search-text">易插</text>
      </view>
    </view>

    <view class="bottom-border" :style="{'bottom':lastBottom+'rpx','background-color':secondColor}"></view>

    <view class="image-box" :style="{'bottom':bottom+'rpx','background-color':backColor}">
      <view class="selected-box" :style="{'left':left+'rpx','background-color':secondColor}">
        <view class="fill-left" :style="{'background-color':secondColor}"></view>
        <view class="ball-left" :style="{'background-color':backColor}"></view>
        <view class="fill-right" :style="{'background-color':secondColor}"></view>
        <view class="ball-right" :style="{'background-color':backColor}"></view>
        <view class="selected-ball" :style="{'background-color':backColor}"></view>
      </view>
      <view class="image-1" @click="tap(0)" :style="{'bottom':imageBottom[0]+'rpx'}">

        <view
            style="position: absolute;height: 15upx;left: 25upx;top: 22upx;transition: all .3s;background-color: white;"
            :style="{'width':length1+'rpx'}"></view>
        <view
            style="position: absolute;height: 17upx;left: 25upx;top: 37upx;transition: all .6s;background-color: white;"
            :style="{'width':length1+'rpx'}"></view>
        <image class="image" :src="imgSrc[0]"></image>


      </view>
      <view class="image-2" @click="tap(1)" :style="{'bottom':imageBottom[1]+'rpx'}">
        <view
            style="position: absolute;height: 10upx;left: 25upx;top: 17upx;transition: all .3s;background-color: white;"
            :style="{'width':length2+'rpx'}"></view>
        <view
            style="position: absolute;height: 20upx;left: 25upx;top: 27upx;transition: all .6s;background-color: white;"
            :style="{'width':length2+'rpx'}"></view>

        <image class="image" :src="imgSrc[1]"></image>
      </view>
      <view class="image-3" @click="tap(2)" :style="{'bottom':imageBottom[2]+'rpx'}">
        <image class="image" src="../static/image/mark.png" :style="{'top':markTop+'rpx'}"></image>
        <image class="image" :src="imgSrc[2]"></image>
      </view>
      <view class="image-4" @click="tap(3)" :style="{'bottom':imageBottom[3]+'rpx'}">

        <image class="image" :src="imgSrc[3]" :style="{'opacity':opacity}"></image>
        <image class="image" src="../static/image/eye.png"
               :style="{'opacity':opacity,'left':eyeLeft+'rpx','top':eyeTop+'rpx'}"></image>
      </view>
    </view>


    <view class="text-box" :style="{'bottom':textBottom+'rpx','background-color':backColor}">
      <view :style="{'opacity':opacity}" style="transition: all .5s;">
        <text class="text-1" :style="{'bottom':word[0].bottom+'rpx','opacity':word[0].opacity}">订单</text>
        <text class="text-2" :style="{'bottom':word[1].bottom+'rpx','opacity':word[1].opacity}">联系</text>
        <text class="text-3" :style="{'bottom':word[2].bottom+'rpx','opacity':word[2].opacity}">全城</text>
        <text class="text-4" :style="{'bottom':word[3].bottom+'rpx','opacity':word[3].opacity}">我的</text>
      </view>

    </view>

  </view>
</template>

<script>
export default {
  data() {
    return {
      position: '未定位',
      weather: '',
      statusHeight: 0,
      width: 0,
      backColor: 'rgb(102,205,170)',
      secondColor: 'rgb(166,236,146)',
      bottom: -120,
      lastBottom: -150,
      textBottom: -30,
      imageBottom: [-48, 5, 5, 5],
      word: [{
        bottom: -10,
        opacity: 1,
      }, {
        bottom: 35,
        opacity: 0,
      }, {
        bottom: 35,
        opacity: 0,
      }, {
        bottom: 35,
        opacity: 0,
      },],
      opacity: 1,
      selected: 0,
      left: 25,
      pointerEvents: 'auto',
      length1: 30,
      length2: 0,
      markTop: -15,
      eyeLeft: 0,
      eyeTop: -8,
      imgSrc: ["../static/image/order-green.png", "../static/image/connect-green.png", "../static/image/map-green.png", "../static/image/face-green.png"]
    }
  },
  props: {
    isLow: {
      type: Boolean,
    },
    buttonSelected: {
      type: Number,
    }
  },
  methods: {
    info() {
      if (!this.$store.state.logInStatus) {
        wx.getUserProfile({
          desc: '获取微信头像以及昵称',
          success: (res) => {
            this.$store.commit('setUserName', res.userInfo.nickName,);
            this.$store.commit('setAvatarUrl', res.userInfo.avatarUrl);
            this.$store.commit('setLogInStatus', true);
            wx.cloud.callFunction({ //uid获取
              name: 'updateUrl',
              data: {
                userName: res.userInfo.nickName,
                avatarUrl: res.userInfo.avatarUrl,
              }
            })
          }
        })
        return false;
      } else return true;
    },
    navigate() {
      uni.navigateTo({
        url: '../search/search',
      });
    },
    tap(index) {
      if (index == 4) {
        uni.navigateTo({
          url: '../selectCity/selectCity',
        });
      }
      if (index == this.selected) {
        if (index == 0) {
          if (this.info()) {
            uni.navigateTo({
              // url: '../selectCity/selectCity',
              url: '../orders/ordersHistory',
            });
          }
        } else if (index == 1) {
          if (this.info()) {
            uni.navigateTo({
              url: '../communication/friends',
            });
          }
        } else if (index == 2) {
          this.$store.commit('setIsWholeCity')
        } else if (index == 3) {
          if (this.info()) {
            uni.navigateTo({
              url: '../my/my',
            });
          }
        }
      } else {
        if (index == 0) {
          if (this.selected == 1) {
            this.length2 = 0;
          } else if (this.selected == 3) {
            this.eyeLeft = 0;
            this.eyeTop = -8;
          } else if (this.selected == 2) {
            this.markTop = -15
          }

          this.length1 = 30;


          this.left = 25;
          this.word[0].bottom = -10;
          this.word[0].opacity = 1;
          this.word[this.selected].bottom = 35;
          this.word[this.selected].opacity = 0;
          this.imageBottom[0] = -48;
          this.imageBottom[this.selected] = 5;
          this.selected = 0;
        } else if (index == 1) {
          if (this.selected == 0) {
            this.length1 = 0;
          } else if (this.selected == 3) {
            this.eyeLeft = 0;
            this.eyeTop = -8;
          } else if (this.selected == 2) {
            this.markTop = -15
          }
          this.length2 = 30;
          this.left = 210;
          this.word[1].bottom = -10;
          this.word[1].opacity = 1;
          this.word[this.selected].bottom = 35;
          this.word[this.selected].opacity = 0;
          this.imageBottom[1] = -48;
          this.imageBottom[this.selected] = 5;
          this.selected = 1;
        } else if (index == 2) {
          if (this.selected == 0) {
            this.length1 = 0;
          } else if (this.selected == 1) {
            this.length2 = 0;
          } else if (this.selected == 3) {
            this.eyeLeft = 0;
            this.eyeTop = -8;
          }
          this.markTop = -8;
          this.left = 400;
          this.word[2].bottom = -10;
          this.word[2].opacity = 1;
          this.word[this.selected].bottom = 35;
          this.word[this.selected].opacity = 0;
          this.imageBottom[2] = -48;
          this.imageBottom[this.selected] = 5;
          this.selected = 2;
        } else if (index == 3) {
          if (this.selected == 0) {
            this.length1 = 0;
          } else if (this.selected == 1) {
            this.length2 = 0;
          } else if (this.selected == 2) {
            this.markTop = -15
          }

          this.eyeLeft = 7;
          this.eyeTop = 0;
          this.left = 590;
          this.word[3].bottom = -10;
          this.word[3].opacity = 1;
          this.word[this.selected].bottom = 35;
          this.word[this.selected].opacity = 0;
          this.imageBottom[3] = -48;
          this.imageBottom[this.selected] = 5;
          this.selected = 3;
        }
      }
    },
    updateInfo(res) {
      uni.request({
        url: 'https://apis.map.qq.com/ws/geocoder/v1/?location=' + res.latitude + ',' + res.longitude + '&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL',
        success: (res) => {
          let position = res.data.result.address_component.city;
          if (position.endsWith("市") || position.endsWith("盟")) {
            position = position.substring(0, position.length - 1)
          } else if (position.endsWith("地区")) {
            position = position.substring(0, position.length - 2)
          } else if (position.endsWith("自治州")) {
            if (position == "西双版纳傣族自治州" || position == "博尔塔拉蒙古自治州" || position == "巴音郭楞蒙古自治州" || position == "克孜勒苏柯尔克孜自治州") {
              position = position.substring(0, 4)
            } else {
              position = position.substring(0, 2)
            }
          }
          this.position = position
          this.$store.commit("setCity", this.position)
        }
      })
      uni.request({
        url: 'https://devapi.qweather.com/v7/weather/now?location=' + res.longitude.toFixed(2) + ',' + res.latitude.toFixed(2) + '&key=c999b86fbd1d4b52aced1189c2ffef63',
        success: (res) => {
          if (res.data.now) {
            this.weather = res.data.now.text + ' ' + res.data.now.temp + '℃'
          }
        }
      })
    }
  },
  mounted() {
    this.statusHeight = uni.getSystemInfoSync().statusBarHeight;
    this.width = uni.getMenuButtonBoundingClientRect().left - 80;

    uni.getLocation({
      type: "gcj02",
      success: res => {
        this.updateInfo(res);

      },
    });

  },
  watch: {
    '$store.state.buttonSelected'() {
      if (this.$store.state.buttonSelected == 1) {
        this.backColor = "rgb(102,205,170)";
        this.secondColor = 'rgb(166,236,146)';
        this.imgSrc = ["../static/image/order-green.png", "../static/image/connect-green.png", "../static/image/map-green.png", "../static/image/face-green.png"];
      } else {
        this.backColor = this.$store.state.color;
        this.secondColor = 'rgb(152,245,255)';
        this.imgSrc = ["../static/image/order-blue.png", "../static/image/connect-blue.png", "../static/image/map-blue.png", "../static/image/face-blue.png"];
      }
    },
    'isLow'() {
      if (this.isLow) {
        this.bottom = -120;
        this.textBottom = -30;
        this.lastBottom = -150;
        this.opacity = 1;
        this.pointerEvents = 'auto';
      } else {
        this.bottom = 0;
        this.textBottom = 90;
        this.lastBottom = -30;
        this.opacity = 0;
        this.pointerEvents = 'none';
        if (this.selected == 3) {
          this.tap(2);
        }
      }
      ;
    },
    '$store.state.locationres'() {
      let res = this.$store.state.locationres;
      if (res && res != {} && res.errMsg == "getLocation:ok") {
        this.updateInfo(res);
      }
    },
    '$store.state.cityLocation': {
      deep: true,
      handler(res) {
        if (res.latitude != null && res.longitude != null) {
          this.position = res.name
          this.$store.commit("setCity", this.position)
          uni.request({
            url: 'https://devapi.qweather.com/v7/weather/now?location=' + res.longitude.toFixed(2) + ',' + res.latitude.toFixed(2) + '&key=c999b86fbd1d4b52aced1189c2ffef63',
            success: (res) => {
              if (res.data.now) {
                this.weather = res.data.now.text + ' ' + res.data.now.temp + '℃'
              }
            }
          })
        }
      }
    }
  }
}
</script>

<style scoped>
.search-box {
  height: 90upx;
  display: flex;
  align-items: center;
  position: relative;
  transition: background-color .3s;
}

.position {
  color: #FFFFFF;
  font-weight: 700;
  margin-left: 15upx;
  margin-top: 35upx;
  margin-bottom: 15upx;
  width: 100upx;
  height: 80upx;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.search-view {
  color: rgba(0, 0, 0, 0.6);
  margin: 15upx;
  height: 60upx;
  background-color: #FFFFFF;
  border-radius: 20upx;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.search-text {
  margin-left: 20upx;
  font-weight: 700;
  font-size: 32upx;
  color: rgba(0, 0, 0, 0.5);
}

.bottom-border {
  position: absolute;
  width: 100%;
  height: 32upx;
  border-bottom-left-radius: 20upx;
  border-bottom-right-radius: 25upx;
  transition: all .3s;
}

.image-box {
  position: absolute;
  height: 92upx;
  width: 100%;
  transition: all .3s;
}

.selected-box {
  position: absolute;
  height: 70upx;
  width: 140upx;
  bottom: 0;
  border-top-left-radius: 70upx;
  border-top-right-radius: 70upx;
  transition-property: left, background-color;
  transition-duration: .5s, .3s;
}

.fill-left {
  position: absolute;
  width: 40upx;
  height: 24upx;
  bottom: -2upx;
  left: -35upx;
  transition: all .3s;
}

.fill-right {
  position: absolute;
  width: 40upx;
  height: 24upx;
  bottom: -2upx;
  right: -35upx;
  transition: all .3s;
}

.ball-left {
  position: absolute;
  width: 70upx;
  height: 70upx;
  bottom: 0;
  left: -64upx;
  border-radius: 50%;
  transition: all .3s;
}

.ball-right {
  position: absolute;
  width: 70upx;
  height: 70upx;
  bottom: 0;
  right: -64upx;
  border-radius: 50%;
  transition: all .3s;
}

.selected-ball {
  position: absolute;
  height: 110upx;
  width: 110upx;
  left: 16upx;
  bottom: -55upx;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.image-1 {
  width: 80upx;
  height: 80upx;
  position: absolute;
  left: 57upx;
  transition: all .5s;
}

.image-2 {
  width: 80upx;
  height: 80upx;
  position: absolute;
  left: 242upx;
  transition: all .5s;
}

.image-3 {
  width: 80upx;
  height: 80upx;
  position: absolute;
  left: 430upx;
  transition: all .5s;
}

.image-4 {
  width: 80upx;
  height: 80upx;
  position: absolute;
  left: 621upx;
  transition: all .5s;
}

.text-box {
  position: absolute;
  width: 100%;
  height: 30upx;
  transition: all .3s;
}

.text-1 {
  position: absolute;
  text-align: center;
  left: 65upx;
  color: #FFFFFF;
  font-size: 30upx;
  font-weight: 700;
  transition: all .5s;
}

.text-2 {
  position: absolute;
  text-align: center;
  left: 250upx;
  color: #FFFFFF;
  font-size: 30upx;
  font-weight: 700;
  transition: all .5s;
}

.text-3 {
  position: absolute;
  text-align: center;
  left: 440upx;
  color: #FFFFFF;
  font-size: 30upx;
  font-weight: 700;
  transition: all .5s;
}

.text-4 {
  position: absolute;
  text-align: center;
  left: 630upx;
  color: #FFFFFF;
  font-size: 30upx;
  font-weight: 700;
  transition: all .5s;
}

.image {
  position: absolute;
  width: 80upx;
  height: 80upx;
  bottom: 8upx;
  transition: all .3s;
  left: 0;
}
</style>
