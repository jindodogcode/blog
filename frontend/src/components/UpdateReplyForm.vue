<template>
  <form @submit.prevent="handleUpdateReplyFormSubmit()" class="form">
    <h5 class="text-lg font-bold">Edit</h5>
    <div class="form-row">
      <textarea
        v-model="replyForm.content"
        spellcheck="true"
        rows="6"
        class="w-full resize-none"
      />
    </div>
    <div class="text-center">
      <input type="submit" value="Update" />
    </div>
  </form>
</template>

<script>
import { mapGetters } from "vuex";
import BlogClient from "@/client";

export default {
  name: "UpdateReplyForm",
  props: {
    reply: {
      type: Object,
      required: true,
    },
  },
  data: function() {
    return {
      replyForm: {
        content: "",
      },
    };
  },
  computed: {
    ...mapGetters(["principal"]),
  },
  methods: {
    handleUpdateReplyFormSubmit: async function() {
      try {
        await BlogClient.userApi.updateUserReply(
          this.principal.username,
          this.reply.id,
          this.replyForm,
        );
        const payload = {
          postId: this.reply.postId,
          replyId: this.reply.id,
        };
        await this.$store.dispatch("fetchPostReply", payload);
        this.$emit("closeUpdateReplyForm");
      } catch (err) {
        console.log(err);
      }
    },
  },
  mounted: function() {
    this.replyForm.content = this.reply.content;

    document.querySelector(".form > .form-row:first-of-type textarea").focus();
  },
};
</script>

<style lang="postcss" scoped></style>
