import Vue from 'vue';
import Router from 'vue-router'
import Personal from "../view/SearchEngine";
import Refresh from "../view/Refresh";
import Content from "../view/Content";

Vue.use(Router);

export default new Router({
  routes:[
    {
    path:'/personal',
    component:Personal,
    name:'Personal'
  },{
      path:'/refresh',
      component:Refresh,
      name:Refresh
    },
    {
      path:'/content',
      component:Content,
      name:Content
    },
    ]




})
