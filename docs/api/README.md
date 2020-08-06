# Documentation for Blog Api

<a name="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *http://127.0.0.1:8080/api/v1*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*DefaultApi* | [**login**](Apis/DefaultApi.md#login) | **GET** /login | Login
*DefaultApi* | [**logout**](Apis/DefaultApi.md#logout) | **GET** /logout | Logout
*DefaultApi* | [**me**](Apis/DefaultApi.md#me) | **GET** /me | Login by cookie
*PostApi* | [**addUserPost**](Apis/PostApi.md#adduserpost) | **POST** /users/{username}/posts | Create a new post
*PostApi* | [**getPost**](Apis/PostApi.md#getpost) | **GET** /posts/{id} | Retreives a specific post
*PostApi* | [**getPostReplies**](Apis/PostApi.md#getpostreplies) | **GET** /posts/{id}/replies | Retreive replies
*PostApi* | [**getPosts**](Apis/PostApi.md#getposts) | **GET** /posts | Retreives posts
*PostApi* | [**getReplyReplies**](Apis/PostApi.md#getreplyreplies) | **GET** /replies/{id}/replies | Retreives replies
*PostApi* | [**getUserPosts**](Apis/PostApi.md#getuserposts) | **GET** /users/{username}/posts | Retreive posts by the user specified
*PostApi* | [**updateUserPost**](Apis/PostApi.md#updateuserpost) | **PATCH** /users/{username}/posts/{id} | Update post
*ReplyApi* | [**addUserReply**](Apis/ReplyApi.md#adduserreply) | **POST** /users/{username}/replies | Create a new reply
*ReplyApi* | [**getPostReplies**](Apis/ReplyApi.md#getpostreplies) | **GET** /posts/{id}/replies | Retreive replies
*ReplyApi* | [**getReplies**](Apis/ReplyApi.md#getreplies) | **GET** /replies | Retreves replies
*ReplyApi* | [**getReply**](Apis/ReplyApi.md#getreply) | **GET** /replies/{id} | Retreive a specific reply
*ReplyApi* | [**getReplyReplies**](Apis/ReplyApi.md#getreplyreplies) | **GET** /replies/{id}/replies | Retreives replies
*ReplyApi* | [**getUserReplies**](Apis/ReplyApi.md#getuserreplies) | **GET** /users/{username}/replies | Retreive replies by specific user
*ReplyApi* | [**updateUserReply**](Apis/ReplyApi.md#updateuserreply) | **PATCH** /users/{username}/replies/{id} | Updates a specific reply
*UserApi* | [**addUser**](Apis/UserApi.md#adduser) | **POST** /users | Creates a new user
*UserApi* | [**addUserPost**](Apis/UserApi.md#adduserpost) | **POST** /users/{username}/posts | Create a new post
*UserApi* | [**addUserReply**](Apis/UserApi.md#adduserreply) | **POST** /users/{username}/replies | Create a new reply
*UserApi* | [**deleteUser**](Apis/UserApi.md#deleteuser) | **DELETE** /users/{username} | Delete user
*UserApi* | [**getUser**](Apis/UserApi.md#getuser) | **GET** /users/{username} | Retreives user information
*UserApi* | [**getUserPosts**](Apis/UserApi.md#getuserposts) | **GET** /users/{username}/posts | Retreive posts by the user specified
*UserApi* | [**getUserReplies**](Apis/UserApi.md#getuserreplies) | **GET** /users/{username}/replies | Retreive replies by specific user
*UserApi* | [**updateUser**](Apis/UserApi.md#updateuser) | **PATCH** /users/{username} | Updates user
*UserApi* | [**updateUserPost**](Apis/UserApi.md#updateuserpost) | **PATCH** /users/{username}/posts/{id} | Update post
*UserApi* | [**updateUserReply**](Apis/UserApi.md#updateuserreply) | **PATCH** /users/{username}/replies/{id} | Updates a specific reply


<a name="documentation-for-models"></a>
## Documentation for Models

 - [ApiError](.//Models/ApiError.md)
 - [NewPost](.//Models/NewPost.md)
 - [NewReply](.//Models/NewReply.md)
 - [NewUser](.//Models/NewUser.md)
 - [Pageable](.//Models/Pageable.md)
 - [PageablePosts](.//Models/PageablePosts.md)
 - [PageableReplies](.//Models/PageableReplies.md)
 - [PageableUsers](.//Models/PageableUsers.md)
 - [Post](.//Models/Post.md)
 - [Reply](.//Models/Reply.md)
 - [Sort](.//Models/Sort.md)
 - [SubError](.//Models/SubError.md)
 - [UpdatePost](.//Models/UpdatePost.md)
 - [UpdateReply](.//Models/UpdateReply.md)
 - [UpdateUser](.//Models/UpdateUser.md)
 - [User](.//Models/User.md)


<a name="documentation-for-authorization"></a>
## Documentation for Authorization

<a name="basicAuth"></a>
### basicAuth

- **Type**: API key
- **API key parameter name**: JSESSIONID
- **Location**: 

