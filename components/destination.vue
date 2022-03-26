<template>
	<view class="card">
		<view class="near">
			<text class="text1">{{month}}月{{date}}日星期{{day}}</text>
			
			<view class="time1" >
				<picker mode="time" :start="minTime1" :end="maxTime1" @change="changetime1">
					<text class="timetext1" :style="{'opacity':opacity1}">{{text1}}</text>
				</picker>
			</view>
			<view class="line">-</view>
			<view class="time2" >
				<picker mode="time" :start="minTime2" :end="maxTime2" @change="changetime2">
					<text class="timetext2" :style="{'opacity':opacity2}">{{text2}}</text>
				</picker>
			</view>
		</view>
		
		<view class="destination"
			@tap="navigate()">
			<image class="image1" src="../static/image/lightning_green.png"></image>
			<text class="text2" :style="{'color':color}">{{destination}}</text>
			
		</view>
	</view>
</template>

<script>
	export default{
		components:{
		},
		data(){
			return{
				month:0,
				date:0,
				day:0,
				time1:'12:01',
				time2:'12:01',
				minTime1:'00:00',
				maxTime1:'24:00',
				minTime2:'00:00',
				maxTime2:'24:00',
				text1:"起始时间",
				text2:"结束时间",
				opacity1:0.5,
				opacity2:0.5,
				destination:"请输入你的目的地",
				color:"rgb(0,0,0)",
			}
		},
		methods:{
			changetime1(e){
				this.time1=e.detail.value;
				this.text1=this.time1;
				this.minTime2=this.time1;
				this.opacity1=1;
			},
			changetime2(e){
				this.time2=e.detail.value;
				this.text2=this.time2;
				this.maxTime1=this.time2;
				this.opacity2=1;
			},
			navigate(){
				uni.navigateTo({
					url: '../search/search',
					animationType: 'slide-in-bottom',
					animationDuration: 200
				});
			}
		},
		mounted(){
			var date=new Date();
			this.month=date.getMonth()+1;
			this.date=date.getDate();
			this.day=date.getDay();
			if(this.day===0) this.day="天";
			else if(this.day===1) this.day="一";
			else if(this.day===2) this.day="二";
			else if(this.day===3) this.day="三";
			else if(this.day===4) this.day="四";
			else if(this.day===5) this.day="五";
			else if(this.day===6) this.day="六";
		},
		watch:{
			'$store.state.destination'() {
				this.destination = this.$store.state.destination.title;
				this.color = "rgb(102,205,170)"
			},
		}
	}
</script>

<style scoped>
	.image1{
		position: absolute;
		top:20upx;
		width:60upx;
		height:60upx;
	}
	
	.card{
		margin:20upx;
		padding: 15upx;

		background-color: rgba(250,255,250,1);
		border-radius: 40upx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		box-shadow:0px 6px 12px 4px #e5e5e5 ;
	} 
	
	.near{
		
		margin: 15upx;
		margin-left: 45upx;
		letter-spacing: 3upx;
		font-weight: 700;
		font-size: 31upx;
		display: flex;
		
	}
	
	.destination{
		border-radius: 20upx;
		margin-top: 5upx;
		margin-bottom: 15upx;
		margin-left: 25upx;
		margin-right: 25upx;
		padding: 23upx;
		background-color: rgb(230,230,230);
		position: relative;
		font-weight: 700;
		letter-spacing: 2upx;
		font-size: 35upx;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	
	.text1{
		color:rgb(102,205,170) ;
	}
	
	.text2{
		position: relative;
		left:60upx;
	}
	
	.time1{
		// background-color: rgb(230,230,230);
		height:56upx;
		width:144upx;
		border-radius: 12upx;
		position: relative;
		bottom:1upx;
		left:30upx;
		background-color: rgb(230,230,230);
	}
	
	.timetext1{
		position: relative;
		top:10upx;
		left:18upx;
		font-size: 27upx;
		letter-spacing: 1upx;
	}
	
	.timetext2{
		position: relative;
		top:10upx;
		left:18upx;
		font-size: 27upx;
		letter-spacing: 1upx;
	}
	
	.line{
		position: relative;
		top:2upx;
		left:45upx;
	}
	
	.time2{
		// background-color: rgb(230,230,230);
		height:56upx;
		width:144upx;
		border-radius: 12upx;
		position: relative;
		bottom:1upx;
		left:60upx;
		background-color: rgb(230,230,230);
	}
	
</style>
