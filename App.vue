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
			console.log(uni.getStorageSync('frePlace'))
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	@import "@/static/css/global.css";
	
</style>
