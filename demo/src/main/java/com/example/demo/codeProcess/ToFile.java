package com.example.demo.codeProcess;

import javafx.scene.shape.HLineTo;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.io.*;

public class ToFile {
    //清空并写入
    public void WriteStringToFile(String filePath,String str) {
        try {
            File file =new File(filePath);
            if(file.exists()) {
                file.delete();
            }
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);

            //bw.append("在已有的基础上添加字符串");
            bw.write(str);// 往已有的文件上添加字符串

            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void JavaCompile(String command){
        Runtime rt = Runtime.getRuntime();
        try {
            Process process = rt.exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
            while((line  = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitValue = process.waitFor();
            System.out.println("进程返回值：" + exitValue);
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String GetReturn(String cmd) {
        // CMD 执行命令
        //String cmd = "cmd /c java -cp C:\\Users\\18363\\Desktop\\testJava HelloJava" ;
        String result = "";
        try {
            String line=null;
            // 执行 CMD 命令
            Process process = Runtime.getRuntime().exec(cmd);
            // 从输入流中读取文本
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // 循环读取
            while ((line = reader.readLine()) != null) {
                // 循环写入
                result=result+line+"\n";
                System.out.println("line:"+line+"\n");
            }
            // 关闭输出流
            process.getOutputStream().close();

            System.out.println("程序执行完毕!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result:"+result);
        return result;
    }


    public String ToASCII(String str){
        StringToHex stringToHex=new StringToHex();
        //System.out.println("替换前："+str);
        char[] chars = str.toCharArray();
        for(int i=0;i < chars.length;i++) {
            if(chars[i]=='%'){
                String str1="";
                str1=str1.concat(String.valueOf(chars[i+1]));
                str1=str1.concat(String.valueOf(chars[i+2]));
                str1=stringToHex.convertHexToString(str1);//转换后的字符
                StringBuilder sb=new StringBuilder(str);
                sb.replace(i,i+3,str1);//替换
                str=String.valueOf(sb);
                chars = str.toCharArray();//刷新
                //i=i+1;
            }
        }
        //System.out.println("替换后:"+str);
        return str;
    }


    public String[] GetSeparate(String str){
        //StringToHex stringToHex=new StringToHex();
        //Long.parseLong("473",  16);
        //StringUtils.substringBefore("“dskeabcee”", "“e”");
        String[] the_split=str.split("&");
        the_split[0]=the_split[0].substring(9);
        the_split[1]=the_split[1].substring(5);
        the_split[2]=the_split[2].substring(6);
        the_split[1]=ToASCII(the_split[1]);//ASCII字符替换
        the_split[1]=ToASCII(the_split[1]);
        the_split[2]=ToASCII(the_split[2]);
        //System.out.println("parse后:"+the_split[1]);

        return the_split;
    }

    public static String readFile(String pathname) {
        String readMes="";
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                readMes=readMes+line;
                // 一次读入一行数据
                //System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readMes;
    }

    public void DeletFile(String filePath){
        File file =new File(filePath);
        if(file.exists()) {
            file.delete();
        }
    }

    public String GetResult(String str){
        String result="";
        String filename="C:\\Users\\18363\\Desktop\\demo\\testJava\\";
        String[] the_split=GetSeparate(str);//分离

        String command4="cmd /c cd C:\\Users\\18363\\Desktop\\demo\\testJava";
        String command5="cmd /c test.exe<input.txt";

        ToFile toFile=new ToFile();
        //删除.exe和erro
        DeletFile("C:\\Users\\18363\\Desktop\\demo\\testJava\\test.exe");
        DeletFile("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
        toFile.WriteStringToFile(filename+"input.txt",the_split[2]);
        switch (the_split[0]){
            case "cpp":
                //写入程序
                toFile.WriteStringToFile(filename+"test.cpp",the_split[1]);
                //编译+执行程序
                //cpp
                result=result+toFile.GetReturn("cmd /c g++ C:\\Users\\18363\\Desktop\\demo\\testJava\\test.cpp -o " +
                        "C:\\Users\\18363\\Desktop\\demo\\testJava\\test 2>C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");//c++编译，exe输出到指定目录
                File file1 =new File("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
                //如果编译出错了，读取错误信息
                if(file1.exists()) {
                    result=result+readFile("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
                }
                result=result+toFile.GetReturn(command4+"&"+command5);//C++执行
                break;
            case "c":
                toFile.WriteStringToFile(filename+"test.c",the_split[1]);
                //c
                result=result+toFile.GetReturn("cmd /c gcc C:\\Users\\18363\\Desktop\\demo\\testJava\\test.c -o " +
                        "C:\\Users\\18363\\Desktop\\demo\\testJava\\test.exe 2>C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");//c编译
                File file2 =new File("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
                //如果编译出错了，读取错误信息
                if(file2.exists()) {
                    result=result+readFile("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
                }
                result=result+toFile.GetReturn(command4+"&"+command5);//C++执行
                break;
            case "java":
                int index1=the_split[1].indexOf("class ")+6;
                int index2=the_split[1].indexOf("{");
                String stringClass=the_split[1].substring(index1,index2-1);
                DeletFile("C:\\Users\\18363\\Desktop\\demo\\testJava\\"+stringClass+".class");
                //System.out.println(stringClass);
                toFile.WriteStringToFile(filename+stringClass+".java",the_split[1]);
                String javaCommand1="cmd /c javac C:\\Users\\18363\\Desktop\\demo\\testJava\\"+stringClass+
                        ".java 2>C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt";//编译
                String javaCommand2="cmd /c java -cp C:\\Users\\18363\\Desktop\\demo\\testJava "+stringClass+
                        "<C:\\Users\\18363\\Desktop\\demo\\testJava\\input.txt" ;//执行

                result=result+toFile.GetReturn(javaCommand1);//java编译
                result=result+toFile.GetReturn(javaCommand2);
                File file3 =new File("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
                if(file3.exists()) {
                    result=result+readFile("C:\\Users\\18363\\Desktop\\demo\\testJava\\error.txt");
                }
                break;
                //str.indexOf()
                //toFile.WriteStringToFile(filename+);
        }
        return result;
    }
}
