package de.traveltogether.expense;

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
 * Interactor for ExpenseActivity
 * Implements IExpenseInteractor
 */
public class ExpenseInteractor implements IExpenseInteractor {
    private IExpensePresenter listener;
    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if (dataType == DataType.TRIP && actionType == ActionType.GETPARTICIPANTS) {
            if (response.getError().equals("false")) {
                Participant[] participants = ((ParticipantList) JsonDecode.getInstance().jsonToClass(response.getData(), ParticipantList.class)).list;
                listener.onSuccessGetParticipants(participants);
            } else {
                listener.onError(response.getMessage());
            }
        }
        else if(dataType==DataType.EXPENSE && actionType == ActionType.LIST){
            if (response.getError().equals("false")) {
                Expense[] expenses = ((ExpenseList) JsonDecode.getInstance().jsonToClass(response.getData(), ExpenseList.class)).list;
                listener.onSuccessGetExpenses(expenses);
            } else {
                listener.onError(response.getMessage());
            }
        }

    }

    @Override
    public void getExpenseList(long tripId, IExpensePresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.EXPENSE, ActionType.LIST, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
            listener.onError("Fehler beim HttpRequest");
        }
    }

    @Override
    public void getParticipantsForTrip(long tripId, IExpensePresenter _listener) {
        listener= _listener;
        try {
            JSONObject obj = new JSONObject();
            obj.put("tripId", tripId);
            HttpRequest request = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);
        }
        catch(Exception e){
            Log.e("NewExpenseInteractor", e.getMessage());
            listener.onError("Fehler beim HttpRequest");
        }
    }

    /**
     * Necessary class for getting Participant array from json string
     */
    class ParticipantList {
        Participant[] list;
    }

    /**
     * Necessary class for getting Expense array from json string
     */
    class ExpenseList {
        Expense[] list;
    }
}
