package de.traveltogether;

import de.traveltogether.model.Response;

/**
 * Interface for Interactors
 */
public interface IInteractor {
    /**
     * Has to be implemented to receive http response from HttpRequest class
     * @param response received json response from server, has to be parsed
     * @param dataType DataType of the request and response
     * @param actionType ActionType of the request and response
     */
    void onRequestFinished(Response response, DataType dataType, ActionType actionType);
}
