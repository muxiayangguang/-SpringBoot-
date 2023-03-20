package com.example.demo.controller;

import com.example.demo.codeProcess.ToFile;
import com.example.demo.process.FromDatabase;
import com.example.demo.model.Topic;
import com.example.demo.process.IdFromHtml;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
/*    Topic topic=new Topic();
    FromDatabase fromDatabase=new FromDatabase();
    topic= fromDatabase.GetQuestion(1);*/


    @RequestMapping("/hello")//http://localhost:8080//hello
    public String hello(@RequestBody String str) {
        System.out.println(str);
        return "hello world3";
    }

    @RequestMapping("/question")//http://localhost:8080//question
    public String ReturnTopic(@RequestBody String str){
        Topic topic=new Topic();
        FromDatabase fromDatabase=new FromDatabase();
        str=str.substring(3);
        //System.out.println("str:"+str);
        /*fromHtml.parseDate(str);//得到id
        int id=fromHtml.ReturnId();*/
        topic=fromDatabase.GetQuestion(Integer.parseInt(str));//得到题目
        return topic.getQuestion();
    }

    @RequestMapping("/result")//http://localhost:8080//result
    public String ReturnResult(@RequestBody String str){
        Topic topic=new Topic();
        FromDatabase fromDatabase=new FromDatabase();
        System.out.println(str);
        ToFile toFile=new ToFile();
        String result=toFile.GetResult(str);

        //topic=fromDatabase.GetQuestion(1);//得到题目
        return result;
    }

    //得到id
    @RequestMapping("/getId")//http://localhost:8080//getId
    public String GetId(@RequestBody String str) throws JSONException {
        IdFromHtml fromHtml=new IdFromHtml();
        fromHtml.parseDate(str);

        JSONObject jsonObject=new JSONObject(str);
        return jsonObject.toString();
    }

    @RequestMapping("/getCode")//http://localhost:8080//getCode
    public String GetCode(@RequestBody String str) throws JSONException {

        JSONObject jsonObject=new JSONObject(str);
        return jsonObject.toString();
    }
}