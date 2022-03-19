<template>
	<view>
		<mymap></mymap>
		<movebox>
			<card>
				<totalbutton :text="'租电桩'" :selected="isSelected1" @tap="tap1()" ></totalbutton>
				<totalbutton :text="'我的电桩'" :selected="isSelected2" @tap="tap2()" ></totalbutton>
			</card>
		</movebox>
	</view>
</template>

<script>
	import babtouchbox from '@/components/bab-Touchbox.vue'
	import totalbutton from '@/components/totalbutton.vue'
	import movebox from '@/components/moveBox.vue'
	import card from  '@/components/card.vue'
	import mymap from '../../components/myMap.vue'
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
			}
		},
		onLoad() {
			
		},
		methods: {
			tap1(){
				this.isSelected1=true;
				this.isSelected2=false;
			},
			tap2(){
				this.isSelected1=false
				this.isSelected2=true;
			}
		}
	}
</script>

<style>

</style>
