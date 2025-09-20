package src;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The ModifyScreen class represents a JFrame that allows the user to modify bus information.
 */
public class ModifyScreen extends JFrame {
    private JTextField txtID; // name
    private JTextField txtName; // name
    private JTextField txtRoute; // route
    private JTextField txtCapacity; // capacity
    private JTextField txtDriver; // driver
    private JButton cmdSave;
    private JButton cmdClose;

    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    JUTCOperation mainscreen;
    ModifyScreen modify;
    int id;
    Bus bus;

    /**
     * Constructs a ModifyScreen object.
     *
     * @param mainscreen the main screen object
     * @param id         the ID of the bus to be modified
     */
    public ModifyScreen(JUTCOperation mainscreen, int id) {
        this.mainscreen = mainscreen;
        this.modify = this;
        this.id = id;

        setTitle("Edit " + mainscreen.getBusList().get(id).getId() + " Bus Information");

        this.bus = this.mainscreen.getBusList().get(id);

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Name:"));
        txtName = new JTextField(bus.getName(), 20);
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("Route:"));
        txtRoute = new JTextField(bus.getRoute(), 20);
        pnlDisplay.add(txtRoute);
        pnlDisplay.add(new JLabel("Capacity:"));
        txtCapacity = new JTextField("" + bus.getCapacity(), 20);
        pnlDisplay.add(txtCapacity);
        pnlDisplay.add(new JLabel("Driver:"));
        txtDriver = new JTextField(bus.getDriver(), 20);
        pnlDisplay.add(txtDriver);
        pnlDisplay.setLayout(new GridLayout(4, 2));

        cmdClose = new JButton("Close");
        cmdSave = new JButton("Save Changes");
        cmdClose.addActionListener(new ButtonListener());
        cmdSave.addActionListener(new ButtonListener());
        cmdSave.setBackground(Color.GREEN);
        cmdClose.setBackground(Color.RED);
        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);

        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    /**
     * ActionListener implementation for the buttons in the ModifyScreen.
     */
    private class ButtonListener implements ActionListener {
        /**
         * Performs an action when a button is clicked.
         *
         * @param e the ActionEvent object
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cmdClose) {
                modify.setVisible(false);
            } else if (e.getSource() == cmdSave) {
                try {
                    String name = txtName.getText();
                    String route = txtRoute.getText();
                    int capacity = Integer.parseInt(txtCapacity.getText());
                    String driver = txtDriver.getText();
                    boolean isAbsent = true;
                    ArrayList<Bus> updatedList = mainscreen.getBusList();

                    int index = 0;
                    for (Bus bus : updatedList) {
                        if (bus.getName().equals(name) && id != index) {
                            JOptionPane.showMessageDialog(null, "Bus name already exists ");
                            isAbsent = false;
                            break;
                        }
                        index++;
                    }
                    if (isAbsent) {
                        bus.setName(name);
                        bus.setRoute(route);
                        bus.setCapacity(capacity);
                        bus.setDriver(driver);

                        updatedList.set(id, bus);

                        mainscreen.updateBusTable(updatedList);

                        modify.setVisible(false);
                    }

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Please enter numerical values for the capacity");
                }
            }
        }
    }
}
