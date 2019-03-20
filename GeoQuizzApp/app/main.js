import Vue from 'nativescript-vue'
import App from './components/App'
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.use(VueAxios, axios)
// Prints Vue logs when --env.production is *NOT* set while building
//Vue.config.silent = (TNS_ENV === 'production')

Vue.registerElement('RadSideDrawer', () => require('nativescript-ui-sidedrawer').RadSideDrawer)

//pour mapbox
Vue.registerElement("Mapbox", () => require("nativescript-mapbox").MapboxView)

new Vue({
  render: h => h('frame', [h(App)])
}).$start()
