<template>
  <article class="p-4 border shadow">
    <popup-container
      v-if="showUpdatePostForm"
      @closePopup="handleEditPostClose()"
      class="fixed top-0 bottom-0 left-0 right-0 z-10 mx-auto bg-white border shadow lg:top-auto lg:bottom-auto max-w-screen-md lg:absolute"
    >
      <update-post-form
        :post="post"
        @closeUpdatePostForm="handleEditPostClose()"
        v-if="showUpdatePostForm"
        class="flex flex-col mx-4 mt-8 mb-4 lg:m-4 lg:block"
      />
    </popup-container>
    <div class="mb-4 border-b">
      <div class="flex items-baseline justify-between">
        <h5 class="text-xl">{{ post.title }}</h5>
        <span v-if="isEditable"
          ><button @click="handleEditPostClick()" class="text-blue-400">
            edit
          </button></span
        >
      </div>
      <span>
        by
        <router-link
          :to="{ name: 'UserProfile', params: { username: post.username } }"
          class="link"
          >{{ post.username }}</router-link
        >
      </span>
      <p class="text-sm">on {{ dateToString() }}</p>
    </div>
    <div @click="handleContentClick()">
      <span class="whitespace-pre-wrap">{{ displayedContent() }}</span>
    </div>
    <div class="text-lg text-right">
      <button
        @click="handleDownArrowClick()"
        v-if="showDownArrow"
        class="p-2 hover:text-green-400"
      >
        &#x25BC;
      </button>
      <button
        @click="handleUpArrowClick()"
        v-if="showUpArrow"
        class="p-2 hover:text-green-400"
      >
        &#x25B2;
      </button>
    </div>
    <div class="flex justify-between">
      <button @click="handleReplyCountClick()" class="text-blue-400">
        Replies: {{ numReplies }}
      </button>
      <button
        v-if="principal.loggedIn"
        @click="handleReplyClick()"
        class="text-blue-400"
      >
        Reply
      </button>
      <span v-else>Login to reply</span>
    </div>
    <div>
      <div v-if="showReplyForm" class="mt-4">
        <p>Reply:</p>
        <form @submit.prevent="handleReplyFormSubmit()" class="form">
          <div class="form-row">
            <textarea
              v-model="newReply.content"
              spellcheck="true"
              rows="5"
              class="w-full border resize-none"
            />
          </div>
          <div class="text-center">
            <input type="submit" value="Reply" />
          </div>
        </form>
      </div>
    </div>
    <div v-if="showReplies" class="mt-4">
      <div v-if="numReplies > 0">
        <reply
          v-for="reply in replies"
          :key="reply.id"
          :reply="reply"
          class="p-4 border"
        />
      </div>
      <div v-else class="p-4 text-center">
        No one has replied to this post yet.
      </div>
      <button @click="handleHideRepliesClick()" class="mt-4 text-blue-400">
        Hide replies
      </button>
    </div>
  </article>
</template>

<script>
import { mapGetters } from "vuex";
import Reply from "@/components/Reply.vue";
import BlogClient from "@/client";
import PopupContainer from "@/components/PopupContainer.vue";
import UpdatePostForm from "@/components/UpdatePostForm.vue";

export default {
  name: "Post",
  components: {
    Reply,
    PopupContainer,
    UpdatePostForm,
  },
  props: {
    post: {
      type: Object,
      required: true,
    },
    full: {
      type: Boolean,
      required: false,
      default: false,
    },
    truncateAt: {
      type: Number,
      required: false,
      default: 500,
    },
  },
  data: function() {
    return {
      isFull: this.full,
      showDownArrow: false,
      showUpArrow: false,
      showReplies: false,
      showReplyForm: false,
      showUpdatePostForm: false,
      newReply: {
        content: "",
        postId: this.post.id,
      },
    };
  },
  computed: {
    ...mapGetters(["principal"]),
    numReplies: function() {
      if (this.post.replies) {
        return Object.keys(this.post.replies).length;
      } else {
        return 0;
      }
    },
    replies: function() {
      return Object.values(this.post.replies).sort(
        (a, b) => a.created - b.created,
      );
    },
    isEditable: function() {
      return this.principal.username === this.post.username;
    },
  },
  methods: {
    dateToString: function() {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour12: true,
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
      };

      return this.post.created.toLocaleDateString("en-US", options);
    },
    displayedContent: function() {
      if (this.post.content.length < this.truncateAt) {
        return this.post.content;
      } else if (this.isFull) {
        this.showDownArrow = false;
        this.showUpArrow = true;

        return this.post.content;
      } else {
        this.showDownArrow = true;
        this.showUpArrow = false;

        return this.post.content.substring(0, this.truncateAt) + "...";
      }
    },
    handleContentClick: function() {
      this.isFull = !this.isFull;
    },
    handleDownArrowClick: function() {
      this.isFull = true;
    },
    handleUpArrowClick: function() {
      this.isFull = false;
    },
    handleReplyCountClick: function() {
      this.showReplies = !this.showReplies;
    },
    handleHideRepliesClick: function() {
      this.showReplies = false;
    },
    handleReplyClick: function() {
      this.showReplyForm = !this.showReplyForm;
    },
    handleEditPostClick: function() {
      this.showUpdatePostForm = !this.showUpdatePostForm;
    },
    handleEditPostClose: function() {
      this.showUpdatePostForm = false;
    },
    handleReplyFormSubmit: async function() {
      await BlogClient.userApi.addUserReply(
        this.principal.username,
        this.newReply,
      );
      this.showReplyForm = false;

      const payload = { postId: this.post.id };
      this.$store.dispatch("fetchPostReplies", payload);
    },
  },
};
</script>

<style lang="postcss" scoped></style>
