package bigdata.smartsafety.master.xliu.common;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by xliu on 2016/9/5.
 */
public class Const {

    public static String MYSQL_PROPERTIES="conf/mysql.properties";
    public static JSONObject JSON_PARAM_FORTMAT_ERROR;
    public static JSONObject JSON_MYSQL_QUERY_ERROR;
    static{
        JSON_PARAM_FORTMAT_ERROR=new JSONObject();
        JSON_PARAM_FORTMAT_ERROR.put("code",1);
        JSON_PARAM_FORTMAT_ERROR.put("msg","parameter format error");

        JSON_MYSQL_QUERY_ERROR=new JSONObject();
        JSON_MYSQL_QUERY_ERROR.put("code",2);
        JSON_MYSQL_QUERY_ERROR.put("msg","SQL query failed");
    }
    public static String response(String JSONP_CALLBACK,String result){
        return JSONP_CALLBACK+"("+result+")";
    }
    public static String response(String JSONP_CALLBACK,JSONObject js){
        return JSONP_CALLBACK+"("+js.toJSONString()+")";
    }
}
