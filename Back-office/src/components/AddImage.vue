<template>
    <div>
        <el-steps :active="active" finish-status="success" align-center>
            <el-step title="Upload vos images"></el-step>
            <el-step title="Geolocalisation des images"></el-step>
            <el-step title="Finalisation"></el-step>
        </el-steps>
        <el-row v-show="active == 0">
            <el-col :span="8" :offset="8">
                <file-pond @processfile="detail" name="file" allow-multiple="true" max-files="3" server="http://localhost:8081/office/file" />
                <el-button :disabled="imgs.length == 0" style="margin-top: 12px;" @click="next" icon="el-icon-arrow-right">Prochaine
                    étape</el-button>
            </el-col>
        </el-row>
        <div v-show="active == 1">
            <geoloc :addImg=addImages :files=this.imgs></geoloc>
            <el-button style="margin-top: 12px;" @click="back">Étape précédente</el-button>
            <el-button style="margin-top: 12px;" @click="next" icon="el-icon-arrow-right">Prochaine étape</el-button>
        </div>
        <div v-show="active == 2">
            <el-button style="margin-top: 12px;" @click="back">Étape précédente</el-button>
            <router-link to="/">
                <el-button @click="createImages" style="margin-top: 10px; margin-left: 12px">Terminer</el-button>
            </router-link>
        </div>

    </div>
</template>

<script>
    // Import FilePond
    import vueFilePond, {
        setOptions
    } from 'vue-filepond';
    import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
    import Geoloc from './Geoloc'
    // Import styles
    import 'filepond/dist/filepond.min.css';
    import 'filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css';


    // Create FilePond component
    const FilePond = vueFilePond(FilePondPluginImagePreview);

    setOptions({
        server: {
            remove: (source, load, error) => {
                // Should somehow send `source` to server so server can remove the file with this source
                console.log(source)
                // Can call the error method if something is wrong, should exit after
                error('oh my goodness');

                // Should call the load method when done, no parameters required
                load();
            }
        }
    });

    export default {
        name: 'addimage',
        data() {
            return {
                active: 0,
                img: 1,
                imgs: [],
                src: "http://localhost:8081/office/files/?name=",
                upImg: "http://localhost:8081/office/series/" + this.idSerie + "/photos",
                obj: []
            }
        },
        props: ['idSerie'],
        components: {
            FilePond,
            Geoloc
        },
        methods: {
            detail(error, file) {
                this.imgs.push(file.file.name)
            },
            next() {
                if (this.active++ > 2) this.active = 0;
            },
            back() {
                if (this.active-- < 0) this.active = 0;
            },
            nextImage() {
                if (this.img++ < this.imgs.length) this.img = 0;
            },
            addImages(o) {
                this.obj.push(o)
            },
            createImages() {
                let token = this.getCookie("token")
                let url = 'http://localhost:8081/office/series/' + this.idSerie + '/photos'
                this.obj.forEach((e) => {
                    this.axios.post(url, e, {
                            headers: {
                                'Authorization': token
                            }
                        })
                        .then((response) => {
                            console.log(response);
                        })
                        .catch(function (error) {
                            console.log(error);
                        });
                })
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
    img {
        width: 50%;
        height: auto;
    }
</style>