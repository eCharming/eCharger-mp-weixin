<template>
  <view style="display: flex;justify-content: center;align-items: center;height: 100vh;">

    <view
        style="position: absolute;height: 100vh;top: 0;width: 100%;perspective: 1000upx;perspective-origin: 50% 0%;transform: translateZ(400upx);">
      <view
          style="position: absolute;height: 100vh;top: 0;width: 100%;transform-origin:top;transition: all 1.5s;perspective: 1000upx;perspective-origin: 50% 25%;"
          :style="{'transform':'rotateX('+backRotate4+'deg)'}">


        <view
            style="position: absolute;height: 75vh;top: 25%;width: 100%;transition: all 1.5s;transform-origin:top;perspective: 1000upx;perspective-origin: 50% 33%;"
            :style="{'transform':'rotateX('+backRotate3+'deg)'}">


          <view
              style="position: absolute;height: 50vh;top: 33.3%;width: 100%;transform-origin:top;transition: all 1.5s;perspective: 1000upx;perspective-origin: 50% 50%;"
              :style="{'transform':'rotateX('+backRotate2+'deg)'}">

            <view style="background-color:rgba(0,185,199,1);height: 25vh;position: absolute;top: 50%;width: 100%;transform-origin:top;transition: all 1.5s;
						display: flex;justify-content: center;align-items: center;"
                  :style="{'transform':'rotateX('+backRotate1+'deg)'}"
            >
              <text style="color: white;font-size: 45upx;font-weight: 800;">eCharger</text>
            </view>

            <view
                style="background-color:rgba(0,185,179,1);height: 25vh;position: absolute;top: 0;width: 100%;transition: all 1s;"></view>

          </view>

          <view
              style="background-color:rgba(0,185,159,1);height: 25vh;position: absolute;top: 0;width: 100%;transition: all 1s;"></view>

        </view>

        <view style="background-color:rgba(0,185,139,1);height: 25vh;position: absolute;top: 0;width: 100%;transform-origin:top;transition: all 1s;
				display: flex;justify-content: center;align-items: center;">
          <text style="color: white;font-size: 45upx;font-weight: 800;">易插</text>
        </view>

      </view>
    </view>


    <view style="transform: translateZ(450upx);height: 700upx;width: 700upx;">
      <view style="height: 700upx;width: 700upx;clip-path: polygon(25% 53%, 50% 0, 81% 0, 53% 40%, 77% 40%, 33% 100%, 52% 53%);position: absolute;
			transition: all .7s cubic-bezier(.52,-0.46,.46,1.01);background-color: #f7f14b;"
            :style="{'transform':'scale('+scale+')'}"
      >
        <!-- <view class="lightning" style="height: 750upx;width: 750upx;background-color: #f7f14b;"></view> -->
        <view class="view1" :style="{'bottom':bottom+'rpx','transform':'rotate('+rotate+'deg)'}"></view>
        <view class="view2" :style="{'bottom':bottom+'rpx','transform':'rotate('+rotate+'deg)'}"></view>

      </view>
    </view>


  </view>
</template>

<script>
export default {
  data() {
    return {
      rotate: 0,
      bottom: 0,
      backRotate1: 0,
      backRotate2: 0,
      backRotate3: 0,
      backRotate4: 0,
      isAvailable: true,
      scale: 1,
    }
  },
  mounted() {
    setTimeout(() => {
      this.rotate = 360;
      this.bottom = 750;

    }, 400)
    setTimeout(() => {

      this.scale = 0;
    }, 4300)
    setTimeout(() => {

      this.backRotate1 = -180;
    }, 4800)
    setTimeout(() => {

      this.backRotate2 = -180;
    }, 5250)
    setTimeout(() => {

      this.backRotate3 = -180;
    }, 5700)
    setTimeout(() => {

      this.backRotate4 = -180;
    }, 6150)
  }

}
</script>

<style scoped>
.view1 {
  position: absolute;
  height: 750upx;
  width: 750upx;
  background-color: #e5e39e;
  border-radius: 52% 25% 62% 52% / 25% 38%;
  transition: 5s all;
}

.view2 {
  position: absolute;
  height: 750upx;
  width: 750upx;
  background-color: #f8f8dd;
  border-radius: 70% 30% 65% 35% / 37% 46% 54% 63%;
  transition: 5s all;
}

</style>
