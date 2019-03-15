<template>
        <StackLayout>
            <Button text="Take Picture" @tap="takePicture" />
            <Button text="Choose Picture" @tap="selectPicture" />
            <WrapLayout>
                <Image v-for="img in images" :src="img.src" width="75" height="75" />
            </WrapLayout>
        </StackLayout>
  
</template>

<script>
    import * as camera from "nativescript-camera";
    import * as imagepicker from "nativescript-imagepicker";

    import {
        Image
    } from "tns-core-modules/ui/image";

    export default {
        data() {
            return {
                images: []
            };
        },
        methods: {
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
        }
    };
</script>
