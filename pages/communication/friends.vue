<template>
	<view>
		<view class="navigator" :style="{'height':statusHeight+'px'}">
			<text class="contacter" :style="{'margin-bottom':contacterBottom+'px'}">联系人</text>
		</view>
		<scroll-view
			scroll-y="true"
			scroll-with-animation=true
			:style="{'height':scrollHeight+'px'}"
		>
			<view class="friends" v-for="friend,index in friends" :key="index" @click="click(friend.name,friend.uid)">
				<text class="name">{{friend.name}}</text>
				<text class="lastWord">{{friend.lastWord}}</text>
				<text class="lastTime">{{friend.lastTime}}</text>
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
				friends:[]
			}
		},
		methods:{
			click(name,toUid){
				uni.navigateTo({
					url: './chat?toUid='+toUid+'&name='+name,
					animationType: 'slide-in-bottom',
					animationDuration: 200
				});
			}
		},
		mounted() {
			this.statusHeight=uni.getSystemInfoSync().statusBarHeight+50;
			this.contacterBottom=(this.statusHeight-uni.getMenuButtonBoundingClientRect().bottom);
			this.scrollHeight=uni.getSystemInfoSync().windowHeight-this.statusHeight;
			this.friends.push({
				uid:1,
				name:'solaking',
				lastWord:'114514',
				lastTime:'11:45'
			},{
				uid:2,
				name:'gxnsos',
				lastWord:'1919810',
				lastTime:'19:19'
			},{
				uid:3,
				name:'d-sketon',
				lastWord:'1919810',
				lastTime:'19:19'
			},{
				uid:4,
				name:'lecter',
				lastWord:'1919810',
				lastTime:'19:19'
			})
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
		letter-spacing: 1upx;
		position: absolute;
		top:35upx;
		right: 30upx;
		color: rgba(0,0,0,0.5);
	}
</style>
