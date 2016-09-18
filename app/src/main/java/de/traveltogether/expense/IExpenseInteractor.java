package de.traveltogether.expense;

import de.traveltogether.IInteractor;

/**
 * Created by Anna-Lena on 12.05.2016.
 */
public interface IExpenseInteractor extends IInteractor {
    public void getExpenseList(long tripId, IExpensePresenter listener);
    public void getParticipantsForTrip(long tripId, IExpensePresenter listener);

}
