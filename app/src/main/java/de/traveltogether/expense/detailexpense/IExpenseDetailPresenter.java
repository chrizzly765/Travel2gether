package de.traveltogether.expense.detailexpense;

import de.traveltogether.model.Expense;


public interface IExpenseDetailPresenter {
     void onGetDetailsForExpense(long featureId);
     void onSuccessGetDetails(Expense expense);
     void onError(String message, String title);
     void onDeleteExpense(long id);
     void onSuccessDelete();
     void onGetParticipantsForTrip(long tripId);
    void onCloseActivity();
     void onSuccessGetParticipants();
}
