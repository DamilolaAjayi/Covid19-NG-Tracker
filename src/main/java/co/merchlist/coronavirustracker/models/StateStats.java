package co.merchlist.coronavirustracker.models;

public class StateStats {

    private String State;
    private int TotalNoOfCases;
    private int TotalNoDischarged;
    //total no of cases receiving treatment
    private int TotalNoOfCasesRT;
    private int TotalNoOfDeaths;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getTotalNoOfCases() {
        return TotalNoOfCases;
    }

    public void setTotalNoOfCases(int totalNoOfCases) {
        TotalNoOfCases = totalNoOfCases;
    }

    public int getTotalNoDischarged() {
        return TotalNoDischarged;
    }

    public void setTotalNoDischarged(int totalNoDischarged) {
        TotalNoDischarged = totalNoDischarged;
    }

    public int getTotalNoOfCasesRT() {
        return TotalNoOfCasesRT;
    }

    public void setTotalNoOfCasesRT(int totalNoOfCasesRT) {
        TotalNoOfCasesRT = totalNoOfCasesRT;
    }

    public int getTotalNoOfDeaths() {
        return TotalNoOfDeaths;
    }

    public void setTotalNoOfDeaths(int totalNoOfDeaths) {
        TotalNoOfDeaths = totalNoOfDeaths;
    }

    @Override
    public String toString() {
        return "StateStats{" +
                "State='" + State + '\'' +
                ", TotalNoOfCases=" + TotalNoOfCases +
                ", TotalNoDischarged=" + TotalNoDischarged +
                ", TotalNoOfCasesRT=" + TotalNoOfCasesRT +
                ", TotalNoOfDeaths=" + TotalNoOfDeaths +
                '}';
    }
}
