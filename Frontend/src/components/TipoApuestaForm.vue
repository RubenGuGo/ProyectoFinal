<script setup>
import { ref, onMounted, readonly } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const isEditMode = ref(false);
const isReadOnly = ref(route.query.readOnly === 'true');
const tipoApuesta = ref({
  nombre: '',
  descripcion: '',
  maxima: null,
  minima: null,
  combinada: false,
  evento: null,
});
const eventos = ref([]);

const fetchEventos = async () => {
  try {
    const response = await axios.get('/api/eventos');
    eventos.value = response.data;
  } catch (error) {
    console.error(error);
  }
};

const fetchTipoApuesta = async (id) => {
  const response = await axios.get(`/api/tipoapuestas/${id}`);
  tipoApuesta.value = response.data;
  tipoApuesta.value.evento = response.data.evento; // Asignar el id del evento
};

const submitForm = async () => {
  if (tipoApuesta.value.maxima < tipoApuesta.value.minima) {
    showModal.value = true;
    return;
  }
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

onMounted(async () => {
  await fetchEventos();
  if (route.params.id) {
    isEditMode.value = true;
    await fetchTipoApuesta(route.params.id);
  }
});
</script>

<template>
  <h1>{{ isEditMode ? tipoApuesta.nombre : "Nuevo" }}</h1>
  <div class="form-container">
    
    <form @submit.prevent="submitForm">
      <div>
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" v-model="tipoApuesta.nombre" :disabled="isReadOnly" required />
      </div>
      <div>
        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" v-model="tipoApuesta.descripcion" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="maxima">Máxima:</label>
        <input type="number" id="maxima" v-model="tipoApuesta.maxima" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="minima">Mínima:</label>
        <input type="number" id="minima" v-model="tipoApuesta.minima" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="combinada">Combinada:</label>
        <input type="checkbox" id="combinada" v-model="tipoApuesta.combinada" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="evento">Evento:</label>
        <select id="evento" v-model="tipoApuesta.evento" :disabled="isReadOnly" required class="large-select">
          <option v-for="evento in eventos" :key="evento.id" :value="evento">{{ evento.nombre }}</option>
        </select>
      </div>
      <div class="button-container">
        <button type="submit" :disabled ="isReadOnly" :class="['btn', 'btn-save', { 'read-only': isReadOnly }]">Guardar</button>
        <button type="button" @click="cancelar" class="btn btn-cancel">Volver</button>
      </div>
    </form>
  </div>
</template>



<style>

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%; 
}

h1 {
  text-align: center;
}

form {
  margin-bottom: 1em;
  width: 100%;
  max-width: 500px;
  border: 1px solid #ccc; 
  padding: 1em; 
  border-radius: 8px; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); 
}
.action-button:hover {
  background-color: #ddd;
}
label {
  display: block;
  margin-bottom: 0.5em;
}

input{
  width: 100%;
}

input[type="number"]{
  text-align: right;
}

select.large-select {
  width: 100%; /* Ajusta el ancho del select para que ocupe todo el espacio disponible */
  padding: 0.5em;
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 1em; /* Añade un espacio entre los botones */
  margin-top: 1em; /* Añade un margen superior para separar los botones del formulario */
}

.btn {
  padding: 0.5em 1em;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-save {
  background-color: #3157ff; 
  color: white;
  font-size: 1.2em; /* Aumenta el tamaño de letra */
  padding: 0.75em 1.5em; /* Ajusta el padding para aumentar el tamaño del botón */
}

.btn-save.read-only {
  background-color: #ccc; /* Color gris para el modo de solo lectura */
  cursor: not-allowed; /* Cambia el cursor para indicar que no es clicable */
}

.btn-cancel {
  background-color: #f44336; 
  color: white;
  font-size: 1.2em; /* Aumenta el tamaño de letra */
  padding: 0.75em 1.5em; /* Ajusta el padding para aumentar el tamaño del botón */
}
.action-button:hover {
  background-color: #ddd;
}
label {
  display: block;
  margin-bottom: 0.5em;
  font-size: 1.2em; /* Aumenta el tamaño de letra */
}

input {
  width: 100%;
  font-size: 1.15em; /* Aumenta el tamaño de letra */
}

input[type="number"] {
  text-align: right;
}

select.large-select {
  width: 100%; /* Ajusta el ancho del select para que ocupe todo el espacio disponible */
  padding: 0.5em;
  font-size: 1.2em; /* Aumenta el tamaño de letra */
}

.button-container {
  display: flex;
  justify-content: center;
  gap: 1em; /* Añade un espacio entre los botones */
  margin-top: 1em; /* Añade un margen superior para separar los botones del formulario */
}
</style>