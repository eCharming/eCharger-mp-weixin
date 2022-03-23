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
			<view class="suggestion" 
				v-for="(suggestion,index) in suggestions" 
				:key="index"
				@tap="tap(suggestion.title)"
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
			}
		},
		methods:{
			request(){
				
				uni.request({
					url:'https://apis.map.qq.com/ws/place/v1/suggestion?keyword='+this.position+'&key=HVTBZ-KOFW6-JDUSX-ESY54-6WWQK-LEF73',
					method:'GET',
					success: (res) => {
						if(res.data.status =='0'){
							this.suggestions.splice(0);
							for(var index in res.data.data){
								this.suggestions.push({
									title: res.data.data[index].title,
									address: res.data.data[index].address,
									category: res.data.data[index].category,
									location: res.data.data[index].location,
								})
								
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
			tap(title){
				console.log(title)
				
				this.$store.commit('setDestination',title);
				
				uni.navigateBack();
			}
		}, 
		mounted(){
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
