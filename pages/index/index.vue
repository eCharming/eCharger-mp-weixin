<template>
	<view>
		<mymap></mymap>
		<movablebox></movablebox>
	</view>
</template>

<script>
	import movablebox from '@/components/movableBox.vue'
	import mymap from '../../components/myMap.vue'
	export default {
		components: {
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

<style scoped>
</style>
