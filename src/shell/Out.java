package shell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dataBase.DataBaseModule;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Out extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameOfLender_tf;
	private JTable outMemberList;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Out frame = new Out();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the frame.
	 */
	public Out(core.Book book, DefaultTableModel tableModel) {
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setResizable(false);
		JFrame jf = this;
		DataBaseModule dbm = new DataBaseModule();
		setTitle("Kiad\u00E1s (Kik\u00F6lcs\u00F6nz\u00E9s)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		nameOfLender_tf = new JTextField();
		nameOfLender_tf.setBackground(new Color(154, 205, 50));
		nameOfLender_tf.setColumns(10);

		JLabel lblNewLabel = new JLabel("A k\u00F6nyv adatai:");

		JButton out_bt = new JButton("Kik\u00F6lcs\u00F6nz\u00E9s");
		Icon bookOutIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_FORWARD, 20, new Color(0, 0, 0));
		out_bt.setIcon(bookOutIcon);

		JScrollPane outScrollPane = new JScrollPane();

		outMemberList = new JTable(tableModel);
		outMemberList.getColumnModel().getColumn(0).setResizable(false);
		outMemberList.getColumnModel().getColumn(0).setPreferredWidth(80);
		outMemberList.getColumnModel().getColumn(0).setMinWidth(80);
		outMemberList.getColumnModel().getColumn(0).setMaxWidth(80);
		outScrollPane.setViewportView(outMemberList);

		JComboBox<String> outSearchMember_cb = new JComboBox<String>();
		outSearchMember_cb.setModel(new DefaultComboBoxModel<String>(new String[] { "Keres\u0151 kateg\u00F3ria",
				"Azonos\u00EDt\u00F3", "N\u00E9v", "Lakc\u00EDm", "Tags\u00E1g" }));

		JButton outSearch_btn = new JButton("Keres\u00E9s");
		Icon searchIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SEARCH, 20, new Color(0, 0, 0));
		outSearch_btn.setIcon(searchIcon);

		JButton outCancel_btn = new JButton("M\u00E9gsem");
		Icon cancelIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CANCEL, 20, new Color(0, 0, 0));
		outCancel_btn.setIcon(cancelIcon);

		JTextArea outBookData = new JTextArea();
		outBookData.setWrapStyleWord(true);
		outBookData.setBackground(UIManager.getColor("Panel.background"));
		outBookData.setEditable(false);

		dbm.clearTable(tableModel);
		int retVal = dbm.listOfMembers(tableModel);
		if (retVal != 0)
			JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
		outBookData.setText("Azonosító:\t" + String.valueOf(book.getId()) + "\r\n" + "Cím:\t" + book.getTitle() + "\r\n"
				+ "Szerzõ:\t" + book.getAuthor() + "\r\n" + "Kiadó:\t" + book.getPublisher() + "\r\n" + "Kiadás éve:\t"
				+ String.valueOf(book.getYear()) + "\r\n" + "Kiadás:\t" + String.valueOf(book.getEdition()) + "\r\n"
				+ "ISBN:\t" + book.getISBN() + "\r\n");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(outScrollPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(37)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(nameOfLender_tf, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addComponent(outSearchMember_cb, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addGap(92)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(outCancel_btn, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
						.addComponent(out_bt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(outSearch_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
						.addComponent(outBookData, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(17)
									.addComponent(nameOfLender_tf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(30)
									.addComponent(outSearchMember_cb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(outBookData, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(34)
							.addComponent(outSearch_btn)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(out_bt)
							.addGap(11)
							.addComponent(outCancel_btn)))
					.addGap(34)
					.addComponent(outScrollPane, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);

//----------------------------------------------------------------------------------------------
//Gombok
		// Mégsem
		outCancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(tableModel);
				outSearchMember_cb.setSelectedItem("Keres\u0151 kateg\u00F3ria");
				nameOfLender_tf.setText("");
				jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
			}
		});
		// Keresés
		outSearch_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(tableModel);
				int retVal = dbm.searchMember(outSearchMember_cb.getSelectedItem().toString(),
						nameOfLender_tf.getText().trim(), tableModel);
				if (retVal != 0)
					if (retVal == -2) {
						retVal = dbm.listOfMembers(tableModel);
						nameOfLender_tf.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
					} else if (retVal == -3) {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						nameOfLender_tf.setText("");
					} else {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
					}
			}
		});
		// kikölcsönzés
		out_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Member lenderMember;
				int retVal = 0, maxBook = 0, borrowInterval = 0;
				if (outMemberList.getSelectedRow() != -1) {
					lenderMember = dbm.selectedMember(outMemberList);
					if (lenderMember != null) {
						retVal = dbm.countBook(lenderMember);
						switch (lenderMember.getMemberType()) {
						case "Egyetemi oktató": {
							maxBook = -1;
							borrowInterval = 1; // year
							break;
						}
						case "Egyetemi hallgató": {
							maxBook = 5;
							borrowInterval = 2; // months
							break;
						}
						case "Másik egyetem polgára": {
							maxBook = 4;
							borrowInterval = 1; // month
							break;
						}
						case "Egyéb": {
							maxBook = 2;
							borrowInterval = 2; // weeks
							break;
						}
						}
						if (retVal >= 0) {
							if (maxBook != -1) { // ha nem oktató

								if (retVal < maxBook) {
									Object[] options = { "Igen", "Nem" };
									retVal = JOptionPane.showOptionDialog(null, "Biztosan kiadja a kijelölt könyvet?",
											"Könyv kiadása", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
											null, options, options[0]);
									if (retVal == JOptionPane.YES_OPTION) {
										// lehet kölcsönözni
										core.Borrow borrow = new core.Borrow();
										borrow.setMemberId(lenderMember.getId());
										borrow.setBookId(book.getId());
										borrow.setBookTitle(book.getTitle());
										borrow.setBookAuthor(book.getAuthor());
										borrow.setBookPublisher(book.getPublisher());
										borrow.setBookYear(book.getYear());
										borrow.setBookEdition(book.getEdition());
										borrow.setBookISBN(book.getISBN());
										borrow.setBookBorrowStatus(0);
										// set date
										LocalDateTime myDateObj = LocalDateTime.now();
										switch (lenderMember.getMemberType()) {
										case "Egyetemi hallgató": {
											// borrowInterval = 2; // months
											myDateObj = LocalDateTime.now().plusMonths(borrowInterval);
											break;
										}
										case "Másik egyetem polgára": {
											// borrowInterval = 1; // month
											myDateObj = LocalDateTime.now().plusMonths(borrowInterval);
											break;
										}
										case "Egyéb": {
											// borrowInterval = 2; // weeks
											myDateObj = LocalDateTime.now().plusWeeks(borrowInterval);
											break;
										}
										}
										DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
										borrow.setDate(myDateObj.format(myFormatObj));
										borrow.setBroughtBackStatus(0);
										retVal = dbm.addBorrowtoDB(borrow);
										if (retVal == 0) {
											retVal = dbm.changeBookBorrowStatus(String.valueOf(book.getId()), 0);
											dbm.clearTable(tableModel);
											outSearchMember_cb.setSelectedItem("Keres\u0151 kateg\u00F3ria");
											nameOfLender_tf.setText("");
											jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
											if (retVal != 0)
												JOptionPane.showMessageDialog(null,
														"A könyv kölcsönzési státuszának módosítása sikertelen, de a könyv kiadásra került!");
										} else
											JOptionPane.showMessageDialog(null, "A kölcsönzés felvétele sikertelen!");
									}
								} else {
									JOptionPane.showMessageDialog(null,
											"A tag meghaladta a számára engedélyezett kölcsönzési limitet!");
								}
							} else {// oktató mindig kölcsönözhet, akár egy raklappal
								Object[] options = { "Igen", "Nem" };
								retVal = JOptionPane.showOptionDialog(null, "Biztosan kiadja a kijelölt könyvet?",
										"Könyv kiadása", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
										options, options[0]);
								if (retVal == JOptionPane.YES_OPTION) {
									core.Borrow borrow = new core.Borrow();
									borrow.setMemberId(lenderMember.getId());
									borrow.setBookId(book.getId());
									borrow.setBookTitle(book.getTitle());
									borrow.setBookAuthor(book.getAuthor());
									borrow.setBookPublisher(book.getPublisher());
									borrow.setBookYear(book.getYear());
									borrow.setBookEdition(book.getEdition());
									borrow.setBookISBN(book.getISBN());
									borrow.setBookBorrowStatus(0);
									LocalDateTime myDateObj = LocalDateTime.now().plusYears(borrowInterval);
									DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
									borrow.setDate(myDateObj.format(myFormatObj));
									borrow.setBroughtBackStatus(0);
									retVal = dbm.addBorrowtoDB(borrow);
									if (retVal == 0) {
										retVal = dbm.changeBookBorrowStatus(String.valueOf(book.getId()), 0);
										dbm.clearTable(tableModel);
										outSearchMember_cb.setSelectedItem("Keres\u0151 kateg\u00F3ria");
										nameOfLender_tf.setText("");
										jf.dispatchEvent(new WindowEvent(jf, WindowEvent.WINDOW_CLOSING));
										if (retVal != 0)
											JOptionPane.showMessageDialog(null,
													"A könyv kölcsönzési státuszának módosítása sikertelen, de a könyv kiadásra került!");
									} else
										JOptionPane.showMessageDialog(null, "A kölcsönzés felvétele sikertelen!");
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "A tag kölcsönzéseinek megszámolása sikertelen!");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki a tagot, akinek kiadja a könyvet!");
				}
			}
		});

	}
}
