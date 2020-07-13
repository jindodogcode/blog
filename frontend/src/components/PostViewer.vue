<template>
  <section>
    <h3 class="text-2xl text-center">Latest Posts</h3>
    <post
      v-for="(post, id) in posts"
      :key="id"
      :post="post"
      class="mx-auto mb-4 max-w-screen-md"
    />
  </section>
</template>

<script>
import Post from "@/components/Post.vue";

export default {
  name: "PostViewer",
  components: {
    Post,
  },
  props: {
    posts: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style lang="postcss" scoped></style>
