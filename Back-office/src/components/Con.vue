<template>
<el-row>
    <el-col :span="8" offset="8">
    <el-card>
        <el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="120px" class="demo-ruleForm">
            <el-form-item label="Username"  prop="pass">
                <el-input type="text" v-model="username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="Password"  prop="pass">
                <el-input type="password" v-model="pass"  autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary">Submit</el-button>
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
                pass: ""
            }
        },
    }
</script>
<style scoped>

</style>