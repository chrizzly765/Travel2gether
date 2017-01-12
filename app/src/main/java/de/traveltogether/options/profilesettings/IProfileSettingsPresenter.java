package de.traveltogether.options.profilesettings;

import de.traveltogether.model.Person;

/**
 * Presenter for Presenter of ProfileSettingsActivity
 */
interface IProfileSettingsPresenter {
     void onGetProfileInfos();
     void onUpdateProfileInfos(Person person);
     void onUpdatePassword(String salt, String hash);
     void onSuccessGetProfileInfos(Person person);
     void onSuccessUpdateProfileInfos();
     void onSuccessUpdatePassword();
     void onError(String message);
     void onReturnSalt(String salt);
     void onGetSalt(String email);
}
