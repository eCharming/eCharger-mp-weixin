<template>
  <view style="margin: 30upx;padding: 25upx;border-radius: 40upx;
	position: relative;transition: all .5s;border: 8upx solid rgb(50,200,210);">
    <view
        style="font-size: 32upx;font-weight: 700;letter-spacing: 1upx;margin-bottom: 15upx;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
      {{ address }}
    </view>
    <view style="font-size: 25upx;font-weight: 600;margin-bottom: 15upx;color: rgba(0,0,0,0.5);
			overflow: hidden;-webkit-line-clamp: 3;text-overflow: ellipsis;display: -webkit-box;">
      {{ location }}
    </view>
    <view style="margin-bottom: 15upx;">
      <text style="font-size: 25upx;font-weight: 600;color: rgba(0,0,0,0.5);">
        下单时间：
      </text>
      <text style="font-size: 25upx;letter-spacing: 1upx;color: rgba(0,0,0,0.5);font-weight: 600;">
        {{ timeText }}
      </text>
    </view>
    <view style="margin-bottom: 15upx;">
      <text style="font-size: 25upx;font-weight: 600;color: rgba(0,0,0,0.5);">
        剩余时间：
      </text>
      <text style="font-size: 25upx;letter-spacing: 1upx;color: rgba(0,0,0,0.5);font-weight: 600;">
        {{ timeRemain }}
      </text>
    </view>
    <view style="margin-bottom: 15upx;">
      <text style="font-size: 25upx;font-weight: 600;color: rgba(0,0,0,0.5);">
        预约时间：
      </text>
      <text style="font-size: 32upx;font-weight: 700;color:rgb(50,200,210) ;letter-spacing: 1upx;">
        {{ startTime }}-{{ endTime }}
      </text>
    </view>
    <view style="margin-bottom: 15upx;">
      <text style="font-size: 25upx;font-weight: 600;color: rgba(0,0,0,0.5);">
        预估价格：
      </text>
      <text style="font-size: 32upx;font-weight: 700;color:rgb(50,200,210) ;letter-spacing: 1upx;">
        ￥{{ predictedPrice }}
      </text>
    </view>
    <view style="margin-bottom: 15upx;">
      <text style="font-size: 25upx;font-weight: 600;color: rgba(0,0,0,0.5);">
        状态：
      </text>
      <text style="font-size: 32upx;font-weight: 700;letter-spacing: 1upx;" :style="{'color':statusColor}">
        {{ statusText }}
      </text>
    </view>
    <view style="display: flex;justify-content: center;">
      <view style="width: 450upx;display: flex;justify-content: space-between;">
        <view style="background-color:rgb(50,200,210);height: 70upx;width: 170upx;color: white;
					font-size: 28upx;font-weight: 700;letter-spacing: 2upx;border-radius: 25upx;
					display: flex;justify-content: center;align-items: center;" @tap="detail()">
          电桩详情
        </view>
        <view style="height: 70upx;width: 170upx;color: white;
					font-size: 28upx;font-weight: 700;letter-spacing: 2upx;border-radius: 25upx;
					display: flex;justify-content: center;align-items: center;" :style="{'background-color':checkColor}"
              @tap="check">
          {{ checkStatus }}
        </view>
      </view>

    </view>
    <image src="../static/image/orderHistory-blue.png"
           style="width: 300upx;height: 300upx;position: absolute;right: -10upx;bottom: 120upx;opacity: 0.3;"></image>
  </view>
</template>

<script>
export default {
  data() {
    return {
      status: '',
      statusText: '',
      statusColor: '',
      checkStatus: '',
      checkColor: '',
      timeText: '',
      timeCount: null,
      socketTask: null,
      timeRemain: '',
    }
  },
  props: {
    uid: {
      type: String
    },
    toUid: {
      type: String
    },
    cid: {
      type: String
    },
    oid: {
      type: String
    },
    address: {
      type: String
    },
    location: {
      type: String
    },
    startTime: {
      type: String
    },
    endTime: {
      type: String
    },
    timeStamp: {
      type: String
    },
    predictedPrice: {
      type: String
    },
    statusContext: {
      type: String
    },
  },
  methods: {
    detail() {
      uni.navigateTo({
        url: '../detail/detail?cid=' + this.cid,
      })
    },
    check() {
      if (this.status == 0) {
        wx.showModal({
          content: '确定预约？',
          success: (res) => {
            if (res.confirm) {
              wx.showLoading({
                title: "请稍候",
                mask: true
              })
              this.status = 1;
              this.timeRemain = '订单完成';
              this.checkStatus = '预约已确定';
              this.checkColor = 'rgba(0,0,0,0.5)';
              this.statusText = '预约已确定';
              this.statusColor = 'rgb(50,200,210)';

              wx.cloud.callFunction({
                name: 'paySharing',
                data: {
                  oid: this.oid,
                },
                success: res => {
                  wx.cloud.callFunction({ //更改order的状态
                    name: 'orderStatusChange',
                    data: {
                      oid: this.oid,
                      status: 1,
                    }
                  }).then(res => {
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
                            wx.hideLoading();
                            wx.showToast({
                              title: "已确定预约",
                              icon: 'success',
                              complete: () => {
                                setTimeout(() => {
                                  uni.navigateTo({
                                    url: '../communication/chat?toUid=' + this.uid,
                                    success: (res) => {
                                      res.eventChannel.emit('sendStatus', {
                                        data: {
                                          message: '已确定预约'
                                        }
                                      })
                                    }
                                  });
                                }, 500)
                              }
                            })
                          }
                        });
                      }
                    })

                  })
                }
              })


            }
          }
        })


      }
    }
  },
  mounted() {
    this.status = this.statusContext;
    var time = new Date(Number(this.timeStamp));
    var year = time.getFullYear();
    var month = time.getMonth() + 1 < 10 ? "0" + (time.getMonth() + 1) : time.getMonth() + 1;
    var date = time.getDate() < 10 ? "0" + time.getDate() : time.getDate();
    var hh = time.getHours() < 10 ? "0" + time.getHours() : time.getHours();
    var mm = time.getMinutes() < 10 ? "0" + time.getMinutes() : time.getMinutes();
    var ss = time.getSeconds() < 10 ? "0" + time.getSeconds() : time.getSeconds();
    this.timeText = year + '年' + month + '月' + date + '日' + hh + ':' + mm + ':' + ss;
    if (this.status == -2) {
      this.timeRemain = '订单完成';
      this.checkStatus = '预约已超时';
      this.checkColor = 'rgba(0,0,0,0.5)';
      this.statusText = '预约已超时';
      this.statusColor = 'rgba(0,0,0,0.5)';
    } else if (this.status == -1) {
      this.timeRemain = '订单完成';
      this.checkStatus = '预约已取消';
      this.checkColor = 'rgba(0,0,0,0.5)';
      this.statusText = '预约已取消';
      this.statusColor = '#be0e0e';
    } else if (this.status == 0) {
      this.checkStatus = '确定预约';
      this.checkColor = 'rgb(50,200,210)';
      this.statusText = '预约已发起';
      this.statusColor = 'rgb(50,200,210)';


      this.timeCount = setInterval(() => {
        if (this.status == 0) {
          var currentTime = new Date().getTime();
          var count = 1800 - ((currentTime - this.timeStamp) / 1000).toFixed(0);
          var mm = parseInt(count / 60);
          var ss = count % 60;

          this.timeRemain = (mm < 10 ? '0' + mm : mm) + ':' + (ss < 10 ? '0' + ss : ss);
          if (count <= 0) {
            this.status = -2;
            this.timeRemain = '订单完成';
            this.checkStatus = '预约已超时';
            this.checkColor = 'rgba(0,0,0,0.5)';
            this.statusText = '预约已超时';
            this.statusColor = 'rgba(0,0,0,0.5)';
            clearInterval(this.timeCount);
            this.timeCount = null;
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

      this.socketTask = uni.connectSocket({		//打开链接
        url: 'wss://ws.healtool.cn/websocketapi/Order/' + this.oid + '/' + this.toUid + '/' + this.uid,
        // url:'ws://127.0.0.1:8080/websocketapi/Order/'+this.oid+'/'+uid+'/'+toUid,
        success: () => {

        }
      });
      this.socketTask.onMessage((res) => {
        var status = (JSON.parse(res.data)).message;
        this.status = status;
        if (this.status == -1) {	//我向对方发起预约 电桩是对方的 按下则为取消预约
          this.timeRemain = '订单完成';
          this.checkStatus = '预约已取消';
          this.checkColor = 'rgba(0,0,0,0.5)';
          this.statusText = '预约已取消';
          this.statusColor = '#be0e0e';

        } else if (this.status == 1) {		//对方向我发起预约 电桩是自己的 按下则为确定预约
          this.timeRemain = '订单完成';
          this.checkStatus = '预约已确定';
          this.checkColor = 'rgba(0,0,0,0.5)';
          this.statusText = '预约已确定';
          this.statusColor = 'rgb(50,200,210)';
        }
        this.socketTask.close({
          success: () => {
            this.socketTask = null;
          }
        });
      });


    } else if (this.status == 1) {
      this.timeRemain = '订单完成';
      this.checkStatus = '预约已确定';
      this.checkColor = 'rgba(0,0,0,0.5)';
      this.statusText = '预约已确定';
      this.statusColor = 'rgb(50,200,210)';
    }
  },
  destroyed() {
    if (this.timeCount != null) {
      clearInterval(this.timeCount);
    }
    if (this.socketTask != null) {
      this.socketTask.close();
    }
  }
}
</script>

<style scoped>
</style>

