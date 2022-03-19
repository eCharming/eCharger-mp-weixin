<template>
	<view>
		<mymap></mymap>
		<movebox ref="movebox">
			<card>
				<totalbutton :text1="'租电桩'" :text2="'电桩共享'" :selected="isSelected1" @tap="tap1()" ></totalbutton>
				<totalbutton :text1="'借电桩'" :text2="'出租电桩'" :selected="isSelected2" @tap="tap2()" ></totalbutton>
			</card>
		</movebox>
	</view>
</template>

<script>
	import babtouchbox from '@/components/bab-Touchbox.vue'
	import totalbutton from '@/components/totalbutton.vue'
	import movebox from '@/components/moveBox.vue'
	import card from '@/components/card.vue'
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
				isSelected1: true,
				isSelected2: false,
			}
		},
		computed: {
			test() {
				console.log(this.$refs.movebox);
				return this.$ref;
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
