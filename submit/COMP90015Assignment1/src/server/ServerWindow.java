/*
 COMP90015
 Name:Boyu Li
 Student ID:878890
 Email address:boyul@student.unimelb.edu.au
 Tutor: Xunyun Liu
 */
package server;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JList;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;

public class ServerWindow {

	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    public int windowsWedth = 800;
    public int windowsHeight = 600;
	private JFrame frame;
	public static Server myServer = new Server();
	private static int port;
	private static String filePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		port = Integer.parseInt(args[0]);
		filePath = args[1];
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		myServer.launch(port,filePath);
	}

	/**
	 * Create the application.
	 */
	public ServerWindow() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds((width - windowsWedth) / 2,
                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 197, 262, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblThreads = new JLabel("Threads:");
		lblThreads.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblThreads = new GridBagConstraints();
		gbc_lblThreads.anchor = GridBagConstraints.WEST;
		gbc_lblThreads.insets = new Insets(0, 0, 5, 5);
		gbc_lblThreads.gridx = 1;
		gbc_lblThreads.gridy = 1;
		frame.getContentPane().add(lblThreads, gbc_lblThreads);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 25));
		GridBagConstraints gbc_lblAddress = new GridBagConstraints();
		gbc_lblAddress.anchor = GridBagConstraints.WEST;
		gbc_lblAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress.gridx = 2;
		gbc_lblAddress.gridy = 1;
		frame.getContentPane().add(lblAddress, gbc_lblAddress);
		
		JButton btnRefreshList = new JButton("Refresh List");
		btnRefreshList.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_btnRefreshList = new GridBagConstraints();
		gbc_btnRefreshList.insets = new Insets(0, 0, 5, 5);
		gbc_btnRefreshList.gridx = 3;
		gbc_btnRefreshList.gridy = 1;
		frame.getContentPane().add(btnRefreshList, gbc_btnRefreshList);
		
		
		
		JList list = new JList();
		list.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 2;
		frame.getContentPane().add(list, gbc_list);
		
		JButton btnShutDown = new JButton("Shut Down");
		btnShutDown.setFont(new Font("Arial", Font.PLAIN, 30));
		btnShutDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		
		JList list_1 = new JList();
		list_1.setFont(new Font("Arial", Font.PLAIN, 30));
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.gridwidth = 2;
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.fill = GridBagConstraints.BOTH;
		gbc_list_1.gridx = 2;
		gbc_list_1.gridy = 2;
		frame.getContentPane().add(list_1, gbc_list_1);
		
		btnRefreshList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setListData(myServer.showThread());
				list_1.setListData(myServer.showAddress());
			}
		});
		
		JButton btnKillThread = new JButton("Kill Thread");
		btnKillThread.setFont(new Font("Arial", Font.PLAIN, 30));
		btnKillThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list.isSelectionEmpty())
				{
					String name = (String) list.getSelectedValue();
					myServer.killThread(name);
					btnRefreshList.doClick();
				}
				
			}
		});
		GridBagConstraints gbc_btnKillThread = new GridBagConstraints();
		gbc_btnKillThread.insets = new Insets(0, 0, 5, 5);
		gbc_btnKillThread.gridx = 1;
		gbc_btnKillThread.gridy = 4;
		frame.getContentPane().add(btnKillThread, gbc_btnKillThread);
		GridBagConstraints gbc_btnShutDown = new GridBagConstraints();
		gbc_btnShutDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnShutDown.gridx = 3;
		gbc_btnShutDown.gridy = 4;
		frame.getContentPane().add(btnShutDown, gbc_btnShutDown);
	}

}
