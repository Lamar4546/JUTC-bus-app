package src;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Represents the JUTC Operation Management application.
 * This class extends JFrame and implements ActionListener.
 */
public class JUTCOperation extends JFrame implements ActionListener {
    private JLabel label;
    private JButton addBusButton;
    private JButton reportAccidentButton;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemEdit;

    private JPopupMenu popupReportMenu;
    private JMenuItem menuItemReportRemove;

    private JMenuItem menuItemRemove;
    private JMenuItem menuItemRemoveAll;
    private JButton cmdSortByBusId;
    private JTable busTable;
    private JTable accidentTable;
    private JTabbedPane tabManager;

    private JUTCOperation thisScreen;

    private ArrayList<Bus> blist;
    private ArrayList<AccidentReport> alist;
    private DefaultTableModel busModel;
    private DefaultTableModel accidentModel;
    private Data data; // Declare busModel as a class member

    /**
     * Constructs a new instance of the JUTCOperation class.
     * Initializes the GUI components and sets up the initial state of the
     * application.
     */
    public JUTCOperation() {
        thisScreen = this;
        tabManager = new JTabbedPane(JTabbedPane.TOP);

        setTitle("JUTC Operation Management");
        setSize(950, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize data structures and models
        blist = new ArrayList<>();
        data = new Data();
        blist = data.loadBuses("BusData.txt");
        alist = data.loadReports("AccidentData.txt");
        busModel = new DefaultTableModel();
        busModel.addColumn("License Plate Number");
        busModel.addColumn("Name");
        busModel.addColumn("Route");
        busModel.addColumn("Capacity");
        busModel.addColumn("Driver");

        accidentModel = new DefaultTableModel();
        accidentModel.addColumn("License Plate Number");
        accidentModel.addColumn("Report");

        // Create and configure tables
        busTable = new JTable();
        busTable.setModel(busModel);
        showBusTable(blist);

        accidentTable = new JTable(accidentModel);
        showReportTable(alist);

        busTable.setPreferredScrollableViewportSize(new Dimension(500, blist.size() * 2));
        busTable.setFillsViewportHeight(true);

        // Create and configure popup menus
        popupMenu = new JPopupMenu();
        menuItemEdit = new JMenuItem("Edit Bus");
        menuItemRemove = new JMenuItem("Remove Bus");
        menuItemRemoveAll = new JMenuItem("Remove All Buses");

        popupReportMenu = new JPopupMenu();
        menuItemReportRemove = new JMenuItem("Delete Report");

        menuItemEdit.addActionListener(this);
        menuItemRemove.addActionListener(this);
        menuItemRemoveAll.addActionListener(this);

        menuItemReportRemove.addActionListener(this);

        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemRemove);
        popupMenu.add(menuItemRemoveAll);

        popupReportMenu.add(menuItemReportRemove);

        // Set popup menus for the tables
        busTable.setComponentPopupMenu(popupMenu);
        accidentTable.setComponentPopupMenu(popupReportMenu);

        busTable.addMouseListener(new TableMouseListener(busTable));
        accidentTable.addMouseListener(new TableMouseListener(accidentTable));

        // Create components
        label = new JLabel("\n");
        addBusButton = new JButton("Add New Bus");
        reportAccidentButton = new JButton("Report Accident");
        cmdSortByBusId = new JButton("Sort By License Plate Number");

        addBusButton.setBackground(Color.CYAN);
        reportAccidentButton.setBackground(Color.CYAN);

        // Add action listeners to the buttons
        addBusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display BusEntry window when "Add New Bus" button is clicked
                BusEntry busEntry = new BusEntry(JUTCOperation.this);
                busEntry.setVisible(true);
            }
        });

        reportAccidentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display AccidentReports window when "Report Accident" button is clicked
                AccidentReportsScreen accidentReports = new AccidentReportsScreen(JUTCOperation.this);
                accidentReports.setVisible(true);
            }
        });

        cmdSortByBusId.addActionListener(new SortByButtonListener());

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(1, 2, 10, 10));
        add(label);
        contentPane.setOpaque(true);
        setContentPane(contentPane);

        JPanel busPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBusButton);
        buttonPanel.add(cmdSortByBusId);
        busPanel.add(buttonPanel, BorderLayout.NORTH);
        busPanel.add(new JScrollPane(busTable), BorderLayout.CENTER);

        JPanel accidentPanel = new JPanel();
        accidentPanel.setLayout(new BoxLayout(accidentPanel, BoxLayout.Y_AXIS));
        reportAccidentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        accidentPanel.add(reportAccidentButton);
        accidentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        accidentPanel.add(new JScrollPane(accidentTable));

        tabManager.addTab("Buses", null, busPanel, "Manage Buses");
        tabManager.addTab("Accident Reports", null, accidentPanel, "Manage Accident Reports");
        contentPane.add(tabManager);
        setVisible(true);
    }

    /**
     * The entry point of the application.
     * Creates an instance of JUTCOperation and starts the application.
     * 
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JUTCOperation();
            }
        });
    }

    /**
     * ActionListener implementation for the Sort By License Plate Number button.
     * Sorts the bus list by license plate number and updates the bus table.
     */
    private class SortByButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ArrayList<Bus> buslist = getBusList();
            busModel.setRowCount(0);
            Collections.sort(buslist, new LicenseSorter());
            showBusTable(buslist);
        }
    }

    /**
     * Comparator implementation for sorting buses by license plate number.
     */
    private class LicenseSorter implements Comparator<Bus> {
        public int compare(Bus bus1, Bus bus2) {
            return bus1.getId().compareTo(bus2.getId());
        }
    }

    /**
     * Retrieves the list of buses.
     * 
     * @return The list of buses.
     */
    public ArrayList<Bus> getBusList() {
        return blist;
    }

    /**
     * Retrieves the list of accident reports.
     * 
     * @return The list of accident reports.
     */
    public ArrayList<AccidentReport> getReportList() {
        return alist;
    }

    /**
     * Adds a new bus to the bus list and updates the bus table.
     * Also writes the bus information to the BusData.txt file.
     * 
     * @param bus The bus to be added.
     */
    public void addBus(Bus bus) {
        blist.add(bus);
        addToBusTable(bus);

        try {
            FileWriter filewriter = new FileWriter("BusData.txt", true);
            String busData = bus.getName() + "_" + bus.getId() + "_" + bus.getRoute() + "_" + bus.getCapacity() + "_"
                    + bus.getDriver();
            filewriter.write(busData);
            filewriter.write(System.getProperty("line.separator"));
            filewriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Adds a new accident report to the report list and updates the report table.
     * Also writes the report information to the AccidentData.txt file.
     * 
     * @param ar The accident report to be added.
     */
    public void addReport(AccidentReport ar) {
        alist.add(ar);
        addToReportTable(ar);

        try {
            FileWriter filewriter = new FileWriter("AccidentData.txt", true);
            String busData = ar.getBusId() + "_" + ar.getReport();
            filewriter.write(busData);
            filewriter.write(System.getProperty("line.separator"));
            filewriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Displays the list of buses in the bus table.
     * 
     * @param blist The list of buses to be displayed.
     */
    private void showBusTable(ArrayList<Bus> blist) {
        for (Bus bus : blist)
            addToBusTable(bus);
    }

    /**
     * Adds a bus to the bus table.
     * 
     * @param b The bus to be added.
     */
    private void addToBusTable(Bus b) {
        String[] item = { b.getId(), b.getName(), b.getRoute(), String.valueOf(b.getCapacity()), b.getDriver() };
        busModel.addRow(item);
    }

    /**
     * Adds an accident report to the report table.
     * 
     * @param ar The accident report to be added.
     */
    private void addToReportTable(AccidentReport ar) {
        String[] item = { ar.getBusId(), ar.getReport() };
        accidentModel.addRow(item);
    }

    /**
     * Displays the list of accident reports in the report table.
     * 
     * @param reports The list of accident reports to be displayed.
     */
    private void showReportTable(ArrayList<AccidentReport> reports) {
        for (AccidentReport report : reports) {
            addToReportTable(report);
        }
    }

    /**
     * Updates the bus table with the provided updated list of buses.
     * Also updates the BusData.txt file with the updated list.
     * 
     * @param updatedList The updated list of buses.
     */
    public void updateBusTable(ArrayList<Bus> updatedList) {
        busModel.setRowCount(0);
        blist = updatedList;
        showBusTable(blist);

        try {
            FileWriter filewriter = new FileWriter("BusData.txt", false);
            filewriter.write("");
            filewriter.close();

            filewriter = new FileWriter("BusData.txt", true);
            for (Bus bus : blist) {
                String busData = bus.getName() + "_" + bus.getId() + "_" + bus.getRoute() + "_" + bus.getCapacity()
                        + "_" + bus.getDriver();
                filewriter.write(busData);
                filewriter.write(System.getProperty("line.separator"));
            }
            filewriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * ActionListener implementation for handling menu item actions.
     * 
     * @param event The action event.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
        if (menu == menuItemEdit) {
            editRow();
        } else if (menu == menuItemRemove) {
            removeCurrentRow();
        } else if (menu == menuItemRemoveAll) {
            removeAllRows();
        } else if (menu == menuItemReportRemove) {
            removeReport();
        }
    }

    /**
     * Handles the action of editing a row in the bus table.
     */
    private void editRow() {
        try {
            // Displays a frame to edit the selected bus's information
            int row = busTable.getSelectedRow();
            new ModifyScreen(thisScreen, row);
        } catch (Exception ie) {
            JOptionPane.showMessageDialog(thisScreen, "Please select a row in the table in order to edit/delete");
        }
        busTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles the action of removing the current row in the bus table.
     */
    private void removeCurrentRow() {
        try {

            int row = busTable.getSelectedRow();
            int id = row;
            int response = JOptionPane.showConfirmDialog(thisScreen,
                    "Do you want to permanently delete this bus information?", "Alert", JOptionPane.YES_OPTION);

            // // Move the selected bus to recycle file if user selects no
            if (response == JOptionPane.YES_OPTION) {
                // Removes bus from the table and the arraylist
                busModel.removeRow(row);
                blist.remove(id);

                // Updates the buss file with the arraylist
                try {
                    FileWriter filewriter = null;
                    filewriter = new FileWriter("BusData.txt", false);
                    filewriter.write("");
                    filewriter = new FileWriter("BusData.txt", true);
                    for (Bus bus : blist) {
                        String busData = bus.getName() + "_" + bus.getId() + "_" + bus.getRoute() + "_"
                                + bus.getCapacity() + "_" + bus.getDriver();
                        filewriter.write(busData);
                        filewriter.write(System.getProperty("line.separator"));
                    }
                    filewriter.close();
                    busTable.getSelectionModel().clearSelection();
                } catch (IOException ioe) {
                }
            }

        } catch (Exception ie) {
            JOptionPane.showMessageDialog(thisScreen, "Please select a row in the table in order to edit/delete");
        }
        busTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles the action of removing all rows in the bus table.
     */
    private void removeAllRows() {
        try {
            int response = JOptionPane.showConfirmDialog(thisScreen,
                    "Do you want to permanently delete the bus information?", "Alert", JOptionPane.YES_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                // Removes bus from the table and the arraylist
                blist.clear();
                int rowCount = busModel.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    busModel.removeRow(0);
                }
                // Updates the buss file with the arraylist
                try {
                    FileWriter filewriter = null;
                    filewriter = new FileWriter("BusData.txt", false);
                    filewriter.write("");
                    filewriter.close();
                } catch (IOException ioe) {
                }
            }

        } catch (Exception ie) {
            JOptionPane.showMessageDialog(thisScreen, "Please select a row in the table in order to edit/delete");
        }
        busTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles the action of removing a report from the report table.
     */
    private void removeReport() {
        try {

            int row = accidentTable.getSelectedRow();
            int id = row;
            int response = JOptionPane.showConfirmDialog(thisScreen,
                    "Do you want to permanently delete this report?", "Alert", JOptionPane.YES_OPTION);

            // // Move the selected bus to recycle file if user selects no
            if (response == JOptionPane.YES_OPTION) {
                // Removes bus from the table and the arraylist
                accidentModel.removeRow(row);
                alist.remove(id);

                // Updates the buss file with the arraylist
                try {
                    FileWriter filewriter = null;
                    filewriter = new FileWriter("AccidentData.txt", false);
                    filewriter.write("");
                    filewriter = new FileWriter("AccidentData.txt", true);
                    for (AccidentReport ar : alist) {
                        String reportData = ar.getBusId() + "_" + ar.getReport();
                        filewriter.write(reportData);
                        filewriter.write(System.getProperty("line.separator"));
                    }
                    filewriter.close();
                    busTable.getSelectionModel().clearSelection();
                } catch (IOException ioe) {
                }
            }

        } catch (Exception ie) {
            JOptionPane.showMessageDialog(thisScreen, "Please select a row in the table in order to edit/delete");
        }
        accidentTable.getSelectionModel().clearSelection();
    }
}
