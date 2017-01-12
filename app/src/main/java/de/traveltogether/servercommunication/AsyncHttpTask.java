package de.traveltogether.servercommunication;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AsyncTask for processing http request
 */
class AsyncHttpTask extends AsyncTask<String, String, String> {
    private HttpURLConnection connection;
    String result= "";
    private IHttpRequest caller;

    public AsyncHttpTask(IHttpRequest _caller){
        caller= _caller;
    }

    /**
     * Work the task should do in background
     * @param strings
     * @return
     */
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
            wr.write(strings[1]);
            wr.flush();
            wr.close();

            InputStream stream = new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            while ((response = bufferedReader.readLine()) != null) {
                builder.append(response);
            }
            response = builder.toString();
            stream.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * After executing onPostExecute is called
     * @param result
     */
    @Override
    protected void onPostExecute(String result){
        caller.setResponse(result);
    }
}
