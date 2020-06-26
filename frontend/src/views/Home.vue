<template>
  <div class="home">
    <post-viewer :posts="sortedPosts" />
  </div>
</template>

<script>
import PostViewer from "@/components/PostViewer.vue";

export default {
  name: "Home",
  components: {
    PostViewer,
  },
  computed: {
    sortedPosts: function() {
      const posts = this.$store.getters.posts;
      return Object.values(posts).sort((a, b) => b.created - a.created);
    },
  },
};
</script>
