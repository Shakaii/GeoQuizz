<template>
<div>
    <el-menu v-show='token == ""' :default-active="activeIndex" mode="horizontal">
        <el-menu-item index="1" @click="toInsc">Inscription</el-menu-item>
        <el-menu-item index="2" @click="toCon">Connexion</el-menu-item>
    </el-menu>
    <el-menu v-show='token != ""' :default-active="activeIndex" mode="horizontal">
        <el-menu-item index="1">Connected</el-menu-item>
        <el-menu-item index="2" @click="disco">Disconnect</el-menu-item>
    </el-menu>
</div>
</template>

<script>
import Con from "./Con";
export default {
    name: 'navbar',
    data() {
        return {
            activeIndex: "0",
            token: ""
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
        },
        disco() {
            document.cookie = "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC";
            this.token = ""
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
        this.token = this.getCookie("token")
    },
}
</script>
<style scoped>
</style>
