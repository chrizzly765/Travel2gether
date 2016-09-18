package de.traveltogether.expense;

import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IExpensePresenter {
    public void onGetExpenseList(long tripId);
    public void onGetParticipantList(long tripId);
    public void onSuccessGetParticipants(Participant[] participants);
    public void onError(String message);
    public void onSuccessGetExpenses(Expense[] expenses);
}
