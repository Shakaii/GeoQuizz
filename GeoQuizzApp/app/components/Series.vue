<template>
  
    <div>
        <div v-for='serie in seriesInfo'>
            <Label class="drawer-item" @tap="current =serie._links.self.href;$refs.drawer.nativeView.closeDrawer()" text="{{serie.ville}}"/>
        </div>
    </div>
</template>

<script>


  export default {
      name: 'Series',
      components: {
      
         
      },

    data() {
      return {
          seriesInfo:"",
      }
    }, 
    props:['current'],
    methods: {
        searchSeries(){
            this.axios
                .get("https://a8b10422.ngrok.io/office/series")
                .then(response => (this.seriesInfo = response.data._embedded.series))         
                .catch(error => {
                console.log(error);             
                this.errored = true; 
            });
        }
    },
   
  }
</script>

<style scoped>
    ActionBar {
        background-color: #53ba82;
        color: #ffffff;
    }

    .title {
        text-align: left;
        padding-left: 16;
    }

    .message {
        vertical-align: center;
        text-align: center;
        font-size: 20;
        color: #333333;
    }

    .drawer-header {
        padding: 50 16 16 16;
        margin-bottom: 16;
        background-color: #53ba82;
        color: #ffffff;
        font-size: 24;
    }

    .drawer-item {
        padding: 8 16;
        color: #333333;
        font-size: 16;
    }
</style>
