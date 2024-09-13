<script setup>
import { ref, onMounted, readonly } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const isEditMode = ref(false);
const isReadOnly = ref(route.query.readOnly === 'true');
const evento = ref({
  nombre: '',
  descripcion: '',
  pais: '',
  ciudad: '',
  direccion: '',
  fecha: '',
  horaInicio: '',
  horaFinal: '',
  tiposDeApuestas: []
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

const cancelar = () => {
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
  <h1>{{ isEditMode ? 'Editar Evento' : 'Crear Evento' }}</h1>
  <div class="form-container">
    <form @submit.prevent="submitForm">
      <div class="title">
        <label>{{ evento.nombre ?  evento.nombre : "Nuevo"}}</label>
      </div>
      <div>
        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" v-model="evento.nombre" :disabled="isReadOnly" required />
      </div>
      <div>
        <label for="descripcion">Descripción:</label>
        <input type="text" id="descripcion" v-model="evento.descripcion" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="pais">País:</label>
        <input type="text" id="pais" v-model="evento.pais" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="ciudad">Ciudad:</label>
        <input type="text" id="ciudad" v-model="evento.ciudad" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="direccion">Dirección:</label>
        <input type="text" id="direccion" v-model="evento.direccion" :disabled="isReadOnly" />
      </div>
      <div>
        <label for="fecha">Fecha:</label>
        <input type="date" id="fecha" v-model="evento.fecha" :disabled="isReadOnly" required />
      </div>
      <div>
        <label for="horaInicio">Hora Inicio:</label>
        <input type="time" id="horaInicio" v-model="evento.horaInicio" :disabled="isReadOnly" required />
      </div>
      <div>
        <label for="horaFinal">Hora Final:</label>
        <input type="time" id="horaFinal" v-model="evento.horaFinal" :disabled="isReadOnly" required />
      </div>
      <div v-if="isReadOnly">
        <label for="tiposDeApuestas">Tipos de apuestas asignados:</label>
        <ul class='tipos-de-apuestas-list'>
          <li v-for="tipo in evento.tiposDeApuestas" :key="tipo.id">{{ tipo.nombre }}</li>
        </ul>
      </div>
      <div class="button-container">
        <button type="submit" :disabled="isReadOnly" :class="['btn', 'btn-save', { 'read-only': isReadOnly }]">{{ isEditMode ? 'Actualizar' : 'Crear' }}</button>
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

label {
  display: block;
  margin-bottom: 0.5em;
  font-size: 1.2em; /* Aumenta el tamaño de letra */
}

input {
  width: 100%;
  font-size: 1.15em; /* Aumenta el tamaño de letra */
}

.title{
  text-align: center;
  font-size: 1.5em; /* Aumenta el tamaño de letra */
  font-weight: bold;
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
.tipos-de-apuestas-list {
  max-height: 8em; /* Limita la altura a 4 elementos aproximadamente */
  overflow-y: auto; /* Habilita el scroll vertical */
  border: 1px solid #ccc; /* Añade un borde para distinguir el área de scroll */
  padding: 0.5em; /* Añade padding para un mejor aspecto */
  border-radius: 4px; /* Añade bordes redondeados */
}
</style>