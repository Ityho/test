package com.miduchina.wrd.api.casebase.commons;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Dwr
 * @Description TODO
 * @Date 2019/6/21 17:50
 * @Author ZhuFangTao
 */
public class Dwr {
    public long getYuanSize(long lo,long num){
        if(num==0){
            return 15;
        }
        long a=lo*80/num;
        long c=a/2;
        if(c<15){
            c=15;
        }
        if(c>30){
            c=30;
        }
        return c;
    }
    public List<String> getAssociation(String[] str){
        List<String> li=new ArrayList<String>();
        String[] xy1={"40,30","60,75","40,75","60,30","50,20","50,92"};
        String[] xy2={"50,-8","30,100","40,115","70,0","70,100","85,30","85,60","20,15","15,45","20,75"};
        String[] xy3={"0,30","100,75","10,0","95,0","0,75","96,100","10,105","100,30","-5,50","105,50"};
        List<String> list1=new ArrayList<String>();
        List<String> list2=new ArrayList<String>();
        List<String> list3=new ArrayList<String>();
        for(int i=0;i<xy1.length;i++){
            list1.add(xy1[i]);
        }
        for(int i=0;i<xy2.length;i++){
            list2.add(xy2[i]);
        }
        for(int i=0;i<xy3.length;i++){
            list3.add(xy3[i]);
        }

        for(int i=0;i<str.length;i++){
            String st=str[i];
            if(null!=st){
                StringBuilder sb2 = new StringBuilder();
                sb2.append("{");
                String[] split = st.split(",");
                String[] xyArr = new String[2];
                double a=Double.parseDouble(split[0]);
                if(i<3){
                    int index = (int) (Math.random() * list1.size());
                    String xyStr=list1.get(index);
                    list1.remove(list1.get(index));
                    xyArr = xyStr.split(",");
                    System.out.println("x: "+xyArr[0]+"y: "+xyArr[1]);
                }else if(i>2 && i<6){
                    int index = (int) (Math.random() * list2.size());
                    String xyStr=list2.get(index);
                    list2.remove(list2.get(index));
                    xyArr = xyStr.split(",");
                    System.out.println("x: "+xyArr[0]+"y: "+xyArr[1]);
                }else{
                    int index = (int) (Math.random() * list3.size());
                    String xyStr=list3.get(index);
                    list3.remove(list3.get(index));
                    xyArr = xyStr.split(",");
                    System.out.println("x: "+xyArr[0]+"y: "+xyArr[1]);
                }
                sb2.append("\"xVal\":\""+xyArr[0]+"\",");
                sb2.append("\"yVal\":\""+xyArr[1]+"\",");
                sb2.append("\"name\":\""+split[3]+"\",");
                sb2.append("\"size\":\""+split[1]+"\",");
                sb2.append("\"u\":\""+split[2]+"\",");
                sb2.append("\"per\":\""+a*100+"\"}");
                li.add(sb2.toString());
            }
        }
        return li;
    }
}
