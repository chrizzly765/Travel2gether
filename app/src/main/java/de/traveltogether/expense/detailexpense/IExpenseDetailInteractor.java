package de.traveltogether.expense.detailexpense;

import de.traveltogether.IInteractor;


interface IExpenseDetailInteractor extends IInteractor {
    public void getDetailsForExpense(long featureId, IExpenseDetailPresenter _listener);
    public void deleteExpense(long id, IExpenseDetailPresenter _listener);
    public void getParticipantsForTrip(long tripId, IExpenseDetailPresenter listener);
}
