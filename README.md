# 宠物猫_Android

#### 介绍
内容社交产品矩阵之 宠物猫 app

#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1.  安装依赖，推荐使用`yarn`，或者使用`npm install`
2.  启动项目：
    * 开启一个模拟器，或者使用 usb 连接真机设备
    * 运行：
      * 方式 1：启动服务(`npm run start` / `react-native start` ) + Android Studio `Run app`
      * 方式 2：`npm run dev` + `npm run debug`(debug 模式)
3.  打包命令：`npm run bundleTab3`
4.  RN 和本地对应的路由：  
    | 路由          | 对应界面         | 参数              |
    | ------------- | ---------------- | ----------------- |
    | fragment3     | 个人中心         |                   |
    | myShare       | 个人页面的分享   |                   |
    | fragment4     | X 友分享列表     |                   |
    | xFriendDetail | X 友分享内页     |                   |
    | comment       | 评论             |                   |
    | detailChat    | 互动话题内页     |                   |
    | search        | 搜索             |                   |
    | myNotice      | 通知             |                   |
    | recommend     | 我要推荐         |                   |
    | catPublish    | 发帖界面         | {type}            |
    | catComment    | 评论——宠物猫     | {contentId, type} |
    | catDetailChat | 图文内页——宠物猫 | {contentId}       |
    