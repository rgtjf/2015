package com.shoulder.xiaonei.others;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.shoulder.xiaonei.R;
import com.shoulder.xiaonei.main.Main;
import com.shoulder.xiaonei.myClass.ExitApplication;
import com.shoulder.xiaonei.myClass.MyStatic;
import com.shoulder.xiaonei.myClass.MyVal;
import com.shoulder.xiaonei.myClass_Machine.TimeMachining;
import com.umeng.analytics.MobclickAgent;

public class Start_Page_Normal extends Activity{
	
	private ImageView startPageImage_Normal;
	
	private Timer timer;
	
	private SharedPreferences preferences_startPage;
	
	protected SharedPreferences preferences_account,preferences_schoolId;
	
	protected File cache;
	private SharedPreferences preferences_month;
	private SharedPreferences.Editor editor;
	private MyVal mv;
	
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page_image); 
        
        ExitApplication.getInstance().addActivity(Start_Page_Normal.this);
        
        mv=(MyVal)getApplication();
        
        MobclickAgent.updateOnlineConfig(Start_Page_Normal.this);//����ͳ��������Ҫ
        MobclickAgent.setDebugMode(true);
        
        startPageImage_Normal = (ImageView)findViewById(R.id.startPage_image);
        
        saveMonth();//���»����е��·���������ÿ�����һ�λ�������
        
        //�Ƿ���Ҫ��������ҳ
        preferences_startPage = getSharedPreferences(MyStatic.Preferences_StartPage, MODE_PRIVATE);
//        if(true){
        if(preferences_startPage.getBoolean(MyStatic.Preferences_StartPage_HasShowed, false) == false){
	      	Intent intent_toStartPage = new Intent(Start_Page_Normal.this,Start_Page.class);
	      	startActivity(intent_toStartPage);
        }
        else {
        	startPageImage_Normal.setImageResource(R.drawable.startpage_normal);
            //һ��ʱ��������ҳ
            timer=new Timer();
            TimerTask timertask=new TimerTask() {
    			public void run() {
    		        //�Ƿ���Ҫ����ע�ᡢ��½����
    		        preferences_account = getSharedPreferences("user_account", MODE_PRIVATE);
    		        String user_name = preferences_account.getString("user_name", null);
    		        if(user_name == null){
    			        Intent intent =new Intent(Start_Page_Normal.this,Login_or_Register.class);
	    				finish();
    			        startActivity(intent);
    		        }
    		        else {
	    				Intent intent = new Intent(Start_Page_Normal.this,Main.class);
	    				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    				finish();
	    				startActivity(intent);
    		        }
    			}
    		}; 
    		timer.schedule(timertask, MyStatic.StartPageLoadingTime);
        }
	}
	
	
	private void saveMonth(){
        preferences_month = getSharedPreferences("month", MODE_PRIVATE);
        int monthNow = preferences_month.getInt("monthNow", -1);
        if(monthNow != TimeMachining.getMonth()){
        	editor = preferences_month.edit();
        	editor.putInt("monthNow", TimeMachining.getMonth());
        	editor.commit();
        	clear();
        }
	}
	
	
	public void clear(){
		cache = new File(mv.getMyDir(), mv.getFileNameHo()); 
		
		//��ջ���
		File[] files = cache.listFiles();
		if(files!=null){
				for(File file :files){
					file.delete();
				}
				cache.delete();
			}
		//ɾ���ɰ��ļ����µ�ͼƬ���¸��汾ȥ��
		File cache_old = new File(mv.getMyDir(), "sheyouApp"); 
		File[] files_old = cache_old.listFiles();
		if(files_old!=null){
				for(File file :files_old){
					file.delete();
				}
				cache_old.delete();
			}
	}
	
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
}
