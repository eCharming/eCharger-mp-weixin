<template>
	<view>
		<mymap></mymap>
		<movablebox></movablebox>
		<loading v-if="isLoading" style="position: absolute;top: 0;width: 100%;"></loading>
	</view>
</template>

<script>
	import loading from '../../components/loading.vue'
	import movablebox from '@/components/movableBox.vue'
	import mymap from '../../components/myMap.vue'
	export default {
		components: {
			loading,
			movablebox,
			mymap,
		},
		data() {
			return {
				isLoading: true,
				refreshInterval: 0,
			}
		},
		methods: {},
		onUnload() {
			clearInterval(this.refreshInterval)
		},
		onLoad() {
			//每隔五分钟自动刷新
			this.refreshInterval = setInterval(() => {
				this.$store.commit('setGetChargers')
				this.$store.commit('setRefresh')
			}, 300000);
			wx.cloud.callFunction({ //uid获取
				name: 'wxlogin',
			}).then(
				res => {
					this.$store.commit('setUid', res.result.uid);
					var logInStatus = res.result.loginStatus
					if (res.result.loginStatus) {
						wx.cloud.callFunction({ //uid获取
							name: 'infoReturn',
							data: {
								uid: res.result.uid
							}
						}).then(
							res => {
								this.$store.commit('setUserName', res.result.userName);
								this.$store.commit('setAvatarUrl', res.result.avatarUrl);
								this.$store.commit('setLogInStatus', logInStatus);
							}
						)
					}
				}
			)
			var windowHeight = uni.getSystemInfoSync().windowHeight - uni.getSystemInfoSync().statusBarHeight - 50;
			this.$store.commit('setWindowHeight', windowHeight);
			setTimeout(() => {
				this.isLoading = false;
			}, 8000)
		},
		onShow() {
			this.$store.commit('setGetChargers')
			this.$store.commit('setChargerCardDefault')
		},
		watch: {
			'$store.state.logInStatus'() {
				this.$store.commit('setGetChargers')
			},
		}
	}
</script>

<style scoped>

</style>
