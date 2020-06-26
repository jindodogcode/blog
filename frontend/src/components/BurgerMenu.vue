<template>
  <div class=""></div>
</template>

<script>
import Vue from "vue";

export default {
  name: "HistoryPopup",
  props: {
    components: {
      type: Array,
      required: true,
      validator: function(components) {
        return components.every(component => component instanceof Object);
      },
    },
  },
  data: function() {
    return {
      history: [],
    };
  },
};
</script>

<style lang="postcss" scoped></style>
