/*
 COMP90015
 Name:Boyu Li
 Student ID:878890
 Email address:boyul@student.unimelb.edu.au
 Tutor: Xunyun Liu
 */
package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JTextField;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class ClientWindow {
	
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 800;
    public int windowsHeight = 650;
    public static Client myClient =new Client(); 
	private static JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private static String host;
	private static int port;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		host = args[0];
		port = Integer.parseInt(args[1]);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow window = new ClientWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, 800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.1, 0.0, 0.0, 0.0, 0.0, 0.05, 0.0, 0.0, 1.0, 0.1, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		
		JLabel lblInput = new JLabel("WORD:");
		lblInput.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblInput = new GridBagConstraints();
		gbc_lblInput.anchor = GridBagConstraints.WEST;
		gbc_lblInput.insets = new Insets(0, 0, 5, 5);
		gbc_lblInput.gridx = 1;
		gbc_lblInput.gridy = 1;
		frame.getContentPane().add(lblInput, gbc_lblInput);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 5;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnQuery = new JButton("Query");
		btnQuery.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_btnQuery = new GridBagConstraints();
		gbc_btnQuery.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnQuery.insets = new Insets(0, 0, 5, 5);
		gbc_btnQuery.gridx = 7;
		gbc_btnQuery.gridy = 2;
		frame.getContentPane().add(btnQuery, gbc_btnQuery);
		
		JLabel lblState = new JLabel("STATE:");
		lblState.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.anchor = GridBagConstraints.WEST;
		gbc_lblState.insets = new Insets(0, 0, 5, 5);
		gbc_lblState.gridx = 1;
		gbc_lblState.gridy = 3;
		frame.getContentPane().add(lblState, gbc_lblState);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemove.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemove.gridx = 7;
		gbc_btnRemove.gridy = 4;
		frame.getContentPane().add(btnRemove, gbc_btnRemove);
		

		
		JLabel lblMeaning = new JLabel("MEANING:");
		lblMeaning.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblMeaning = new GridBagConstraints();
		gbc_lblMeaning.anchor = GridBagConstraints.WEST;
		gbc_lblMeaning.insets = new Insets(0, 0, 5, 5);
		gbc_lblMeaning.gridx = 1;
		gbc_lblMeaning.gridy = 6;
		frame.getContentPane().add(lblMeaning, gbc_lblMeaning);
		
		JButton btnConnect = new JButton("CONNECT");
		
		btnConnect.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_btnConnect = new GridBagConstraints();
		gbc_btnConnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnConnect.gridx = 3;
		gbc_btnConnect.gridy = 6;
		frame.getContentPane().add(btnConnect, gbc_btnConnect);
		btnConnect.setVisible(true);
		

		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.gridwidth = 5;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 7;
		frame.getContentPane().add(textArea, gbc_textArea);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 7;
		gbc_btnAdd.gridy = 7;
		frame.getContentPane().add(btnAdd, gbc_btnAdd);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_btnDisconnect = new GridBagConstraints();
		gbc_btnDisconnect.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDisconnect.insets = new Insets(0, 0, 5, 5);
		gbc_btnDisconnect.gridx = 7;
		gbc_btnDisconnect.gridy = 8;
		frame.getContentPane().add(btnDisconnect, gbc_btnDisconnect);
		
		textField_1 = new JTextField();
		textField_1.setForeground(Color.RED);
		textField_1.setEditable(false);
		textField_1.setFont(new Font("Arial", Font.PLAIN, 30));
		textField_1.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 5;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 4;
		frame.getContentPane().add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.setFont(new Font("Arial", Font.PLAIN, 25));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 3;
		gbc_btnClose.gridy = 9;
		frame.getContentPane().add(btnClose, gbc_btnClose);
		
		btnAdd.setVisible(false);
		btnQuery.setVisible(false);
		btnRemove.setVisible(false);
		btnDisconnect.setVisible(false);
		textField.setVisible(false);
		textField_1.setVisible(false);
		textArea.setVisible(false);
		lblState.setVisible(false);
		lblMeaning.setVisible(false);
		lblInput.setVisible(false);
		btnConnect.setVisible(true);
		btnClose.setVisible(true);
		
		
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals(""))
					textField_1.setText("Please enter the word!");
				else
				{
					ArrayList<String> input = new ArrayList();
					input.add(textField.getText());
					ArrayList<String> output = myClient.getMes(1,input);
					if(output.size()>=2)
					{
						textField_1.setText(output.get(0));
						textArea.setText(output.get(1));
					}
				}	
			}
		});
		
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals(""))
					textField_1.setText("Please enter the word!");
				else if(textArea.getText().equals(""))
					textField_1.setText("Please enter the meaning!");
				else
				{
					ArrayList<String> input = new ArrayList();
					input.add(textField.getText());
					input.add(textArea.getText());
					ArrayList<String> output = myClient.getMes(2,input);
					if(output.size()>=1)
						textField_1.setText(output.get(0));
				}
			}
		});
		
		
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().equals(""))
					textField_1.setText("Please enter the word!");
				else
				{
					ArrayList<String> input = new ArrayList();
					input.add(textField.getText());
					ArrayList<String> output = myClient.getMes(3,input);
					if(output.size()>=1)
					{
						textField_1.setText(output.get(0));
					}
				}	
			}
		});
		
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> input = new ArrayList();
				ArrayList<String> output = myClient.getMes(4,input);
				btnAdd.setVisible(false);
				btnQuery.setVisible(false);
				btnRemove.setVisible(false);
				btnDisconnect.setVisible(false);
				textField.setVisible(false);
				textField_1.setVisible(false);
				textArea.setVisible(false);
				lblState.setVisible(false);
				lblMeaning.setVisible(false);
				lblInput.setVisible(false);
				btnConnect.setVisible(true);
				btnClose.setVisible(true);
			}
		});
		
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int clientState = myClient.connect(host,port);
				if (clientState == 1)
				{
					JOptionPane.showMessageDialog(null,"Connection with server established");
					btnAdd.setVisible(true);
					btnQuery.setVisible(true);
					btnRemove.setVisible(true);
					btnDisconnect.setVisible(true);
					textField.setVisible(true);
					textField_1.setVisible(true);
					textArea.setVisible(true);
					lblState.setVisible(true);
					lblMeaning.setVisible(true);
					lblInput.setVisible(true);
					btnConnect.setVisible(false);
					btnClose.setVisible(false);
				}
				else if(clientState == 2)
				{
					JOptionPane.showMessageDialog(null,"Unknow host!");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Unable to connect to server!");
				}
			}
		});
	}

}
