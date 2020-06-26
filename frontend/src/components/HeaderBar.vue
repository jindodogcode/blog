<template>
  <div>
    <header class="header">
      <nav-bar />
    </header>
    <div class="relative">
      <popup-container
        v-if="ui.loginFormOpen"
        @closePopup="handleLoginClosePopup()"
        class="absolute top-0 right-0 z-50 border shadow"
      >
        <login-form @closePopup="handleLoginClosePopup()" />
      </popup-container>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import NavBar from "@/components/NavBar.vue";
import PopupContainer from "@/components/PopupContainer.vue";
import LoginForm from "@/components/LoginForm.vue";

export default {
  name: "HeaderBar",
  components: {
    NavBar,
    PopupContainer,
    LoginForm,
  },
  computed: {
    ...mapGetters(["ui"]),
  },
  methods: {
    handleLoginClosePopup: function() {
      this.$store.dispatch("setLoginFormOpen", false);
    },
  },
};
</script>

<style lang="postcss" scoped></style>
