#### Spring Security 
OAuth2.0中定义了4中授权方式
1.implicit		简化模式，只通过前端js控制权限
2.authorization code 授权码模式，单次临时凭证换取access token&refresh token，一oAuth2.0鼓励此种模式，也即本项目所使用的模式
3.resource owner password credentials	密码模式，用密码换取token，需要对客户端高度信任（通常为自家客户端）
4.client credentials	客户端模式，当来源极其可信任、或调用一个后端模块、或无用户界面时，可使用该模式，鉴权服务器对客户端进行身份认证，通过后返回token调取资源


无法启动！存在javax问题！
WebSecurityConfiguration        进行认证
AuthorizationServerConfiguration进行授权

todo 这个模块没有成功过,相关资料比较欠缺,需要重新构建