<template>
	<view>
		
		<view :class="isLaunch?'bg-class':'bg-class-none'" v-if="isShown">
			<image src="/static/image/logo.gif" class="img-class"></image>
		</view>
		<mymap v-show='!isShown'></mymap>
		<movablebox v-if='!isShown' :chargers="chargers"></movablebox>
	</view>
</template>

<script>
	import movablebox from '@/components/movableBox.vue'
	import mymap from '../../components/myMap.vue'
	export default {
		components: {
			movablebox,
			mymap,
		},
		data() {
			return {
				isLaunch: true,
				isShown:true,
				chargers:[],
			}
		},
		methods: {},
		onLoad() {
			wx.cloud.callFunction({   //uid获取
				name:'wxlogin',
				data:{
				
				}
			}).then(
				res=>{
					this.$store.commit('setUid',res.result);
					wx.cloud.callFunction({   //uid获取
						name:'infoReturn',
						data:{
							uid: res.result
						}
					}).then(
						res=>{
							this.$store.commit('setUserName',res.result.userName);
							this.$store.commit('setAvatarUrl',res.result.avatarUrl);
						}
					)
				}
			)
			
			// this.$store.commit('setUid',2);	
			
			var windowHeight=uni.getSystemInfoSync().windowHeight-uni.getSystemInfoSync().statusBarHeight-50;
			this.$store.commit('setWindowHeight',windowHeight);
			setTimeout(() => {
				this.isLaunch = false;
			}, 1500)
			setTimeout(() => {
				this.isShown = false;
			}, 2500)
		},
		onShow() {
				wx.cloud.callFunction({ //uid获取
					name: 'searchCharger',
					data: {
						uid:this.$store.state.uid
					}
				}).then(
					res => {
						if(res.result!=null && res.result) {
							this.chargers.splice(0)
							this.chargers.push(...res.result)
						}
					}
				)
			
		},
		watch:{
			'$store.state.uid'() {
				wx.cloud.callFunction({ //uid获取
					name: 'searchCharger',
					data: {
						uid:this.$store.state.uid
					}
				}).then(
					res => {
						if(res.result!=null && res.result) {
							this.chargers.splice(0)
							this.chargers.push(...res.result)
						}
					}
				)
			}
		}
	}
</script>

<style scoped>
	.img-class {
		width: 444upx;
		height: 333upx;
	}

	.bg-class {
		background-color: #66CDAA;
		height: 100vh;
		width: 100vw;
		display: flex;
		justify-content: center;
		align-items: center;
		opacity: 1;
		
	}

	.bg-class-none {
		background-color: #66CDAA;
		height: 100vh;
		width: 100vw;
		display: flex;
		justify-content: center;
		align-items: center;
		opacity: 0;
		transform: translate(0, -100%);
		transition: opacity 1s,top 1s,transform 1s;
		transition-timing-function: ease-in;
	}
</style>
