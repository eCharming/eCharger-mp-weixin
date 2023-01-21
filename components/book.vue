<template>
  <view>
    <view class="card">
      <view>
        <view style="width: 400upx;font-size: 32upx;font-weight: 700;white-space: nowrap;
				overflow: hidden;text-overflow: ellipsis;margin-bottom: 25upx;color: rgba(102,205,170,1);">{{ address }}
        </view>
        <view style="width: 400upx;overflow : hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-line-clamp: 2;
				-webkit-box-orient: vertical;margin-bottom: 25upx;color: rgba(0,0,0,0.5);">{{ location }}
        </view>
        <image style="width: 420upx;height: 315upx;margin-bottom: 25upx;" :src="imageUrl"></image>

        <view style="display: flex;margin-bottom: 25upx;">
          <view style="color: rgba(0,0,0,0.5);">下单时间：</view>
          <view style="font-weight: 600;color:rgba(0,0,0,0.5) ;letter-spacing: 1upx;">{{ bookTimeText }}</view>
        </view>
        <view style="display: flex;margin-bottom: 25upx;" v-if="status==0">
          <view style="color: rgba(0,0,0,0.5);">剩余时间：</view>
          <view style="font-weight: 600;color:rgba(0,0,0,0.5) ;letter-spacing: 1upx;">{{ timeRemain }}</view>
        </view>
        <view style="display: flex;margin-bottom: 25upx;">
          <view style="color: rgba(0,0,0,0.5);">预约时间：</view>
          <view style="font-size: 35upx;font-weight: 700;color:rgba(102,205,170,1) ;letter-spacing: 3upx;">
            {{ startTime }}-{{ endTime }}
          </view>
        </view>
        <view style="display: flex;margin-bottom: 25upx;">
          <view style="color: rgba(0,0,0,0.5);">预估价格：</view>
          <view style="font-size: 35upx;font-weight: 700;color:rgba(102,205,170,1) ;letter-spacing: 3upx;">
            ￥{{ price }}
          </view>
        </view>

        <view style="width: 100%;height: 70upx;display: flex;justify-content: center;align-items: center;">
          <view style="width: 400upx;display: flex;justify-content: space-between;">
            <view style="height: 70upx;width: 170upx;background-color:rgba(102,205,170,1);
						color: white;font-size: 28upx;font-weight: 700;letter-spacing: 2upx;border-radius: 25upx;
						display: flex;justify-content: center;align-items: center;" @tap="detail()">
              <view>电桩详情</view>
            </view>
            <view style="height: 70upx;width: 170upx;color: white;
						font-size: 28upx;font-weight: 700;letter-spacing: 2upx;border-radius: 25upx;
						display: flex;justify-content: center;align-items: center;" :style="{'background-color':color}"
                  @tap="check()">
              <view>{{ statusText }}</view>
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
    message: {
      type: Object
    }
  },
  data() {
    return {
      status: 0,
      oid: 0,
      cid: 0,
      uid: 0,
      toUid: 0,
      longitude: '',
      latitude: '',
      address: '',
      location: '',
      price: '',
      timeStamp: '',
      startTime: '',
      endTime: '',
      imageUrl: '',
      statusText: '',
      bookTimeText: '',
      timeRemain: '',
      color: 'rgba(102,205,170,1)',
      timeCount: '',//倒计时计时器
      socketTask: null,
    }
  },
  methods: {
    detail() {
      uni.navigateTo({
        url: '../detail/detail?cid=' + this.cid,
      })
    },
    check() {
      if (this.status == 0) {		//状态码为0 也即预约订单正在等待桩主确定
        if (this.uid == this.$store.state.uid) {	//我向对方发起预约 电桩是对方的 按下则为取消预约

          wx.showModal({
            content: '确定取消预约？',
            success: (res) => {
              if (res.confirm) {
                wx.showLoading({
                  title: "请稍候",
                  mask: true
                })
                wx.cloud.callFunction({
                  name: 'orderRefund',
                  data: {
                    oid: this.oid,
                  },
                  success: (res) => {
                    console.log(res)
                    if (res.result.returnCode == "SUCCESS" && res.result.resultCode == "SUCCESS") {
                      wx.cloud.callFunction({ //更改order的状态
                        name: 'orderStatusChange',
                        data: {
                          oid: this.oid,
                          status: -1,
                        }
                      }).then(res => {
                        wx.hideLoading();
                        wx.showToast({
                          title: "预约取消成功！",
                          icon: 'success',
                          complete: () => {

                          }
                        })
                        this.$emit('changeOrderStatus', -1);
                        this.$emit('orderText', '已取消预约');
                        this.status = -1;
                        this.statusText = '已取消预约';
                        this.color = 'rgba(0,0,0,0.6)';
                        var data = {
                          oid: this.oid,
                          uid: this.uid,
                          toUid: this.toUid,
                          message: '-1'
                        };
                        data = JSON.stringify(data);
                        this.socketTask.send({
                          data: data,
                          success: () => {
                            this.socketTask.close({
                              success: () => {
                                this.socketTask = null;
                              }
                            });
                          }
                        })
                      })

                    } else {
                      wx.showToast({
                        title: "预约取消失败！",
                        icon: 'error',
                        complete: () => {

                        }
                      })
                    }

                  }
                })
              }
            }
          })


        } else {		//对方向我发起预约 电桩是自己的 按下则为确定预约
          wx.showModal({
            content: '确定预约？',
            success: (res) => {
              if (res.confirm) {
                wx.showLoading({
                  title: "请稍候",
                  mask: true
                })
                wx.cloud.callFunction({
                  name: 'paySharing',
                  data: {
                    oid: this.oid,
                  },
                  success: (res) => {

                    wx.cloud.callFunction({ //更改order的状态
                      name: 'orderStatusChange',
                      data: {
                        oid: this.oid,
                        status: 1,
                      }
                    }).then(res => {
                      console.log(res)
                    });
                    wx.hideLoading();
                    wx.showToast({
                      title: "预约取消成功！",
                      icon: 'success',
                      complete: () => {

                      }
                    })
                    this.$emit('changeOrderStatus', 1);
                    this.$emit('orderText', '已确定预约');
                    this.status = 1;
                    this.statusText = '已确定预约';
                    this.color = 'rgba(0,0,0,0.6)';
                    var data = {
                      oid: this.oid,
                      uid: this.uid,
                      toUid: this.toUid,
                      message: '1'
                    };
                    data = JSON.stringify(data);
                    this.socketTask.send({
                      data: data,
                      success: () => {
                        this.socketTask.close({
                          success: () => {
                            this.socketTask = null;
                          }
                        });
                      }
                    })
                  },
                });

              }
            }
          })


        }
      }
    }
  },
  mounted() {
    this.status = this.message.status;
    this.oid = this.message.oid;
    this.cid = this.message.cid;
    this.uid = this.message.uid;
    this.toUid = this.message.toUid;
    this.longitude = this.message.longitude;
    this.latitude = this.message.latitude;
    this.address = this.message.address;
    this.location = this.message.location;
    this.price = this.message.price;
    this.timeStamp = this.message.timeStamp;
    this.startTime = this.message.startTime;
    this.endTime = this.message.endTime;
    this.imageUrl = 'https://apis.map.qq.com/ws/staticmap/v2/?key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL&size=200x150&center='
        + this.latitude + ',' + this.longitude + '&markers=' + this.latitude + ',' + this.longitude + '&zoom=14';
    var time = new Date(Number(this.timeStamp));
    var year = time.getFullYear();
    var month = time.getMonth() + 1 < 10 ? "0" + (time.getMonth() + 1) : time.getMonth() + 1;
    var date = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
    var hh = time.getHours() < 10 ? "0" + time.getHours() : time.getHours();
    var mm = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();
    var ss = time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds();
    this.bookTimeText = month + '月' + date + '日' + hh + ':' + mm + ':' + ss;
    if (this.status == -2) {		//状态码为-2 也即预约订单超时
      this.statusText = '预约已过期';
      this.color = 'rgba(0,0,0,0.6)';
    } else if (this.status == -1) {		//状态码为-1 也即预约订单被取消
      if (this.uid == this.$store.state.uid) {
        this.statusText = '已取消预约';
        this.color = 'rgba(0,0,0,0.6)';
      } else {
        this.statusText = '对方已取消';
        this.color = 'rgba(0,0,0,0.6)';
      }
    } else if (this.status == 0) {		//状态码为0 也即预约订单正在等待桩主确定
      if (this.uid == this.$store.state.uid) {	//我向对方发起预约 电桩是对方的
        this.statusText = '取消预约'
        this.color = '#be0e0e'
      } else {		//对方向我发起预约 电桩是自己的
        this.statusText = '确定预约'
        this.color = 'rgba(102,205,170,1)';
      }

      var currentTime = new Date().getTime();
      var count = 1800 - ((currentTime - this.timeStamp) / 1000).toFixed(0);
      if (count <= 0) {
        this.$emit('changeOrderStatus', -2);
        this.status = -2;
        this.statusText = '预约已过期';
        this.color = 'rgba(0,0,0,0.6)';

        wx.cloud.callFunction({ //查询order的状态
          name: 'orderStatusQuery',
          data: {
            oid: this.oid,
          }
        }).then(res => {

          if (res.result != -2) {
            this.status = res.result;
            this.$emit('changeOrderStatus', this.status);
            if (this.status == 1) {
              if (this.uid != this.$store.state.uid) {
                this.statusText = '已确定预约';
                this.color = 'rgba(0,0,0,0.6)';
              } else {
                this.statusText = '对方已确定';
                this.color = 'rgba(0,0,0,0.6)';
              }
            } else if (this.status == -1) {
              if (this.uid == this.$store.state.uid) {
                this.statusText = '已取消预约';
                this.color = 'rgba(0,0,0,0.6)';
              } else {
                this.statusText = '对方已取消';
                this.color = 'rgba(0,0,0,0.6)';
              }
            }
          }

        });
      } else {
        wx.cloud.callFunction({ //查询order的状态
          name: 'orderStatusQuery',
          data: {
            oid: this.oid,
          }
        }).then(res => {

          if (res.result != -2) {
            this.status = res.result;
            this.$emit('changeOrderStatus', this.status);
            if (this.status == 1) {
              if (this.uid != this.$store.state.uid) {
                this.statusText = '已确定预约';
                this.color = 'rgba(0,0,0,0.6)';
              } else {
                this.statusText = '对方已确定';
                this.color = 'rgba(0,0,0,0.6)';
              }
            } else if (this.status == -1) {
              if (this.uid == this.$store.state.uid) {
                this.statusText = '已取消预约';
                this.color = 'rgba(0,0,0,0.6)';
              } else {
                this.statusText = '对方已取消';
                this.color = 'rgba(0,0,0,0.6)';
              }
            } else if (this.status == 0) {
              var uid = this.uid;
              var toUid = this.toUid;
              if (this.uid != this.$store.state.uid) {
                uid = this.toUid;
                toUid = this.uid;
              }
              this.socketTask = uni.connectSocket({		//打开链接
                url: 'wss://ws.healtool.cn/websocketapi/Order/' + this.oid + '/' + uid + '/' + toUid,
                // url:'ws://127.0.0.1:8080/websocketapi/Order/'+this.oid+'/'+uid+'/'+toUid,
                success: () => {

                }
              });

              this.socketTask.onMessage((res) => {
                var status = (JSON.parse(res.data)).message;
                this.status = status;
                if (this.status == -1) {	//我向对方发起预约 电桩是对方的 按下则为取消预约
                  this.$emit('changeOrderStatus', -1);
                  this.statusText = '对方已取消';
                  this.color = 'rgba(0,0,0,0.6)';

                } else {		//对方向我发起预约 电桩是自己的 按下则为确定预约
                  this.$emit('changeOrderStatus', 1);
                  this.statusText = '对方已确定';
                  this.color = 'rgba(0,0,0,0.6)';
                }
                this.socketTask.close({
                  success: () => {
                    this.socketTask = null;
                  }
                });
              });

              this.timeCount = setInterval(() => {
                if (this.status == 0) {
                  var currentTime = new Date().getTime();
                  var count = 1800 - ((currentTime - this.timeStamp) / 1000).toFixed(0);
                  var mm = parseInt(count / 60);
                  var ss = count % 60;
                  this.timeRemain = (mm < 10 ? '0' + mm : mm) + ':' + (ss < 10 ? '0' + ss : ss);
                  if (count <= 0) {
                    this.$emit('changeOrderStatus', -2);
                    this.status = -2;
                    this.statusText = '预约已过期';
                    this.color = 'rgba(0,0,0,0.6)';
                    clearInterval(this.timeCount);
                    this.timeCount = '';
                    if (this.socketTask != null) {
                      this.socketTask.close({
                        success: () => {
                          this.socketTask = null;
                        }
                      });
                    }
                  }
                } else clearInterval(this.timeCount);

              }, 1000);
            }
          }
        });
      }

    } else if (this.status == 1) {		//状态码为1 也即桩主确定了订单
      if (this.uid != this.$store.state.uid) {
        this.statusText = '已确定预约';
        this.color = 'rgba(0,0,0,0.6)';
      } else {
        this.statusText = '对方已确定';
        this.color = 'rgba(0,0,0,0.6)';
      }

    }
  },
  destroyed() {
    if (this.timeCount) {
      clearInterval(this.timeCount);
    }
    if (this.socketTask != null) {
      this.socketTask.close();
    }
  }
}
</script>

<style scoped>
.card {
  background-color: rgb(255, 255, 255);
  width: 500upx;
  border-radius: 25upx;
  padding: 40upx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
