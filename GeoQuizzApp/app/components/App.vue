<template>
    <Page>
        <ActionBar>
            <GridLayout width="100%" columns="auto, *">
                <Label v-show="current != 0" text="MENU" @tap="$refs.drawer.nativeView.showDrawer()" col="0" />
                <Label class="title" text="GeoQuizz Mobile" col="1" />
            </GridLayout>
        </ActionBar>

        <RadSideDrawer ref="drawer">


            <StackLayout v-show="current != 0" ~drawerContent backgroundColor="#ffffff">
                <ScrollView>
                    <StackLayout>
                    <Label class="drawer-header" @tap="$refs.drawer.nativeView.closeDrawer()" text="Séries"/>
                 
                    <Label class="drawer-item" @tap="current = 2;$refs.drawer.nativeView.closeDrawer()" text='Créer une nouvelle série'/>

                    <Label v-for='serie in seriesInfo' class="drawer-item" @tap="current = 1;searchSerie(serie._links.self.href);$refs.drawer.nativeView.closeDrawer()" :text='serie.ville'/>
                    
                    </StackLayout>
                </ScrollView>

            </StackLayout>
            <GridLayout ~mainContent columns="*" rows="*">
                <StackLayout v-show="current == 0">
                    <TextField hint="Utilisateur" autocorrect="false" v-model="username"></TextField>
                    <TextField secure="true" hint="Mot de passe" autocorrect="false" v-model="pass"></TextField>
                    <Button @tap="auth" text="authentification" />
                </StackLayout>
                <Label v-show="current == 2" class="message" :text="msg" col="0" row="0" />
                <StackLayout v-show="current == 1">
                    <label class="message" :text="serieInfo" col="0" row="0" />



                    <Button text="Prendre une photo" @tap="takePicture" />
                 
                    <ScrollView orientation="horizontal">
                        <WrapLayout>
                            <Image v-for="img in images" :src="img.src" width="150" height="150" />
                        </WrapLayout>

                    </ScrollView>
                    <Button v-show="images.length >=1" @tap="upPhoto" text="Envoyer les photos prises" />
                    <ScrollView orientation="horizontal">
                        <WrapLayout>
                            <Image v-for="ima in seriePhoto" :src="ima.url" width="150" height="150" />
                        </WrapLayout>
                    </ScrollView>
                </StackLayout>
            
                <create-serie :token=this.token v-show="current == 2"/>
          
            </GridLayout>

        </RadSideDrawer>

        
    </Page>
</template>

<script>
import CreateSerie from './CreateSerie.vue'
    import * as camera from "nativescript-camera";
    const geolocation = require("nativescript-geolocation");
    const imageSourceModule = require("tns-core-modules/image-source");
    import {
        Image
    } from "tns-core-modules/ui/image";
    import {
        error
    } from 'tns-core-modules/trace/trace';

  export default {
      name: 'App',
      components: {
          CreateSerie,
         
      },


        

        data() {
            return {
                msg: 'Bienvenue sur GeoQuizz Mobile!',
                current: "",
                seriesInfo: "",
                serieInfo: "",
                seriePhoto: "",
                images: [],
                geocal: 0,
                serieId: "",
                photoName: "",
                lat: [],
                lon:[],
                username: "",
                pass: "",
                token:"",
            }
        },
        methods: {
            searchSeries() {

                this.token = this.getToken();
                this.token = this.token.replace('token=', '')
      
                this.axios
                    .get("https://cbc0cb8c.ngrok.io/office/series", {
                        headers: {
                            'Authorization': this.token
                        }
                    })
                    .then(response => (this.seriesInfo = response.data._embedded.series))
                    .catch(error => {
                        console.log(error);
                        this.errored = true;
                    });
            },
            searchSerie(link) {
                this.axios
                    .get(link, {
                        headers: {
                            'Authorization': this.token
                        }
                    })
                    .then(response => (this.serieInfo = response.data.ville, this.seriePhoto = response.data.photos))
                    .catch(error => {
                        console.log(error);
                        this.errored = true;
                    });
            },
            takePicture() {
                camera
                    .requestPermissions()
                    .then(() => {
                        camera
                            .takePicture({
                                width: 300,
                                height: 300,
                                keepAspectRatio: true,
                                saveToGallery: false
                            })
                            .then(imageAsset => {
                                let img = new Image();
                                img.src = imageAsset;
                                this.images.push(img);



                                /*let watchId = geolocation.watchLocation(
                                     function(loc){
                                          if (loc) {
                                            
                                            console.log("Received location: " + loc);
                                          }
                                     }, function(e){
                                              console.log("Error: " + e.message);
                                          },{desiredAccuracy: 3, updateDistance: 10, minimumUpdateTime : 1000 * 20}
                                    )
                                    if (watchId) {
                                          geolocation.clearWatch(watchId);
                                      }*/
                                this.setGeolocation();
                                console.log(
                                    "ive got " + this.images.length +
                                    " images now."
                                );
                            })
                            .catch(e => {
                                console.log("error:", e);
                            });
                    })
                    .then(() => {

                    })
                    .catch(e => {
                        console.log("Error requesting permission");
                    });
            },

            upPhoto() {

                this.images.forEach(image => {


                    this.axios
                        .post("https://cbc0cb8c.ngrok.io/office/series/" + this.serieId + "/photos", image, {
                            headers: {
                                'Content-type': 'multipart/form-data',
                                 'Authorization': this.token
                            }
                        })
                        .then(response => (this.photoName = response.data.name, console.log(response)).catch(
                            err => console.log(error)));

                });
            },
            setSerie(serie) {
                this.serieId = serie;
            },
            setGeolocation() {
                let loca = geolocation.getCurrentLocation({
                    //desiredAccuracy: Accuracy.high,
                    maximumAge: 5000,
                    timeout: 20000
                }).then(response => this.lat.push(response.latitude),this.lon.push(response.longitude))
             
            },
            auth() {
                this.axios.post("https://cbc0cb8c.ngrok.io/login", JSON.stringify({
                        username: this.username,
                        password: this.pass
                    }), {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then((res) => {
                        //$cookie.set('token', res.headers)
                        let d = new Date();
                        d.setTime(d.getTime() + (7 * 24 * 60 * 60 * 1000));
                        let expires = "expires=" + d.toUTCString();
                        var cookieString = "token" + "=" + res.headers.authorization + ";" + expires;
                      
                        var cookieManager = android.webkit.CookieManager.getInstance();
                        cookieManager.setAcceptCookie(true);
                        cookieManager.removeSessionCookie();
                        cookieManager.setCookie("/token", cookieString);

                   



                        this.getAllSeries();
                        this.current = 2;
                    })
                    .catch((e) => {
                        this.error = true
                        console.log(e)
                    })

            },
            getAllSeries() {
                this.searchSeries();
                geolocation.isEnabled().then(function (isEnabled) {
                    if (!isEnabled) {
                        this.geocal = 1;
                        geolocation.enableLocationRequest().then(function () {}, function (e) {
                            console.log("Error: " + (e.message || e));
                        });
                    }
                }, function (e) {
                    console.log("Error: " + (e.message || e));
                });
            },
            getToken() {
                var cookieManager = android.webkit.CookieManager.getInstance();
             
                return cookieManager.getCookie("/token");
            }

        },
        mounted() {
            geolocation.enableLocationRequest();
        },

    }

</script>

<style scoped>
    ActionBar {
        background-color: teal;
        color: #ffffff;

    }

    .title {
        text-align: left;
        padding-left: 16;
    }

    .message {
        vertical-align: center;
        text-align: center;
        font-size: 20;
        color: #333333;
    }

    .drawer-header {
        padding: 50 16 16 16;
        margin-bottom: 16;
        background-color: teal;
        color: #ffffff;
        font-size: 24;
    }

    .drawer-item {
        padding: 8 16;
        color: #333333;
        font-size: 16;
    }

</style>
