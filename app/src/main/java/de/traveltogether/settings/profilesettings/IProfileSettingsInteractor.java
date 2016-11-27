package de.traveltogether.settings.profilesettings;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Person;
import de.traveltogether.servercommunication.HttpRequest;

/**
 * Created by Anna-Lena on 27.11.2016.
 */

public interface IProfileSettingsInteractor extends IInteractor {
    public void getProfileInfos(IProfileSettingsPresenter _listener);
    public void changePasswort(String salt, String hash, IProfileSettingsPresenter _listener);
    public void updateProfileInfos(Person person, IProfileSettingsPresenter _listener);
    public void getSalt(String email, IProfileSettingsPresenter _listener);
}
