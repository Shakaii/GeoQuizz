<template>
    <div>
        <el-steps :active="active" finish-status="success" align-center>
            <el-step title="Nom de la serie"></el-step>
            <el-step title="Geolocalisation de la serie"></el-step>
            <el-step title="Finalisation"></el-step>
        </el-steps>
        <div v-show="active == 0">
            <el-input placeholder="Nom/Ville de la serie" v-model="name" clearable>
            </el-input>
            <el-button :disabled='name == ""' style="margin-top: 12px;" icon="el-icon-arrow-right" @click="next">Prochaine
                étape</el-button>
        </div>
        <div v-show="active == 1">
            <div>
                <l-map @click="demoClick" :zoom="zoom" :center="center">
                    <l-tile-layer ref="map" :key="tileProvider.name" :name="tileProvider.name" :url="tileProvider.url"
                        :attribution="tileProvider.attribution" layer-type="base" />
                </l-map>
            </div>
            <el-button style="margin-top: 12px;" @click="back">Étape précédente</el-button>
            <el-button :disabled="lat == null" style="margin-top: 12px;" icon="el-icon-arrow-right" @click="next">Prochaine
                étape</el-button>
        </div>
        <div v-show="active == 2">
            <el-button style="margin-top: 12px;" @click="back">Étape précédente</el-button>
            <el-button style="margin-top: 12px;" @click="createSerie">Ajouter des photos</el-button>
            <router-link to="/">
                <el-button style="margin-top: 12px;" @click="createSerie">Terminer</el-button>
            </router-link>
        </div>
    </div>
</template>

<script>
    import Vue2Leaflet from 'vue2-leaflet';
    import {
        LMap,
        LTileLayer,
        LMarker
    } from 'vue2-leaflet';

    export default {
        name: 'createserie',
        data() {
            return {
                active: 0,
                name: "",
                tileProvider: {
                    name: 'OpenStreetMap',
                    visible: true,
                    attribution: '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
                    url: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                },
                zoom: 13,
                center: L.latLng(48.69333, 6.18324),
                lat: null,
                lng: null,
                imgUrl: ""
            }
        },
        components: {
            LMap,
            LTileLayer,
            LMarker,
        },
        methods: {
            next() {
                if (this.active++ > 2) this.active = 0;
            },
            back() {
                if (this.active-- < 0) this.active = 0;
            },
            demoClick(e) {
                this.lat = e.latlng.lat
                this.lng = e.latlng.lng
            },
            createSerie() {
                let token = this.getCookie("token")
                this.axios.post('http://localhost:8081/office/series', {
                        ville: this.name,
                        dist: 3,
                        lat: this.lat,
                        lng: this.lng
                    }, {
                        headers: {
                            'Authorization': token
                        }
                    })
                    .then((response) => {
                        this.$router.push("/addPhotos/" + response.data.id)
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            },
            getCookie(cname) {
                var name = cname + "=";
                var decodedCookie = decodeURIComponent(document.cookie);
                var ca = decodedCookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ') {
                        c = c.substring(1);
                    }
                    if (c.indexOf(name) == 0) {
                        return c.substring(name.length, c.length);
                    }
                }
                return "";
            }
        },
        mounted() {
            if (this.getCookie('token') == '') {
                this.$router.push('/connexion')
            }
        }
    }
</script>
<style scoped>
    .vue2leaflet-map {
        height: 500px;
        width: 50%;
    }

    .el-input {
        width: 50%
    }
</style>