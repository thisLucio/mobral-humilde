<template>
  <v-select
      v-model="selectedValue"
      :items="items"
      item-text="name"
      item-value="value"
      persistent-hint
      return-object
      hide-details
      single-line
      @change="handleChange(selectedValue)"
    >
    </v-select>
</template>

<script lang="ts">
import { translate } from '../i18n'

export default {
  data: function () {
    const lang: string = localStorage.getItem('lang') || 'pt'

    return {
      selectedValue: lang,
      items: [
        { value: 'pt', name: `${translate('front.6')}` },
        { value: 'en', name: `${translate('front.5')}` },
      ],
    }
  },
  methods: {
    handleChange: function (event) {
      localStorage.setItem('lang', event.value)
      window.location.reload()
    },
  },
}
</script>