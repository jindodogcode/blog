<template>
  <section>
    <div v-if="user" class="user-profile">
      <div class="user-info-bar">
        <h3 class="text-2xl font-bold">{{ user.username }}</h3>
        <div class="user-info">
          <div class="user-info-row">
            <span>email:</span>
            <span>{{ user.email }}</span>
          </div>
          <div class="user-info-row">
            <span>name:</span>
            <span>{{ user.firstName }} {{ user.lastName }}</span>
          </div>
          <div class="user-info-row">
            <span># posts:</span
            ><span>{{ user.posts.content.length || 0 }}</span>
          </div>
        </div>
      </div>
      <post-viewer :posts="user.posts.content" class="flex-grow" />
    </div>
    <div v-else class="text-center">
      That user does not exist.
    </div>
  </section>
</template>

<script>
import PostViewer from "@/components/PostViewer.vue";

export default {
  name: "UserProfile",
  components: {
    PostViewer,
  },
  props: {
    username: {
      type: String,
      required: true,
    },
  },
  computed: {
    user: function() {
      return this.$store.getters.userByUsername(this.username);
    },
  },
};
</script>

<style lang="postcss" scoped>
.user-profile {
  @apply flex flex-col w-full;
}

.user-info {
  @apply flex flex-col;
}

.user-info-row {
  @apply flex;
}

.user-info-row span:first-of-type {
  @apply mr-2 text-right w-16;
}

@media (min-width: 1024px) {
  .user-profile {
    @apply flex-row;
  }
}
</style>
