package de.traveltogether.expense.newexpense;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface INewExpenseInteractor extends IInteractor {
    public void getParticipantsForTrip(long tripId, INewExpensePresenter listener);
}


