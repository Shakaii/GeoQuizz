<template>
    <Page>
        <ActionBar>
            <GridLayout width="100%" columns="auto, *">
                <Label text="MENU" @tap="$refs.drawer.nativeView.showDrawer()" col="0"/>
                <Label class="title" text="GeoQuizz Mobile"  col="1"/>
            </GridLayout>
        </ActionBar>
        <RadSideDrawer  ref="drawer">
         
            <StackLayout  ~drawerContent backgroundColor="#ffffff">
                <Label class="drawer-header" @tap="current = 0; $refs.drawer.nativeView.closeDrawer()" text="Series"/>
                
                    <Label v-for='serie in seriesInfo' class="drawer-item" @tap="current =serie._links.self.href;$refs.drawer.nativeView.closeDrawer()" :text='serie.ville'/>
                
             
            </StackLayout>
           
            <GridLayout ~mainContent columns="*" rows="*">
                <Label v-show="current == 0" class="message" :text="msg" col="0" row="0"/>
                <Camera v-show="current == 1" ></Camera>
          
            </GridLayout>
        </RadSideDrawer>
    </Page>
</template>

<script>
import Camera from './Camera.vue';
  export default {
      name: 'App',
      components: {
          Camera,
         
      },

    data() {
      return {
        msg: 'Bienvenue sur GeoQuizz Mobile!',
        current: "",
        seriesInfo:"",
      }
    },
    methods:{
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
     created:function() {
        this.searchSeries();
    }
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
