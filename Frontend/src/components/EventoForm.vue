<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
const isEditMode = ref(false);
const isViewMode = ref(false);
const evento = ref({
  nombre: '',
  descripcion: '',
  pais: '',
  ciudad: '',
  direccion: '',
  fecha: '',
  horaInicio: '',
  horaFinal: ''
});

const fetchEvento = async (id) => {
  const response = await axios.get(`/api/eventos/${id}`);
  evento.value = response.data;
};

const handleSubmit = async () => {
  if (isEditMode.value) {
    await axios.put(`/api/eventos/${route.params.id}`, evento.value);
  } else {
    await axios.post('/api/eventos', evento.value);
  }
  router.push('/');
};

onMounted(() => {
  if (route.params.id) {
    if (route.name === 'viewEvento') {
      isViewMode.value = true;
    } else {
      isEditMode.value = true;
    }
    fetchEvento(route.params.id);
  }
});
</script>
<template>
    <div>
      <h1>{{ isEditMode ? 'Editar Evento' : isViewMode ? 'Ver Evento' : 'Crear Evento' }}</h1>
      <form @submit.prevent="handleSubmit" v-if="!isViewMode">
        <div>
          <label>Nombre:</label>
          <input v-model="evento.nombre" :disabled="isViewMode" required />
        </div>
        <div>
          <label>Descripción:</label>
          <input v-model="evento.descripcion" :disabled="isViewMode" />
        </div>
        <div>
          <label>País:</label>
          <input v-model="evento.pais" :disabled="isViewMode" />
        </div>
        <div>
          <label>Ciudad:</label>
          <input v-model="evento.ciudad" :disabled="isViewMode" />
        </div>
        <div>
          <label>Dirección:</label>
          <input v-model="evento.direccion" :disabled="isViewMode" />
        </div>
        <div>
          <label>Fecha:</label>
          <input type="date" v-model="evento.fecha" :disabled="isViewMode" />
        </div>
        <div>
          <label>Hora Inicio:</label>
          <input type="time" v-model="evento.horaInicio" :disabled="isViewMode" />
        </div>
        <div>
          <label>Hora Final:</label>
          <input type="time" v-model="evento.horaFinal" :disabled="isViewMode" />
        </div>
        <button type="submit">{{ isEditMode ? 'Actualizar' : 'Crear' }}</button>
      </form>
      <div v-else>
        <div>
          <label>Nombre:</label>
          <span>{{ evento.nombre }}</span>
        </div>
        <div>
          <label>Descripción:</label>
          <span>{{ evento.descripcion }}</span>
        </div>
        <div>
          <label>País:</label>
          <span>{{ evento.pais }}</span>
        </div>
        <div>
          <label>Ciudad:</label>
          <span>{{ evento.ciudad }}</span>
        </div>
        <div>
          <label>Dirección:</label>
          <span>{{ evento.direccion }}</span>
        </div>
        <div>
          <label>Fecha:</label>
          <span>{{ evento.fecha }}</span>
        </div>
        <div>
          <label>Hora Inicio:</label>
          <span>{{ evento.horaInicio }}</span>
        </div>
        <div>
          <label>Hora Final:</label>
          <span>{{ evento.horaFinal }}</span>
        </div>
      </div>
    </div>
  </template>
  
