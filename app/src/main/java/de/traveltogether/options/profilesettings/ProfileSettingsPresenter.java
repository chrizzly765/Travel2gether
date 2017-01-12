package de.traveltogether.options.profilesettings;

import de.traveltogether.model.Person;

/**
 * Presenter for ProfileSettingsActivity
 * implements IProfileSettingsPresenter
 */
public class ProfileSettingsPresenter implements IProfileSettingsPresenter {
    private ProfileSettingsActivity view;
    private IProfileSettingsInteractor interactor;

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
    public void onUpdatePassword(String salt, String hash) {
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
    public void onSuccessUpdatePassword() {
        view.onSuccessUpdatePasswort();
    }

    @Override
    public void onError(String message) {
        view.onError(message);
    }

    @Override
    public void onReturnSalt(String salt) {
        view.onSuccessGetSalt(salt);
    }

    @Override
    public void onGetSalt(String email) {
        interactor.getSalt(email, this);
    }
}
