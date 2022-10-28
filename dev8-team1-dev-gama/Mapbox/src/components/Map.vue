<template>
  <div id="map"></div>
</template>

<script lang="ts">
import Mapbox, { Control } from "mapbox-gl";
import { Component, Vue, Watch } from "vue-property-decorator";
import { getters } from "../observables/Equipment";

@Component
export default class Map extends Vue {
  map?: Mapbox.Map = undefined; // ? pode ou não ter valor - ! variavél terá valor
  nav?: Control;
  container = "map";
  accessToken =
    "pk.eyJ1Ijoic2F5dXJpMDgiLCJhIjoiY2txOXEyN3UzMDFuMjJvbGp3bWpiY3JnbSJ9.ACI53nLC52OHC4XNhQtaEg"; // key
  mapStyle = "mapbox://styles/sayuri08/ckq9sror12jzk17lbqglyddik"; // estilo do mapa feito pelo mapbox
  zoom = 4;
  showCompass = true;
  showZoom = true;
  lat = -21.2115;
  lng = -50.4261;

  get equipment(): any {
    return getters.getEquipment();
  }

  @Watch("equipment")
  onPropertyChanged(V: string, ov: string) {
    console.log("onPropertyChanged", V);
  }

  mounted(): void {
    Mapbox.accessToken = this.accessToken;
    this.map = new Mapbox.Map({
      container: this.container,
      style: this.mapStyle,
      center: [this.lat, this.lng], // onde o mapa irá ser centralizado no começo
      zoom: this.zoom,
    });
    this.nav = new Mapbox.NavigationControl({
      // adcionar controles de zoom e compasso
      showCompass: this.showCompass,
      showZoom: this.showZoom,
    });
    this.map.addControl(this.nav, "top-left"); // localização do controle de navegação
    new Mapbox.Marker({
      //adicionar marcador
      color: "#FF0000",
    })
      .setLngLat([this.lat, this.lng])
      .addTo(this.map);

      new Mapbox.Marker({
      //adicionar marcador
      color: "#FFFFFF",
    })
      .setLngLat([this.lat + 10, this.lng + 10])
      .addTo(this.map);

      new Mapbox.Marker({
      //adicionar marcador
      color: "#FFFFFF",
    })
      .setLngLat([this.lat + 10, this.lng + 10])
      .addTo(this.map);
  }
}
</script>

<style scoped>
#map {
  width: 100vw;
  height: 100vh;
}
</style>
