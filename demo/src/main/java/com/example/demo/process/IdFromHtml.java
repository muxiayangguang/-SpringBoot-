package com.example.demo.process;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.demo.model.Number;

public class IdFromHtml {
    Number number=new Number();
    static int idList;

    public void parseDate(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            number.setId(Integer.parseInt(jsonObject.getString("id")));
            //System.out.print("解析的信息1：");
            //System.out.println(setStatus.getOnOrOff());
            //System.out.println("111"+alarm.getAlarmInformation());
            //Log.i("qin",alarm.getAlarmInformation());
            //parseStr=parseStr+jsonObject.getString("alarmInformation");

            idList = number.getId();
            //System.out.println("首个need: "+controlList[2]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int ReturnId(){
/*        for(int i=0;i<needList.length;i++){
            System.out.print(needList[i]+"  ");
        }*/
        return idList;
    }
}
