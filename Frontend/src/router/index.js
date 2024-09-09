import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import EventoList from '../components/EventoList.vue';
import EventoForm from '../components/EventoForm.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/eventos', component: EventoList },
  { path: '/create', component: EventoForm },
  { path: '/edit/:id', component: EventoForm, props: true, name: 'editEvento' },
  { path: '/view/:id', component: EventoForm, props: true, name: 'viewEvento' }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
