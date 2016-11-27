package de.traveltogether.settings.profilesettings;

import de.traveltogether.model.Person;

/**
 * Created by Anna-Lena on 27.11.2016.
 */

public interface IProfileSettingsPresenter {
    public void onGetProfileInfos();
    public void onUpdateProfileInfos(Person person);
    public void onUpdatePasswort(String salt, String hash);
    public void onSuccessGetProfileInfos(Person person);
    public void onSuccessUpdateProfileInfos();
    public void onSuccessUpdatePasswort();
    public void onError(String message);
}
