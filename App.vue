<script>
	export default {
		onLaunch: function() {
			let frePlace= uni.getStorageSync('frePlace')
			if(frePlace=='') {
				let obj={
					freHome:{},
					freCompany:{},
					freSchool:{},
					freOther:[]
				}
				uni.setStorageSync('frePlace',JSON.stringify(obj))
			}
		},
	}
</script>

<style>
	/*每个页面公共css */
	@import "@/static/css/global.css";
	
</style>
