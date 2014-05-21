import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenuBar menubar;
	JPanel panel;
	JButton btnStart;
	
	public GUI(){
		int width = 800;
		int height = 600;
		
		panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(null);

		
		menubar = new JMenuBar();
		JMenu menuSettings = new JMenu("Settings");
		menuSettings.setMnemonic(KeyEvent.VK_S);
		JMenuItem menuitemReset = new JMenuItem("Reset");
		menuitemReset.setToolTipText("Restore default settings");
		
		menuitemReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Restore default settings");
			}
		});
		
		menuSettings.add(menuitemReset);
		
		menubar.add(menuSettings);
		setJMenuBar(menubar);
		
		
		btnStart = new JButton("start");
		btnStart.setBounds((width-80)/2, (height-100), 80, 30);
		btnStart.setToolTipText("Start the simulation");
		
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("start button pressed!");
				
			}
		});
		
		panel.add(btnStart);
		
		setTitle("Emergency Room Model");
		setSize(width,height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				GUI gui = new GUI();
				gui.setVisible(true);
			}
		});
	}
	
}
