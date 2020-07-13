<template>
  <div class="text-lg font-bold text-center">{{ error.message }}</div>
</template>

<script>
import { DisplayError } from "@/errors";

export default {
  name: "Error",
  props: {
    error: {
      type: DisplayError,
    },
  },
};
</script>

<style lang="postcss" scoped></style>
