<template>
        <el-button size="mini" @click="callRetrieve">
            <h1>{{name}}</h1>
            <img src="../assets/logo.png">
        </el-button>
</template>

<script>

export default {
  name: 'serie',
  data() {
      return {
          series: []
      }
  },
  props: {
    name: String,
    fn: Function,
    id: String
  },
  methods: {
      callRetrieve() {
          this.fn(this.id)
      }
  }
}
</script>