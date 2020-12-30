package ex.deserialization.objects;

import java.io.Serializable;

public class Flight implements Serializable {

    private String airlineDisplayCode;
    private String departureAirport;
    private String arrivalAirport;
    private String originDate;
    private String flightStatus;
    private String scheduled;

    public String getAirlineDisplayCode() {
        if (airlineDisplayCode == null) return "";
        return airlineDisplayCode;
    }

    public String getDepartureAirport() {
        if (departureAirport == null) return "";
        return departureAirport;
    }

    public String getArrivalAirport() {
        if (arrivalAirport == null) return "";
        return arrivalAirport;
    }

    public String getOriginDate() {
        if (originDate == null) return "";
        return originDate;
    }

    public String getFlightStatus() {
        if (flightStatus == null) return "";
        return flightStatus;
    }

    public String getScheduledTime() {
        if (scheduled == null || scheduled.isEmpty()) return "";

        return scheduled.substring(11, 19);
    }

    public String getScheduled() {
        if (scheduled == null) return "";
        return scheduled;
    }

    public void setAirlineDisplayCode(String airlineDisplayCode) {
        this.airlineDisplayCode = airlineDisplayCode;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public void setOriginDate(String originDate) {
        this.originDate = originDate;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public void setScheduled(String scheduled) {
        this.scheduled = scheduled;
    }

    public String toString() {
        return airlineDisplayCode + "|" + departureAirport + "|" + arrivalAirport + "|" + originDate + "|" + flightStatus + "|" + scheduled;
    }
}
