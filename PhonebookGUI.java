package phonebookGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 		2025年2月27日 by @author 18212
 * 		16:41 2025/3/3 modification by author
 * 		在原版本的基础上进行了改动：
 * 			1.在私有类成员TableModel的构造函数中增加List参数，它的作用就是保存诸多联系人信息，
 * 			  为主程序界面自动添加后台数据。
 * 			
 *
 * 
 */
public class PhonebookGUI extends JFrame {	//总页面

	JButton addButton = new ColorButton("添加", new Color(230, 230, 255)); 	//五个功能按钮
	JButton deleteButton = new ColorButton("删除", new Color(240, 255, 255));
	JButton setButton = new ColorButton("修改", new Color(250, 235, 215));
	JButton findButton = new ColorButton("查找", new Color(255, 250, 205));
	JButton backButton = new ColorButton("复原", new Color(135, 206, 250));
	//增加save按钮
	JButton saveButton = new ColorButton("保存", new Color(135, 206, 250));
//	private static String[][] strArray2Layer;
//	private static String[][] strArray2Layer = {{"A1", "A2", "A3"},
//			{"B1", "B2", "B3"},
//			{"C1", "C2", "C3"}
//			};
	private TableModel model =null;	//表格控件
	private GUITable table = null;
	private List contactsL = new ArrayList();
	private static final String CVS_FILE_PATH = "D:\\用户目录\\下载\\2023contacts_test_v2.csv";  //本地文件路径字符串   原值D:\\用户目录\\下载\\2023contacts.csv                 
	
	public PhonebookGUI(String title) {
		super(title);
		
	/*	try {
			File file  = new File(CVS_FILE_PATH); //读取本地文件动作
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");//
				BufferedReader br = new BufferedReader(read);
			
				String lineTxt = null;
				int i =0;
				while((lineTxt = br.readLine()) != null) {
					String[] fields = lineTxt.split(",");
					System.out.println(fields.length);
					Contacts cts = new Contacts(fields[0], fields[4], fields[fields.length-1]);
//					System.out.println(cts);
					contactsL.add(cts);
				}
			}*/
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(CVS_FILE_PATH), "utf-8");
			BufferedReader bReader = new BufferedReader(reader);
			bReader.readLine();//跳过第一行不处理
			String lineTxt = null;
			while((lineTxt = bReader.readLine())!=null) {
				String[] fields = lineTxt.split(",");
				Contacts c = new Contacts(fields[0], fields[1], fields[fields.length-1]);
				contactsL.add(c);
			}
		}catch(IOException e) {
			System.err.println("无法找到文件: "+e.getMessage());
		}
		
		
		String[] titles = {"姓名", "电话", "备注"};
		model = new TableModel(titles, contactsL);    
		table = new GUITable(model);
		this.setBounds(0, 0, 600, 600);
		this.add(new PanelGUI(), BorderLayout.NORTH);
		this.add(new PanelTable(table));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		addButton.addActionListener((e)->{add();});
		deleteButton.addActionListener((e)->{delete();});
		findButton.addActionListener((e)->{find();});
		setButton.addActionListener((e)->{set();});
		backButton.addActionListener((e)->{back();});
		saveButton.addActionListener((e)->{save2Disk();});
		
		
		
	}

	
	
	private class PanelGUI extends JPanel{	//总页面上层面板， 放置五个按钮
		public PanelGUI() {
			this.setBounds(0, 0, 600, 50);
			this.add(addButton);
			this.add(deleteButton);
			this.add(findButton);
			this.add(setButton);
			this.add(backButton);
			this.add(saveButton);
		}
	}

	private class PanelTable extends JPanel{	//总页面下层面板放置表格	
		public PanelTable(JTable table) {
			this.setBounds(0, 0, 800, 400);
//			this.add(table);
			this.add(new JScrollPane(table), BorderLayout.CENTER);
		}
	}
		
	private void add() { //添加联系人
		Add add = new Add(this);
		add.setTitle("Add contactant");
		add.setModal(true);
		add.setVisible(true);
		boolean flag = true;
		if(add.getAccepted()) {
			for(int i=0; i < table.getRowCount(); i++) {
				if(table.getValueAt(i, 1).equals(add.getValue().getPhoneNo())) {
					flag = false;
					JOptionPane.showMessageDialog(null, "该号码不能重复保存", "错误", JOptionPane.ERROR_MESSAGE);			 
				}
			}
			if(flag ==true) {
				model.addRow(add.getValue());
			}
		}
	}

	private void delete() {	//删除联系人
		int[]  rows = table.getSelectedRows();
		for(int i= rows.length -1; i >=0; i--) {
			int index = rows[i];
			model.removeRow(index);
		}
	}

	private void find() {
		Find find = new Find(this);
		find.setModal(true);
		find.setVisible(true);
		if(find.getAccepted()) {
			table.hide(find.getName());
		}
	}
	
	private void set() {	//修改信息
		int row = table.getSelectedRow();
		if(row == -1)
			JOptionPane.showMessageDialog(null, "请选择要修改的信息", "错误", JOptionPane.ERROR_MESSAGE);
		else {
			System.out.println(table.getValueAt(row, 1));
			System.out.println(row);
			boolean flag = true;
				Add add = new Add(this, table.getValueAt(row, 0).toString(), table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString());
				add.setTitle("");
				add.setModal(true);
				add.setVisible(true);
				if(add.getAccepted()) {
					for(int i=0; i < table.getRowCount(); i++) {
						if(table.getValueAt(i, 1).equals(add.getPhone()) && i != row) {
							flag = false;
							JOptionPane.showMessageDialog(null, "该号码不能重复保存", "错误", JOptionPane.ERROR_MESSAGE);
						}
					}
					if(flag) {
						model.setValueAt(add.getName(), row, 0);
						model.setValueAt(add.getPhone(), row, 1);
						model.setValueAt(add.getSex(), row,  2);
					}
				}
		}
	}
	
	private void back() {	//复原
		table.setRowHeight(24);
	}

	//在Java中，将StringBuilder对象的内容写入文件，可以通过多种方式实现，例如使用FileWriter、BufferedWriter、PrintWriter等
	
	private void save2Disk() {
		StringBuilder sbd = new StringBuilder();
		String[][] strArray2Layer= new String[model.getRowCount()][model.getColumnCount()];
		for(int k=0;k< model.getRowCount();k++) {
			for(int t=0;t<model.getColumnCount();t++) {
				strArray2Layer[k][t]="";
			}
		}
//		System.out.println("现在开始打印二维字符串数组：\r\n");
		
		for(int i=0;i < model.getRowCount();i++){
			Vector tempRowData =(Vector)model.getDataVector().elementAt(i);
			
			for(int j=0; j< model.getColumnCount();j++) {
				strArray2Layer[i][j] = (String)tempRowData.elementAt(j);
				if(j==model.getColumnCount()-1)
					sbd=sbd.append(strArray2Layer[i][j]+"\r\n");
				else
					sbd=sbd.append(strArray2Layer[i][j]+",");
			}	
		}
		
		try {
			FileWriter fWriter = new FileWriter("output.txt");
			fWriter.write(sbd.toString());
			fWriter.close();
			System.out.println("data has writen to file yet. ");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
		}
//		System.out.println(sbd.toString());
		
	}
	
	private class GUITable extends JTable{//自定义表格类
		public GUITable(DefaultTableModel model) {
			this.setFillsViewportHeight(true);
			this.setRowSelectionAllowed(true);
			this.setRowHeight(24);
			this.setModel(model);
		}
		
		public void hide(String name) {
			int row = this.getRowCount();
			for(int i =0; i < row; i++) {
				if(!table.getValueAt(i, 0).equals(name)) {
					table.setRowHeight(i, 1);
				}
			}
		}
		
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}
	
	private class ColorButton extends JButton{	//自定义按钮类
		public ColorButton(String str, Color color) {
			super(str);
			this.setOpaque(true);
			this.setPreferredSize(new Dimension(60, 30));
			this.setBackground(color);
			this.setBorderPainted(false);
		}
	}
	
	private class TableModel extends DefaultTableModel{		//自定义表格数据类
		public TableModel(String[] titles, List list) {
			for(int i=0; i < titles.length; i++) {
				this.addColumn(titles[i]);
			}
			initJb(list);
//			print2Cmd(list);
		}
		
		public void addRow(Contacts contact) {
			Vector<Object> rowData = new Vector<>();
			rowData.add(contact.getName());
			rowData.add(contact.getPhoneNo());
			rowData.add(contact.getSex());
			super.addRow(rowData);
		}
		
		public void initJb(List li){
			for (int j = 0; j < li.size(); j++) {
				addRow((Contacts)li.get(j));	
			}
			
		}
		
		public void  print2Cmd(List list) {
			System.out.println("name, phoneNo, sex");
			for(int k=0;k<list.size();k++) {
				Contacts tc = (Contacts)list.get(k);
				System.out.println("("+ tc.getName()+", "+ tc.getPhoneNo()+", "+ tc.getSex()+")");
			}
		}
	}
	
	public JTable getGUITable() {
		return table;
	}
}
