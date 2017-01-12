package de.traveltogether.expense.newexpense;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Expense;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewExpenseInteractor extends IInteractor {
    void getParticipantsForTrip(long tripId, INewExpensePresenter listener);
    void createExpense(Expense expense, INewExpensePresenter _listener);
    void getDetailsForExpense(long featureId, INewExpensePresenter listener);
    void updateExpense(Expense expense, INewExpensePresenter listener);
}


