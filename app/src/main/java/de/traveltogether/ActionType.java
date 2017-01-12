package de.traveltogether;

/**
 * Enum for the action type of a http request. Not every action type are combinable with every data type
 */
public enum ActionType {
    //actions for features and trip
    LIST,
    DETAIL,
    ADD,
    UPDATE,
    DELETE,
    //actions for registration, login and password
    REGISTER,
    LOGIN,
    GETSALT,
    UPDATEDEVICEID,
    UPDATEPASSWORD,
    FORGOTPASSWORD,
    //actions for invitation
    GETPARTICIPANTS,
    RESIGN,
    ACCEPT,
    DECLINE,
    INVITE,
    //actions for main trip menu
    GETSTATISTIC,
    GETNOTICOUNT,
    GETTITLE
}
