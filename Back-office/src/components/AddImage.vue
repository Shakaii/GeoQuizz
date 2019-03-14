<template>
<el-dialog>
    <el-form :inline="true">
        <el-form-item>
            <el-form-item label="Latitude">
                <el-input placeholder="Latitude"></el-input>
            </el-form-item>
            <el-form-item label="Longitude">
                <el-input placeholder="Longitude"></el-input>
            </el-form-item>
        </el-form-item>
        <br>
        <el-form-item label="Description">
            <el-input type="textarea" ></el-input>
        </el-form-item>
    </el-form>
    <file-pond  label-idle="Drop files here..." server="localhost:8080/api"/>
</el-dialog>
</template>

<script>
export default {
    name: 'addimage'
}
</script>
