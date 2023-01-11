import router from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import {getToken} from '@/utils/auth'
import {isRelogin} from '@/utils/request'
import login from './views/login'

NProgress.configure({showSpinner: false})

const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({path: '/'})
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        isRelogin.show = true
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then((res) => {
          let spaceId = sessionStorage.getItem("spaceId")
          let spaces = res.spaces;
          let isAdmin = res.user.admin;
          let isSpace = false;
          if (!spaceId) {
            store.dispatch('LogOut').then((res)=>{
              next({path: '/'})
            })

          } else {
            spaces.forEach(s => {
              if (s === spaceId) {
                isSpace = true;
                return
              }
            })
            if (isAdmin || isSpace) {
              isRelogin.show = false
              store.dispatch('GenerateRoutes').then(accessRoutes => {
                // 根据roles权限生成可访问的路由表
                router.addRoutes(accessRoutes) // 动态添加可访问路由表
                next({...to, replace: true}) // hack方法 确保addRoutes已完成
              })
            } else if (!isAdmin && !isSpace) {
              store.dispatch('LogOut').then(() => {
                Message.error("请选择正确的空间 ")
                setTimeout(() => {
                  //需要定时执行的代码
                  location.reload()
                }, 800)
              })
            }
          }
        }).catch(err => {
          store.dispatch('LogOut').then(() => {
            Message.error(err)
            next({path: '/'})
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else {
      next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
