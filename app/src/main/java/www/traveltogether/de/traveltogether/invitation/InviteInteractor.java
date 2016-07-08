package www.traveltogether.de.traveltogether.invitation;

import org.json.JSONObject;

import java.util.ArrayList;

import www.traveltogether.de.traveltogether.ActionType;
import www.traveltogether.de.traveltogether.DataType;
import www.traveltogether.de.traveltogether.StaticData;
import www.traveltogether.de.traveltogether.model.Participant;
import www.traveltogether.de.traveltogether.model.Person;
import www.traveltogether.de.traveltogether.model.Response;
import www.traveltogether.de.traveltogether.servercommunication.HttpRequest;
import www.traveltogether.de.traveltogether.servercommunication.JsonDecode;

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
