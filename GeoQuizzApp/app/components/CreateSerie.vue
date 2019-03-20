<template>
<FlexboxLayout>
    <Label text="Création de série"/>
    <StackLayout v-show="active == 0" >
        <Label text="Nom de la série"/>
        <TextField hint="Nom de la série (ville)" autocorrect="false" v-model="name" class="input input-border"></TextField> 
        <Button text="Prochaine étape" icon="el-icon-arrow-right" @tap="next"/>
    </StackLayout>
    <StackLayout v-show="active == 1">
        <Label text="Géolocalisation de la série"/>
        <Button text="Prochaine étape" icon="el-icon-arrow-right" @tap="next"/>
        <Button text="Etape précédente" icon="el-icon-arrow-left" @tap="back"/>
    </StackLayout>
    <StackLayout v-show="active == 2">
        <Label text="Ajout des photos"/>
        <Button text="Terminer" @tap="createSerie"/>
        <Button text="Etape précédente" icon="el-icon-arrow-left" @tap="back"/>
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
