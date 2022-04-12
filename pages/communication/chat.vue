<template>
	<view style="background-color: rgb(240,240,240);">
		<view class="navigator" :style="{'height':statusHeight+'px'}">
			<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}" @tap="back"></image>
			<text :style="{'margin-bottom':friendBottom+'px'}">{{name}}</text>
		</view>
		<view>
			<scroll-view
				style="overflow-anchor:auto;"
				scroll-anchoring="true"
				:scroll-y="scrollStatus"
				:scroll-with-animation="False"
				:scroll-top="scrollAnchor"
				:scroll-into-view="textIndex"
				:style="{'height':scrollHeight+'px'}"
				@scrolltoupper="scrollToUpper()"
			>
				<view v-for="text,index in texts" :key="index" :id="'index'+index" >
					<view class="time" v-if="text.showTime">
						<text>{{text.time.timeText}}</text>
					</view>
					<view :class="text.fromMe?'myTextView':'otherTextView'">
						<view class="avatarView" v-if="!text.fromMe">
							<image style="width: 90upx;height: 90upx;" :src="yourAvatarUrl"></image>
						</view>
						
						<text :class="text.fromMe?'myText':'otherText'" selectable="true" space="nbsp">{{text.message}}</text>
						<view class="avatarView" v-if="text.fromMe">
							<image style="width: 90upx;height: 90upx;"  :src="myAvatarUrl"></image>
						</view>
					</view>
					
					
				</view>
			</scroll-view>
		</view>
		<view class="typeView">
			<view style="display: flex;">
				<textarea class="type" v-model="message" maxlength="140"
					confirm-hold="true"
					:show-confirm-bar="False"
					hold-keyboard="true"
					auto-height="true"
					:adjust-position="False"
					@focus="focus($event)"
					@blur="unfocus()"
				></textarea>
				<view class="send" @click="send">发送</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				// friendBottom:0,
				// statusHeight:0,
				// scrollHeight:0,
				name:'',
				yourAvatarUrl:'',
				myAvatarUrl:'',
				uid:0,
				toUid:0,
				socketTask:null,
				message:'',//textarea中的文字
				texts:[],//所有消息
				firstTop:true,//是否第一次爬升到顶部
				textIndex:'index0',//判定scroll滚动到哪里
				historyIndex:0,//判定缓存记录显示到哪里
				statusBarHeight:uni.getSystemInfoSync().statusBarHeight,
				statusHeight:uni.getSystemInfoSync().statusBarHeight+50,
				
				scrollStatus:true,
				scrollHeight:uni.getSystemInfoSync().windowHeight-(uni.getSystemInfoSync().statusBarHeight+50)-uni.upx2px(130),
				scrollAnchor:1,
				
				False:false,
				
				keyboardHeight:0,//键盘高度
				
				storageNum:100,//最大存储历史数据数量
				storage:[],//缓存历史数据
			}
		},
		methods:{
			focus(e){
				this.keyboardHeight=e.detail.height;
				this.scrollHeight-=this.keyboardHeight;
				this.textIndex='';
				this.$nextTick(function(){
					this.textIndex='index'+(this.texts.length-1);
				})
			},
			unfocus(){
				this.scrollHeight+=this.keyboardHeight;
			},
			back() {
				uni.navigateBack({
				})
			},
			judgeTime(time){
				if(this.texts=='')
					return true;
				else{
					if(time-this.texts[this.texts.length-1].time.timeStamp>=300000)
						return true;
					else return false;
				}
			},
			timeObject(time,bool){
				if(bool){
					var currentTime=new Date();
					var currentYear=currentTime.getFullYear();
					var currentMonth=currentTime.getMonth()+1;
					var currentDay=currentTime.getDate();
					var timeDate=new Date(time);
					var year=timeDate.getFullYear();
					var month=timeDate.getMonth()+1;
					var day=timeDate.getDate();
					var hour=timeDate.getHours();
					var minute=timeDate.getMinutes();
					if(hour<10)
						hour='0'+hour;
					if(minute<10)
						minute='0'+minute;
					var timeText='';
					if(currentYear==year){
						if(currentMonth==month&&currentDay==day){
							timeText=hour+':'+minute;
						}else if(currentMonth==month&&currentDay-day==1){
							timeText='昨天'+hour+':'+minute;
						}else {
							month=(month<10)?'0'+month:month;
							day=(day<10)?'0'+day:day;
							timeText=month+'-'+day+' '+hour+':'+minute;				
						}
					}else{
						month=(month<10)?'0'+month:month;
						day=(day<10)?'0'+day:day;
						timeText=year+'-'+month+'-'+day+' '+hour+':'+minute;	
					} 

					return {
						timeStamp:time,
						timeText:timeText,
					}
					
				}else return{
					timeStamp:time,
					timeText:'',
				}
				
			},
			send(){
				var message=this.message;
				this.message='';
				var time=new Date().getTime();
				var showTime=this.judgeTime(time);
				var timeObject=this.timeObject(time,showTime);
				
				this.texts.push({
					fromMe:true,//是否是我发出的
					time:timeObject,
					showTime:showTime,
					message:message,
				});
				this.textIndex="index"+(this.texts.length-1);//移动到这条数据
				var data={
					uid:this.uid,
					toUid:this.toUid,
					time:time,
					message:message
				}
				data=JSON.stringify(data);
				this.storage.push(data);
				if(this.storage.length>=100)
					this.storage.splice(0,1);
				this.socketTask.send({ //发送到服务器
					data:data,
					success: () => {
				
					}
				});
			},
			connect(){
				this.socketTask=uni.connectSocket({		//打开链接
					url:'wss://ws.healtool.cn/websocketapi/Chat/'+this.uid+'/'+this.toUid,
					// url:'ws://127.0.0.1:8080/websocketapi/Chat/'+this.uid+'/'+this.toUid,
					success: () => {
						
					}
				});
				this.socketTask.onMessage((res)=>{
					console.log(res)
					var data=JSON.parse(res.data);
					var num=data.num;
					var messages=data.message;
					this.storage.push(...messages);
					if(this.storage.length>=this.storageNum){
						this.storage.splice(0,this.storage.length-this.storageNum);
					}
					for(var index in messages){
						var text=JSON.parse(messages[index])
						var showTime=this.judgeTime(text.time);
						var timeObject=this.timeObject(text.time,showTime);
						this.texts.push({
							fromMe:false,//是否是我发出的
							time:timeObject,
							showTime:showTime,
							message:text.message,
						})
					}
					
				});
				this.socketTask.onClose((res)=>{
					console.log(res)
				})
			},
			scrollToUpper(){
				// this.scrollAnchor=2;
				// this.$nextTick(function(){
				// 	this.scrollAnchor=1;
				// })
				this.scrollStatus=false;
				
				if(this.historyIndex!=0){
					setTimeout(() => {	
						var length=this.storage.length;
						var lastIndex=this.historyIndex-1;
						if(this.historyIndex>15)
							this.historyIndex-=15;
						else this.historyIndex=0;
						var textsCopy=[];
						for(var i=this.historyIndex;i<=lastIndex;i++){
							var text= JSON.parse(this.storage[i]);
							var fromMe=false;
							if(text.uid==this.uid)
								fromMe=true;
								
							var showTime=false;
							if(i==this.historyIndex)
								showTime=true;
							else{
								if(text.time-textsCopy[textsCopy.length-1].time.timeStamp>=300000)
									showTime=true;
								else showTime=false;
							}	
							var timeObject=this.timeObject(text.time,showTime);
							textsCopy.push({
								fromMe:fromMe,//是否是我发出的
								time:timeObject,
								showTime:showTime,
								message:text.message,
							})
						}
						this.texts.unshift(...textsCopy);
						this.textIndex='index'+(lastIndex-this.historyIndex+1);
						// this.$nextTick(function(){
							
						// })
						
						// this.scrollAnchor=200;
					}, 1000)
					
				}
				// this.$nextTick(function(){
				this.scrollStatus=true;	
					
				
				// })
				
			}
		},
		computed:{
			
			friendBottom(){
				return (this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
			},
			
		},
		onLoad(option) {
			this.uid=this.$store.state.uid;
			this.toUid=option.toUid;
			this.name=option.name;
			this.yourAvatarUrl=option.avatarUrl;
			this.myAvatarUrl=this.$store.state.avatarUrl;
			wx.cloud.callFunction({   //uid获取
				name:'infoReturn',
				data:{
					uid: Number(this.toUid)
				}
			}).then(
				res=>{
					this.name=res.result.userName;
					this.yourAvatarUrl=res.result.avatarUrl;
				}
			)
			
			this.storage=uni.getStorageSync(this.uid+'ChatWith'+this.toUid);
			if(this.storage==''){
				this.storage=[];
			}else this.storage=JSON.parse(this.storage);
			var length=this.storage.length;
			if(length>15)
				this.historyIndex=length-15;
			else this.historyIndex=0;
			for(var i=this.historyIndex;i<=length-1;i++){
				var text= JSON.parse(this.storage[i]);
				var fromMe=false;
				if(text.uid==this.uid)
					fromMe=true;
				var showTime=this.judgeTime(text.time);
				var timeObject=this.timeObject(text.time,showTime);
				this.texts.push({
					fromMe:fromMe,//是否是我发出的
					time:timeObject,
					showTime:showTime,
					message:text.message,
				})
			}
			if(this.texts.length!=0)
				this.textIndex='index'+(this.texts.length-1)
		},
		onUnload() {
			if(this.socketTask!=null){
				this.socketTask.close({
					success: () => {
						this.socketTask=null;
					}
				});
			}
			var reminder=uni.getStorageSync(this.uid+'friends');
			if(this.storage!=''){
				uni.setStorageSync(this.uid+'ChatWith'+this.toUid,JSON.stringify(this.storage));
				if(reminder!=''){
					reminder=JSON.parse(reminder);
				}else reminder={};
				var last=JSON.parse(this.storage[this.storage.length-1]);
				reminder[this.toUid]={
					name:this.name,
					avatarUrl:this.yourAvatarUrl,
					time:last.time,
					message:last.message
				};
				uni.setStorageSync(this.uid+'friends',JSON.stringify(reminder));
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
	
	.typeView{
		background-color: rgba(240,240,240,1);
		height: 130upx;
		width: 100%;
		/* border: 2px solid red; */
	}
	
	.type{
		/* border: 2px red solid; */
		/* padding: 10upx;
		padding-left: 20upx;
		padding-top: 18upx; */
		padding: 20upx;
		font-size: 30upx;
		line-height: 30upx;
		min-height: 70upx;
		margin: 20upx;
		/* height: 70upx; */
		border-radius: 35upx;
		width: 600upx;
		background-color: rgb(255,255,255);
	}
	
	.send{
		background-color: rgba(102,205,170,1);
		color: #FFFFFF;
		width: 100upx;
		border-radius: 35upx;
		margin: 20upx;
		margin-left: 0;
		height: 70upx;
		display: flex;
		justify-content: center;
		align-items: center;
		letter-spacing: 1upx;
	}
	
	.avatarView{
		width: 90upx;
		height: 90upx;
		margin: 20upx;
		border-radius: 50%;
		overflow: hidden;
	}
	
	.time{
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 20upx;
		font-size: 25upx;
		color: rgba(0,0,0,0.5);
	}
	
	.myTextView{
		display:flex;
		justify-content: flex-end;
	}
	
	.otherTextView{
		display:flex;
	}
	
	.myText{
		background-color: rgba(102,205,170,1);
		color: #FFFFFF;
		margin: 20upx;
		margin-right: 0;
		padding: 30upx;
		border-radius: 30upx;
		border-top-right-radius: 0upx;
		font-size: 30upx;
		word-break:break-all;
		word-wrap:break-word; 
	}
	
	.otherText{
		background-color: rgba(255,255,255,1);
		margin: 20upx;
		margin-left: 0;
		padding: 30upx;
		border-radius: 30upx;
		border-top-left-radius: 0upx;
		font-size: 30upx;
		word-break:break-all;
		word-wrap:break-word; 
	}
	
	.backimg {
		position:absolute;
		left:8px;
		width:25px;
		height:25px;
	}
</style>
