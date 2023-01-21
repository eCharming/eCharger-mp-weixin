<template>
  <view>
    <view class="orderHistoryNavi" :style="{'height':statusHeight+'px','background':doubleColor}">
      <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
             @tap="back"></image>
      <text :style="{'margin-bottom':orderHistoryHeight+'px'}">订单</text>
    </view>

    <view style="margin-top:30upx ;">
      <view>
        <view style="display: flex;">
          <view class="myOrderView">
            <text>我的订单</text>
          </view>
          <view class="borrowOrderView">
            <text>出租订单</text>
          </view>
        </view>
        <view style="margin-left: 30upx;width: 290upx;height: 17upx;display: flex;"
              :style="{'justify-content':justifyContent}">
          <view class="modelSelected"
                :style="{'width':modelWidth+'rpx','background-color':color}">
          </view>
        </view>
      </view>


      <swiper :style="{'height': (storageHeight+20)+'px'}" @animationfinish="animationfinish($event)"
              @transition="transition($event)">
        <swiper-item>
          <scroll-view scroll-y="true"
                       style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
                       :style="{'height': storageHeight+'px'}">

            <myOrderHistotry v-for="(order,index) in myOrder" :key="index" :cid="order.cid" :uid="order.uid"
                             :toUid="order.toUid" :oid="order.oid" :startTime="order.startTime" :endTime="order.endTime"
                             :predictedPrice="order.predictedPrice"
                             :timeStamp="order.timeStamp" :statusContext="order.status" :address="order.address"
                             :location="order.location"></myOrderHistotry>
            <view v-if="myOrder.length===0"
                  style="width:100%;height:100%;display: flex;align-items: center;justify-content: center;flex-direction: column;">
              <image src="/static/image/blank.png" style="width: 200rpx;height: 200rpx;"></image>
              <text style="font-weight: bold;color: rgba(102,205,170,1);">还没有预约订单</text>
            </view>
          </scroll-view>
        </swiper-item>
        <swiper-item>
          <scroll-view scroll-y="true"
                       style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
                       :style="{'height': (storageHeight)+'px'}">

            <borrowOrderHistory v-for="(order,index) in borrowOrder" :key="index" :cid="order.cid" :uid="order.uid"
                                :toUid="order.toUid" :oid="order.oid" :startTime="order.startTime"
                                :endTime="order.endTime" :predictedPrice="order.predictedPrice"
                                :timeStamp="order.timeStamp" :statusContext="order.status" :address="order.address"
                                :location="order.location"></borrowOrderHistory>
            <view v-if="borrowOrder.length===0"
                  style="width:100%;height:100%;display: flex;align-items: center;justify-content: center;flex-direction: column;">
              <image src="/static/image/blank_blue.png" style="width: 200rpx;height: 200rpx;"></image>
              <text style="font-weight: bold;color: rgba(50,200,210,1);">还没有出租订单</text>
            </view>
          </scroll-view>
        </swiper-item>
      </swiper>


    </view>
  </view>
</template>

<script>
import myOrderHistotry from '../../components/myOrderHistory.vue'
import borrowOrderHistory from '../../components/borrowOrderHistory.vue'

export default {
  components: {
    myOrderHistotry,
    borrowOrderHistory,
  },
  data() {
    return {
      statusHeight: uni.getSystemInfoSync().statusBarHeight + 50,
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
      windowWidth: uni.getSystemInfoSync().windowWidth,
      orderHistoryHeight: 0,
      color: 'rgba(102,205,170,1)',
      doubleColor: "linear-gradient(to right bottom,rgb(102,205,170) 0% 100%,rgb(50,200,210))",
      justifyContent: "flex-start",
      modelWidth: 120,
      storageHeight: this.$store.state.windowHeight * 0.9 - 10,
      currentPage: 0,
      myOrder: [],
      borrowOrder: [],
    }
  },
  methods: {
    back() {
      uni.navigateBack({})
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
        this.color = "rgba(" + (50 + 52 * percent) + "," + (200 + 5 * percent) + "," + (210 - 40 * percent) +
            ",1)";
        this.doubleColor = "linear-gradient(to right bottom,rgb(102,205,170) 0% " + percent * 100 + "%," + this
            .$store.state.color + ")";
      }
    },
  },
  onLoad() {
    this.orderHistoryHeight = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
    wx.cloud.callFunction({   //uid获取
      name: 'orderQuery',
      data: {
        uid: this.$store.state.uid
      }
    }).then(res => {
      //console.log(res)
      this.myOrder = res.result.myOrder;
      this.borrowOrder = res.result.borrowOrder;

    })
  }
}
</script>

<style scoped>
.orderHistoryNavi {
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

.borrowOrderView {
  margin-left: 50upx;
  border-radius: 8upx;
  font-size: 30upx;
  font-weight: 700;
  letter-spacing: 1upx;
}

.myOrderView {
  margin-left: 30upx;
  border-radius: 8upx;
  font-size: 30upx;
  font-weight: 700;
  letter-spacing: 1upx;
}

.modelSelected {
  border-radius: 10upx;
  transition: all .1s;
  height: 15upx;
}
</style>
