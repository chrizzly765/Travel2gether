package de.traveltogether.expense.detailexpense;

import de.traveltogether.model.Expense;

/**
 * Created by Anna-Lena on 15.09.2016.
 */
public interface IExpenseDetailPresenter {
    public void onGetDetailsForExpense(long featureId);
    public void onSuccessGetDetails(Expense expense);
    public void onError(String message, String title);
    public void onDeleteExpense(long id);
    public void onSuccessDelete();

    void onCloseActivity();
}
