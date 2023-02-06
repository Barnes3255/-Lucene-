<template>
  <div id="app">
    <el-container>
      <el-header>&nbsp;</el-header>
      <el-main><router-view></router-view></el-main>
      <el-footer>&nbsp;</el-footer>
    </el-container>

  </div>
</template>


<script>
export default {
  name: 'App',
  data(){
    return{
    }
  },
  created() {
    this.$router.push({path: '/personal'});
  }
  ,methods:{
    search(){
      this.$router.push({path: '/search'});
    }
  }
}
</script>

<style>

.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}


.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
}

</style>

























<!--<template>-->
<!--  <el-container>-->
<!--    <el-header>&nbsp;</el-header>-->
<!--    <el-main>-->
<!--      <div id="app">-->
<!--        <router-view v-on:childChange="showResult" v-bind:parent-query="query"></router-view>-->
<!--      </div>-->
<!--    </el-main>-->
<!--    <el-footer>&nbsp;</el-footer>-->
<!--  </el-container>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  name: 'app',-->
<!--  data () {-->
<!--    return {-->
<!--      query: ''-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    showResult: function(data){-->
<!--      this.query=data-->
<!--      console.log(this.query)-->
<!--      this.$router.replace({path:'/result'})-->
<!--    }-->
<!--  }-->
<!--}-->
<!--</script>-->

<!--<style>-->


<!--</style>-->
