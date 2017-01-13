package de.traveltogether.expense.newexpense;

import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

/**
 * Interface for Presenter of NewExpenseActivity
 */
public interface INewExpensePresenter {
    void onGetParticipantsForTrip(long tripId);
    void onSuccessGetParticipants(Participant[] participants);
    void onError(String message, String title);
    void onCreateExpense(Expense expense);
    void onSuccessAddingExpense();
    void onUpdateExpense(Expense expense);
    void onGetDetailForExpense(long featureId);
    void onSuccessGetDetail(Expense expense);
    void onSuccessUpdateExpense();
}
