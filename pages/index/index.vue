<template>
	<view>
		<mymap></mymap>
		<movebox>
			<template slot="locationBtn">
				<locationbutton />
				</locationbutton>
			</template>
			<template slot="card">
				<card>
					<totalbutton :text1="'租电桩'" :text2="'电桩共享'" :selected="isSelected1" @tap="tap1()"></totalbutton>
					<totalbutton :text1="'借电桩'" :text2="'出租电桩'" :selected="isSelected2" @tap="tap2()"></totalbutton>
				</card>
			</template>
		</movebox>
	</view>
</template>

<script>
	import totalbutton from '@/components/totalbutton.vue'
	import movebox from '@/components/moveBox.vue'
	import card from '@/components/card.vue'
	import mymap from '@/components/myMap.vue'
	import locationbutton from '@/components/locationButton.vue'
	export default {
		components: {
			totalbutton,
			movebox,
			mymap,
			card,
			locationbutton
		},
		data() {
			return {
				isSelected1: true,
				isSelected2: false,
			}
		},
		methods: {
			tap1() {
				this.isSelected1 = true;
				this.isSelected2 = false;
			},
			tap2() {
				this.isSelected1 = false
				this.isSelected2 = true;
			}
		},
	}
</script>

<style>

</style>
