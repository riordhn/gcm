package acamedyid.pushacademy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rio Ramadhan D on 4/19/2016.
 */
public class Function {
    public String regid, username, password;
    private String TAG = "Function";
    private Context context;
    private String response = "";

    public Function(){
    }

    public Function(Context context) {
        this.context = context;
    }

    public String getResponse(){
        return response;
    }

    public void newRegist(String id){
        regid = id;
        new updateRegistId().execute();
    }

    public void login(String id, String password){
        username = id;
        this.password = password;
        new loginCheck().execute();
    }

    private class updateRegistId extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String respon;
                String url = "http://venolfkhua.com/api/registid.php";

                Log.i(TAG, "FAILED"+SaveSharedPreference.getUser(context));

                List<NameValuePair> myPair = new ArrayList<NameValuePair>();
                myPair.add(new BasicNameValuePair("regid", regid));
                myPair.add(new BasicNameValuePair("username", SaveSharedPreference.getUser(context)));
                JSONParser jsonParser = new JSONParser(false);
                jsonParser.setMyPair(myPair);
                jsonParser.setResponse(jsonParser.exec(url));
                respon = jsonParser.getResponse();

            } catch (Exception e) {
                Log.i(TAG, "FAILED");
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private class loginCheck extends AsyncTask<String, Void, String> {
        String respon;
        @Override
        protected String doInBackground(String... params) {
            try {

                String url = "http://venolfkhua.com/api/login.php";

                List<NameValuePair> myPair = new ArrayList<NameValuePair>();
                myPair.add(new BasicNameValuePair("username", username));
                myPair.add(new BasicNameValuePair("password", password));
                JSONParser jsonParser = new JSONParser(false);
                jsonParser.setMyPair(myPair);
                jsonParser.setResponse(jsonParser.exec(url));
                respon = jsonParser.getResponse();

            } catch (Exception e) {
                Log.i(TAG, "FAILED");
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            if(respon.equals("true")){
                SaveSharedPreference.setUser(context, username);
            }
            response = respon;

            if(respon.equals("true")){
                context.startActivity(new Intent(context, MainActivity.class));
                ((Activity)context).finish();
            }
            else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT);
            }
        }
    }
}
