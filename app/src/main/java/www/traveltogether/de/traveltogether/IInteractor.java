package www.traveltogether.de.traveltogether;

import www.traveltogether.de.traveltogether.model.Response;

/**
 * Created by Anna-Lena on 28.05.2016.
 */
public interface IInteractor {
    public void onRequestFinished(Response response, DataType dataType, ActionType actionType);
}
