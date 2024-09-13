<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();
const navigateToEvento = () => {
  router.push('/eventos');
};

const navigateToTipoApuestas = () => {
  router.push('/tipo-apuestas');
};

</script>



<template>
  <main class="inicio background-image">
    <div class="content">
      <h1>Bienvenido a Apuestas Deportivas</h1>
      <p>Descubre la emoción de apostar en tus deportes favoritos con nosotros. Ofrecemos una amplia gama de opciones de apuestas para que puedas disfrutar de cada momento del juego.</p>
      <div class="button-container">
        <a href="https://pnsd.sanidad.gob.es/noticiasEventos/actualidad/2019_Actualidadpublica/pdf/2019_CEAPA_Ludopatia.pdf" class="btn btn-green">Regístrate Ahora</a>
        <button @click="navigateToEvento()">Explora Eventos</button>
        <button @click="navigateToTipoApuestas()">Explora Tipos de Apuestas</button>
      </div>
    </div>
  </main>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

html, body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

.inicio {
  height: 100vh;
  width: 100%;
  margin: 0;
  padding: 0;
  display: flex; /* Utiliza flexbox para centrar el contenido */
  justify-content: center; /* Centra el contenido horizontalmente */
  align-items: center; /* Centra el contenido verticalmente */
  font-family: 'Roboto', sans-serif; /* Aplica la fuente Roboto */
  color: white;
  text-align: center;
  overflow: hidden; /* Evita el scroll innecesario */
}

.background-image {
  background-image: url('/apuestas-deportivas.png');
  background-size: cover;
  background-position: center;
  width: 100%;
  height: 100vh; /* O usa 100% si quieres que ocupe todo el contenedor */
  background-repeat: no-repeat; /* Evita que la imagen se repita */

}

.content {
  background: rgba(255, 255, 255, 0.5); /* Fondo semitransparente para mejorar la legibilidad */
  padding: 20px;
  border-radius: 15px;
}

.button-container {
  margin-top: 20px;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  text-decoration: none;
  font-size: 16px;
  cursor: pointer;
}

.btn-green {
  background-color: #4CAF50;
  color: white;
}

.btn-green:hover {
  background-color: #45a049;
}

button {
  background-color: #ff5722;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #e64a19;
}
</style>