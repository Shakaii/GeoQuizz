<template>
    <Mapbox
                    accessToken="pk.eyJ1Ijoid2l0dG1hbjE5dSIsImEiOiJjanRoMDE5dDgxejllNDR0ZjIwajUzeGFmIn0.TJg-3MrTU34i10a7UUpXhA"
                    mapStyle="traffic_day"
                    latitude="37.7397"
                    longitude="-121.4252"
                    hideCompass="true"
                    zoomLevel="12"
                    showUserLocation="false"
                    disableZoom="false"
                    disableRotation="false"
                    disableScroll="false"
                    disableTilt="false"
                    @mapReady="onMapReady($event)">
    </Mapbox>
</template>

<script>
    import * as utils from "utils/utils";
    export default {
        name: 'mapbox',
        data () {
            return { };
        },
        methods: {
            onMapReady(args) {
                args.map.addMarkers([
                    {
                        lat: 37.7397,
                        lng: -121.4252,
                        title: "Tracy, CA",
                        subtitle: "Home of The Polyglot Developer!",
                        onCalloutTap: () => {
                            utils.openUrl("https://www.thepolyglotdeveloper.com");
                        }
                    }
                ]);
            }
        }
    };
</script>

<style scoped></style>