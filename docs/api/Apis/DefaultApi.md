# DefaultApi

All URIs are relative to *http://127.0.0.1:8080/api/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**login**](DefaultApi.md#login) | **GET** /login | Login
[**logout**](DefaultApi.md#logout) | **GET** /logout | Logout
[**me**](DefaultApi.md#me) | **GET** /me | Login by cookie


<a name="login"></a>
# **login**
> User login(authorization)

Login

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **authorization** | **String**|  | [default to null]

### Return type

[**User**](..//Models/User.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

<a name="logout"></a>
# **logout**
> logout()

Logout

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined

<a name="me"></a>
# **me**
> User me()

Login by cookie

### Parameters
This endpoint does not need any parameter.

### Return type

[**User**](..//Models/User.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

