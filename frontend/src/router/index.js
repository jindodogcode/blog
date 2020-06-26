import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";
import Home from "@/views/Home.vue";
import Registration from "@/views/Registration.vue";
import UserProfile from "@/views/UserProfile.vue";
import NewPost from "@/views/NewPost.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    beforeEnter: async function(_to, _from, next) {
      const lastFetch = store.getters.lastFetch;
      await store.dispatch("fetchPosts", { after: lastFetch });
      next();
    },
  },
  {
    path: "/register",
    name: "Registration",
    component: Registration,
  },
  {
    path: "/user/:username",
    name: "UserProfile",
    component: UserProfile,
    props: true,
    beforeEnter: async function(to, _from, next) {
      const username = to.params.username;
      await Promise.all([
        store.dispatch("fetchUser", username),
        store.dispatch("fetchUserPosts", { username }),
      ]);
      next();
    },
  },
  {
    path: "/user/:username/post",
    name: "NewPost",
    component: NewPost,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(() => {
    store.dispatch("resetUI");
  });
};

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((_to, _from, next) => {
  store.dispatch("resetUI");
  next();
});

export default router;
