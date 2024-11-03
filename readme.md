# Springboot登录系统接口文档

## 1. 用户注册

### 接口描述
注册新用户

### 请求信息
- 请求路径: `/api/user/register`
- 请求方法: POST
- Content-Type: application/x-www-form-urlencoded

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

### 响应参数
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码，200成功，500失败 |
| message | String | 提示信息 |
| data | Object | 用户信息 |

### 示例

```bash
# 请求示例
curl -X POST 'http://localhost:8080/api/user/register' \
-H 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'username=testuser' \
--data-urlencode 'password=123456'

# 成功响应
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "username": "testuser",
        "password": "e10adc3949ba59abbe56e057f20f883e"
    }
}

# 失败响应
{
    "code": 500,
    "message": "用户名已存在",
    "data": null
}
```

## 2. 用户登录

### 接口描述
用户登录并获取session

### 请求信息
- 请求路径: `/api/user/login`
- 请求方法: POST
- Content-Type: application/x-www-form-urlencoded

### 请求参数
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

### 响应参数
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码，200成功，500失败 |
| message | String | 提示信息 |
| data | Object | 用户信息 |

### 示例

```bash
# 请求示例
curl -X POST 'http://localhost:8080/api/user/login' \
-H 'Content-Type: application/x-www-form-urlencoded' \
-c cookies.txt \
--data-urlencode 'username=testuser' \
--data-urlencode 'password=123456'

# 成功响应
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "username": "testuser",
        "password": "e10adc3949ba59abbe56e057f20f883e"
    }
}

# 失败响应
{
    "code": 500,
    "message": "用户名或密码错误",
    "data": null
}
```

## 3. 用户登出

### 接口描述
用户登出，清除session

### 请求信息
- 请求路径: `/api/user/logout`
- 请求方法: POST
- 需要登录: 是

### 请求参数
无

### 响应参数
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码，200成功，500失败 |
| message | String | 提示信息 |
| data | null | 无返回数据 |

### 示例

```bash
# 请求示例
curl -X POST 'http://localhost:8080/api/user/logout' \
-b cookies.txt

# 成功响应
{
    "code": 200,
    "message": "success",
    "data": null
}
```

## 4. 获取用户信息

### 接口描述
获取当前登录用户信息

### 请求信息
- 请求路径: `/api/user/info`
- 请求方法: GET
- 需要登录: 是

### 请求参数
无

### 响应参数
| 参数名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码，200成功，500失败 |
| message | String | 提示信息 |
| data | Object | 用户信息 |

### 示例

```bash
# 请求示例
curl -X GET 'http://localhost:8080/api/user/info' \
-b cookies.txt

# 成功响应
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "username": "testuser",
        "password": "e10adc3949ba59abbe56e057f20f883e"
    }
}

# 未登录响应
{
    "code": 500,
    "message": "用户未登录",
    "data": null
}
```

## 注意事项
1. 所有接口返回格式统一
2. 密码使用MD5加密存储
3. 除了登录和注册接口，其他接口都需要登录后才能访问
4. Session默认过期时间为30分钟
5. 使用curl测试时，登录后需要保存cookies（-c cookies.txt），后续请求需要带上cookies（-b cookies.txt）