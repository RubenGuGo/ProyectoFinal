<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
    <div class="wrapper">
      
      <nav class="navbar">
        <router-link to="/">Inicio</router-link>
        <router-link to="/eventos">Eventos</router-link>
        <router-link to="/tipo-apuestas">Tipos de Apuestas</router-link>
      </nav>
    </div>
  </header>

  <RouterView />
</template>

<style scoped>
.active {
  font-weight: bold;
  color: #b82d2d;
}



.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 40px; /* Aumenta la altura de la barra de navegación */
  display: flex;
  align-items: center;
  justify-content: space-around;
  background-color: #333;
  color: white;
  font-size: 1.2rem; /* Aumenta el tamaño de la fuente */
  padding: 0 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.navbar a {
  color: white;
  text-decoration: none;
  padding: 10px 20px;
}

.navbar a:hover {
  background-color: #444;
  border-radius: 5px;
}

.main-content {
  margin-top: 80px; /* Ajusta el margen superior para evitar que el contenido se superponga con la barra de navegación */
  padding: 20px;
}
</style>