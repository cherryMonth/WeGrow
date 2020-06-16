import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ToDoList from '@/components/ToDoList'
import login from '@/components/login'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/ToDoList',
      name: 'ToDoList',
      component: ToDoList
    },
    {
      path: '/login',
      name: 'login',
      component: login
    }
  ]
})

router.beforeEach((to, from, next) => {
  // to and from are both route objects. must call `next`.
  if (to.path === '/login') {
    next()
  } else {
    let token = localStorage.getItem('Authorization')
    if (token === null || token === '') {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
