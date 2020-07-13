<template>
  <nav class="navbar">
    <router-link to="/">Home</router-link>
    <router-link to="/about">About</router-link>
    <router-link v-if="!principal.loggedIn" to="/register"
      >Register</router-link
    >
    <button v-if="!principal.loggedIn" @click="handleLoginClick()">
      Login
    </button>
    <router-link
      v-if="principal.loggedIn"
      :to="{
        name: 'NewPost',
        params: { username: principal.username },
      }"
      >Post</router-link
    >
    <router-link
      v-if="principal.loggedIn"
      :to="{ name: 'UserProfile', params: { username: principal.username } }"
      >Account</router-link
    >
    <button v-if="principal.loggedIn" @click="handleLogoutClick">Logout</button>
  </nav>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "NavBar",
  computed: {
    ...mapGetters(["principal"]),
  },
  methods: {
    handleLoginClick: function() {
      this.$store.dispatch("toggleLoginFormOpen");
    },
    handleLogoutClick: async function() {
      await this.$store.dispatch("logout");
      this.$router.push("/");
    },
  },
};
</script>

<style lang="postcss" scoped></style>
