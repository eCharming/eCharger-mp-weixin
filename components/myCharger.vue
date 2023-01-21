<template>
  <view
      style="position: relative;height: 300upx;margin:20upx;margin-bottom: 40upx;transform-style: preserve-3d; transition: .7s all;"
      :style="{'transform':'rotateY('+rotate+'deg)','height':height+'rpx'}">

    <view class="card" :style="{'width':width+'px'}">
      <image style="position: absolute;opacity: 0.25;height: 280upx;width: 328upx;right: 0;top: 0;"
             src="../static/image/background-blue.png"></image>

      <view
          style="padding: 15upx;display: flex;flex-direction: column;justify-content: space-between;position: absolute;height: 100%;width: 100%;">
        <view style="display: flex;flex-direction: column;">
          <view class="view1">
            <text class="location">{{ address }}</text>
            <text class="state1" v-if="state">未使用</text>
            <text class="state2" v-if="!state">使用中</text>
          </view>
          <view style="display: flex;justify-content: space-between;position: relative;">
            <text class="text">{{ location }}</text>
          </view>
        </view>
        <view style="margin-left: 10upx;">
          <text>价格</text>
          <text class="yuan">￥</text>
          <text class="price">{{ price }}</text>
        </view>
        <view style="margin-left: 10upx;margin-bottom: 10upx;">
          <text>可用时间：</text>
          <text class="time">{{ showTime }}</text>
        </view>
      </view>


    </view>

    <view style="position: absolute;transform: rotateY(90deg) translateZ(300upx);width: 100%;
		transition: .7s all;border-radius: 30upx;border-top: 10upx solid rgba(50,200,210,1);border-bottom: 10upx solid rgba(50,200,210,1);
		transform-style: preserve-3d;overflow: hidden;" :style="{'height':height+'rpx'}">
      <view
          style="position: relative;transition: .7s all;transform-style: preserve-3d;transform-origin: 50% 50% -150upx;"
          :style="{'transform':'rotateX('+buttonRotate+'deg)','height':height+'rpx'}">
        <view class="button" :style="{'opacity':buttonOpacitty}">
          <view style="position: absolute;right: 30upx;top: 20upx;">
            <text style="color:rgba(50,200,210,1) ;letter-spacing: 2upx;font-size: 28upx;"
                  @click.native.stop.prevent="untap">
              返回
            </text>
          </view>
          <view class="smalldetailview">
            <image src="../static/image/chargerdetail.png" style="height: 125upx;width: 125upx;"
                   @click.native.stop.prevent="editCharger"></image>
            <text class="smalldetail">修改</text>
          </view>
          <view class="smalldetailview">
            <image src="../static/image/delete.png" style="height: 125upx;width: 125upx;"
                   @click.native.stop.prevent="deleteCharger"></image>
            <text class="smalldetail">删除</text>
          </view>
        </view>

      </view>
    </view>

  </view>
</template>

<script>
export default {
  props: {
    address: {
      type: String
    },
    location: {
      type: String
    },
    state: {
      type: Boolean
    },
    price: {
      type: String
    },
    cid: {
      type: String
    },
    time: {
      type: Array,
      default: () => []
    },
    windowWidth: {
      type: Number
    },
    detail: {
      type: Boolean
    },
  },
  data() {
    return {
      boxshadow: "",

      height: 300,
      translate: 'translate(' + (this.windowWidth - uni.upx2px(160) + 10) + 'px,-' + uni.upx2px(300) + 'px)',
      rotate: 0,
      buttonRotate: 0,
      buttonOpacitty: 1,
      bookRotate: -90,
    }
  },
  computed: {
    showTime() {
      var tempDate = new Date();
      var days = tempDate.getDay();
      if (days == 0) {
        days = 7;
      }
      var showTime = "";
      if (this.time[days - 1] == "") {
        showTime = "-"
      } else {
        showTime = this.time[days - 1]
      }
      return showTime;
    },
    width() {
      return (this.windowWidth - uni.upx2px(160))
    },
  },
  methods: {
    untap() {
      this.$emit('chargerUndetail')
    },
    editCharger() {
      if (this.state == false) {
        wx.showToast({
          title: "无法修改！",
          icon: 'error',
          complete: () => {

          }
        })
        return;
      }
      this.$store.commit('setChargerCardDefault')
      uni.navigateTo({
        url: '../editCharger/editCharger?cid=' + this.cid,
      });
    },
    deleteCharger() {
      wx.showModal({
        title: '提⽰',
        content: '确认要删除该电桩?',
        success: res => {
          if (res.confirm) {
            wx.cloud.callFunction({ //输入订单
              name: 'chargerDelete',
              data: {
                _id: Number(this.cid)
              }
            }).then(
                res => {
                  if (res.result == "charger unavailable") {
                    wx.showToast({
                      title: "删除失败！",
                      icon: 'error',
                      complete: () => {
                        this.$store.commit('setChargerCardDefault')
                        this.$store.commit('setGetChargers')
                      }
                    })
                  } else {
                    wx.request({
                      url: "https://ws.healtool.cn/deletePic/" + this.cid,
                      method: "POST",
                      success: res => {
                        //如果图片删除失败也不管了
                        wx.showToast({
                          title: "删除成功！",
                          icon: 'success',
                          complete: () => {
                            this.$store.commit('setChargerCardDefault')
                            this.$store.commit('setGetChargers')
                          }
                        })
                      }
                    })

                  }
                }
            )
          }
        }
      })
    }
  },
  watch: {
    'detail'() {
      if (this.detail == true) {
        this.$nextTick(function () {
          this.rotate = -90;
        })
      } else {
        this.$nextTick(function () {
          this.rotate = 0;
          this.bookRotate = -90;
          this.height = 300;
          this.buttonRotate = 0;
          this.buttonOpacitty = 1;
        })
      }
    }
  }
}
</script>

<style scoped>
.card {
  position: absolute;

  border: 10upx inset rgba(50, 200, 210, 1);
  background-color: white;
  border-radius: 40upx;

  transition: all .5s;
  transform: translateZ(300upx);
  height: 300upx;
}

.view1 {
  display: flex;
  justify-content: space-between;
}

.view2 {
  display: flex;
  justify-content: space-between;
  margin: 15upx;
  margin-top: 60upx;
}

.location {
  margin: 15upx;
  font-size: 30upx;
  font-weight: 700;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 350upx;
}

.text {
  margin-top: 10upx;
  margin-left: 15upx;
  opacity: 0.5;
  width: 450upx;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.state1 {
  margin: 15upx;
  margin-top: 20upx;
  color: rgb(50, 200, 210);
}

.state2 {
  margin: 15upx;
  margin-top: 20upx;
  color: rgb(255, 99, 71);
}

.yuan {
  font-weight: 700;
  color: #249399;
}

.price {
  font-size: 40upx;
  font-weight: 700;
  color: #249399;
}

.time {
  font-size: 35upx;
  font-weight: 700;
  color: #249399;
  letter-spacing: 3upx;
}

.timeview {
  margin-top: 15upx;
}

.priceview {
  margin-top: 15upx;
}

.smalldetailview {
  height: 180upx;
  display: flex;
  flex-direction: column;
  justify-content: center;

}

.smalldetail {
  font-size: 25upx;
  letter-spacing: 0.6upx;
  margin-left: 38upx;
  position: relative;
  bottom: 10upx;
}

.button {
  position: absolute;
  width: 100%;
  height: 300upx;
  display: flex;
  align-items: center;
  justify-content: space-around;
  transform: translateZ(5upx);
  background-color: rgba(250, 255, 250, 1);
  transition: all .7s;
}
</style>
