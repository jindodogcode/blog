<template>
  <div>
    <popup-container
      v-if="showUpdateReplyForm"
      @closePopup="handleEditReplyClose()"
      class="fixed top-0 bottom-0 left-0 right-0 bg-white border shadow lg:absolute lg:top-auto lg:bottom-auto lg:mx-auto lg:max-w-screen-md"
    >
      <update-reply-form
        :reply="reply"
        @closeUpdateReplyForm="handleEditReplyClose()"
        class="m-4"
      />
    </popup-container>
    <div class="flex justify-between">
      <router-link
        :to="{ name: 'UserProfile', params: { username: reply.username } }"
        class="link"
        >{{ reply.username }}</router-link
      >
      <span v-if="isEditable">
        <button @click="handleEditReplyClick()" class="text-blue-400">
          edit
        </button>
      </span>
    </div>
    <div>
      <span>{{ reply.content }}</span>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import PopupContainer from "@/components/PopupContainer.vue";
import UpdateReplyForm from "@/components/UpdateReplyForm.vue";

export default {
  name: "Reply",
  components: {
    PopupContainer,
    UpdateReplyForm,
  },
  props: {
    reply: {
      type: Object,
      required: true,
    },
  },
  data: function() {
    return {
      showUpdateReplyForm: false,
    };
  },
  computed: {
    ...mapGetters(["principal"]),
    isEditable: function() {
      return this.reply.username === this.principal.username;
    },
  },
  methods: {
    handleEditReplyClick: function() {
      this.showUpdateReplyForm = !this.showUpdateReplyForm;
    },
    handleEditReplyClose: function() {
      this.showUpdateReplyForm = false;
    },
  },
};
</script>

<style lang="postcss" scoped></style>
