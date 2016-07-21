package de.traveltogether.invitation;

import org.json.JSONObject;

import de.traveltogether.ActionType;
import de.traveltogether.DataType;
import de.traveltogether.StaticData;
import de.traveltogether.model.Response;
import de.traveltogether.servercommunication.HttpRequest;

/**
 * Created by Anna-Lena on 16.05.2016.
 */
public class InviteInteractor implements IInviteInteractor {
    IInvitePresenter listener;

    @Override
    public void getFormerParticipants(IInvitePresenter _listener) {
        listener = _listener;
        listener = _listener;
        JSONObject obj = new JSONObject();
        try {
            obj.put("personId", StaticData.getUserId());
        } catch (Exception e) {

        }
        HttpRequest req = new HttpRequest(DataType.INVITATION, ActionType.GETPARTICIPANTS, obj.toString(), this);
    }

    @Override
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType) {
        if (response.getError() == "true") {
            listener.onError(response.getMessage());
        } else {
            //TODO parse data and give back to presenter
            /*ArrayList<Person> persons = JsonDecode.getInstance().jsonToArray(response.getData(), DataType.PERSON);
            Person[] personArray= new Person[persons.size()];
            if(persons.size()>0) {
                int i = 0;
                for (Person t : persons) {
                    personArray[i] = t;
                    i++;
                }
            }
            listener.onSuccess(personArray);*/
        }
    }
}
