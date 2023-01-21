<template>
  <view>
    <view :style="{'height':statusHeight+'px','background':doubleColor}">
      <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
             @tap="back"></image>
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

      <scroll-view scroll-y="true"
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
      position: "",
      suggestions: [],
      suggestionHeight: this.$store.state.windowHeight * 0.85,
      index: 0,
      isInput: false,
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
    setOtherFreStorage(id, title, location, category) {
      var otherFrePlace = uni.getStorageSync('frePlace');
      otherFrePlace = JSON.parse(otherFrePlace);
      for (var index in otherFrePlace.freOther) {
        if (otherFrePlace.freOther[index].id == id) {
          otherFrePlace.freOther.splice(index, 1);
          break;
        }
      }
      otherFrePlace.freOther.push({
        id: id,
        title: title,
        location: location,
        category: category
      });
      uni.setStorageSync('frePlace', JSON.stringify(otherFrePlace));
    },
    setFreStorage(id, title, location, category, type) {
      var otherFrePlace = uni.getStorageSync('frePlace');
      otherFrePlace = JSON.parse(otherFrePlace);
      var frePlace = {
        id: id,
        title: title,
        location: location,
        category: category
      }
      if (type == 0) {
        otherFrePlace.freHome = frePlace
      } else if (type == 1) {
        otherFrePlace.freCompany = frePlace
      } else if (type == 2) {
        otherFrePlace.freSchool = frePlace
      }
      uni.setStorageSync('frePlace', JSON.stringify(otherFrePlace));
    },
    tap(id, title, location, category) {
      uni.showActionSheet({
        itemList: ['家', '公司', '学校', '其他'],
        success: (res) => {
          if (res.tapIndex == 3) {
            this.setOtherFreStorage(id, title, location, category);
          } else {
            this.setFreStorage(id, title, location, category, res.tapIndex);
          }
          wx.showToast({
            title: "添加成功！",
            icon: 'success',
            complete: () => {
              setTimeout(() => {
                uni.navigateBack({})
              }, 500)
            }
          })
        },
        fail: (res) => {
          wx.showToast({
            title: "添加失败！",
            icon: 'error',
            complete: () => {
              setTimeout(() => {
                uni.navigateBack({})
              }, 500)
            }
          })
        }
      })
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
    }
  },
}
</script>

<style scoped>
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
  padding: 20upx;
  padding-left: 25upx;
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
  margin: 18upx 15upx 0;
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
