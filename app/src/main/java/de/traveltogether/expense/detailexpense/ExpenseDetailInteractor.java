package de.traveltogether.expense.detailexpense;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticTripData;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Interactor for ExpenseDetailActivity
 * Implements IExpenseDetailInteractor
 */
public class ExpenseDetailInteractor implements IExpenseDetailInteractor {
    private IExpenseDetailPresenter listener;

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(!response.getError().equals("true")){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                try {
                    listener.onSuccessGetDetails((Expense) JsonDecode.getInstance().jsonToClassByType(response.getData(), DataType.EXPENSE));
                }
                catch(Exception e){
                    listener.onCloseActivity();
                }
            }
            else if (actionType == ActionType.GETPARTICIPANTS) {
                Participant[] participants = ((ParticipantList) JsonDecode.getInstance().jsonToClass(response.getData(), ParticipantList.class)).list;
                StaticTripData.setParticipants(participants);
                listener.onSuccessGetParticipants();
            }
        }
        else{
            if (actionType == ActionType.DETAIL) {
                listener.onCloseActivity();
            }
            listener.onError(response.getMessage(), response.getMessage());
        }
    }

    @Override
    public void getDetailsForExpense(long featureId, IExpenseDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", featureId);
            HttpRequest request = new HttpRequest(DataType.EXPENSE, ActionType.DETAIL, obj.toString(), this);
        }
        catch(Exception e){
            listener.onError("Fehler beim HttpRequest", "Fehler");
            Log.e("DetailExpenseInteractor", e.getMessage());
        }
    }

    @Override
    public void deleteExpense(long id, IExpenseDetailPresenter _listener) {
        listener = _listener;
        try{
            JSONObject obj = new JSONObject();
            obj.put("featureId", id);
            HttpRequest request = new HttpRequest(DataType.EXPENSE, ActionType.DELETE, obj.toString(), this);
        }
        catch(Exception e){
            listener.onError("Fehler beim HttpRequest", "Fehler");
            Log.e("DetailExpenseInteractor", e.getMessage());
        }
    }

    @Override
    public void getParticipantsForTrip(long tripId, IExpenseDetailPresenter _listener) {
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("tripId", tripId);
        }
        catch(Exception e){
            listener.onError("Fehler beim HttpRequest", "Fehler");
            Log.e(e.getClass().toString(), e.getMessage());
        }
        HttpRequest req = new HttpRequest(DataType.TRIP, ActionType.GETPARTICIPANTS, obj.toString(), this);

    }

    /**
     * Necessary helper class for getting participant array from json string
     */
    class ParticipantList{
        public Participant[] list;
    }
}
