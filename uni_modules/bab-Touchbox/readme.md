# 上拉滑动框

仿高德地图、美团上拉滑动框，支持NVUE、VUE、微信小程序，其它平台待测

参数

* @property {String} radius 左上右上圆角 例： 50rpx，默认50rpx
* @property {Number} minHeight 最低高度 占总屏幕高度占比 例： 0.2，默认0.2
* @property {Number} maxHeight 最高高度 占总屏幕高度占比  例：0.8，默认0.5
* @property {Number} touchHeight 允许滑动区域高度默认顶部横线高度 0为任意区域可滑动，单位rpx  例：50  =>  50rpx，默认0
* @property {Boolean} showLine 上否显示顶部滑动线  例：false  ，默认 true
* @currentHeight 组件内置返回当前高度（包括滑动中）方便外部使用，例效果图中跟着滑动的；两个按钮
* @maxtHeight 组件返回最高高度，用途：如果组件内包含滚动组件，用于计算滚动组件scroll-view高度 = maxtHeight-line -不许滚动高度；
* 例： <scroll-view :style="{'height':maxtHeight - 100 +'px'}" scroll-y="true"></scroll-view>，此时需要配置touchHeight，不配置将会产生scroll-view和组件同时滚动BUG

包含滚动效果，需配置 touchHeight
![image](https://bab-demo.oss-cn-shenzhen.aliyuncs.com/450jh-ycai7.gif)

返回当前高度给外部使用效果 高度来源currentHeight
![image](https://bab-demo.oss-cn-shenzhen.aliyuncs.com/8vi5r-2iwx0.gif)