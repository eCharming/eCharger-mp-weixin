<template>
	<view>
		<view >
			<image :src="src" mode="widthFix" style="width: 80px;" :style="{'pointer-events':pointerEvents}" @click="info"></image>
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
				}else{
					this.pointerEvents='none';
				} 
			}
		}
	}
</script>

<style scoped>

</style>
