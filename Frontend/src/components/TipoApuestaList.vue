<template>
    <div>
      <h1>Tipos de Apuestas</h1>
      <router-link to="/crear-tipo-apuesta">Crear Tipo de Apuesta</router-link>
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Máxima</th>
            <th>Mínima</th>
            <th>Combinada</th>
            <th>Acciones</th>
            <th>Evento</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipoApuesta in tipoApuestas" :key="tipoApuesta.id">
            <td>{{ tipoApuesta.nombre }}</td>
            <td>{{ tipoApuesta.descripcion }}</td>
            <td>{{ tipoApuesta.maxima }}</td>
            <td>{{ tipoApuesta.minima }}</td>
            <td>{{ tipoApuesta.combinada ? 'Sí' : 'No' }}</td>
            <td>{{ tipoApuesta.evento.nombre }}</td>
            <td>
              <router-link :to="`/ver-tipo-apuesta/${tipoApuesta.id}`">Ver</router-link>
              <router-link :to="`/editar-tipo-apuesta/${tipoApuesta.id}`">Editar</router-link>
              <button @click="deleteTipoApuesta(tipoApuesta.id)">Eliminar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  
  const tipoApuestas = ref([]);
  
  const fetchTipoApuestas = async () => {
    const response = await axios.get('/api/tipoapuestas');
    tipoApuestas.value = response.data;
  };
  
  const deleteTipoApuesta = async (id) => {
    await axios.delete(`/api/tipoapuestas/${id}`);
    fetchTipoApuestas();
  };
  
  onMounted(fetchTipoApuestas);
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