<template>
  <view style="display: flex;flex-direction:column">
    <view :style="{'height':statusHeight+'px','width':'100%','top':0,'z-index':9999}">
      <view class="cityNavi"
            :style="{'height':statusHeight+'px','background':color,'position':'fixed','width':'100%','top':0}">
        <image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
               @tap="back"></image>
        <text :style="{'margin-bottom':cityHeight+'px'}">常见问题</text>
      </view>
    </view>
    <addcard>
      <view style='width:100%' class='display'>
        <view class="problem">附近没有电桩怎么办？</view>
        <view class="divLine"></view>
        <view class="answer">可以尝试使用全城功能</view>
      </view>
    </addcard>
    <addcard>
      <view style='width:100%' class='display'>
        <view class="problem">为何预约不了可用时间内的电桩？</view>
        <view class="divLine"></view>
        <view class="answer">预约时间应在可用之间结束前30分钟内，若电桩将在30分钟内下线，则该电桩无法被再次预约</view>
      </view>
    </addcard>
    <addcard>
      <view style='width:100%' class='display'>
        <view class="problem">预约电桩后发现无法正常使用？</view>
        <view class="divLine"></view>
        <view class="answer">使用问题反馈，核实后我们将对本单进行退款并强制电桩下线</view>
      </view>
    </addcard>
    <addcard>
      <view style='width:100%' class='display'>
        <view class="problem">租金什么时候到账？</view>
        <view class="divLine"></view>
        <view class="answer">在确认该单正常的情况下，租金将很快支付到您的账户中</view>
      </view>
    </addcard>
    <addcard>
      <view style='width:100%' class='display'>
        <view class="problem">如何取消订单？</view>
        <view class="divLine"></view>
        <view class="answer">在预约订单后若未完成支付，将在15分钟后自动取消订单，若已完成支付则无法取消订单</view>
      </view>
    </addcard>
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
      statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
      cityHeight: 0,
      color: 'rgba(102,205,170,1)',
    }
  },
  methods: {
    back() {
      uni.navigateBack({})
    },
  },
  mounted() {
    this.cityHeight = (this.statusHeight - uni.getMenuButtonBoundingClientRect().bottom);
  }
}
</script>

<style scoped>
.cityNavi {
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

.divLine {
  width: 100%;
  height: 3upx;
  background-color: #E0E3DA;
  margin-top: 18rpx;
  margin-bottom: 18rpx;
}

.problem {
  margin: 10upx;
  letter-spacing: 2upx;
  font-weight: 700;
  color: #555555;
}

.answer {
  margin: 10upx;
  letter-spacing: 2upx;
  font-weight: 700;
}

.display {
  margin: 15upx;
}
</style>
