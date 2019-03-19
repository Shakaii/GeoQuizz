import Vue from 'vue'
import Router from 'vue-router'
import AddImage from './components/AddImage.vue'
import Series from './components/Series.vue'
import CreateSerie from './components/CreateSerie.vue'
import Con from './components/Con.vue'
import Insc from './components/Insc.vue'

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
      path: '/addPhotos/:idSerie',
      name: 'add photos',
      component: AddImage,
      props: true,
    },
    {
      path: '/connexion',
      name: 'con',
      component: Con
    },
    {
      path: '/inscription',
      name: 'insc',
      component: Insc
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
