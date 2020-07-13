<template>
  <div class="home">
    <post-viewer :posts="sortedPosts" />
  </div>
</template>

<script>
import PostViewer from "@/components/PostViewer.vue";
import { amountScrolled } from "@/utils";
import { postsDefaults } from "@/defaults";

export default {
  name: "Home",
  components: {
    PostViewer,
  },
  data: function() {
    return {
      scrollPos: 0,
      scrollTicking: false,
      updating: false,
    };
  },
  computed: {
    sortedPosts: function() {
      const posts = this.$store.getters.posts;
      return Object.values(posts).sort((a, b) => b.created - a.created);
    },
  },
  methods: {
    handleScroll: function() {
      if (!this.ticking) {
        window.requestAnimationFrame(() => {
          this.scrollPos = amountScrolled();
          this.scrollTicking = false;
        });

        this.scrollTicking = true;
      }
    },
  },
  created: function() {
    window.addEventListener("scroll", this.handleScroll);
  },
  destroyed: function() {
    window.removeEventListener("scroll", this.handleScroll);
  },
  watch: {
    scrollPos: async function(val) {
      if (Number.isNaN(val)) return;

      if (val >= 80) {
        const last = this.$store.getters.postsPageLast;
        const empty = this.$store.getters.postsPageEmpty;

        if (!(last && empty) && !this.updating) {
          this.updating = true;
          const pageNumber = this.$store.getters.postsPageNumber;
          const options = {
            page: pageNumber + 1,
            pagesize: postsDefaults.pagesize,
          };

          try {
            const posts = await this.$store.dispatch("fetchPosts", options);
            const replies = [];
            posts.forEach(post =>
              replies.push(
                this.$store.dispatch("fetchPostReplies", { postId: post.id }),
              ),
            );
            await Promise.all(replies);
            this.updating = false;
          } catch (err) {
            console.log(err);
          }
        }
      }
    },
  },
};
</script>
