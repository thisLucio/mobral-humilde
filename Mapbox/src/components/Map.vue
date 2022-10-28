<template>
  <div id="map"></div>
</template>

<script lang="ts">
import Mapbox, { Control, Marker,Popup } from "mapbox-gl";
import { Component, Vue } from "vue-property-decorator";
import api from "../services/api";
import socket from "../services/socket";
import { Message } from "webstomp-client";

type Equipment = {
  id: number;
  latitude: number;
  longitude: number;
  description: string;
  color: string;
  operationType: "UPDATE" | "CREATE" | "DELETE";
};

type EquipmentMarker = {
  equipment: Omit<Equipment, "operationType">;
  marker: Marker;
  popup: Popup;
};

@Component
export default class Map extends Vue {
  equipments: Array<Omit<Equipment, "operationType">> = [];
  map!: Mapbox.Map;
  nav?: Control;
  markers: Array<EquipmentMarker> = [];
  container = "map";
  accessToken =
    "pk.eyJ1Ijoic2F5dXJpMDgiLCJhIjoiY2txOXEyN3UzMDFuMjJvbGp3bWpiY3JnbSJ9.ACI53nLC52OHC4XNhQtaEg"; // key
  mapStyle = "mapbox://styles/mapbox/streets-v11?optimize=true"; // estilo do mapa feito pelo mapbox
  zoom = 6;
  showCompass = true;
  showZoom = true;
  lng = -46.6388;
  lat = -23.5489;
  maxZoom = 13;
  minZoom = 1;
  
  created() {
    socket.connect({}, () => {
      socket.subscribe("/topic/equipment", this.handleMessage);
    });
  }

  handleMessage(message: Message) {
    const equipmentUpdate = JSON.parse(message.body) as Equipment;

    if (equipmentUpdate.operationType === "UPDATE") {
      const markerItem = this.markers.find(
        (item) => item.equipment.id === equipmentUpdate.id
      );

      if (!markerItem) {
        return;
      }

      const gItems = markerItem.marker
        .getElement()
        .querySelectorAll(`svg g[fill="${markerItem.equipment.color}"]`);

      gItems.forEach((item) => {
        item.setAttribute("fill", equipmentUpdate.color);
      });

      markerItem.equipment = {
        ...equipmentUpdate,
      };

      markerItem.marker.setLngLat([
        equipmentUpdate.longitude,
        equipmentUpdate.latitude,
      ]);
      markerItem.popup.setHTML("<h3>"+ equipmentUpdate.description+
            "</h3>"+ "<p>"+ "lng: "+equipmentUpdate.longitude+" "+"lat: "+equipmentUpdate.latitude+"</p>")
    } else if (equipmentUpdate.operationType === "CREATE") {
      const popup = new Mapbox.Popup({ offset: 25 })
        .setHTML("<h3>"+ equipmentUpdate.description+"</h3>"+ "<p>"+ "lng: "+equipmentUpdate.longitude+" "+"lat: "+equipmentUpdate.latitude+"</p>")
      const marker = new Mapbox.Marker({
        color: equipmentUpdate.color,
      })
        .setLngLat([equipmentUpdate.longitude, equipmentUpdate.latitude])
        .setPopup(popup)
        .addTo(this.map);

      this.markers.push({
        equipment: equipmentUpdate,
        marker,
        popup
      });
    } else if (equipmentUpdate.operationType === "DELETE") {
      const markerItem = this.markers.find(
        (item) => item.equipment.id === equipmentUpdate.id
      )
      
      if (!markerItem) {
        return;
      }
      
      markerItem.marker.remove();
      this.markers = this.markers.filter((item) => item.equipment.id !== equipmentUpdate.id);
    };
  }

  async mounted() {
    Mapbox.accessToken = this.accessToken;
    this.map = new Mapbox.Map({
      container: this.container,
      style: this.mapStyle,
      center: [this.lng, this.lat],
      zoom: this.zoom,
      maxZoom: this.maxZoom,
      minZoom: this.minZoom,
    });
    this.nav = new Mapbox.NavigationControl({
      showCompass: this.showCompass,
      showZoom: this.showZoom,
    });
    this.map.addControl(this.nav, "top-left");
    this.map.addControl(new Mapbox.FullscreenControl());

    const scale = new Mapbox.ScaleControl({
      maxWidth: 80,
      unit: 'imperial'
    });
    this.map.addControl(scale);
    scale.setUnit('metric');

    const { data: equipments } = await api.get<Array<Equipment>>("/equipment");
    this.equipments = equipments;
      this.equipments.forEach((item) => {
      const popup = new Mapbox.Popup({ offset: 25 })
        .setHTML("<h3>"+ item.description+"</h3>"+ "<p>"+ "lng: "+item.longitude+" "+"lat: "+item.latitude+"</p>")
        
      const marker = new Mapbox.Marker({
        color: item.color,
      })
     
        .setLngLat([item.longitude, item.latitude])
        .setPopup(popup)
        .addTo(this.map);
        
      
      this.markers.push({
        equipment: item,
        marker,
        popup
         
      });
    });
  }
}
</script>

<style scoped>
#map {
  width: 100vw;
  height: 100vh;
}
</style>