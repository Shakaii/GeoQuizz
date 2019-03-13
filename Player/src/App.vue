<template>
    <html>
        <head><meta charset="UTF-8"> 
                <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>

        </head>
        <body>
            <div class="mapAndPictureContainer" v-if="state == 'demo' || state == 'inGame'">
                <div id="img">
                    <img src="1.jpg">
                </div>
                <l-map @click="mapClick"  :zoom="zoom" :center="center">
                    <l-tile-layer ref="map" :key="tileProvider.name" :name="tileProvider.name" :url="tileProvider.url" :attribution="tileProvider.attribution" layer-type="base"/>
                    <l-circle v-for="circle in circles" :lat-lng="circle.latLng" :radius="circle.radius" :opacity="circle.opacity" :color="circle.color" :fillColor="circle.fillColor" />
                </l-map>
            </div>
            
            <div class="center" id="texte">
                {{texte}} <br> {{texte2}}
            </div>
            <div class="center">
                <el-button @click="showSeries" >Commencer une série</el-button> 
            </div>
        </body>
    </html>
</template>

<script>
    import * as Vue2Leaflet from 'vue2-leaflet' 
    import { LMap, LTileLayer, LMarker, LCircle} from 'vue2-leaflet';
    
    export default {
        name:"GeoQuizz",
        components: {
            LMap,
            LTileLayer,
            LMarker,
            LCircle
        },

        data(){
            return{
                state: 'demo',
                tileProvider:{
                  name: 'OpenStreetMap',
                  visible: true,
                  attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
                  url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                },
                found : false,
                compteur:0,
                zoom:13,
                center: L.latLng(48.69333, 6.18324),
                map: null,
                circles : [] ,
                texte: "Bienvenue sur GeoQuizz ! Le but du jeu est de retrouver l'endroit où a été pris la photo.",
                texte2: "Essaye donc!" 
            }
        },

        methods:{

            mapClick: function(e) {

                this.compteur ++

                let lat = e.latlng.lat
                let lng = e.latlng.lng
                let pointA = L.latLng(lat, lng);
                let pointB = L.latLng(48.6829, 6.16106);
                let distance = pointA.distanceTo(pointB);

                if (distance < 100 && !this.found){
                    this.texte = "Bravo !";
                    this.texte2 = "Tu as trouvé l'endroit exact en seulement " + this.compteur + " fois!";
                    this.circles.push( {
                        radius: 100,
                        latLng: [48.6829, 6.16106],
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                    });

                    this.found = true;
                }
                else if (!this.found){
                    this.circles.push( {
                        radius: 50,
                        latLng: [lat, lng],
                        color: "red",
                        fillColor: "red",
                        opacity: 0.4
                    });
                    this.texte = "Vous avez cliqué à " + Math.round(distance) + " mètres de l'endroit où la photo a été prise.";
                    this.texte2 = "Heuresement, c'est une démo : vous pouvez réessayer autant que vous le voulez!";
                }

          },



        },
        
    }
</script>

<style>
    body{
        margin : 0;
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    }
</style>
    
<style scoped>
    .vue2leaflet-map{
        height:400px;        
        width:50%;
    }

    .mapAndPictureContainer{
        display:flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: #CCD4B4;
        width: 100vw;
    }

    #img{
        width: 50%;
    }

    #img img{
        width: 100%;
        height: auto;
    }

    #mapid{
        height: 400px;
        width: 50%;        
    }

     .center{
        padding: 1em;
        text-align:center;
        font-size: 1.5em;
    }
</style>