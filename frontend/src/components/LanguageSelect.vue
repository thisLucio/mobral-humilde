<template>
  <div>
    <v-menu offset-y open-on-hover close-delay="300" close-on-content-click>
      <template v-slot:activator="{ on, attrs }">
        <v-icon v-bind="attrs" v-on="on">mdi-translate</v-icon>
      </template>
      <v-list>
        <v-list-item-group v-model="selectedValue" color="primary">
          <v-list-item
            :value="item.value"
            @click="handleChange(item)"
            v-for="item in items"
            :key="item.value"
          >
            <v-list-item-title>{{ item.name }}</v-list-item-title>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-menu>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import { translate } from '../i18n'

type LanguageItem = {
  name: string
  value: string
}

export default Vue.extend({
  data: function() {
    const lang: string = localStorage.getItem('lang') || 'pt'

    return {
      selectedValue: lang,
      items: [
        { value: 'pt', name: `${translate('front.6')}` },
        { value: 'en', name: `${translate('front.5')}` },
        { value: 'es', name: `${translate('front.31')}` }
      ] as Array<LanguageItem>,
    }
  },
  methods: {
    handleChange: function(event: LanguageItem) {
      localStorage.setItem('lang', event.value)
      window.location.reload()
    },
  },
})
</script>
