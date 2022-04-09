<template>
	<view>
		<view :style="{'height':statusHeight+'px','background':color}">
			<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}"
				@tap="back"></image>
		</view>
		<addcard>
			<view class="display">
				<text class="labeltext">姓名</text>
				<input class="input" placeholder="请输入姓名" type="text" v-model="name"></input>
			</view>
			<view class="divLine"></view>
			<view class="display">
				<text class="labeltext">电话号码</text>
				<input class="input" placeholder="请输入电话号码" type="number" v-model="phoneNumber"></input>
			</view>
		</addcard>
		<addcard>
			<view class="display">
				<text class="labeltext">电桩位置</text>
				<input class="input" placeholder="请输入电桩位置" type="text" v-model='location'></input>
			</view>
			<view class="divLine"></view>
			
			<map id="myMap" style="width: 100%; height: 500upx;" layer-style="1" :latitude="center_latitude"
				:longitude="center_longitude" showLocation='true' subkey="ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL">
			</map>
			
			<view class="divLine"></view>
			<view class="display">
				<text class="labeltext">电桩单价</text>
				<input class="input" placeholder="请输入单价" type="digit" v-model="price"></input>
			</view>
			<view class="divLine"></view>
			<view class="display">
				<text class="labeltext">电桩可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime1" :end="maxTime1" @change="changetime1">
							<text class="timetext1" :style="{'opacity':opacity1}">{{text1}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime2" :end="maxTime2" @change="changetime2">
							<text class="timetext1" :style="{'opacity':opacity2}">{{text2}}</text>
						</picker>
					</view>

				</view>

			</view>
		</addcard>
		<addcard>
			<view class="display">
				<text class="labeltext">图片</text>
			</view>
			<view class='content'>
				<view class='img-view' v-for='(item,index) in avatarUrl' :key='index'>
					<image :src='item' @tap='showPic(item)'
						style="width:200upx;height:200upx;margin-left: 3.9upx;margin-right: 3.9upx;" mode='aspectFill'>
					</image>
					<icon @tap='deletePic(index)' class='close' color='rgb(199, 0, 3)' type='clear' size='15'></icon>
				</view>
				<image @tap='uploadPic' class='add-img' src='../../static/image/addImg.png' v-show="isAdd"></image>
			</view>
			<view class="divLine"></view>
			<view class="display">
				<text class="labeltext">备注</text>
				<textarea class="input" placeholder="备注" maxlength="140" auto-height="true">{{remarks}}</textarea>
			</view>
		</addcard>
		<button class="submit" @tap="submit">提交</button>
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
				windowWidth: uni.getSystemInfoSync().windowWidth,
				color: 'rgba(50,200,210,1)',
				statusBarHeight: uni.getSystemInfoSync().statusBarHeight,
				name: "",
				phoneNumber: "",
				location: "",
				price: "",
				text1: "起始时间",
				text2: "结束时间",
				minTime1: '00:00',
				maxTime1: '24:00',
				minTime2: '00:00',
				maxTime2: '24:00',
				opacity1: 0.7,
				opacity2: 0.7,
				remarks: "",
				avatarUrl: [],
				imgwrap: 0,
				center_latitude: this.$store.state.currentLocation.latitude, //中心纬度
				center_longitude:  this.$store.state.currentLocation.longitude, //中心经度
				covers: [],
			}
		},
		computed: {
			isAdd() {
				return this.avatarUrl.length < 6;
			},
			listLength() {
				return 6 - this.avatarUrl.length;
			}
		},
		methods: {
			back() {
				uni.navigateBack({})
			},
			changetime1(e) {
				this.time1 = e.detail.value;
				this.text1 = this.time1;
				this.minTime2 = this.time1;
				this.opacity1 = 1;
			},
			changetime2(e) {
				this.time2 = e.detail.value;
				this.text2 = this.time2;
				this.maxTime1 = this.time2;
				this.opacity2 = 1;
			},
			uploadPic() {
				if (this.avatarUrl.length >= 6) {
					wx.showToast({
						title: "图片数量不能超过六张！",
						icon: 'none',
					})
					return;
				}
				wx.chooseMedia({
					count: this.listLength,
					mediaType: ['image'],
					sourceType: ['album', 'camera'],
					maxDuration: 30,
					camera: 'back',
					success: res => {
						for (var i = 0; i < res.tempFiles.length; i++) {
							this.avatarUrl.push(res.tempFiles[i].tempFilePath);
						}
					}
				})
			},
			submit() {
				var reg_tel = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if (!reg_tel.test(this.phoneNumber)) {
					wx.showToast({
						title: "请输入正确的手机号！",
						icon: 'none',
					})
					return;
				}
				if (this.listLength == 6) {
					wx.showToast({
						title: "请上传照片！",
						icon: 'none',
					})
					return;
				}
				
				for (var i = 0; i < this.avatarUrl.length; i++) {
					wx.uploadFile({
						url: "https://ws.healtool.cn/uploadPic/001",
						filePath: this.avatarUrl[i],
						name: 'file',
						success: function(result) {
							console.log(result.data)
						}
					})
				}
			},
			showPic(item) {
				wx.previewImage({
					current: item,
					urls: this.avatarUrl,
				})
			},
			deletePic(index) {
				this.avatarUrl.splice(index, 1);
			}
		},
		mounted() {
			console.log(this.$store.state.currentLocation.latitude);
			console.log(this.$store.state.currentLocation.longitude);
		}
	}
</script>

<style scoped>

	.backimg {
		position: absolute;
		left: 8px;
		width: 25px;
		height: 25px;
	}

	.labeltext {
		letter-spacing: 2upx;
		font-weight: 700;
	}

	.display {
		display: flex;
		justify-content: space-between;
		margin: 15px;
	}

	.divLine {
		width: 100%;
		height: 3upx;
		background-color: #E0E3DA;
	}

	.input {
		width: 300upx;
		text-align: right;
	}

	.line {
		margin-left: 5px;
		margin-right: 5px;
	}

	.timer1 {
		width: 110upx;
		display: flex;
		justify-content: center;
	}

	.add-img {
		width: 200upx;
		height: 200upx;
		opacity: 0.3;
		margin-left: 3.9upx;
		margin-right: 3.9upx;
	}

	.content {
		display: flex;
		justify-content: flex-start;
		flex-wrap: wrap;
		margin-left: 15px;
		margin-right: 15px;
		margin-bottom: 15px;
	}

	.close {
		position: absolute;
		margin-left: -25px;
		margin-top: 5px;
	}

	.submit {
		background-color: rgb(50, 200, 210);
		color: white;
		font-weight: 700;
		letter-spacing: 3upx;
		margin:20upx;
		border-radius: 20upx;
	}
</style>
