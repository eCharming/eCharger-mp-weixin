<template>
	<view>
		<view class="navigator" :style="{'height':statusHeight+'px'}">
			<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}" @tap="back"></image>
			<text class="contacter" :style="{'margin-bottom':contacterBottom+'px'}">联系人</text>
		</view>
		<scroll-view
			scroll-y="true"
			scroll-with-animation=true
			:style="{'height':scrollHeight+'px'}"
		>
			<view class="friends" v-for="friend,index in friends" :key="index" @click="click(friend.name,friend.uid)">
				<view class="avatarView">
					<image class="avatar" src="https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epC7z1MOibsdn9Z8P5kl2oNOMaJufQy7luCAlzmBVlY8ERytGHgOvw4CD9xUoxPhiciaBrJIJTPM1dcQ/132"></image>
				</view>
				<text class="name">{{friend.name}}</text>
				<text class="lastWord">{{friend.lastWord}}</text>
				<text class="lastTime">{{friend.lastTime}}</text>
				<view v-if="friend.hasNew" class="newMessage">
					<text>{{friend.newMessageNum}}</text>
				</view>
			</view>
		</scroll-view>
		
	</view>
	
</template>

<script>
	export default{
		data(){
			return{
				contacterBottom:0,
				statusHeight:0,
				scrollHeight:0,
				friends:[],
				uid:-1,
				statusBarHeight:uni.getSystemInfoSync().statusBarHeight,
			}
		},
		methods:{
			back() {
				uni.navigateBack({
				})
			},
			click(name,toUid){
				uni.navigateTo({
					url: './chat?toUid='+toUid+'&name='+name,
					animationType: 'slide-in-bottom',
					animationDuration: 200
				});
			},
			connect(){
				this.socketTask=uni.connectSocket({		//打开链接
					url:'wss://ws.healtool.cn/websocketapi/Reminder/'+this.uid,
					// url:'ws://127.0.0.1:8080/websocketapi/Reminder/'+this.uid,
					success: () => {
						
					}
				});
				this.socketTask.onMessage((res)=>{
					console.log(res)
					var reminders=JSON.parse(res.data);
					console.log(reminders)
					for(var index in reminders){
						var fromUid=reminders[index].fromUid;
						var text=JSON.parse(reminders[index].latestText);
						var num=reminders[index].num;
						for(var i in this.friends){
							if(fromUid==this.friends[i].uid){
								this.friends[i].lastWord=text.message;
								this.friends[i].newMessageNum=num;
								this.friends[i].hasNew=true;
								var time=new Date(text.time);
								var hour=time.getHours();
								var minute=time.getMinutes();
								this.friends[i].lastTime=hour+':'+minute;
							}
						}
					}
				});
				this.socketTask.onClose((res)=>{
					console.log(res)
				});
			}
		},
		onLoad() {
			this.uid=this.$store.state.uid;
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight+50;
			this.contacterBottom=(this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
			this.scrollHeight=uni.getSystemInfoSync().windowHeight-this.statusHeight;
			this.friends.push({
				uid:1,
				name:'solaking',
				lastWord:'',
				lastTime:'',
				newMessageNum:0,//新消息数量
				hasNew:false//是否有新消息
			},{
				uid:2,
				name:'gxnsos',
				lastWord:'',
				lastTime:'',
				newMessageNum:0,//新消息数量
				hasNew:false//是否有新消息
			},{
				uid:3,
				name:'d-sketon',
				lastWord:'',
				lastTime:'',
				newMessageNum:0,//新消息数量
				hasNew:false//是否有新消息
			},{
				uid:4,
				name:'lecter',
				lastWord:'',
				lastTime:'',
				newMessageNum:0,//新消息数量
				hasNew:false//是否有新消息
			});
	
		},
		onUnload() {
			if(this.socketTask!=null){
				this.socketTask.close({
					success: () => {
						this.socketTask=null;
					}
				});
			}
		},
		onShow() {
			if(this.socketTask==null){
				this.connect();
			}
		},
		onHide() {
			if(this.socketTask!=null){
				this.socketTask.close({
					success: () => {
						this.socketTask=null;
					}
				});
			}
		}
	}
</script>

<style scoped>
	.navigator{
		background-color: rgba(102,205,170,1);
		display: flex;
		justify-content: center;
		align-items: flex-end;
		color: #FFFFFF;
		font-size: 35upx;
		font-weight: 700;
		letter-spacing: 2upx;
	}

	.friends{
		height: 150upx;
		/* border: 2px red solid; */
		position: relative;
	}
	
	.avatarView{
		position: absolute;
		height: 100upx;
		width: 100upx;
		border-radius: 50%;
		top:25upx;
		left: 25upx;
		overflow: hidden;
	}
	
	.avatar{
		position: absolute;
		height: 100upx;
		width: 100upx;
	}
	
	.name{
		font-size: 35upx;
		letter-spacing: 2upx;
		position: absolute;
		top:30upx;
		left: 150upx;
	}
	
	.lastWord{
		letter-spacing: 1upx;
		position: absolute;
		top:80upx;
		left: 150upx;
		color: rgba(0,0,0,0.5);
	}
	
	.lastTime{
		font-size: 25upx;
		letter-spacing: 1upx;
		position: absolute;
		top:35upx;
		right: 30upx;
		color: rgba(0,0,0,0.5);
	}
	
	.newMessage{
		background-color: rgba(102,205,170,1);
		color: #FFFFFF;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
		height: 40upx;
		width: 40upx;
		position: absolute;
		top:80upx;
		right: 30upx;
	}
	
	.backimg {
		position:absolute;
		left:8px;
		width:25px;
		height:25px;
	}
</style>
