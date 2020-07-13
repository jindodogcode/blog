<template>
  <form @submit.prevent="handleSubmit()" class="form">
    <div class="form-row">
      <label for="updateTitle">Title</label>
      <input type="text" id="updateTitle" v-model="postForm.title" />
    </div>
    <div class="form-row">
      <textarea
        v-model="postForm.content"
        spellcheck="true"
        rows="10"
        class="w-full resize-none"
      />
    </div>
    <div class="text-center">
      <input type="submit" value="update" />
    </div>
  </form>
</template>

<script>
import { mapGetters } from "vuex";
import BlogClient from "@/client";

export default {
  name: "UpdatePostForm",
  props: {
    post: {
      type: Object,
      required: true,
    },
  },
  data: function() {
    return {
      postForm: {
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
      try {
        await BlogClient.userApi.updateUserPost(
          this.principal.username,
          this.post.id,
          this.postForm,
        );
        await this.$store.dispatch("fetchPost", this.post.id);
        this.$emit("closeUpdatePostForm");
      } catch (err) {
        console.log(err);
      }
    },
  },
  mounted: function() {
    this.postForm.title = this.post.title;
    this.postForm.content = this.post.content;

    document.querySelector(".form > .form-row:first-of-type input").focus();
  },
};
</script>

<style lang="postcss" scoped></style>

