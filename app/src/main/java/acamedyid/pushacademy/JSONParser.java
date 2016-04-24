package acamedyid.pushacademy;

    import org.apache.http.HttpResponse;
    import org.apache.http.NameValuePair;
    import org.apache.http.client.HttpClient;
    import org.apache.http.client.entity.UrlEncodedFormEntity;
    import org.apache.http.client.methods.HttpGet;
    import org.apache.http.client.methods.HttpPost;
    import org.apache.http.impl.client.DefaultHttpClient;
    import org.json.JSONArray;
    import org.json.JSONException;

    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.util.List;

    public class JSONParser {
        private JSONArray data;
        public static HttpClient client = new DefaultHttpClient();
        private String response;
        private Boolean isget;
        private List<NameValuePair> myPair;

        public String exec(String params) {
            if (isget) {
                return GET(params);
            } else {
                return POST(params);
            }
        }

        public JSONParser(Boolean isget) {
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
            String result = "";
            try {
                HttpResponse response = client.execute(new HttpGet(url));
                result = getHttpResponse(response);
            } catch (Exception e) {

            }
            return result;
        }

        public String POST(String url) {
            String result = "";
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(myPair));
                HttpResponse response = client.execute(httpPost);
                result = getHttpResponse(response);

            } catch (Exception e) {
            }
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
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }