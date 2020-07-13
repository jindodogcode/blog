import Vue from "vue";
import BlogClient from "@/client";

const state = {
  posts: {},
  lastFetch: new Date(Date.UTC(0)),
  empty: false,
  pageNumber: 0,
  last: false,
};

const mutations = {
  /**
   * Add or update post in state
   *
   * @param {Object} state
   * @param {Object} state.posts
   * @param {Object} payload a post
   * @param {Number} payload.id
   */
  ADD_POST(state, payload) {
    if (state.posts[payload.id]) {
      payload.replies = state.posts[payload.id].replies;
      payload.lastRepliesFetch = state.posts[payload.id].lastRepliesFetch;
      Vue.set(state.posts, payload.id, payload);
    } else {
      Vue.set(state.posts, payload.id, payload);
      const post = state.posts[payload.id];
      Vue.set(post, "replies", {});
      Vue.set(post, "lastRepliesFetch", new Date(Date.UTC(0)));
    }
  },

  /**
   * Add or update a single reply to a post
   *
   * @param {Object} state
   * @param {Object} state.posts
   * @param {Object} payload
   * @param {Number} payload.postId
   * @param {Object} payload.reply
   */
  ADD_POST_REPLY(state, payload) {
    const post = state.posts[payload.postId];
    Vue.set(post.replies, payload.reply.id, payload.reply);
  },

  /**
   * Add replies to a post
   *
   * @param {Object} state
   * @param {Object} state.posts
   * @param {Array} payload array of replies
   */
  ADD_POST_REPLIES(state, payload) {
    const post = state.posts[payload.postId];
    // post.replies = post.replies.concat(payload.replies);
    for (const reply of payload.replies) {
      Vue.set(post.replies, reply.id, reply);
    }
  },

  /**
   * Add or update multiple posts in state
   *
   * @param {Object} state
   * @param {Object} state.posts
   * @param {Array} payload  array of posts
   */
  ADD_POSTS(state, payload) {
    for (const post of payload) {
      if (state.posts[post.id]) {
        post.replies = state.posts[post.id].replies;
        post.lastRepliesFetch = state.posts[post.id].lastRepliesFetch;
        Vue.set(state.posts, post.id, post);
      } else {
        post.replies = {};
        post.lastRepliesFetch = new Date(Date.UTC(0));
        Vue.set(state.posts, post.id, post);
      }
    }
  },

  /**
   * Update time last fetch occured
   *
   * @param {Object} state
   * @param {Date} state.lastFetch
   */
  SET_LAST_FETCH(state) {
    state.lastFetch = new Date();
  },

  /**
   * Update time fetch for replies occured for a given post
   *
   * @param {Object} state
   * @param {Object} state.posts
   * @param {Number} payload post id
   */
  SET_POST_LAST_REPLIES_FETCH(state, payload) {
    state.posts[payload].lastRepliesFetch = new Date();
  },

  /**
   * Set the last state
   *
   * @param {Object} state
   * @param {Boolean} state.last
   * @param {Boolean} payload laststate
   */
  SET_LAST(state, payload) {
    state.last = payload;
  },

  /**
   * Set the empty state
   *
   * @param {Object} state
   * @param {Boolean} state.empty
   * @param {Boolean} payload
   */
  SET_EMPTY(state, payload) {
    state.empty = payload;
  },

  /**
   * Set page number in state
   *
   * @param {Object} state
   * @param {Number} state.pageNumber
   * @param {Number} payload page number
   */
  SET_PAGE_NUMBER(state, payload) {
    state.pageNumber = payload;
  },

  CLEAR_POSTS(state) {
    state.posts = [];
    state.lastFetch = new Date(Date.UTC(0));
    state.last = false;
    state.empty = false;
    state.pageNumber = 0;
  },
};

const actions = {
  /**
   * Fetch post
   *
   * @param {Object} context
   * @param {Function} context.commit
   * @param {Number} postId
   */
  async fetchPost({ commit }, postId) {
    try {
      const post = await BlogClient.postApi.getPost(postId);
      commit("ADD_POST", post);
    } catch (err) {
      console.log(err);
    }
  },

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
      commit("SET_LAST", pageablePosts.last);
      commit("SET_EMPTY", pageablePosts.empty);
      commit("SET_PAGE_NUMBER", pageablePosts.number);

      if (!options.page) commit("SET_LAST_FETCH");

      return pageablePosts.content;
    } catch (err) {
      console.log(err);
    }
  },

  /**
   * Fetch a single reply to a post
   *
   * @param {Object} context
   * @param {Function} commit
   * @param {Object} payload
   * @param {Number} payload.postId
   * @param {Number} payload.replyId
   */
  async fetchPostReply({ commit }, payload) {
    try {
      const reply = await BlogClient.replyApi.getReply(payload.replyId);
      const commitPayload = {
        postId: payload.postId,
        reply,
      };
      commit("ADD_POST_REPLY", commitPayload);
    } catch (err) {
      console.log(err);
    }
  },

  /**
   * Fetch post replies
   *
   * @param {Object} context
   * @param {Object} context.getters
   * @param {Function} context.getters.postById
   * @param {Function} context.commit
   * @param {Object} payload
   * @param {Number} payload.postId
   * @param {Object} payload.options
   * @param {Number} payload.options.page
   * @param {Number} payload.options.pagesize
   * @param {Date} payload.options.after
   */
  async fetchPostReplies(context, payload) {
    try {
      const lastRepliesFetch = context.getters.postById(payload.postId)
        .lastRepliesFetch;
      let options = payload.options;
      if (!options) options = {};
      if (!options.after) options.after = lastRepliesFetch;

      const replies = await BlogClient.postApi.getPostReplies(
        payload.postId,
        options,
      );
      context.commit("ADD_POST_REPLIES", {
        postId: payload.postId,
        replies: replies.content,
      });
      context.commit("SET_POST_LAST_REPLIES_FETCH", payload.postId);
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
  postById(state) {
    return function(postId) {
      return state.posts[postId];
    };
  },
  postsByIds(state) {
    return function(postIds) {
      return postIds.map(id => state.posts[id]);
    };
  },
  lastFetch(state) {
    return state.lastFetch;
  },
  postsPageLast(state) {
    return state.last;
  },
  postsPageEmpty(state) {
    return state.empty;
  },
  postsPageNumber(state) {
    return state.pageNumber;
  },
};

const posts = {
  state,
  mutations,
  actions,
  getters,
};

export default posts;
