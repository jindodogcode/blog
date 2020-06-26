import Vue from "vue";
import Vuex from "vuex";
import ui from "./modules/ui";
import principal from "./modules/principal";
import users from "./modules/users";
import posts from "./modules/posts";
import replies from "./modules/replies";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  mutations: {},
  actions: {},
  modules: {
    ui,
    principal,
    users,
    posts,
    replies,
  },
});
