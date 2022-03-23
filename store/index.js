import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
    state: {
        locationres:null,
		destination:'请输入你的目的地',
		destinationLocation:null,
		relocate:true,
    },
    mutations: {
        setLocationRes(state, locationres){
            state.locationres = locationres;
        },
		setDestination(state,destination){
			state.destination=destination;
		},
		setDestinationLocation(state,destinationLocation){
			state.destinationLocation=destinationLocation;
		},
		setRelocate(state,relocate){
			state.relocate=relocate;
		}
    },
    actions: {},
    getters: {}
})