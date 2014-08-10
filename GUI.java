import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Interface used for testing makint it easier to change parameters
 * @author Home
 *
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	JMenuBar menubar;
	JPanel panel;
	JButton btnStart;
	JLabel lblNrDr, lblAvgPatArr, lblSimuTime, lblP1min, lblP1max, lblP2min,
			lblP2max, lblP3min, lblP3max;
	JSpinner spinP1min, spinP2min, spinP3min, spinP1max, spinP2max, spinP3max,
			spinNrDr, spinAvgPatArr, spinSimuTime, spinDeathMin, spinDeathMax,
			spinRuns;
	JCheckBox chkInitialphase, chkDeathOfPatients, chkPrio3Kicks1;

	@SuppressWarnings("deprecation")
	public GUI() {
		int width = 400;
		int height = 400;

		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);
		setResizable(false);

		/*
		 * menubar
		 */
		menubar = new JMenuBar();
		JMenu menuSettings = new JMenu("Settings");
		menuSettings.setMnemonic(KeyEvent.VK_S);
		JMenuItem menuitemReset = new JMenuItem("Reset");
		menuitemReset.setToolTipText("Restore default settings");
		menuitemReset.setMnemonic(KeyEvent.VK_R);

		menuitemReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Restoring default settings.");
				spinP1min.setValue(new Integer(10));
				spinP1max.setValue(new Integer(30));
				spinP2min.setValue(new Integer(10));
				spinP2max.setValue(new Integer(20));
				spinP3min.setValue(new Integer(50));
				spinP3max.setValue(new Integer(120));
				spinDeathMin.setValue(new Integer(15));
				spinDeathMax.setValue(new Integer(25));
				spinRuns.setValue(new Integer(1));

				spinNrDr.setValue(new Integer(2));
				spinAvgPatArr.setValue(new Integer(40));
				spinSimuTime.setValue(new Integer(28800));
				chkDeathOfPatients.setSelected(false);
				chkInitialphase.setSelected(false);
				chkPrio3Kicks1.setSelected(false);
			}
		});

		JMenuItem menuitemsetRuns100 = new JMenuItem("Runs 100");
		menuitemsetRuns100.setToolTipText("Set the runs to 100");
		menuitemsetRuns100.setMnemonic(KeyEvent.VK_H);

		menuitemsetRuns100.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Runs are set to 100");
				spinRuns.setValue(new Integer(100));
			}
		});

		menuSettings.add(menuitemReset);
		menuSettings.add(menuitemsetRuns100);

		menubar.add(menuSettings);
		setJMenuBar(menubar);

		/*
		 * Textfields & Labels
		 */
		/*
		 * SimuZeit /
		 */

		lblNrDr = new JLabel("Number of doctors:");
		lblNrDr.setBounds(10, 15, 120, 10);

		SpinnerModel NrDr = new SpinnerNumberModel(2, 1, 10, 1);
		spinNrDr = new JSpinner(NrDr);
		spinNrDr.setBounds(130, 10, 70, 20);
		((DefaultEditor) spinNrDr.getEditor()).getTextField()
				.setEditable(false);

		panel.add(lblNrDr);
		panel.add(spinNrDr);

		lblAvgPatArr = new JLabel("Average arrival time:");
		lblAvgPatArr.setBounds(10, 50, 120, 15);

		SpinnerModel AvgPatArr = new SpinnerNumberModel(40, 5, 600, 5);
		spinAvgPatArr = new JSpinner(AvgPatArr);
		spinAvgPatArr.setBounds(130, 50, 70, 20);
		((DefaultEditor) spinAvgPatArr.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblAvgPatArr);
		panel.add(spinAvgPatArr);

		lblSimuTime = new JLabel("Simulation time:");
		lblSimuTime.setBounds(10, 90, 120, 15);

		SpinnerModel SimuTime = new SpinnerNumberModel(28800, 360, 144000, 360);
		spinSimuTime = new JSpinner(SimuTime);
		spinSimuTime.setBounds(130, 90, 70, 20);
		((DefaultEditor) spinSimuTime.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblSimuTime);
		panel.add(spinSimuTime);

		lblP1min = new JLabel("Priority 1 min:");
		lblP1min.setBounds(10, 130, 120, 15);

		SpinnerModel P1min = new SpinnerNumberModel(10, 1, 1000, 1);
		spinP1min = new JSpinner(P1min);
		spinP1min.setBounds(130, 130, 70, 20);
		((DefaultEditor) spinP1min.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblP1min);
		panel.add(spinP1min);

		lblP1max = new JLabel("max:");
		lblP1max.setBounds(220, 130, 40, 15);

		SpinnerModel P1max = new SpinnerNumberModel(30, 1, 1000, 1);
		spinP1max = new JSpinner(P1max);
		spinP1max.setBounds(250, 130, 70, 20);
		((DefaultEditor) spinP1max.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblP1max);
		panel.add(spinP1max);

		lblP2min = new JLabel("Priority 2 min:");
		lblP2min.setBounds(10, 170, 120, 15);

		SpinnerModel P2min = new SpinnerNumberModel(10, 1, 1000, 1);
		spinP2min = new JSpinner(P2min);
		spinP2min.setBounds(130, 170, 70, 20);
		((DefaultEditor) spinP2min.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblP2min);
		panel.add(spinP2min);

		lblP2max = new JLabel("max:");
		lblP2max.setBounds(220, 170, 40, 15);

		SpinnerModel P2max = new SpinnerNumberModel(20, 1, 1000, 1);
		spinP2max = new JSpinner(P2max);
		spinP2max.setBounds(250, 170, 70, 20);
		((DefaultEditor) spinP2max.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblP2max);
		panel.add(spinP2max);

		lblP3min = new JLabel("Priority 3 min:");
		lblP3min.setBounds(10, 210, 120, 15);

		SpinnerModel P3min = new SpinnerNumberModel(50, 1, 1000, 1);
		spinP3min = new JSpinner(P3min);
		spinP3min.setBounds(130, 210, 70, 20);
		((DefaultEditor) spinP3min.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblP3min);
		panel.add(spinP3min);

		lblP3max = new JLabel("max:");
		lblP3max.setBounds(220, 210, 40, 15);

		SpinnerModel P3max = new SpinnerNumberModel(120, 1, 1000, 1);
		spinP3max = new JSpinner(P3max);
		spinP3max.setBounds(250, 210, 70, 20);
		((DefaultEditor) spinP3max.getEditor()).getTextField().setEditable(
				false);

		panel.add(lblP3max);
		panel.add(spinP3max);

		SpinnerModel DeathMin = new SpinnerNumberModel(15, 1, 1000, 1);
		spinDeathMin = new JSpinner(DeathMin);
		spinDeathMin.setBounds(280, (height - 125), 40, 20);
		((DefaultEditor) spinDeathMin.getEditor()).getTextField().setEditable(
				false);
		SpinnerModel DeathMax = new SpinnerNumberModel(25, 1, 100, 1);
		spinDeathMax = new JSpinner(DeathMax);
		spinDeathMax.setBounds(330, (height - 125), 40, 20);
		((DefaultEditor) spinDeathMax.getEditor()).getTextField().setEditable(
				false);

		spinDeathMin.show(false);
		spinDeathMax.show(false);

		panel.add(spinDeathMin);
		panel.add(spinDeathMax);

		SpinnerModel runs = new SpinnerNumberModel(1, 1, 1000, 1);
		spinRuns = new JSpinner(runs);
		spinRuns.setBounds(345, 5, 40, 20);
		((DefaultEditor) spinRuns.getEditor()).getTextField()
				.setEditable(false);

		JLabel lblRuns = new JLabel("Runs:");
		lblRuns.setBounds(305, 3, 40, 20);

		panel.add(lblRuns);
		panel.add(spinRuns);

		/*
		 * Buttons
		 */
		btnStart = new JButton("start");
		btnStart.setBounds((width / 2 - 40), (height - 70), 80, 30);
		btnStart.setToolTipText("Start the simulation");
		btnStart.setMnemonic(KeyEvent.VK_ENTER);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean ok = true;
				EmergencyRoomModel.dist1Min = (int) spinP1min.getValue();
				EmergencyRoomModel.dist1Max = (int) spinP1max.getValue();
				if (EmergencyRoomModel.dist1Min > EmergencyRoomModel.dist1Max) {
					ok = false;
					JOptionPane.showMessageDialog(null, "Distr. 1 min>max");
				}
				EmergencyRoomModel.dist2Min = (int) spinP2min.getValue();
				EmergencyRoomModel.dist2Max = (int) spinP2max.getValue();
				if (EmergencyRoomModel.dist2Min > EmergencyRoomModel.dist2Max) {
					ok = false;
					JOptionPane.showMessageDialog(null, "Distr. 2 min>max");
				}
				EmergencyRoomModel.dist3Min = (int) spinP3min.getValue();
				EmergencyRoomModel.dist3Max = (int) spinP3max.getValue();
				if (EmergencyRoomModel.dist3Min > EmergencyRoomModel.dist3Max) {
					ok = false;
					JOptionPane.showMessageDialog(null, "Distr. 3 min>max");
				}
				EmergencyRoomModel.numberOfDoctors = (int) spinNrDr.getValue();
				EmergencyRoomModel.arrivalTime = (int) spinAvgPatArr.getValue();
				EmergencyRoomModel.simulationTime = (int) spinSimuTime
						.getValue();
				EmergencyRoomModel.prio3kicks1 = chkPrio3Kicks1.isSelected();
				EmergencyRoomModel.initialPhaseFlag = chkInitialphase
						.isSelected();
				EmergencyRoomModel.deathOfPatientsFlag = chkDeathOfPatients
						.isSelected();

				if (chkDeathOfPatients.isSelected()) {
					EmergencyRoomModel.deathOfPatientsMin = (int) spinDeathMin
							.getValue();
					EmergencyRoomModel.deathOfPatientsMax = (int) spinDeathMax
							.getValue();
					if (EmergencyRoomModel.deathOfPatientsMin > EmergencyRoomModel.deathOfPatientsMax) {
						ok = false;
						JOptionPane.showMessageDialog(null,
								"Death distr. min > max");
					}
				}
				EmergencyRoomModel.underFive = 0;
				EmergencyRoomModel.deaths = 0;
				EmergencyRoomModel.runs = (int) spinRuns.getValue();
				if (ok) {
					System.out.println("Starting Simulation.");
					EmergencyRoomModel.runSimulation();
				}
			}
		});

		panel.add(btnStart);

		/*
		 * Checkboxes
		 */
		chkInitialphase = new JCheckBox("Initialphase");
		chkInitialphase.setSelected(false);
		chkInitialphase.setBounds(127, (height - 150), 120, 20);
		panel.add(chkInitialphase);

		chkDeathOfPatients = new JCheckBox("Death prio 3");
		chkDeathOfPatients.setSelected(false);
		chkDeathOfPatients.setBounds(127, (height - 125), 125, 20);
		panel.add(chkDeathOfPatients);

		chkPrio3Kicks1 = new JCheckBox("3 kicks 1");
		chkPrio3Kicks1.setSelected(false);
		chkPrio3Kicks1.setBounds(127, (height - 100), 140, 20);
		panel.add(chkPrio3Kicks1);

		setTitle("Emergency Room Model");
		setSize(width, height + 50);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		chkDeathOfPatients.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				spinDeathMax.show(chkDeathOfPatients.isSelected());
				spinDeathMin.show(chkDeathOfPatients.isSelected());
			}
		});

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.setVisible(true);
			}
		});
	}

}
