<template>
  <view>
    <view>
      <image :src="src" style="width: 100rpx;height:100rpx;transition: opacity .3s;margin:20rpx"
             :style="{'pointer-events':pointerEvents,'opacity':opacity}" @click="relocate"></image>
    </view>
  </view>
</template>

<script>
export default {
  name: "locationButton",
  data() {
    return {
      src: "../static/image/location.png",
      pointerEvents: 'auto',
      opacity: 1,
    };
  },
  props: {
    isLow: {
      type: Boolean,
    },
  },
  methods: {
    relocate() {
      wx.startLocationUpdate()
      uni.getLocation({
        type: "gcj02",
        success: res => {
          this.$store.commit('setLocationRes', res);
          this.$store.commit('setDestination', null);
          this.$store.commit('setCurrentLocation', {
            latitude: res.latitude,
            longitude: res.longitude
          });
          this.$store.commit('setCityLocation', {
            name: null,
            latitude: null,
            longitude: null
          })
        },
        fail: () => {
          this.$store.commit('setLocationRes', {});
        },
      });
      this.$store.state.relocate = true;
    }
  },
  watch: {
    '$store.state.buttonSelected'() {
      if (this.$store.state.buttonSelected == 1) {
        this.src = "../static/image/location.png";
      } else {
        this.src = "../static/image/location_blue.png";
      }
    },
    'isLow'() {
      if (this.isLow) {
        this.pointerEvents = 'auto';
        this.opacity = 1;
      } else {
        this.pointerEvents = 'none';
        this.opacity = 0;
      }
    }
  }
}
</script>

<style scoped>
</style>
