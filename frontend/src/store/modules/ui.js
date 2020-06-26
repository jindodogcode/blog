import NavBar from "@/components/NavBar.vue";

const state = {
  largeScreen: false,
  loginFormOpen: false,
  mobile: {
    burgerOpen: false,
    menuHistory: [NavBar],
  },
};

const mutations = {
  UPDATE_LARGE_SCREEN(state, payload) {
    state.largeScreen = payload;
  },
  UPDATE_LOGIN_FORM_OPEN(state, payload) {
    state.loginFormOpen = payload;
  },
  UPDATE_BURGER_OPEN(state, payload) {
    state.mobile.burgerOpen = payload;
  },
  ADD_MENU_HISTORY(state, payload) {
    state.mobile.menuHistory.push(payload);
  },
  REMOVE_MENU_HISTORY(state) {
    state.mobile.menuHistory.pop();
  },
  RESET_UI(state) {
    state.loginFormOpen = false;
    state.mobile.burgerOpen = false;
    state.mobile.menuHistory = [NavBar];
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
   * Toggle vale of burgerOpen
   *
   * @param {Object} context
   */
  toggleBurgerOpen(context) {
    context.commit("UPDATE_BURGER_OPEN", !context.state.mobile.burgerOpen);
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
   * Add a component to menu history
   *
   * @param {Function} commit
   * @param {Object} component
   */
  pushMenuHistory({ commit }, component) {
    commit("ADD_MENU_HISTORY", component);
  },

  /**
   * Remove a component from menu history
   *
   * @param {Function} commit
   */
  popMenuHistory({ commit }) {
    commit("REMOVE_MENU_HISTORY");
  },

  /**
   * Reset all values
   *
   * @param {Function} commit
   */
  resetUI({ commit }) {
    commit("RESET_UI");
  },
};

const getters = {
  ui(state) {
    return state;
  },
};

const ui = {
  state,
  mutations,
  actions,
  getters,
};

export default ui;
