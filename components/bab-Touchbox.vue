<template>
	<view>
		<view :class="isend?'fixedbox2' :'fixedbox'"
			:style="{'height':windowHeight + 'px','width':windowWidth + 'px','top':fixboxtop +'px','border-top-left-radius':radius,'border-top-right-radius':radius}"
			@touchmove="getstart($event)" @tap="tap" @touchend="getend" ref="fixbox">

			<view class="content" :style="{'height':windowHeight + 'px'}">
				<view class="tapBoxTouchLine" v-if="showLine">
					<view class="line"></view>
				</view>
				<u-row justify="space-between" gutter="10">
					<button :plain="true"
						style="width: 42%;border: 2px solid #66CDAA;border-radius:10px;color: #66CDAA ;position:relative ;top:20upx">租电桩</button>
					<button :plain="true"
						style="width: 42%;border: 2px solid #66CDAA;border-radius:10px;color: #66CDAA ;position:relative ;top:20upx">我的电桩</button>
				</u-row>
			</view>
		</view>
	</view>
</template>

<script>
	/**
	 * tapBox 触摸上拉组件
	 * @description 触摸上拉组件，类似于高德美团上拉组件
	 * @property {String} radius 左上右上圆角
	 * @property {Number} minHeight 最低高度 占总屏幕高度占比
	 * @property {Number} minHeight 最高高度 占总屏幕高度占比
	 * @property {Number} touchHeight 允许滑动区域高度默认顶部横线高度 0为任意区域可滑动，单位rpx
	 * @property {Boolean} showLine 上否显示顶部滑动线
	 * @event {Function} tap 点击事件
	 * @event {Function} getstart 开始滑动时触发事件
	 * @event {Function} getend 滑动结束事件
	 */
	export default {
		name: "tapBox",
		data() {
			return {
				windowHeight: 0, // 屏幕高度
				windowWidth: 0, // 屏幕宽度
				firsttop: 0, // 默认高度
				fixboxtop: 0, // 实际高度
				phonetop: 200, // 默认滑动分界线 - 后面计算为最低与最高的一半
				isend: false, // 触摸结束
				isfirst: true, // 手指第一次触摸
				tapboxtop: 0, // 手指距离顶部距离
			};
		},

		props: {
			radius: {
				type: String,
				default: '50rpx'
			},
			minHeight: {
				type: Number,
				default: 0.3
			},
			maxHeight: {
				type: Number,
				default: 0.5
			},
			touchHeight: {
				type: Number,
				default: 0
			},
			showLine: {
				type: Boolean,
				default: true
			},
		},
		mounted() {
			this.$nextTick(function() {
				this.windowWidth = uni.getSystemInfoSync().windowWidth;
				this.windowHeight = uni.getSystemInfoSync().windowHeight;
				var defaultHeight = this.windowHeight * (1 - this.minHeight)
				this.firsttop = defaultHeight
				this.phonetop = (this.windowHeight * this.maxHeight - this.windowHeight * this.minHeight) /
					2
				this.fixboxtop = defaultHeight
				this.$emit('currentHeight', (this.windowHeight - this.fixboxtop))
				this.$emit('maxtHeight', (this.windowHeight * this.maxHeight))
			})
		},
		onReady() {},
		computed: {},
		methods: {
			tap(e) {
				// console.log(e)
			},
			getstart(e) {
				var screenY;
				//#ifdef MP-WEIXIN
				screenY = e.touches[0].clientY;
				//#endif
				//#ifndef MP-WEIXIN
				screenY = e.touches[0].screenY;
				//#endif
				// console.log(screenY)
				// 这里特殊处理 解决：在滑动框内如果存在滚动元素，则会出现滑动时滑动框和内部滚动同时滑的问题
				if (this.touchHeight !== 0) {
					if (screenY - this.fixboxtop > this.touchHeight) {
						return false;
					}
				}
				this.isend = false
				if (this.isfirst) {
					this.isfirst = false
					this.tapboxtop = screenY - this.fixboxtop
				}
				this.fixboxtop = screenY - this.tapboxtop
				if (this.fixboxtop > this.firsttop) { // 往下滑 不允许
					this.fixboxtop = this.firsttop
				} else { // 往上滑
					if (this.fixboxtop <= this.windowHeight * (1 - this.maxHeight)) {
						this.fixboxtop = this.windowHeight * (1 - this.maxHeight)
					}
				}
				this.$emit('currentHeight', (this.windowHeight - this.fixboxtop))
			},
			getend() {
				this.isend = true
				this.isfirst = true
				if ((this.firsttop - this.fixboxtop) <= this.phonetop) {
					this.fixboxtop = this.firsttop
				} else {
					this.fixboxtop = this.windowHeight * (1 - this.maxHeight)
				}
				this.$emit('currentHeight', (this.windowHeight - this.fixboxtop))
			}
		},
	}
</script>

<style lang="scss" scoped>
	.tapBoxTouchLine {
		margin-top: 20rpx;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.line {
		margin: 0px;
		vertical-align: middle;
		border-bottom: 8rpx solid rgb(214, 215, 217);
		width: 60rpx;
		transform: scaleY(0.5);
		border-top-color: rgb(214, 215, 217);
		border-right-color: rgb(214, 215, 217);
		border-left-color: rgb(214, 215, 217);
	}

	.fixedbox {
		position: fixed;
		left: 0;
		background-color: #FFFFFF;
		padding: 0 12px;
	}

	.fixedbox2 {
		position: fixed;
		left: 0;
		background-color: #FFFFFF;
		padding: 0 12px;
		transition-property: top;
		transition-duration: .8s;
	}
</style>
