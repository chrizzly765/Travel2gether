package de.traveltogether.notification;

import de.traveltogether.ActionType;
import de.traveltogether.model.Notification;

/**
 * Interface for presenter of NotificationActivity
 */
interface INotificationPresenter {
    void onAnswerInvitation(long tripId, ActionType type);
    void onGetNotificationList();
    void onViewNotificationList(Notification[] notifications);
    void onSetNotificationRead(long notificationId);
    void onSuccessSetNotificationRead();
    void onError(String message);
}
