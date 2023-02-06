<template>
  <div>
    <el-tabs id="box" type="border-card">
      <el-tab-pane >
        <span slot="label">本地书籍搜索</span>
        <div style="margin-top: 50px">
          <el-form :label-position="lap" label-width="100px" :model="info">
            <el-form-item  style="width:500px;margin-right: 50px; float: left">
              <el-input v-model="info.query" ></el-input>
            </el-form-item>
            <el-button @click="search" style="float: left">查询</el-button>
          </el-form>
          <el-table
            ref="tableData"
            :data="tableData"
            tooltip-effect="dark"
            style="margin:auto;width: 80%"
            >
            <el-table-column >
              <template slot-scope="scope">
                <a @click="turn" style="text-decoration:underline;" v-html="scope.row.fileName"></a>
                <h3 v-html="scope.row.content"></h3>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            background
          @current-change="handleCurrentChange"
          layout="prev, pager, next"
          :page-size="pageSize"
          :total="total">
          </el-pagination>

        </div>
      </el-tab-pane>

      <el-tab-pane >
        <span slot="label">新闻搜索</span>
        <div style="margin-top: 50px">
          <el-form :label-position="lap" label-width="100px" :model="info2">
            <el-form-item  style="width:500px;margin-right: 50px; float: left">
              <el-input v-model="info2.query" ></el-input>
            </el-form-item>
            <el-button @click="search2" style="float: left">查询</el-button>
          </el-form>
          <el-table
            ref="tableData2"
            :data="tableData2"
            tooltip-effect="dark"
            style="margin:auto;width: 80%"
          >
            <el-table-column >
              <template slot-scope="scope">
                <a @click="turn" style="text-decoration:underline;" v-html="scope.row.fileName"></a>
                <h3 v-html="scope.row.content"></h3>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            background
            @current-change="handleCurrentChange2"
            layout="prev, pager, next"
            :page-size="pageSize2"
            :total="total2">
          </el-pagination>

        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Personal",
  data(){
    return{
      lap:"right",
      info: {
        query:""
      },info2: {
        query:""
      },
      tableData:[],
      tableData2:[],
      keyword:"",
      keyword2:"",
      total:0,
      pageSize:10,
      total2:0,
      pageSize2:10
    }
  },methods:{
    search(){
      axios.post('/search_test', {
        keyword:this.info.query,
        pageNum:1
      }).then((response)=> {
        console.log(response.data);
        if(response.data==null){
          alert("暂无结果！您查询的可能是停用词！")
        }else{
          this.pageSize=response.data.pageSize;
          this.total=response.data.total;
          response.data.keywords.some((item)=>{
            this.keyword=item;
            this.changeColor(response.data.list)
          })
        }
      }).catch(function (error) {
        console.log(error);
      });
    },
    search2(){
      axios.post('/search_news', {
        keyword:this.info2.query,
        pageNum:1
      }).then((response)=> {
        console.log(response.data);
        if(response.data==null){
          alert("暂无结果！您查询的可能是停用词！")
        }else{
          this.pageSize2=response.data.pageSize;
          this.total2=response.data.total;
          response.data.keywords.some((item)=>{
            this.keyword2=item;
            this.changeColor2(response.data.list)
          })
        }
      }).catch(function (error) {
        console.log(error);
      });
    },turn(){
      this.$router.push({path: '/content'});
    },
    changeColor(result) {
      //result为接口返回的数据
      result.map((item, index) => {
        if (this.keyword) {
          /**
           * 使用正则表达式进行全文匹配关键词
           * ig : 表示 全文查找 ,忽略大小写
           *  i : 忽略大小写
           *  g : 全文查找
           *
           * 使用字符串的replace方法进行替换
           * stringObject.replace('被替换的值',替换的值)
           */
          let replaceReg = new RegExp(this.keyword, 'ig')
          let replaceString = `<span style="color: #ed4014">${this.keyword}</span>`
          result[index].fileName = item.fileName.replace(replaceReg, replaceString)
          result[index].content = item.content.replace(replaceReg, replaceString)
        }
      })
      this.tableData = result
    },
  changeColor2(result) {
    //result为接口返回的数据
    result.map((item, index) => {
      if (this.keyword2) {
        /**
         * 使用正则表达式进行全文匹配关键词
         * ig : 表示 全文查找 ,忽略大小写
         *  i : 忽略大小写
         *  g : 全文查找
         *
         * 使用字符串的replace方法进行替换
         * stringObject.replace('被替换的值',替换的值)
         */
        let replaceReg = new RegExp(this.keyword2, 'ig')
        let replaceString = `<span style="color: #ed4014">${this.keyword2}</span>`
        result[index].fileName = item.fileName.replace(replaceReg, replaceString)
        result[index].content = item.content.replace(replaceReg, replaceString)
      }
    })
    this.tableData2 = result
  },
    handleCurrentChange(currentindex){
        axios.post('/search_test', {
          keyword:this.info.query,
          pageNum:currentindex
        }).then((response)=> {
          console.log(response.data);
          if(response.data==null){
            alert("暂无结果！")
          }else{
            response.data.keywords.some((item)=>{
              this.keyword=item
              this.changeColor(response.data.list)
            })
          }
        }).catch(function (error) {
          console.log(error);
        });
    },
    handleCurrentChange2(currentindex){
      axios.post('/search_news', {
        keyword:this.info.query,
        pageNum:currentindex
      }).then((response)=> {
        console.log(response.data);
        if(response.data==null){
          alert("暂无结果！")
        }else{
          response.data.keywords.some((item)=>{
            this.keyword2=item
            this.changeColor2(response.data.list)
          })
        }
      }).catch(function (error) {
        console.log(error);
      });
    }

  }
}
</script>

<style scoped>
#box{
  margin: auto;
  width: 1000px;
  min-height: 540px;
}
</style>
