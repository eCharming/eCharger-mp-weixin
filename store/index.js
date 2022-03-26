import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
    state: {
		currentLocation:null,
        locationres:null,
		destination:null,
		relocate:true,
		orders:[],
		
    },
    mutations: {
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
    },
    actions: {},
    getters: {}
})