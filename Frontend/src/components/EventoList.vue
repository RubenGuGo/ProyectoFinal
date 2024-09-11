<template>
  <div>
    <h1>Eventos</h1>
    <router-link to="/crear-evento  ">Crear Evento</router-link>
    <table>
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>País</th>
          <th>Ciudad</th>
          <th>Dirección</th>
          <th>Fecha</th>
          <th>Hora Inicio</th>
          <th>Hora Final</th>
          <th>Tipos de apuestas</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="evento in eventos" :key="evento.id">
          <td>{{ evento.nombre }}</td>
          <td>{{ evento.descripcion }}</td>
          <td>{{ evento.pais }}</td>
          <td>{{ evento.ciudad }}</td>
          <td>{{ evento.direccion }}</td>
          <td>{{ evento.fecha }}</td>
          <td>{{ evento.horaInicio }}</td>
          <td>{{ evento.horaFinal }}</td>
          <td>{{ evento.tipoApuestas ? evento.tipoApuestas.length : 0 }}</td>
          <td>
            <router-link :to="`/crear-evento/${evento.id}`">Ver</router-link>
            <router-link :to="`/editar-evento/${evento.id}`">Editar</router-link>
            <button @click="deleteEvento(evento.id)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const eventos = ref([]);

const fetchEventos = async () => {
  const response = await axios.get('/api/eventos');
  eventos.value = response.data;
};

const deleteEvento = async (id) => {
  await axios.delete(`/api/eventos/${id}`);
  fetchEventos();
};

onMounted(fetchEventos);
</script>

<style>
table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

tr:hover {
  background-color: #ddd;
}

button {
  margin-left: 10px;
}
</style>