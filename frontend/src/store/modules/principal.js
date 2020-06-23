import BlogClient from "@/client";

const state = {
  username: "",
  email: "",
  firstName: "",
  lastName: "",
  posts: new BlogClient.pages.PageablePosts(),
  replies: new BlogClient.pages.PageableReplies(),
  loggedIn: false,
};

const mutations = {
  UPDATE_PINCIPAL(state, payload) {
    state.username = payload.username;
    state.email = payload.email;
    state.firstName = payload.firstName;
    state.lastName = payload.lastName;
  },
  UPDATE_POSTS(state, payload) {
    state.posts = payload;
  },
  UPDATE_REPLIES(state, payload) {
    state.replies = payload;
  },
  UPDATE_LOGGED_IN(state, payload) {
    state.loggedIn = payload;
  },
  CLEAR_PRINCIPAL(state) {
    state.username = "";
    state.email = "";
    state.firstName = "";
    state.lastName = "";
    state.posts = new BlogClient.pages.PageablePosts();
    state.replies = new BlogClient.pages.PageableReplies();
    state.loggedIn = false;
  },
};

const actions = {
  /**
   * login
   *
   * @param {Function} commit
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
      commit("UPDATE_PINCIPAL", user);
      commit("UPDATE_LOGGED_IN", true);
    } catch (err) {
      console.log(err);
    }
  },
  /**
   * logout
   *
   * @param {Function} commit
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
   * load principal's posts
   *
   * @param {Object} context
   * @param {Object} pageParams Optional paging parameters
   * @param {Number} pageParams.page page number
   * @param {Number} pageParams.pagesize number of items per page
   */
  async fetchPrincipalPosts(context, pageParams) {
    try {
      const username = context.state.username;
      const pageablePosts = await BlogClient.userApi.getUserPosts(
        username,
        pageParams,
      );
      context.commit("UPDATE_POSTS", pageablePosts);
    } catch (err) {
      console.log(err);
    }
  },
  /**
   * load principal's replies
   *
   * @param {Object} context
   * @param {Object} pageParams Optional paging parameters
   * @param {Number} pageParams.page page number
   * @param {Number} pageParams.pagesize number of items per page
   */
  async fetchPrincipalReplies(context, pageParams) {
    try {
      const username = context.state.username;
      const pageableReplies = await BlogClient.userApi.getUserReplies(
        username,
        pageParams,
      );
      context.commit("UPDATE_REPLIES", pageableReplies);
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
