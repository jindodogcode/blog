<template>
  <div
    @click="$emit('click')"
    :class="{
      'burger-close': !appState.mobile.burgerOpen,
      'burger-open': appState.mobile.burgerOpen && !largeHistory(),
      'burger-arrow': appState.mobile.burgerOpen && largeHistory(),
    }"
    class="burger space-y-1"
  >
    <div></div>
    <div></div>
    <div></div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Burger",
  computed: {
    ...mapGetters(["appState"]),
  },
  methods: {
    largeHistory: function() {
      return this.appState.mobile.menuHistory.length > 1;
    },
  },
};
</script>

<style lang="postcss" scoped>
.burger {
  @apply w-4 h-4 cursor-pointer;
}

.burger:hover div {
  @apply border-blue-400;
}

.burger div {
  @apply border-b-2 border-black transition-opacity duration-700;
}

.burger-open div {
  @apply duration-500;
}

.burger-close div:nth-child(1) {
  @apply transition-transform transform rotate-0 translate-y-0;
}

.burger-close div:nth-child(2) {
  @apply transition-opacity opacity-100;
}

.burger-close div:nth-child(3) {
  @apply transition-transform transform rotate-0 translate-y-0;
}

.burger-open div:nth-child(1) {
  @apply transition-transform transform rotate-45 translate-y-1;
}

.burger-open div:nth-child(2) {
  @apply transition-opacity opacity-0;
}

.burger-open div:nth-child(3) {
  @apply transition-transform transform -rotate-45 -translate-y-2;
}

.burger-arrow div:nth-child(1) {
  @apply transition-transform transform -rotate-45 translate-y-0;
}

.burger-arrow div:nth-child(2) {
  @apply transition-opacity opacity-0;
}

.burger-arrow div:nth-child(3) {
  @apply transition-transform transform rotate-45 translate-y-0;
}
</style>
