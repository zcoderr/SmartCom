package xyz.zhenhua.smartcom.xmlparse;

/**
 * Created by zachary on 16/8/30.
 */

public class ParseUser {
    public static String PackUser(String username,String  password){
        String result =
                "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+
                        "<User>"+
                            "<username>"+username+"</username>"+
                            "<password>"+password+"</password>"+
                        "</User>";
        return result;
    }
    public String PackLogout(String username,String key){
        String result =
                "<?xml version=\"1.0\" encoding=\"GB2312\"?>"+
                        "<mess>"+
                            "<username>"+username+"</username>"+
                            "<token>"+key+"</token>"+
                        "</mess>";
        return result;
    }

    public String PackRegister(String username,String password){
        return "";
    }
}
