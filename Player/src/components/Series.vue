<template>
    <el-row>
        <serie v-on:selectSerie="selectSerie" v-for="serie in series" v-bind:name="serie.ville" v-bind:id="serie.id" v-bind:selected="serie.ville == selectedSerie"></serie>
    </el-row>
</template>

<script>
import Serie from './Serie.vue'
export default {
  name: 'series',
  components: {
    Serie,
   
  },
  data() {
      return {
          series: [],
          selectedSerie: ""
      }
  },
  methods: {
      retrieveSeries() {
          this.axios.get('http://localhost:8081/office/series')
          .then((response) => {
              this.series = response.data._embedded.series
          });
      },
      selectSerie(ville){
          this.selectedSerie = ville
      }
  },
  created: function() {
      this.retrieveSeries()
      this.series = [{
                  ville: "Nancy"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }, {
                ville: "Paris"
              }]
  }
}
</script>

<style scoped>

.h {
  overflow-x: scroll;
  overflow-y: hidden;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
}

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