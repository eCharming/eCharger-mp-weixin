<template>
  <view
      style="position: relative;height: 300upx;margin:20upx;margin-bottom: 40upx;transform-style: preserve-3d; transition: .7s all;"
      :style="{'transform':'rotateY('+rotate+'deg)','height':height+'rpx'}">

    <view id="box" style="border-radius: 40upx;height: 300upx;width: 100%;position: absolute;transform: translateZ(300upx);
		background:linear-gradient(to right bottom,rgb(102,205,170),#d4fce9) ;overflow: hidden;">
      <view
          style="position: absolute;background-color: #219779;border-radius: 40upx;height: 300upx;width:100%;left: 10upx;top: 10upx;transition: all .3s;"
          :style="{'transform':translate}"></view>

      <view class="card" :style="{'width':width+'px'}" @tap="checkMap()">

        <image style="position: absolute;opacity: 0.25;height: 284upx;width: 333upx;right: -2upx;"
               src="../static/image/background.png"></image>

        <view
            style="display: flex;flex-direction: column;justify-content: space-between;position: absolute;height: 100%;width: 100%;padding: 15upx;">
          <view style="display: flex;flex-direction: column;">
            <view class="view1">
              <text class="location">{{ address }}</text>
              <view>
                <image class="image" src="../static/image/arrow.png"></image>
                <text class="distance">{{ distance }}km</text>
              </view>

            </view>
            <view style="display: flex;justify-content: space-between;position: relative;">
              <text class="text">{{ location }}</text>
              <image class="image1" :style="{'opacity':checkOpacity,'right':checkRight+'rpx'}"
                     src='../static/image/checkdetail.png' v-if="check"
                     @click.native.stop.prevent="checkDetail"></image>
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
    </view>

    <view style="position: absolute;transform: rotateY(90deg) translateZ(300upx);width: 100%;
		transition: .7s all;border-radius: 30upx;border-top: 10upx solid rgba(102,205,170,1);border-bottom: 10upx solid rgba(102,205,170,1);
		transform-style: preserve-3d;overflow: hidden;" :style="{'height':height+'rpx'}">
      <view
          style="position: relative;transition: .7s all;transform-style: preserve-3d;transform-origin: 50% 50% -150upx;"
          :style="{'transform':'rotateX('+buttonRotate+'deg)','height':height+'rpx'}">
        <view class="button" :style="{'opacity':buttonOpacitty}">
          <view style="position: absolute;right: 30upx;top: 20upx;">
            <text style="color:rgba(102,205,170,1) ;letter-spacing: 2upx;font-size: 28upx;"
                  @tap="unCheckDetail()">
              返回
            </text>
          </view>
          <view class="smalldetailview" :animation="animationBook">
            <image src="../static/image/order.png" style="height: 125upx;width: 125upx;" @tap="book">
            </image>
            <text class="smalldetail">预约</text>
          </view>
          <view class="smalldetailview" :animation="animationContact">
            <image src="../static/image/connection.png" style="height: 125upx;width: 125upx;" @tap="chat">
            </image>
            <text class="smalldetail">联系</text>
          </view>
          <view class="smalldetailview" :animation="animationNavigate">
            <image src="../static/image/navigation.png" style="height: 125upx;width: 125upx;"
                   @tap="navigate"></image>
            <text class="smalldetail">导航</text>
          </view>
          <view class="smalldetailview" :animation="animationDetail">
            <image src="../static/image/chargerdetail.png" style="height: 125upx;width: 125upx;"
                   @tap="orderDetail"></image>
            <text class="smalldetail">详情</text>
          </view>
        </view>

        <view class="book" style="transform-origin: center;transform:rotateX(180deg);position: absolute;transform-style: preserve-3d;
				height: 100%;width: 100%;">
          <view style="position: relative;height: 100%;width: 100%;transform-style: preserve-3d;perspective: 500px;
					transform:translateZ(300upx);">
            <view style="height: 300upx;width: 100%;position: absolute;background-color: #edfdf6;
						display: flex;flex-direction: column;justify-content: space-between;">

              <view style="margin-right:30upx;margin-top: 20upx;display:flex;justify-content: flex-end;">
                <text style="color:rgba(102,205,170,1) ;letter-spacing: 2upx;font-size: 28upx;"
                      @tap="unbook()">
                  返回
                </text>

              </view>

              <view>
                <view
                    style="margin:30upx;margin-top: 0;width: 550upx;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                  <text style="font-size: 30upx;font-weight: 700;
									">{{ location }}
                  </text>
                </view>
                <view
                    style="margin-left:30upx;margin-right:30upx;margin-top: 10upx;display: flex;justify-content: space-between;">
                  <view>
                    <image class="image" src="../static/image/arrow.png"></image>
                    <text
                        style="color:rgba(102,205,170,1) ;font-size: 30upx;letter-spacing: 1upx;">
                      距离您约{{ distance }}km
                    </text>
                  </view>
                  <view style="color:rgba(102,205,170,1) ;font-size: 30upx;letter-spacing: 1upx;"
                        @tap="bookDetail()">
                    详情
                  </view>
                </view>


              </view>
              <view style="display: flex;justify-content: space-between;margin: 30upx;">
                <view class="priceview">
                  <text>价格</text>
                  <text class="yuan">￥</text>
                  <text class="price">{{ price }}</text>
                </view>
                <view class="timeview">
                  <text>可用时间：</text>
                  <text class="time">{{ showTime }}</text>
                </view>
              </view>
            </view>

            <view style="height: 300upx;width: 100%;position: absolute;transform-origin: top;top: 300upx;
						border-top: 2px solid rgba(0,0,0,0.3);transition: 0.7s all;background-color: #edfdf6;
						display: flex;flex-direction: column;justify-content: space-between;"
                  :style="{'transform':'rotateX('+bookRotate+'deg)'}">
              <view
                  style="margin: 30upx;display: flex;letter-spacing: 3upx;font-weight: 700;font-size: 31upx;align-items: center;">
                <text>
                  预约时间
                </text>
                <view class="time1">
                  <picker mode="time" :start="minTime1" :end="maxTime1" @change="changetime1">
                    <text class="timetext1" :style="{'opacity':opacity1}">{{ text1 }}</text>
                  </picker>
                </view>
                <view class="line">-</view>
                <view class="time2">
                  <picker mode="time" :start="minTime2" :end="maxTime2" @change="changetime2">
                    <text class="timetext2" :style="{'opacity':opacity2}">{{ text2 }}</text>
                  </picker>
                </view>
              </view>
              <view class="priceview">
                <text>预估价格：</text>
                <text class="time">￥{{ possiblePrice }}</text>
              </view>
              <view
                  style="display: flex;justify-content: center;align-items: center;margin-bottom: 40upx;">
                <view style="width: 150rpx;height: 70upx;background-color: rgba(102,205,170,1);
								border-radius: 20upx;display: flex;justify-content: center;align-items: center;">
                  <text style="color: white;font-size: 33upx;font-weight: 700;letter-spacing: 10upx;"
                        @tap="bookOrder">
                    预约
                  </text>

                </view>
              </view>

            </view>
          </view>

        </view>
      </view>

    </view>
  </view>

</template>

<script>
export default {
  props: {
    index: {
      type: Number
    },
    cid: {
      type: Number
    },
    uid: {
      type: Number
    },
    longitude: {
      type: String
    },
    latitude: {
      type: String
    },
    address: {
      type: String
    },
    location: {
      type: String
    },
    distance: {
      type: Number
    },
    price: {
      type: String
    },
    time: {
      type: Array,
      default: () => []
    },
    detail: {
      type: Boolean
    },
    windowWidth: {
      type: Number
    }
  },
  data() {
    return {
      height: 300,
      width: 0,
      translate: 'translate(' + (this.windowWidth - uni.upx2px(160) + 10) + 'px,-' + uni.upx2px(300) + 'px)',
      check: false,
      checkOpacity: 0,
      rotate: 0,
      checkRight: 100,
      animationBook: {},
      animationContact: {},
      animationNavigate: {},
      animationDetail: {},
      buttonRotate: 0,
      buttonOpacitty: 1,
      bookRotate: -90,

      minTime1: '00:00',
      maxTime1: '24:00',
      minTime2: '00:00',
      maxTime2: '24:00',
      text1: '起始时间',
      text2: '结束时间',
      opacity1: 0.5,
      opacity2: 0.5,
      possiblePrice: '0'
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

  },
  methods: {
    changetime1(e) {
      var time1 = e.detail.value;
      this.text1 = time1;
      this.minTime2 = time1;
      this.opacity1 = 1;
      if (this.text2 != "结束时间") {
        var sp1 = this.text1.split(":")
        var sp2 = this.text2.split(":")
        var time1 = Number(sp1[0]) * 60 + Number(sp1[1])
        var time2 = Number(sp2[0]) * 60 + Number(sp2[1])
        this.possiblePrice = ((time2 - time1) / 60 * this.price).toFixed(2)
      }
    },
    changetime2(e) {
      var time2 = e.detail.value;
      this.text2 = time2;
      this.maxTime1 = time2;
      this.opacity2 = 1;
      if (this.text1 != "起始时间") {
        var sp1 = this.text1.split(":")
        var sp2 = this.text2.split(":")
        var time1 = Number(sp1[0]) * 60 + Number(sp1[1])
        var time2 = Number(sp2[0]) * 60 + Number(sp2[1])
        this.possiblePrice = ((time2 - time1) / 60 * this.price).toFixed(2)
      }
    },
    tap() {
      this.translate = 'translate(0rpx,-300rpx)';
      setTimeout(() => {
        this.translate = 'translate(' + (-this.width) + 'px,0px)';
      }, 300);
      setTimeout(() => {
        this.translate = 'translate(0rpx,284rpx)';
      }, 600);
      setTimeout(() => {
        this.translate = 'translate(0rpx,0rpx)';
      }, 900);

      this.check = true;
      this.$nextTick(function () {
        this.checkOpacity = 1;
        this.checkRight = 10;
      });
    },
    untap() {
      this.translate = 'translate(0rpx,284rpx)';
      setTimeout(() => {
        this.translate = 'translate(' + (-this.width) + 'px,0px)';
      }, 300);
      setTimeout(() => {
        this.translate = 'translate(0rpx,-300rpx)';
      }, 600);
      setTimeout(() => {
        this.translate = 'translate(' + (this.width + 10) + 'px,-' + uni.upx2px(300) + 'px)';
      }, 900);

      this.check = false;
      this.checkOpacity = 0;
      this.checkRight = 100;
    },
    checkMap() {
      this.$emit('map');
    },
    checkDetail() {
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
      } else this.$emit('detail');
    },
    unCheckDetail() {
      this.$emit('undetail');
    },
    navigate() {
      var animation = uni.createAnimation({
        duration: 100,
        timingFunction: 'ease',
      })
      animation.scale(0.8).step()
      this.animationNavigate = animation.export()
      setTimeout(() => {
        var animation2 = uni.createAnimation({
          duration: 100,
          timingFunction: 'ease',
        })
        animation2.scale(1).step()

        this.animationNavigate = animation2.export();
        this.$store.commit('setNavigateSelected', this.index);
        this.$emit('toLow');
      }, 100)


    },
    book() {
      if (this.uid == this.$store.state.uid) {
        wx.showToast({
          title: "禁止向自己预约",
          icon: 'error',
        })
        return;
      }
      this.buttonRotate = 180;
      this.buttonOpacitty = 0;
      var animation = uni.createAnimation({
        duration: 100,
        timingFunction: 'ease',
      })
      animation.scale(0.8).step()
      this.animationBook = animation.export()
      setTimeout(() => {
        var animation2 = uni.createAnimation({
          duration: 100,
          timingFunction: 'ease',
        })
        animation2.scale(1).step()

        this.animationBook = animation2.export();
      }, 100)
      setTimeout(() => {
        this.height = 600;
      }, 200)

      setTimeout(() => {
        this.bookRotate = 0;
      }, 400)

    },
    unbook() {
      this.bookRotate = -90;
      setTimeout(() => {
        this.height = 300;
      }, 100)
      setTimeout(() => {
        this.buttonRotate = 0;
        this.buttonOpacitty = 1;
      }, 400)
    },
    orderDetail() {
      var animation = uni.createAnimation({
        duration: 100,
        timingFunction: 'ease',
      })
      animation.scale(0.8).step()
      this.animationDetail = animation.export()
      setTimeout(() => {
        var animation2 = uni.createAnimation({
          duration: 100,
          timingFunction: 'ease',
        })
        animation2.scale(1).step()

        this.animationDetail = animation2.export();
        uni.navigateTo({
          url: '../detail/detail?cid=' + this.cid,
        })
      }, 100)

    },
    chat() {
      if (this.uid == this.$store.state.uid) {
        wx.showToast({
          title: "禁止联系自己",
          icon: 'error',
        })
        return;
      }
      var animation = uni.createAnimation({
        duration: 100,
        timingFunction: 'ease',
      })
      animation.scale(0.8).step()
      this.animationContact = animation.export()
      setTimeout(() => {
        var animation2 = uni.createAnimation({
          duration: 100,
          timingFunction: 'ease',
        })
        animation2.scale(1).step()

        this.animationContact = animation2.export();
        uni.navigateTo({
          url: '../communication/chat?toUid=' + this.uid,
        })
      }, 100)
    },
    bookDetail() {
      uni.navigateTo({
        url: '../detail/detail?cid=' + this.cid,
      })
    },
    bookOrder() {
      wx.showModal({
        content: '确定预约吗？',
        success: (res) => {
          if (res.confirm) {
            let time1 = this.text1.split(':')
            let time2 = this.text2.split(':')
            if (time1.length != 2 || time2.length != 2) {
              wx.showToast({
                title: "时间不合法！",
                icon: 'error',
              })
              return;
            }
            if (Number(time2[0]) * 60 + Number(time2[1]) - Number(time1[0]) * 60 - Number(time1[1]) < 30) {
              wx.showToast({
                title: "预约时间过短！",
                icon: 'error',
              })
              return;
            }
            if (Number(this.possiblePrice) < 0.1) {
              wx.showToast({
                title: "预约金额过少！",
                icon: 'error',
              })
              return;
            }
            wx.showLoading({
              title: "请稍候",
              mask: true
            })
            wx.cloud.callFunction({ //查询我是否有未完成的订单以及该电桩是否可用
              name: 'orderNum',
              data: {
                uid: this.$store.state.uid,
                cid: this.cid
              }
            }).then(res => {
              if (res.result == 1) {
                wx.cloud.callFunction({
                  name: 'orderPay',
                  data: {
                    predictedPrice: Math.round(this.possiblePrice * 100)
                  },
                  success: res => {
                    console.log(res)
                    if (res.result.returnCode == "SUCCESS" && res.result.resultCode == "SUCCESS") {
                      var outTradeNo = res.result.outTradeNo;
                      const payment = res.result.payment
                      wx.requestPayment({
                        ...payment,
                        success: (res) => {
                          wx.cloud.callFunction({
                            name: 'payQuery',
                            data: {
                              outTradeNo: outTradeNo,
                            }
                          }).then(res => {
                            var transactionId = res.result.transactionId;
                            var timeStamp = new Date().getTime();
                            wx.cloud.callFunction({ //输入订单
                              name: 'orderInput',
                              data: {
                                status: 0,
                                uid: Number(this.$store.state.uid),
                                toUid: Number(this.uid),
                                cid: Number(this.cid),
                                timeStamp: timeStamp,
                                startTime: this.text1,
                                endTime: this.text2,
                                address: this.address,
                                location: this.location,
                                predictedPrice: this.possiblePrice,
                                outTradeNo: outTradeNo,
                                transactionId: transactionId,
                              }
                            }).then(
                                res => {
                                  var oid = res.result;
                                  wx.hideLoading()
                                  wx.showToast({
                                    title: "预约成功！",
                                    icon: 'success',
                                    complete: () => {
                                      setTimeout(() => {
                                        uni.navigateTo({
                                          url: '../communication/chat?toUid=' +
                                              this.uid,
                                          success: (res) => {
                                            res.eventChannel
                                                .emit('bookOrder', {
                                                      data: {
                                                        oid: oid,
                                                        cid: this.cid,
                                                        longitude: this.longitude,
                                                        latitude: this.latitude,
                                                        address: this.address,
                                                        location: this.location,
                                                        price: this.possiblePrice,
                                                        timeStamp: timeStamp,
                                                        startTime: this.text1,
                                                        endTime: this.text2,
                                                      }
                                                    }
                                                )
                                          }
                                        });
                                        this.$store.commit('setRefresh') //更新order
                                      }, 500)
                                    }
                                  })
                                }
                            )
                          })
                        },
                        fail(err) {
                          wx.hideLoading()
                          wx.showToast({
                            title: "支付失败！",
                            icon: 'error',
                            complete: () => {

                            }
                          })
                        }
                      })
                    } else {
                      wx.hideLoading()
                      wx.showToast({
                        title: "预约失败！",
                        icon: 'error',
                      })
                    }

                  },
                  fail: wx.hideLoading(),
                })
              } else if (res.result == -1) {
                wx.hideLoading()
                wx.showToast({
                  title: "您有订单未处理",
                  icon: 'error',
                })
              } else if (res.result == -2) {
                wx.hideLoading()
                wx.showToast({
                  title: "该电桩已被预约",
                  icon: 'error',
                })
              }
            })
          }
        }
      })

    }
  },
  watch: {
    'detail'() {
      if (this.detail == false) {
        this.$nextTick(function () {
          this.rotate = 0;
          this.bookRotate = -90;
          this.height = 300;
          this.buttonRotate = 0;
          this.buttonOpacitty = 1;
        })

      } else if (this.detail == true) {
        this.$nextTick(function () {
          this.rotate = -90;
        })

      }
    },
    'cid': { //监听cid，用于实时更新预约picker
      immediate: true,
      handler() {
        this.text1 = '起始时间'
        this.text2 = '结束时间'
        var tempDate = new Date();
        var days = tempDate.getDay();
        if (days == 0) {
          days = 7;
        }
        if (this.time[days - 1] != "") {
          var minMinutes = tempDate.getHours() * 60 + tempDate.getMinutes() + 30
          var minHours = Math.floor(minMinutes / 60) + ':' + minMinutes % 60
          var showTime = this.time[days - 1].split("-")
          this.minTime1 = this.minTime2 = minHours
          this.maxTime1 = this.maxTime2 = showTime[1]
        }
      }
    }
  },
  mounted() {
    let info = uni.createSelectorQuery().in(this).select("#box");
    info.boundingClientRect((data) => {
      this.width = data.width - uni.upx2px(16);
    }).exec(function (res) {

    })
    var tempDate = new Date();
    var days = tempDate.getDay();
    if (days == 0) {
      days = 7;
    }
    if (this.time[days - 1] != "") {
      var minMinutes = tempDate.getHours() * 60 + tempDate.getMinutes() + 30
      var minHours = Math.floor(minMinutes / 60) + ':' + minMinutes % 60
      var showTime = this.time[days - 1].split("-")
      this.minTime1 = this.minTime2 = minHours
      this.maxTime1 = this.maxTime2 = showTime[1]
    }
  }
}
</script>

<style scoped>
.card {
  position: absolute;
  background-color: white;
  border-radius: 32upx;
  transition: all .5s;
  top: 9upx;
  left: 8upx;
  height: 284upx;
}

.view1 {
  display: flex;
  justify-content: space-between;
}

.view2 {
  display: flex;
  justify-content: space-between;
  margin: 15upx;
  margin-bottom: 0;
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

.distance {
  margin-right: 15upx;
  color: rgba(102, 205, 170, 1);
}

.yuan {
  font-weight: 700;
  color: #219779;
}

.price {
  font-size: 40upx;
  font-weight: 700;
  color: #219779;
}

.time {
  font-size: 35upx;
  font-weight: 700;
  color: #219779;
  letter-spacing: 3upx;
}

.timeview {
  margin-top: 15upx;
}

.image {
  width: 40upx;
  height: 40upx;
  position: relative;
  top: 11upx;
  right: 5upx;

}

.image1 {
  position: absolute;
  width: 80upx;
  height: 80upx;
  transition: all .5s;
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

.smalldetail {
  font-size: 25upx;
  letter-spacing: 0.6upx;
  margin-left: 38upx;
  position: relative;
  bottom: 10upx;
}

.smalldetailview {
  height: 180upx;
  display: flex;
  flex-direction: column;
  justify-content: center;

}

.time1 {
  height: 56upx;
  width: 144upx;
  border-radius: 12upx;
  position: relative;
  bottom: 1upx;
  left: 30upx;
  background-color: rgb(230, 230, 230);
}

.timetext1 {
  position: relative;
  top: 10upx;
  left: 18upx;
  font-size: 27upx;
  letter-spacing: 1upx;
}

.timetext2 {
  position: relative;
  top: 10upx;
  left: 18upx;
  font-size: 27upx;
  letter-spacing: 1upx;
}

.line {
  position: relative;
  top: 2upx;
  left: 45upx;
}

.time2 {
  height: 56upx;
  width: 144upx;
  border-radius: 12upx;
  position: relative;
  bottom: 1upx;
  left: 60upx;
  background-color: rgb(230, 230, 230);
}

.priceview {
  margin-right: 30upx;
  margin-top: 15upx;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
</style>
