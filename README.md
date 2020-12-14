# Tickoff

## 前端

#### 第三方框架

> mpvue

http://mpvue.com/



#### 第三方组件库



> Vant Weapp

https://youzan.github.io/vant-weapp/#/intro

如何在mpvue里面使用Vant：https://github.com/Rychou/mpvue-vant



> Wux Weapp

https://wux-weapp.github.io/wux-weapp-docs/#/introduce



> iView+mpvue

https://iview-mpvue.gitbook.io/iview-mpvue/bu-ju/layout-zha-ge-bu-ju



> wx_calendar

一个日历第三方组件库

https://github.com/treadpit/wx_calendar

```json
{
  "usingComponents": {
      //两个版本
      "xl-calendar1":"../../../static/xl_calendar/v1",
      "xl-calendar2":"../../../static/xl_calendar/v2"
  }
}
```



#### mpvue框架|举例：在home页面里面使用第三方组件库“wux-form”： 

1. 下载Wux Weapp组件库，将相应的文件夹复制到static文件夹下

- `home/index.vue`
- `home/main.js`

2. 在home文件夹下新建

- `home/main.json`

3. 在`home/main.json`内引入第三方组件

```json
{
"usingComponents": {
    "wux-form": "../../../static/wux/form"
  }
}
```


