package xyz.zhenhua.smartcom.xmlparse;

/**
 * Created by zachary on 16/8/30.
 */

public class ParseSendData {
    public String packData(String username,String title,String buf,String token,double x,double y){
        String result = "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+
                "<mess>"+
                    "<username>"+username+"</username>"+
                    "<title>"+title+"</title>"+
                    "<buf>"+buf+"</buf>"+
                    "<north>"+x+"</north>"+
                    "<east>"+y+"</east>"+
                    "<token>"+token+"</token>"+
                "</mess>";
        return result;
    }
}
