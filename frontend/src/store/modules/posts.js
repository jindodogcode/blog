import BlogClient from "@/client";

const state = {
  posts: [],
};

const mutations = {
  ADD_POST(state, payload) {
    state.posts.push(payload);
  },
  ADD_POSTS(state, payload) {
    state.posts = state.posts.concat(payload);
  },
  CLEAR_POSTS(state) {
    state.posts = [];
  },
};

const actions = {
  /**
   * Fetch posts
   *
   * @param {Function} commit
   * @param {Object} pageParams object containing paging info
   * @param {Number} pageParams.page page number
   * @param {Number} pageParams.pagesize number of items per page
   */
  async fetchPosts({ commit }, pageParams) {
    try {
      const pageablePosts = await BlogClient.postApi.getPosts(pageParams);
      commit("ADD_POSTS", pageablePosts.content);
    } catch (err) {
      console.log(err);
    }
  },
  clearPosts({ commit }) {
    commit("CLEAR_POSTS");
  },
};

const getters = {
  posts(state) {
    return state.posts;
  },
};

const posts = {
  state,
  mutations,
  actions,
  getters,
};

export default posts;
