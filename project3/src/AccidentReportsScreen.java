package src;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import javax.swing.*;

/**
 * Represents a screen for entering and saving accident reports for buses.
 */
public class AccidentReportsScreen extends JFrame {
  private JTextField report;
  private JTextField busId;
  private JButton cmdSave;
  private JUTCOperation jutcoperation;
  AccidentReportsScreen cuReportsScreen;

  /**
   * Constructs a new AccidentReportsScreen object.
   * 
   * @param jutcoperation the JUTCOperation object used for adding accident reports
   */
  public AccidentReportsScreen(JUTCOperation jutcoperation) {
    cuReportsScreen = this;
    this.jutcoperation = jutcoperation;
    setTitle("Bus Accident Report");

    JPanel pnlDisplay = new JPanel(); // Initialize pnlDisplay
    pnlDisplay.setLayout(new GridLayout(2, 2));

    // First entry
    pnlDisplay.add(new JLabel("License Plate Number:"));
    busId = new JTextField(10);
    pnlDisplay.add(busId);

    // Second Entry
    pnlDisplay.add(new JLabel("Accident Report"));
    report = new JTextField(10);
    pnlDisplay.add(report);

    // Buttons
    JPanel pnlCommand = new JPanel(); // Initialize pnlCommand
    cmdSave = new JButton("Save");
    cmdSave.setBackground(Color.CYAN);
    cmdSave.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String busIdText = busId.getText();
        String reportText = report.getText();

        if (reportText.isEmpty() || busIdText.isEmpty()) {
          JOptionPane.showMessageDialog(AccidentReportsScreen.this, "Please fill out all fields.");
          // jutcoperation.addBus(busName);
          setVisible(false); // Hide AccidentReports window after saving
        } else {
          Data data = new Data();
          ArrayList<Bus> busList = data.loadBuses("BusData.txt");
          Bus busFound = data.findBusById(busList, busIdText);

          if(busFound == null) {
            JOptionPane.showMessageDialog(AccidentReportsScreen.this, "Bus not found.");
          } else {
            AccidentReport accidentReport = new AccidentReport(reportText, busIdText);
            jutcoperation.addReport(accidentReport);
            JOptionPane.showMessageDialog(AccidentReportsScreen.this, "Report saved.");
            setVisible(false); // Hide AccidentReports window after saving
          }
        }
      }
    });

    pnlCommand.add(cmdSave); // Add cmdSave button to pnlCommand

    // Add panels to frame
    add(pnlDisplay, BorderLayout.CENTER);
    add(pnlCommand, BorderLayout.SOUTH);

    pack(); // Adjusts the frame size based on its components
    setLocationRelativeTo(jutcoperation);
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);// Centers the frame on the screen
    setVisible(true);
  }

}
