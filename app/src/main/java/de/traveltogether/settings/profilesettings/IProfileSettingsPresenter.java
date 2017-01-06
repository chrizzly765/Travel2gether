package de.traveltogether.settings.profilesettings;

import de.traveltogether.model.Person;


interface IProfileSettingsPresenter {
     void onGetProfileInfos();
     void onUpdateProfileInfos(Person person);
     void onUpdatePasswort(String salt, String hash);
     void onSuccessGetProfileInfos(Person person);
     void onSuccessUpdateProfileInfos();
     void onSuccessUpdatePasswort();
     void onError(String message);
     void onReturnSalt(String salt);
     void onGetSalt(String email);
}
