package com.rakesh.Evaluator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sixdee.Help.Constants;

/**
 * @author Rakesh KR
 *
 */
public class MajorEvaluator implements ActionListener {
	
	JFrame    frame      = new JFrame();
	JMenuBar           mainMenuBar;
	Toolkit   kit        = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	
	public MajorEvaluator(){
		
	
	}
	
	public void mainFrame(){
		
		frame.setVisible(true);
		mainMenuBar = createJmenuBar();
		frame.setJMenuBar(mainMenuBar);
		frame.add(createJPanelMain());
		frame.setTitle(Constants.softwareName);
		frame.setIconImage(Constants.softwareIcon);
		frame.setSize(screenSize.width/2,screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}
	
	public JMenuBar createJmenuBar(){

		JMenu     fileMenu = new JMenu("  File  ");
		JMenu     helpMenu = new JMenu("  Help  ");
		JMenuBar  menuBar  = new JMenuBar();
		JMenuItem add      = new JMenuItem("  Add           ");
		JMenuItem exit     = new JMenuItem(Constants.exitButton);


		menuBar.add(fileMenu);
		menuBar.setVisible(true);
		
		menuBar.add(helpMenu);
		menuBar.setVisible(true);

		add.setMnemonic(KeyEvent.VK_A);
		add.setToolTipText(Constants.addDocButton);

		exit.setMnemonic(KeyEvent.VK_X);
		exit.setToolTipText(Constants.exitButton);

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});


		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(add);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		
		helpMenu.setMnemonic(KeyEvent.VK_H);

		return menuBar;	
	}
	
	public JPanel createJPanelMain(){
		
		JPanel jpanel  = new JPanel();
		final JPanel middle = new JPanel();
		final JPanel result = new JPanel();
		
		final JTextField textField = new JTextField();
		final JTextArea CDRTextField = new JTextArea();
		final JTextArea resultTextArea = new JTextArea();
		JButton button = new JButton();
		JButton buttonRun = new JButton();
		
		button.setText("Browse");
		button.setVisible(true);
		
		buttonRun.setText("Run");
		buttonRun.setVisible(true);
		
		textField.setPreferredSize(new Dimension(320,30));
		textField.setVisible(true);
		
		
		CDRTextField.setPreferredSize(new Dimension(420,200));
		CDRTextField.setSize(new Dimension(420,200));
		CDRTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		CDRTextField.setCaretPosition(CDRTextField.getDocument().getLength());
		CDRTextField.setVisible(true);
		
		/*JScrollPane logScrollPane = new JScrollPane(CDRTextField);
		logScrollPane.setVisible(true);
		logScrollPane.setAutoscrolls(true);*/
		
		middle.setVisible(false);
		middle.add(CDRTextField);
		middle.add(buttonRun);
		//middle.setLayout(new GridLayout(2,1));
		middle.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		resultTextArea.setPreferredSize(new Dimension(520,200));
		resultTextArea.setSize(new Dimension(520,200));
		resultTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
		resultTextArea.setCaretPosition(CDRTextField.getDocument().getLength());
		resultTextArea.setVisible(true);
		
		result.setVisible(false);
		result.add(resultTextArea);
		result.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser openFile = new JFileChooser();
                openFile.showOpenDialog(null);
                if(openFile.getSelectedFile()!=null){
                	String selectedFile = openFile.getSelectedFile().getAbsolutePath();
                	textField.setText(selectedFile);
                	middle.setVisible(true);
                }
                
			}
		});
		
		buttonRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(CDRTextField.getText()!=null && !CDRTextField.getText().equals("") ){
					result.setVisible(true);
					resultTextArea.setText(CDRTextField.getText());
				}
			}
		});
		
		jpanel.add(textField);
		jpanel.add(button);
		jpanel.add(middle);
		jpanel.add(result);
		
		return jpanel;
		
	}

	public void actionPerformed(ActionEvent arg0) {
		
		
	}
	
	public static void main(String[] args) {
		
		MajorEvaluator majorEvaluator = new MajorEvaluator();
		majorEvaluator.mainFrame();

	}

}
