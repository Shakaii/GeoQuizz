<template>
  <div id="app">
    <img src="./assets/logo.png">
    <Series></Series>
  </div>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import Series from './components/Series.vue'

export default {
  name: 'app',
  components: {
    HelloWorld,
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
  margin-top: 60px;
}
</style>
