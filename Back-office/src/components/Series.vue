<template>
    <el-row>
        <serie v-for="serie in series" v-bind:name="serie.ville"></serie>
    </el-row>
</template>

<script>
import Serie from './Serie.vue'

export default {
  name: 'series',
  components: {
    Serie
  },
  data() {
      return {
          series: []
      }
  },
  props: {
    
  },
  methods: {
      retrieveSeries() {
          this.axios.get('http://localhost:8081/office/series')
          .then((response) => {
              console.log(response)
              this.series = response.data._embedded.series
          })
      }
  },
  created: function() {
      this.retrieveSeries()
  }
}
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>