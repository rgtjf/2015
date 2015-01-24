package com.shoulder.xiaonei.myClass_Machine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.Session;
import com.avos.avoscloud.SessionManager;
import com.shoulder.xiaonei.myClass.CustomerHttpClient;
import com.shoulder.xiaonei.myClass.MyStatic;
import com.shoulder.xiaonei.myClass.MyVal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;
import android.content.SharedPreferences;

public class LoginMachine{
	
	public static SharedPreferences preferences_account , preferences_schoolId;
	public static SharedPreferences.Editor editor;
	
	public static String Login(){
		preferences_account = MyVal.getApplication().getSharedPreferences("user_account", 0);
		String getResponse = "";
		try
		{
			if(preferences_account != null){
				String getName = "home/login";
				final String user_name = preferences_account.getString("user_name", null);
				String user_password = preferences_account.getString("user_password", null);
				NameValuePair param1 = new BasicNameValuePair("name", user_name);
				NameValuePair param2 = new BasicNameValuePair("passwd", user_password);
				getResponse = CustomerHttpClient.post(MyVal.getApplication().getIpName()+getName, param1,param2);
				if(!getResponse.equals("false")){
					
					JSONObject jsonObject = new JSONObject(getResponse);
					JSONArray jsonArray = jsonObject.getJSONArray("memberinfo");
					JSONObject jsonpet = jsonArray.getJSONObject(0);
					int sex = jsonpet.getInt("Sex");
					String schoolId = jsonpet.getString("SchoolID");
					String myNickName = jsonpet.getString("nickname");
					String myIntroduce = jsonpet.getString("introduce");
					String myAvator = jsonpet.getString("photo");
					
					MyVal.getApplication().setSchoolId(schoolId);
					MyVal.getApplication().setMySex(sex);
					MyVal.getApplication().setLoginState(1);
					MyVal.getApplication().setMyName(myNickName);
					MyVal.getApplication().setMyId(user_name);
					MyVal.getApplication().setMyIntroduce(myIntroduce);
					MyVal.getApplication().setMyAvator(myAvator);
					
					//��½leanCloud
					openSession(user_name);
					
					//���豸ע�ᵽInstallation
					AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
						public void done(AVException e) {
							//��ȡInstallationֵ
							final String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
							//��ѯmyUser�����Ƿ���������¼���������Installationֵ��û�������
							final AVQuery<AVObject> userQuery = new AVQuery<AVObject>(MyStatic.LeanCloud_myUser);
							userQuery.whereEqualTo(MyStatic.LeanCloud_myUser_Name, user_name);
							userQuery.findInBackground(new FindCallback<AVObject>() {
								public void done(List<AVObject> avObjects, AVException e) {
									//�������
									if(e == null){
										String objectId = avObjects.get(0).getObjectId();
										try {
											AVObject avObject = userQuery.get(objectId);
											avObject.put(MyStatic.LeanCloud_myUser_Installation, installationId);
											avObject.saveInBackground();
										} catch (AVException e1) {
											e1.printStackTrace();
										}
									}
									else {
										AVObject avObject = AVObject.createWithoutData(MyStatic.LeanCloud_myUser, null);
										avObject.put(MyStatic.LeanCloud_myUser_Name, user_name);
										avObject.put(MyStatic.LeanCloud_myUser_Installation, installationId);
										avObject.saveInBackground();
									}
									}
							});
						}
					});
					
					
					
					//����schoolId
					preferences_schoolId = MyVal.getApplication().getSharedPreferences("schoolId", 0);
					editor = preferences_schoolId.edit();
					editor.putString("school_id", schoolId);
					editor.commit();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return getResponse;
	}
	
	
	public static void openSession(String user_name){
		//��½leanCloud
		MyVal.session = SessionManager.getInstance(user_name);
		List<String> myFriends = new ArrayList<String>();
		myFriends.add("7422");//����ֵ��ʱ��open����session
		MyVal.session.open(myFriends); 
	}

}
