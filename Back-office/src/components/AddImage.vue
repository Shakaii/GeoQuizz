<template>
<div>
    <el-steps :active="active" finish-status="success" align-center>
        <el-step title="Upload vos images"></el-step>
        <el-step title="Geolocalisation des images"></el-step>
        <el-step title="Finalisation"></el-step>
    </el-steps>
    <div v-show="active == 0" >
        <file-pond  @processfile="detail" name="file" allow-multiple="true" max-files="3" server="http://localhost:8081/office/file"/>
        <el-button :disabled="imgs.length == 0"  style="margin-top: 12px;" @click="next" icon="el-icon-arrow-right">Prochaine étape</el-button>
    </div>
    <div v-show="active == 1">
        <geoloc :files=this.imgs></geoloc>
        <el-button  style="margin-top: 12px;" @click="back">Étape précédente</el-button>
        <el-button style="margin-top: 12px;" @click="next" icon="el-icon-arrow-right">Prochaine étape</el-button>
    </div>
    <div v-show="active == 2">
        <el-button  style="margin-top: 12px;" @click="back">Étape précédente</el-button>
        <router-link to="/"><el-button  style="margin-top: 10px; margin-left: 12px">Terminer</el-button></router-link>
    </div>
    
</div>
</template>

<script>
// Import FilePond
import vueFilePond, {setOptions } from 'vue-filepond';
import Geoloc from './Geoloc'
// Import styles
import 'filepond/dist/filepond.min.css';

// Create FilePond component
const FilePond = vueFilePond();

setOptions({
    server: {
        remove: (source, load, error) => {
            // Should somehow send `source` to server so server can remove the file with this source
            console.log(source)
            // Can call the error method if something is wrong, should exit after
            error('oh my goodness');

            // Should call the load method when done, no parameters required
            load();
        }
    }
});

export default {
    name: 'addimage',
    data() {
        return {
            active: 0,
            img: 1,
            imgs: [],
            src: "http://localhost:8081/office/files/?name="
        }
    },
    components: {
        FilePond,
        Geoloc
    },
    methods: {
        detail(error, file) {
            this.imgs.push(file.file.name)
            console.log(this.imgs[0])
        },
        next() {
        if (this.active++ > 2) this.active = 0;
      },
      back() {
        if (this.active-- < 0) this.active = 0;
      },
      nextImage() {
          if (this.img++ < this.imgs.length) this.img = 0;
      }
    },
    computed: {
        
    },
}
</script>

<style scoped>
    img {
  width: 50%;
  height: auto;
}
</style>
