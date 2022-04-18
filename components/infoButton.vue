<template>
	<view>
		<view >
			<image :src="src" mode="widthFix" style="width: 150rpx;transition: opacity .3s;"
			 :style="{'pointer-events':pointerEvents,'opacity':opacity}" @click="info"></image>
		</view>
	</view>
</template>

<script>
	export default {
		name: "infoButton",
		data() {
			return {
				src:"../static/image/person.gif",
				pointerEvents:'auto',
				opacity:1,
			};
		},
		props:{
			isLow:{
				type:Boolean,
			},
		},
		methods:{
			info() {
				//TODO
				wx.getUserProfile({
					desc:'获取微信头像以及昵称',
					success: (res) => {
						this.$store.commit('setLogInStatus',true);
						wx.cloud.callFunction({   //uid获取
							name:'updateUrl',
							data:{
								userName:res.userInfo.nickName,
								avatarUrl:res.userInfo.avatarUrl
							}
						})
					}
				})
			}
		},
		watch:{
			'$store.state.buttonSelected'(){
				if(this.$store.state.buttonSelected==1){
					this.src="../static/image/person.gif";
				}	
				else{
					this.src="../static/image/person_blue.gif";
				} 
			},
			'isLow'(){
				if(this.isLow){
					this.pointerEvents='auto';
					this.opacity=1;
				}else{
					this.pointerEvents='none';
					this.opacity=0;
				} 
			}
		}
	}
</script>

<style scoped>

</style>
