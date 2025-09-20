package src;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Represents a screen for creating a bus entry.
 */
public class BusEntry extends JFrame {
    private JTextField busNameField;
    private JTextField busIdField;
    private JTextField busRouteField;
    private JTextField busCapacityField;
    private JTextField busDriverField;
    private JLabel busNameLabel;
    private JLabel busIdLabel;
    private JLabel busRouteLabel;
    private JLabel busCapacityLabel;
    private JLabel busDriverLabel;
    private JButton addBusButton;
    private JButton cancelButton;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    BusEntry currAddBusScreen;
    JUTCOperation mainScreen;

    /**
     * Constructs a BusEntry object.
     * 
     * @param mainScreen the main screen of the application
     */
    public BusEntry(JUTCOperation mainScreen) {
        currAddBusScreen = this;

        // Establishes a link between this screen and the main screen.
        this.mainScreen = mainScreen;

        setTitle("Creating bus");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        // Accepts the name of the bus that the user wants to create.
        busNameLabel = new JLabel("Bus Name");
        pnlDisplay.add(busNameLabel);
        busNameField = new JTextField(20);
        pnlDisplay.add(busNameField);

        // Accepts the ID of the bus being created.
        busIdLabel = new JLabel("License Plate Number");
        pnlDisplay.add(busIdLabel);
        busIdField = new JTextField(20);
        pnlDisplay.add(busIdField);

        // Accepts the route of the bus being created.
        busRouteLabel = new JLabel("Bus Route");
        pnlDisplay.add(busRouteLabel);
        busRouteField = new JTextField(20);
        pnlDisplay.add(busRouteField);

        // Accepts the capacity of the bus being created.
        busCapacityLabel = new JLabel("Bus Capacity");
        pnlDisplay.add(busCapacityLabel);
        busCapacityField = new JTextField(20);
        pnlDisplay.add(busCapacityField);

        // Accepts the driver of the bus being created.
        busDriverLabel = new JLabel("Bus Driver");
        pnlDisplay.add(busDriverLabel);
        busDriverField = new JTextField(20);
        pnlDisplay.add(busDriverField);

        pnlDisplay.setLayout(new GridLayout(5, 2));

        // Implementing the "Add Bus" button.
        addBusButton = new JButton("Add Bus");
        addBusButton.addActionListener(new AddBusListener());
        pnlCommand.add(addBusButton);

        // Implementing the "Cancel" button that enables the user to exit the screen.
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new CancelListener());
        pnlCommand.add(cancelButton);

        // Organizing the screen.
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * ActionListener for the "Add Bus" button.
     */
    private class AddBusListener implements ActionListener {
        /**
         * Performs the action when the "Add Bus" button is clicked.
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            try {
                String nameInput = busNameField.getText();
                String idInput = busIdField.getText();
                String routeInput = busRouteField.getText();
                int capacityInput = Integer.parseInt(busCapacityField.getText());
                String driverInput = busDriverField.getText();

                if (nameInput.isEmpty() || idInput.isEmpty() || routeInput.isEmpty()
                        || busCapacityField.getText().isEmpty() || driverInput.isEmpty()) {
                    JOptionPane.showMessageDialog(currAddBusScreen, "Please fill in all fields.");
                } else {
                    Data data = new Data();
                    if (data.findBusById(mainScreen.getBusList(), idInput) != null || data.findReportByBusId(
                            mainScreen.getReportList(), idInput) != null) {
                        JOptionPane.showMessageDialog(currAddBusScreen, "ID already exists in the system.");
                    } else {
                        Bus bus = new Bus(nameInput, idInput, routeInput, capacityInput, driverInput);
                        mainScreen.addBus(bus);
                        JOptionPane.showMessageDialog(currAddBusScreen, "Successfully Added!");
                        currAddBusScreen.setVisible(false);
                    }

                }

            } catch (NumberFormatException nfe) {
                // An error message is displayed if the user's input for the capacity is not a
                // number.
                JOptionPane.showMessageDialog(null, "Please enter a numerical value for the capacity.");
            }
        }
    }

    /**
     * ActionListener for the "Cancel" button.
     */
    private class CancelListener implements ActionListener {
        /**
         * Performs the action when the "Cancel" button is clicked.
         * 
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e) {
            currAddBusScreen.setVisible(false);
        }
    }
}