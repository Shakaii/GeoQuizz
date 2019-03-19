<template>
<FlexboxLayout>
    <Label text="Création de série"/>
    <StackLayout v-show="active == 0" >
            <TextView text="Nom de la série"/>
            <!--el-input
                placeholder="Nom/Ville de la serie"
                v-model="name"
                clearable>
            </el-input>
        <el-button :disabled='name == ""' style="margin-top: 12px;" icon="el-icon-arrow-right" @click="next">Prochaine étape</el-button-->
    </StackLayout>
    <StackLayout v-show="active == 1">
        <TextView text="Géolocalisation de la série"/>
        <!--el-button  style="margin-top: 12px;" @click="back">Étape précédente</el-button>
        <el-button :disabled="lat == null"  style="margin-top: 12px;" icon="el-icon-arrow-right" @click="next">Prochaine étape</el-button-->
    </StackLayout>
    <StackLayout v-show="active == 2">
        <TextView text="Ajout des photos"/>
        <!--el-button  style="margin-top: 12px;" @click="back">Étape précédente</el-button>
        <el-button  style="margin-top: 12px;" @click="createSerie">Ajouter des photos</el-button>
        <router-link to="/"><el-button  style="margin-top: 12px;" @click="createSerie">Terminer</el-button></router-link-->
    </StackLayout>
</FlexboxLayout>
</template>

<script>
//import Vue2Leaflet from 'vue2-leaflet';
//import { LMap, LTileLayer, LMarker} from 'vue2-leaflet';

export default {
    name: 'createserie',
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
            zoom:13,
            center: null,//L.latLng(48.69333, 6.18324),
            lat: null,
            lng: null,
            imgUrl: ""
        }
    },
    components: {
        /*LMap,
        LTileLayer,
        LMarker,*/
    },
    methods: {
      next() {
        if (this.active++ > 2) this.active = 0;
      },
      back() {
        if (this.active-- < 0) this.active = 0;
      },
      /*demoClick(e) {
                this.lat = e.latlng.lat
                this.lng = e.latlng.lng
                console.log(this.lat)
                console.log(this.lng)
      },*/
      createSerie() {
        let rt = this.$router;
        this.axios.post('http://localhost:8081/office/series', {
            ville: this.name,
            dist: 3,
            lat: this.lat,
            lng: this.lng
        })
        .then((response) => {
            console.log(response.data);
            this.$router.push("/addPhotos/"+response.data.id)
        })
        .catch(function (error) {
            console.log(error);
        });
      },
    }
}
</script>
<style scoped>
    .vue2leaflet-map{
        height:500px;        
        width:50%;
    }

    .el-input {
        width: 50%
    }
</style>
