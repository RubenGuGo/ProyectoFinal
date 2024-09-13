<template>
    <div>
      <div v-for="aviso in avisos" :key="aviso.id" :class="['modal-aviso', { 'success': isSuccess.valueOf, 'error': ! isSuccess.valueOf }]">{{ aviso.mensaje }}</div>
      <table class="rounded-table">
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Máxima (€)</th>
            <th>Mínima (€) </th>
            <th>Combinada</th>
            <th>Evento</th>
            <th><button class="create-button" @click="goToCreateTipoApuesta">Crear Nuevo</button></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipoApuesta in tipoApuestas" :key="tipoApuesta.id">
            <td>{{ tipoApuesta.nombre }}</td>
            <td>{{ tipoApuesta.descripcion }}</td>
            <td>{{ tipoApuesta.maxima}}</td>
            <td>{{ tipoApuesta.minima}} </td>
            <td>{{ tipoApuesta.combinada ? 'Sí' : 'No' }}</td>
            <td>{{ tipoApuesta.evento.nombre }}</td>
            <td>
              <button class="action-button" @click="viewTipoApuesta(tipoApuesta.id)">Ver</button>
              <button class="action-button" @click="editTipoApuesta(tipoApuesta.id)">Editar</button>
              <button class="action-button" @click="confirmDelete(tipoApuesta.id)">Eliminar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
      <div v-if="showConfirmationDialog" class="confirmation-dialog">
    <div class="confirmation-dialog-content">
      <p>¿Estás seguro de que deseas realizar esta acción?</p>
      <div class="confirmation-dialog-buttons">
        <button @click="confirmSubmit" class="confirm-button">Aceptar</button>
        <button @click="cancelSubmit" class="cancel-button">Cancelar</button>
      </div>
    </div>
  </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import { useRoute, useRouter } from 'vue-router';

  const tipoApuestas = ref([]);
  const router = useRouter();
  const route = useRoute();
  const isSuccess = ref(true);
  
  const fetchTipoApuestas = async () => {
    const response = await axios.get('/api/tipoapuestas');
    tipoApuestas.value = response.data;
  };
  
  const deleteTipoApuesta = async (id) => {
  await axios.delete(`/api/tipoapuestas/${id}`);
  fetchTipoApuestas();
};

const viewTipoApuesta = (id) => {
  router.push(`/ver-tipo-apuesta/${id}?readOnly=true`);
};

const editTipoApuesta = (id) => {
  router.push(`/editar-tipo-apuesta/${id}`);
};

const goToCreateTipoApuesta = () => {
  router.push('/crear-tipo-apuesta');
};

const showConfirmationDialog = ref(false);
const isConfirming = ref(false);
const tipoApuestaIdToDelete = ref(null);

const confirmDelete = (id) => {
  tipoApuestaIdToDelete.value = id;
  showConfirmationDialog.value = true;
};

const cancelSubmit = () => {
  showConfirmationDialog.value = false;
  tipoApuestaIdToDelete.value = null;
};

const confirmSubmit = async () => {
  showConfirmationDialog.value = false;
  isConfirming.value = true;
  try {
    // Lógica para eliminar el tipoApuesta
    await deleteTipoApuesta(tipoApuestaIdToDelete.value);
    console.log('TipoApuesta eliminado');
    showAviso('Tipo de Apuesta borrado exitosamente'); // Actualizar mensaje de aviso
    checkAviso(); // Mostrar mensaje de aviso
    // Actualizar la lista de tipoApuestas después de eliminar
    tipoApuestas.value = tipoApuestas.value.filter(tipoApuesta => tipoApuesta.id !== tipoApuestaIdToDelete.value);
  } catch (error) {
    console.error('Error eliminando el tipoApuesta:', error);
    showAviso('Error al intentar borrar el tipo de apuestas'); // Actualizar mensaje de aviso
    checkAviso(); // Mostrar mensaje de aviso
  } finally {
    isConfirming.value = false;
    tipoApuestaIdToDelete.value = null;
  }
};

const avisos = ref([]);
const showAviso = (mensaje) => {
  const id = Date.now();
  avisos.value.push({ id, mensaje });
  setTimeout(() => {
    avisos.value = avisos.value.filter(aviso => aviso.id !== id);
  }, 5000); // Ocultar el mensaje después de 5 segundos
};

const checkAviso = () => {
  const mensaje = localStorage.getItem('aviso');
  if (mensaje) {
    showAviso(mensaje);
    localStorage.removeItem('aviso'); // Eliminar el mensaje de localStorage después de mostrarlo
  }
};


  onMounted(fetchTipoApuestas);
  </script>
  
  <style scoped>

#app {
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;
  font-weight: normal;
}


nav {
  position: sticky;
  z-index: 10; /* Asegura que el nav esté por encima de otros elementos */
}
 table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  margin-top: 60px; /* Añade un margen superior para evitar que la tabla se superponga con el nav */
}
table thead {
  position: sticky;
  top: 30px;
  z-index: 5; /* Asegura que la cabecera de la tabla esté por debajo del nav */
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: left;
  position: sticky;
  top: 0;
  z-index: 1;
}

td.action-button {
  display: flex;
  justify-content: center;
  align-items: center;
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

.action-button {
  border-radius: 8px;
  padding: 5px 10px;
  border: 1px solid #ccc;
  background-color: #f2f2f2;
  cursor: pointer;
  width: 80px; /* Ancho fijo */
  height: 40px; /* Altura fija */
}

.action-button:hover {
  background-color: #ddd;
}

.create-button {
  border-radius: 8px;
  padding: 10px 20px;
  border: 1px solid #ccc;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  margin-bottom: 20px;
  width: 280px; /* Ancho fijo */
  height: 40px; /* Altura fija */
}

.create-button:hover {
  background-color: #45a049;
}

.rounded-table {
  border-radius: 8px;
  overflow: hidden;
}

.confirmation-dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.confirmation-dialog-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  text-align: center;
  width: 400px;
}

.confirmation-dialog-buttons {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.confirm-button {
  padding: 10px 15px;
  background-color: #48bb78;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.confirm-button:hover {
  background-color: #38a169;
}

.cancel-button {
  padding: 10px 15px;
  background-color: #e53e3e;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s ease;
}

.cancel-button:hover {
  background-color: #c53030;
}
.modal-aviso {
  position: fixed;
  top: 85px; /* Ajusta este valor para mover los avisos más abajo */
  right: 20px; /* Cambiado a la esquina superior derecha */
  transform: none; /* Eliminado el translateX para evitar centrado */
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  margin-top: 10px;
}
.modal-aviso.success {
  background-color: #28a745; /* Verde para éxito */
}

.modal-aviso.error {
  background-color: #dc3545; /* Rojo para error */
}
  </style>