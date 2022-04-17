<template>
	<view>
		
		<mymap ></mymap>
		<movablebox :chargers="chargers"></movablebox>
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
				isLoading:true,
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
				this.isShown = false;
			}, 2500)
			setTimeout(() => {
				this.isLoading = false;
			}, 10000)
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
							console.log(this.chargers)
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

</style>
