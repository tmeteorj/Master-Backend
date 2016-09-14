package bigdata.smartsafety.master.xliu.impl;

import bigdata.smartsafety.master.xliu.common.Const;
import bigdata.smartsafety.master.xliu.common.MySQLUtil;
import bigdata.smartsafety.master.xliu.model.Plane;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by xliu on 2016/9/7.
 */
@Service
public class PlaneQuery {

    public JSONObject searchByYMA(String year, String month, String attr) {
        String sql=String.format("select plane_id,lng,lat,polygon,attr from plane "+
                "where year=%s and month=%s",year,month);
        try {
            ResultSet rs = MySQLUtil.getResult("plane", sql);
            JSONArray data = new JSONArray();
            while (rs.next()) {
                Plane plane = new Plane(rs.getInt(1), rs.getDouble(2), rs.getDouble(3), rs.getString(4), rs.getString(5));
                data.add(plane.toMapJSON(attr));
            }
            JSONObject result = new JSONObject();
            result.put("data", data);
            result.put("msg", "success");
            result.put("code", 0);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Const.JSON_MYSQL_QUERY_ERROR;
        }
    }
    public JSONObject searchByPID(int pid){
        String sql=String.format("select year,month,attr from plane where plane_id=%d",pid);
        try {
            ResultSet rs = MySQLUtil.getResult("plane", sql);
            JSONArray data = new JSONArray();
            while (rs.next()) {
                Plane plane = new Plane(rs.getInt(1), rs.getInt(2),rs.getString(3));
                data.add(plane.toLineJSON());
            }
            JSONObject result = new JSONObject();
            result.put("data", data);
            result.put("msg", "success");
            result.put("code", 0);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return Const.JSON_MYSQL_QUERY_ERROR;
        }
    }

}
