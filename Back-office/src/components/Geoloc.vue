<template>
<el-row v-if="active < 3">
    <el-col>
            <img :src="this.src + this.files[this.active]">
            <el-button @click="nextImage" :disabled="latlng == null" icon="el-icon-arrow-right">Photo suivante</el-button>
    <div >
            <l-map @click="demoClick"  :zoom="zoom" :center="center">
                <l-marker v-if="this.latlng" :lat-lng="this.latlng"></l-marker>
                <l-tile-layer ref="map" :key="tileProvider.name" :name="tileProvider.name" :url="tileProvider.url" :attribution="tileProvider.attribution" layer-type="base"/>
            </l-map>
    </div>
    </el-col>
</el-row>
</template>
<script>

import Vue2Leaflet from 'vue2-leaflet';
import { LMap, LTileLayer, LMarker} from 'vue2-leaflet';

export default {
    name: 'geoloc',
    data() {
        return {
            active: 0,
            name: "",
            tileProvider:{
                  name: 'OpenStreetMap',
                  visible: true,
                  attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
                  url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                },
            zoom:10,
            center: L.latLng(48.69333, 6.18324),
            lat: null,
            lng: null,
            latlng: null,
            src: "https://a8b10422.ngrok.io/office/files/?name=",
        }
    },
    props: {
        files: Array,
        addImg: Function
    },
    components: {
        LMap,
        LTileLayer,
        LMarker
    },
    methods: {
        demoClick(e) {
                this.lat = e.latlng.lat
                this.lng = e.latlng.lng
                this.latlng = e.latlng
                console.log(e)
      },
        nextImage() {
            //construction de l'element photo pour ajout dans un tableau et appel POST pour creer des photos
            this.addImg({
                dsc: "rien",
                url: this.src + this.files[this.active],
                x: this.lat,
                y: this.lng
            })
            console.log(this.images)
            this.latlng = null
            this.active++
             //if (this.active++ < this.imgs.length) this.active = 0;
        }
    },
}
</script>
<style scoped>
    img {
  width: 40%;
  height: auto;
}
.vue2leaflet-map{
        height:500px;        
        width:100%;
        margin: 0
    }
    .cont{
        display:flex;
        flex-direction: row;
        justify-content: center;
        width: 100%;
    }
</style>