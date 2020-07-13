import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";
import Home from "@/views/Home.vue";
import Error from "@/views/Error.vue";
import Registration from "@/views/Registration.vue";
import UserProfile from "@/views/UserProfile.vue";
import NewPost from "@/views/NewPost.vue";
import { UserNotFoundError } from "@/errors";
import { postsDefaults, userPostsDefaults } from "@/defaults";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    beforeEnter: async function(_to, _from, next) {
      try {
        const lastFetch = store.getters.lastFetch;
        await store.dispatch("fetchPosts", {
          after: lastFetch,
          pagesize: postsDefaults.pagesize,
        });
        Object.values(store.getters.posts).forEach(post =>
          store.dispatch("fetchPostReplies", { postId: post.id }),
        );
      } catch (err) {
        console.log(err);
      }
      next();
    },
  },
  {
    path: "/error",
    name: "Error",
    component: Error,
    props: true,
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
      try {
        await store.dispatch("fetchUser", username);
        await store.dispatch("fetchUserPosts", {
          username,
          options: { pagesize: userPostsDefaults.pagesize },
        });
        const user = store.getters.userByUsername(username);
        Object.keys(user.posts).forEach(post =>
          store.dispatch("fetchPostReplies", { postId: post }),
        );
        next();
      } catch (err) {
        console.log(err);
        if (err instanceof UserNotFoundError) {
          store.dispatch("addPageError", err);
        }
        next();
      }
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
  store.dispatch("clearErrors");
  next();
});

export default router;
