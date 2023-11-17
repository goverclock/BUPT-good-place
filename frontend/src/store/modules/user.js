// import { userInfo } from '@/apis/user';
export default {
  namespaced: true,
  state: {
    token: '',
    userInfo: {}
  },
  getters: {
    isLogin(state) {
      return !!state.token || !!localStorage.getItem('pm_token');
    },
    userInfo(state) {
      return JSON.parse(localStorage.getItem('user_info'));
    }
  },
  mutations: {
    setToken(state, token) {
      localStorage.setItem('pm_token', token);
      state.token = token;
    },
    clearToken(state) {
      state.token = ''
      localStorage.removeItem('pm_token');
    },
    setUserInfo(state, info) {
      localStorage.setItem('user_info', JSON.stringify(info))
      state.userInfo = info;
    },
    clearUserInfo(state) {
      state.userInfo = {};
      localStorage.removeItem('user_info')
    }
  },
  actions: {
    // refreshInfo({ commit }) {
    //   userInfo().then(res => {
    //     commit('setUserInfo', res.data)
    //   })
    // }
  }
};
