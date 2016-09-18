package de.traveltogether.expense.newexpense;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Expense;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewExpenseInteractor extends IInteractor {
    public void getParticipantsForTrip(long tripId, INewExpensePresenter listener);
    public void createExpense(long tripId, Expense expense, INewExpensePresenter listener);
    public void getDetailsForExpense(long featureId, INewExpensePresenter listener);
    public void updateExpense(Expense expense, INewExpensePresenter listener);
}


