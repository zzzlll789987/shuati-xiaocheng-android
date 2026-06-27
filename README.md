# 刷题小橙

一个面向手机使用的刷题练习项目，当前包含 Android 安装版和微信小程序版。

## Android 版功能

- 首页统计：总题数、已完成、正确率、连续答对
- 分类刷题：综合能力、数学基础、英语词汇、常识判断
- 即时判题：答完立即显示正确/错误和解析
- 错题本：答错的题会自动收集，可单独重练
- 本地进度：答题记录保存在手机本地

## 微信小程序版

小程序工程在 `miniprogram/` 目录中，功能与 Android MVP 保持一致：

- 首页统计
- 分类刷题
- 即时判题与解析
- 错题本
- 微信本地缓存保存进度

### 打开方式

1. 安装微信开发者工具
2. 选择导入项目
3. 项目目录选择本仓库的 `miniprogram/`
4. 暂时没有 AppID 时可选择测试号或使用 `touristappid`
5. 获取正式小程序 AppID 后，替换 `miniprogram/project.config.json` 里的 `appid`

## Android APK 构建

这个仓库已经配置 GitHub Actions。推送代码后：

1. 打开 GitHub 仓库页面
2. 进入 `Actions`
3. 选择 `Build Android APK`
4. 下载 `shuati-xiaocheng-debug-apk`
5. 在 Android 手机上安装其中的 `app-debug.apk`
