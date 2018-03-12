import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField seed;
	private JTextField currentFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("MST GRAPH");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 441, 556);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JEditorPane wordBox = new JEditorPane();
		wordBox.setBounds(21, 84, 234, 242);
		contentPane.add(wordBox);
		
		JLabel lblNewLabel = new JLabel("Text");
		lblNewLabel.setBounds(21, 50, 92, 26);
		contentPane.add(lblNewLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(227, 412, 175, 32);
		contentPane.add(progressBar);
		
		JButton Encrypt = new JButton("Encrypt");
		Encrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread()
				{
				    public void run() {
						mst_graph runMe = new mst_graph(seed.getText(),currentFile.getText(), wordBox.getText(),progressBar);
						runMe.encrypt();
				    }
				}.start();
			}
		});
		Encrypt.setBounds(93, 21, 113, 30);
		contentPane.add(Encrypt);
		
		JLabel lblSeed = new JLabel("Seed");
		lblSeed.setBounds(276, 82, 92, 26);
		contentPane.add(lblSeed);
		
		seed = new JTextField();
		seed.setBounds(272, 109, 107, 30);
		contentPane.add(seed);
		seed.setColumns(10);
		
		JButton Decrpyt = new JButton("Decrypt");
		Decrpyt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new Thread()
				{
				    public void run() {
				    	String words;
						mst_graph runMe = new mst_graph(seed.getText(),currentFile.getText(), wordBox.getText(),progressBar);
						runMe.decrypt();
						words = runMe.getDecypheredString();
						wordBox.setText(words);
				    }
				}.start();
				

				
			}
		});
		Decrpyt.setBounds(227, 23, 113, 26);
		contentPane.add(Decrpyt);
		
		JButton fileChoose = new JButton("Chose Image");
		fileChoose.setEnabled(false);
		fileChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/* stub .selectFile();
				 *
				 */
			}
		});
		fileChoose.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		fileChoose.setBounds(21, 347, 175, 26);
		contentPane.add(fileChoose);
		
		currentFile = new JTextField();
		currentFile.setBounds(21, 412, 186, 32);
		contentPane.add(currentFile);
		currentFile.setColumns(10);
		
		JLabel lblCurrentFile = new JLabel("Current File");
		lblCurrentFile.setBounds(21, 379, 206, 26);
		contentPane.add(lblCurrentFile);
		
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setBounds(227, 379, 92, 26);
		contentPane.add(lblStatus);
	}
}
