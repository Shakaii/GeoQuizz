<template>
    <Page>
        <ActionBar>
            <GridLayout width="100%" columns="auto, *">
                <Label text="MENU" @tap="$refs.drawer.nativeView.showDrawer()" col="0"/>
                <Label class="title" text="GeoQuizz Mobile"  col="1"/>
            </GridLayout>
        </ActionBar>
       
        <RadSideDrawer ref="drawer">
         
           
            <StackLayout  ~drawerContent backgroundColor="#ffffff">
                <ScrollView >
                    <StackLayout>
                    <Label class="drawer-header" @tap="current = 0; $refs.drawer.nativeView.closeDrawer()" text="Séries"/>
                 
                    <Label class="drawer-item" @tap="current = 2;$refs.drawer.nativeView.closeDrawer()" text='Créer une nouvelle série'/>

                    <Label v-for='serie in seriesInfo' class="drawer-item" @tap="current = 1;searchSerie(serie._links.self.href);$refs.drawer.nativeView.closeDrawer()" :text='serie.ville'/>
                    
                    </StackLayout>
                 </ScrollView >
             
            </StackLayout>
            <GridLayout ~mainContent columns="*" rows="*">
                <Label v-show="current == 0" class="message" :text="msg" col="0" row="0"/>
                
                <StackLayout v-show="current == 1">
                    <label class="message" :text="serieInfo" col="0" row="0"/>
                    <Button text="Prendre une photo" @tap="takePicture" />
                    <ScrollView orientation="horizontal">
                        <WrapLayout>                             
                            <Image v-for="img in images" :src="img.src" width="150" height="150" />                            
                        </WrapLayout> 
                        </ScrollView >
                        <ScrollView orientation="horizontal">
                        <WrapLayout>
                            <Image v-for="ima in seriePhoto" :src="ima.url" width="150" height="150" />
                        </WrapLayout>
                        </ScrollView >
                </StackLayout>
            
                <create-serie v-show="current == 2"/>
          
            </GridLayout>
            
        </RadSideDrawer>

        
    </Page>
</template>

<script>
import CreateSerie from './CreateSerie.vue'

import * as camera from "nativescript-camera";
    import * as imagepicker from "nativescript-imagepicker";

    import {
        Image
    } from "tns-core-modules/ui/image";

  export default {
      name: 'App',
      components: {
          CreateSerie,
         
      },

    data() {
      return {
        msg: 'Bienvenue sur GeoQuizz Mobile!',
        current: "",
        seriesInfo:"",
        serieInfo:"",
        seriePhoto:"",
        images: [],
      }
    },
    methods:{
        searchSeries(){
            this.axios
                .get("https://a8b10422.ngrok.io/office/series")
                .then(response => (this.seriesInfo = response.data._embedded.series))         
                .catch(error => {
                console.log(error);             
                this.errored = true; 
            });
        },
        searchSerie(link){
             this.axios
                .get(link)
                .then(response => (this.serieInfo = response.data.ville,this.seriePhoto = response.data.photos))         
                .catch(error => {
                console.log(error);             
                this.errored = true; 
            }); 
        },
        selectPicture() {
                let context = imagepicker.create({
                    mode: "multiple"
                });

                context
                    .authorize()
                    .then(function() {
                        return context.present();
                    })
                    .then(selection => {
                        selection.forEach(selected => {
                            console.log(JSON.stringify(selected));

                            let img = new Image();
                            img.src = selected;
                            this.images.push(img);
                        });
                    })
                    .catch(function(e) {
                        console.log("error in selectPicture", e);
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
                                console.log(
                                    "ive got " + this.images.length +
                                    " images now."
                                );
                            })
                            .catch(e => {
                                console.log("error:", e);
                            });
                    })
                    .catch(e => {
                        console.log("Error requesting permission");
                    });
            }
    },
     created:function() {
        this.searchSeries();
    }
  }
</script>

<style scoped>
    ActionBar{
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
        background-color:teal;
        color: #ffffff;
        font-size: 24;
    }

    .drawer-item {
        padding: 8 16;
        color: #333333;
        font-size: 16;
    }

   
</style>
