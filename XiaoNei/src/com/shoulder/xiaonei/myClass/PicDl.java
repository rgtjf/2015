package com.shoulder.xiaonei.myClass;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.shoulder.xiaonei.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class PicDl extends Activity{
	ImageView show;
	// ������������صõ���ͼƬ
	Bitmap bitmap;
	
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what == 0x123)
			{
				// ʹ��ImageView��ʾ��ͼƬ
				show.setImageBitmap(bitmap);
			}
		}
	};
	public void pic()
	{
		show = (ImageView) findViewById(R.id.Card_picture);
		new Thread()
		{
			public void run()
			{
				try
				{
					// ����һ��URL����
					URL url = new URL("http://www.baidu.com/img/" + "baidu_sylogo1.gif");
					// �򿪸�URL��Ӧ����Դ��������
					InputStream is = url.openStream();
					// ��InputStream�н�����ͼƬ
					bitmap = BitmapFactory.decodeStream(is);
					// ������Ϣ��֪ͨUI�����ʾ��ͼƬ
					handler.sendEmptyMessage(0x123);
					is.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

}
