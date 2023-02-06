package com.example.mongoex.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.mongoex.LuceneUtil;
import com.example.mongoex.PageInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@CrossOrigin
@RestController
public class TestController {
    @RequestMapping(value = "/search_test",method = RequestMethod.POST)
    public String search_student(HttpServletRequest req) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String s = reader.readLine();
        System.out.println(s);
        JSONObject oo= JSON.parseObject(s);
        String keyword=JSON.toJSONString(oo.get("keyword"));
        String pageNum=JSON.toJSONString(oo.get("pageNum"));
        System.out.println(keyword+pageNum);
        PageInfo pageInfo = LuceneUtil.query(keyword,Integer.valueOf(pageNum), 10);

        return JSON.toJSONString(pageInfo);

    }
}
