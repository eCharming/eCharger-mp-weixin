<template>
  <view style="display: flex;flex-direction:column;justify-content: space-between;height:100vh;">
    <view>
      <view class="navigator" :style="{'height':statusHeight+'px'}">
        <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
               @tap="back"></image>
        <text :style="{'margin-bottom':myBottom+'px'}">我的</text>
      </view>

      <view class="avatar-view"
            style="height: 220rpx;display: flex;justify-content: center;align-items: center;margin: 20upx;">
        <view
            style="display: flex;flex-direction: column;align-items: center;color: rgba(0,0,0,0.6);font-size: 35rpx;">
          <image :src="avatarUrl" style="height: 130rpx;width: 130rpx;border-radius: 50%;margin: 20rpx;">
          </image>
          <text>{{ userName }}</text>
          <text style="font-size: 25rpx;">余额：{{ balance }}元</text>
        </view>
      </view>

      <addcard style='margin:10rpx;'>
        <view style="display: flex;justify-content: space-around;">
          <view @tap="navi(1)" class='funbutton'>
            <image src='../../static/image/historyOrder.png' style="width: 100upx;height:100upx;"></image>
            <view class='labeltext'>历史订单</view>
          </view>
          <view @tap="navi(2)" class='funbutton'>
            <image src='../../static/image/myCharger.png' style="width: 100upx;height:100upx;"></image>
            <view class='labeltext'>我的电桩</view>
          </view>
          <view @tap="navi(3)" class='funbutton'>
            <image src='../../static/image/faq.png' style="width: 100upx;height:100upx;"></image>
            <view class='labeltext'>常见问题</view>
          </view>
        </view>

      </addcard>

      <addcard style='margin:10rpx;'>
        <view class='labeltext' @tap="navi(7)">
          申请提现（Beta）
        </view>
        <view class='divLine'></view>
        <view class='labeltext' @tap="navi(6)">
          意见反馈
        </view>
        <view class='divLine'></view>
        <view class='labeltext' @tap="navi(5)">
          关于我们
        </view>
      </addcard>
    </view>

    <view>
      <button class="submit" @tap="logout">退出登录</button>
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
      uid: -1,
      avatarUrl: '',
      userName: '',
      myBottom: 0, //联系人距离导航栏底部距离
      statusHeight: 0, //导航栏高度
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight, //状态栏高度
      balance: 0,	//余额
    }
  },
  methods: {
    back() {
      uni.navigateBack({})
    },
    navi(index) {
      if (index === 1) {
        uni.navigateTo({
          url: '../orders/ordersHistory',
        });
      } else if (index === 2) {
        this.$store.commit('setMyCharger')
        uni.navigateBack({})
      } else if (index === 3) {
        uni.navigateTo({
          url: '../faq/faq',
        })
      } else if (index === 5) {
        uni.navigateTo({
          url: '../aboutUs/aboutUs',
        })
      } else if (index === 6) {
        uni.navigateTo({
          url: '../feedback/feedback',
        })
      } else if (index === 7) {
        uni.navigateTo({
          url: '../withdraw/withdraw?maxBalance=' + this.balance
        })
      }
    },
    logout() {
      wx.cloud.callFunction({ //uid获取
        name: 'logout',
        data: {
          uid: this.$store.state.uid
        }
      }).then(
          res => {
            this.$store.commit('setLogInStatus', null);
            wx.showToast({
              title: "退出登录成功！",
              icon: 'success',
              complete: () => {
                setTimeout(() => {
                  uni.navigateBack({})
                }, 1000)
              }
            })
          }
      )
    }
  },
  mounted() {
    this.statusHeight = uni.getSystemInfoSync().statusBarHeight + 50;
    this.myBottom = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
    this.avatarUrl = this.$store.state.avatarUrl;
    this.userName = this.$store.state.userName;
    wx.cloud.callFunction({
      name: 'getBalance',
      data: {
        uid: this.$store.state.uid
      }
    }).then(res => {
      this.balance = res.result / 100
    })
  },
  onShow() {
    wx.cloud.callFunction({
      name: 'getBalance',
      data: {
        uid: this.$store.state.uid
      }
    }).then(res => {
      this.balance = res.result / 100
    })
  }
}
</script>

<style scoped>
.navigator {
  background-color: rgba(102, 205, 170, 1);
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
  margin-top: 10upx;
  margin-bottom: 10upx;
}

.funbutton {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 30upx;
  margin-bottom: 30upx;
}

.divLine {
  width: 100%;
  height: 3upx;
  background-color: #E0E3DA;
  margin-top: 18rpx;
  margin-bottom: 18rpx;
}

.submit {
  background-color: rgba(102, 205, 170, 1);
  color: white;
  font-weight: 700;
  letter-spacing: 3upx;
  margin: 20upx;
  border-radius: 20upx;
}
</style>
