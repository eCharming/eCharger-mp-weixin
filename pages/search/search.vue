<template>
	<view class="searchbox">
		
		<textarea class="textarea" 
			placeholder="输入目的地"
			auto-focus="true"
			v-model="position"
			@input="request()"
		>
			<image src="../../static/image/lightning_green.png" class="image1"></image>
		</textarea>
		<scroll-view
			scroll-y="true" :style="{'height': height+'px'}"
		>
			<view @tap="clear">清空历史记录</view>
			<view
				class="storage"
				v-if="!isInput"
				v-for="(storage,index) in storages"
				:key="index"
				@tap="tapStorage(storage.title,storage.location)"
			>
				
				<text>{{storage.title}}</text>
			</view>
			
			<view class="suggestion" 
				v-if="isInput"
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
				isInput:false
			}
		},
		methods:{
			request(){
				this.isInput=true;
				uni.request({
					url:'https://apis.map.qq.com/ws/place/v1/suggestion?keyword='+this.position+'&key=HVTBZ-KOFW6-JDUSX-ESY54-6WWQK-LEF73',
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
								console.log(res.data.data[index].id)
							}
						} 
						// else {
						// 	this.suggestions.splice(0);
						// 	uni.showToast({
						// 		icon:'loading',
						// 		title:'网络请求失败'
						// 	})
						// }
					},
					// fail() { //失败
					// 	this.suggestions.splice(0);
					// 	uni.showToast({
					// 		icon:'loading',
					// 		title:'网络请求失败'
					// 	})
					// },
				})
			},
			tap(id,title,location){
				var keys=uni.getStorageInfoSync().keys;
				uni.setStorage({
					key:id,
					data:{
						title:title,
						location:location
					}
				});
				if(keys.length>=10){
					uni.removeStorageSync(keys[0]);
				}
				this.$store.commit('setDestination',title);
				this.$store.commit('setDestinationLocation',location);
				uni.navigateBack();
			},
			tapStorage(title,location){
				console.log(title)
				this.$store.commit('setDestination',title);
				this.$store.commit('setDestinationLocation',location);
				uni.navigateBack();
			},
			clear(){
				uni.clearStorageSync();
			}
		}, 
		mounted(){
			var keys=uni.getStorageInfoSync().keys;
			if(keys!=null){
				for(var index in keys){
					if(uni.getStorageSync(keys[index])!=null)
						this.storages.push(uni.getStorageSync(keys[index]));
					
				}
				this.storages.reverse();
			}
			
			this.height=uni.getSystemInfoSync().windowHeight*0.9;
		}
	}
	
</script>

<style>
	.image1{
		position: relative;
		height: 70upx;
		width: 70upx;
		right:65upx;
		bottom: 15upx; 
	}
	.image2{
		position:relative;
		height: 50upx;
		width: 50upx;
		top:15upx;
		right:10upx;
		
	}
	
	.searchbox{
		background-color:rgb(240, 240, 240) ;
	}
	
	.textarea{
		background-color: rgb(255,255,255);
		margin: 30upx;
		padding:27upx;
		padding-left:80upx;
		width:700upx;
		height:90upx;
		font-size: 32upx;
		letter-spacing: 1upx;
		border-radius: 10upx;
	}
	
	.storage{
		font-size: 30upx;
		background-color: rgb(255,255,255);
		margin: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
		padding: 15upx;
		padding-left: 30upx;
		border-radius: 10upx;
		display: flex;
		flex-direction: column;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	
	.suggestion{
		background-color: rgb(255,255,255);
		margin: 20upx;
		margin-left: 30upx;
		margin-right: 30upx;
		border-radius: 10upx;
		display: flex;
		flex-direction: column;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	
	.view1{
		margin: 20upx;
		margin-left: 30upx;
		font-size: 30upx;
		font-weight: 700;
		letter-spacing: 1upx;
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
