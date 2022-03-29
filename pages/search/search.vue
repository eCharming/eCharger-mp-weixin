<template>
	<view class="searchbox">
		<view class="textareaview">
			<textarea class="textarea" 
				placeholder="输入目的地"
				auto-focus="true"
				v-model="position"
				@input="request()"
			>
				
			</textarea>
			<text class="searchtext">搜索</text>
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
						<view class="modelSelected" :style="{'height':modelHeight+'px','width':modelWidth+'px'}"></view>
					</view>
				</view>
				
				<view class="clear" @tap="clear">清空历史记录</view>
			</view>
			
			<swiper :style="{'height': height+'px'}" @animationfinish="animationfinish($event)" @transition="transition($event)">
				<swiper-item>
					<scroll-view
						scroll-y="true"
						style="margin-top: 20upx;"
						:style="{'height': height+'px'}"
					>
						<view
							class="storage"
							v-for="(storage,index) in storages"
							:key="index"
							@tap="tapStorage(storage.title,storage.location)"
						>
							<view class="view5">
								<image src="../../static/image/search.png" class="image3"></image>
								<text class="text3">{{storage.title}}</text>
							</view>
							
							<view class="view4" @tap.stop.prevent="del(index)">
								<icon type="cancel" color="rgba(102,205,170,1)" style="margin-top: 10upx;"></icon>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
				<swiper-item>
					<scroll-view
						scroll-y="true"
						style="margin-top: 20upx;"
						:style="{'height': height+'px'}"
					>
						<view
							class="storage"
							v-for="(storage,index) in storages"
							:key="index"
							@tap="tapStorage(storage.title,storage.location)"
						>
							<view class="view5">
								<image src="../../static/image/search.png" class="image3"></image>
								<text class="text3">{{storage.title}}</text>
							</view>
							
							<view class="view4" @tap.stop.prevent="del(index)">
								<icon type="cancel" color="rgba(102,205,170,1)" style="margin-top: 10upx;"></icon>
							</view>
						</view>
					</scroll-view>
				</swiper-item>
			</swiper>
			
			
		</view>
		
		<scroll-view
			v-if="isInput"
			scroll-y="true"  
			:style="{'height': height+'px'}"
		>		
			<view class="suggestion" 
				
				v-for="(suggestion,index) in suggestions" 
				:key="index"
				@tap="tap(suggestion.id,suggestion.title,suggestion.location)"
			>
				<view class="view1">
					<image class="image2" src="../../static/image/landmark.png"></image>
					<text class="text1">{{suggestion.title}}</text>
				</view>
				<view class="view2">
					<text class="text2">{{suggestion.category}}</text>
				</view>
				<view class="view3">
					<text>{{suggestion.address}}</text>
				</view>
			</view>
		</scroll-view>
		
		
		
	</view>
</template>

<script>
	export default{
		data(){
			return{
				position:"",
				suggestions:[],
				height:0,
				index:0,
				storages:[],
				isInput:false,
				modelWidth:0,
				modelHeight:0,
				windowWidth:0,
				justifyContent:"flex-start",
				currentPage:0,
			}
		},
		methods:{
			request(){
				this.isInput=true;
				uni.request({
					url:'https://apis.map.qq.com/ws/place/v1/suggestion?keyword='+this.position+'&key=ORFBZ-V73LX-N3Z4Y-Z3MR4-V35MJ-LNBFL',
					method:'GET',
					success: (res) => {
						if(res.data.status =='0'){
							this.suggestions.splice(0);
							
							for(var index in res.data.data){
								this.suggestions.push({
									id:res.data.data[index].id,
									title: res.data.data[index].title,
									address: res.data.data[index].address,
									category: res.data.data[index].category,
									location: res.data.data[index].location,
								});
							}
						} 
					},
				})
			},
			tap(id,title,location){
				var keys=uni.getStorageInfoSync().keys;
				uni.setStorageSync(id,
					{
						title:title,
						location:location
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
			animationfinish(e){
				console.log(e)
				this.currentPage=e.detail.current;
				console.log(this.currentPage)
			},
			transition(e){
				
				var dx=e.detail.dx;
				var percent=Math.abs(dx)/this.windowWidth;
				if(this.currentPage==0){//向右翻页
					if(percent<=0.5){
						this.$nextTick(function(){
							this.justifyContent="flex-start";
						})
						
						this.modelWidth=uni.upx2px(120+280*percent*2);
						this.modelHeight=uni.upx2px(15-8*percent*2);
					}else{
						this.$nextTick(function(){
							this.justifyContent="flex-end";
						})
						
						this.modelWidth=uni.upx2px(400-280*(percent-0.5)*2);
						this.modelHeight=uni.upx2px(7+8*(percent-0.5)*2);
						// console.log(this.modelWidth)
					}
				}else{
					if(percent<=0.5){
						this.$nextTick(function(){
							this.justifyContent="flex-end";
						})
						
						this.modelWidth=uni.upx2px(120+280*percent*2);
						this.modelHeight=uni.upx2px(15-8*percent*2);
					}else{
						this.$nextTick(function(){
							this.justifyContent="flex-start";
						})
						
						this.modelWidth=uni.upx2px(400-280*(percent-0.5)*2);
						this.modelHeight=uni.upx2px(7+8*(percent-0.5)*2);
						// 
					}
				}
			}
		}, 
		mounted(){
			var keys=uni.getStorageInfoSync().keys;
			if(keys!=null){
				
				for(var index in keys){
					if(uni.getStorageSync(keys[index]).title!=null){
						this.storages.push(uni.getStorageSync(keys[index]));
					}
				}
			}
			this.windowWidth=uni.getSystemInfoSync().windowWidth;
			this.height=uni.getSystemInfoSync().windowHeight*0.85;
			this.modelHeight=uni.upx2px(15);
			this.modelWidth=uni.upx2px(120);
			
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
		top:15upx;
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
		border: 3px solid rgba(102,205,170,0.5);
	}
	
	.searchtext{
		position: absolute;
		left:608upx;
		bottom:7upx;
		font-size: 32upx;
		color: rgba(102,205,170,1);
		font-weight: 700;
		padding: 20upx;
		padding-left: 25upx;
		/* border-left: 3px solid rgba(102,205,170,0.5); */
	}
	
	.history{
		display: flex;
		justify-content: space-between;
	}
	
	.historyview{
		
		margin-left: 30upx;
		border-radius: 8upx;
		/* box-shadow:0px 5px 5px -3px rgba(102,205,170,1) ; */
		/* border-bottom: 3px solid rgba(102,205,170,1); */
		font-size: 30upx;
		font-weight: 700;
		letter-spacing: 1upx;
	}
	
	.commonview{
		
		margin-left: 50upx;
		border-radius: 8upx;
		/* box-shadow:0px 5px 5px -3px rgba(102,205,170,1) ; */
		/* border-bottom: 3px solid rgba(102,205,170,1); */
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
		background-color:rgba(102,205,170,1) ;
		transition: all .1s;
	}
	
	.clear{
		background-color:rgba(102,205,170,0.2) ;
		color: rgba(102,205,170,1);
		margin-right: 30upx;
		padding: 10upx;
		font-size: 23upx;
		font-weight: 700;
	}
	
	.storage{
		background-color: rgb(255,255,255);
		margin: 30upx;
		/* margin-left: 30upx;
		margin-right: 30upx; */
		padding: 15upx;
		padding-left: 20upx;
		border-radius: 10upx;
		display: flex;
		justify-content: space-between;
	}
	
	.view5{
		position: relative;
		display: flex;
		
	}
	
	.text3{
		margin: 15upx;
		margin-top: 18upx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
		width: 500upx;
		font-size: 29upx;
		font-weight: 700;
		letter-spacing: 1upx;
	}
	
	.view4{
		margin-top: 10upx;
		margin-right: 20upx;
		/* border: 2px solid red; */
	}
	
	.suggestion{
		background-color: rgb(255,255,255);
		margin: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
		border-radius: 10upx;
		display: flex;
		flex-direction: column;
	}
	
	.view1{
		margin: 20upx;
		margin-left: 30upx;
		font-size: 30upx;
		font-weight: 700;
		letter-spacing: 1upx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	
	.text2{
		background-color:rgba(102,205,170,0.2) ;
		color: rgba(102,205,170,1);
		margin-left: 80upx;
		padding: 10upx;
		font-size: 20upx;
	}
	
	.view3{
		margin: 20upx;
		margin-left: 82upx;
	}
</style>
