<template>
  <view style="display: flex;flex-direction:column">
    <view :style="{'height':statusHeight+'px','width':'100%','top':0,'z-index':9999}">
      <view class="addChargerNavi"
            :style="{'height':statusHeight+'px','background':color,'position':'fixed','width':'100%','top':0}">
        <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
               @tap="back"></image>
        <text :style="{'margin-bottom':addChargerHeight+'px'}">添加电桩</text>
      </view>
    </view>

    <view>
      <addcard>
        <view class="display">
          <text class="labeltext">姓名</text>
          <input class="input" placeholder="请输入姓名" type="text" v-model="name" maxlength="20"></input>
        </view>
        <view class="divLine"></view>
        <view class="display">
          <text class="labeltext">电话号码</text>
          <input class="input" placeholder="请输入电话号码" type="number" v-model="phoneNumber" maxlength="11"></input>
        </view>
      </addcard>
      <addcard style="position: relative;">
        <view class="display">
          <text class="labeltext">电桩位置</text>
          <input class="input" placeholder="请输入电桩位置" type="text" v-model='address'
                 @input="searchKeyword"></input>
        </view>


        <map id="myMap" style="width: 680upx; height: 500upx;" layer-style="1" :latitude="center_latitude"
             :longitude="center_longitude" showLocation='true' subkey="ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL"
             :markers="covers" @tap="addMarker">
        </map>
        <view class="scroll" v-if="locationList.length!=0">
          <view style="display: flex;justify-content: flex-end;">
            <scroll-view class="scrollview" scroll-y="true" enhanced="true" show-scrollbar="true"
                         scroll-with-animation="true">
              <view v-for="(item,index) in locationList" :key="index" class="scroll-innerview"
                    @tap="tapsearch(item)">
                <text v-text="item.title" class="scroll-text" style="margin:10upx"></text>
              </view>

            </scroll-view>
          </view>
        </view>

        <view class="display">
          <text class="labeltext">详细地址</text>
          <textarea class="textareainput" placeholder="请输入详细地址,精确到门牌号或停车位" maxlength="100"
                    v-model='location'></textarea>
        </view>

        <view class="divLine"></view>

        <view class="display">
          <text class="labeltext">电桩单价</text>
          <input class="input" placeholder="请输入单价" type="digit" v-model="price" :maxlength="maxlength"></input>
        </view>
      </addcard>
      <addcard>
        <view class="display">
          <text class="labeltext">周一可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[0]" :end="maxTime[0]" @change="changetime1($event,0)">
                <text class="timetext1" :style="{'opacity':opacity[0]}">{{ text[0] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[1]" :end="maxTime[1]" @change="changetime2($event,1)">
                <text class="timetext1" :style="{'opacity':opacity[1]}">{{ text[1] }}</text>
              </picker>
            </view>
          </view>
        </view>
        <view class="display">
          <text class="labeltext">周二可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[2]" :end="maxTime[2]" @change="changetime1($event,2)">
                <text class="timetext1" :style="{'opacity':opacity[2]}">{{ text[2] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[3]" :end="maxTime[3]" @change="changetime2($event,3)">
                <text class="timetext1" :style="{'opacity':opacity[3]}">{{ text[3] }}</text>
              </picker>
            </view>
          </view>
        </view>
        <view class="display">
          <text class="labeltext">周三可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[4]" :end="maxTime[4]" @change="changetime1($event,4)">
                <text class="timetext1" :style="{'opacity':opacity[4]}">{{ text[4] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[5]" :end="maxTime[5]" @change="changetime2($event,5)">
                <text class="timetext1" :style="{'opacity':opacity[5]}">{{ text[5] }}</text>
              </picker>
            </view>
          </view>
        </view>
        <view class="display">
          <text class="labeltext">周四可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[6]" :end="maxTime[6]" @change="changetime1($event,6)">
                <text class="timetext1" :style="{'opacity':opacity[6]}">{{ text[6] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[7]" :end="maxTime[7]" @change="changetime2($event,7)">
                <text class="timetext1" :style="{'opacity':opacity[7]}">{{ text[7] }}</text>
              </picker>
            </view>
          </view>
        </view>
        <view class="display">
          <text class="labeltext">周五可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[8]" :end="maxTime[8]" @change="changetime1($event,8)">
                <text class="timetext1" :style="{'opacity':opacity[8]}">{{ text[8] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[9]" :end="maxTime[9]" @change="changetime2($event,9)">
                <text class="timetext1" :style="{'opacity':opacity[9]}">{{ text[9] }}</text>
              </picker>
            </view>
          </view>
        </view>
        <view class="display">
          <text class="labeltext">周六可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[10]" :end="maxTime[10]"
                      @change="changetime1($event,10)">
                <text class="timetext1" :style="{'opacity':opacity[10]}">{{ text[10] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[11]" :end="maxTime[11]"
                      @change="changetime2($event,11)">
                <text class="timetext1" :style="{'opacity':opacity[11]}">{{ text[11] }}</text>
              </picker>
            </view>
          </view>
        </view>
        <view class="display">
          <text class="labeltext">周日可用时段</text>
          <view style="display:flex;justify-content: space-between;align-items:center;">
            <view class="timer1">
              <picker mode="time" :start="minTime[12]" :end="maxTime[12]"
                      @change="changetime1($event,12)">
                <text class="timetext1" :style="{'opacity':opacity[12]}">{{ text[12] }}</text>
              </picker>
            </view>

            <text class="line">-</text>

            <view class="timer1">
              <picker mode="time" :start="minTime[13]" :end="maxTime[13]"
                      @change="changetime2($event,13)">
                <text class="timetext1" :style="{'opacity':opacity[13]}">{{ text[13] }}</text>
              </picker>
            </view>
          </view>
        </view>
      </addcard>
      <addcard>
        <view class="display">
          <text class="labeltext">图片</text>
        </view>
        <view class='content'>
          <view class='img-view' v-for='(item,index) in avatarUrl' :key='index'>
            <image :src='item' @tap='showPic(item)'
                   style="width:200upx;height:200upx;margin-left: 3.9upx;margin-right: 3.9upx;"
                   mode='aspectFill'>
            </image>
            <icon @tap='deletePic(index)' class='close' color='rgb(199, 0, 3)' type='clear' size='15'>
            </icon>
          </view>
          <image @tap='uploadPic' class='add-img' src='../../static/image/addImg.png' v-show="isAdd"></image>
        </view>
        <view class="divLine"></view>
        <view class="display">
          <text class="labeltext">备注</text>
          <textarea class="input" placeholder="备注" maxlength="100" auto-height="true" v-model='remarks'></textarea>
        </view>
      </addcard>
      <button class="submit" @tap="submit" :disabled="disable">提交</button>
    </view>
  </view>
</template>

<script>
import addcard from '../../components/addCard.vue'

export default {
  components: {
    addcard
  },
  data() {
    return {
      statusHeight: uni.getSystemInfoSync().statusBarHeight + 50,
      windowWidth: uni.getSystemInfoSync().windowWidth,
      addChargerHeight: 0,
      color: 'rgba(50,200,210,1)',
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
      name: "",
      phoneNumber: "",
      location: "",
      address: "",
      price: "",
      maxlength: 5,
      haveDDot: false,
      text: ["起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间",
        "起始时间", "结束时间"
      ],
      minTime: ['00:00', '00:00', '00:00', '00:00', '00:00', '00:00', '00:00', '00:00', '00:00', '00:00',
        '00:00', '00:00', '00:00', '00:00'
      ],
      maxTime: ['24:00', '24:00', '24:00', '24:00', '24:00', '24:00', '24:00', '24:00', '24:00', '24:00',
        '24:00', '24:00', '24:00', '24:00'
      ],
      opacity: [0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7],
      remarks: "",
      avatarUrl: [],
      covers: [],
      geopoint: {
        latitude: 39.909,
        longitude: 116.39742,
      },
      locationList: [],
      center_latitude: this.$store.state.currentLocation == null ? 39.909 : this.$store.state.currentLocation.latitude,
      center_longitude: this.$store.state.currentLocation == null ? 116.39742 : this.$store.state.currentLocation.longitude,
      disable: false,
    }
  },
  computed: {
    isAdd() {
      return this.avatarUrl.length < 6;
    },
    listLength() {
      return 6 - this.avatarUrl.length;
    }
  },
  methods: {
    showMarker() {
      this.covers.splice(0, 1, {
        title: "定位点",
        id: 0,
        latitude: this.geopoint.latitude,
        longitude: this.geopoint.longitude,
        iconPath: "/static/image/charger.png",
        width: 50,
        height: 50,
      })
    },
    tapsearch(item) {
      this.address = item.title;
      this.geopoint.latitude = item.location.lat;
      this.geopoint.longitude = item.location.lng;
      this.showMarker();
      this.center_latitude = this.geopoint.latitude;
      this.center_longitude = this.geopoint.longitude;
      this.locationList.splice(0);
    },
    searchKeyword() {
      var url = 'https://apis.map.qq.com/ws/place/v1/suggestion?keyword=' + this.address +
          '&address_format=short' +
          '&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL';
      uni.request({
        url: url,
        method: 'GET',
        success: (res) => {
          this.locationList.splice(0)
          if (res.data.status == 0) {
            this.locationList.push(...res.data.data)
          }
        },
        fail: (res) => {
          this.locationList.splice(0)
        }
      })
    },
    addMarker(res) {
      this.geopoint.longitude = res.detail.longitude;
      this.geopoint.latitude = res.detail.latitude;
      this.showMarker();
      var url = "https://apis.map.qq.com/ws/geocoder/v1/?location=" + this.geopoint.latitude + "," + this
          .geopoint.longitude + '&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL';
      uni.request({
        url: url,
        method: 'GET',
        success: (res) => {
          this.address = res.data.result.address
        },
      })
    },
    back() {
      uni.navigateBack({})
    },
    changetime1(e, index) {
      var time = e.detail.value;
      this.text.splice(index, 1, time)
      this.minTime.splice(index + 1, 1, time)
      this.opacity.splice(index, 1, 1);
    },
    changetime2(e, index) {
      var time = e.detail.value;
      this.text.splice(index, 1, time)
      this.maxTime.splice(index - 1, 1, time)
      this.opacity.splice(index, 1, 1);
    },
    uploadPic() {
      if (this.avatarUrl.length >= 6) {
        wx.showToast({
          title: "图片数量不能超过六张！",
          icon: 'none',
        })
        return;
      }
      wx.chooseMedia({
        count: this.listLength,
        mediaType: ['image'],
        sourceType: ['album', 'camera'],
        maxDuration: 30,
        camera: 'back',
        success: res => {
          //限制图片大小
          for (var i = 0; i < res.tempFiles.length; i++) {
            let size = res.tempFiles.every(item => {
              return item.size <= 10000000
            })
            if (size) {
              this.avatarUrl.push(res.tempFiles[i].tempFilePath);
            } else {
              wx.showToast({
                title: '上传图片不能大于10M!',
                icon: 'none'
              })
              return;
            }
          }
        }
      })
    },
    submit() {
      var reg_tel = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
      if (this.name == "") {
        wx.showToast({
          title: "请输入名字！",
          icon: 'none',
        })
        return;
      }
      if (this.phoneNumber == "") {
        wx.showToast({
          title: "请输入电话号码！",
          icon: 'none',
        })
        return;
      }
      if (!reg_tel.test(this.phoneNumber)) {
        wx.showToast({
          title: "请输入正确的手机号！",
          icon: 'none',
        })
        return;
      }
      if (this.location == "") {
        wx.showToast({
          title: "请输入电桩位置！",
          icon: 'none',
        })
        return;
      }
      if (this.geopoint.longitude == -1 || this.geopoint.latitude == -1) {
        wx.showToast({
          title: "请在地图上标注电桩位置！",
          icon: 'none',
        })
        return;
      }
      if (this.price == "") {
        wx.showToast({
          title: "请输入电桩单价！",
          icon: 'none',
        })
        return;
      }
      if (this.listLength == 6) {
        wx.showToast({
          title: "请上传照片！",
          icon: 'none',
        })
        return;
      }
      let isSet = false;
      for (let i = 0; i < this.text.length; i += 2) {
        if (this.text[i] != '起始时间' || this.text[i + 1] != '结束时间') {
          isSet = true;
          break;
        }
      }
      if (isSet == false) {
        wx.showToast({
          title: "请填写至少一个时间段！",
          icon: 'none',
        })
        return;
      }
      let isFinish = true;
      for (let i = 0; i < this.text.length; i += 2) {
        if (this.text[i] != '起始时间' && this.text[i + 1] == '结束时间') {
          isFinish = false;
          break;
        }
        if (this.text[i] == '起始时间' && this.text[i + 1] != '结束时间') {
          isFinish = false;
          break;
        }
      }
      if (isFinish == false) {
        wx.showToast({
          title: "请填写完整的时间段！",
          icon: 'none',
        })
        return;
      }
      let isLong = true;
      for (let i = 0; i < this.text.length; i += 2) {
        if (this.text[i] != '起始时间' && this.text[i + 1] != '结束时间') {
          let first = this.text[i].split(':')
          let second = this.text[i + 1].split(':')
          if (second[0] * 60 + second[1] - first[0] * 60 - first[1] < 60) {
            isLong = false;
            break;
          }
        }
      }
      if (isLong == false) {
        wx.showToast({
          title: "时间段请大于1小时！",
          icon: 'none',
        })
        return;
      }
      var time = new Array();
      for (var i = 0; i < 14; i += 2) {
        if (this.text[i] == "起始时间" || this.text[i + 1] == "结束时间") {
          time.push("")
        } else {
          time.push(this.text[i] + "-" + this.text[i + 1])
        }
      }
      var timestamp = new Array();
      for (var i = 0; i < 14; i++) {
        if (this.text[i] == "起始时间" || this.text[i] == "结束时间") {
          timestamp.push(-1)
        } else {
          var sp = this.text[i].split(":")
          timestamp.push(Number(sp[0]) * 60 + Number(sp[1]))
        }
      }
      this.disable = true;
      wx.cloud.callFunction({ //uid获取
        name: 'chargerInput',
        data: {
          uid: this.$store.state.uid,
          userName: this.name,
          phoneNumber: this.phoneNumber,
          address: this.address,
          location: this.location,
          geoPoint: {
            coordinates: [this.geopoint.longitude, this.geopoint.latitude],
            type: "Point",
          },
          timeStamp: timestamp,
          time: time,
          price: this.price,
          remarks: this.remarks,
        }
      }).then(
          res => {
            var id = res.result._id;
            for (var i = 0; i < this.avatarUrl.length; i++) {
              wx.uploadFile({
                url: "https://ws.healtool.cn/uploadPic/" + id,
                filePath: this.avatarUrl[i],
                name: 'file',
                success: res => {
                  if (res.data.startsWith("<html>")) {
                    wx.showToast({
                      title: "图片上传失败！",
                      icon: 'none',
                    })
                    wx.cloud.callFunction({
                      name: 'chargerDelete',
                      data: {
                        _id: id
                      },
                    })
                    this.disable = false;
                    return;
                  }
                  var res = JSON.parse(res.data)
                  if (res.code != 200) {
                    wx.showToast({
                      title: "图片上传失败！",
                      icon: 'none',
                    })
                    wx.cloud.callFunction({
                      name: 'chargerDelete',
                      data: {
                        _id: id
                      },
                    })
                    this.disable = false;
                    return;
                  } else {
                    this.$store.commit('setRefresh')
                    wx.showToast({
                      title: "提交成功！",
                      icon: 'success',
                      complete: () => {
                        setTimeout(() => {
                          this.disable = false;
                          uni.navigateBack({})
                        }, 1500)
                      }
                    })
                  }
                },
                fail: res => {
                  wx.showToast({
                    title: "图片上传失败！",
                    icon: 'none',
                  })
                  wx.cloud.callFunction({
                    name: 'chargerDelete',
                    data: {
                      _id: id
                    },
                  })
                  this.disable = false;
                  return;
                }
              })
            }
          }
      )
    },
    showPic(item) {
      wx.previewImage({
        current: item,
        urls: this.avatarUrl,
      })
    },
    deletePic(index) {
      this.avatarUrl.splice(index, 1);
    }
  },
  mounted() {
    this.addChargerHeight = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
    if (this.$store.state.currentLocation == null) {
      wx.showToast({
        title: "请打开定位！",
        icon: 'error',
        complete: () => {
          setTimeout(() => {
            uni.navigateBack({})
          }, 1500)

        }
      })
    }
  },
  watch: {
    price() {
      if (this.price.indexOf('.') != -1 && !this.haveDot) {
        this.maxlength = this.price.length + 2
        this.haveDot = true
      } else if (this.price.indexOf('.') == -1 && this.haveDot) {
        this.maxlength = 5
        this.haveDot = false
      }
    }
  }
}
</script>

<style scoped>
.addChargerNavi {
  display: flex;
  justify-content: center;
  align-items: flex-end;
  color: #FFFFFF;
  font-size: 35upx;
  font-weight: 700;
  letter-spacing: 2upx;
}

.backimg {
  position: absolute;
  left: 8px;
  width: 25px;
  height: 25px;
}

.labeltext {
  letter-spacing: 2upx;
  font-weight: 700;
}

.display {
  display: flex;
  justify-content: space-between;
  margin: 28upx;
}

.divLine {
  width: 100%;
  height: 3upx;
  background-color: #E0E3DA;
}

.input {
  width: 500upx;
  text-align: right;
}

.line {
  margin-left: 10upx;
  margin-right: 10upx;
}

.timer1 {
  width: 110upx;
  display: flex;
  justify-content: center;
}

.add-img {
  width: 200upx;
  height: 200upx;
  opacity: 0.3;
  margin-left: 3.9upx;
  margin-right: 3.9upx;
}

.content {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin-left: 28upx;
  margin-right: 28upx;
  margin-bottom: 28upx;
}

.close {
  position: absolute;
  margin-left: -45upx;
  margin-top: 10upx;
}

.submit {
  background-color: rgb(50, 200, 210);
  color: white;
  font-weight: 700;
  letter-spacing: 3upx;
  margin: 20upx;
  border-radius: 20upx;
}

.textareainput {
  height: 100upx;
  width: 500upx;
  text-align: right;
}

.scroll {
  position: absolute;
  height: 300upx;
  width: 680upx;
  top: 110upx;
}

.scrollview {
  background-color: rgba(250, 255, 250, 1);
  height: 300upx;
  width: 100%;
  border-bottom-right-radius: 20upx;
  border-bottom-left-radius: 20upx;
  box-shadow: -16upx 16upx 20upx -8upx rgba(116, 118, 116, 0.2), 16upx 16upx 20upx -8upx rgba(116, 118, 116, 0.2);
}

.scroll-innerview {
  margin: 28upx;
}
</style>
