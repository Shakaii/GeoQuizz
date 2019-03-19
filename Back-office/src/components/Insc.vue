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
                <el-button type="primary" @click="createAccount">Submit</el-button>
                <el-button>Reset</el-button>
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
                this.pass === this.passCheck ? console.log("yes") : console.log("no")
            }
        },
    }
</script>
<style scoped>

</style>