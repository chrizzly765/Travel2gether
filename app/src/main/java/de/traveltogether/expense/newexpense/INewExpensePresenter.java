package de.traveltogether.expense.newexpense;

import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewExpensePresenter {
    public void onGetParticipantsForTrip(long tripId);
    public void onSuccessGetParticipants(Participant[] participants);
    public void onError(String message, String title);
    public void onCreateExpense(Expense expense);
    public void onSuccessAddingExpense();
    public void onUpdateExpense(Expense expense);
    public void onGetDetailForExpense(long featureId);
    public void onSuccessGetDetail(Expense expense);
    public void onSuccessUpdateExpense();
}
