<template>
<el-row>
    <el-form :inline="true" >
            <el-form-item>
                <el-input placeholder="Nom de la nouvelle série" v-model="newS" clearable @keyup.enter="createSerie()"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button @click="createSerie()" icon="el-icon-plus" type="success" circle></el-button>
            </el-form-item>
        </el-form>
    <el-row class="h">
        <serie :fn="retrieveSerie" v-for="serie in series" :name="serie.ville" :id="serie._links.self.href"></serie>
    </el-row>
    <div v-if="serie">
        <el-button @click="dialogVisible = true" :before-close="handleClose" icon="el-icon-plus">Ajouter une image</el-button>
        <add-image :visible.sync="dialogVisible"></add-image>
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

import vueFilePond from 'vue-filepond';

// Create FilePond component
const FilePond = vueFilePond();

export default {
  name: 'series',
  components: {
    Serie,
    FilePond,
    AddImage,
    DisplayPhotos
  },
  data() {
      return {
          series: [],
          serie:null,
          newS: "",
          myFiles: null,
          dialogVisible: false
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
    createSerie() {
        this.axios.post('http://localhost:8081/office/series', {
            ville: this.newS,
            dist: 3
        })
        .then((response) => {
            console.log(response.data);
            this.series.push(response.data)
        })
        .catch(function (error) {
            console.log(error);
        });
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