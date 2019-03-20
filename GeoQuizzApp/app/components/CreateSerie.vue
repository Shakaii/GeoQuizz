<template>
<FlexboxLayout>
    <Label text="Création de série"/>
    <StackLayout v-show="active == 0" >
        <Label text="Nom de la série"/>
        <TextField hint="Nom de la série (ville)" autocorrect="false" v-model="name" class="input input-border"></TextField> 
        <Button text="Prochaine étape" @tap="next"/>
    </StackLayout>
    <StackLayout v-show="active == 1">
        <Label text="Géolocalisation de la série"/>
        <TextField hint="Latitude" autocorrect="false" v-model="lat" keyboardType="number" class="input input-border"></TextField> 
        <TextField hint="Longitude" autocorrect="false" v-model="lng" keyboardType="number" class="input input-border"></TextField> 
        <Button text="Prochaine étape" @tap="next"/>
        <Button text="Etape précédente" icon="el-icon-arrow-left" @tap="back"/>
    </StackLayout>
    <StackLayout v-show="active == 2">
        <Label text="Ajout des photos"/>
        <Button text="Prendre une photo" @tap="takePicture" />
        <Button text="Importer une photo" @tap="selectPicture" />
        <Button text="Prochaine étape" @tap="next"/>
        <Button text="Etape précédente" @tap="back"/>
    </StackLayout>
    <StackLayout v-show="active == 3">
        <Label text="Confirmation"/>
        <Button text="Terminer" @tap="createSerie"/>
        <Button text="Etape précédente" @tap="back"/>
    </StackLayout>
</FlexboxLayout>
</template>

<script>
import * as camera from "nativescript-camera";
    import * as imagepicker from "nativescript-imagepicker";
import {
        Image
    } from "tns-core-modules/ui/image";

export default {
    name: 'createserie',
    components: {
    },
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
            imgUrl: "",
            images: [],
        }
    },
    components: {
        
    },
    methods: {
      next() {
        if (this.active++ > 3) this.active = 0;
        console.log(this.lat+" / "+this.lng);
      },
      back() {
        if (this.active-- < 0) this.active = 0;
      },
      createSerie() {
        this.axios.post('https://cbc0cb8c.ngrok.io/', {
            ville: this.name,
            dist: 3,
            lat: this.lat,
            lng: this.lng
        })
        .then((response) => {
            console.log(response.data);
            this.$router.push("/addPhotos/"+this.images);
        })
        .catch(function (error) {
            console.log(error);
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
            },
    }
}
</script>
<style scoped>
</style>
