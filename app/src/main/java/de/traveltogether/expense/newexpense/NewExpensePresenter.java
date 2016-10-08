package de.traveltogether.expense.newexpense;

import de.traveltogether.expense.detailexpense.ExpenseDetailPresenter;
import de.traveltogether.expense.detailexpense.IExpenseDetailPresenter;
import de.traveltogether.model.Expense;
import de.traveltogether.model.Participant;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public class NewExpensePresenter implements INewExpensePresenter {
    NewExpenseActivity view;
    INewExpenseInteractor interactor;

    public NewExpensePresenter (NewExpenseActivity _view){
        view= _view;
        interactor= new NewExpenseInteractor();
    }

    public void onGetParticipantsForTrip(long tripId){
        interactor.getParticipantsForTrip(tripId, this);
    }

    @Override
    public void onSuccessGetParticipants(Participant[] participants) {
        view.onViewParticipants(participants);
    }

    @Override
    public void onError(String message) {
            view.onViewError(message);
    }

    @Override
    public void onCreateExpense(Expense expense) {
        interactor.createExpense(expense, this);
    }

    @Override
    public void onSuccessAddingExpense() {
        view.onSuccessAddingExpense();
    }


    @Override
    public void onGetDetailForExpense(long featureId) {
        interactor.getDetailsForExpense(featureId, this);
    }

    @Override
    public void onUpdateExpense(Expense expense){
        interactor.updateExpense(expense, this);
    }

    @Override
    public void onSuccessGetDetail(Expense expense) {
        view.setValues(expense);
    }

    @Override
    public void onSuccessUpdateExpense() {
        view.onSuccessUpdateExpense();
    }
}
