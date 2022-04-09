<template>
	<view style="background-color: rgb(240,240,240);">
		<view class="navigator" :style="{'height':statusHeight+'px'}">
			<image src="../../static/image/back.png" class="backimg" :style="{'top':statusBarHeight+12.5+'px'}" @tap="back"></image>
			<text :style="{'margin-bottom':friendBottom+'px'}">{{name}}</text>
		</view>
		<view>
			<scroll-view
				scroll-y="true"
				scroll-with-animation=true
				:scroll-into-view="textIndex"
				:style="{'height':scrollHeight+'px'}"
			>
				<view v-for="text,index in texts" :key="index" :id="'index'+index" :class="text.fromMe?'myTextView':'otherTextView'">
					<text :class="text.fromMe?'myText':'otherText'" selectable="true" space="nbsp">{{text.message}}</text>
				</view>
			</scroll-view>
		</view>
		<view class="typeView">
			<view style="display: flex;">
				<textarea class="type" v-model="message" maxlength="140"
					confirm-hold="true"
					show-confirm-bar="false"
					hold-keyboard="true"
					auto-height="true"
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
				uid:0,
				toUid:0,
				socketTask:null,
				message:'',//textarea中的文字
				texts:[],//所有消息
				textIndex:'index0',
				statusBarHeight:uni.getSystemInfoSync().statusBarHeight,
				
				storageNum:100,//最大存储历史数据数量
				storage:[],//缓存历史数据
			}
		},
		methods:{
			back() {
				uni.navigateBack({
				})
			},
			send(){
				var message=this.message;
				this.message='';
				this.texts.push({
					fromMe:true,//是否是我发出的
					message:message,
				});
				this.textIndex="index"+(this.texts.length-1);//移动到这条数据
				var data={
					uid:this.uid,
					toUid:this.toUid,
					time:new Date().getTime(),
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
						this.texts.push({
							fromMe:false,//是否是我发出的
							message:text.message,
						})
					}
					
				});
				this.socketTask.onClose((res)=>{
					console.log(res)
				})
			},
		},
		computed:{
			statusHeight(){
				return uni.getSystemInfoSync().statusBarHeight+50;
			},
			friendBottom(){
				return (this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
			},
			scrollHeight(){
				return uni.getSystemInfoSync().windowHeight-this.statusHeight-uni.upx2px(160);
			}
		},
		onLoad(option) {
			this.uid=this.$store.state.uid;
			this.toUid=option.toUid;
			console.log(this.toUid)
			
			wx.cloud.callFunction({   //uid获取
				name:'infoReturn',
				data:{
					uid: Number(this.toUid)
				}
			}).then(
				res=>{
					this.name=res.result.userName;
				}
			)
			
			this.storage=uni.getStorageSync(this.uid+'ChatWith'+this.toUid);
			if(this.storage==''){
				this.storage=[];
			}else this.storage=JSON.parse(this.storage);
			var length=this.storage.length;
			var init=0;
			if(length>=10){
				init=length-10;
			}else{
				init=0;
			}
			for(var i=init;i<=length-1;i++){
				var text= JSON.parse(this.storage[i]);
				var fromMe=false;
				if(text.uid==this.uid)
					fromMe=true;
				this.texts.push({
					fromMe:fromMe,//是否是我发出的
					message:text.message,
				})
			}
			
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
				var time=new Date(last.time);
				var hour=time.getHours();
				var minute=time.getMinutes();
				reminder[this.toUid]={
					name:this.name,
					avatarUrl:'',
					time:hour+':'+minute,
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
		position: absolute;
		bottom: 0;
		background-color: rgba(240,240,240,1);
		height: 150upx;
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
