import Vue from "vue";
import Vuex from "vuex";
import * as mutationtypes from './mutation-types'

Vue.use(Vuex);
const store = new Vuex.Store({
  state: {
    userInfo:{},
    token:'',
    openId: '',
    token:'',
    sessionId_3rd: '',
    scene: '',
    gData:''
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
    [mutationtypes.GDATA_MUTATION](state, value) {
      state.gData = value;
    }

  },
  getters: {},
});

export default store;
