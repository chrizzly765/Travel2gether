package www.traveltogether.de.traveltogether.comments;

import android.util.Log;

import org.json.JSONObject;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.model.Request;
import www.traveltogether.de.traveltogether.model.Response;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public class CommentInteractor implements ICommentInteractor {
    ICommentPresenter listener;
    @Override
    public void onRequestFinished(Response response) {
        //TODO
    }

    @Override
    public void getComments(long id, CommentPresenter commentPresenter) {
        try {
            JSONObject json = new JSONObject();
            json.put("featureId", id);
            HttpRequest httpRequest = new HttpRequest(DataType.COMMENT, ActionType.LIST, json.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }
}
