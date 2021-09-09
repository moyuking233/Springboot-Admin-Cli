# Springboot-Security-Single-Server-Cli
## 自用springboot项目脚手架，仅集成Security，无Oauth2.0无jwt，单例服务器
### 1. 内含标准user,permission,role标准三表，以及对应的联系表sql
### 2. 集成mybatis-plus,security,lombok,swagger(knife4j)
### 3. 密码在测试用例里执行获得 
## 使用方式（都在HelloController）
### 1.登录接口：`http://localhost:8080/doLogin` 
### 参数格式：
### form-data
### username: admin
### password: 123
### 成功后返回userInfo,如果不想返回也可以自己再额外定义一个接口，security本身的认证是基于cookies的
## -------------------------------------
### 2.测试接口：·http://localhost:8080/hello·
### 在未登录情况下直接访问会被重定向到/login（可以自己定义），登录了之后访问返回hello字样
## -------------------------------------
### 3.测试接口：·http://localhost:8080/fuck·
### 登录了之后访问，由于没有权限， 返回403 forbidden

### 权限的设置，可以在数据内设置
### 每个用户可以扮演多个角色，每个角色可以有多个权限（实际权限就是能够被放行的url）