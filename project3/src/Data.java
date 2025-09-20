package src;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Data class represents a collection of buses and accident reports.
 * It provides methods to load data from files, find buses by ID, and find reports by bus ID.
 */
public class Data {
    ArrayList<Bus> buses = new ArrayList<Bus>();

    /**
     * Constructs a new Data object.
     */
    public Data() {
    }

    /**
     * Loads the list of buses from a file.
     *
     * @param bfile the path of the file containing the bus data
     * @return the list of loaded buses
     */
    public ArrayList<Bus> loadBuses(String bfile) {
        Scanner bscan = null;
        ArrayList<Bus> busList = new ArrayList<Bus>();
        try {
            bscan = new Scanner(new File(bfile));
            while (bscan.hasNextLine()) {
                String[] nextLine = bscan.nextLine().split("_");
                String name = nextLine[0];
                String id = nextLine[1];
                String route = nextLine[2];
                int capacity = Integer.parseInt(nextLine[3]);
                String driver = nextLine[4];
                Bus bus = new Bus(name, id, route, capacity, driver);
                busList.add(bus);
            }

            bscan.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        buses = busList;
        return busList;
    }

    /**
     * Loads the list of accident reports from a file.
     *
     * @param bfile the path of the file containing the accident report data
     * @return the list of loaded accident reports
     */
    public ArrayList<AccidentReport> loadReports(String bfile) {
        Scanner bscan = null;
        ArrayList<AccidentReport> reportList = new ArrayList<AccidentReport>();
        try {
            bscan = new Scanner(new File(bfile));
            while (bscan.hasNextLine()) {
                String[] nextLine = bscan.nextLine().split("_");
                String id = nextLine[0];
                String report = nextLine[1];
                reportList.add(new AccidentReport(report, id));
            }

            bscan.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }

        return reportList;
    }

    /**
     * Finds a bus in the given list of buses by its ID.
     *
     * @param busList the list of buses to search in
     * @param id      the ID of the bus to find
     * @return the found bus, or null if no bus with the specified ID is found
     */
    public Bus findBusById(ArrayList<Bus> busList, String id) {
        for (Bus bus : busList) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null; // Bus with specified id not found
    }

    /**
     * Finds an accident report in the given list of reports by the bus ID.
     *
     * @param reportList the list of accident reports to search in
     * @param busId      the ID of the bus associated with the report
     * @return the found accident report, or null if no report for the specified bus ID is found
     */
    public AccidentReport findReportByBusId(ArrayList<AccidentReport> reportList, String busId) {
        for (AccidentReport report : reportList) {
            if (report.getBusId().equals(busId)) {
                return report;
            }
        }
        return null; // Report for specified bus id not found
    }
}
