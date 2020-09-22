package shell;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import dataBase.DataBaseModule;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout.Alignment;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Cursor;
import java.awt.Color;

public class LogIN extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JLabel username_la = new JLabel("Felhaszn\u00E1l\u00F3n\u00E9v");
	private JTextField username_tf;
	private JPasswordField password_pwf;
	private int wrongCounter = 0; // Hányszor adtunk meg hibás infót
	DataBaseModule dbm = new DataBaseModule();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIN frame = new LogIN();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public LogIN(MainWindow mw) {
		setResizable(false);
		setPreferredSize(new Dimension(400, 300));
		//MainWindow mw = new MainWindow();
		setTitle("Bel\u00E9p\u00E9s a KAR fel\u00FClet\u00E9be");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 324);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 400, 4300));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel password_la = new JLabel("Jelsz\u00F3");

		username_tf = new JTextField();
		username_tf.setColumns(10);
		
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		Icon signInIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ASSIGNMENT_IND, 20, new Color(0, 0, 0));

		JButton login_bt = new JButton("Bejelentkezés");
		login_bt.setIcon(signInIcon);
		login_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login_bt.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				try {
					if (dbm.authenticate(username_tf.getText(), password_pwf.getText())) {
						mw.setExtendedState(JFrame.MAXIMIZED_BOTH);
						//mw.setUndecorated(true);
						mw.setLocationRelativeTo(null);
						mw.setVisible(true);
						setVisible(false);
					} else {
						wrongCounter++;
						JOptionPane.showMessageDialog(null, "Helytelen felhasználónév vagy jelszó megadás!");
						
						// 3 hibás próbálkozás után bezárjuk
						if(wrongCounter >= 3)
							System.exit(0); 
							
						username_tf.setText("");
						password_pwf.setText("");
					}

				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
		});

		password_pwf = new JPasswordField();

		JLabel logo_la = new JLabel("");
		logo_la.setIgnoreRepaint(true);
		logo_la.setBackground(new Color(214, 217, 223));
		logo_la.setHorizontalAlignment(SwingConstants.CENTER);
		Image img = new ImageIcon(this.getClass().getResource("/logo.jpg")).getImage();
		logo_la.setIcon(new ImageIcon(img));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(118)
					.addComponent(logo_la))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(68)
					.addComponent(username_la, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addComponent(username_tf, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(68)
					.addComponent(password_la, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addComponent(password_pwf, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(118)
					.addComponent(login_bt, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(logo_la)
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(username_la, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(username_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(password_la, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(password_pwf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addComponent(login_bt))
		);
		contentPane.setLayout(gl_contentPane);
		
		String className = getLookAndFeelClassName("Nimbus");
		try {
			UIManager.setLookAndFeel(className);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SwingUtilities.updateComponentTreeUI(this);
		
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				username_tf.setText("");
				password_pwf.setText("");
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
			}
		});
	}
	
	public static String getLookAndFeelClassName(String nameSnippet) {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(nameSnippet)) {
	            return info.getClassName();
	        }
	    }
	    return null;
	}
	
	
}
