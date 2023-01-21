<template>
  <view class="card" :style="{'top':top+'rpx','opacity':opacity}">
    <text class="text1">订单详情</text>
  </view>
</template>

<script>
export default {
  props: {
    isLow: {
      type: Boolean
    },
  },
  data() {
    return {
      top: -130,
      opacity: 1,
    }
  },
  watch: {
    'isLow'() {
      if (this.isLow) {
        this.top = -130;
        this.opacity = 1;
      } else {
        this.top = -30;
        this.opacity = 0;
      }
    },
  }
}
</script>

<style scoped>
.card {
  position: absolute;
  margin: 20upx;
  padding: 15upx 20upx;
  background-color: rgba(250, 255, 250, 1);
  border-radius: 55upx;
  display: flex;
  justify-content: center;
  box-shadow: 0 14upx 20upx -6upx #a6a6a6;
  height: 90upx;
  top: -30upx;
  left: 235upx;

  font-size: 32upx;
  letter-spacing: 2upx;
  font-weight: 700;
  transition-property: all;
  transition-duration: .1s;
}

.text1 {
  margin: 10upx 10upx 20upx 20upx;
  color: rgba(102, 205, 170, 1);
}
</style>