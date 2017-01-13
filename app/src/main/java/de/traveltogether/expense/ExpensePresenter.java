package de.traveltogether.expense;

import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

/**
 * Presenter for ExpenseActivity
 * Implements IExpensePresenter
 */
public class ExpensePresenter implements IExpensePresenter{
    private ExpenseActivity view;
    private IExpenseInteractor interactor;

    public ExpensePresenter(ExpenseActivity _view){
        view = _view;
        interactor = new ExpenseInteractor();
    }

    @Override
    public void onGetExpenseList(long tripId) {
        interactor.getExpenseList(tripId, this);
    }

    @Override
    public void onGetParticipantList(long tripId) {
        interactor.getParticipantsForTrip(tripId, this);
    }

    @Override
    public void onSuccessGetParticipants(Participant[] participants) {
        view.onViewParticipants(participants);
    }

    @Override
    public void onError(String message){
        view.onViewError(message);
    }

    @Override
    public void onSuccessGetExpenses(Expense[] expenses) {
        view.onViewExpenses(expenses);
    }
}
