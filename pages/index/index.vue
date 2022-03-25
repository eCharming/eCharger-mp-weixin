<template>
	<view>
		<mymap></mymap>
		<!-- <movebox></movebox> -->
		<movablebox></movablebox>
	</view>
</template>

<script>
	
	import movebox from '@/components/moveBox.vue'
	import movablebox from '@/components/movableBox.vue'
	import mymap from '../../components/myMap.vue'
	export default {
		components: {
			movebox,
			movablebox,
			mymap,
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
