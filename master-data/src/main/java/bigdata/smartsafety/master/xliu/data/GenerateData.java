package bigdata.smartsafety.master.xliu.data;

import bigdata.smartsafety.master.xliu.common.GeoUtil;
import bigdata.smartsafety.master.xliu.common.MySQLUtil;
import bigdata.smartsafety.master.xliu.model.Plane;
import bigdata.smartsafety.master.xliu.model.Point;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xliu on 2016/9/7.
 */
public class GenerateData {
    private static Logger logger=Logger.getLogger(GenerateData.class);
    public static JSONObject makeAttr(String type){
        JSONObject js = new JSONObject();
        js.put("Type", type);
        js.put("Area", Math.random() * 10000);
        js.put("Population", Math.random() * 10000);
        js.put("CallOutCnt", Math.random() * 10000);
        js.put("CallInCnt", Math.random() * 10000);
        js.put("CallOutInter", Math.random() * 10000);
        js.put("CallInInter", Math.random() * 10000);
        js.put("CallInter", Math.random() * 10000);
        js.put("MessOutCnt", Math.random() * 10000);
        js.put("MessInCnt", Math.random() * 10000);
        js.put("MessOutInter", Math.random() * 10000);
        js.put("MessInInter", Math.random() * 10000);
        js.put("MessInter", Math.random() * 10000);
        js.put("CallDegree", Math.random() * 10000);
        js.put("MessDegree", Math.random() * 10000);
        js.put("CallComCnt", Math.random() * 10000);
        js.put("MessComCnt", Math.random() * 10000);
        js.put("CallMessNMI", Math.random());
        js.put("CallEntropy", Math.random());
        js.put("MessEntropy", Math.random());
        return js;
    }
    public static void GenTJU(int pid) throws SQLException {
        List<Point> list = new ArrayList<Point>();
        list.add(new Point(117.168705, 39.114261));
        list.add(new Point(117.179112, 39.114228));
        list.add(new Point(117.178941, 39.106186));
        list.add(new Point(117.165294, 39.106003));
        list.add(new Point(117.165272, 39.109849));
        list.add(new Point(117.166731, 39.109966));
        list.add(new Point(117.166839, 39.111697));
        list.add(new Point(117.16862, 39.11183));
        for (int year = 2014; year <= 2015; year++) {
            for (int month = 1; month <= 12; month++) {
                Plane pl = new Plane(year, month, 117.172762, 39.111031);
                pl.setPolygon(list);
                pl.setAttr(makeAttr(randomType()));
                String sql = String.format("insert into plane(plane_id,year,month,lng,lat,polygon,attr) values(%d,%s)", pid, pl.toInsertSQLString());
                MySQLUtil.updateResult("plane", sql);
            }
        }
    }
    private static String [] typeArray=new String[]{"Storage Facility,1","News Facility,2","Library Facility,3",
            "Adult Education Facility,4","Sports Facility,5","Hotel Facility,6","Military Facility,7",
            "Middle Education Facility,8","Office Facility,9","Logistic Facility,10","Traffic Facility,11",
            "Entertainment Facility,12","Service Facility,13","Higher Education Facility,14",
            "Medical Facility,15","Industry Facility,16","Research Facility,17","Primary Education Facility,18",
            "Government Facility,19","Public Facility,20","Financial Facility,21","Residential Facility,22"};
    private static String randomType() {
        return typeArray[(int)(Math.random()*22)];
    }


    public static void genOne(double sx,double sy,int pid) throws SQLException {
        List<Point> org=new ArrayList<>();
        org.add(new Point(sx,sy));
        for(int i=0;i<20;i++){
            sx+=Math.random()/500.0-0.001;
            sy+=Math.random()/500.0-0.001;
            org.add(new Point(sx,sy));
        }
        org=GeoUtil.convexHull(org);
        sx=0;
        sy=0;
        for(Point p:org){
            sx+=p.lng;
            sy+=p.lat;
        }
        sx/=org.size();
        sy/=org.size();
        for(int year=2014;year<=2015;year++) {
            for (int month = 1; month <= 12; month++) {
                Plane pl = new Plane(year, month, sx,sy);
                pl.setPolygon(org);
                pl.setAttr(makeAttr(randomType()));
                String sql = String.format("insert into plane(plane_id,year,month,lng,lat,polygon,attr) values(%d,%s)", pid, pl.toInsertSQLString());
                MySQLUtil.updateResult("plane", sql);
            }
        }
    }

    public static void genAll(double x1,double y1,double x2,double y2,int start,int end) throws SQLException {
        double dx=x2-x1;
        double dy=y2-y1;

        for(int i=start;i<end;i++){
            if(i==1){
                GenTJU(1);
            }else {
                genOne(x1 + Math.random() * dx, y1 + Math.random() * dy, i);
            }
        }
    }

    public static void main(String args[]) throws SQLException {
        genAll(117.10,39.00,117.35,39.30,101,1000);
    }
}
