package acamedyid.pushacademy;


/**
 * Created by F on 5/25/2015.
 */

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.List;


public class Bridge {
    private JSONArray data;
    private String response;
    private Boolean isget;
    private List<NameValuePair> myPair;

    /* @Override
     protected String doInBackground(String... params) {
         // TODO Auto-generated method stub
         if(isget){return GET(params[0]);}
         else {return POST(params[0]);}
     }

     @Override
     protected void onPostExecute(String result) {
         super.onPostExecute(result);
     }
     */
    public String cobalah(String params) {
        if (isget) {
            return GET(params);
        } else {
            return POST(params);
        }
    }

    public Bridge(Boolean isget) {
        // TODO Auto-generated constructor stub
        this.isget = isget;
    }

    public List<NameValuePair> getMyPair() {
        return myPair;
    }

    public void setMyPair(List<NameValuePair> myPair) {
        this.myPair = myPair;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(String response) {
        try {
            this.data = new JSONArray(response);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String GET(String url) {
        // TODO Auto-generated method stub
        String result = "";
        try {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpProtocolParams.setHttpElementCharset(params, HTTP.UTF_8);
            DefaultHttpClient client2 = new DefaultHttpClient(params);
            HttpResponse response = client2.execute(new HttpGet(url));
            result = getHttpResponse(response);
        } catch (ConnectTimeoutException e) {
            result = "timeout";
        } catch (SocketTimeoutException e) {
            result = "timeout";
        } catch (Exception e) {
            result = "timeout";
        }
        Log.d("I/O", "url:" + url);
        Log.d("I/O", "OUTPUT :" + result);
        return result;
    }


    public String POST(String url) {
        // TODO Auto-generated method stub
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpProtocolParams.setHttpElementCharset(params, HTTP.UTF_8);
            httpPost.setEntity(new UrlEncodedFormEntity(myPair, "UTF-8"));

            DefaultHttpClient client2 = new DefaultHttpClient(params);
            HttpResponse response = client2.execute(httpPost);
            result = getHttpResponse(response);

        } catch (ConnectTimeoutException e) {
            result = "timeout";
        } catch (SocketTimeoutException e) {
            result = "timeout";
        } catch (Exception e) {
            result = "timeout";
        }
        Log.d("I/O", "url:" + url);
        Log.d("I/O", "OUTPUT :" + result);
        return result;
    }

    public String getHttpResponse(HttpResponse response) {
        InputStream is = null;
        String result = "";
        try {
            is = response.getEntity().getContent();
            if (is != null) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));
                StringBuilder str = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
                is.close();
                result = str.toString();
            } else
                result = "error";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}