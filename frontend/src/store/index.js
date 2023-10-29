import { createStore } from 'vuex'

export default createStore({
  state: {
    count: 0,
  },
  getters: {
  },
  mutations: {
    increase(state, count) {
      state.count += count
    }
  },
  actions: {
  },
  modules: {
  }
})
