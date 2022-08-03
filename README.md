Github Android客户端

[apk链接](https://github.com/CandyShoes/GithubClient/blob/main/app/release/app-release.apk)

## 框架设计
1、网络：
OKHTTP+Retrofit
原因：OkHttp 是一个非常优秀的网络请求框架
•  易使用、易扩展。
•  支持 HTTP/2 协议，允许对同一主机的所有请求共用同一个 socket 连接。
•  如果 HTTP/2 不可用, 使用连接池复用减少请求延迟。
•  支持 GZIP，减小了下载大小。
•  支持缓存处理，可以避免重复请求。
•  如果你的服务有多个 IP 地址，当第一次连接失败，OkHttp 会尝试备用地址。
•  OkHttp 还处理了代理服务器问题和SSL握手失败问题。
 而Retrofit 负责网络请求接口的封装，简化网络请求过程
使用kotlin的协成来简化网络请求调度时的线程切换


Retrofit中的设计模式
Builder（建造者）模式
将复杂对象的构建和表示相分离，使复杂对象的构建简单化
防止构造方法参数过多，造成使用者使用不便，通过链式调用不同方法设置不同参数
工厂模式
将“类实例化的操作”与“使用对象的操作”分开，降低耦合，易于扩展，有利于产品的一致性
策略模式
— 策略类之间可以自由切换，由于策略类都实现同一个接口，所以使它们之间可以自由切换。
易于扩展，增加一个新的策略只需要添加一个具体的策略类即可，基本不需要改变原有的代码，符合“开闭原则“
观察者模式
定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新

图片库：
Glide
原因：Glide 是在Picasso 基础之上进行的二次开发做了不少改进
对比Fresco，使用较Fresco简单，加载速度和缓存虽比不上Fresco，但对于github客户端这种不是特别注重速度和缓存的应用来说，选择glide更好

3、本地缓存：(后期优化，还使用原生的SharedPreferences来进行本地数据存储)
MMKV
登录信息、用户设置等持久化的本地数据相比于SharedPreferences选择腾讯的开源框架-MMKV
原因：MMKV 是基于 mmap 内存映射的 key-value 组件，底层序列化/反序列化使用 protobuf 实现，性能高，稳定性强。


4、日志记录：（后期优化）
XLog
原因：
日志支持加密，提高日志信息的安全性
底层使用c++实现，支持Android和iOS平台
支持设置单个日志文件的保存天数和大小，基本满足日常的开发需求

5、Toast吐司
Toasty
原因：第三方开源的小型提示语框，提供多种风格的主题，作为工具类十分好用


## 页面设计
主页
用户冷启进入主页时对未授权用户弹出授权弹窗，同意或拒绝都可继续使用。
后续可以优化为查看你所有的版本库，包括公开、私有、fork的，以及星标的
星标、关注和fork版本库


登录页
2.1、进入页面后调起浏览器，打开github的登录页，登录完成后唤起原activity，在intent里返回用户的token
（因为没有去github申请appid，所以使用的是第三方的应用client_id唤起github浏览器去登录）
2.1、使用MVP模式，通过LoginPresenter来进行服务的分发，数据和登录状态的处理
2.2、登录态token记录在本地，并缓存基础信息
2.3、下次启动使用缓存的token自动登录进入主页

搜索页
3.1、通过输入关键字发服务获得想搜索用户的数据，展示搜索详情，头部tab用于输入信息修改
3.2、内容搜索不到和无输入需toast和界面显示提醒
3.3、搜索结果使用recycleview展示，后面可实施分页加载
3.4、当前支持用户搜索，后面可扩展支持项目团队搜索
3.5、点击item跳转到用户详情页，可查看名字、头像、location、blog、关注数

个人资料页
4.1、支持查看查看用户头像、名字、邮箱地址、关注数、粉丝
4.2、查看用户的跟随者、跟随的人和属于的组织
4.3、支持退登，使用Builer设计模式链式调用构建退登询问弹窗
退出弹窗用户点击确认后成功后清除本地用户信息和记录

单例
5.1、application、appData（维护登录数据）使用了单例模式减少内存开销

BaseActivity
6.1、包含默认样式（即网络异常等原因）展现给用户

BasePresenter
7.1、对于使用MVP模式的presenter提供上下文Context，View的绑定和取消绑定方法
7.2、项目里login和search的presenter都继承BasePresenter

详情页
8.1、页面复用个人资料页，支持对搜索到的用户内容做详细展示
8.2、包含搜索到的用户头像、名字、location、email、关注数等信息
8.3、后续搜索支持项目搜索后可展示项目内容信息


## 测试用例
3.1登录页
唤起浏览器，正常登录会返回应用并更新资料页和用户信息
登录失败或网络异常有对应提示告知用户
杀掉app重新冷启会维持已登录状态
清楚数据，重新展示登录页

3.2、主页&搜索页
页面正常展示，滑动无异常
点击搜索栏能弹起键盘并不压缩页面
键盘输入后点击搜索按钮能发送服务接受到返回结果
无搜索结果有提示
结果数据压力测试（比如上万条返回）
数据准确性比较
边界case测试，如用户名为null或者特别长时展示情况
输入异常有提示

3.3、详情页
页面正常展示，点击无反应
数据准确性
边界长度case测试，如数据为null或者特别长时展示情况

## app使用截图
![3F1F3B296C869C0CDC8E8159A39163B4](https://user-images.githubusercontent.com/22748106/182506916-e31d2f19-6b79-4662-928f-74fc06de60c7.jpg)
![83D5992C12DCEF38DC1689E6306EF339](https://user-images.githubusercontent.com/22748106/182506939-c735594a-ad3c-4281-8bc0-01a8fc641bdc.jpg)
![AF08C3C4E413867E925C3AEA32F08F6C](https://user-images.githubusercontent.com/22748106/182506955-72de73a6-4cbc-4f77-9dea-e6eb11c0f2fb.jpg)
![41A478F5C7409E79DEBCBA82211581D2](https://user-images.githubusercontent.com/22748106/182506970-19958151-cb81-402f-a4b2-98f1f3180e3d.jpg)
![722D46DFEDDAF99BC92C4BC48183E418](https://user-images.githubusercontent.com/22748106/182507005-a369d804-0c16-4ddc-acc9-5f715de0cdb9.jpg)
![0CD817E56807EDFF94D6C0237CEB399E](https://user-images.githubusercontent.com/22748106/182507030-04142f27-c5d3-4792-9463-09bb1ae5d4ce.jpg)
![EFFF99CC590350B98A691BAB4628D479](https://user-images.githubusercontent.com/22748106/182507051-65c9588f-2a28-4e0c-bd1e-5e189e1e1032.jpg)
![260B198C888302ADB3E2927D7B5F6D39](https://user-images.githubusercontent.com/22748106/182507172-daa4d8b9-5414-4203-8882-05f823f7e395.jpg)
