import BlogClient from "@/client";
import { UnauthorizedError } from "@/errors.js";

const state = {
  username: "",
  email: "",
  firstName: "",
  lastName: "",
  about: "",
  loggedIn: false,
};

const mutations = {
  /**
   * Set principal state
   *
   * @param {Object} state
   * @param {Object} payload
   * @param {String} payload.username
   * @param {String} payload.email
   * @param {String} payload.firstName
   * @param {String} payload.lastName
   * @param {String} payload.about
   */
  SET_PRINCIPAL(state, payload) {
    state.username = payload.username;
    state.email = payload.email;
    state.firstName = payload.firstName;
    state.lastName = payload.lastName;
    state.about = payload.about;
  },

  /**
   * Set principal logged in status
   *
   * @param {Object} state
   * @param {Boolean} payload
   */
  SET_LOGGED_IN(state, payload) {
    state.loggedIn = payload;
  },

  /**
   * Reset principal state
   *
   * @param {Object} state
   */
  CLEAR_PRINCIPAL(state) {
    state.username = "";
    state.email = "";
    state.firstName = "";
    state.lastName = "";
    state.about = "";
    state.loggedIn = false;
  },
};

const actions = {
  /**
   * login
   *
   * @param {Object} context
   * @param {Function} context.commit
   * @param {Object} userCredentials
   * @param {String} userCredentials.username
   * @param {String} userCredentials.password
   */
  async login({ commit }, userCredentials) {
    const loginStr = `Basic ${btoa(
      `${userCredentials.username}:${userCredentials.password}`,
    )}`;
    try {
      const user = await BlogClient.defaultApi.login(loginStr);
      commit("SET_PRINCIPAL", user);
      commit("SET_LOGGED_IN", true);
    } catch (err) {
      if (err.response.unauthorized) {
        throw new UnauthorizedError();
      } else {
        console.log(err);
      }
    }
  },

  /**
   * me
   *
   * @param {Object} context
   * @param {Function} context.commit
   */
  async me({ commit }) {
    try {
      const user = await BlogClient.defaultApi.me();
      commit("SET_PRINCIPAL", user);
      commit("SET_LOGGED_IN", true);
    } catch (err) {
      if (!err.response) {
        console.log(err);
      }
      if (!err.response.unauthorized) {
        console.log(err);
      }
    }
  },

  /**
   * logout
   *
   * @param {Object} context
   * @param {Function} context.commit
   */
  async logout({ commit }) {
    try {
      await BlogClient.defaultApi.logout();
      commit("CLEAR_PRINCIPAL");
    } catch (err) {
      console.log(err);
    }
  },

  /**
   * update principal information
   *
   * @param {Object} context
   * @param {Function} context.commit
   * @param {Object} payload
   * @param {String} payload.username
   * @param {Object} payload.updateForm
   * @param {String} payload.updateForm.email
   * @param {String} payload.updateForm.firstName
   * @param {String} payload.updateForm.lastName
   * @param {String} payload.updateForm.about
   */
  async updatePrincipal(context, payload) {
    try {
      await BlogClient.userApi.updateUser(payload.username, payload.updateForm);
      await Promise.all([
        context.dispatch("fetchUser", payload.username),
        context.dispatch("me"),
      ]);
    } catch (err) {
      console.log(err);
    }
  },
};

const getters = {
  principal(state) {
    return state;
  },
};

const principal = {
  state,
  mutations,
  actions,
  getters,
};

export default principal;
