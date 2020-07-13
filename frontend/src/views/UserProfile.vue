<template>
  <section>
    <popup-container
      v-if="isPrincipal && showEditForm"
      @closePopup="handleEditClosePopup()"
      class="absolute inset-x-0 z-10 mx-auto bg-white border shadow max-w-screen-sm"
    >
      <update-user-form
        @closeUpdateUserForm="handleEditClosePopup()"
        class="flex flex-col m-4"
      />
    </popup-container>
    <div class="user-profile">
      <div class="mx-auto max-w-screen-md">
        <div class="flex">
          <h3 class="mr-4 text-2xl font-bold">{{ user.username }}</h3>
          <button
            v-if="isPrincipal"
            @click="handleEditClick()"
            class="p-1 text-blue-400"
          >
            edit
          </button>
        </div>
        <div class="user-info">
          <div class="user-info-row">
            <span>name:</span>
            <span>{{ user.firstName }} {{ user.lastName }}</span>
          </div>
          <div class="user-info-row">
            <span>posts:</span><span>{{ user.totalPosts || 0 }}</span>
          </div>
          <div class="user-info-row">
            <span>about:</span>
            <span>{{ user.about }}</span>
          </div>
        </div>
      </div>
      <post-viewer :posts="userPosts" class="flex-grow" />
    </div>
  </section>
</template>

<script>
import { UserNotFoundError } from "@/errors";
import { amountScrolled } from "@/utils";
import { userPostsDefaults } from "@/defaults";
import PostViewer from "@/components/PostViewer.vue";
import PopupContainer from "@/components/PopupContainer.vue";
import UpdateUserForm from "@/components/UpdateUserForm.vue";

export default {
  name: "UserProfile",
  components: {
    PostViewer,
    PopupContainer,
    UpdateUserForm,
  },
  props: {
    username: {
      type: String,
      required: true,
    },
  },
  data: function() {
    return {
      showEditForm: false,
      scrollTicking: false,
      scrollPos: 0,
      updating: false,
    };
  },
  computed: {
    user: function() {
      return this.$store.getters.userByUsername(this.username);
    },
    userPosts: function() {
      if (!this.user || !this.user.posts) {
        return [];
      } else {
        return this.$store.getters
          .postsByIds(Object.keys(this.user.posts))
          .sort((a, b) => b.created - a.created);
      }
    },
    isPrincipal: function() {
      const principal = this.$store.getters.principal;
      return principal.username === this.username;
    },
  },
  methods: {
    handleEditClick: function() {
      this.showEditForm = true;
    },
    handleEditClosePopup: function() {
      this.showEditForm = false;
    },
    handleScroll: function() {
      window.requestAnimationFrame(() => {
        this.scrollPos = amountScrolled();
        this.scrollTicking = false;
      });
      this.scrollTicking = true;
    },
  },
  watch: {
    scrollPos: async function(val) {
      if (Number.isNaN(val)) return;

      if (val >= 80) {
        if (!this.user.lastPage && this.user.totalPosts && !this.updating) {
          this.updating = true;
          const request = {
            username: this.username,
            options: {
              page: this.user.pageNumber + 1,
              pagesize: userPostsDefaults.pagesize,
            },
          };

          try {
            console.log("in try");
            const posts = await this.$store.dispatch("fetchUserPosts", request);
            const replies = [];
            posts.forEach(post =>
              replies.push(
                this.$store.dispatch("fetchPostReplies", { postId: post.id }),
              ),
            );
            this.updating = false;
          } catch (err) {
            console.log(err);
          }
        }
      }
    },
  },
  created: function() {
    window.addEventListener("scroll", this.handleScroll);
  },
  destroyed: function() {
    window.removeEventListener("scroll", this.handleScroll);
  },
  beforeRouteUpdate: async function(to, _from, next) {
    const username = to.params.username;
    try {
      await this.$store.dispatch("fetchUser", username);
      await this.$store.dispatch("fetchUserPosts", {
        username,
        options: { pagesize: userPostsDefaults.pagesize },
      });
      Object.keys(this.user.posts).forEach(post =>
        this.$store.dispatch("fetchPostReplies", { postId: post }),
      );
      next();
    } catch (err) {
      console.log(err);
      if (err instanceof UserNotFoundError) {
        this.$store.dispatch("addPageError", err);
      }
    }
  },
};
</script>

<style lang="postcss" scoped>
.user-info {
  @apply flex flex-col;
}

.user-info-row {
  @apply flex;
}

.user-info-row span:first-of-type {
  @apply mr-2 text-right w-16;
}

.user-update-form-row {
  @apply flex flex-col;
}

@media (min-width: 1024px) {
  .user-profile {
    @apply flex-row;
  }
}
</style>
