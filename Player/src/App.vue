<template>
    <html>
        <head>
            <title>GeoQuizz</title>
            <meta charset="UTF-8"> 
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>
        </head>
        <body>
            <div class="mapAndPictureContainer" v-if="state == 'demo' || state == 'inGame'">
                <div id="img">
                    <img v-bind:src="currentPicture">
                </div>
                <l-map @click="mapClick"  :zoom="zoom" :center="center">
                    <l-tile-layer ref="map" :key="tileProvider.name" :name="tileProvider.name" :url="tileProvider.url" :attribution="tileProvider.attribution" layer-type="base"/>
                    <l-circle v-for="circle in circles" :lat-lng="circle.latLng" :radius="circle.radius" :opacity="circle.opacity" :color="circle.color" :fillColor="circle.fillColor" />
                </l-map>
            </div>
            
            <Series v-on:serieId="selectSerieId" class="h" v-if="state== 'chosingSerie'"></Series>

            <div class="center" id="texte" v-if="texte">
                {{texte}} <br v-if="texte2"> {{texte2}}
            </div>

            <div class="difficultyContainer" v-if="state == 'chosingSerie'">
                <el-card v-bind:shadow="button1">
                    <el-button type="primary" plain @click="difficulty = 0; button1= 'always'; button2= 'hover'; button3='hover'" >Parisien</el-button>
                    <br><br>
                    Vous partez en vacance tous les ans au même endroit.
                </el-card>
                <el-card v-bind:shadow="button2">
                    <el-button type="primary" plain @click="difficulty = 1; button2= 'always'; button1= 'hover'; button3='hover'" >Touriste</el-button>
                    <br><br>
                    Vous vous arrêtez à chaques station service pour admirer la carte de la région.
                </el-card>
                <el-card v-bind:shadow="button3">
                    <el-button type="primary" plain @click="difficulty = 2; button3= 'always'; button2= 'hover'; button1='hover'" >Guide du routard</el-button>
                    <br><br>
                    Vous avez laissé des avis sur la page google de tous les restaurants.
                </el-card>
            </div>
            <div class="center" v-if="state == 'chosingSerie'">
              <el-input class="nameInput" placeholder="Entrez votre pseudo" prefix-icon="el-icon-edit" v-model="username"></el-input>
              <el-button type="primary" plain @click="startGame" :disabled="!username || !selectedSerie" >Commencer la série</el-button> 
            </div>
            <div class="center" v-if="state == 'demo'">
                <el-button type="primary" plain  @click="showSeries" >Choisir une série</el-button> 
            </div>

            <div v-if="state == 'inGame'" class="gameInterface">
                
                <div class="score">
                    Votre Score : {{score}} / {{maxCurrentScore}}<br>
                    High-Score de la série : 0 / {{maxScore}}
                </div>
                <div class="timerAndText">
                    <el-progress v-if="!resultTexte" type="circle" :percentage="(time * 100) / 20" :color="color" status="text">{{time}}</el-progress>
                    {{resultTexte}}
                </div>

                <!-- {{serieName}} ( zone {{currentPictureIndex}} / {{photos.length}} )<br> -->
                <el-button type="primary" plain @click="nextPicture">Zone suivante</el-button>
            </div>

        </body>
    </html>
</template>

<script>
    import * as Vue2Leaflet from 'vue2-leaflet';
    import { LMap, LTileLayer, LMarker, LCircle} from 'vue2-leaflet';
    import Series from './components/Series.vue';
    
    export default {
        name:"Geoquizz",
        components: {
            LMap,
            LTileLayer,
            LMarker,
            LCircle,
            Series
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
                texte2: "Essaye donc!",
                difficulty: 0,
                button1: 'always',
                button2: 'hover',
                button3: 'hover',
                selectedSerie: "",
                username: "",
                photos: [],
                currentPicture: "1.jpg",
                currentPictureIndex: 0,
                pictureCoordonates : [],
                score: 0,
                maxScore: 0,
                maxCurrentScore: 0,
                token: "",
                serieName: "",
                resultTexte: "",
                multiplier: 0,
                time: 0,
                interval: "",
                color: "#67C23A"
            }
        },

        methods:{

            demoClick: function(e) {

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

          //run the appropriate function depending of the game state
          mapClick: function(e){
              if (this.state == 'demo'){
                  this.demoClick(e);
              }
              else if (this.state == 'inGame' && !this.resultTexte){
                  this.inGameClick(e);
              }
          },

          inGameClick: function(e){

              let lat = e.latlng.lat
              let lng = e.latlng.lng
              let pointA = L.latLng(lat, lng);
              let pointB = L.latLng(this.pictureCoordonates[0], this.pictureCoordonates[1]);
              let distance = pointA.distanceTo(pointB);

              if (distance < (this.distanceD * 3)){
                  this.circles.push( {
                        radius: this.distanceD,
                        latLng: this.pictureCoordonates,
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                  });
                  this.circles.push( {
                        radius: this.distanceD * 2,
                        latLng: this.pictureCoordonates,
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                  });
                  this.circles.push( {
                        radius: this.distanceD * 3,
                        latLng: this.pictureCoordonates,
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                  });

                  let scoreGain = 0;

                  if (distance < this.distanceD) scoreGain = 5 * this.multiplier;
                  else if (distance < (this.distanceD * 2)) scoreGain = 3 * this.multiplier;
                  else if (distance < (this.distanceD * 3)) scoreGain = 1 * this.multiplier;

                  this.score += scoreGain;

                  this.resultTexte = "Bien joué ! Vous avez cliqué à " + Math.round(distance) + " mètres de l'endroit où la photo a été prise. Vous gagnez " + scoreGain + ' points.'
              }
              else{
                  this.circles.push( {
                        radius: this.distanceD,
                        latLng: this.pictureCoordonates,
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                  });
                  this.circles.push( {
                        radius: this.distanceD * 2,
                        latLng: this.pictureCoordonates,
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                  });
                  this.circles.push( {
                        radius: this.distanceD * 3,
                        latLng: this.pictureCoordonates,
                        color: "#5bc0de",
                        fillColor: "#5bc0de",
                        opacity: 0.8
                  });

                  this.circles.push( {
                        radius: 50,
                        latLng: [lat, lng],
                        color: "red",
                        fillColor: "red",
                        opacity: 0.4
                  });

                  this.resultTexte = "Pas de chance ! Vous avez cliqué à " + Math.round(distance - 100) + " mètres de la zone où la photo a été prise."
              }

               clearInterval(this.interval);

              

          },

          showSeries: function(){
              this.state = "chosingSerie"
              this.texte = "Choisissez une série et une difficultée pour commencer!"
              this.texte2 = ""   
          },

          //request api to start a game and retrieve the pictures
          startGame: function(){

              let $this= this;
              this.state = "inGame";
              this.texte = "";
              this.texte2 = "";

              this.axios.post('http://localhost:8081/game/new?id=' + $this.selectedSerie, 
              {
                  username: $this.username,
                  difficulty: $this.difficulty
              })
              .then((response) => {
                  $this.token = response.data._embedded.token;
                  $this.axios.get('http://localhost:8081/game/' + serieId + '?token=' + $this.token )
                  .then((response) => {
                      $this.photos = response.data._embedded.photos;
                      this.maxScore = this.photos.length * 20;
                      this.distanceD = response.data._embedded.distance
                      $this.nextPicture();
                  });
              });

              this.photos = [{ src: "1.jpg", pos: [48.6829,6.16106] },{ src: "2.jpg", pos: [48.68559,6.16104] },{ src: "3.jpg", pos: [48.68891,6.17493] },{ src: "4.jpg", pos: [48.69339,6.18422] },{ src: "5.jpg", pos: [48.66634,6.16687] }]
              this.serieName = "Nancy"
              this.maxScore = this.photos.length * 20;
              this.distanceD = 100;
              this.nextPicture();
          },

          //kinda recursive
          nextPicture: function(){
              if (this.photos[this.currentPictureIndex]){
                  this.circles = [];
                  this.currentPicture = this.photos[this.currentPictureIndex].src;
                  this.pictureCoordonates = this.photos[this.currentPictureIndex].pos;
                  this.maxCurrentScore = (this.currentPictureIndex + 1) * 20;
                  this.currentPictureIndex ++;
                  this.time = 20;
                  this.multiplier = 4;
                  this.resultTexte = "";
                  this.color = "#67C23A"
                  let $this = this;
                  clearInterval(this.interval);
                  this.interval = setInterval(function(){
                      if ($this.time > 0){
                          $this.time --;
                      }
                      if ($this.time == 15){
                        $this.multiplier = 2;
                        $this.color = "orange"
                      }
                      else if ($this.time == 10){
                        $this.multiplier = 1;
                        $this.color = "#F56C6C"
                      }
                      else if ($this.time == 0){
                        $this.multiplier = 0;
                      }

                  }, 1000);
              }else{
                  this.endGameSignal();
                  alert("Série terminée avec un score de " + this.score + " sur " + this.maxScore);
              }
          },

          selectSerieId(id){
            this.selectedSerie = id;
          },

          endGameSignal(){
              let $this = this;
              this.axios.put('http://localhost:8081/game/'+ $this.token +'/result/',
              {
                  score: $this.score,
              })
              .then((response) => {
                console.log("partie sauvegardée")
              });
              });
          }

        }
        
    }
</script>

<style>

    body{
        margin : 0;
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    }
</style>
    
<style scoped>

  .nameInput{
    max-width: 30%;
    margin-right: 1em;
  }

    .vue2leaflet-map{
        height:400px;        
        width:60%;
    }

    .mapAndPictureContainer{
        display:flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: #CCD4B4;
        width: 100vw;
    }

    .difficultyContainer{
        display:flex;
        flex-direction: row;
        justify-content: center;
        align-items : center;
        text-align: center;
    }

    .el-card{
      max-width: 20vw;
      margin: 1em;
    }

    #img{
        width: 40%;
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

    .gameInterface{
      margin-top: 1em;
      text-align: center;
      display:flex;
      flex-direction: row;
      align-items: center;
      justify-content: space-evenly;

    }


</style>