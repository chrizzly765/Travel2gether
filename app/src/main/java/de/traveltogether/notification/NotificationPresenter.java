package de.traveltogether.notification;

import de.traveltogether.ActionType;
import de.traveltogether.model.Notification;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class NotificationPresenter implements INotificationPresenter {

    INotificationInteractor interactor;
    NotificationActivity view;

    public NotificationPresenter(NotificationActivity _view){
        view= _view;
        interactor= new NotificationInteractor();
    }

    @Override
    public void onAnswerInvitation(long tripId, ActionType type) {
        interactor.answerInvitation(tripId,type,this);
    }

    @Override
    public void onGetNotificationList() {
        interactor.getNotificationList(this);
    }

    @Override
    public void onViewNotificationList(Notification[] notifications) {
        view.onViewNotificationList(notifications);
    }

    @Override
    public void onSetNotificationRead(long notificationId) {
        interactor.setNotificationRead(notificationId, this);
    }

    @Override
    public void onSuccessSetNotificationRead() {
        view.onSuccessSetNotificationRead();
    }

    @Override
    public void onError(String message) {
        view.onViewErrorMessage(message);
    }
}
