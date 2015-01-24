package com.shoulder.xiaonei.myClass;

import com.shoulder.xiaonei.R;
import com.shoulder.xiaonei.main.HuoDong_Remain;
import com.shoulder.xiaonei.myClass_Machine.TimeMachining;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class AlarmReceiver extends BroadcastReceiver{

	private String get_uri;
	private String get_name;
	private String get_activity_content;
	private String get_time;
	private String get_address;
	public void onReceive(Context context, Intent intent) {
		
		Bundle extra = intent.getExtras();
		get_uri = extra.getString("uri");
		get_name = extra.getString("title");
		get_activity_content = extra.getString("introduce");
		get_time = extra.getString("time");
		get_address = extra.getString("address");
		
		
		 //1.����һ��NotificationManager������
        NotificationManager mNotificationManager = 
        		(NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
       
        //2.ʵ����һ��֪ͨ��ָ��ͼ�ꡢ��Ҫ��ʱ��
        Notification notification=new Notification(R.drawable.icon,"3Сʱ�������ע�Ļ",System.currentTimeMillis());
//        TimeMachining.BeforeHand(get_time)
        //3.ָ��֪ͨ�ı��⡢���ݺ�intent
//        Context context = getApplicationContext(); //������
        String title = get_name;//����
        String notification_content = TimeMachining.DateTranslator(get_time) + "," + get_address;//����
        Intent notificationIntent = new Intent(context ,HuoDong_Remain.class); //�����֪ͨ��Ҫ��ת��Activity
        notificationIntent.putExtra("uri", get_uri);
        notificationIntent.putExtra("title", get_name);
        notificationIntent.putExtra("introduce", get_activity_content);
        notificationIntent.putExtra("time", get_time);
        notificationIntent.putExtra("address", get_address);
        PendingIntent pi= PendingIntent.getActivity(context,TimeMachining.getTimeRelatedId(), notificationIntent, 0);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(context, title, notification_content, pi);
        notification.defaults = Notification.DEFAULT_SOUND; //ָ������
        notification.defaults |= Notification.DEFAULT_VIBRATE;//�趨��
        mNotificationManager.notify(TimeMachining.getTimeRelatedId(), notification);//4.����֪ͨ
		
	}

	

}
