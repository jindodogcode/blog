<template>
  <div :class="{ 'menu-open': ui.mobile.burgerOpen }" class="bg-white">
    <header class="header">
      <div>
        <router-link to="/">
          <img src="@/assets/images/b-logo-128.png" alt="Blog Logo" />
        </router-link>
      </div>
      <burger @click="handleBurgerClick()" />
    </header>
    <component
      v-if="ui.mobile.burgerOpen"
      :class="currentStyle"
      :is="currentComponent"
    ></component>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Burger from "@/components/Burger.vue";
import NavBar from "@/components/NavBar.vue";
import LoginForm from "@/components/LoginForm.vue";

export default {
  name: "MobileHeaderBar",
  components: {
    Burger,
    NavBar,
    LoginForm,
  },
  data: function() {
    return {
      history: [NavBar],
    };
  },
  computed: {
    ...mapGetters(["ui"]),
    currentComponent: function() {
      return this.ui.mobile.menuHistory.slice(-1)[0];
    },
    currentStyle: function() {
      if (this.currentComponent === NavBar) {
        return "nav-bar";
      } else {
        return "";
      }
    },
  },
  methods: {
    handleBurgerClick: function() {
      if (this.ui.mobile.menuHistory.length > 1) {
        if (this.ui.mobile.menuHistory.slice(-1)[0] === LoginForm) {
          this.$store.dispatch("setLoginFormOpen", false);
        }
        this.$store.dispatch("popMenuHistory");
      } else if (this.ui.mobile.menuHistory.length === 1) {
        this.$store.dispatch("toggleBurgerOpen");
      }
    },
  },
  watch: {
    "ui.loginFormOpen": function(isOpen) {
      if (isOpen === true) {
        this.$store.dispatch("pushMenuHistory", LoginForm);
      }
    },
  },
};
</script>

<style lang="postcss" scoped>
.menu-open {
  @apply absolute z-40 top-0 right-0 left-0 p-4 h-screen;
}

.nav-bar {
  @apply flex flex-col items-center justify-evenly;
  height: 80%;
}
</style>
