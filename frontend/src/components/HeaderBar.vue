<template>
  <div>
    <header class="header">
      <div>
        <router-link to="/"
          ><img src="@/assets/images/b-logo-128.png" alt="Blog Logo"
        /></router-link>
      </div>
      <nav-bar />
    </header>
    <div class="relative">
      <popup-container
        v-if="ui.loginFormOpen"
        @closePopup="handleLoginClosePopup()"
        class="absolute top-0 right-0 z-50 bg-white border shadow"
      >
        <login-form @closePopup="handleLoginClosePopup()" />
      </popup-container>
      <popup-container
        v-if="errors.popupErrors.length > 0"
        @closePopup="handleErrorsClosePopup()"
        class="absolute top-0 left-0 z-50 w-3/12 bg-red-100 border border-red-600"
      >
        <errors
          :errors="errors.popupErrors"
          class="max-w-xs m-4 text-red-600 bg-red-100"
        />
      </popup-container>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import NavBar from "@/components/NavBar.vue";
import PopupContainer from "@/components/PopupContainer.vue";
import LoginForm from "@/components/LoginForm.vue";
import Errors from "@/components/Errors.vue";

export default {
  name: "HeaderBar",
  components: {
    NavBar,
    PopupContainer,
    LoginForm,
    Errors,
  },
  computed: {
    ...mapGetters(["ui", "errors"]),
  },
  methods: {
    handleLoginClosePopup: function() {
      this.$store.dispatch("setLoginFormOpen", false);
    },
    handleErrorsClosePopup: function() {
      this.$store.dispatch("clearErrors");
    },
  },
};
</script>

<style lang="postcss" scoped></style>
