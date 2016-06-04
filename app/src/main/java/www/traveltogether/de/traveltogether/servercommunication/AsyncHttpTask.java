package www.traveltogether.de.traveltogether.servercommunication;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Anna-Lena on 28.05.2016.
 */
public class AsyncHttpTask extends AsyncTask<String, String, String> {
    HttpURLConnection connection;
    String result= "";
    IHttpRequest caller;

    public AsyncHttpTask(IHttpRequest _caller){
        caller= _caller;
    }

    @Override
    public String doInBackground(String... strings) {
        String response = "UNDEFINED";
        try {
            Thread.sleep(1000);
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            Log.d("send: ", strings[1]);
            wr.write(strings[1]);
            wr.flush();
            wr.close();

            InputStream stream = new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            //String inputString;
            while ((response = bufferedReader.readLine()) != null) {
                builder.append(response);
            }
            response = builder.toString();
            stream.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result){
        Log.d("result",result);
        caller.setResponse(result);
    }


}
