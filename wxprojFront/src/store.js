import Vue from "vue";
import Vuex from "vuex";
import * as mutationtypes from './mutation-types'

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo:{},
  },
  mutations: {
    [mutationtypes.USERINFO_MUTATION](state, value) {
      state.userInfo = value;
    },
  },
  getters: {},
});

export default store;
