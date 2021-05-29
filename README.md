# miko 集动漫、音乐、美图、galgame于一体的二次元社区app, 白嫖伸手党的福音です。

## 介绍
### miko二次元社区，整合动漫、音乐、galgame、美图资源，为您提供最好的看番追剧、听歌体验。
动漫区可以搜索近乎所有的动漫，使用自定义封装的AnimeView在线看番，动漫区和评论区布局借鉴B站app实现，弹幕功能测试ing。<br>
音乐区则完全使用了网易云音乐的API, 仿网易云app实现。<br>
美图区API完善ing，未分类，问就是random浏览本人爬取的几千张setu（笑），实现了长按下载功能，以及一些彩蛋(绅士必看)<br>
游戏区主要是galgame分享，使用scrapy爬取全网的galgame资源。链接失效正在补链ing。<br>

#### ps: 请使用网易云音乐账号登录miko!   您的star是开发者不竭的动力~~

<p class="half">
   <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/release/miko_1.0.2-beta.png" width="200"/><img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/release/qq.jpg" alt="联系我QQ" height="200"/>
</p>

[扫码或点击下载体验](https://mikochat.oss-ap-northeast-1.aliyuncs.com/release/miko_1.0.2-beta.apk)
<br/>

## 软件架构
### miko使用标准MVP模式、模块化开发。
1、app存放Activity和Fragment, 使用ButterKnife进行控件绑定。<br/>
2、com定义项目中公用的部分。封装网络和数据回调接口以及MVP模式基本契约，<br/>
   网络请求API、常量，封装RecyclerView, Adapter, 定义个性化控件等。 <br/>
3、fac定义数据Model，封装网络请求工具类，定义Retrofit2代理,以及Presenter的实现。<br/>
4、res存放item布局文件，各种drawable、string、color、dimen资源。是主要的资源模块。<br/>

## 功能截图
### 主页
<p class="half">
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/index/0.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/index/1.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/index/2.jpg" width="200px" />
    </p>
 <p>
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/index/3.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/index/4.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/index/5.jpg" width="200px" />
</p>

### 音乐区
<p class="half">
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/music/6.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/music/7.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/music/8.jpg" width="200px" />
 </p>
 <p>
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/music/9.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/music/10.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/music/11.jpg" width="200px" />
</p>
### 动漫区
<p class="half">
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/anime/19.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/anime/20.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/anime/21.jpg" width="200px" />
    </p>
 <p>
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/anime/22.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/anime/27.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/anime/28.jpg" width="200px" />
</p>

### 搜索
<p class="half">
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/12.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/13.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/14.jpg" width="200px" />
    </p>
 <p>
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/15.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/16.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/17.jpg" width="200px" />
    </p>
 <p>
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/search/18.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/game/25.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/game/26.jpg" width="200px" />
</p>

### 彩蛋
<p>
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/more/23.jpg" width="200px" />
    <img src="https://mikochat.oss-ap-northeast-1.aliyuncs.com/Screenshoots/miko/more/24.jpg" width="200px" />
</p>

## 参与贡献
1.  Fork 本仓库
2.  新建 master 分支
3.  提交代码
4.  新建 Pull Request

## 致谢
### Binaryify
NeteaseCloudMusicApi<br>
网易云音乐 Node.js API service<br>
[开源地址](https://github.com/Binaryify/NeteaseCloudMusicApi)

### zaxtyson
AnimeSearcher<br>
整合第三方网站的视频和弹幕资源, 提供最舒适的看番追剧体验<br>
[开源地址](https://gitee.com/zaxtyson/AnimeSearcher)
