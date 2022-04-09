import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
    state: {
		uid:null,//用户id
		avatarUrl:null,//用户头像
		userName:null,//用户名
		currentLocation:null, //用于记录自身的经纬度坐标
        locationres:null,
		destination:null,	//用于记录目的地的名字、地址、经纬度坐标以及和现在位置的距离
		relocate:true,		
		orders:[],			//用于从mymap组件中拉取order的信息给movablebox
		orderSelected:null,	//用于向mymap组件传递movablebox中选择的order编号
		markerSelected:null, //用于向movablebox组件传递mymap中选择的marker编号
		navigateSelected:null,//用于向mymap组件传递需要导航的marker编号
		isLow:0,	//用于从search页面返回时收起movablebox至低位
		windowHeight:null,
		buttonSelected:1,
		color:"rgb(50,200,210)",
		
    },
    mutations: {
		setUid(state,uid){
			state.uid=uid;
		},
		setAvatarUrl(state,avatarUrl){
			state.avatarUrl=avatarUrl;
		},
		setUserName(state,userName){
			state.userName=userName;
		},
		setCurrentLocation(state,currentLocation){
			state.currentLocation=currentLocation;
		},
        setLocationRes(state, locationres){
            state.locationres = locationres;
        },
		setDestination(state,destination){
			state.destination=destination;
		},
		setRelocate(state,relocate){
			state.relocate=relocate;
		},
		setOrders(state,orders){
			state.orders=orders;
		},
		setOrderSelected(state,orderSelected){
			state.orderSelected=orderSelected;
		},
		setMarkerSelected(state,markerSelected){
			state.markerSelected=markerSelected;
		},
		setNavigateSelected(state,navigateSelected){
			state.navigateSelected=navigateSelected;
		},
		addIsLow(state){
			state.isLow++;
		},
		setWindowHeight(state,windowHeight){
			state.windowHeight=windowHeight;
		},
		setButtonSelected(state,buttonSelected){
			state.buttonSelected=buttonSelected;
		},
		setColor(state,color){
			state.color=color
		}
    },
    actions: {},
    getters: {}
})