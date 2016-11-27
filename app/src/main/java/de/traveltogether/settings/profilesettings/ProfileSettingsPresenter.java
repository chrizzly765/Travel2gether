package de.traveltogether.settings.profilesettings;

import de.traveltogether.model.Person;

/**
 * Created by Anna-Lena on 27.11.2016.
 */

public class ProfileSettingsPresenter implements IProfileSettingsPresenter {
    ProfileSettingsActivity view;
    IProfileSettingsInteractor interactor;
    public ProfileSettingsPresenter(ProfileSettingsActivity activity){
        view=activity;
        interactor = new ProfileSettingsInteractor();
    }
    @Override
    public void onGetProfileInfos() {
        interactor.getProfileInfos(this);
    }

    @Override
    public void onUpdateProfileInfos(Person person) {
        interactor.updateProfileInfos(person, this);
    }

    @Override
    public void onUpdatePasswort(String salt, String hash) {
        interactor.changePasswort(salt, hash, this);
    }

    @Override
    public void onSuccessGetProfileInfos(Person person) {
        view.onSuccessGetProfileInfos(person);
    }

    @Override
    public void onSuccessUpdateProfileInfos() {
        view.onSuccessUpdateProfileInfos();
    }

    @Override
    public void onSuccessUpdatePasswort() {
        view.onSuccessUpdatePasswort();
    }

    @Override
    public void onError(String message) {
        view.onError(message);
    }
}
