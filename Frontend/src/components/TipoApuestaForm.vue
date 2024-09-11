<template>
    <div>
      <h1>{{ isEditMode ? 'Editar Tipo de Apuesta' : 'Crear Tipo de Apuesta' }}</h1>
      <form @submit.prevent="submitForm">
        <div>
          <label for="nombre">Nombre:</label>
          <input type="text" id="nombre" v-model="tipoApuesta.nombre" required />
        </div>
        <div>
          <label for="descripcion">Descripción:</label>
          <input type="text" id="descripcion" v-model="tipoApuesta.descripcion" />
        </div>
        <div>
          <label for="maxima">Máxima:</label>
          <input type="number" id="maxima" v-model="tipoApuesta.maxima" />
        </div>
        <div>
          <label for="minima">Mínima:</label>
          <input type="number" id="minima" v-model="tipoApuesta.minima" />
        </div>
        <div>
          <label for="combinada">Combinada:</label>
          <input type="checkbox" id="combinada" v-model="tipoApuesta.combinada" />
        </div>
        <div>
          <label for="evento">Evento:</label>
          <select id="evento" v-model="tipoApuesta.evento" required>
            <option v-for="evento in eventos" :key="evento.id" :value="evento">{{ evento.nombre }}</option>
          </select>
        </div>
        <button type="submit">{{ isEditMode ? 'Actualizar' : 'Crear' }}</button>
        <button type="button" @click="cancelar">Cancelar</button>
      </form>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRoute, useRouter } from 'vue-router';
  import axios from 'axios';
  
  const route = useRoute();
  const router = useRouter();
  const isEditMode = ref(false);
  const tipoApuesta = ref({
    nombre: '',
    descripcion: '',
    maxima: null,
    minima: null,
    combinada: false,
    evento: null
  });

  const eventos = ref([]);

  const fetchTipoApuesta = async (id) => {
    const response = await axios.get(`/api/tipoapuestas/${id}`);
    tipoApuesta.value = response.data;
  };
  
  const submitForm = async () => {
    if (isEditMode.value) {
      await axios.put(`/api/tipoapuestas/${route.params.id}`, tipoApuesta.value);
    } else {
      await axios.post('/api/tipoapuestas', tipoApuesta.value);
    }
    router.push('/tipo-apuestas');
  };

  const cancelar = () => {
  router.push('/tipo-apuestas');
};
  
  onMounted(() => {
    axios.get('/api/eventos')
      .then(response => {
        eventos.value = response.data;
      })
      .catch(error => {
        console.error(error);
      });
    if (route.params.id) {
      isEditMode.value = true;
      fetchTipoApuesta(route.params.id);
    }
  });
  </script>
  
  <style>
  /* Add your styles here */
  </style>  