package src;
/**
 * The AccidentReport class represents an accident report for a bus.
 */
public class AccidentReport {
    private String report;
    private String busId;

    /**
     * Constructs an AccidentReport object with the specified report and bus ID.
     *
     * @param report the accident report
     * @param busId  the ID of the bus involved in the accident
     */
    public AccidentReport(String report, String busId) {
        this.report = report;
        this.busId = busId;
    }

    /**
     * Sets the accident report.
     *
     * @param report the accident report
     */
    public void setReport(String report) {
        this.report = report;
    }

    /**
     * Sets the ID of the bus involved in the accident.
     *
     * @param busId the ID of the bus
     */
    public void setBusId(String busId) {
        this.busId = busId;
    }

    /**
     * Returns the accident report.
     *
     * @return the accident report
     */
    public String getReport() {
        return report;
    }

    /**
     * Returns the ID of the bus involved in the accident.
     *
     * @return the ID of the bus
     */
    public String getBusId() {
        return busId;
    }
}
