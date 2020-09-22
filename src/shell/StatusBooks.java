package shell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import core.Book;
import core.Borrow;
import core.Member;
import dataBase.DataBaseModule;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Insets;
public class StatusBooks extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable borrowedList;
	private JTable returnedList;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { StatusBooks frame = new StatusBooks();
	 * frame.setVisible(true); frame.setLocationRelativeTo(null); } catch (Exception
	 * e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public StatusBooks(Member member) {
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		
		setResizable(false);
		setMaximumSize(new Dimension(1100, 700));
		setMinimumSize(new Dimension(1100, 700));
		setPreferredSize(new Dimension(1100, 700));
		JFrame jf = this;
		DataBaseModule dbm = new DataBaseModule();
		setTitle("K\u00F6lcs\u00F6nz\u00E9sek \u00E1llapota");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1032, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel outBooks_la = new JLabel("Kik\u00F6lcs\u00F6nz\u00F6tt k\u00F6nyvek");

		JLabel inBooks_la = new JLabel("Visszahozott k\u00F6nyvek");

		JButton back_bt = new JButton("Visszav\u00E9tel");
		Icon bookBackIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_BACK, 20, new Color(0, 0, 0));
		back_bt.setIcon(bookBackIcon);

		JScrollPane scrollPane_1 = new JScrollPane();

		borrowedList = new JTable();
		borrowedList.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Azonos\u00EDt\u00F3", "K\u00F6nyv azonos\u00EDt\u00F3", "C\u00EDm", "Szerz\u0151", "Lej\u00E1rat d\u00E1tuma"
				}
			));
		borrowedList.getColumnModel().getColumn(0).setResizable(false);
		borrowedList.getColumnModel().getColumn(0).setPreferredWidth(70);
		borrowedList.getColumnModel().getColumn(1).setResizable(false);
		borrowedList.getColumnModel().getColumn(1).setPreferredWidth(100);
		borrowedList.getColumnModel().getColumn(2).setResizable(false);
		borrowedList.getColumnModel().getColumn(2).setPreferredWidth(100);
		borrowedList.getColumnModel().getColumn(3).setResizable(false);
		borrowedList.getColumnModel().getColumn(3).setPreferredWidth(120);
		borrowedList.getColumnModel().getColumn(4).setResizable(false);
		borrowedList.getColumnModel().getColumn(4).setPreferredWidth(120);
		borrowedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowedList.setShowGrid(false);
		borrowedList.setShowHorizontalLines(false);
		scrollPane_1.setViewportView(borrowedList);

		DefaultTableModel borrowListModel = (DefaultTableModel) borrowedList.getModel();

		JScrollPane scrollPane = new JScrollPane();

		returnedList = new JTable(borrowListModel);
		returnedList.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Azonos\u00EDt\u00F3", "K\u00F6nyv azonos\u00EDt\u00F3", "C\u00EDm", "Szerz\u0151", "Lej\u00E1rat d\u00E1tuma", "Visszahoz\u00E1 d\u00E1tuma", "K\u00E9s\u00E9s"
			}
		));
		returnedList.getColumnModel().getColumn(0).setResizable(false);
		returnedList.getColumnModel().getColumn(0).setPreferredWidth(70);
		returnedList.getColumnModel().getColumn(1).setResizable(false);
		returnedList.getColumnModel().getColumn(1).setPreferredWidth(100);
		returnedList.getColumnModel().getColumn(2).setPreferredWidth(200);
		returnedList.getColumnModel().getColumn(3).setPreferredWidth(150);
		returnedList.getColumnModel().getColumn(4).setResizable(false);
		returnedList.getColumnModel().getColumn(4).setPreferredWidth(120);
		returnedList.getColumnModel().getColumn(5).setPreferredWidth(120);
		returnedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnedList.setShowGrid(false);
		returnedList.setShowHorizontalLines(false);
				scrollPane.setViewportView(returnedList);
		DefaultTableModel returnedListModel = (DefaultTableModel) returnedList.getModel();

		JButton statusBookOK_btn = new JButton("OK");
		Icon okIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CHECK, 20, new Color(0, 0, 0));
		statusBookOK_btn.setIcon(okIcon);
		
		JTextArea oldBookData = new JTextArea();
		oldBookData.setMargin(new Insets(0, 2, 0, 2));
		oldBookData.setWrapStyleWord(true);
		oldBookData.setText("");
		oldBookData.setEditable(false);
		oldBookData.setBackground(SystemColor.menu);
		
		JLabel lblNewLabel = new JLabel("A visszahozott k\u00F6nyv adatai:");
		
		JTextArea outBookData = new JTextArea();
		outBookData.setWrapStyleWord(true);
		outBookData.setText("");
		outBookData.setEditable(false);
		outBookData.setBackground(SystemColor.menu);
		
		JLabel lblNewLabel_1 = new JLabel("A k\u00F6lcs\u00F6nz\u00F6tt k\u00F6nyv adatai:");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(outBooks_la, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
					.addGap(687)
					.addComponent(back_bt, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addGap(35))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addComponent(inBooks_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGap(965)
					.addComponent(statusBookOK_btn, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
					.addGap(30))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
								.addComponent(oldBookData, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
								.addComponent(outBookData, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))))
					.addGap(10))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(22)
							.addComponent(outBooks_la))
						.addComponent(back_bt))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addGap(6)
							.addComponent(outBookData, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addComponent(inBooks_la)
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel)
							.addGap(11)
							.addComponent(oldBookData, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
					.addGap(120)
					.addComponent(statusBookOK_btn))
		);
		contentPane.setLayout(gl_contentPane);

		dbm.clearTable(borrowListModel);
		int retVal = dbm.listOfActiveBorrowings(borrowListModel, member);
		if (retVal != 0) {
			JOptionPane.showMessageDialog(null, "A tag aktív kölcsönzéseinek megjelenítése sikertelen!");
		}

		dbm.clearTable(returnedListModel);
		retVal = dbm.listOfOldBorrowings(returnedListModel, member);
		if (retVal != 0) {
			JOptionPane.showMessageDialog(null, "A tag korábbi kölcsönzéseinek megjelenítése sikertelen!");
		}
//--------------------------------------------------------------------------------------------------------		
//OK gomb
		statusBookOK_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(borrowListModel);
				dbm.clearTable(returnedListModel);
				jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
			}
		});
//visszavétel gomb
		back_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book bookBack = new Book();
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.");
				Date dateBorrow, dateBackExpect;
				String bDate = "1970.01.01";
				int retVal = 0;
				if (borrowedList.getSelectedRow() != -1) {
					bookBack = dbm.selectedBook(borrowedList, 1);
					if (bookBack != null) {
						Borrow borrow = dbm.getBorrow(member, bookBack);
						if (borrow != null) {

							try {
								dateBorrow = format.parse(borrow.getDate());
								LocalDateTime myDateObj = LocalDateTime.now();
								DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
								bDate = myDateObj.format(myFormatObj);
								dateBackExpect = format.parse(bDate);
								if (dateBackExpect.compareTo(dateBorrow) > 0)
									borrow.setDelayStatus(1);
								else
									borrow.setDelayStatus(0);
							} catch (ParseException e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "A dátum ellenõrzés sikertelen!");
							}
							borrow.setBackDate(bDate);
							borrow.setBroughtBackStatus(1);
							retVal = dbm.bookTakeBack(borrow);
							if (retVal == 0) {
								retVal = dbm.changeBookBorrowStatus(String.valueOf(bookBack.getId()), 1);
								outBookData.setText("");
								if (borrow.getDelayStatus() == 1) {
									JOptionPane.showMessageDialog(null,
											"A könyv visszavétele sikeres, azonban késett!");
								}
								if (retVal != 0)
									JOptionPane.showMessageDialog(null,
											"A könyv kölcsönzési státuszának módosítása sikertelen, de a könyv visszavételre került!");
								dbm.clearTable(borrowListModel);
								retVal = dbm.listOfActiveBorrowings(borrowListModel, member);
								if (retVal != 0) {
									JOptionPane.showMessageDialog(null,
											"A tag aktív kölcsönzéseinek megjelenítése sikertelen!");
								}
								dbm.clearTable(returnedListModel);
								retVal = dbm.listOfOldBorrowings(returnedListModel, member);
								if (retVal != 0) {
									JOptionPane.showMessageDialog(null,
											"A tag korábbi kölcsönzéseinek megjelenítése sikertelen!");
								}

							} else {
								JOptionPane.showMessageDialog(null, "A könyv visszavétele sikertelen!");
							}
						} else {
							JOptionPane.showMessageDialog(null, "A kölcsönzés adatainak lekérése sikertelen!");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki azt a könyvet, amit vissza kíván venni!");
				}
			}
		});
		
		//kölcsönzött könyv adatai 
		borrowedList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String borrowId;
				if(borrowedList.getSelectedRow() != -1) {
					borrowId = ((DefaultTableModel)borrowedList.getModel()).getValueAt(borrowedList.getSelectedRow(), 0).toString();
					dbm.getBookDataFromBorrow(borrowId,outBookData);
							}
				
			}
		});
		returnedList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String borrowId;
				if(returnedList.getSelectedRow() != -1) {
					borrowId = ((DefaultTableModel)returnedList.getModel()).getValueAt(returnedList.getSelectedRow(), 0).toString();
					dbm.getBookDataFromBorrow(borrowId,oldBookData);
							}
				
			}
		});

	}
}
