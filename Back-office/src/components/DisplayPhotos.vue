<template>
<div>
    <el-col :span="12" v-for="photo in photos">
        <el-card :body-style="{ padding: '0px' }">
            <img class="image" :src=photo.url>
        </el-card>
    </el-col>
</div>
</template>

<script>
    export default {
        name: 'displayphoto',
        props: {
            photos: Array
        }
    }
</script>

<style scoped>
.image {
    width: 100%;
    display: block;
  }
</style>
