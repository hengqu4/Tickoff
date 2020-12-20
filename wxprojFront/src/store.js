import Vue from "vue";
import Vuex from "vuex";
import * as mutationtypes from './mutation-types'

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo:{},
    openId:'open91cd84d64333821d73e2751f',
    sessionId_3rd:'2674983f429d11eb8ae1525400094d64',
  },
  mutations: {
    [mutationtypes.USERINFO_MUTATION](state, value) {
      state.userInfo = value;
    },
    [mutationtypes.OPENID_MUTATION](state, value) {
      state.openId = value;
    },
    [mutationtypes.SESSIONID3RD_MUTATION](state, value) {
      state.sessionId_3rd = value;
    },
  },
  getters: {},
});

export default store;
