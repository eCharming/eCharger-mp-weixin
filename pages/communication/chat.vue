<template>
	<view style="background-color: rgb(240,240,240);">
		<view class="navigator" :style="{'height':statusHeight+'px'}">
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
					<text :class="text.fromMe?'myText':'otherText'">{{text.message}}</text>
				</view>
			</scroll-view>
		</view>
		<view class="typeView">
			<view style="display: flex;">
				<textarea class="type" v-model="message"></textarea>
				<view class="send" @click="send">发送</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default{
		data(){
			return{
				friendBottom:0,
				statusHeight:0,
				scrollHeight:0,
				name:'',
				uid:0,
				toUid:0,
				socketTask:null,
				message:'',
				texts:[],
				textIndex:'index0',
			}
		},
		methods:{
			send(){
				var message=this.message;
				this.message='';
				this.texts.push({
					fromMe:true,//是否是我发出的
					message:message,
				});
				this.textIndex="index"+(this.texts.length-1);//移动到这条数据
				console.log(this.textIndex)
				var data={
					uid:this.uid,
					toUid:this.toUid,
					time:new Date().getTime(),
					message:message
				}
				data=JSON.stringify(data);
				console.log(data)
				this.socketTask.send({ //发送到服务器
					data:data,
					success: () => {
				
					}
				});
			},
			connect(){
				this.socketTask=uni.connectSocket({		//打开链接
					url:'wss://ws.healtool.cn/websocketapi/'+this.uid+'/'+this.toUid,
					// url:'ws://127.0.0.1:8080/websocketapi/'+this.uid+'/'+this.toUid,
					success: () => {
						uni.onSocketMessage((res)=>{
							console.log(res)
							var data=JSON.parse(res.data);
							var message=data.message;
							this.texts.push({
								fromMe:false,//是否是我发出的
								message:message,
							})
						});
						uni.onSocketClose(function(res){
							console.log(res)
						})
					}
				});
			}
		},
		onLoad(option) {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight+50;
			this.friendBottom=(this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
			this.scrollHeight=uni.getSystemInfoSync().windowHeight-this.statusHeight-uni.upx2px(160);
			this.name=option.name;
			this.uid=this.$store.state.uid;
			console.log(this.uid)
			this.toUid=option.toUid;
			
			// this.texts.push({
			// 	fromMe:true,
			// 	message:'111'
			// })
			// this.texts.push({
			// 	fromMe:false,
			// 	message:'222'
			// });
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
		padding: 10upx;
		padding-left: 20upx;
		padding-top: 18upx;
		font-size: 30upx;
		margin: 20upx;
		height: 70upx;
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
		justify-content: flex-end
	}
	
	.otherTextView{
		display:flex;
	}
	
	.myText{
		background-color: rgba(102,205,170,1);
		color: #FFFFFF;
		margin: 20upx;
		padding: 30upx;
		border-radius: 40upx;
		font-size: 30upx;
	}
	
	.otherText{
		background-color: rgba(255,255,255,1);
		margin: 20upx;
		padding: 30upx;
		border-radius: 40upx;
		font-size: 30upx;
	}
</style>
