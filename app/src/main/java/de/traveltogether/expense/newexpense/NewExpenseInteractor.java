package de.traveltogether.expense.newexpense;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewExpenseInteractor implements INewExpenseInteractor {
    INewExpensePresenter listener;

    @Override
    public void getParticipantsForTrip(long tripId, INewExpensePresenter _listener) {
        listener= _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
        }

    }

    @Override
    public void createExpense(long tripId, Expense expense, INewExpensePresenter _listener) {
        listener=_listener;
        try {
            String jsonString = JsonDecode.getInstance().classToJson(expense);
            JSONObject obj = new JSONObject(jsonString);
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.EXPENSE, ActionType.ADD, obj.toString(), this);
        }
        catch (Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
        }
    }

    @Override
    public void getDetailsForExpense(long featureId, INewExpensePresenter _listener) {
        listener= _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.EXPENSE, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
        }
    }

    @Override
    public void updateExpense(Expense expense, INewExpensePresenter _listener) {
        listener= _listener;
        try {
            String json = JsonDecode.getInstance().classToJson(expense);
            HttpRequest request = new HttpRequest(DataType.EXPENSE, ActionType.UPDATE, json, this);
        }
        catch(Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
        }
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if (response.getError() == "false") {
            if (dataType == DataType.TRIP && actionType == ActionType.GETPARTICIPANTS) {
                Participant[] participants = ((ParticipantList) JsonDecode.getInstance().jsonToArray(response.getData(), ParticipantList.class)).list;
                listener.onSuccessGetParticipants(participants);
            } else if (dataType == DataType.EXPENSE && actionType == ActionType.ADD) {
                listener.onSuccessAddingExpense();
            } else if (dataType == DataType.EXPENSE && actionType == ActionType.DETAIL) {
                listener.onSuccessGetDetail((Expense)JsonDecode.getInstance().jsonToClass(response.getData(), DataType.EXPENSE));
            } else if (dataType == DataType.EXPENSE && actionType == ActionType.UPDATE) {
                listener.onSuccessUpdateExpense();
            }
        } else {
            listener.onError(response.getMessage());
        }
    }

    class ParticipantList {
        Participant[] list;
    }
}
