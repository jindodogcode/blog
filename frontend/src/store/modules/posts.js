import Vue from "vue";
import BlogClient from "@/client";

const state = {
  posts: {},
  lastFetch: new Date(Date.UTC(0)),
};

const mutations = {
  ADD_POST(state, payload) {
    Vue.set(state.posts, payload.id, payload);
  },
  ADD_POSTS(state, payload) {
    for (const post of payload) {
      Vue.set(state.posts, post.id, post);
    }
  },
  SET_LAST_FETCH(state) {
    state.lastFetch = new Date();
  },
  CLEAR_POSTS(state) {
    state.posts = [];
    state.lastFetch = new Date(Date.UTC(0));
  },
};

const actions = {
  /**
   * Fetch posts
   *
   * @param {Object} context
   * @param {Function} context.commit
   * @param {Object} options object containing paging info
   * @param {Number} options.page page number
   * @param {Number} options.pagesize number of items per page
   * @param {Date} options.after only posts created after this date time
   */
  async fetchPosts({ commit }, options) {
    try {
      const pageablePosts = await BlogClient.postApi.getPosts(options);
      commit("ADD_POSTS", pageablePosts.content);
      commit("SET_LAST_FETCH");
    } catch (err) {
      console.log(err);
    }
  },

  /**
   * Clear all posts and last fetch time
   *
   * @param {Object} context
   * @param {Function} context.commit
   */
  clearPosts({ commit }) {
    commit("CLEAR_POSTS");
  },
};

const getters = {
  posts(state) {
    return state.posts;
  },
  lastFetch(state) {
    return state.lastFetch;
  },
};

const posts = {
  state,
  mutations,
  actions,
  getters,
};

export default posts;
