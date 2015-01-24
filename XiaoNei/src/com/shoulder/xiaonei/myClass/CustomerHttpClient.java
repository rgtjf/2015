package com.shoulder.xiaonei.myClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.widget.Toast;

import com.shoulder.xiaonei.main.Main;

public class CustomerHttpClient {  
    private static final String CHARSET = HTTP.UTF_8;  
    private static HttpClient customerHttpClient;  


    private CustomerHttpClient() {  
    }  


    public static synchronized HttpClient getHttpClient() {  
        if (null == customerHttpClient) {  
            HttpParams params = new BasicHttpParams();  
            // ����һЩ��������  
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
            HttpProtocolParams.setContentCharset(params,  
                    CHARSET);  
            HttpProtocolParams.setUseExpectContinue(params, true);  
            HttpProtocolParams  
                    .setUserAgent(  
                            params,  
                            "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "  
                                    + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");  
            // ��ʱ����  
            /* �����ӳ���ȡ���ӵĳ�ʱʱ�� */  
            ConnManagerParams.setTimeout(params, 10000);  
            /* ���ӳ�ʱ */  
            HttpConnectionParams.setConnectionTimeout(params, 10000);  
            /* ����ʱ */  
            HttpConnectionParams.setSoTimeout(params, 10000);  
            
            // �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ  
            SchemeRegistry schReg = new SchemeRegistry();  
            schReg.register(new Scheme("http", PlainSocketFactory  
                    .getSocketFactory(), 80));  
            schReg.register(new Scheme("https", SSLSocketFactory  
                    .getSocketFactory(), 443));  


            // ʹ���̰߳�ȫ�����ӹ���������HttpClient  
            ClientConnectionManager conMgr = new ThreadSafeClientConnManager(  
                    params, schReg);  
            customerHttpClient = new DefaultHttpClient(conMgr, params);  
        }  
        return customerHttpClient;  
    }  
    
    public static String post(String url, NameValuePair... params) {  
        try {  
            // �������  
            List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // �������  
            for (NameValuePair p : params) {  
                formparams.add(p);  
            }  
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,  
                    CHARSET);  
            // ����POST����  
            HttpPost request = new HttpPost(url);  
            request.setEntity(entity);  
            // ��������  
            HttpClient client = getHttpClient();  
            HttpResponse response = client.execute(request);  
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {  
            	return MyStatic.HTTPFAIL;
            }  
            HttpEntity resEntity =  response.getEntity();  
            return (resEntity == null) ? null : EntityUtils.toString(resEntity, CHARSET);  
        } catch (UnsupportedEncodingException e) {  
            return null;  
        } catch (ClientProtocolException e) {  
            return null;  
        } catch (IOException e) {  
            throw new RuntimeException("����ʧ��", e);  
        }  
    }
    
    public static String get(String url) {  
        try {  
            HttpGet get = new HttpGet(url);
            // ��������  
            HttpClient client = getHttpClient();
            HttpResponse httpResponse = client.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			String result="";
			if (entity != null)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
				String line = null;
				while ((line = br.readLine()) != null)
				{
					result = result + line;
				}
			}
			return result;
        } catch (UnsupportedEncodingException e) {  
            return null;  
        } catch (ClientProtocolException e) {  
            return null;  
        } catch (IOException e) {  
            throw new RuntimeException("����ʧ��", e);  
        }
    }
    
    
}
