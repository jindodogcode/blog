<template>
  <section>
    <form @submit.prevent="handleSubmit()" class="post-form form ">
      <div class="form-row post-form-row">
        <label>Title:</label>
        <input type="text" v-model="newPost.title" />
      </div>
      <div class="form-row">
        <textarea
          v-model="newPost.content"
          spellcheck="true"
          rows="10"
          class="w-full border resize-none"
        />
      </div>
      <input type="submit" value="Post" />
    </form>
  </section>
</template>

<script>
import { mapGetters } from "vuex";
import BlogClient from "@/client";

export default {
  name: "NewPost",
  data: function() {
    return {
      newPost: {
        title: "",
        content: "",
      },
    };
  },
  computed: {
    ...mapGetters(["principal"]),
  },
  methods: {
    handleSubmit: async function() {
      await BlogClient.userApi.addUserPost(
        this.principal.username,
        this.newPost,
      );
      this.$router.push(`/${this.principal.username}`);
    },
  },
};
</script>

<style lang="postcss" scoped>
.post-form {
  @apply flex flex-col;
}

.post-form-row {
  @apply w-full flex flex-col;
}
</style>
