package de.traveltogether.expense.detailexpense;

import de.traveltogether.IInteractor;

/**
 * Interface for Interactor of ExpenseDetailActivity
 */
interface IExpenseDetailInteractor extends IInteractor {
    void getDetailsForExpense(long featureId, IExpenseDetailPresenter _listener);
    void deleteExpense(long id, IExpenseDetailPresenter _listener);
    void getParticipantsForTrip(long tripId, IExpenseDetailPresenter listener);
}
