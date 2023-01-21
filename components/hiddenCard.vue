<template>
  <view class="card" :style="{'top':top+'rpx','opacity':opacity}">
    <text class="text1">{{ text1 }}</text>
    <text class="text2" :style="{'color':color}">{{ text2 }}</text>
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
      text1: '在附近搜索电桩',
      text2: '',
      color: 'rgb(102,205,170)',
      top: -30,
      opacity: 0,
    }
  },
  watch: {
    'isLow'() {
      if (this.isLow) {
        this.top = -30;
        this.opacity = 0;
      } else {
        this.top = -130;
        this.opacity = 1;
      }
    },
    '$store.state.destination'() {
      if (this.$store.state.destination != null) {
        this.text1 = '距离目的地';
        if (this.$store.state.buttonSelected == 1)
          this.color = 'rgb(102,205,170)';
        else this.color = this.$store.state.color;
        this.text2 = '车程约' + this.$store.state.destination.distance + 'km';
      } else { //按下locationbutton重置回到自己位置，destination置为空
        this.text1 = "在附近搜索电桩";
        this.text2 = '';
      }
    },
    '$store.state.buttonSelected'() {
      if (this.$store.state.buttonSelected == 1) {
        this.color = "rgb(102,205,170)";
      } else this.color = this.$store.state.color;
    }
  }
}
</script>

<style scoped>
.card {
  position: absolute;
  margin: 20upx;
  padding: 20upx 15upx 15upx;
  background-color: rgba(250, 255, 250, 1);
  border-radius: 55upx;
  display: flex;
  justify-content: center;
  box-shadow: 0 14upx 20upx -6upx #a6a6a6;
  width: 550upx;
  height: 90upx;
  top: -30upx;
  left: 70upx;

  font-size: 32upx;
  letter-spacing: 2upx;
  font-weight: 700;
  transition: all .3s;

}

.text1 {
  margin: 10upx 10upx 20upx 20upx;
}

.text2 {
  margin: 10upx 20upx 20upx 10upx;
  transition: all .3s;
}
</style>