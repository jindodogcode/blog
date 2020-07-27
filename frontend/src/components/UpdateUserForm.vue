<template>
  <form @submit.prevent="handleUserUpdateFormSubmit()" class="form">
    <h4 class="form-title">Update Profile</h4>
    <div class="form-row user-update-form-row">
      <label for="first-name">First Name:</label>
      <input
        type="text"
        id="first-name"
        minlength="1"
        maxlength="32"
        v-model="userForm.firstName"
      />
    </div>
    <div class="form-row user-update-form-row">
      <label for="last-name">Last Name:</label>
      <input
        type="text"
        id="last-name"
        minlength="1"
        maxlength="32"
        v-model="userForm.lastName"
      />
    </div>
    <div class="form-row user-update-form-row">
      <label for="about">About:</label>
      <textarea
        id="about"
        v-model="userForm.about"
        spellcheck="true"
        rows="4"
        maxlength="255"
        class="w-full resize-none"
      />
    </div>
    <div
      :class="{ 'text-red-600': aboutCharsRemaining < 1 }"
      class="text-right"
    >
      {{ aboutCharsRemaining }}
    </div>
    <div class="text-center">
      <input type="submit" value="Update" />
    </div>
  </form>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "UpdateUserForm",
  data: function() {
    return {
      userForm: {
        email: "",
        firstName: "",
        lastName: "",
        about: "",
      },
    };
  },
  computed: {
    ...mapGetters(["principal"]),
    aboutCharsRemaining: function() {
      return 255 - this.userForm.about.length;
    },
  },
  methods: {
    handleUserUpdateFormSubmit: async function() {
      try {
        const payload = {
          username: this.principal.username,
          updateForm: this.userForm,
        };
        await this.$store.dispatch("updatePrincipal", payload);
        this.$emit("closeUpdateUserForm");
      } catch (err) {
        console.log(err);
      }
    },
  },
  mounted: function() {
    this.userForm.email = this.principal.email;
    this.userForm.firstName = this.principal.firstName;
    this.userForm.lastName = this.principal.lastName;
    this.userForm.about = this.principal.about;

    document.querySelector(".form > .form-row:first-of-type input").focus();
  },
};
</script>

<style lang="postcss" scoped>
.user-update-form-row label {
  @apply flex flex-row mb-2;
}

@media (min-width: 1024px) {
  .user-update-form-row label {
    @apply mb-0 mr-4;
  }
}
</style>
