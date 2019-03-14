import Vue from "nativescript-vue";

import App from "./components/App";
import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)
Vue.registerElement('RadSideDrawer', () => require('nativescript-ui-sidedrawer').RadSideDrawer)

new Vue({
    render: h => h('frame', [h(App)])
}).$start();
