import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Series from './components/Series.vue'
import CreateSerie from './components/CreateSerie.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Series
    },
    {
      path: '/newSerie',
      name: 'new serie',
      component: CreateSerie
    },
    {
      path: '/addPhotos',
      name: 'add photos',
      component: null
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    },
  ]
})
