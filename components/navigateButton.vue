<template>
  <view>
    <view>
      <image :src="src" style="width: 100rpx;height:100rpx;transition: opacity .3s;margin:20rpx"
             :style="{'pointer-events':pointerEvents,'opacity':opacity}" @click="unnavigate"></image>
    </view>
  </view>
</template>

<script>
export default {
  name: "navigateButton",
  data() {
    return {
      src: "../static/image/navigate.png",
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
    unnavigate() {
      if (this.$store.state.isNavigate == true) {
        this.$store.commit('refreshPolyline');
        this.$store.commit('setIsNavigate', false);
      }
    }
  },
  watch: {
    '$store.state.buttonSelected'() {
      if (this.$store.state.buttonSelected == 1) {
        this.src = "../static/image/navigate.png";
      } else {
        this.src = "../static/image/navigate_blue.png";
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
