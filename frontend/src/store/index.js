import Vue from "vue";
import Vuex from "vuex";
import appState from "./modules/appState";
import principal from "./modules/principal";
import posts from "./modules/posts";
import replies from "./modules/replies";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    appState,
    principal,
    posts,
    replies,
  },
});
