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


