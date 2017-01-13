package de.traveltogether.expense;

import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

/**
 * Interface for presenter of ExpenseActivity
 */
interface IExpensePresenter {
     void onGetExpenseList(long tripId);
     void onGetParticipantList(long tripId);
     void onSuccessGetParticipants(Participant[] participants);
     void onError(String message);
     void onSuccessGetExpenses(Expense[] expenses);
}
