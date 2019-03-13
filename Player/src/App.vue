<template>
<html>
    <head><meta charset="UTF-8"> 
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>

    </head>
<body>
    <div class="header">
        GeoQuizz
    </div>
    <div class="flex">
            <div id="img">
                <img src="1.jpg">
            </div>
            <l-map @click="mapClick"  :zoom="zoom" :center="center">
              <l-tile-layer
                ref="map"
                :key="tileProvider.name"
                :name="tileProvider.name"
                :url="tileProvider.url"
                :attribution="tileProvider.attribution"
                layer-type="base"/>
            </l-map>
    </div>
    <div class="txt" id="txt">
            Bienvenue sur GeoQuizz ! Le but du jeu est de retrouver l'endroit ou a été pris la photo<br>
            Essaye donc !         
    </div>
    <div class="txt">
            <button>Nouvelle partie</button>        
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

          tileProvider:
            {
              name: 'OpenStreetMap',
              visible: true,
              attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
              url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
            }
          ,
          found : false,
          compteur:0,
          zoom:13,
          center: L.latLng(48.69333, 6.18324),
          map: null
    
      }},
      methods:{
          mapClick: function(e) {

              this.compteur ++

              console.log(this.$refs.map.mapObject);

              let lat = e.latlng.lat
              let lng = e.latlng.lng

              let pointA = L.latLng(lat, lng);
              let pointB = L.latLng(48.6829, 6.16106);

              let distance = pointA.distanceTo(pointB);
              if (distance < 100 && !this.found){
                  document.getElementById("txt").innerHTML = "Bravo ! Tu as trouvé l'endroit exact en seulement " + this.compteur + " fois!"

              this.found = true;
              }else if (!this.found){
                  document.getElementById("txt").innerHTML = "Vous avez cliqué à " + Math.round(distance) + " mètre de l'endroit ou la photo a été prise, Heuresement, c'est une démo : vous pouvez réessayer autant que vous le vouler !"
              }

            }
        },
        mounted(){

        }
    }
    </script>
    <style>
      body{
        margin : 0;
      }
    </style>
    
<style scoped>
        .vue2leaflet-map{
          height:400px;        }

        .flex{
            display:flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            background-color: CCD4B4;
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

        .header{
            background-color: rgba(200,200,200);
            padding-top:0.8em;
            padding-bottom: 0.8em;
            padding-left : 3em;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            font-size: 1.5em;
        }

        .txt{
            padding: 1em;
            text-align:center;
            font-size: 1.5em;
        }

        button{
            background-color: rgba(150,150,150);
            color: white;
            border-style: none;
            padding:1em;
            font-size: 1em;
            border-radius: 30px;
        }
</style>