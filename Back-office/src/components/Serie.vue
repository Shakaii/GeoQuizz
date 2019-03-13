<template>
        <el-button>
            <h1>{{name}}</h1>
            <img src="../assets/logo.png">
        </el-button>
</template>

<script>
import Serie from './Serie.vue'

export default {
  name: 'serie',
  data() {
      return {
          series: []
      }
  },
  props: {
    name
  },
  methods: {
      retrieveSeries() {
          this.axios.get('http://localhost:8081/office/series')
          .then((response) => {
              console.log(response)
              this.series = response.data._embedded.series
          })
      }
  }
}
</script>