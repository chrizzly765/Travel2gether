package de.traveltogether.expense.newexpense;

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

    }
}
