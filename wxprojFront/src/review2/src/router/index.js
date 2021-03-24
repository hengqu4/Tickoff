import Vue from 'vue'
import Router from 'vue-router'

// 首页
import Home from '@/views/Home'
// 登录注册界面
import Login from '@/views/Login'
// 欢迎界面
import Welcome from '@/views/Welcome'
// 项目
import Project from '@/views/project'
import File from '@/components/project/File'
import Meeting from '@/components/project/Meeting'
import Recycle from '@/components/project/Recycle'
// 个人中心 
import User from '@/views/User'
import Account from '@/components/user/Account'
import Message from '@/components/user/Message'
import MyMeeting from '@/components/user/MyMeeting'
import MyVideo from '@/components/user/MyVideo'
import UserInfo from '@/components/user/UserInfo' 
// 审阅界面
import Review from '@/views/Review'
import ReviewOffline from '@/views/ReviewOffline'
// 管理员界面
import Admin from '@/views/Admin'
import ProjectManage from '@/admin/ProjectManage'
import UserManage from '@/admin/UserManage'
import VideoManage from '@/admin/VideoManage'
import MeetingManage from '@/admin/MeetingManage'
import AdminLogin from '@/admin/Login'
Vue.use(Router)
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: '主页',
    component: Home,
  },{
    path: '/welcome',
    name: '欢迎界面',
    component: Welcome
  },
  {
    path: '/login',
    name: '登录',
    component: Login
  },
  {
    path:'/project',
    name:'项目',
    component: Project,
    redirect:'/project/file',
    children:[
      {
        path:'/project/file',
        name:'文件',
        component:File,
      },{
        path:'/project/meeting',
        name:'会议',
        component:Meeting,
      },{
        path:'/project/recycle',
        name:'回收站',
        component:Recycle
      }
    ]
  },{
    path: '/user',
    name: 'User',
    name:'用户中心',
    component: User,
    redirect:'/user/userInfo',
    children:[
      {
        path: '/user/userInfo',
        name: '个人信息',
        component: UserInfo,
      },
      {
        path: '/user/message',
        name: '消息中心',
        component: Message,
      },
      {
        path: '/user/myVideo',
        name: '我的审阅',
        component: MyVideo,
      },{
        path: '/user/account',
        name: '账号安全',
        component: Account,
      },{
      path:'/user/myMeeting',
        name:'我的会议',
        component:MyMeeting
      },
    ]
  },{
    path:'/review',
    name:'审阅',
    component:Review,
  },{
    path:'/reviewOffline',
    name:'线下审阅',
    component:ReviewOffline
  },
  {
    path:'/admin/login',
    name:'管理员登录',
    component:AdminLogin
  },
  {
    path:'/admin',
    name:"Admin",
    component: Admin,
    redirect:'/admin/userManage',
    children:[
      {
        path:'/admin/userManage',
        name:"用户管理",
        component: UserManage,
      },
      {
        path:'/admin/projectManage',
        name:"项目管理",
        component: ProjectManage,
      },
      {
        path:'/admin/videoManage',
        name:"视频管理",
        component: VideoManage,
      },
      {
        path:'/admin/meetingManage',
        name:"会议管理",
        component: MeetingManage,
      }
    ]
  },
]

const router = new Router({
  mode: "history",
  routes
});
  // 路由导航守卫
router.beforeEach((to, from, next) => {
  if (to.path === "/login") {
    window.localStorage.removeItem('token');
    window.localStorage.removeItem('userId')
    window.localStorage.removeItem('mobileNum')
    return next();
  }

  if (to.path === "/welcome") {
    return next();
  }
  if(to.path === '/admin/login'){
    window.localStorage.removeItem('token');
    return next()
  }
const tokenStr = window.localStorage.getItem("token");
if (!tokenStr) {
  if (to.path ==='/admin' || to.path==='/admin/viewManage' ||to.path==='/admin/meetingManage'||to.path==='/admin/videoManage' || to.path==='/admin/roleManage' || to.path === '/admin/userManage' || to.path==='/admin/projectManage'){
    return next("/admin/login");
  }else{
    return next("/login");
  }
}
 
next();

});

export default router;
