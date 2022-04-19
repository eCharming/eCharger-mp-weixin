<template>
	<view>
		<view class="biglabel">{{alphabet}}</view>
		<view class="card">
			<view v-if="alist.length!=0" v-for='(item,index) in list' :key='index' class="display" @tap="changeCity(item)">
				<view class="labeltext">{{item.name}}</view>
				<view v-if="index!=list.length-1" class="divLine"></view>
			</view>
		</view>
	</view>
</template>

<script>
	import addcard from '../components/addCard.vue'
	export default {
		components: {
			addcard
		},
		props: {
			alphabet: {
				type: String,
			},
			list: {
				type: Array,
				default: () => []
			}
		},
		data() {
			return {

			};
		},
		methods:{
			changeCity(item) {
				
			}
		}
	}
</script>

<style scoped>
	.display {
		margin: 15upx;
	}

	.divLine {
		width: 100%;
		height: 3upx;
		background-color: #E0E3DA;
		margin-top: 18rpx;
	}

	.labeltext {
		letter-spacing: 2upx;
		font-weight: 700;
	}

	.biglabel {
		margin: 20upx;
		margin-left:30upx;
		padding: 15upx;
		font-weight: 900;
		font-size: 900;
		color: #666666;
	}
	.card{
		margin:20upx;
		padding: 15upx;
		margin-left:30upx;
		margin-right:30upx;
		background-color: rgba(250,255,250,1);
		border-radius: 20upx;
		display: flex;
		flex-direction:column;
		justify-content: space-between;
		box-shadow:0upx 12upx 24upx 8upx #e5e5e5 ;
	}
</style>
