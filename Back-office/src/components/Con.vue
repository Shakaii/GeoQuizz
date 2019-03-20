<template>
    <el-row>
        <el-col :span="8" :offset="8">
            <el-card>
                <el-form status-icon ref="ruleForm2" label-width="120px" class="demo-ruleForm">
                    <el-form-item label="Username" prop="pass">
                        <el-input type="text" v-model="username" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="Password" prop="pass">
                        <el-input type="password" v-model="pass" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-alert v-if="error" title="erreur" type="error" show-icon>
                        Mot de passe ou nom d'utilisateur incorrect
                    </el-alert>
                    <el-form-item>
                        <el-button @click="con" type="primary">Submit</el-button>
                        <el-button>Reset</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>
    </el-row>
</template>
<script>
    export default {
        name: 'connexion',
        data() {
            return {
                username: "",
                pass: "",
                error: false
            }
        },
        methods: {
            con() {
                this.axios.post("http://localhost:8081/login", JSON.stringify({
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
                        document.cookie = "token=" + res.headers.authorization + ";" + expires
                        console.log(res)
                        this.$router.push("/")
                    })
                    .catch((e) => {
                        this.error = true
                        console.log(e)
                    })
            }
        },
    }
</script>
<style scoped>

</style>