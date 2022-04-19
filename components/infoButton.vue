<template>
	<view>
		<view>
			<image :src="src" style="transition: opacity .3s;border-radius: 50%;"
			 :style="{'pointer-events':pointerEvents,'opacity':opacity,'width':length,'height':length,'margin':margin}" @click="info"></image>
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
				logInStatus:false,
				length:'150rpx',
				margin:''
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
				if(!this.logInStatus){
					wx.getUserProfile({
						desc:'获取微信头像以及昵称',
						success: (res) => {
							this.$store.commit('setLogInStatus',true);
							wx.cloud.callFunction({   //uid获取
								name:'updateUrl',
								data:{
									userName:res.userInfo.nickName,
									avatarUrl:res.userInfo.avatarUrl,
								}
							})
						}
					})
				}
			}
		},
		mounted() {
			this.logInStatus=this.$store.state.logInStatus;
			if(this.logInStatus){
				this.margin='20rpx';
				this.src=this.$store.state.avatarUrl;
				this.length='100rpx';
			}
		},
		watch:{
			'$store.state.buttonSelected'(){
				if(!this.logInStatus){
					if(this.$store.state.buttonSelected==1){
						this.src="../static/image/person.gif";
					}	
					else{
						this.src="../static/image/person_blue.gif";
					} 
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
			},
			'$store.state.logInStatus'(){
				this.logInStatus=this.$store.state.logInStatus;
				if(this.$store.state.logInStatus){
					this.margin='20rpx'
					this.src=this.$store.state.avatarUrl;
					this.length='100rpx';
				}
			}
		}
	}
</script>

<style scoped>

</style>
