<template>
    <el-button @click="selectSerie(name, id)" v-bind:class="{isSelected: selected}">
        <h1>{{name}}</h1>
        <img src="../assets/logo.png">
    </el-button>
</template>

<script>

    export default {
        name: 'serie',
        data() {
            return {
                series: [],
            }
        },
        props: ['name', 'selected','id'],
        methods: {
            selectSerie(ville, id){
                this.selectedSerie = ville;
                this.$emit('selectSerie', ville)
                this.$emit('serieId', id);
            }
        },
    }
</script>

<style scoped>
    .isSelected{
        background-color: #e6f2f2;
    }
</style>