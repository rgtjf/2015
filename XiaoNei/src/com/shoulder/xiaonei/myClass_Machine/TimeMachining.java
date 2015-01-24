package com.shoulder.xiaonei.myClass_Machine;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.shoulder.xiaonei.myClass.MyStatic;
import com.shoulder.xiaonei.tucao.TuCaoQiang_Comment;

import android.text.format.DateFormat;
import android.util.Log;

public class TimeMachining {
	
	public static String twoDateDistance(String temp_startDate1){  
    	SimpleDateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	java.util.Date d1 = null;
    	java.util.Date d2 = new java.util.Date(System.currentTimeMillis());
    	try
    	{
    	d1 = df.parse(temp_startDate1);
    	}
    	catch(Exception e){
    	}
    	if(d2==null) {
			return "d2wrong";
		} else if(d1==null){
    		return "d1wrong";
    	}
    	else{
        long timeLong = d2.getTime() - d1.getTime();  
        if (timeLong < 3*60*1000)
        	return "�ո�";
        else if (timeLong<60*60*1000){  
            timeLong = timeLong/1000 /60;  
            return timeLong + "����ǰ";  
        }  
        else if (timeLong<60*60*24*1000){  
            timeLong = timeLong/60/60/1000;  
            return timeLong+"Сʱǰ";  
        }  
        else{  
            timeLong = timeLong/1000/ 60 / 60 / 24;  
            return timeLong + "��ǰ";  
        } 
    	}
    }
	
	public static Boolean TimeTOEvaluate(String tempDate){
		SimpleDateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	java.util.Date d1 = null;
    	java.util.Date d2 = new java.util.Date(System.currentTimeMillis());
    	try
    	{
    	d1 = df.parse(tempDate);
    	}
    	catch(Exception e){
    	}
    	if(d2.getTime() - d1.getTime() >0){
    		return true; //��ǰʱ��Ȼʱ�����ˣ����Ի�Ѿ���ȥ��
    	}
    	else {
    		return false;
    	}
    	
	}
	
	public static long BeforeHand(String temp_Date){
    	SimpleDateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	java.util.Date d1 = null;
    	try
    	{
    	d1 = df.parse(temp_Date);
    	}
    	catch(Exception e){
    	}
    	long timeBefore = d1.getTime() - 3*60*60*1000;
    	return timeBefore;
	}
	
	public static int getTimeRelatedId(){
		java.util.Date d2 = new java.util.Date(System.currentTimeMillis());
		return (d2.getDate()*1000000 + d2.getHours()*10000 + d2.getMinutes()*100 +d2.getSeconds());
	}
	
	public static String DateTranslator(String temp_Date){
    	SimpleDateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	java.util.Date d1 = null;
    	try
    	{
    	d1 = df.parse(temp_Date);
    	}
    	catch(Exception e){
    	}
    	
    	String result="";
    	String day = "";
    	switch (d1.getDay()){
    	case 0:
    		day = "����";break;
    	case 1:
    		day = "��һ";break;
    	case 2:
    		day = "�ܶ�";break;
    	case 3:
    		day = "����";break;
    	case 4:
    		day = "����";break;
    	case 5:
    		day = "����";break;
    	case 6:
    		day = "����";break;
    	}
    	DecimalFormat mFormat= new DecimalFormat("00"); // ������λ����0����
    	result = (d1.getMonth()+1) + "��" +d1.getDate() +"��(" + day + ") " + mFormat.format(Double.valueOf(d1.getHours())) + ":" + mFormat.format(Double.valueOf(d1.getMinutes()));
    	return result;
	}
	
	
	public static int getMonth(){
		java.util.Date d1 = new java.util.Date(System.currentTimeMillis());
		return d1.getMonth();
	}
	
	
	//��������������ʱ�����м���%��ʱ�䣬����get������
	public static String TimeTranslatorToGetJson(String temp_Date){
		
		//��������ʱ��ת����date
		SimpleDateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	java.util.Date d1 = null;
    	try
    	{
    	d1 = df.parse(temp_Date);
    	}
    	catch(Exception e){
    	}
    	
    	//��dateʱ��ת������%��ʱ��
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd%20HH:mm:ss");
    	String dateString = formatter.format(d1);
    	
    	return dateString;
	}
	
	//��ȡ��ǰʱ�䣬��string�����м���%����ʽ
	public static String getCurrentTime(long minutes){//������ʾ��ǰ�����ӣ���Ϊ0����Ϊ��ǰʱ��
		
		//����ǰʱ��ת����date
    	java.util.Date d1 = new java.util.Date(System.currentTimeMillis() - minutes*60*1000);
    	
    	//��dateʱ��ת������%��ʱ��
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd%20HH:mm:ss");
    	String dateString = formatter.format(d1);
    	
    	return dateString;
	}
	
	
	 public static boolean haveTimeGap(long lastTime, long time) {
		    long gap = MyStatic.Chat_TimeGap;
		    return time - lastTime > gap;
	}
	 
	 public static String millisecs2DateString(long timestamp) {
		    long gap=System.currentTimeMillis()-timestamp;
		    java.util.Date d1 = new java.util.Date(timestamp);
		    DecimalFormat mFormat= new DecimalFormat("00"); // ������λ����0����
		    if(gap<1000*60*60*24){
		    	return mFormat.format(Double.valueOf(d1.getHours())) + ":" + d1.getMinutes();
		    }
		    else if(gap<1000*60*60*24*15){
		      return d1.getDate() + "��" + mFormat.format(Double.valueOf(d1.getHours())) + ":" + mFormat.format(Double.valueOf(d1.getMinutes()));
		    }
		    else{
		    	return d1.getMonth() + "��" + d1.getDate() + "��" + mFormat.format(Double.valueOf(d1.getHours())) + ":" + mFormat.format(Double.valueOf(d1.getMinutes()));
		    }
	 }
}
