package de.traveltogether.model;

/**
 * Model class for Statistic
 */
public class Statistic {
    private double group;
    private double personal;
    private String startDate;


    public Statistic (double _group, double _personal, String _startDate){
        this.group = _group;
        this.personal = _personal;
        this.startDate = _startDate;
    }

    public double getGroup(){
        return group;
    }
    public double getPersonal(){
        return personal;
    }
    public String getStartDate(){ return startDate; }

    public void setGroup(double _group ) {
        group = _group;
    }
    public void setPersonal(double _personal) {
        personal = _personal;
    }
    public void setStartDate(String _startDate) {
        startDate = _startDate;
    }



}
