package de.traveltogether.comments;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Comment;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 15.06.2016.
 */
public class CommentInteractor implements ICommentInteractor {
    ICommentPresenter listener;
    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()=="true"){

        }
        else if(dataType == DataType.CHAT){
            if(actionType== ActionType.LIST) {
                String data = response.getData();
                CommentList commentList = (CommentList) JsonDecode.getInstance().jsonToArray(data, CommentList.class);
                if (listener != null) {
                    listener.onSuccessCommentList(commentList.list);
                }
            }
            else if(actionType==ActionType.ADD){
                listener.onSuccessAddComment();
            }
        }

    }

    @Override
    public void getCommentsForFeature(long id, ICommentPresenter _listener) {
        try {
            listener=_listener;
            JSONObject json = new JSONObject();
            json.put("featureId", id);
            HttpRequest httpRequest = new HttpRequest(DataType.COMMENT, ActionType.LIST, json.toString(), this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }

    @Override
    public void getCommentsForTrip(long id,ICommentPresenter _listener){
        try{
            listener=_listener;
            JSONObject json = new JSONObject();
            json.put("tripId", id);
            HttpRequest httpRequest = new HttpRequest(DataType.CHAT, ActionType.LIST, json.toString(),this);
        }
        catch(Exception e){
            Log.e(e.getClass().toString(),e.getMessage());
        }
    }

    @Override
    public void sendCommentForFeature(long id, int personId, String text, ICommentPresenter _listener) {
        try {
            listener=_listener;
            JSONObject json = new JSONObject();
            json.put("featureId", id);
            json.put("content",text);
            json.put("author", personId);
            HttpRequest httpRequest = new HttpRequest(DataType.COMMENT, ActionType.ADD, json.toString(), this);
        }
        catch(Exception e){

        }
    }

    @Override
    public void sendCommentForTrip(long id, int personId, String text, ICommentPresenter _listener) {
        try {
            listener=_listener;
            JSONObject json = new JSONObject();
            json.put("tripId", id);
            json.put("content",text);
            json.put("author", personId);
            HttpRequest httpRequest = new HttpRequest(DataType.CHAT, ActionType.ADD, json.toString(), this);
        }
        catch(Exception e){

        }
    }

    class CommentList{
        Comment[] list;
    }
}
