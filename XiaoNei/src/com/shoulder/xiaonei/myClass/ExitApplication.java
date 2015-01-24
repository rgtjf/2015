package com.shoulder.xiaonei.myClass;

import java.util.LinkedList;
import java.util.List;

import com.shoulder.xiaonei.myClass.DragImageView.MyAsyncTask;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.Application;

public class ExitApplication extends Application {


	private List<Activity> activityList=new LinkedList<Activity>();
	private static ExitApplication instance;

	private ExitApplication(){}
	
	//����ģʽ�л�ȡΨһ��ExitApplication ʵ��
	public static ExitApplication getInstance()
	{
		if(null == instance){
			instance = new ExitApplication();
		}
	return instance;
	}
	
	
	//���Activity ��������
	public void addActivity(Activity activity){
		activityList.add(activity);
	}
	
	//��������Activity ��finish
	public void exit(){
		try{
			for(Activity activity:activityList){
				activity.finish();
			}
			if(MyVal.session != null)
				MyVal.session.close();//�˳�Ӧ��ʱ�ر�session
			MobclickAgent.onKillProcess(MyVal.getApplication());//�������˵�ͳ������
			System.exit(0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
