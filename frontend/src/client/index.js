import {
  ApiClient,
  DefaultApi,
  UserApi,
  PostApi,
  ReplyApi,
  User,
  Post,
  Reply,
  PageableUsers,
  PageablePosts,
  PageableReplies,
  NewUser,
  NewPost,
  NewReply,
  UpdateUser,
  UpdatePost,
  UpdateReply,
} from "@jindodog/blog-client";

const apiClient = new ApiClient();
apiClient.enableCookies = true;

const defaultApi = new DefaultApi(apiClient);
const userApi = new UserApi(apiClient);
const postApi = new PostApi(apiClient);
const replyApi = new ReplyApi(apiClient);

const model = {
  User,
  Post,
  Reply,
};

const pages = {
  PageableUsers,
  PageablePosts,
  PageableReplies,
};

const forms = {
  new: {
    NewUser,
    NewPost,
    NewReply,
  },
  update: {
    UpdateUser,
    UpdatePost,
    UpdateReply,
  },
};

export default {
  defaultApi,
  userApi,
  postApi,
  replyApi,
  model,
  pages,
  forms,
};
