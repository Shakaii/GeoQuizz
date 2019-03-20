<template>
<el-row>
    <el-col :span="8" :offset="8">
    <el-card>
        <el-form status-icon ref="ruleForm2" label-width="120px" class="demo-ruleForm">
            <el-form-item label="Username">
                <el-input type="text" v-model="username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Password">
                <el-input type="password" v-model="pass"  autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Confirm">
                <el-input type="password" v-model="passCheck"  autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="createAccount">S'inscrire</el-button>
            </el-form-item>
        </el-form>
    </el-card>
    </el-col>
</el-row>
</template>
<script>
    export default {
        name: 'inscription',
        data() {
            return {
                username: "",
                pass: "",
                passCheck: ""
            }
        },
        methods: {
            createAccount() {
                this.pass === this.passCheck ? this.insc() : console.log("no")
            },
            insc() {
                this.axios.post("http://localhost:8081/registration", {
                    username: this.username,
                    password: this.pass
                })
                .then((res) => {
                    console.log(res)
                })
                .catch((e) => {
                    console.log(e)
                })
            }
        },
    }
</script>
<style scoped>

</style>