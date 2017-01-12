package de.traveltogether.options.profilesettings;

import de.traveltogether.IInteractor;
import de.traveltogether.model.Person;

/**
 * Interface for Interactor of ProfileSettingsActivity
 */
interface IProfileSettingsInteractor extends IInteractor {
    void getProfileInfos(IProfileSettingsPresenter _listener);
    void changePasswort(String salt, String hash, IProfileSettingsPresenter _listener);
    void updateProfileInfos(Person person, IProfileSettingsPresenter _listener);
    void getSalt(String email, IProfileSettingsPresenter _listener);
}
