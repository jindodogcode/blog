<template>
  <div @keyup.esc="$emit('closePopup')" :class="{ 'pt-5': showClose }">
    <button
      @click="$emit('closePopup')"
      v-if="showClose"
      class="absolute top-0 right-0 w-6 h-6 mt-1 mr-1 text-sm bg-gray-100 border rounded-full hover:border-red-600 hover:bg-red-200"
    >
      X
    </button>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "PopupContainer",
  props: {
    showClose: {
      type: Boolean,
      default: true,
      required: false,
    },
  },
};
</script>

<style lang="postcss" scoped></style>

