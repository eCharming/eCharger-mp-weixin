<template>
	<button 
		id="btn" 
		class="btn" 
		:plain="true"  
		:style="{'border-color':bordercolor}"
	>
		{{text}}
	</button>
</template>

<script>
	export default{
		data(){
			return {
				bordercolor:"rgba(0,0,0,0.3)",
			}
			
		},
		props:{
			text:{
				type:String
			},
			selected:{
				type:Boolean,
				default:false
			}
		},
		watch:{
			test:{ 
				selected:function(){
				console.log(1)
				if(this.selected)
					activate();
				else inactivate();
				}
			}
			
		},
		methods:{
			activate(){
				this.bordercolor="rgba(102,205,170,1)";
			},
			inactivate(){
				this.bordercolor="rgba(0,0,0,0.3)";
			} 
		}
	}
</script>

<style scoped lang="scss">
	.btn{
		height: 120upx;
		width:270upx;
		radius: 15px;
		margin: 20upx;
		border: 2px solid;
	}
</style>
