<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const isSuccess = ref(true);
const eventos = ref([]);
const showConfirmationDialog = ref(false);
const isConfirming = ref(false);
const eventoIdToDelete = ref(null);
const eventoNombreToDelete = ref('');
const confirmationMessage = ref('');
const hasTiposDeApuestas = ref(false);

const fetchEventos = async () => {
  const response = await axios.get('/api/eventos');
  eventos.value = response.data;
};

const deleteEvento = async (id) => {
  await axios.delete(`/api/eventos/${id}`);
  fetchEventos();
};

const confirmDelete = (id, nombre, tiposDeApuestas) => {
  if (tiposDeApuestas && tiposDeApuestas.length > 0) {
    confirmationMessage.value = `No se puede eliminar el evento "${nombre}" porque tiene tipos de apuestas asignados.`;
    hasTiposDeApuestas.value = true;
  } else {
    confirmationMessage.value = `¿Estás seguro de que deseas eliminar el evento "${nombre}"? Esta acción no se puede deshacer.`;
    hasTiposDeApuestas.value = false;
  }
  eventoIdToDelete.value = id;
  eventoNombreToDelete.value = nombre;
  showConfirmationDialog.value = true;
};

const cancelSubmit = () => {
  showConfirmationDialog.value = false;
  eventoIdToDelete.value = null;
  eventoNombreToDelete.value = '';
  confirmationMessage.value = '';
  hasTiposDeApuestas.value = false;
};

const confirmSubmit = async () => {
  showConfirmationDialog.value = false;
  isConfirming.value = true;
  try {
    await deleteEvento(eventoIdToDelete.value);
    console.log('Evento eliminado');
    isSuccess.value=true;
    showAviso('Evento borrado exitosamente'); // Actualizar mensaje de aviso
    checkAviso(); // Mostrar mensaje de aviso
    eventos.value = eventos.value.filter(evento => evento.id !== eventoIdToDelete.value);
  } catch (error) {
    console.error('Error eliminando el evento:', error);
    isSuccess.value = false;
    showAviso('Error al intentar borrar el evento'); // Actualizar mensaje de aviso
  } finally {
    
    isConfirming.value = false;
    eventoIdToDelete.value = null;
    eventoNombreToDelete.value = '';
    confirmationMessage.value = '';
    hasTiposDeApuestas.value = false;
  }
};

const navigateToCrearEvento = () => {
  router.push('/crear-evento');
};

const navigateToVerEvento = (id) => {
  router.push(`/ver-evento/${id}?readOnly=true`);
};

const navigateToEditarEvento = (id) => {
  router.push(`/editar-evento/${id}`);
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


onMounted(fetchEventos);
</script>

<template>
  <div>
    <div v-for="aviso in avisos" :key="aviso.id" :class="['modal-aviso', { 'success': isSuccess.valueOf, 'error': ! isSuccess.valueOf }]">{{ aviso.mensaje }}</div>
    <table class="rounded-table">
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
          <th><button class="create-button" @click="navigateToCrearEvento()">Crear Nuevo</button></th>
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
          <td>{{ evento.tiposDeApuestas ? evento.tiposDeApuestas.length : 0 }}</td>
          <td>
            <button class="action-button" @click="navigateToVerEvento(evento.id)">Ver</button>
            <button class="action-button" @click="navigateToEditarEvento(evento.id)">Editar</button>
            <button class="action-button" @click="confirmDelete(evento.id, evento.nombre, evento.tiposDeApuestas)">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
    <div v-if="showConfirmationDialog" class="confirmation-dialog">
      <div class="confirmation-dialog-content">
        <p>{{ confirmationMessage }}</p>
        <div class="confirmation-dialog-buttons">
          <button v-if="!hasTiposDeApuestas" @click="confirmSubmit" class="confirm-button">Aceptar</button>
          <button @click="cancelSubmit" class="cancel-button">Cancelar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style>


#app {
  max-width: 1280px;
  margin: 0 auto;
  padding: 2rem;
  font-weight: normal;
}

nav {
  position: sticky;
  z-index: 10;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  margin-top: 60px;
}
table thead {
  position: sticky;
  top: 30px;
  z-index: 5;
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
  width: 80px;
  height: 40px;
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
  width: 280px;
  height: 40px;
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