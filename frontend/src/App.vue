<template>
  <div id="app" class="relative z-0 p-4 mx-auto max-w-screen-md">
    <header-bar v-if="ui.largeScreen" />
    <mobile-header-bar v-else />
    <main class="relative px-4 mx-auto mt-8">
      <errors
        v-if="errors.pageErrors.length > 0"
        :errors="errors.pageErrors"
        class="text-center"
      />
      <router-view v-else />
    </main>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import HeaderBar from "@/components/HeaderBar.vue";
import MobileHeaderBar from "@/components/MobileHeaderBar.vue";
import Errors from "@/components/Errors.vue";

export default {
  name: "App",
  components: {
    HeaderBar,
    MobileHeaderBar,
    Errors,
  },
  computed: {
    ...mapGetters(["ui", "errors"]),
  },
  methods: {
    handleResize: function() {
      if (window.innerWidth >= 1024) {
        this.$store.dispatch("setLargeScreen", true);
      } else {
        this.$store.dispatch("setLargeScreen", false);
      }
    },
  },
  mounted: async function() {
    this.$store.dispatch("me");
    this.handleResize();
    window.addEventListener("resize", this.handleResize);
  },
  beforeDestroy: function() {
    window.removeEventListener("resize", this.handleResize);
  },
};
</script>

<style lang="postcss">
#app {
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

.link {
  @apply text-blue-400;
}
</style>

<style lang="postcss" scoped>
.login-form {
  @apply absolute bg-white w-full top-0 right-0 h-full w-full;
}

@media (min-width: 1024px) {
  .login-form {
    @apply w-auto h-auto border;
  }
}
</style>
