<template>
    <el-row>
        <serie v-on:serieId="selectSerieId" v-on:selectSerie="selectSerie" v-for="serie in series" v-bind:name="serie.ville" v-bind:id="serie.id" v-bind:selected="serie.ville == selectedSerie"></serie>
        <div class="spinnerContainer"><Spinner v-if="series.length == 0"></Spinner></div>
    </el-row>
</template>

<script>
    import Serie from './Serie.vue'
    import Spinner from './Spinner.vue'
    export default {
        name: 'series',
        components: {
            Serie,
            Spinner
        },

        data() {
            return {
                series: [],
                selectedSerie: ""
            }
        },

        methods: {
            retrieveSeries() {
                let $this = this;
                this.axios.get('http://localhost:8083/player/series')
                .then((response) => {
                    $this.series = response.data;
                }).catch((err) => {
                $this.$message.error("Erreur lors de la récupération des séries. Réessayez plus tard : " + err);
            });;
            },
            selectSerie(ville){
                this.selectedSerie = ville
            },
            selectSerieId(id){
              this.$emit('serieId', id);
            }
        },
        created: function() {
            this.retrieveSeries()
        }
    }
</script>

<style scoped>

    .h {
        overflow-x: scroll;
        overflow-y: hidden;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
    }

    h3 {
        margin: 40px 0 0;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }

    .spinnerContainer{
        display:flex;
        align-items: center;
        justify-content:center;
    }
</style>