<template>
  <view>
    <view class="searchNavi" :style="{'height':statusHeight+'px','background':doubleColor}">
      <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
             @tap="back"></image>
      <text :style="{'margin-bottom':searchHeight+'px'}">搜索</text>
    </view>
    <view class="searchbox">
      <view class="textareaview">
        <input class="textarea" placeholder="输入目的地" v-model="position" @input="request()"
               :style="{'border-color':color}">
        </input>
        <text class="searchtext" :style="{'color':color}">搜索</text>
        <image :src="src1" :class="changeImg==0?'image1':'image1_none'"></image>
        <image :src="src2" :class="changeImg==0?'image1_none':'image1'"></image>
      </view>

      <view class="storageview" v-if="!isInput">
        <view class="history">
          <view>
            <view style="display: flex;">
              <view class="historyview">
                <text>历史记录</text>
              </view>
              <view class="commonview">

                <text>常用地点</text>
              </view>
            </view>
            <view style="margin-left: 30upx;width: 290upx;height: 17upx;display: flex;"
                  :style="{'justify-content':justifyContent}">
              <view class="modelSelected"
                    :style="{'height':modelHeight+'rpx','width':modelWidth+'rpx','background-color':color}">
              </view>
            </view>
          </view>

          <view style="display: flex;position: relative;transition: all .1s;"
                :style="{'left':buttonLeft+'rpx'}">
            <view class="clear" :style="{'opacity':buttonOpacity1}" @tap="clear">清空历史记录</view>
            <view class="add" :style="{'opacity':buttonOpacity2 }" @tap="addFrePlace">添加常用地点</view>
          </view>

        </view>

        <swiper :style="{'height': (storageHeight+20)+'px'}" @animationfinish="animationfinish($event)"
                @transition="transition($event)" @change="change($event)">
          <swiper-item>
            <scroll-view scroll-y="true"
                         style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
                         :style="{'height': storageHeight+'px'}">
              <view
                  style="width:100%;height:100%;display: flex;align-items: center;justify-content: center;flex-direction: column;"
                  v-if='storages.length===0'>
                <image src="/static/image/blank.png" style="width: 200rpx;height: 200rpx;"></image>
                <text style="font-weight: bold;color: rgba(102,205,170,1);">还没有搜索记录</text>
              </view>
              <view v-for="(storage,index) in storages" :key="index"
                    style="display: flex;flex-direction: column;">
                <view class="storage" @tap="tapStorage(storage.title,storage.location)">

                  <view class="view5">
                    <image src="../../static/image/search.png" class="image3"></image>
                    <text class="text3">{{ storage.title }}</text>
                  </view>

                  <view class="view4" @tap.stop.prevent="del(index)">
                    <icon type="cancel" color="rgba(102,205,170,1)" style="margin-top: 5upx;">
                    </icon>
                  </view>
                </view>
                <view class="view6">
                  <text class="text6">{{ storage.category }}</text>
                </view>
                <view style="display: flex;justify-content: center;margin-top: 20upx;">
                  <view style="border-bottom: 2upx solid rgba(0,0,0,0.1);width: 500upx;"></view>
                </view>

              </view>


            </scroll-view>
          </swiper-item>
          <swiper-item>
            <view style="display: flex;justify-content: space-between;margin: 30upx;">
              <view style="width: 230upx;display: flex;justify-content:center; align-items:center;"
                    @tap="tapStorageIcon(0)">
                <image src="../../static/image/home.png"
                       style="height:40upx;width:40upx;margin-right:20upx;"></image>
                回家
              </view>
              <view style="width: 230upx;display: flex;justify-content: center;
							border-left:2upx solid rgba(0,0,0,0.1);border-right:2upx solid rgba(0,0,0,0.1);align-items:center;"
                    @tap="tapStorageIcon(1)">
                <image src="../../static/image/work.png"
                       style="height:40upx;width:40upx;margin-right:20upx;"></image>
                公司
              </view>
              <view style="width: 230upx;display: flex;justify-content: center;align-items:center;"
                    @tap="tapStorageIcon(2)">
                <image src="../../static/image/school.png"
                       style="height:40upx;width:40upx;margin-right:20upx;"></image>
                学校
              </view>
            </view>
            <scroll-view scroll-y="true"
                         style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
                         :style="{'height': (storageHeight-40)+'px'}">
              <view
                  style="width:100%;height:100%;display: flex;align-items: center;justify-content: center;flex-direction: column;"
                  v-if="
							JSON.stringify(frePlace.freHome) == '{}' && JSON.stringify(frePlace.freCompany) == '{}' && JSON.stringify(frePlace.freSchool) == '{}' && frePlace.freOther.length==0">
                <image src="/static/image/blank_blue.png" style="width: 200rpx;height: 200rpx;"></image>
                <text style="font-weight: bold;color: rgba(50,200,210,1);">还没有常用地点</text>
              </view>
              <view style="display: flex;flex-direction: column;" v-if="JSON.stringify(frePlace.freHome) != '{}'">
                <view class="storage" @tap="tapStorage(frePlace.freHome.title,frePlace.freHome.location)">

                  <view class="view5">
                    <image src="../../static/image/search_blue.png" class="image3"></image>
                    <text class="text3">{{ frePlace.freHome.title }}</text>
                  </view>

                  <view class="view4" @tap.stop.prevent="delFreIcon(0)">
                    <icon type="cancel" color="rgb(50,200,210,1)" style="margin-top: 5upx;"></icon>
                  </view>
                </view>
                <view class="view6">
                  <text class="text6_blue">{{ frePlace.freHome.category }}</text>
                  <text class="text6_blue_2">家</text>
                </view>
                <view style="display: flex;justify-content: center;margin-top: 20upx;">
                  <view style="border-bottom: 2upx solid rgba(0,0,0,0.1);width: 500upx;"></view>
                </view>
              </view>

              <view style="display: flex;flex-direction: column;" v-if="JSON.stringify(frePlace.freCompany) != '{}'">
                <view class="storage" @tap="tapStorage(frePlace.freCompany.title,frePlace.freCompany.location)">

                  <view class="view5">
                    <image src="../../static/image/search_blue.png" class="image3"></image>
                    <text class="text3">{{ frePlace.freCompany.title }}</text>
                  </view>

                  <view class="view4" @tap.stop.prevent="delFreIcon(1)">
                    <icon type="cancel" color="rgb(50,200,210,1)" style="margin-top: 5upx;"></icon>
                  </view>
                </view>
                <view class="view6">
                  <text class="text6_blue">{{ frePlace.freCompany.category }}</text>
                  <text class="text6_blue_2">公司</text>
                </view>
                <view style="display: flex;justify-content: center;margin-top: 20upx;">
                  <view style="border-bottom: 2upx solid rgba(0,0,0,0.1);width: 500upx;"></view>
                </view>
              </view>

              <view style="display: flex;flex-direction: column;" v-if="JSON.stringify(frePlace.freSchool) != '{}'">
                <view class="storage" @tap="tapStorage(frePlace.freSchool.title,frePlace.freSchool.location)">

                  <view class="view5">
                    <image src="../../static/image/search_blue.png" class="image3"></image>
                    <text class="text3">{{ frePlace.freSchool.title }}</text>
                  </view>

                  <view class="view4" @tap.stop.prevent="delFreIcon(2)">
                    <icon type="cancel" color="rgb(50,200,210,1)" style="margin-top: 5upx;"></icon>
                  </view>
                </view>
                <view class="view6">
                  <text class="text6_blue">{{ frePlace.freSchool.category }}</text>
                  <text class="text6_blue_2">学校</text>
                </view>
                <view style="display: flex;justify-content: center;margin-top: 20upx;">
                  <view style="border-bottom: 2upx solid rgba(0,0,0,0.1);width: 500upx;"></view>
                </view>
              </view>

              <view v-for="(storage,index) in frePlace.freOther" :key="index"
                    style="display: flex;flex-direction: column;">
                <view class="storage" @tap="tapStorage(storage.title,storage.location)">

                  <view class="view5">
                    <image src="../../static/image/search_blue.png" class="image3"></image>
                    <text class="text3">{{ storage.title }}</text>
                  </view>

                  <view class="view4" @tap.stop.prevent="delFre(index)">
                    <icon type="cancel" color="rgb(50,200,210,1)" style="margin-top: 5upx;"></icon>
                  </view>
                </view>
                <view class="view6">
                  <text class="text6_blue">{{ storage.category }}</text>
                  <text class="text6_blue_2">其他</text>
                </view>
                <view style="display: flex;justify-content: center;margin-top: 20upx;">
                  <view style="border-bottom: 2upx solid rgba(0,0,0,0.1);width: 500upx;"></view>
                </view>

              </view>
            </scroll-view>
          </swiper-item>
        </swiper>


      </view>

      <scroll-view v-if="isInput" scroll-y="true"
                   style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
                   :style="{'height': suggestionHeight+'px'}">
        <view class="suggestion" v-for="(suggestion,index) in suggestions" :key="index"
              @tap="tap(suggestion.id,suggestion.title,suggestion.location,suggestion.category)">
          <view style="display: flex;justify-content: space-between;">
            <view class="view1">
              <image class="image2" src="../../static/image/landmark.png"></image>
              <rich-text :nodes="suggestion.strings" class="richtext"></rich-text>
            </view>
            <view
                style="position: absolute;right: 40upx;top: 5upx;display: flex;flex-direction: column;justify-content: center;">
              <image src='../../static/image/distance.png' style="width: 80upx;height: 80upx;"></image>
              <text
                  style="font-size: 23upx;color: rgb(0,0,0,0.6);margin-top: 5upx;">{{ suggestion.distance }}km
              </text>
            </view>
          </view>

          <view class="view2">
            <text class="text2">{{ suggestion.category }}</text>
          </view>
          <view class="view3">
            <text style="font-size: 23upx;color: rgb(0,0,0,0.6);">{{ suggestion.address }}</text>
          </view>
          <view style="display: flex;justify-content: center;">
            <view style="border-bottom: 1px solid rgba(0,0,0,0.1);width: 500upx;"></view>
          </view>
        </view>
      </scroll-view>


    </view>
  </view>

</template>

<script>
import navigator from '../../components/navigator.vue'

export default {
  components: {
    navigator
  },
  data() {
    return {
      statusHeight: uni.getSystemInfoSync().statusBarHeight + 50,
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
      searchHeight: 0,
      position: "",
      suggestions: [],
      storageHeight: this.$store.state.windowHeight * 0.8,
      suggestionHeight: this.$store.state.windowHeight * 0.85,
      index: 0,
      storages: [],
      frePlace: {},
      isInput: false,
      modelWidth: 120,
      modelHeight: 15,
      windowWidth: uni.getSystemInfoSync().windowWidth,
      justifyContent: "flex-start",
      currentPage: 0,
      changeImg: 0,
      buttonLeft: 185,
      buttonOpacity1: 1,
      buttonOpacity2: 0,
      color: 'rgba(102,205,170,1)',
      doubleColor: "linear-gradient(to right bottom,rgb(102,205,170) 0% 100%,rgb(50,200,210))",
      src1: "../../static/image/lightning_green.png",
      src2: "../../static/image/lightning_blue.png",
    }
  },
  methods: {
    back() {
      uni.navigateBack({})
    },
    request() {
      if (this.position == "") {
        this.isInput = false;
      } else {
        this.isInput = true;
        var url = 'https://apis.map.qq.com/ws/place/v1/suggestion?keyword=' + this.position + '&location=' +
            this.$store.state.currentLocation.latitude + ',' + this.$store.state.currentLocation.longitude +
            '&address_format=short' +
            '&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL';
        uni.request({
          url: url,
          method: 'GET',
          success: (res) => {
            if (res.data.status == '0') {
              this.suggestions.splice(0);
              for (var index in res.data.data) {
                this.suggestions.push({
                  id: res.data.data[index].id,
                  title: res.data.data[index].title,
                  address: res.data.data[index].address,
                  category: res.data.data[index].category,
                  location: res.data.data[index].location,
                  distance: (res.data.data[index]._distance / 1000).toFixed(1),
                  strings: "<div style='width: 100%;text-overflow: ellipsis;white-space: nowrap;overflow: hidden;'>" +
                      this.keyword(res.data.data[index].title, this.position) +
                      "<div>",
                });
              }
              ;
            }
          },
        })
      }
    },
    keyword(title, position) {
      if (title.includes(position)) {
        var html = (title.replace(
            position,
            '<span style="color:rgb(102,205,170);">' + position + '</span>'
        ));
        html = '<span class="text1">' + html + '</span>';
        return html;
      } else {
        return title;
      }
    },
    setStorage(id, title, location, category) {
      var searchHistory = uni.getStorageSync('searchHistory');
      if (searchHistory != '') {
        searchHistory = JSON.parse(searchHistory);
        for (var index in searchHistory) {
          if (searchHistory[index].id == id) {
            searchHistory.splice(index, 1);
            break;
          }
        }
        if (searchHistory.length >= 10) {
          searchHistory.splice(0, 1);
        }
        searchHistory.push({
          id: id,
          title: title,
          location: location,
          category: category
        });
        uni.setStorageSync('searchHistory', JSON.stringify(searchHistory));
      } else {
        searchHistory = [];
        searchHistory.push({
          id: id,
          title: title,
          location: location,
          category: category
        });
        uni.setStorageSync('searchHistory', JSON.stringify(searchHistory));
      }
    },
    tap(id, title, location, category) {
      this.setStorage(id, title, location, category);
      uni.request({
        url: 'https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=' +
            this.$store.state.currentLocation.latitude + ',' + this.$store.state.currentLocation
                .longitude + '&to=' +
            location.lat + ',' + location.lng + '&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL',
        success: (res) => {
          this.$store.commit('setDestination', {
            title: title,
            location: location,
            distance: (res.data.result.rows[0].elements[0].distance / 1000).toFixed(1),
          });
        }
      });

      uni.navigateBack();
      this.$store.commit('addIsLow');
    },
    tapStorage(title, location) {
      uni.request({
        url: 'https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from=' +
            this.$store.state.currentLocation.latitude + ',' + this.$store.state.currentLocation
                .longitude + '&to=' +
            +location.lat + ',' + location.lng + '&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL',
        success: (res) => {
          this.$store.commit('setDestination', {
            title: title,
            location: location,
            distance: (res.data.result.rows[0].elements[0].distance / 1000).toFixed(1),
          });
        }
      });
      uni.navigateBack();
      this.$store.commit('addIsLow');
    },
    tapStorageIcon(type) {
      if (type == 0 && JSON.stringify(this.frePlace.freHome) != '{}') {
        this.tapStorage(this.frePlace.freHome.title, this.frePlace.freHome.location)
      } else if (type == 1 && JSON.stringify(this.frePlace.freCompany) != '{}') {
        this.tapStorage(this.frePlace.freCompany.title, this.frePlace.freCompany.location)
      } else if (type == 2 && JSON.stringify(this.frePlace.freSchool) != '{}') {
        this.tapStorage(this.frePlace.freSchool.title, this.frePlace.freSchool.location)
      } else {
        uni.navigateTo({
          url: '../addFrePlace/addFrePlace',
        })
      }
    },
    clear() {
      uni.removeStorageSync('searchHistory');
      this.storages.splice(0);
    },
    del(index) {
      this.storages.splice(index, 1);
      var searchHistory = JSON.parse(uni.getStorageSync('searchHistory'));

      if (searchHistory.length != 1) {
        var historyIndex = searchHistory.length - 1 - index;
        searchHistory.splice(historyIndex, 1);
        uni.setStorageSync('searchHistory', JSON.stringify(searchHistory));
      } else uni.removeStorageSync('searchHistory');

    },
    delFre(index) {
      this.frePlace.freOther.splice(index, 1);
      uni.setStorageSync('frePlace', JSON.stringify(this.frePlace));
    },
    delFreIcon(type) {
      if (type == 0) {
        this.frePlace.freHome = {}
      } else if (type == 1) {
        this.frePlace.freCompany = {}
      } else if (type == 2) {
        this.frePlace.freSchool = {}
      }
      uni.setStorageSync('frePlace', JSON.stringify(this.frePlace));
    },
    change(e) {
      this.changeImg = e.detail.current;
    },
    animationfinish(e) {
      this.currentPage = e.detail.current;
    },
    transition(e) {
      // 50,200,210 蓝
      // 102,205,170 绿
      var dx = e.detail.dx;
      var percent = Math.abs(dx) / this.windowWidth;
      if (this.currentPage == 0) { //向右翻页
        if (percent <= 0.6) {
          this.$nextTick(function () {
            this.justifyContent = "flex-start";
          })

          this.modelWidth = 120 + 280 * percent * 2;
        } else {
          this.$nextTick(function () {
            this.justifyContent = "flex-end";
          })

          this.modelWidth = 400 - 280 * (percent - 0.5) * 2;
        }
        this.buttonLeft = 185 - 185 * percent;
        this.buttonOpacity1 = 1 - percent;
        this.buttonOpacity2 = percent;
        this.color = "rgba(" + (102 - 52 * percent) + "," + (205 - 5 * percent) + "," + (170 + 40 * percent) +
            ",1)";
        this.doubleColor = "linear-gradient(to right bottom,rgb(102,205,170) 0% " + (1 - percent) * 100 +
            "%," + this.$store.state.color + ")";
      } else {
        if (percent <= 0.6) {
          this.$nextTick(function () {
            this.justifyContent = "flex-end";
          })

          this.modelWidth = 120 + 280 * percent * 2;
        } else {
          this.$nextTick(function () {
            this.justifyContent = "flex-start";
          })

          this.modelWidth = 400 - 280 * (percent - 0.5) * 2;
        }
        this.buttonLeft = 185 * percent;
        this.buttonOpacity1 = percent;
        this.buttonOpacity2 = 1 - percent;
        this.color = "rgba(" + (50 + 52 * percent) + "," + (200 + 5 * percent) + "," + (210 - 40 * percent) +
            ",1)";
        this.doubleColor = "linear-gradient(to right bottom,rgb(102,205,170) 0% " + percent * 100 + "%," + this
            .$store.state.color + ")";
      }
    },
    addFrePlace() {
      uni.navigateTo({
        url: '../addFrePlace/addFrePlace',
      })
    }
  },
  mounted() {
    this.searchHeight = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
    var searchHistory = uni.getStorageSync('searchHistory');
    if (searchHistory) {
      searchHistory = JSON.parse(searchHistory);
      var length = searchHistory.length;
      for (var index in searchHistory) {
        this.storages.push(searchHistory[length - 1 - index]);
      }
    }
    this.frePlace = JSON.parse(uni.getStorageSync('frePlace'));
  },
  onShow() {
    this.frePlace = JSON.parse(uni.getStorageSync('frePlace'));
  }
}
</script>

<style scoped>
.searchNavi {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  color: #FFFFFF;
  font-size: 35upx;
  font-weight: 700;
  letter-spacing: 2upx;
}

.image1 {
  position: absolute;
  height: 70upx;
  width: 70upx;
  left: 45upx;
  bottom: 10upx;
  opacity: 1;
  transition: opacity 0.5s;
}

.image1_none {
  position: absolute;
  height: 70upx;
  width: 70upx;
  left: 45upx;
  bottom: 10upx;
  opacity: 0;
  transition: opacity 0.5s;
}

.image2 {
  position: relative;
  height: 50upx;
  width: 50upx;
  bottom: 7upx;
  right: 10upx;

}

.image3 {
  height: 70upx;
  width: 70upx;
}

.searchbox {
  background-color: rgb(240, 240, 240);
}

.textareaview {
  position: relative;
}

.textarea {
  background-color: rgb(255, 255, 255);
  margin: 30upx;
  padding-left: 80upx;
  padding-right: 100upx;
  width: 700upx;
  height: 90upx;
  font-size: 32upx;
  letter-spacing: 1upx;
  border-radius: 20upx;
  border: 6upx outset;
}

.searchtext {
  position: absolute;
  left: 608upx;
  bottom: 7upx;
  font-size: 32upx;
  color: rgba(102, 205, 170, 1);
  font-weight: 700;
  padding: 20upx 20upx 20upx 25upx;
}

.history {
  display: flex;
  justify-content: space-between;
}

.historyview {
  margin-left: 30upx;
  border-radius: 8upx;
  font-size: 30upx;
  font-weight: 700;
  letter-spacing: 1upx;
}

.commonview {
  margin-left: 50upx;
  border-radius: 8upx;
  font-size: 30upx;
  font-weight: 700;
  letter-spacing: 1upx;
}

.modelSelected {
  border-radius: 10upx;
  transition: all .1s;
}

.clear {
  background-color: rgba(102, 205, 170, 0.2);
  color: rgba(102, 205, 170, 1);
  margin-right: 30upx;
  padding: 10upx;
  font-size: 23upx;
  font-weight: 700;
  border-radius: 10upx;
  transition: all .1s;
}

.add {
  background-color: rgba(50, 200, 210, 0.2);
  color: rgb(50, 200, 210);
  margin-right: 30upx;
  padding: 10upx;
  font-size: 23upx;
  font-weight: 700;
  border-radius: 10upx;
  transition: all .1s;
}

.storage {
  margin-top: 10upx;
  padding: 15upx 15upx 5upx 20upx;
  border-radius: 10upx;
  display: flex;
  justify-content: space-between;
  overflow: hidden;
}

.view5 {
  position: relative;
  display: flex;
}

.text3 {
  margin: 18upx 15upx 0upx;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 500upx;
  font-size: 29upx;
  font-weight: 700;
  letter-spacing: 1upx;
}

.view4 {
  margin-top: 10upx;
  margin-right: 20upx;
}

.suggestion {
  margin-bottom: 10upx;
  border-radius: 10upx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
}

.view1 {
  display: flex;
  margin: 20upx 20upx 10upx 30upx;
  font-size: 30upx;
  font-weight: 700;
  letter-spacing: 1upx;
}

.text2 {
  background-color: rgba(102, 205, 170, 0.2);
  color: rgba(102, 205, 170, 1);
  margin-left: 80upx;
  padding: 10upx;
  font-size: 20upx;
}

.view3 {
  margin: 20upx 20upx 20upx 82upx;
}

.text6 {
  background-color: rgba(102, 205, 170, 0.2);
  color: rgba(102, 205, 170, 1);
  margin-left: 100upx;
  margin-bottom: 20upx;
  padding: 10upx;
  font-size: 20upx;
}

.text6_blue {
  background-color: rgba(102, 205, 170, 0.2);
  color: rgb(50, 200, 210, 1);
  margin-left: 100upx;
  margin-bottom: 20upx;
  padding: 10upx;
  font-size: 20upx;
}

.text6_blue_2 {
  background-color: rgba(102, 205, 170, 0.2);
  color: rgb(50, 200, 210, 1);
  margin-left: 10upx;
  margin-bottom: 20upx;
  padding: 10upx;
  font-size: 20upx;

}

.text1 {
  border: 2upx red solid;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 350upx;
}

.richtext {
  width: 450upx;
}

.backimg {
  position: absolute;
  left: 8px;
  width: 25px;
  height: 25px;
}
</style>
