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

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class BookBackWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField bookBackSearch;
	private JTable bookBackList;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { BookBackWindow frame = new
	 * BookBackWindow(); frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public BookBackWindow(Member member) {
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setResizable(false);
		JFrame jf = this;
		DataBaseModule dbm = new DataBaseModule();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		bookBackSearch = new JTextField();
		bookBackSearch.setBackground(new Color(154, 205, 50));
		bookBackSearch.setColumns(10);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Keres\u0151 kateg\u00F3ria",
				"Azonos\u00EDt\u00F3", "K\u00F6nyv azonos\u00EDt\u00F3", "Szerz\u0151", "C\u00EDm", "ISBN" }));

		JScrollPane bookBackScrollPane = new JScrollPane();

		bookBackList = new JTable();
		bookBackList.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Azonos\u00EDt\u00F3",
				"K\u00F6nyv azonos\u00EDt\u00F3", "C\u00EDm", "Szerz\u0151", "Lej\u00E1rat d\u00E1tuma" }));
		bookBackList.getColumnModel().getColumn(0).setResizable(false);
		bookBackList.getColumnModel().getColumn(0).setPreferredWidth(80);
		bookBackList.getColumnModel().getColumn(0).setMaxWidth(80);
		bookBackList.getColumnModel().getColumn(1).setResizable(false);
		bookBackList.getColumnModel().getColumn(1).setPreferredWidth(80);
		bookBackList.getColumnModel().getColumn(1).setMinWidth(80);
		bookBackList.getColumnModel().getColumn(1).setMaxWidth(80);
		bookBackList.getColumnModel().getColumn(2).setResizable(false);
		bookBackList.getColumnModel().getColumn(2).setPreferredWidth(100);
		bookBackList.getColumnModel().getColumn(3).setResizable(false);
		bookBackList.getColumnModel().getColumn(3).setPreferredWidth(120);
		bookBackList.getColumnModel().getColumn(4).setResizable(false);
		bookBackList.getColumnModel().getColumn(4).setPreferredWidth(120);
		bookBackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookBackList.setShowGrid(false);
		bookBackList.setShowHorizontalLines(false);
		bookBackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookBackList.setShowGrid(false);
		bookBackList.setShowHorizontalLines(false);
		bookBackScrollPane.setViewportView(bookBackList);

		DefaultTableModel bookBackListModel = (DefaultTableModel) bookBackList.getModel();

		JButton bookBack_btn = new JButton("Visszav\u00E9tel");
		Icon bookBackIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_BACK, 20, new Color(0, 0, 0));
		bookBack_btn.setIcon(bookBackIcon);

		JButton bookBackSearch_btn = new JButton("Keres\u00E9s");
		Icon searchIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SEARCH, 20, new Color(0, 0, 0));
		bookBackSearch_btn.setIcon(searchIcon);

		JButton bookBackCancel_btn = new JButton("M\u00E9gsem");
		Icon cancelIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CANCEL, 20, new Color(0, 0, 0));
		bookBackCancel_btn.setIcon(cancelIcon);

		JLabel lblNewLabel = new JLabel("A tag adatai:");

		JTextArea backBookMemberData = new JTextArea();
		backBookMemberData.setWrapStyleWord(true);
		backBookMemberData.setBackground(UIManager.getColor("Panel.background"));

		dbm.clearTable(bookBackListModel);
		int retVal = dbm.listOfActiveBorrowings(bookBackListModel, member);
		if (retVal != 0)
			JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
		backBookMemberData.setText("Azonosító:\t" + String.valueOf(member.getId()) + "\r\n" + "Lakcím:\t"
				+ member.getAddress() + "\r\n" + "Tagság:\t" + member.getMemberType() + "\r\n" + "Elérhetõség:\t"
				+ member.getContact() + "\r\n");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(bookBackScrollPane, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(bookBackSearch, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGap(120)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bookBackSearch_btn, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
						.addComponent(bookBack_btn, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(bookBackCancel_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addComponent(backBookMemberData, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(43)
							.addComponent(bookBackSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(25)
							.addComponent(bookBackSearch_btn)
							.addGap(11)
							.addComponent(bookBack_btn)
							.addGap(11)
							.addComponent(bookBackCancel_btn))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(backBookMemberData, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addComponent(bookBackScrollPane, GroupLayout.PREFERRED_SIZE, 365, GroupLayout.PREFERRED_SIZE))
		);
		contentPane.setLayout(gl_contentPane);

//-----------------------------------------------------------------------------------------------------
//Gombok

		// Mégsem
		bookBackCancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(bookBackListModel);
				comboBox.setSelectedItem("Keres\u0151 kateg\u00F3ria");
				bookBackSearch.setText("");
				jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
			}
		});
		// Keresés
		bookBackSearch_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(bookBackListModel);
				int retVal = dbm.searchBorrowing(comboBox.getSelectedItem().toString(), bookBackSearch.getText().trim(),
						bookBackListModel, member, 1);
				if (retVal != 0)
					if (retVal == -2) {
						retVal = dbm.listOfActiveBorrowings(bookBackListModel, member);
						bookBackSearch.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
					} else if (retVal == -3) {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						bookBackSearch.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
					}
			}
		});
		// Visszavétel
		bookBack_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Book bookBack = new Book();
				SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.");
				Date dateBorrow, dateBack;
				String bDate = "1970.01.01";
				int retVal = 0;
				if (bookBackList.getSelectedRow() != -1) {
					bookBack = dbm.selectedBook(bookBackList, 1);
					if (bookBack != null) {
						Borrow borrow = dbm.getBorrow(member, bookBack);
						if (borrow != null) {
							Object[] options = { "Igen", "Nem" };
							retVal = JOptionPane.showOptionDialog(null, "Biztosan visszaveszi a kijelölt könyvet?",
									"Könyv visszavétel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									options, options[0]);
							if (retVal == JOptionPane.YES_OPTION) {

								try {
									dateBorrow = format.parse(borrow.getDate());
									LocalDateTime myDateObj = LocalDateTime.now();
									DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
									bDate = myDateObj.format(myFormatObj);
									dateBack = format.parse(bDate);
									if (dateBack.compareTo(dateBorrow) > 0)
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
									dbm.clearTable(bookBackListModel);
									comboBox.setSelectedItem("Keres\u0151 kateg\u00F3ria");
									bookBackSearch.setText("");
									jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
									if (borrow.getDelayStatus() == 1) {
										JOptionPane.showMessageDialog(null,
												"A könyv visszavétele sikeres, azonban késett!");
									}
									if (retVal != 0)
										JOptionPane.showMessageDialog(null,
												"A könyv kölcsönzési státuszának módosítása sikertelen, de a könyv visszavételre került!");
								} else {
									JOptionPane.showMessageDialog(null, "A könyv visszavétele sikertelen!");
								}
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

	}
}
