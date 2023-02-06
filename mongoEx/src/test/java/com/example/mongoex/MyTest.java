package com.example.mongoex;


import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;



public class MyTest {


    public MyTest() throws IOException {
    }

    @Test
    public void test1() throws IOException {
        Analyzer analyzer=new IKAnalyzer();
        Directory directory = FSDirectory.open(Paths.get("E:\\LuceneProCopy\\TestIndex"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        indexWriter.deleteAll();

        File f = new File("E:\\books");
        File[] listFiles = f.listFiles();
        if(listFiles != null){
            for (File file : listFiles) {
                if(file.isFile()){
                    // 第三步：创建document对象, 并把文件信息添加到document对象中
                    Document document = new Document();
                    // 文件名称
                    String file_name = file.getName();
                    Field fileNameField = new TextField("fileName", file_name, Field.Store.YES);
                    // 文件路径
                    String file_path = file.getPath();
                    Field filePathField = new StoredField("filePath", file_path);

                    // 文件大小
                    long file_size = FileUtils.sizeOf(file);
                    //索引
                    Field fileSizeField1 = new LongPoint("fileSize", file_size);
                    //存储
                    Field fileSizeField2 = new StoredField("fileSize", file_size);

                    // 文件内容
                    String file_content = FileUtils.readFileToString(file, "UTF-8");
                    Field fileContentField = new TextField("fileContent", file_content, Field.Store.NO);

                    document.add(fileNameField);
                    document.add(fileSizeField1);
                    document.add(fileSizeField2);
                    document.add(filePathField);
                    document.add(fileContentField);
                    // 第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
                    indexWriter.addDocument(document);
                }
            }
            // 第五步：关闭IndexWriter对象。
            indexWriter.close();
        }
    }
    @Test
    public void test2() throws IOException {
        Analyzer analyzer=new IKAnalyzer();
        Directory directory = FSDirectory.open(Paths.get("E:\\LuceneProCopy\\TestIndex2"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config);

        indexWriter.deleteAll();

        File f = new File("E:\\news");
        File[] listFiles = f.listFiles();
        if(listFiles != null){
            for (File file : listFiles) {
                if(file.isFile()){
                    // 第三步：创建document对象, 并把文件信息添加到document对象中
                    Document document = new Document();
                    // 文件名称
                    String file_name = file.getName();
                    Field fileNameField = new TextField("fileName", file_name, Field.Store.YES);
                    // 文件路径
                    String file_path = file.getPath();
                    Field filePathField = new StoredField("filePath", file_path);

                    // 文件大小
                    long file_size = FileUtils.sizeOf(file);
                    //索引
                    Field fileSizeField1 = new LongPoint("fileSize", file_size);
                    //存储
                    Field fileSizeField2 = new StoredField("fileSize", file_size);

                    // 文件内容
                    String file_content = FileUtils.readFileToString(file, "UTF-8");
                    Field fileContentField = new TextField("fileContent", file_content, Field.Store.NO);

                    document.add(fileNameField);
                    document.add(fileSizeField1);
                    document.add(fileSizeField2);
                    document.add(filePathField);
                    document.add(fileContentField);
                    // 第四步：使用indexwriter对象将document对象写入索引库，此过程进行索引创建。并将索引和document对象写入索引库。
                    indexWriter.addDocument(document);
                }
            }
            // 第五步：关闭IndexWriter对象。
            indexWriter.close();
        }
    }
    @Test
    public void testSearch() throws IOException, ParseException {
        Analyzer analyzer=new IKAnalyzer();
        String q="种植蔬菜";
        // 第一步：创建一个Directory对象，也就是索引库存放的位置。
        Directory directory = FSDirectory.open(Paths.get("E:\\LuceneProCopy\\TestIndex2"));
        // 第二步：创建一个indexReader对象，需要指定Directory对象。
        IndexReader indexReader = DirectoryReader.open(directory);
        // 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        // 第四步：创建一个TermQuery对象，指定查询的域和查询的关键词。
        String[] fields = {"fileName","fileContent"};
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        Query query = parser.parse(q);
//
//        Query query = new TermQuery(new Term("fileContent", "种植"));
        // 第五步：执行查询。
        TopDocs topDocs = indexSearcher.search(query, 100);
        // 第六步：返回查询结果。遍历查询结果并输出。
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            int doc = scoreDoc.doc;
            Document document = indexSearcher.doc(doc);
            // 文件名称
            String fileName = document.get("fileName");
            System.out.println(fileName);
            // 文件内容
            String fileContent = document.get("fileContent");
            System.out.println(fileContent);
            // 文件大小
            String fileSize = document.get("fileSize");
            System.out.println(fileSize);
            // 文件路径
            String filePath = document.get("filePath");
            System.out.println(filePath);
            System.out.println("------------");
        }
        // 第七步：关闭IndexReader对象
        indexReader.close();
    }
    @Test
    public void query() {
        Analyzer analyzer=new IKAnalyzer();
        String q="奇怪的坏人";
        int pageNum=1;
        int pageSize=10;
        try {
            // 第一步：创建一个Directory对象，也就是索引库存放的位置。
            Directory directory = FSDirectory.open(Paths.get("E:\\LuceneProCopy\\TestIndex"));
            // 第二步：创建一个indexReader对象，需要指定Directory对象。
            IndexReader reader = DirectoryReader.open(directory);
            // 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
            IndexSearcher searcher = new IndexSearcher(reader);
            String[] fields = {"fileName","fileContent"};
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
            Query query = parser.parse(q);
            String qus=query.toString();
            System.out.println(qus);
            String[] queries=qus.split("\\)");
            TopDocs topDocs = searcher.search(query,  (pageNum + 1) * pageSize);
            ScoreDoc[] hits = topDocs.scoreDocs;
            Long total = topDocs.totalHits;
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();
            int start = (pageNum - 1) * pageSize;
            int end = start + pageSize;
            for (int i = start; i < end && i < total; i++) {
                Map<String, String> map = new HashMap<String, String>();
                Document hitDoc = searcher.doc(hits[i].doc);
                for (Iterator<IndexableField> iter = hitDoc.iterator(); iter.hasNext(); ) {
                    IndexableField field = iter.next();
                    String value = field.stringValue();
                    map.put(field.name(), value);
                }

                String path="E:\\books"+"\\"+hitDoc.get("fileName");
                File f = new File(path);
                String fileContent=FileUtils.readFileToString(f,"UTF-8");
                //fileContent=fileContent.substring(0,1000);
                String content="..."+fileContent.substring(50,80)+"...";
                for(String qu:queries){
                    int ind=qu.lastIndexOf(':');
                    qu=qu.substring(ind+1);
                        int index=fileContent.indexOf(qu);
                        if(index!=-1){
                            content=content.concat(fileContent.substring(index,index+20));
                            content.concat("...");
                        }
                }
                if(content.length()>201){
                    content=content.substring(0,200);
                }
                map.put("content",content);
                result.add(map);
            }
            reader.close();
            //return new PageInfo(result, pageNum, pageSize, total, 8);
            System.out.println(JSON.toJSONString(new PageInfo(result, pageNum, pageSize, total, 8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
