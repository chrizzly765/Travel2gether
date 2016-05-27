package www.traveltogether.de.traveltogether.servercommunication;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class HttpRequest implements IHttpRequest {

    HttpClient client = HttpClientBuilder.create().build();
    Response responseObject;
    public String getUrl(){
        String url = "http://www.imagik.de/traveltogether/main.php";
        return url;
    }

    public HttpRequest(DataType dataType, ActionType actionType, String json){
        HttpPost post = new HttpPost(getUrl());
        try {
            StringEntity params = new StringEntity(json);
            post.addHeader("content-type", "application/json");
            post.setEntity(params);
            HttpResponse response= client.execute(post);
            responseObject = (Response)JsonDecode.getInstance().jsonToClass(response.getEntity().toString(), DataType.HTTPRESPONSE);
        }
        catch(Exception e) {
        }
    }

    public Response getResponse(){
        if(responseObject!=null) {
            return responseObject;
        }
        return new Response("", "", "");
    }

}
