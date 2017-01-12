package de.traveltogether.notification;

import de.traveltogether.ActionType;
import de.traveltogether.IInteractor;

/**
 * Interface for Interactor of NotificationActivity
 */
interface INotificationInteractor extends IInteractor {
    void answerInvitation(long tripId,  ActionType type, INotificationPresenter listener);
    void getNotificationList(INotificationPresenter listener);
    void setNotificationRead(long NotificationId, INotificationPresenter listener);
}

