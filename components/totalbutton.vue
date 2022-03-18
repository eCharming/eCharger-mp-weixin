<template>
	<button id="btn" class="btn" :plain="true"  @click="active()">{{text}}</button>
</template>

<script>
	export default{
		name:"btn",
		props:['text'],
		data(){
			return {
				// style:"width:42%;border: 2px solid #66CDAA;border-radius:10px;color:#66CDAA;position:relative;"
				estyle:{width:'42%',border: '2px solid #66CDAA',radius:'10px',color:'#66CDAA',position:'relative'},
					
				
					
					
					
					// 'top':'20upx' 
				
			}
			
		},
		methods:{
			active:function(){
				this.estyle.color='#000000';
				console.log(1);
			} 
		}
	}
</script>

<style>
	
</style>
