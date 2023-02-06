/*
 * Copyright (c) 2016 lcc523572741@qq.com
 */

package com.example.mongoex;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LuceneUtil {
    public static PageInfo query(String q, int pageNum, int pageSize) {
        Analyzer analyzer=new IKAnalyzer();
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
            if(!qus.equals("")){
                int in=qus.indexOf(':');
                System.out.println(in);
                if (qus.charAt(in+1)=='"'){
                    qus=qus.substring(in+1);
                    in=qus.indexOf("fileContent");
                    qus=qus.substring(1,in-2);
                }else {
                    qus=qus.substring(in+1);
                    in=qus.indexOf("fileContent");
                    qus=qus.substring(0,in-1);
                }

                System.out.println(qus);
                String[] queries=qus.split(" ");

                BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

                for(String qu:queries){
                    Query query1 = new TermQuery(new Term("fileName",qu));
                    Query query2 = new TermQuery(new Term("fileContent",qu));

                    booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
                    booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
                }

                BooleanQuery bq=booleanQuery.build();
                TopDocs topDocs = searcher.search(bq,(pageNum + 1) * pageSize);
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
                        if (field.name().equals("fileName")){
                            map.put(field.name(), value.substring(0,value.length()-4));
                        }else{
                            map.put(field.name(), value);
                        }
                    }

                    String path="E:\\books"+"\\"+hitDoc.get("fileName");
                    File f = new File(path);
                    String fileContent= FileUtils.readFileToString(f,"UTF-8");
                    fileContent=replaceBlank(fileContent);
                    //fileContent=fileContent.substring(0,1000);
                    String content="..."+fileContent.substring(400,450)+"...";
                    for(String qu:queries){
                        int index=fileContent.indexOf(qu);
                        if(index!=-1){
                            content+=fileContent.substring(index,index+20)+"...";
                        }
                    }
                    content.concat("...");
                    map.put("content",content);
                    result.add(map);
                }
                reader.close();
                return new PageInfo(result, pageNum, pageSize, total, 8,queries);
            }else{
                return null;
            }
            //System.out.println(JSON.toJSONString(new PageInfo(result, pageNum, pageSize, total, 8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static PageInfo queryNews(String q, int pageNum, int pageSize) {
        Analyzer analyzer=new IKAnalyzer();
        try {
            // 第一步：创建一个Directory对象，也就是索引库存放的位置。
            Directory directory = FSDirectory.open(Paths.get("E:\\LuceneProCopy\\TestIndex2"));
            // 第二步：创建一个indexReader对象，需要指定Directory对象。
            IndexReader reader = DirectoryReader.open(directory);
            // 第三步：创建一个indexsearcher对象，需要指定IndexReader对象
            IndexSearcher searcher = new IndexSearcher(reader);
            String[] fields = {"fileName","fileContent"};
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer);
            Query query = parser.parse(q);
            String qus=query.toString();
            if(!qus.equals("")){
                int in=qus.indexOf(':');
                System.out.println(in);
                if (qus.charAt(in+1)=='"'){
                    qus=qus.substring(in+1);
                    in=qus.indexOf("fileContent");
                    qus=qus.substring(1,in-2);
                }else {
                    qus=qus.substring(in+1);
                    in=qus.indexOf("fileContent");
                    qus=qus.substring(0,in-1);
                }

                System.out.println(qus);
                String[] queries=qus.split(" ");

                BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

                for(String qu:queries){
                    Query query1 = new TermQuery(new Term("fileName",qu));
                    Query query2 = new TermQuery(new Term("fileContent",qu));

                    booleanQuery.add(query1, BooleanClause.Occur.SHOULD);
                    booleanQuery.add(query2, BooleanClause.Occur.SHOULD);
                }

                BooleanQuery bq=booleanQuery.build();
                TopDocs topDocs = searcher.search(bq,(pageNum + 1) * pageSize);
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
                        if (field.name().equals("fileName")){
                            map.put(field.name(), value.substring(0,value.length()-4));
                        }else{
                            map.put(field.name(), value);
                        }
                    }

                    String path="E:\\news"+"\\"+hitDoc.get("fileName");
                    File f = new File(path);
                    String fileContent= FileUtils.readFileToString(f,"UTF-8");
                    fileContent=replaceBlank(fileContent);
                    //fileContent=fileContent.substring(0,1000);
                    String content="...";
                    boolean flag=false;
                    for(String qu:queries){
                        int index=fileContent.indexOf(qu);
                        if(index!=-1){
                            if(fileContent.length()>index+50){
                                content+=fileContent.substring(index,index+50)+"...";
                            }
                            else{
                                content+=fileContent.substring(index);
                            }
                            flag=true;
                        }
                    }
                    if(!flag){
                        if (fileContent.length()>200){
                            content+=fileContent.substring(0,200);
                        }else{
                            content=fileContent;
                        }
                    }
                    content.concat("...");
                    map.put("content",content);
                    result.add(map);
                }
                reader.close();
                return new PageInfo(result, pageNum, pageSize, total, 8,queries);
            }else{
                return null;
            }
            //System.out.println(JSON.toJSONString(new PageInfo(result, pageNum, pageSize, total, 8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String replaceBlank(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }
        return dest;
    }
}
