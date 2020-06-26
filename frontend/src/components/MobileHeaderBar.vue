<template>
  <div :class="{ 'menu-open': appState.mobile.burgerOpen }" class="bg-white">
    <header class="header">
      <burger @click="handleBurgerClick()" />
    </header>
    <component
      v-if="appState.mobile.burgerOpen"
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
    ...mapGetters(["appState"]),
    currentComponent: function() {
      return this.appState.mobile.menuHistory.slice(-1)[0];
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
      if (this.appState.mobile.menuHistory.length > 1) {
        if (this.appState.mobile.menuHistory.slice(-1)[0] === LoginForm) {
          this.$store.dispatch("setLoginFormOpen", false);
        }
        this.$store.dispatch("popMenuHistory");
      } else if (this.appState.mobile.menuHistory.length === 1) {
        this.$store.dispatch("toggleBurgerOpen");
      }
    },
  },
  watch: {
    "appState.loginFormOpen": function(isOpen) {
      if (isOpen === true) {
        this.$store.dispatch("pushMenuHistory", LoginForm);
      }
    },
  },
};
</script>

<style lang="postcss" scoped>
.menu-open {
  @apply absolute z-10 top-0 right-0 left-0 p-4 h-screen;
}

.nav-bar {
  @apply flex flex-col items-center justify-evenly;
  height: 80%;
}
</style>
