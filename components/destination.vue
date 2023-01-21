<template>
  <view class="card">
    <view class="near">
      <text class="text1" :style="{'color':dayColor,'transition':'color 0.5s'}">{{ month }}月{{ date }}日星期{{ day }}
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

    <view class="destination"
          @tap="navigate()">
      <image :src="src1" :class="changeImg==0?'image1':'image1_none'"></image>
      <image :src="src2" :class="changeImg==0?'image1_none':'image1'"></image>
      <image class="image1" :src="src"></image>
      <text class="text2" :style="{'color':color}">{{ destination }}</text>

    </view>
  </view>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      month: 0,
      date: 0,
      day: 0,
      minTime1: '00:00',
      maxTime1: '24:00',
      minTime2: '00:00',
      maxTime2: '24:00',
      text1: this.$store.state.startTime,
      text2: this.$store.state.endTime,
      opacity1: this.$store.state.startTime == "起始时间" ? 0.5 : 1,
      opacity2: this.$store.state.endTime == "结束时间" ? 0.5 : 1,
      destination: "请输入你的目的地",
      color: "rgba(0,0,0,0.5)",
      dayColor: "rgb(102,205,170)",
      src1: "../static/image/lightning_green.png",
      src2: "../static/image/lightning_blue.png",
      changeImg: 0,
    }
  },
  methods: {
    changetime1(e) {
      var time1 = e.detail.value;
      this.text1 = time1;
      this.minTime2 = time1;
      this.opacity1 = 1;
      this.$store.commit('setStartTime', this.text1);
    },
    changetime2(e) {
      var time2 = e.detail.value;
      this.text2 = time2;
      this.maxTime1 = time2;
      this.opacity2 = 1;
      this.$store.commit('setEndTime', this.text2);
    },
    navigate() {
      uni.navigateTo({
        url: '../search/search',
      });
    }
  },
  mounted() {
    var date = new Date();
    this.month = date.getMonth() + 1;
    this.date = date.getDate();
    this.day = date.getDay();
    if (this.day === 0) this.day = "日";
    else if (this.day === 1) this.day = "一";
    else if (this.day === 2) this.day = "二";
    else if (this.day === 3) this.day = "三";
    else if (this.day === 4) this.day = "四";
    else if (this.day === 5) this.day = "五";
    else if (this.day === 6) this.day = "六";
  },
  watch: {
    '$store.state.destination'() {
      if (this.$store.state.destination != null) { //按下locationbutton重置回到自己位置，destination置为空
        this.destination = this.$store.state.destination.title;
        if (this.$store.state.buttonSelected == 1)
          this.color = "rgb(102,205,170)";
        else this.color = this.$store.state.color;
      } else {
        this.destination = "请输入你的目的地";
        this.color = "rgba(0,0,0,0.5)";
      }

    },
    '$store.state.buttonSelected'() {
      if (this.$store.state.buttonSelected == 1) {
        this.dayColor = "rgb(102,205,170)";
        this.changeImg = 0;
      } else {
        this.dayColor = this.$store.state.color;
        this.changeImg = 1;
      }
      if (this.color != "rgba(0,0,0,0.5)") {
        if (this.$store.state.buttonSelected == 1) {
          this.color = "rgb(102,205,170)";
        } else this.color = this.$store.state.color;
      }
    }
  }
}
</script>

<style scoped>
.image1 {
  position: absolute;
  top: 15upx;
  width: 60upx;
  height: 60upx;
  opacity: 1;
  transition: opacity 0.5s;
}

.image1_none {
  position: absolute;
  top: 15upx;
  width: 60upx;
  height: 60upx;
  opacity: 0;
  transition: opacity 0.5s;
}

.card {
  margin: 20upx;
  padding: 15upx;

  background-color: rgba(250, 255, 250, 1);
  border-radius: 40upx;
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-shadow: 0 12upx 24upx 8upx #e5e5e5;
}

.near {
  margin: 15upx 15upx 15upx 45upx;
  letter-spacing: 3upx;
  font-weight: 700;
  font-size: 31upx;
  display: flex;
}

.destination {
  border-radius: 20upx;
  margin: 5upx 25upx 15upx;
  padding: 23upx 23upx 15upx;
  background-color: rgb(230, 230, 230);
  position: relative;
  font-weight: 700;
  letter-spacing: 2upx;
  font-size: 35upx;
}

.text1 {
  transition: all .3s;
  position: relative;
  top: 10upx;
}

.text2 {
  transition: all .3s;
  display: inline-block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-left: 60upx;
  width: 450upx;
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

</style>
