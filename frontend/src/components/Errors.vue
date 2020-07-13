<template>
  <div>
    <div v-for="(error, index) in errors" :key="index">
      {{ error.message }}
    </div>
  </div>
</template>

<script>
export default {
  name: "Errors",
  props: {
    errors: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style lang="postcss" scoped></style>
