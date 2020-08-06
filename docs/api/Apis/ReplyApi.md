# ReplyApi

All URIs are relative to *http://127.0.0.1:8080/api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**addUserReply**](ReplyApi.md#addUserReply) | **POST** /users/{username}/replies | Create a new reply
[**getPostReplies**](ReplyApi.md#getPostReplies) | **GET** /posts/{id}/replies | Retreive replies
[**getReplies**](ReplyApi.md#getReplies) | **GET** /replies | Retreves replies
[**getReply**](ReplyApi.md#getReply) | **GET** /replies/{id} | Retreive a specific reply
[**getReplyReplies**](ReplyApi.md#getReplyReplies) | **GET** /replies/{id}/replies | Retreives replies
[**getUserReplies**](ReplyApi.md#getUserReplies) | **GET** /users/{username}/replies | Retreive replies by specific user
[**updateUserReply**](ReplyApi.md#updateUserReply) | **PATCH** /users/{username}/replies/{id} | Updates a specific reply


<a name="addUserReply"></a>
# **addUserReply**
> Reply addUserReply(username, newReply, jsessionid)

Create a new reply

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **newReply** | [**NewReply**](..//Models/NewReply.md)| Object representing a new reply |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**Reply**](..//Models/Reply.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

<a name="getPostReplies"></a>
# **getPostReplies**
> PageableReplies getPostReplies(id, page, pagesize, after)

Retreive replies

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]
 **after** | **Date**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="getReplies"></a>
# **getReplies**
> PageableReplies getReplies(page, pagesize)

Retreves replies

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

<a name="getReply"></a>
# **getReply**
> Reply getReply(id)

Retreive a specific reply

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]

### Return type

[**Reply**](..//Models/Reply.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="getReplyReplies"></a>
# **getReplyReplies**
> PageableReplies getReplyReplies(id, page, pagesize)

Retreives replies

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="getUserReplies"></a>
# **getUserReplies**
> PageableReplies getUserReplies(username, page, pagesize)

Retreive replies by specific user

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **page** | **Integer**|  | [optional] [default to null]
 **pagesize** | **Integer**|  | [optional] [default to null]

### Return type

[**PageableReplies**](..//Models/PageableReplies.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json, applicaton/json

<a name="updateUserReply"></a>
# **updateUserReply**
> Reply updateUserReply(username, id, updateReply, jsessionid)

Updates a specific reply

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **username** | **String**|  | [default to null]
 **id** | **Integer**|  | [default to null]
 **updateReply** | [**UpdateReply**](..//Models/UpdateReply.md)| Object containing the fields to update a reply |
 **jsessionid** | **String**|  | [optional] [default to null]

### Return type

[**Reply**](..//Models/Reply.md)

### Authorization

[basicAuth](../README.md#basicAuth)

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: application/json, applicaton/json

