<template>
	<view>
		<navigator :color="color" :isBinded="true"></navigator>
		<view class="searchbox">
			<view class="textareaview">
				<textarea class="textarea" 
					placeholder="输入目的地"
					v-model="position"
					@input="request()"
					:style="{'border-color':color}"
				>
					
				</textarea>
				<text class="searchtext" :style="{'color':color}">搜索</text>
				<image src="../../static/image/lightning_green.png" class="image1"></image>
			</view>
			
			<view class="storageview" v-if="!isInput">
				<view class="history">
					<view>
						<view style="display: flex;">
							<view class="historyview">
								<text>历史记录</text>
							</view>
							<view class="commonview">
								
								<text>常用地点</text>
							</view>
						</view>
						<view style="margin-left: 30upx;width: 290upx;height: 17upx;display: flex;" :style="{'justify-content':justifyContent}">
							<view class="modelSelected" :style="{'height':modelHeight+'px','width':modelWidth+'px','background-color':color}"></view>
						</view>
					</view>
					
					<view style="display: flex;position: relative;transition: all .1s;" :style="{'left':buttonLeft+'px'}">
						<view class="clear" :style="{'opacity':buttonOpacity1}" @tap="clear">清空历史记录</view>
						<view class="add" :style="{'opacity':buttonOpacity2 }">添加常用地点</view>
					</view> 
					
				</view>
				
				<swiper :style="{'height': (storageHeight+20)+'px'}" @animationfinish="animationfinish($event)" @transition="transition($event)" @change="change($event)">
					<swiper-item>
						<scroll-view
							scroll-y="true"
							style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
							:style="{'height': storageHeight+'px'}"
						>
							<view v-for="(storage,index) in storages" :key="index" style="display: flex;flex-direction: column;">
								<view
									class="storage"
									@tap="tapStorage(storage.title,storage.location)"
								>
								
									<view class="view5">
										<image src="../../static/image/search.png" class="image3"></image>
										<text class="text3">{{storage.title}}</text>
									</view>
									
									<view class="view4" @tap.stop.prevent="del(index)">
										<icon type="cancel" color="rgba(102,205,170,1)" style="margin-top: 5upx;"></icon>
									</view>
								</view>
								<view class="view6">
									<text class="text6">{{storage.category}}</text>
								</view>
								<view style="display: flex;justify-content: center;margin-top: 20upx;">
									<view style="border-bottom: 1px solid rgba(0,0,0,0.1);width: 500upx;"></view>
								</view>
								
							</view>
						
						
						</scroll-view>
				</swiper-item>
					<swiper-item>
						<view style="display: flex;justify-content: space-between;margin: 30upx;">
							<view style="width: 230upx;display: flex;justify-content: center;">回家</view>
							<view style="width: 230upx;display: flex;justify-content: center;
							border-left:1px solid rgba(0,0,0,0.1);border-right:1px solid rgba(0,0,0,0.1);">公司</view>
							<view style="width: 230upx;display: flex;justify-content: center;">学校</view>
						</view>
						<scroll-view
							scroll-y="true"
							style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
							:style="{'height': (storageHeight-50)+'px'}"
						>
							
						</scroll-view>
					</swiper-item>
				</swiper>
				
				
			</view>
			
			<scroll-view
				v-if="isInput"
				scroll-y="true"  
				style="margin: 30upx;background-color: #FFFFFF;border-radius: 40upx;width:690upx"
				:style="{'height': suggestionHeight+'px'}"
			>		
				<view class="suggestion" 
					
					v-for="(suggestion,index) in suggestions" 
					:key="index"
					@tap="tap(suggestion.id,suggestion.title,suggestion.location,suggestion.category)"
				>
					<view style="display: flex;justify-content: space-between;">
						<view class="view1">
							<image class="image2" src="../../static/image/landmark.png"></image>
							<rich-text :nodes="suggestion.strings"></rich-text>
						</view>
						<view style="position: absolute;right: 40upx;top: 5upx;display: flex;flex-direction: column;justify-content: center;">
							<image src='../../static/image/distance.png' style="width: 80upx;height: 80upx;"></image>
							<text style="font-size: 23upx;color: rgb(0,0,0,0.6);margin-top: 5upx;">{{suggestion.distance}}km</text>
						</view>
					</view>
					
					<view class="view2">
						<text class="text2">{{suggestion.category}}</text>
					</view>
					<view class="view3">
						<text style="font-size: 23upx;color: rgb(0,0,0,0.6);">{{suggestion.address}}</text>
					</view>
					<view style="display: flex;justify-content: center;">
						<view style="border-bottom: 1px solid rgba(0,0,0,0.1);width: 500upx;"></view>
					</view>
				</view>
			</scroll-view>
			
			
			
		</view>
	</view>
	
</template>

<script>
	import navigator from '../../components/navigator.vue'
	export default{
		components:{
			navigator
		},
		data(){
			return{
				position:"",
				suggestions:[],
				storageHeight:0,
				suggestionHeight:0,
				index:0,
				storages:[],
				isInput:false,
				modelWidth:0,
				modelHeight:0,
				windowWidth:0,
				justifyContent:"flex-start",
				currentPage:0,
				buttonLeft:0,
				buttonOpacity1:1,
				buttonOpacity2:1,
				color:'rgba(102,205,170,1)'
			}
		},
		methods:{
			request(){
				this.isInput=true;
				var url='https://apis.map.qq.com/ws/place/v1/suggestion?keyword='+this.position+'&location='
					+this.$store.state.currentLocation.latitude+','+this.$store.state.currentLocation.longitude+'&address_format=short'
					+'&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL';
				uni.request({
					url:url,
					method:'GET',
					success: (res) => {
						console.log(url)
						if(res.data.status =='0'){
							this.suggestions.splice(0);
							for(var index in res.data.data){
								this.suggestions.push({
									id:res.data.data[index].id,
									title: res.data.data[index].title,
									address: res.data.data[index].address,
									category: res.data.data[index].category,
									location: res.data.data[index].location,
									distance: (res.data.data[index]._distance/1000).toFixed(1),
									strings: this.keyword(res.data.data[index].title,this.position),
								});
							};
						} 
					},
				})
			},
			keyword(title,position){
				if(title.includes(position)){
					var html=(title.replace(
						position, 
						'<span style="color:rgb(102,205,170);">'+ position +'</span>'
					));
					html='<span class="text1">'+html+'</span>';
					return html;
				}else{
					return title;
				}
			},
			tap(id,title,location,category){
				var keys=uni.getStorageInfoSync().keys;
				uni.setStorageSync(id,
					{
						title:title,
						location:location,
						category:category
					}
				);
				if(keys.length>=10){
					uni.removeStorageSync(keys[0]);
				}
				uni.request({
					url:'https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from='
					+this.$store.state.currentLocation.latitude+','+this.$store.state.currentLocation.longitude+'&to='
					+location.lat+','+location.lng+'&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL',
					success: (res) => {
						this.$store.commit('setDestination',{
							title:title,
							location:location,
							distance:(res.data.result.rows[0].elements[0].distance/1000).toFixed(1),
						});
					}
				});
				
				uni.navigateBack();
				this.$store.commit('addIsLow');
			},
			tapStorage(title,location){
				uni.request({
					url:'https://apis.map.qq.com/ws/distance/v1/matrix/?mode=driving&from='
					+this.$store.state.currentLocation.latitude+','+this.$store.state.currentLocation.longitude+'&to='
					+location.lat+','+location.lng+'&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL',
					success: (res) => {
						this.$store.commit('setDestination',{
							title:title,
							location:location,
							distance:(res.data.result.rows[0].elements[0].distance/1000).toFixed(1),
						});
					}
				});
				uni.navigateBack();
				this.$store.commit('addIsLow');
			},
			clear(){
				uni.clearStorageSync();
				this.storages.splice(0);
			},
			del(index){
				this.storages.splice(index,1);
				var keys=uni.getStorageInfoSync().keys;
				if(keys!=null){
					var k=0;
					for(var i in keys){
						if(uni.getStorageSync(keys[i]).title!=null){
							if(k==index){
								uni.removeStorageSync(keys[i]);
								break;
							}
							k++;
						}
					}
				}
			},
			change(e){
				// var currentPage=e.detail.current;
				// // console.log(currentPage)
				// if(currentPage==0){
				// 	console.log(currentPage)
				// 	uni.setNavigationBarColor({
				// 		frontColor:'#ffffff',
				// 		backgroundColor:'#66cdaa',
				// 		animation:{
				// 			duration:300,
				// 			timingFunc:'easeInOut'
				// 		}
				// 	});
				// }else{
				// 	console.log(currentPage)
				// 	uni.setNavigationBarColor({
				// 		frontColor:'#ffffff',
				// 		backgroundColor:'#3787e6',
				// 		animation:{
				// 			duration:300,
				// 			timingFunc:'easeInOut'
				// 		}
				// 	});
				// }
				
			},
			animationfinish(e){
				this.currentPage=e.detail.current;
			},
			transition(e){
				// 55,135,230 蓝
				// 102,205,170 绿
				var dx=e.detail.dx;
				var percent=Math.abs(dx)/this.windowWidth;
				if(this.currentPage==0){//向右翻页
					if(percent<=0.5){
						this.$nextTick(function(){
							this.justifyContent="flex-start";
						})
						
						this.modelWidth=uni.upx2px(120+280*percent*2);
						// this.modelHeight=uni.upx2px(15-8*percent*2);
					}else{
						this.$nextTick(function(){
							this.justifyContent="flex-end";
						})
						
						this.modelWidth=uni.upx2px(400-280*(percent-0.5)*2);
						// this.modelHeight=uni.upx2px(7+8*(percent-0.5)*2);
						// console.log(this.modelWidth)
					}
					this.buttonLeft=uni.upx2px(185-185*percent);
					this.buttonOpacity1=1-percent;
					this.buttonOpacity2=percent;
					this.color="rgba("+(102-47*percent)+","+(205-70*percent)+","+(170+60*percent)+",1)";
					
				}else{
					if(percent<=0.5){
						this.$nextTick(function(){
							this.justifyContent="flex-end";
						})
						
						this.modelWidth=uni.upx2px(120+280*percent*2);
						// this.modelHeight=uni.upx2px(15-8*percent*2);
					}else{
						this.$nextTick(function(){
							this.justifyContent="flex-start";
						})
						
						this.modelWidth=uni.upx2px(400-280*(percent-0.5)*2);
						// this.modelHeight=uni.upx2px(7+8*(percent-0.5)*2);
						// 
					}
					this.buttonLeft=uni.upx2px(185*percent);
					this.buttonOpacity1=percent;
					this.buttonOpacity2=1-percent;
					this.color="rgba("+(55+47*percent)+","+(135+70*percent)+","+(230-60*percent)+",1)";
				}
			}
		}, 
		mounted(){
			var keys=uni.getStorageInfoSync().keys;
			if(keys!=null){
				
				for(var index in keys){
					if(uni.getStorageSync(keys[index]).title!=null){
						this.storages.push(uni.getStorageSync(keys[index]));
					}else{
						uni.removeStorageSync(keys[index]);
					}
				}
			}
			this.windowWidth=uni.getSystemInfoSync().windowWidth;
			this.storageHeight=this.$store.state.windowHeight*0.8;
			this.suggestionHeight=this.$store.state.windowHeight*0.85;
			this.modelHeight=uni.upx2px(15);
			this.modelWidth=uni.upx2px(120);
			this.buttonLeft=uni.upx2px(185);
			this.buttonOpacity2=0;
			
		}
	}
	
</script>

<style scoped >
	.image1{
		position: absolute;
		height: 70upx;
		width: 70upx;
		left:45upx;
		bottom: 10upx;
	}
	
	.image2{
		position:relative;
		height: 50upx;
		width: 50upx;
		/* margin-right: 10upx;
		margin-bottom: 10upx; */
		bottom:7upx;
		right:10upx;
		
	}
	
	.image3{
		height: 70upx;
		width: 70upx;
		/* position:absolute;
		right:10upx; */
		/* bottom: 1upx;
		right:194upx; */
		
	}
	
	.searchbox{
		background-color:rgb(240, 240, 240) ;
	}
	
	.textareaview{
		position: relative;
	}
	
	.textarea{
		background-color: rgb(255,255,255);
		margin: 30upx;
		padding-top:21upx;
		padding-left:80upx;
		width:700upx;
		height:90upx;
		font-size: 32upx;
		letter-spacing: 1upx;
		border-radius: 20upx;
		border: 3px outset;
		/* border: 3px outset rgba(55,135,230,0.5); */
	}
	
	.searchtext{
		position: absolute;
		left:608upx;
		bottom:7upx;
		font-size: 32upx;
		color: rgba(102,205,170,1);
		/* color: rgb(55,135,230); */
		font-weight: 700;
		padding: 20upx;
		padding-left: 25upx;
	}
	
	.history{
		display: flex;
		justify-content: space-between;
	}
	
	.historyview{
		
		margin-left: 30upx;
		border-radius: 8upx;
		font-size: 30upx;
		font-weight: 700;
		letter-spacing: 1upx;
	}
	
	.commonview{
		
		margin-left: 50upx;
		border-radius: 8upx;
		font-size: 30upx;
		font-weight: 700;
		letter-spacing: 1upx;
	}
	
	.modelSelected{
		/* margin-left: 30upx; */
		border-radius: 10upx;
		/* height: 15upx;
		width: 120upx; */
		/* width: 300upx; */
		/* background-color:rgba(102,205,170,1) ; */
		/* background-color: rgb(55,135,230); */
		transition: all .1s;
	}
	
	.clear{
		background-color:rgba(102,205,170,0.2) ;
		color: rgba(102,205,170,1);
		margin-right: 30upx;
		padding: 10upx;
		font-size: 23upx;
		font-weight: 700;
		border-radius: 10upx;
		transition: all .1s;
	}
	
	.add{
		background-color: rgba(55,135,230,0.2);
		color: rgb(55,135,230);
		margin-right: 30upx;
		padding: 10upx;
		font-size: 23upx;
		font-weight: 700;
		border-radius: 10upx;
		transition: all .1s;
	}
	
	.storage{
		margin-top: 10upx;
		/* margin-bottom: 10upx; */
		/* margin-left: 30upx;
		margin-right: 30upx; */
		padding: 15upx;
		padding-left: 20upx;
		padding-bottom: 5upx;
		border-radius: 10upx;
		display: flex;
		justify-content: space-between;
		overflow: hidden;
		/* border: 2px solid red; */
	}
	
	.view5{
		position: relative;
		display: flex;
		/* border: 2px solid red; */
	}
	
	.text3{
		margin: 15upx;
		margin-top: 18upx;
		margin-bottom: 0upx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		width: 500upx;
		font-size: 29upx;
		font-weight: 700;
		letter-spacing: 1upx;
		/* border: 2px solid red; */
		/* border-bottom: 2px solid rgba(0,0,0,0.2); */
	}
	
	.view4{
		margin-top: 10upx;
		margin-right: 20upx;
		/* border: 2px solid red; */
	}
	
	.suggestion{
		/* background-color: rgb(255,255,255); */
		margin-bottom: 10upx;
		/* margin-left: 30upx;
		margin-right: 30upx; */
		border-radius: 10upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		position: relative;
	}
	
	.view1{
		margin: 20upx;
		/* margin-top: 20upx; */
		margin-bottom: 10upx;
		margin-left: 30upx;
		font-size: 30upx;
		font-weight: 700;
		letter-spacing: 1upx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		display: flex;
	}
	
	.text2{
		background-color:rgba(102,205,170,0.2) ;
		color: rgba(102,205,170,1);
		/* background-color: rgba(55,135,230,0.2);
		color: rgb(55,135,230); */
		margin-left: 80upx;
		padding: 10upx;
		font-size: 20upx;
	}
	
	.view3{
		margin: 20upx;
		/* margin-top: 30upx; */
		margin-left: 82upx;
	}
	
	.text6{
		background-color:rgba(102,205,170,0.2) ;
		color: rgba(102,205,170,1);
		/* background-color: rgba(55,135,230,0.2);
		color: rgb(55,135,230); */
		margin-left: 100upx;
		margin-bottom: 20upx;
		padding: 10upx;
		font-size: 20upx;
		
	}
</style>
