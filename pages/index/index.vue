<template>
	<view>
		<map 
			style="width: 100%; height: 100vh;" 
			:latitude="latitude" 
			:longitude="longitude" 
			:markers="covers"
		>
		</map>
		
		<babtouchbox maxHeight="0.7">
			<totalbutton :text="111"></totalbutton>
		</babtouchbox>
		
		<!-- <u-tabbar>
			<u-tabbar-item text="附近" :icon="nearicon"></u-tabbar-item>
			<u-tabbar-item text="租电桩" :icon="bicon"></u-tabbar-item>
			<u-tabbar-item text="我的" :icon="myicon"></u-tabbar-item>
		</u-tabbar> -->
		
		
	</view>
</template>

<script>
	import babtouchbox from '../../components/bab-Touchbox.vue'
	import totalbutton from '../../components/totalbutton.vue'
	export default {
		components:{
			babtouchbox,
			totalbutton
		},
		data() {
			return {
				// 使用 marker点击事件 需要填写id
				title: 'map',
				latitude: 39.909,
				longitude: 116.39742,
				covers: [{
					id:1,
					latitude: 39.909,
					longitude: 116.39742,
					iconPath: '../../static/pos.png',
					height:30,
					width:30,
					anchor:{x: .5, y: 1}
				}],
				myicon:require("@/static/my.png"),
				nearicon:require("@/static/near.png"),
				bicon:require("@/static/b.png"),
				
			}
		},
		onLoad() {
			uni.getLocation({
			    type: 'wgs84',
			    success:res=>{
					console.log(1);
					console.log(res.latitude,res.longitude);
					this.latitude=res.latitude;
					this.longitude=res.longitude;
					this.covers[0].latitude=res.latitude;
					this.covers[0].longitude=res.longitude;
			    }
			});
			
		},
	
		methods: {

		}
	}
</script>

<style>
	
</style>
