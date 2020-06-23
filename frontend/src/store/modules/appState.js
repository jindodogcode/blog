const state = {
  largeScreen: false,
  burgerOpen: false,
  loginFormOpen: false,
};

const mutations = {
  UPDATE_LARGE_SCREEN(state, payload) {
    state.largeScreen = payload;
  },
  UPDATE_BURGER_OPEN(state, payload) {
    state.burgerOpen = payload;
  },
  UPDATE_LOGIN_FORM_OPEN(state, payload) {
    state.loginFormOpen = payload;
  },
  RESET_APP_STATE(state) {
    state.largeScreen = false;
    state.burgerOpen = false;
    state.loginFormOpen = false;
  },
};

const actions = {
  /**
   * Toggle vale of largeScreen
   *
   * @param {Object} context
   */
  toggleLargeScreen(context) {
    context.commit("UPDATE_LARGE_SCREEN", !context.state.largeScreen);
  },

  /**
   * Sets value of largeScreen
   *
   * @param {Function} commit
   * @param {Boolean} isLargeScreen
   */
  setLargeScreen({ commit }, isLargeScreen) {
    commit("UPDATE_LARGE_SCREEN", isLargeScreen);
  },

  /**
   * Toggle vale of burgerOpen
   *
   * @param {Object} context
   */
  toggleBurgerOpen(context) {
    context.commit("UPDATE_BURGER_OPEN", !context.state.burgerOpen);
  },

  /**
   * Sets value of burgerOpen
   *
   * @param {Function} commit
   * @param {Boolean} isBurgerOpen
   */
  setBurgerOpen({ commit }, isBurgerOpen) {
    commit("UPDATE_BURGER_OPEN", isBurgerOpen);
  },

  /**
   * Toggle vale of loginFormOpen
   *
   * @param {Object} context
   */
  toggleLoginFormOpen(context) {
    context.commit("UPDATE_LOGIN_FORM_OPEN", !context.state.loginFormOpen);
  },

  /**
   * Sets value of loginFormOpen
   *
   * @param {Function} commit
   * @param {Boolean} isLoginFormOpen
   */
  setLoginFormOpen({ commit }, isLoginFormOpen) {
    commit("UPDATE_LOGIN_FORM_OPEN", isLoginFormOpen);
  },

  /**
   * Reset all values
   *
   * @param {Function} commit
   */
  resetAppState({ commit }) {
    commit("RESET_APP_STATE");
  },
};

const getters = {
  appState(state) {
    return state;
  },
};

const appState = {
  state,
  mutations,
  actions,
  getters,
};

export default appState;
