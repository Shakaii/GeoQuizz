<template>
    <html>
        <head>
            <title>GeoQuizz</title>
            <meta charset="UTF-8"> 
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css" integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA==" crossorigin=""/>
        </head>
        <el-header>
            <div class="header-left title" v-if="state != 'inGame'">
                <img class="icon" src="GeoQuizZWhite.png">
            </div>
            <div class="header-left" v-if="state=='inGame'">
                <b>{{serieName}}</b> : {{currentPictureIndex}} / {{photos.length}}<br>
            </div>
            <div class="header-right" v-if="state == 'inGame' || state =='chosingSerie' || state == 'score'">
                <el-button type="primary" plain v-if="state == 'inGame'" @click="save">Sauvegarder</el-button>
                <el-button type="primary" plain  @click="backToDemo" >Quitter</el-button>
            </div>
        </el-header>
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
                <el-card v-bind:class="{selected: (button1 == 'always')}" v-bind:shadow="button1">
                    <el-button type="primary"  plain @click="difficulty = 0; button1= 'always'; button2= 'hover'; button3='hover'" >Parisien</el-button>
                    <br><br>
                    <span class="green">60 secondes pour trouver l'endroit.</span><hr><span class="green">Zone à trouver plus grande.</span>
                </el-card>
                <el-card v-bind:class="{selected: (button2 == 'always')}" v-bind:shadow="button2">
                    <el-button type="primary"  plain @click="difficulty = 1; button2= 'always'; button1= 'hover'; button3='hover'" >Touriste</el-button>
                    <br><br>
                    <span class="orange">40 secondes pour trouver l'endroit.</span><hr><span class="green">Score multiplié par 2.</span>
                </el-card>
                <el-card v-bind:class="{selected: (button3 == 'always')}" v-bind:shadow="button3">
                    <el-button type="primary" plain @click="difficulty = 2; button3= 'always'; button2= 'hover'; button1='hover'" >Guide du routard</el-button>
                    <br><br>
                    <span class="red">20 secondes pour trouver l'endroit.</span><hr><span class="red">Zone à trouver plus petite.</span><hr>  <span class="green">Score multiplié par 3.</span>
                </el-card>
            </div>

            <div class="center" v-if="state == 'chosingSerie'">
              <el-input class="nameInput" v-on:keyup.enter="startGame"  placeholder="Entrez votre pseudo" prefix-icon="el-icon-edit" v-model="username"></el-input>
              <el-button type="primary" plain @click="startGame" :disabled="!username || !selectedSerie" >Commencer la partie</el-button> 
            </div>

            <div class="center" v-if="state == 'demo'">
                <el-button type="primary" plain  @click="showSeries" >Choisir un niveau</el-button> 
                <el-button type="primary" v-if="gameSaved" plain  @click="getSave" >Reprendre la partie sauvegardée</el-button>
                <el-button type="primary" plain  @click="showScore" >Voir les scores</el-button>  
            </div>

            <div v-if="state == 'inGame'" class="gameInterface">
                <div class="score">
                    Votre Score : {{score}} / {{maxCurrentScore}}<br>
                    High-Score de la série : {{bestScore}} / {{maxScore}}
                </div>
                <div class="timerAndText">
                    <el-progress v-if="!resultTexte" class="progressCircle" type="circle" :percentage="(time * 100) / maxTime" :color="color" status="text">{{time}}<br>secondes<br>restantes</el-progress>
                    <el-progress v-if="!resultTexte" class="progressBar" :percentage="(time * 100) / maxTime" :color="color" status="text" >{{time}} secondes restantes</el-progress>
                    {{resultTexte}} <br> {{zoneName}}
                </div>
                <div class="actionButton">
                    <el-button type="primary" v-if="currentPictureIndex == photos.length"  :disabled="!resultTexte" plain @click="nextPicture">Fin</el-button>
                    <el-button type="primary" v-if="currentPictureIndex < photos.length"  :disabled="!resultTexte" plain @click="nextPicture">Photo suivante</el-button><br><br>
                </div>
            </div>

            <Scores v-if="state == 'score'"></Scores>

        </body>
    </html>
</template>

<script>
    import * as Vue2Leaflet from 'vue2-leaflet';
    import { LMap, LTileLayer, LMarker, LCircle} from 'vue2-leaflet';
    import Series from './components/Series.vue';
    import Scores from './components/Scores.vue';
    import Spinner from './components/Spinner.vue';
    
    export default {
        name:"Geoquizz",
        components: {
            LMap,
            LTileLayer,
            LMarker,
            LCircle,
            Series,
            Scores,
            Spinner
        },

    data(){
        return{
            state: 'demo',
            tileProvider:{
                name: 'OpenStreetMap',
                visible: true,
                attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a>',
                url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
            },
            found : false,
            compteur:0,
            zoom:13,
            center: [48.69333, 6.18324],
            circles : [] ,
            texte: "Bienvenue sur GeoQuizz ! Le but du jeu est de retrouver l'endroit où a été pris la photo.",
            texte2: "Clique sur la carte pour marquer un endroit !",
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
            time: 60,
            maxTime: 60,
            interval: "",
            color: "#67C23A",
            gameSaved: false,
            bestScore: 0,
            gameId: 0,
            zoneName: ""
        }
    },
    mounted(){
        //register the save
        if(localStorage.getItem('save')){
            this.gameSaved = true;
        }
    },

    methods:{

        //deals with clicks on the map when the app is in demo state
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
                this.texte = "Vous avez cliqué à " + (Math.round(distance) - 100) + " mètres de la zone où la photo a été prise.";
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

        //deal with the on click event of the map when the game is started
        inGameClick: function(e){

            let lat = e.latlng.lat
            let lng = e.latlng.lng
            let pointA = L.latLng(lat, lng);
            let pointB = L.latLng(this.pictureCoordonates[0], this.pictureCoordonates[1]);
            let distance = pointA.distanceTo(pointB);

            //affichage des cercles représentants les trois zones
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

                

                if (this.difficulty == 1){
                  scoreGain = scoreGain * 2;
                }
                else if (this.difficulty == 2){
                  scoreGain = scoreGain * 3;
                }

                this.score += scoreGain;

                this.resultTexte = "Bien joué ! Vous avez cliqué à " + Math.round(distance) + " mètres de l'endroit où la photo a été prise. Vous gagnez " + scoreGain + ' points.'
            }
            else{
                //affichage des cercles représentants les trois zones
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

                this.resultTexte = "Pas de chance ! Vous avez cliqué à " + Math.round(distance - 300) + " mètres de la zone où la photo a été prise."
            }
              this.zoneName = "La photo a été prise à " + this.photos[this.currentPictureIndex - 1].dsc
              clearInterval(this.interval);
        },

        //change the state of the game for chosing Serie and difficulty
        showSeries: function(){

            this.state = "chosingSerie";
            this.texte = "Choisissez un niveau et une difficultée pour commencer!";;
            this.texte2 = ""   
        },

        //change the state of the game for displaying scores
        showScore: function(){

            this.state = "score";
            this.texte = "";
            this.texte2 = ""   
        },

        //request api to start a game and retrieve the pictures
        startGame: function(){

            let $this= this;
            this.state = "inGame";
            this.texte = "";
            this.texte2 = "";

            this.axios.post('http://localhost:8083/game/new?serie=' + $this.selectedSerie, 
            {
                joueur: $this.username,
                difficulty: $this.difficulty,
                status: 1
            })
            .then((response) => {

                $this.token = response.data.token;
                $this.gameId = response.data.id;
                $this.axios.get('http://localhost:8083/game/' + response.data.id + '?token=' + $this.token )
                .then((gameResponse) => {

                    this.axios.get('http://localhost:8083/player/serie/' + $this.selectedSerie).then((serieResponse) => {

                        $this.photos = gameResponse.data.photos;
                        $this.maxScore = $this.photos.length * 20;
                        $this.distanceD = serieResponse.data.dist;

                        //there's a bug with zoom, sometimes api won't send out zoom so we double check here
                        if (serieResponse.data.zoom){
                            $this.zoom = serieResponse.data.zoom;
                        }
                        else $this.zoom = 13;
                        
                        $this.center = [serieResponse.data.x,serieResponse.data.y];
                        $this.center[0] = serieResponse.data.x
                        $this.center[1] = serieResponse.data.y
                        $this.serieName = serieResponse.data.ville;
                        $this.score = 0;
                        $this.currentPictureIndex = 0;

                        //more score if more difficulty            
                        if ($this.difficulty == 1){
                            $this.maxScore = $this.maxScore * 2;
                        }
                        else if ($this.difficulty == 2){
                            $this.maxScore = $this.maxScore * 3
                        }

                        //larger zone for difficulty 0 and smaller for difficulty 2
                        if ($this.difficulty == 0){
                            $this.distanceD = $this.distanceD * 1.1;
                        }
                        else if ($this.difficulty == 2){
                            $this.distanceD = $this.distanceD * 0.9
                        }
                        $this.getMaxScore();
                        $this.nextPicture();
                    }).catch((err) => {
                        $this.backToDemo();
                        $this.$message.error('Erreur lors de la récupération de la série. Réessayez plus tard : ' + err);
                    });
                    
                }).catch((err) => {
                    $this.backToDemo();
                    $this.$message.error('Erreur lors de la récupération des données de la partie. Réessayez plus tard : ' + err);
                });
            }).catch((err) => {
                    $this.backToDemo();
                    $this.$message.error('Erreur lors de la création de la partie. Réessayez plus tard :  ' + err);
            });
        },

        //switch to the next picture and set the interval for time gestion
        //if it's the last picture, prompt a message to save the score or not
        nextPicture: function(){

            if (this.photos[this.currentPictureIndex]){      
                this.circles = [];
                this.currentPicture = this.photos[this.currentPictureIndex].url;
                this.pictureCoordonates = [this.photos[this.currentPictureIndex].x,this.photos[this.currentPictureIndex].y];
                this.maxCurrentScore = (this.currentPictureIndex + 1) * 20;
                if (this.difficulty == 1){
                    this.maxCurrentScore = this.maxCurrentScore * 2
                }
                else if (this.difficulty == 2){
                    this.maxCurrentScore = this.maxCurrentScore * 3
                }
                this.currentPictureIndex ++;
                this.zoneName = "";
                
                this.multiplier = 4;
                this.resultTexte = "";
                this.color = "#67C23A";

                if (this.difficulty == 0){
                    this.maxTime = 60;
                    this.time = 60;
                }
                else if (this.difficulty == 1){
                    this.maxTime = 40;
                    this.time = 40;
                }
                else{
                    this.maxTime = 20;
                    this.time = 20;
                }

                let $this = this;
                clearInterval(this.interval);
                this.interval = setInterval(function(){
                    if ($this.time > 0){
                        $this.time --;
                    }
                    if ($this.time == (Math.round(2/3 * $this.maxTime))){
                      $this.multiplier = 2;
                      $this.color = "orange"
                    }
                    else if ($this.time == (Math.round(1/3 * $this.maxTime))){
                      $this.multiplier = 1;
                      $this.color = "#F56C6C"
                    }
                    else if ($this.time == 0){
                      $this.multiplier = 0;
                    }
                }, 1000);
            }
            else{
                this.$confirm("Série terminée avec un score de " + this.score + " sur " + this.maxScore + ". Voulez vous enregistrer votre score ?" , 'Pas mal !', {
                    confirmButtonText: 'Enregistrer mon score',
                    cancelButtonText: 'Quitter',
                    type: 'warning'
                }).then(() => {
                    this.endGameSignal(true);
                }).catch(() => {
                    this.endGameSignal(false);
                });
            }
        },

        //receive the emitted serie id
        selectSerieId: function(id){

          this.selectedSerie = id;
        },

        //save the data needed to resume a game later
        save: function(){

            let save = JSON.stringify({
                state: this.state,
                tileProvider: this.tileProvider,
                circles : this.circles ,
                texte: "",
                texte2: "",
                difficulty: this.difficulty,
                selectedSerie: this.selectedSerie,
                username: this.username,
                photos: this.photos,
                currentPicture: this.currentPicture,
                currentPictureIndex: this.currentPictureIndex,
                pictureCoordonates : this.pictureCoordonates,
                score: this.score,
                maxScore: this.maxScore,
                maxCurrentScore: this.maxCurrentScore,
                token: this.token,
                serieName: this.serieName,
                resultTexte: this.resultTexte,
                multiplier: this.multiplier,
                time: this.time,
                maxTime: this.maxTime,
                interval: this.interval,
                color: this.color,
                gameSaved: this.gameSaved,
                distanceD: this.distanceD,
                zoom: this.zoom,
                center: this.center
            });

            localStorage.setItem('save', save);
            this.gameSaved = true;
            this.$message({
                message: 'La partie a été sauvegardée.',
                type: 'success'
            });
        },

        //get the local stored data and resume the interval if its needed 
        getSave: function(){

            let $this = this;
            let save = localStorage.getItem('save');
            let dataArray = Object.entries(JSON.parse(save))
            for(let dataArrayIndex = 0; dataArrayIndex < dataArray.length;dataArrayIndex ++){
                $this[dataArray[dataArrayIndex][0]] = dataArray[dataArrayIndex][1];
            }
            if(this.time > 0 && !this.resultTexte){
                this.interval = setInterval(function(){
                    if ($this.time > 0){
                        $this.time --;
                    }
                    if ($this.time == 40){
                        $this.multiplier = 2;
                        $this.color = "orange"
                    }
                    else if ($this.time == 20){
                        $this.multiplier = 1;
                        $this.color = "#F56C6C"
                    }
                    else if ($this.time == 0){
                        $this.multiplier = 0;
                    }
                }, 1000);
            }
        },

        //send the endgame signal to the api and remove the saved game
        // in : true if sending score else false
        endGameSignal: function(saveScore){  

            localStorage.removeItem("save");
            let $this = this;
            this.axios.post('http://localhost:8083/game/result/'+ $this.gameId,
            {
                score: $this.score,
                saveScore: saveScore,
                token: $this.token
            })
            .then((response) => {
                this.$message({
                    message: 'Le score a été sauvegardé avec succès.',
                    type: 'success'
                });
            })
            .catch((err) => {
                $this.$message.error("Erreur lors de l'envoi du score au serveur. Réessayez plus tard :  " + err);
            });
            this.backToDemo();
        },

        //set the state to demo and reset demo
        backToDemo: function(){
            this.state = "demo";
            this.zoom = 13;
            this.center = [48.69333, 6.18324]; 
            this.found = false;
            this.compteur=0;
            this.circles = []; 
            this.texte= "Bienvenue sur GeoQuizz ! Le but du jeu est de retrouver l'endroit où a été pris la photo.";
            this.texte2= "Clique sur la carte pour marquer un endroit !";
            this.difficulty= 0;
            this.resultTexte= "";
            this.currentPicture = "1.jpg"
            clearInterval(this.interval);
        },

        //get the max achieved score for the current series and difficulty
        getMaxScore: function(){
            let $this = this;
            this.axios.get('http://localhost:8083/game/score/')
            .then((response) => {
                let scores = response.data
                let bestScore = 0;
                for(let scoreIndex = 0; scoreIndex < scores.length; scoreIndex ++){
                    let score = scores[scoreIndex];
                    if ( score.ville == $this.serieName && score.score > bestScore && score.difficulty == $this.difficulty){
                        bestScore = score.score;
                    }
                }
                $this.bestScore = bestScore;
            })
            .catch((err) => {
                $this.$message.error("Erreur lors de la récupération du meilleur score : " + err);
            });
        },

        //process the data for display
        processScore: function(){

            let $this = this;
            for(let scoreIndex = 0; scoreIndex < $this.scores.length; scoreIndex ++){
                let score = $this.scores[scoreIndex];
                if ( score.ville && score.joueur && score.difficulty >= 0 && score.score >= 0 /* && score.saveScore*/){
                    $this.tableData.push(score);
                }
            }
        }
    }
}
</script>

<style>

    html{
        width: 100vw;
    }

    body{
        margin : 0;
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
        background-color: rgba(0,0,0,0.01);
        min-height: 100%;

        width: 100vw;
    }

    .el-row{
        box-shadow: 10px 10px 10px -10px #000000;
    }

</style>
    
<style scoped>

    .title{
        font-weight: 100;
        line-height: 20px;
        font-size: 20px;
        text-shadow: 1px 1px 1px white
    }

    .icon{
        height:2.70em;
        
    }

    .nameInput{
        max-width: 30%;
        margin-right: 1em;
    }

    .vue2leaflet-map{
        height:400px;        
        width:60%;
        box-shadow: 10px 10px 10px -10px #000000;
        border-bottom-left-radius: 20px;
    }

    .mapAndPictureContainer{
        display:flex;
        flex-direction: row;
        justify-content: center;
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
        box-shadow: 10px 10px 10px -10px #000000;
        border-bottom-right-radius: 5px;
        
    }

    #mapid{
        height: 400px;
        width: 50%;        
    }

    .center{
        padding: 1em 1em 0em 1em;
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

    .el-header{
        display:flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        background-color: rgb(0,140,140);
    }

    .header-left{
        width: 40%;
        text-align: center;
    }

    .header-right{
        width:60%;
        text-align: right;
    }

    .progressBar{
        display:none;
    }

    .green{
        font-weight: bold;
        color: #67C23A;
    }

    .red{
        font-weight: bold;
        color: #F56C6C;
    }

    .orange{
        font-weight: bold;
        color: #E6A23C;
    }

    hr{
      border-color: white;
    }

    .selected{
        background-color: #e6f2f2;
    }

    @media screen and (max-width: 1000px) {

       #texte{
         font-size: 0.8em;
       }

       .timerAndText{
            font-size: 0.8em;
        }

        .icon{
          height:30px;
        }

        .difficultyContainer{
            flex-direction: column;
        }

        .el-card{
          max-width: 100%;
        }

        .nameInput{
            max-width: 100%;
            margin-bottom: 1em;
        }

        .mapAndPictureContainer{
            flex-direction:column;
        }

        #img{
            width:100%;
        }

        .vue2leaflet-map{
            width: 100%;
            margin:0;
            padding: 0;
            height:200px;
            border-bottom-left-radius: 0px;
        }

        .gameInterface{
            flex-direction: column-reverse;
        }
    
        .el-header{
            flex-direction:row;
            justify-content: space-between;
            height: 100px!important;
        }

        .header-left{
            padding-top: 1em;
            width:100%;
        }

        .header-right{
            width: 100%;
        }

        .progressCircle{
            display:none;
        }

        .progressBar{
            display:initial;
            width: 100%;
            
            padding-bottom:0.5em;
        }

        .timerAndText{
            width: 100%;
            order: 1;
        }

        .actionButton{
            padding-top:1em;
        }

        #img img{
          border-bottom-right-radius: 0px;
        }
    }




</style>