package de.traveltogether.expense.detailexpense;

import android.util.Log;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.expense.IExpenseInteractor;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;
import de.traveltogether.servercommunication.JsonDecode;

/**
 * Created by Anna-Lena on 15.09.2016.
 */
public class ExpenseDetailInteractor implements IExpenseDetailInteractor {
    IExpenseDetailPresenter listener;

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if(response.getError()!="true"){
            if(actionType == ActionType.DELETE){
                listener.onSuccessDelete();
            }
            else if (actionType == ActionType.DETAIL) {
                try {
                    listener.onSuccessGetDetails((Expense) JsonDecode.getInstance().jsonToClass(response.getData(), DataType.EXPENSE));
                }
                catch(Exception e){
                    //listener.onError("Auf diese Ausgabe kann leider nicht mehr zugegriffen werden.");
                    listener.onCloseActivity();
                }
            }
        }
        else{
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
            Log.e("DetailExpenseInteractor", e.getMessage());
        }
    }
}
