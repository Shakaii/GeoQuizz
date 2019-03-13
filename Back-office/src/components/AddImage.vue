<template>
<el-dialog>
    <el-form :inline="true" :model="formInline">
        <el-form-item>
            <el-form-item label="Latitude">
                <el-input v-model="Zzz" placeholder="Latitude"></el-input>
            </el-form-item>
            <el-form-item label="Longitude">
                <el-input v-model="Zzz" placeholder="Longitude"></el-input>
            </el-form-item>
        </el-form-item>
        <br>
        <el-form-item label="Description">
            <el-input type="textarea" v-model="z"></el-input>
        </el-form-item>
    </el-form>
    <file-pond :files="myFiles" label-idle="Drop files here..." server="localhost:8080/api"/>
</el-dialog>
</template>

<script>
export default {
    name: 'addimage'
}
</script>
