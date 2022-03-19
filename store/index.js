import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);


export default new Vuex.Store({
    state: {
        locationres:null
    },
    mutations: {
        setLocationRes(state, locationres){
            state.locationres = locationres
        }
    },
    actions: {},
    getters: {}
})