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
				:longitude="center_longitude" showLocation='true' subkey="ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL"
				@tap="addMarker" :markers="covers">
			</map>

			<view class="divLine"></view>
			<view class="display">
				<text class="labeltext">电桩单价</text>
				<input class="input" placeholder="请输入单价" type="digit" v-model="price"></input>
			</view>
			<view class="divLine"></view>
			<view class="display">
				<text class="labeltext">周一可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[0]" :end="maxTime[0]" @change="changetime1($event,0)">
							<text class="timetext1" :style="{'opacity':opacity[0]}">{{text[0]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[1]" :end="maxTime[1]" @change="changetime2($event,1)">
							<text class="timetext1" :style="{'opacity':opacity[1]}">{{text[1]}}</text>
						</picker>
					</view>
				</view>
			</view>
			<view class="display">
				<text class="labeltext">周二可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[2]" :end="maxTime[2]" @change="changetime1($event,2)">
							<text class="timetext1" :style="{'opacity':opacity[2]}">{{text[2]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[3]" :end="maxTime[3]" @change="changetime2($event,3)">
							<text class="timetext1" :style="{'opacity':opacity[3]}">{{text[3]}}</text>
						</picker>
					</view>
				</view>
			</view>
			<view class="display">
				<text class="labeltext">周三可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[4]" :end="maxTime[4]" @change="changetime1($event,4)">
							<text class="timetext1" :style="{'opacity':opacity[4]}">{{text[4]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[5]" :end="maxTime[5]" @change="changetime2($event,5)">
							<text class="timetext1" :style="{'opacity':opacity[5]}">{{text[5]}}</text>
						</picker>
					</view>
				</view>
			</view>
			<view class="display">
				<text class="labeltext">周四可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[6]" :end="maxTime[6]" @change="changetime1($event,6)">
							<text class="timetext1" :style="{'opacity':opacity[6]}">{{text[6]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[7]" :end="maxTime[7]" @change="changetime2($event,7)">
							<text class="timetext1" :style="{'opacity':opacity[7]}">{{text[7]}}</text>
						</picker>
					</view>
				</view>
			</view>
			<view class="display">
				<text class="labeltext">周五可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[8]" :end="maxTime[8]" @change="changetime1($event,8)">
							<text class="timetext1" :style="{'opacity':opacity[8]}">{{text[8]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[9]" :end="maxTime[9]" @change="changetime2($event,9)">
							<text class="timetext1" :style="{'opacity':opacity[9]}">{{text[9]}}</text>
						</picker>
					</view>
				</view>
			</view>
			<view class="display">
				<text class="labeltext">周六可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[10]" :end="maxTime[10]" @change="changetime1($event,10)">
							<text class="timetext1" :style="{'opacity':opacity[10]}">{{text[10]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[11]" :end="maxTime[11]" @change="changetime2($event,11)">
							<text class="timetext1" :style="{'opacity':opacity[11]}">{{text[11]}}</text>
						</picker>
					</view>
				</view>
			</view>
			<view class="display">
				<text class="labeltext">周日可用时段</text>
				<view style="display:flex;justify-content: space-between;align-items:center;">
					<view class="timer1">
						<picker mode="time" :start="minTime[12]" :end="maxTime[12]" @change="changetime1($event,12)">
							<text class="timetext1" :style="{'opacity':opacity[12]}">{{text[12]}}</text>
						</picker>
					</view>

					<text class="line">-</text>

					<view class="timer1">
						<picker mode="time" :start="minTime[13]" :end="maxTime[13]" @change="changetime2($event,13)">
							<text class="timetext1" :style="{'opacity':opacity[13]}">{{text[13]}}</text>
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
				text: ["起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间", "起始时间", "结束时间",
					"起始时间", "结束时间"
				],
				minTime: ['00:00', '00:00', '00:00', '00:00', '00:00', '00:00', '00:00','00:00', '00:00', '00:00', '00:00', '00:00', '00:00', '00:00'],
				maxTime: ['24:00', '24:00', '24:00', '24:00', '24:00', '24:00', '24:00','24:00', '24:00', '24:00', '24:00', '24:00', '24:00', '24:00'],
				opacity: [0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7],
				remarks: "",
				avatarUrl: [],
				center_latitude: this.$store.state.currentLocation.latitude, //中心纬度
				center_longitude: this.$store.state.currentLocation.longitude, //中心经度
				covers: [],
				geopoint: {
					latitude: -1,
					longitude: -1,
				}
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
			addMarker(res) {
				this.geopoint.longitude = res.detail.longitude;
				this.geopoint.latitude = res.detail.latitude;
				this.covers.splice(0, 1, {
					title: "定位点",
					id: 0,
					latitude: this.geopoint.latitude,
					longitude: this.geopoint.longitude,
					iconPath: "/static/image/charger.png",
					width: 50,
					height: 50,
				})
			},
			back() {
				uni.navigateBack({})
			},
			changetime1(e, index) {
				var time = e.detail.value;
				this.text.splice(index,1,time)
				this.minTime.splice(index+1,1,time)
				this.opacity.splice(index,1,1);
			},
			changetime2(e, index) {
				var time = e.detail.value;
				this.text.splice(index,1,time)
				this.maxTime.splice(index-1,1,time)
				this.opacity.splice(index,1,1);
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
						//限制图片大小
						for (var i = 0; i < res.tempFiles.length; i++) {
							let size = res.tempFiles.every(item => {
								return item.size <= 10000000
							})
							if (size) {
								this.avatarUrl.push(res.tempFiles[i].tempFilePath);
							} else {
								wx.showToast({
									title: '上传图片不能大于10M!',
									icon: 'none'
								})
								return;
							}
						}
					}
				})
			},
			submit() {
				var reg_tel = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
				if (this.name == "") {
					wx.showToast({
						title: "请输入名字！",
						icon: 'none',
					})
					return;
				}
				if (this.phoneNumber == "") {
					wx.showToast({
						title: "请输入电话号码！",
						icon: 'none',
					})
					return;
				}
				if (!reg_tel.test(this.phoneNumber)) {
					wx.showToast({
						title: "请输入正确的手机号！",
						icon: 'none',
					})
					return;
				}
				if (this.location == "") {
					wx.showToast({
						title: "请输入电桩位置！",
						icon: 'none',
					})
					return;
				}
				if (this.geopoint.longitude == -1 || this.geopoint.latitude == -1) {
					wx.showToast({
						title: "请在地图上标注电桩位置！",
						icon: 'none',
					})
					return;
				}
				if (this.price == "") {
					wx.showToast({
						title: "请输入电桩单价！",
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
				//云函数上传
				var time = new Array();
				for (var i = 0; i < 14; i += 2) {
					if (this.text[i] == "起始时间" || this.text[i + 1] == "结束时间") {
						time.push("")
					} else {
						time.push(this.text[i] + "-" + this.text[i + 1])
					}
				}
				wx.cloud.callFunction({ //uid获取
					name: 'chargerInput',
					data: {
						uid: this.$store.state.uid,
						userName: this.name,
						phoneNumber: this.phoneNumber,
						location: this.location,
						geoPoint: {
							coordinates: [this.geopoint.longitude, this.geopoint.latitude],
							type: "Point",
						},
						time: time,
						price: this.price,
						remarks: this.remarks,
					}
				}).then(
					res => {
						console.log("云函数"+res.data);
					}
				)
				for (var i = 0; i < this.avatarUrl.length; i++) {
					wx.uploadFile({
						url: "https://ws.healtool.cn/uploadPic/001",
						filePath: this.avatarUrl[i],
						name: 'file',
						success: res => {
							console.log(res.data)
							if (res.data.code != 200) {
								wx.showToast({
									title: "图片上传失败！",
									icon: 'none',
								})
								//回滚todo
								return;
							}
						},
						fail: res => {
							wx.showToast({
								title: "图片上传失败！",
								icon: 'none',
							})
							//回滚todo
							return;
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
		margin: 20upx;
		border-radius: 20upx;
	}
</style>
