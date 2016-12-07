package de.traveltogether.expense.detailexpense;

import de.traveltogether.model.Expense;

/**
 * Created by Anna-Lena on 15.09.2016.
 */
public class ExpenseDetailPresenter implements IExpenseDetailPresenter {
    ExpenseDetailActivity view;
    IExpenseDetailInteractor interactor;

    public ExpenseDetailPresenter(ExpenseDetailActivity _view){
        view= _view;
        interactor= new ExpenseDetailInteractor();
    }

    @Override
    public void onGetDetailsForExpense(long featureId) {
        interactor.getDetailsForExpense(featureId, this);
    }

    @Override
    public void onSuccessGetDetails(Expense expense) {
        view.onViewDetails(expense);
    }

    @Override
    public void onError(String message, String title) {
        view.onViewError(message, title);
    }

    @Override
    public void onDeleteExpense(long id) {
        interactor.deleteExpense(id, this);
    }

    @Override
    public void onSuccessDelete() {
        view.onSuccessDelete();
    }

    @Override
    public void onGetParticipantsForTrip(long tripId) {
        interactor.getParticipantsForTrip(tripId, this);
    }

    @Override
    public void onCloseActivity(){view.onCloseActivity();}
}
