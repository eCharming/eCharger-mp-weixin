<template>
	<view>
		<mymap></mymap>
		<movebox></movebox>
		<!-- <scroller></scroller> -->
	</view>
</template>

<script>
	
	import movebox from '@/components/moveBox.vue'
	import mymap from '../../components/myMap.vue'
	
	import scroller from '../../components/scroller.vue'
	export default {
		components: {
			movebox,
			mymap,
			scroller,
		},
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
