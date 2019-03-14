<template>
<el-row>
    <router-link to="/newSerie"><el-button icon="el-icon-plus">Nouvel série</el-button></router-link>
    <el-row class="h">
        <serie :fn="retrieveSerie" v-for="serie in series" :name="serie.ville" :id="serie._links.self.href"></serie>
    </el-row>
    <div v-if="serie">
        <router-link to="/addPhotos/"><el-button icon="el-icon-plus">Ajouter une image</el-button></router-link>
        <el-row type="flex" :gutter="8">
            <display-photos :photos="serie.photos"></display-photos>
        </el-row>
    </div>
</el-row>
</template>

<script>
import Serie from './Serie.vue'
import AddImage from './AddImage.vue'
import DisplayPhotos from './DisplayPhotos.vue'
import { constants } from 'fs';

export default {
  name: 'series',
  components: {
    Serie,
    AddImage,
    DisplayPhotos
  },
  data() {
      return {
          series: [],
          serie:null,
          newS: "",
      }
  },
  props: {
    
  },
  methods: {
    retrieveSeries() {
          this.axios.get('http://localhost:8081/office/series')
          .then((response) => {
              this.series = response.data._embedded.series
          })
      },
    retrieveSerie(link) {
          console.log("here")
          this.axios.get(link)
          .then((response) => {
              console.log(response)
              this.serie = response.data
          })
          .catch((error) => {
              console.log(error);
          })
      },
      handleClose(done) {
        this.$confirm('Voulez-vous vraiment quitter ?')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      }
  },
  created() {
      this.retrieveSeries()
  },
  updated() {
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

.h {
  overflow-x: scroll;
  overflow-y: hidden;
  white-space: nowrap;
  -webkit-overflow-scrolling: touch;
}

.image {
    width: 100%;
    display: block;
  }
</style>