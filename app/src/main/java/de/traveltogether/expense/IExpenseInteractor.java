package de.traveltogether.expense;

import de.traveltogether.IInteractor;


interface IExpenseInteractor extends IInteractor {
     void getExpenseList(long tripId, IExpensePresenter listener);
     void getParticipantsForTrip(long tripId, IExpensePresenter listener);

}
