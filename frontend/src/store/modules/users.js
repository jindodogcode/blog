import Vue from "vue";
import BlogClient from "@/client";
import { UserNotFoundError } from "@/errors";

const state = {
  users: {},
};

const mutations = {
  /**
   * Add user to state
   *
   * @param {Object} state
   * @param {Object} state.users
   * @param {Object} payload user object
   * @param {String} payload.username
   */
  ADD_USER(state, payload) {
    let user = state.users[`${payload.username}`];
    if (user) {
      payload.posts = user.posts;
      payload.totalPosts = user.totalPosts;
      payload.pageNumber = user.pageNumber;
      payload.lastPage = user.lastPage;
      payload.lastFetch = user.lastFetch;
      Vue.set(state.users, payload.username, payload);
    } else {
      payload.posts = {};
      payload.totalPosts = 0;
      payload.pageNumber = 0;
      payload.lastPage = true;
      payload.lastFetch = new Date(Date.UTC(0));
      Vue.set(state.users, payload.username, payload);
    }
  },

  /**
   * Add id reference of user's posts to user state
   *
   * @param {Object} state
   * @param {Object} state.users
   * @param {Object} payload
   * @param {String} payload.username
   * @param {Array} payload.posts
   * @param {Number} payload.totalPosts
   * @param {Number} payload.pageNumber
   * @param {Boolean} payload.lastPage
   */
  ADD_USER_POSTS(state, payload) {
    let user = state.users[`${payload.username}`];
    if (!user) {
      Vue.set(state.users, payload.username, {});
      user = state.users[`${payload.username}`];
    }
    payload.posts.forEach(post => Vue.set(user.posts, post.id, null));
    user.totalPosts = payload.totalPosts;
    user.pageNumber = payload.pageNumber;
    user.lastPage = payload.lastPage;
  },

  /**
   * Update timestamp user's posts were last fetched
   *
   * @param {Object} state
   * @param {String} payload username
   */
  UPDATE_USER_LAST_FETCH(state, payload) {
    const user = state.users[`${payload}`];
    if (user) {
      user.lastFetch = new Date();
    }
  },

  /**
   * Clears all user data
   *
   * @param {Object} state
   * @param {Object} users
   */
  RESET_USERS(state) {
    state.users = {};
  },
};

const actions = {
  /**
   * fetch user
   *
   * @param {Object} context
   * @param {Function} context.commit
   * @param {String} username
   */
  async fetchUser({ commit }, username) {
    try {
      const user = await BlogClient.userApi.getUser(username);
      commit("ADD_USER", user);
    } catch (err) {
      if (err.response.notFound) {
        throw new UserNotFoundError();
      }
      console.log(err);
    }
  },

  /**
   * fetch user posts
   *
   * @param {Object} context
   * @param {Function} context.commit
   * @param {Object} request
   * @param {String} request.username
   * @param {Date} request.options.after
   * @param {Number} request.options.page
   * @param {Number} request.options.pagesize
   */
  async fetchUserPosts(context, request) {
    try {
      let options = request.options;
      if (!options) options = {};

      const posts = await BlogClient.userApi.getUserPosts(
        request.username,
        options,
      );
      const payload = {
        username: request.username,
        posts: posts.content,
        totalPosts: posts.totalElements,
        pageNumber: posts.number,
        lastPage: posts.last,
      };
      context.commit("ADD_USER_POSTS", payload);
      context.commit("ADD_POSTS", posts.content);

      context.commit("UPDATE_USER_LAST_FETCH", request.username);

      return posts.content;
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
