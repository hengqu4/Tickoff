import Vue from "vue";
import Vuex from "vuex";
import * as mutationtypes from './mutation-types'

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo:{},
    openId:123,
  },
  mutations: {
    [mutationtypes.USERINFO_MUTATION](state, value) {
      state.userInfo = value;
    },
    [mutationtypes.OPENID_MUTATION](state, value) {
      state.openId = value;
    },
  },
  getters: {},
});

export default store;
