<template>
	<view>
		
		<!-- <map id="myMap" style="width: 100%; height: 100vh;" :latitude="latitude" :longitude="longitude"
			:markers="covers" :setting="mapSetting" :circles="circles">
		</map> -->
		<mymap></mymap>
		<movebox>
			<card>
				<totalbutton :text="111" :selected="isSelected1" @tap="tap1()" ></totalbutton>
				<totalbutton :text="111" :selected="isSelected2" @tap="tap2()" ></totalbutton>
			</card>
			
		</movebox>
		<!-- <babtouchbox maxHeight="1" minHeight="0.1">
		</babtouchbox> -->

		


	</view>
</template>

<script>
	import babtouchbox from '@/components/bab-Touchbox.vue'
	import totalbutton from '@/components/totalbutton.vue'
	import movebox from '@/components/moveBox.vue'
	import card from  '@/components/card.vue'
	import mymap from '../../components/myMap.vue'
	import QQMapWX from '@/static/js/qqmap-wx-jssdk.min.js'
	export default {
		components: {
			babtouchbox,
			totalbutton,
			movebox,
			mymap,
			card
		},
		data() {
			return {
				isSelected1:true,
				isSelected2:false,
			// 	latitude: 39.909,
			// 	longitude: 116.39742,
			// 	mapSetting: {
			// 		"showCompass": true,
			// 		"enableOverlooking": false,
			// 		"enableSatellite": false,
			// 		"enableTraffic": false,
			// 		"enableZoom": true,
			// 		"enableScroll": true,
			// 		"enableRotate": true,
			// 		"showLocation": true,
			// 		"subkey": "HVTBZ-KOFW6-JDUSX-ESY54-6WWQK-LEF73"
			// 	},
			// 	covers: [],
			// 	circles: []
			}
		},
		onLoad() {
			// uni.getLocation({
			// 	type: 'gcj02',
			// 	success: res => {
			// 		this.latitude = res.latitude;
			// 		this.longitude = res.longitude;
			// 		this.circles.push({
			// 			latitude: this.latitude,
			// 			longitude: this.longitude,
			// 			fillColor: "#4162996A",
			// 			color: "#b0daff",
			// 			radius: 300,
			// 			strokeWidth: 1,
			// 		})
			// 	}
			// });
		},
		methods: {
			tap1(){
				this.isSelected1=true;
				this.isSelected2=false;
				console.log(this.isSelected1)
			},
			tap2(){
				this.isSelected1=false
				this.isSelected2=true;
				console.log(this.isSelected1)
			}
		}
	}
</script>

<style>

</style>
