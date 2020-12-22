import Vue from "vue";
import Vuex from "vuex";
import * as mutationtypes from './mutation-types'

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo:{},

    token:'',
    openId: 'open91cd84d64333821d73e2751f',
    token:'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcGVuaWQiOiJvcGVuOTFjZDg0ZDY0MzMzODIxZDczZTI3NTFmIiwiZXhwIjoxNjA4NjEwNjQyfQ.Aga9JN0ALFeNW3Oj8P4FpWiPJ2UGOChYMjvNIYJRxU8',
    sessionId_3rd: '2674983f429d11eb8ae1525400094d64',
    scene:'',

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
    [mutationtypes.TOKEN_MUTATION](state, value) {
      state.token = value;
    },
    [mutationtypes.SENCE_MUTATION](state, value) {
      state.scene = value;
    },

  },
  getters: {},
});

export default store;
