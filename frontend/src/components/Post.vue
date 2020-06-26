<template>
  <article class="p-4 border border-black shadow">
    <div class="mb-4 border-b">
      <h5 class="text-xl">{{ post.title }}</h5>
      <span>
        by
        <router-link
          :to="{ name: 'UserProfile', params: { username: post.username } }"
          class="link"
          >{{ post.username }}</router-link
        >
      </span>
      <p class="text-sm">on {{ post.created.toUTCString() }}</p>
    </div>
    <div>
      <span v-html="post.content"></span>
    </div>
  </article>
</template>

<script>
export default {
  name: "Post",
  props: {
    post: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style lang="postcss" scoped></style>
