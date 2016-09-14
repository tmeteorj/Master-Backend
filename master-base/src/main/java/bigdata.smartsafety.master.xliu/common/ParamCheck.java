package bigdata.smartsafety.master.xliu.common;

/**
 * Created by xliu on 2016/9/7.
 */
public class ParamCheck {
    public static boolean isInteger(String s){
        try{
            if(isEmpty(s))return false;
            Integer.parseInt(s);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean isEmpty(String s){
        return s==null||s.length()==0;
    }
}
