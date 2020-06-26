import Vue from "vue";
import BlogClient from "@/client";

const state = {
  users: {},
};

const mutations = {
  ADD_USER(state, payload) {
    Vue.set(state.users, payload.username, payload);
  },

  ADD_USER_POSTS(state, payload) {
    const user = state.users[`${payload.username}`];
    if (user && user.posts) {
      user.posts.push(payload.posts);
    } else if (user) {
      Vue.set(user, "posts", payload.posts);
    } else {
      Vue.set(state.users, payload.username, {});
      const user = state.users[`${payload.username}`];
      Vue.set(user, "posts", payload.posts);
    }
  },

  RESET_USERS(state) {
    state.users = {};
  },
};

const actions = {
  /**
   * fetch user
   *
   * @param {Function} commit
   * @param {String} username
   */
  async fetchUser({ commit }, username) {
    try {
      const user = await BlogClient.userApi.getUser(username);
      commit("ADD_USER", user);
    } catch (err) {
      console.log(err);
    }
  },

  /**
   * fetch user posts
   *
   * @param {Function} commit
   * @param {Object} request
   * @param {String} request.username
   * @param {Date} request.options.after
   * @param {Number} request.options.page
   * @param {Number} request.options.pageSize
   */
  async fetchUserPosts({ commit }, request) {
    try {
      const posts = await BlogClient.userApi.getUserPosts(
        request.username,
        request.options,
      );
      const payload = {
        username: request.username,
        posts,
      };
      commit("ADD_USER_POSTS", payload);
    } catch (err) {
      console.log(err);
    }
  },
};

const getters = {
  userByUsername(state) {
    return function(username) {
      return state.users[`${username}`];
    };
  },
};

const users = {
  state,
  mutations,
  actions,
  getters,
};

export default users;
