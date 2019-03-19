<template>
    <el-menu :default-active="activeIndex" mode="horizontal">
        <el-menu-item index="1" @click="toInsc">Inscription</el-menu-item>
        <el-menu-item index="2" @click="toCon">Connexion</el-menu-item>
    </el-menu>
</template>

<script>
import Con from "./Con";
export default {
    name: 'navbar',
    data() {
        return {
            activeIndex: "0"
        }
    },
    components: {
        Con
    },
    methods: {
        toCon() {
            this.$router.push("/connexion")
        },
        toInsc() {
            this.$router.push("/inscription")
        }
    },
}
</script>
<style scoped>
</style>
