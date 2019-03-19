<template>
  <div id="app">
    <nav-bar></nav-bar>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>
    <img src="./assets/logo.png">
    
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue'
import Series from './components/Series.vue'

export default {
  name: 'app',
  components: {
    NavBar,
    Series
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  
}
</style>
