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
			<view class="friends" v-for="friend,index in friends" :key="index" @click="click(friend.name,friend.uid,index)">
				<view class="avatarView">
					<image class="avatar" :src="friend.avatarUrl"></image>
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
			click(name,toUid,index){
				this.friends[index].hasNew=false;
				this.friends[index].newMessageNum=0;
				uni.navigateTo({
					url: './chat?toUid='+toUid,
				});
			},
			connect(reminder){
				this.socketTask=uni.connectSocket({		//打开链接
					url:'wss://ws.healtool.cn/websocketapi/Reminder/'+this.uid,
					// url:'ws://127.0.0.1:8080/websocketapi/Reminder/'+this.uid,
					success: () => {
						
					}
				});
				this.socketTask.onMessage((res)=>{
					// console.log(res)
					var reminders=JSON.parse(res.data);
					console.log(reminders)
					for(var index in reminders){
						var fromUid=reminders[index].fromUid;
						var text=JSON.parse(reminders[index].latestText);
						var num=reminders[index].num;
						var hasFound=false;
						var time=new Date(text.time);
						var hour=time.getHours();
						var minute=time.getMinutes();
						for(var i in this.friends){
							if(fromUid==this.friends[i].uid){
								this.friends[i].lastWord=text.message;
								this.friends[i].newMessageNum=num;
								this.friends[i].hasNew=true;
								this.friends[i].lastTime=hour+':'+minute;
								hasFound=true;
								break;
							}
						}
						if(hasFound==false){
							wx.cloud.callFunction({   //uid获取
								name:'infoReturn',
								data:{
									uid: Number(fromUid)
								}
							}).then(
								res=>{
									reminder[fromUid]={
										name:res.result.userName,
										avatarUrl:res.result.avatarUrl,
										time:hour+':'+minute,
										message:text.message
									};
									this.friends.unshift({
										uid:fromUid,
										name:res.result.userName,
										avatarUrl:res.result.avatarUrl,
										lastWord:text.message,
										lastTime:hour+':'+minute,
										newMessageNum:num,//新消息数量
										hasNew:true//是否有新消息
									})
								}
							)
							
						}
					}
				});
				this.socketTask.onClose((res)=>{
					// console.log(res)
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
				name:'',
				avatarUrl:'',
				lastWord:'',
				lastTime:'',
				newMessageNum:0,//新消息数量
				hasNew:false//是否有新消息
			},{
				uid:2,
				name:'',
				avatarUrl:'',
				lastWord:'',
				lastTime:'',
				newMessageNum:0,//新消息数量
				hasNew:false//是否有新消息
			},{
				uid:3,
				name:'',
				avatarUrl:'',
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
			// this.friends.splice(0);   //正式版
			var reminder=uni.getStorageSync(this.uid+'friends');
			if(reminder!=''){
				reminder=JSON.parse(reminder);
				var keys=Object.keys(reminder);
				// for(var index in keys){   //正式版
				// 	this.friends.push({
				// 		uid:keys[index],
				// 		name:reminder[[keys[index]]].name,
				// 		lastWord:reminder[[keys[index]]].message,
				// 		lastTime:reminder[[keys[index]]].time,
				// 		newMessageNum:0,//新消息数量
				// 		hasNew:false//是否有新消息
				// 	})
				// }
				
				for(var index in keys){ //临时测试用
					for(var i in this.friends){
						if(this.friends[i].uid==keys[index]){
							this.friends[i].name=reminder[[keys[index]]].name;
							this.friends[i].avatarUrl=reminder[[keys[index]]].avatarUrl;
							this.friends[i].lastWord=reminder[[keys[index]]].message;
							this.friends[i].lastTime=reminder[[keys[index]]].time;
						}
					}
				}
				
			}else reminder={};
			if(this.socketTask==null){
				this.connect(reminder);
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
		width: 500upx;
		top:80upx;
		left: 150upx;
		color: rgba(0,0,0,0.5);
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
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
