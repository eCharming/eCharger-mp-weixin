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
			if(this.$store.state.uid!=null) {
				wx.cloud.callFunction({ //uid获取
					name: 'searchCharger',
					data: {
						uid:this.$store.state.uid
					}
				}).then(
					
					res => {
						if(res && res.result.data) {
							this.chargers.splice(0)
							this.chargers.push(...res.result.data)
						}
					}
				)
			} else {
				setTimeout(()=>{
					wx.cloud.callFunction({ //uid获取
						name: 'searchCharger',
						data: {
							uid:this.$store.state.uid
						}
					}).then(
						
						res => {
							if(res && res.result.data) {
								this.chargers.splice(0)
								this.chargers.push(...res.result.data)
							}
						}
					)
				},500);
			}
			
		},
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
