<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const isEditMode = ref(false);
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

const submitForm = async () => {
  if (isEditMode.value) {
    await axios.put(`/api/eventos/${route.params.id}`, evento.value);
  } else {
    await axios.post('/api/eventos', evento.value);
  }
  router.push('/eventos');
};

onMounted(() => {
  if (route.params.id) {
    isEditMode.value = true;
    fetchEvento(route.params.id);
  }
});
</script>

<template>
  <div>
    <h1>{{ isEditMode ? 'Editar Evento' : 'Crear Evento' }}</h1>
    <form @submit.prevent="submitForm">
      <div>
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" v-model="evento.nombre" required />
      </div>
      <div>
        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" v-model="evento.descripcion" />
      </div>
      <div>
        <label for="pais">País:</label>
        <input type="text" id="pais" v-model="evento.pais" />
      </div>
      <div>
        <label for="ciudad">Ciudad:</label>
        <input type="text" id="ciudad" v-model="evento.ciudad" />
      </div>
      <div>
        <label for="direccion">Dirección:</label>
        <input type="text" id="direccion" v-model="evento.direccion" />
      </div>
      <div>
        <label for="fecha">Fecha:</label>
        <input type="date" id="fecha" v-model="evento.fecha" required />
      </div>
      <div>
        <label for="horaInicio">Hora Inicio:</label>
        <input type="time" id="horaInicio" v-model="evento.horaInicio" required />
      </div>
      <div>
        <label for="horaFinal">Hora Final:</label>
        <input type="time" id="horaFinal" v-model="evento.horaFinal" required />
      </div>
      <button type="submit">{{ isEditMode ? 'Actualizar' : 'Crear' }}</button>
    </form>
  </div>
</template>



<style>
/* Add your styles here */
</style>