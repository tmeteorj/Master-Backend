package bigdata.smartsafety.master.xliu.service;

import bigdata.smartsafety.master.xliu.common.Const;
import bigdata.smartsafety.master.xliu.common.ParamCheck;
import bigdata.smartsafety.master.xliu.impl.PlaneQuery;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.IntegerCodec;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xliu on 2016/9/5.
 */

@RestController
@RequestMapping("/plane")
public class PlaneController {
    private static Logger logger=Logger.getLogger(PlaneController.class);

    @Autowired
    private PlaneQuery planeQuery;

    @RequestMapping("/say")
    String say(@RequestParam(value="word",defaultValue = "world",required = false) String word){
        return "hello "+word;
    }

    @RequestMapping("/search-by-yma")
    String searchByYMA(
            @RequestParam(value="year",required = false) String year,
            @RequestParam(value="month",required = false) String month,
            @RequestParam(value="attr",required = false) String attr,
            @RequestParam(value="callback",required = false) String callback){
        if(!ParamCheck.isInteger(year)||!ParamCheck.isInteger(month))
            return  Const.response(callback,Const.JSON_PARAM_FORTMAT_ERROR);
            //return  Const.JSON_PARAM_FORTMAT_ERROR;
        return Const.response(callback,planeQuery.searchByYMA(year,month,attr));
        //return planeQuery.searchByYMA(year,month,attr);
    }

    @RequestMapping("/search-by-pid")
    String searchByPID(
            @RequestParam(value="pid",required = false) String pid,
            @RequestParam(value="callback",required = false) String callback){
        if(!ParamCheck.isInteger(pid))return  Const.response(callback,Const.JSON_PARAM_FORTMAT_ERROR);
        return Const.response(callback,planeQuery.searchByPID(Integer.parseInt(pid)));
    }
}
