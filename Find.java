/**
 * 
 */
package phonebookGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * 2025年2月28日 by @author 18212
 *
 * 
 */
public class Find extends JDialog{
	private boolean accepted =false;
	private JLabel label1 = new AddLabel("查找联系人", new Color(230, 230, 255));
	private JLabel label2 = new AddLabel("姓名", new Color(230, 230, 255));
	private JTextField name = new AddText(new Color(240, 255, 255));
	private JButton  okButton = new ColorButton("确定", new Color(255, 250, 205));
	private JButton cancelButton = new ColorButton("取消", new Color(250, 235, 215));
	
	public Find(Window owner) {
		super(owner);
		JPanel root = new JPanel(new GridLayout(5, 0));
		JPanel[] panel = new AddPanel[5];
		for(int i =0; i < 5; i++) {
			panel[i] = new AddPanel();
		}
		panel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
		panel[0].add(label1);
		panel[1].add(label2);
		panel[1].add(name);
		
		panel[4].add(okButton);
		panel[4].add(cancelButton);
		for(int i=0; i < 5; i++) {
			root.add(panel[i]);
		}
		this.add(root);
		this.setBounds(0, 0, 300, 300);
		this.setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
		okButton.addActionListener((e)-> {accepted = true;setVisible(false);});
		cancelButton.addActionListener((e)->{accepted = false; setVisible(false);});
	}
			
	private class AddLabel extends JLabel{
		public AddLabel(String str, Color color) {
			super(str);
			this.setOpaque(true);
			this.setBackground(color);
			this.setPreferredSize(new Dimension(80, 20));
			this.setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
	
	private class AddText extends JTextField{
		public AddText(Color color) {
			this.setPreferredSize(new Dimension(150, 20));
			this.setBackground(color);
		}
	}
	
	private class ColorButton extends JButton{
		public ColorButton(String str, Color color) {
			super(str);
			this.setOpaque(true);
			this.setPreferredSize(new Dimension(60, 30));
			this.setBackground(color);
			this.setBorderPainted(false);
		}
	}
	
	private class AddPanel extends JPanel{
		public AddPanel() {
			this.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.setVisible(true);
			this.setSize(new Dimension(200, 30));
		}
	}
	
	public String getName() {
		return name.getText();
	}
	
	public boolean getAccepted() {
		return accepted;
	}
}
