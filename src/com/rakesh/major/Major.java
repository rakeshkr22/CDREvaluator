package com.rakesh.major;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;



/**
 * @author Rakesh KR
 *
 */

public class Major extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton run;
	JPanel middlePanel;
	JPanel addPanel;
	JPanel editPanel;
	JLabel label;
	JLabel label1;
	JLabel groupNameLabel;
	JLabel groupNameLabel1;
	JTextField groupNameText;
	JTextArea groupcdrLine;
	JScrollPane groupscroll;
	JTextArea cdrLine;
	JScrollPane scroll;
	JTextArea cdrLine1;
	JScrollPane scroll1;
	JButton groupRun;
	JButton groupDelete;
	JScrollPane resultScrollPane ;
	JPanel resultPanel;

	JComboBox combobox;
	JMenuBar mainMenuBar;

	ArrayList<String>      groupListName = null;
	ArrayList<DataStorage> groupList     = null;


	public Major(){

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/5,Toolkit.getDefaultToolkit().getScreenSize().height/7);
		loadData();
		setupGUI();

	}
	
	void loadData(){
		
		groupList       = new ArrayList<DataStorage>();
		File       file = new File(Constants.dsFileName);
		String     none = String.format("%-150s", "None");
		DataStorage ds  = new DataStorage();
		ds.setName(none);
		ds.setCdrLine("  ");
		groupList.add(ds);
		
		if(!file.exists()){
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				errorMethod(Constants.errorInCons);
			}
		}
		else{
			FileOperations    fp              = new FileOperations();
			ArrayList<String> dataStorageList = new ArrayList<String>(); 
			
			dataStorageList = fp.fileRead(file.getAbsolutePath());

			for(String i :dataStorageList){
				DataStorage ds1 = new DataStorage();
				ds1.setName(i.split("::")[0]);
				ds1.setCdrLine(i.split("::")[1]);
				groupList.add(ds1);
			}
		}
	}
	
	void setupGUI(){
		
		groupListName = new ArrayList<String>();
		run = new JButton();
		run.setLocation(405,455);
		run.setSize(100,25);
		run.setText("Run");
		run.setVisible(true);
		//run.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		getContentPane().add(run);
		
		middlePanel = new JPanel();
		middlePanel.setBorder(new TitledBorder(new EtchedBorder(),"CDR Evaluator"));
		cdrLine = new JTextArea (10,50);
		cdrLine.setEditable(true);
		cdrLine.setForeground(Color.black);
		cdrLine.setText("Paste CDR Format Here");
		scroll = new JScrollPane(cdrLine);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		label = new JLabel();
		label.setText("Open");
		cdrLine1 = new JTextArea (10,50);
		cdrLine1.setEditable(true);
		cdrLine1.setForeground(Color.black);
		scroll1 = new JScrollPane(cdrLine1);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		label1 = new JLabel();
		label1.setSize(100,70);
		label1.setText("Paste Generated CDR Line Below");

		for(int i=0;i<groupList.size();i++){
			groupListName.add(groupList.get(i).getName());
		}
		
		combobox = new JComboBox(groupListName.toArray());
		combobox.setLocation(137,215);
		combobox.setSize(300,25);
		combobox.setEditable(false);
		combobox.setVisible(true);

		combobox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				if(combobox.getSelectedItem().toString().contains("None"))
					cdrLine.setText("Paste CDR Format Here");

				for(int i=1;i<groupList.size();i++){
					if(combobox.getSelectedItem().toString().contains(groupList.get(i).getName()))
						cdrLine.setText(groupList.get(i).getCdrLine());
				}
			}
		});
		
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				String cdrFromatLine    = cdrLine.getText();
				String cdrGenerLine     = cdrLine1.getText();
				String columnNames[]    = {"Sl.No","Field Name","Value"};
				String columnValues[][] = new String[cdrFromatLine.split(",").length][2];
				
				
				if(cdrFromatLine!=null && cdrGenerLine!=null){
					if(cdrFromatLine.split(",").length == cdrGenerLine.split(",").length){
						for(int i=0;i<cdrFromatLine.split(",").length;i++){
							String slno  = i+1+"";
							String field = cdrFromatLine.split(",")[i];
							String value = cdrGenerLine.split(",")[i];
							//System.out.println(slno+"        "+field+"         "+value);
							columnValues[i] = new String[]{slno,field,value};
							
						}
						
						resultPanel = new JPanel();
						if(resultScrollPane!=null)
							resultScrollPane.setVisible(true);
						JTable table = new JTable(columnValues,columnNames);
						
						JButton backbutton = new JButton(" <<  Back");
						backbutton.setLocation(137,215);
						backbutton.setSize(100,25);
						//backbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						getContentPane().add(backbutton);
						backbutton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event) {
								Frame g[] = JFrame.getFrames();
								for(int i=0;i<g.length;i++){
									g[i].dispose();
								}
								new Major();
							}
						});
						
						JScrollPane scrollPane = new JScrollPane( table );
						//middlePanel.add(scrollPane);
						resultPanel.add(scrollPane);
						resultPanel.add(backbutton);
						scrollPane.setVisible(true);
						
						
						resultScrollPane = new JScrollPane(resultPanel);
						
						getContentPane().add(resultScrollPane);
						
						if(addPanel!=null)
							addPanel.setVisible(false);
						if(run!=null)
							run.setVisible(false);
						if(middlePanel!=null)
							middlePanel.setVisible(false);
						
						
					}
					else{
						errorMethod("Error in input fields \n Both should't in same format ");
					}
					
				}
			}
		});

		middlePanel.add(label);
		middlePanel.add(combobox);
		middlePanel.add(scroll);
		middlePanel.add(label1);
		middlePanel.add(scroll1);
		getContentPane().add(middlePanel);

		mainMenuBar = createJmenuBar();
		setJMenuBar(mainMenuBar);
		
		if(middlePanel!=null)
			middlePanel.setVisible(true);
		if(addPanel!=null)
			addPanel.setVisible(false);
		
		setTitle("CDR Evaluator");
		setSize(600,550);
		setVisible(true);
		setResizable(false);

	}

	public String space(int i){
		return String.format("%-"+i+"s", "  ");
	}

	public void addNew(){

		/*
	        Add New CDR Format
		 */
		try {
			groupNameLabel = new JLabel();
			groupNameLabel.setLocation(50,50);
			groupNameLabel.setSize(100,25);
			groupNameLabel.setText("Name");

			groupNameLabel1 = new JLabel();
			groupNameLabel1.setLocation(50,50);
			groupNameLabel1.setSize(100,25);
			groupNameLabel1.setText("Paste New CDR Format : ");

			groupNameText = new JTextField();
			groupNameText.setLocation(100,50);
			groupNameText.setSize(300,25);
			groupNameText.setText("Name"+space(150));

			addPanel = new JPanel();
			addPanel.setBorder(new TitledBorder(new EtchedBorder(),"Add New CDR Line"));
			groupcdrLine = new JTextArea (10,50);
			groupcdrLine.setEditable(true);
			groupscroll = new JScrollPane(groupcdrLine);
			groupscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

			groupRun = new JButton();
			groupRun.setLocation(429,418);
			groupRun.setSize(100,30);
			groupRun.setText("Add");

			addPanel.add(groupNameLabel);
			addPanel.add(groupNameText);
			addPanel.add(groupNameLabel1);
			addPanel.add(groupscroll);
			addPanel.add(groupRun);
			getContentPane().add(addPanel);

			groupRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					try {
						if(groupNameText.getText().replaceAll("\\s", "")==null || groupNameText.getText().replaceAll("\\s", "").equalsIgnoreCase("") ||
								groupcdrLine.getText()==null || groupcdrLine.getText().equalsIgnoreCase("")){
							errorMethod(Constants.errorInAdd);

						}
						else{

							/*DataStorage ds2 = new DataStorage();
							ds2.setName();
							ds2.setCdrLine();
							groupList.add(ds2);*/

							String toFile = groupNameText.getText().replaceAll("\\s", "")+" :: "+groupcdrLine.getText().replaceAll("\\s", "");

							FileOperations op1 = new FileOperations();
							op1.fileAppend(Constants.dsFileName, toFile);

							if(addPanel!=null)
								addPanel.setVisible(false);
							if(resultScrollPane!=null)
								resultScrollPane.setVisible(false);
							if(run!=null)
								run.setVisible(true);
							if(middlePanel!=null)
								middlePanel.setVisible(true);
							

							Frame g[] = JFrame.getFrames();
							for(int i=0;i<g.length;i++){
								g[i].dispose();
							}

							new Major();
							//loadData();
							//setupGUI();
							infoMethod("Added Sucessfully");


						}
					} catch (Exception e) {

						errorMethod("Internal Error"+e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			errorMethod("Internal Error"+e.getMessage());

		} finally{
			/*
			groupNameLabel  = null;
			groupNameLabel1 = null;
			groupNameText   = null;
			groupcdrLine    = null;
			groupscroll     = null;
			groupRun        = null;*/
		}
	}


	public void edit(String name11,String cdrLine11){

		/*
	        Edit CDR Format
		 */

		//this.setLayout(null);

		try {
			groupNameLabel = new JLabel();
			groupNameLabel.setLocation(50,50);
			groupNameLabel.setSize(100,25);
			groupNameLabel.setText("Name");

			groupNameLabel1 = new JLabel();
			groupNameLabel1.setLocation(50,50);
			groupNameLabel1.setSize(100,25);
			groupNameLabel1.setText("Edit CDR Format : ");

			groupNameText = new JTextField();
			groupNameText.setLocation(100,50);
			groupNameText.setSize(300,25);
			groupNameText.setEditable(false);
			groupNameText.setText(name11);

			editPanel = new JPanel();
			editPanel.setBorder(new TitledBorder(new EtchedBorder(),"Edit CDR Line"));
			groupcdrLine = new JTextArea (10,50);
			groupcdrLine.setEditable(true);
			groupcdrLine.setText(cdrLine11);
			groupscroll = new JScrollPane(groupcdrLine);
			groupscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

			groupRun = new JButton();
			groupRun.setLocation(429,418);
			groupRun.setSize(100,30);
			groupRun.setText("Save");

			groupDelete = new JButton();
			groupDelete.setLocation(429,518);
			groupDelete.setSize(100,30);
			groupDelete.setText("Delete");


			editPanel.add(groupNameLabel);
			editPanel.add(groupNameText);
			editPanel.add(groupNameLabel1);
			editPanel.add(groupscroll);
			editPanel.add(groupRun);
			editPanel.add(groupDelete);
			getContentPane().add(editPanel);

			groupRun.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					try {
						FileOperations    op         = new FileOperations();
						File              fp         = new File(Constants.dsFileName);
						String            rplcStr    = null;
						ArrayList<String> dsContent  = new ArrayList<String>();
						ArrayList<String> dsNContent = new ArrayList<String>();

						if(fp.exists()){
							dsContent = op.fileRead(fp.getAbsolutePath());
							if(dsContent!=null){
								for(String i:dsContent){
									if(i.split(":")[0].replaceAll("\\s","").equals(groupNameText.getText().replaceAll("\\s",""))){
										rplcStr = (groupNameText.getText().replaceAll("\\s","")+" :: "+groupcdrLine.getText().replaceAll("\\s",""));
									}
								}
							}
						}
						else{
							errorMethod("Error in Internal Operation");
						}

						if(!dsContent.isEmpty() && rplcStr!=null){
							for(String i:dsContent){
								if(i.split("::")[0].equals(rplcStr.split("::")[0])){
									dsNContent.add(rplcStr);
								}
								else{
									dsNContent.add(i);
								}
							}
						}

						if(fp.exists() && !dsNContent.isEmpty()){
							op.fileWrite(Constants.dsFileName,dsNContent);
						}

						if(addPanel!=null)
							addPanel.setVisible(false);
						if(editPanel!=null)
							editPanel.setVisible(false);
						if(resultScrollPane!=null)
							resultScrollPane.setVisible(false);
						if(run!=null)
							run.setVisible(true);
						if(middlePanel!=null)
							middlePanel.setVisible(true);

						loadData();
						infoMethod("Saved Sucessfully");

					} catch (Exception e) {
						errorMethod("Error in internal operations -"+e.getMessage());
						e.printStackTrace();
					}
				}
			});

			groupDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						FileOperations    op         = new FileOperations();
						File              fp         = new File(Constants.dsFileName);
						String            rplcStr    = null;
						ArrayList<String> dsContent  = new ArrayList<String>();
						ArrayList<String> dsNContent = new ArrayList<String>();

						if(fp.exists()){
							dsContent = op.fileRead(fp.getAbsolutePath());
							if(dsContent!=null){
								for(String i:dsContent){
									if(i.split(":")[0].replaceAll("\\s","").equals(groupNameText.getText().replaceAll("\\s",""))){
										rplcStr = (groupNameText.getText().replaceAll("\\s","")+" :: "+groupcdrLine.getText().replaceAll("\\s",""));
									}
								}
							}
						}
						else{
							errorMethod("Error in Internal Operation");
						}

						if(!dsContent.isEmpty() && rplcStr!=null){
							for(String i:dsContent){
								if(!i.split("::")[0].equals(rplcStr.split("::")[0])){
									dsNContent.add(i);
								}
							}
						}

						if(fp.exists() && !dsNContent.isEmpty()){
							op.fileWrite(Constants.dsFileName,dsNContent);
						}

						if(addPanel!=null)
							addPanel.setVisible(false);
						if(editPanel!=null)
							editPanel.setVisible(false);
						if(resultScrollPane!=null)
							resultScrollPane.setVisible(false);
						if(run!=null)
							run.setVisible(true);
						if(middlePanel!=null)
							middlePanel.setVisible(true);
						
						Frame g[] = JFrame.getFrames();
						for(int i=0;i<g.length;i++){
							g[i].dispose();
						}

						new Major();
						//loadData();
						infoMethod("Deleted Sucessfully");

					} catch (Exception e) {
						errorMethod("Error in internal operations -"+e.getMessage());
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}finally{

			/*groupNameLabel  = null;
			groupNameLabel1 = null;
			groupNameText   = null;
			groupcdrLine    = null;
			groupscroll     = null;
			groupRun        = null;*/
		}
	}


	private void errorMethod(String message) {
		JOptionPane.showMessageDialog(this,message,Constants.softwareName,JOptionPane.ERROR_MESSAGE);

	}

	private void infoMethod(String message) {
		JOptionPane.showMessageDialog(this,message,Constants.softwareName,JOptionPane.INFORMATION_MESSAGE);

	}
	private void aboutMethod(String message) {
		JOptionPane.showMessageDialog(this,message,"About - "+Constants.softwareName,JOptionPane.PLAIN_MESSAGE);
	}
	
	
	/*
		Menu Bar Components
	*/
	
	public JMenuBar createJmenuBar(){

		JMenu     fileMenu = new JMenu("File");
		JMenu     editMenu = new JMenu("Edit");
		JMenu     runMenu  = new JMenu("Run");
		JMenu     abtMenu  = new JMenu("About");
		JMenuBar  menuBar  = new JMenuBar();

		JMenuItem add      = new JMenuItem("Add New"+space(25));
		JMenuItem exit     = new JMenuItem("Exit");

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(runMenu);
		menuBar.add(abtMenu);
		menuBar.setVisible(true);

		/*
		    File Menu Components
		 */

		add.setMnemonic(KeyEvent.VK_A);
		add.setToolTipText("Add New");

		exit.setMnemonic(KeyEvent.VK_X);
		exit.setToolTipText("Exit");

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(run!=null)
					run.setVisible(false);
				if(middlePanel!=null)
					middlePanel.setVisible(false);
				if(editPanel!=null)
					editPanel.setVisible(false);
				if(resultScrollPane!=null)
					resultScrollPane.setVisible(false);
				if(addPanel!=null)
					addPanel.setVisible(true);
				
				addNew();
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});


		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(add);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		/*
	         Edit Menu Components
		 */
		for(int i=1;i<groupList.size();i++){
			
			JMenuItem item = new JMenuItem(groupList.get(i).getName()+space(15));
			try {
				editMenu.add(item);
				final String name11    = groupList.get(i).getName()+space(150);
				final String cdrLine11 = groupList.get(i).getCdrLine();

				item.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						run.setVisible(false);
						if(middlePanel!=null)
							middlePanel.setVisible(false);
						if(addPanel!=null)
							addPanel.setVisible(false);
						if(resultScrollPane!=null)
							resultScrollPane.setVisible(false);
						if(editPanel!=null && editPanel.isVisible()){
							//System.out.println("Visible");
							editPanel.setVisible(false);

						}
						edit(name11,cdrLine11);
						editPanel.setVisible(true);
						//System.out.println(name11+"    "+cdrLine11);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				item = null;

			}
		}
		
		/*
        Run Menu Components
	    */
		
		runMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent arg0) {
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
	
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				if(run!=null)
					run.setVisible(true);
				if(middlePanel!=null)
					middlePanel.setVisible(true);
				if(addPanel!=null)
					addPanel.setVisible(false);
				if(editPanel!=null)
					editPanel.setVisible(false);
				if(resultScrollPane!=null)
					resultScrollPane.setVisible(false);
				
			}
		});
		
		/*
        About Menu Components
	    */
		
		abtMenu.addMenuListener(new MenuListener() {
			@Override
			public void menuCanceled(MenuEvent arg0) {
				
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
	
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				aboutMethod("Version : v.1.0 \n \n  Rakesh KR \n Sixdee Technologies,Banglore \n mail-id: 2krrakesh2@gmail.com");
				
			}
		});

		return menuBar;	
	}

	public static void main(String args[]){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		System.out.println("Note : ");
		System.out.println("1. If you are running first time, run it as root");
		System.out.println("2. Make sure X11 forwarding is enabled in your PuTTY settings");
		System.out.println("3. If you need to add a new CDR format, add it with root permission");
		System.out.println("-------------------------------------------------------------------");
		System.out.println("Rakesh KR");
		System.out.println("Version : v.1.0");
		System.out.println("Sixdee Technologies,Banglore");
		System.out.println("rakesh.kr@6dtech.co.in");
		new Major();
	}
}  

