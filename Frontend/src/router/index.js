import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import EventoList from '../components/EventoList.vue';
import EventoForm from '../components/EventoForm.vue';
import TipoApuestaList from '../components/TipoApuestaList.vue';
import TipoApuestaForm from '../components/TipoApuestaForm.vue';

const routes = [
  { 
    path: '/',
    component: Home 
  },
  { 
    path: '/eventos',
    component: EventoList 
  },
  { 
    path: '/crear-evento',
    component: EventoForm 
  },
  { 
    path: '/editar-evento/:id',
    component: EventoForm,
    props: true,
    name: 'editEvento' },
  { 
    path: '/ver-evento/:id',
    component: EventoForm,
    props: true,
    name: 'viewEvento' 
  },
  {
    path: '/tipo-apuestas',
    component: TipoApuestaList,
    name: 'TipoApuestaList',
    
  },
  {
    path: '/crear-tipo-apuesta',
    component: TipoApuestaForm,
    name: 'CreateTipoApuesta'
  },
  {
    path: '/editar-tipo-apuesta/:id',
    component: TipoApuestaForm,
    name: 'EditTipoApuesta'
  },
  {
    path: '/ver-tipo-apuesta/:id',
    component: TipoApuestaForm,
    name: 'ViewTipoApuesta'
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
