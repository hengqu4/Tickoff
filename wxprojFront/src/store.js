import Vue from "vue";
import Vuex from "vuex";
import * as mutationtypes from './mutation-types'

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo:{},
    isShow:false
  },
  mutations: {
    [mutationtypes.USERINFO_MUTATION](state, value) {
      state.userInfo = value;
    },
    [mutationtypes.ISSHOW_MUTATION](state, value) {
      state.isShow = value;
    },
  },
  getters: {},
});

export default store;
