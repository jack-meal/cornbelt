/**
 * 
 */
package phonebookGUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * 2025Äê2ÔÂ28ÈÕ by @author 18212
 *
 * 
 */
public class Run {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new PhonebookGUI("phonebookTest");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
