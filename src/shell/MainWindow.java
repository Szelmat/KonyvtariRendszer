package shell;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import core.Book;
import core.Member;
import dataBase.DataBaseModule;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import java.awt.BorderLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Cursor;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField name_tf, contact_tf, address_tf;
	private ButtonGroup bg;
	private JRadioButton noModify_rbt, yesModify_rbt;
	private JLayeredPane layeredPane;
	private JPanel newBook_panel, searchBook_panel, newMember_panel, searchMember_panel, bookBack_panel, bookOut_panel,
			modifyBook_panel, modifyMember_panel;
	protected static boolean visibility = false;
	private JTextField textBook_tf;
	private JTextField textMember_tf;
	private JTextField textField3;
	private JTextField textField_2;
	private JTextField authorModify_tf;
	private JTextField titleModify_tf;
	private JTextField publisherModify_tf;
	private JTextField isbnModify_tf;
	private JTextField nameModify_tf;
	private JTextField addressModify_tf;
	private JTextField contactModify_tf;
	Member currentMember;
	Book currentBook;

	final String[] types = { "Egyetemi hallgató", "Egyetemi oktató", "Másik egyetem polgára", "Egyéb" };
	private JTable bookList;
	private JTable borrowBookList;
	private JTable memberList;
	private JTable backMemberList;
	private JTextField title_tf;
	private JTextField publisher_tf;
	private JTextField ISBN_tf;
	private JTextField author_tf;
	private JTable borrowList;
	private JTextField borrowListSearch_tf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					// frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * It switches between windows panels. It's suitable for show more than one
	 * JPanel UI in the same window.
	 * 
	 * @param panel which will be loaded to window.
	 */
	public void changePanels(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}

	public MainWindow() {
		IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
		
		JFrame jf = this;
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		LogIN lIn = new LogIN(this);
		lIn.setVisible(true);
		lIn.setLocationRelativeTo(null);
		DataBaseModule dbm = new DataBaseModule();
		setTitle("K.A.R");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
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

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu books_menu = new JMenu("K\u00F6nyvek");
		Icon bookIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LIBRARY_BOOKS, 17, new Color(0, 0, 0));
		books_menu.setIcon(bookIcon);
		menuBar.add(books_menu);

		JMenuItem newBook_menuItem = new JMenuItem("\u00DAj k\u00F6nyv felv\u00E9tele");
		Icon newBookIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LIBRARY_ADD, 17, new Color(0, 0, 0));
		newBook_menuItem.setIcon(newBookIcon);
		newBook_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// új könyv felvétele
				changePanels(newBook_panel);
			}
		});
		books_menu.add(newBook_menuItem);

		JMenuItem searchBook_menuItem = new JMenuItem("List\u00E1z\u00E1s \u00E9s keres\u00E9s");
		Icon searchBookIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LIST, 17, new Color(0, 0, 0));
		searchBook_menuItem.setIcon(searchBookIcon);

		books_menu.add(searchBook_menuItem);

		JMenu members_menu = new JMenu("Tagok");
		Icon membersIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PEOPLE, 17, new Color(0, 0, 0));
		members_menu.setIcon(membersIcon);
		menuBar.add(members_menu);

		JMenuItem newMember_menuItem = new JMenuItem("\u00DAj tag felv\u00E9tele");
		Icon newMemberIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.PERSON_ADD, 17, new Color(0, 0, 0));
		newMember_menuItem.setIcon(newMemberIcon);
		newMember_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// új tag hozzáadása
				changePanels(newMember_panel);
			}
		});
		members_menu.add(newMember_menuItem);

		JMenuItem searchMember_menuItem = new JMenuItem("List\u00E1z\u00E1s \u00E9s keres\u00E9s");
		Icon ListMembersIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LIST, 17, new Color(0, 0, 0));
		searchMember_menuItem.setIcon(ListMembersIcon);
		members_menu.add(searchMember_menuItem);

		JMenu borrows_menu = new JMenu("K\u00F6lcs\u00F6nz\u00E9sek");
		Icon borrowsIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FOLDER_SHARED, 17, new Color(0, 0, 0));
		borrows_menu.setIcon(borrowsIcon);
		menuBar.add(borrows_menu);

		JMenuItem bookBack_menuItem = new JMenuItem("K\u00F6nyv visszav\u00E9tel");
		Icon bookBackIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_BACK, 17, new Color(0, 0, 0));
		bookBack_menuItem.setIcon(bookBackIcon);
		borrows_menu.add(bookBack_menuItem);

		JMenuItem bookGive_menuItem = new JMenuItem("K\u00F6nyv kiad\u00E1s");
		Icon bookGiveIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_FORWARD, 17, new Color(0, 0, 0));
		bookGive_menuItem.setIcon(bookGiveIcon);
		borrows_menu.add(bookGive_menuItem);

		JMenuItem borrowList_menuItem = new JMenuItem("Aktív kölcsönzések");
		Icon borrowListIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FOLDER_SPECIAL, 17, new Color(0, 0, 0));
		borrowList_menuItem.setIcon(borrowListIcon);
		borrows_menu.add(borrowList_menuItem);
		getContentPane().setLayout(new BorderLayout(0, 0));

		layeredPane = new JLayeredPane();
		getContentPane().add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));

		JPanel welcome_panel = new JPanel();
		welcome_panel.setName("welcome_panel");
		layeredPane.add(welcome_panel, "name_481347393387000");

		JLabel main_la = new JLabel("\u00DCdv\u00F6z\u00F6llek a KAR fel\u00FClet\u00E9ben!", SwingConstants.CENTER);
		welcome_panel.setLayout(new BorderLayout(0, 0));
		main_la.setHorizontalAlignment(SwingConstants.CENTER);
		main_la.setFont(new Font("Tahoma", Font.PLAIN, 14));
		welcome_panel.add(main_la);

		JPanel welcomeLayoutPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) welcomeLayoutPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		welcome_panel.add(welcomeLayoutPanel, BorderLayout.NORTH);

		Icon signOutIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.EXIT_TO_APP, 20, new Color(0, 0, 0));
		JButton LogOut_btn = new JButton("Kijelentkezés");
		LogOut_btn.setIcon(signOutIcon);
		LogOut_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(false);
				lIn.setVisible(true);
			}
		});
		welcomeLayoutPanel.add(LogOut_btn);

		newBook_panel = new JPanel();
		newBook_panel.setName("newBook_panel");
		layeredPane.add(newBook_panel, "name_410899508282100");

		bg = new ButtonGroup();

		JTextPane infoText = new JTextPane();
		infoText.setEditable(false);
		infoText.setBackground(new Color(240, 240, 240));

		JPanel componentPanel = new JPanel();
		componentPanel.setMaximumSize(new Dimension(800, 600));
		componentPanel.setPreferredSize(new Dimension(784, 540));
		componentPanel.setMinimumSize(new Dimension(0, 0));

		JLabel title_la = new JLabel("C\u00EDm");

		title_tf = new JTextField();
		title_tf.setMargin(new Insets(1, 2, 1, 2));
		title_tf.setColumns(10);

		JLabel publisher_la = new JLabel("Kiad\u00F3");

		publisher_tf = new JTextField();
		publisher_tf.setMargin(new Insets(1, 2, 1, 2));
		publisher_tf.setColumns(10);

		JLabel ISBN_la = new JLabel("ISBN-sz\u00E1m");

		ISBN_tf = new JTextField();
		ISBN_tf.setMargin(new Insets(1, 2, 1, 2));
		ISBN_tf.setColumns(10);

		JLabel date_la = new JLabel("\u00C9vsz\u00E1m");

		JComboBox<String> date_cB = new JComboBox<String>();
		date_cB.addItem("Válasszon...");
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy");
		int yearDate = Integer.parseInt(myDateObj.format(myFormatObj));
		for (int i = yearDate; i >= 1940; i--) {
			date_cB.addItem(String.valueOf(i));
		}

		JLabel edition_la = new JLabel("Kiad\u00E1s");

		JSpinner edition_spinner = new JSpinner();
		edition_spinner.setModel(new SpinnerNumberModel(0, 0, 20, 1));

		JLabel copy_la = new JLabel("P\u00E9ld\u00E1ny");

		JSpinner copy_spinner = new JSpinner();
		copy_spinner.setModel(new SpinnerNumberModel(0, 0, 20, 1));

		JLabel borrow_la = new JLabel("K\u00F6lcs\u00F6n\u00F6zhet\u0151-e?");

		JButton cancelNewBook_bt = new JButton("M\u00E9gsem");
		cancelNewBook_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon cancelIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CANCEL, 20, new Color(0, 0, 0));
		cancelNewBook_bt.setIcon(cancelIcon);
		
		JRadioButton no_rbt = new JRadioButton("nem");
		bg.add(no_rbt);

		JRadioButton yes_rbt = new JRadioButton("igen");
		bg.add(yes_rbt);

		JButton addNewBook_bt = new JButton("Felv\u00E9tel");
		addNewBook_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon saveIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SAVE, 20, new Color(0, 0, 0));
		addNewBook_bt.setIcon(saveIcon);

		JLabel author_la = new JLabel("Szerz\u0151");

		author_tf = new JTextField();
		author_tf.setMaximumSize(new Dimension(400, 30));
		author_tf.setMargin(new Insets(1, 2, 1, 2));
		author_tf.setColumns(10);
		newBook_panel.setLayout(new BorderLayout(0, 0));
		newBook_panel.add(infoText, BorderLayout.SOUTH);

		JPanel layoutPanel = new JPanel();
		layoutPanel.add(componentPanel);
		GroupLayout gl_componentPanel = new GroupLayout(componentPanel);
		gl_componentPanel.setHorizontalGroup(
			gl_componentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_componentPanel.createSequentialGroup()
					.addGap(258)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addComponent(title_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(title_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addComponent(author_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(author_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addComponent(publisher_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(publisher_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addComponent(ISBN_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(ISBN_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addComponent(date_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(date_cB, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addGroup(gl_componentPanel.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(edition_la, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
								.addComponent(borrow_la, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
								.addComponent(copy_la, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_componentPanel.createSequentialGroup()
									.addComponent(cancelNewBook_bt, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(6)
							.addGroup(gl_componentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_componentPanel.createSequentialGroup()
										.addGap(49)
										.addComponent(copy_spinner, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_componentPanel.createSequentialGroup()
										.addGap(49)
										.addComponent(edition_spinner, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_componentPanel.createSequentialGroup()
										.addGap(73)
										.addGroup(gl_componentPanel.createParallelGroup(Alignment.TRAILING)
											.addComponent(no_rbt)
											.addComponent(yes_rbt))))
								.addComponent(addNewBook_bt, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(257, Short.MAX_VALUE))
		);
		gl_componentPanel.setVerticalGroup(
			gl_componentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_componentPanel.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(title_la))
						.addComponent(title_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(author_la))
						.addComponent(author_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(publisher_la))
						.addComponent(publisher_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(ISBN_la))
						.addComponent(ISBN_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(date_la))
						.addComponent(date_cB, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_componentPanel.createSequentialGroup()
							.addComponent(edition_spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_componentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(copy_spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(copy_la))
							.addGap(2)
							.addGroup(gl_componentPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(yes_rbt)
								.addComponent(borrow_la))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(no_rbt))
						.addComponent(edition_la))
					.addGap(30)
					.addGroup(gl_componentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelNewBook_bt)
						.addComponent(addNewBook_bt))
					.addContainerGap(168, Short.MAX_VALUE))
		);
		componentPanel.setLayout(gl_componentPanel);
		newBook_panel.add(layoutPanel);

		searchBook_panel = new JPanel();
		searchBook_panel.setName("searchBook_panel");
		layeredPane.add(searchBook_panel, "name_410902106894000");

		JComboBox<String> searchBook_comboBox = new JComboBox<String>();
		searchBook_comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Kereső kategória", "Azonosító", "Szerz\u0151", "C\u00EDm", "ISBN" }));
		searchBook_comboBox.setToolTipText("");

		JButton searchBook_bt = new JButton("Keresés");
		searchBook_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon searchIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.SEARCH, 20, new Color(0, 0, 0));
		searchBook_bt.setIcon(searchIcon);

		JButton deleteBook_bt = new JButton("Törlés");
		deleteBook_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon deleteIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.DELETE, 20, new Color(0, 0, 0));
		deleteBook_bt.setIcon(deleteIcon);

		JButton editBook_bt = new JButton("Módosítás");
		editBook_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon editIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.EDIT, 20, new Color(0, 0, 0));
		editBook_bt.setIcon(editIcon);

		JScrollPane scrollPane = new JScrollPane();
		bookList = new JTable();
		bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookList.setShowGrid(false);
		bookList.setShowHorizontalLines(false);
		bookList.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Azonos\u00EDt\u00F3", "C\u00EDm",
				"Szerz\u0151", "Kiad\u00F3", "\u00C9v", "Kiad\u00E1s", "ISBN", "K\u00F6lcs\u00F6n\u00F6zhet\u0151" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookList.getColumnModel().getColumn(0).setResizable(false);
		bookList.getColumnModel().getColumn(0).setPreferredWidth(80);
		bookList.getColumnModel().getColumn(0).setMaxWidth(80);
		bookList.getColumnModel().getColumn(1).setResizable(false);
		bookList.getColumnModel().getColumn(1).setPreferredWidth(200);
		bookList.getColumnModel().getColumn(2).setResizable(false);
		bookList.getColumnModel().getColumn(2).setPreferredWidth(130);
		bookList.getColumnModel().getColumn(3).setResizable(false);
		bookList.getColumnModel().getColumn(3).setPreferredWidth(200);
		bookList.getColumnModel().getColumn(4).setResizable(false);
		bookList.getColumnModel().getColumn(4).setPreferredWidth(80);
		bookList.getColumnModel().getColumn(4).setMaxWidth(80);
		bookList.getColumnModel().getColumn(5).setResizable(false);
		bookList.getColumnModel().getColumn(5).setPreferredWidth(80);
		bookList.getColumnModel().getColumn(5).setMaxWidth(80);
		bookList.getColumnModel().getColumn(6).setResizable(false);
		bookList.getColumnModel().getColumn(6).setPreferredWidth(140);
		bookList.getColumnModel().getColumn(7).setResizable(false);
		bookList.getColumnModel().getColumn(7).setPreferredWidth(100);
		scrollPane.setViewportView(bookList);

		DefaultTableModel bookListModel = (DefaultTableModel) bookList.getModel();

		JButton backBook_btn = new JButton("Vissza");
		backBook_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon backIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CHEVRON_LEFT, 20, new Color(0, 0, 0));
		backBook_btn.setIcon(backIcon);

		textBook_tf = new JTextField();
		textBook_tf.setMargin(new Insets(1, 2, 1, 2));
		textBook_tf.setPreferredSize(new Dimension(150, 30));
		textBook_tf.setBackground(new Color(154, 205, 50));
		textBook_tf.setColumns(10);
		GroupLayout gl_searchBook_panel = new GroupLayout(searchBook_panel);
		gl_searchBook_panel.setHorizontalGroup(gl_searchBook_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_searchBook_panel.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE).addContainerGap())
				.addGroup(gl_searchBook_panel.createSequentialGroup().addGroup(gl_searchBook_panel
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_searchBook_panel.createSequentialGroup().addContainerGap().addComponent(
								searchBook_bt, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_searchBook_panel.createSequentialGroup().addGap(40)
								.addGroup(gl_searchBook_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(textBook_tf, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
										.addComponent(searchBook_comboBox, GroupLayout.PREFERRED_SIZE, 150,
												GroupLayout.PREFERRED_SIZE))
								.addGap(234)
								.addGroup(gl_searchBook_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(editBook_bt, GroupLayout.PREFERRED_SIZE, 110,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(deleteBook_bt, GroupLayout.PREFERRED_SIZE, 110,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(backBook_btn, GroupLayout.PREFERRED_SIZE, 110,
												GroupLayout.PREFERRED_SIZE))))
						.addGap(200)));
		gl_searchBook_panel
				.setVerticalGroup(gl_searchBook_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_searchBook_panel.createSequentialGroup().addGap(20).addComponent(searchBook_bt)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_searchBook_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(textBook_tf, GroupLayout.PREFERRED_SIZE, 30,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(editBook_bt))
								.addGroup(gl_searchBook_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_searchBook_panel.createSequentialGroup().addGap(20).addComponent(
												searchBook_comboBox, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_searchBook_panel.createSequentialGroup().addGap(6)
												.addComponent(deleteBook_bt).addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(backBook_btn)))
								.addGap(42).addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
								.addContainerGap()));
		searchBook_panel.setLayout(gl_searchBook_panel);

		JPanel memberComponentsPanel = new JPanel();
		memberComponentsPanel.setPreferredSize(new Dimension(784, 540));
		memberComponentsPanel.setMaximumSize(new Dimension(800, 600));

		JPanel memberLayoutPanel = new JPanel();

		newMember_panel = new JPanel();
		newMember_panel.setName("newMember_panel");
		layeredPane.add(newMember_panel, "name_410904261182100");
		newMember_panel.setLayout(new BorderLayout(0, 0));
		memberLayoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JTextPane memberInfoText = new JTextPane();
		newMember_panel.add(memberInfoText, BorderLayout.SOUTH);

		JLabel name_la = new JLabel("N\u00E9v");

		JLabel address_la = new JLabel("Lakc\u00EDm");

		JLabel contact_la = new JLabel("El\u00E9rhet\u0151s\u00E9g");

		JLabel type_la = new JLabel("Tags\u00E1g");

		name_tf = new JTextField();
		name_tf.setMargin(new Insets(1, 2, 1, 2));
		name_tf.setColumns(10);

		address_tf = new JTextField();
		address_tf.setMargin(new Insets(1, 2, 1, 2));
		address_tf.setColumns(10);

		contact_tf = new JTextField();
		contact_tf.setMargin(new Insets(1, 2, 1, 2));
		contact_tf.setColumns(10);

		JComboBox<String> type_cB = new JComboBox<String>();
		type_cB.setModel(new DefaultComboBoxModel<String>(new String[] { "V\u00E1lasszon...", "Egyetemi hallgat\u00F3",
				"Egyetemi oktat\u00F3", "Másik egyetem polgára", "Egyéb" }));

		JButton addNewMember_bt = new JButton("Felv\u00E9tel");
		addNewMember_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addNewMember_bt.setIcon(saveIcon);

		JButton cancelNewMember_bt = new JButton("M\u00E9gsem");
		cancelNewMember_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelNewMember_bt.setIcon(cancelIcon);

		memberLayoutPanel.add(memberComponentsPanel);
		newMember_panel.add(memberLayoutPanel, BorderLayout.CENTER);

		searchMember_panel = new JPanel();
		searchMember_panel.setName("searchMember_panel");
		layeredPane.add(searchMember_panel, "name_410906656984400");

		JButton searchMember_bt = new JButton("Keres\u00E9s");
		searchMember_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchMember_bt.setIcon(searchIcon);

		JButton borBooks_bt = new JButton("K\u00F6lcs\u00F6nz\u00E9sek");
		borBooks_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon borrowsIconBig = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FOLDER_SHARED, 20, new Color(0, 0, 0));
		borBooks_bt.setIcon(borrowsIconBig);

		JButton deleteMember_bt = new JButton("T\u00F6rl\u00E9s");
		deleteMember_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		deleteMember_bt.setIcon(deleteIcon);

		JComboBox<String> searchMember_cb = new JComboBox<String>();
		searchMember_cb.setModel(new DefaultComboBoxModel<String>(new String[] { "Keres\u0151 kateg\u00F3ria",
				"Azonos\u00EDt\u00F3", "N\u00E9v", "Lakc\u00EDm", "Tags\u00E1g" }));

		JButton editMember_bt = new JButton("M\u00F3dos\u00EDt\u00E1s");
		editMember_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		editMember_bt.setIcon(editIcon);

		JButton backMember_bt = new JButton("Vissza");
		backMember_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backMember_bt.setIcon(backIcon);

		textMember_tf = new JTextField();
		textMember_tf.setMargin(new Insets(1, 2, 1, 2));
		textMember_tf.setBackground(new Color(154, 205, 50));
		textMember_tf.setColumns(10);

		JScrollPane memberScrollPane = new JScrollPane();
		memberScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		memberList = new JTable();
		memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		memberList.setShowHorizontalLines(false);
		memberList.setShowGrid(false);
		memberList.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Azonos\u00EDt\u00F3", "N\u00E9v",
				"Lakc\u00EDm", "Tags\u00E1g", "El\u00E9rhet\u0151s\u00E9g" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		memberList.getColumnModel().getColumn(0).setResizable(false);
		memberList.getColumnModel().getColumn(0).setPreferredWidth(80);
		memberList.getColumnModel().getColumn(0).setMinWidth(80);
		memberList.getColumnModel().getColumn(0).setMaxWidth(80);
		memberList.getColumnModel().getColumn(1).setResizable(false);
		memberList.getColumnModel().getColumn(1).setPreferredWidth(150);
		memberList.getColumnModel().getColumn(2).setResizable(false);
		memberList.getColumnModel().getColumn(2).setPreferredWidth(250);
		memberList.getColumnModel().getColumn(3).setResizable(false);
		memberList.getColumnModel().getColumn(3).setPreferredWidth(120);
		memberList.getColumnModel().getColumn(4).setResizable(false);
		memberList.getColumnModel().getColumn(4).setPreferredWidth(250);
		memberScrollPane.setViewportView(memberList);
		GroupLayout gl_searchMember_panel = new GroupLayout(searchMember_panel);
		gl_searchMember_panel.setHorizontalGroup(
			gl_searchMember_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_searchMember_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(memberScrollPane, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_searchMember_panel.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_searchMember_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_searchMember_panel.createSequentialGroup()
							.addComponent(textMember_tf, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
							.addGap(222))
						.addGroup(gl_searchMember_panel.createSequentialGroup()
							.addComponent(searchMember_cb, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_searchMember_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(searchMember_bt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_searchMember_panel.createSequentialGroup()
							.addComponent(backMember_bt, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addComponent(editMember_bt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(deleteMember_bt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(borBooks_bt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(200))
		);
		gl_searchMember_panel.setVerticalGroup(
			gl_searchMember_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchMember_panel.createSequentialGroup()
					.addGap(20)
					.addComponent(searchMember_bt)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_searchMember_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_searchMember_panel.createSequentialGroup()
							.addComponent(borBooks_bt)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(deleteMember_bt)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(editMember_bt))
						.addGroup(gl_searchMember_panel.createSequentialGroup()
							.addComponent(textMember_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(20)
							.addComponent(searchMember_cb, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(backMember_bt)
					.addGap(20)
					.addComponent(memberScrollPane, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
					.addContainerGap())
		);
		searchMember_panel.setLayout(gl_searchMember_panel);
		DefaultTableModel memberListModel = (DefaultTableModel) memberList.getModel();

		bookBack_panel = new JPanel();
		bookBack_panel.setMinimumSize(new Dimension(784, 540));
		bookBack_panel.setPreferredSize(new Dimension(784, 540));
		bookBack_panel.setName("bookBack_panel");
		layeredPane.add(bookBack_panel, "name_410959096946700");

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Keres\u0151 kateg\u00F3ria",
				"Azonos\u00EDt\u00F3", "N\u00E9v", "Lakc\u00EDm", "Tags\u00E1g" }));

		JButton search_bt = new JButton("Keres\u00E9s");
		search_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		search_bt.setIcon(searchIcon);

		JButton choose_bt = new JButton("Kiv\u00E1laszt");
		choose_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon chooseIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.CHECK, 20, new Color(0, 0, 0));
		choose_bt.setIcon(chooseIcon);

		textField_2 = new JTextField();
		textField_2.setMargin(new Insets(1, 2, 1, 2));
		textField_2.setBackground(new Color(154, 205, 50));
		textField_2.setColumns(10);

		JScrollPane bookBackScrollPane = new JScrollPane();
		backMemberList = new JTable(memberListModel);
		backMemberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		backMemberList.setShowHorizontalLines(false);
		backMemberList.setShowGrid(false);
		backMemberList.getColumnModel().getColumn(0).setResizable(false);
		backMemberList.getColumnModel().getColumn(0).setPreferredWidth(80);
		backMemberList.getColumnModel().getColumn(0).setMinWidth(80);
		backMemberList.getColumnModel().getColumn(0).setMaxWidth(80);
		bookBackScrollPane.setViewportView(backMemberList);

		JButton backCancel_btn = new JButton("Vissza");
		backCancel_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		backCancel_btn.setIcon(backIcon);

		JButton bookBackActiveBorr_btn = new JButton("Aktív kölcsönzések");
		bookBackActiveBorr_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon activeBorrowsIcon = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FOLDER_SPECIAL, 20, new Color(0, 0, 0));
		bookBackActiveBorr_btn.setIcon(activeBorrowsIcon);
		GroupLayout gl_bookBack_panel = new GroupLayout(bookBack_panel);
		gl_bookBack_panel.setHorizontalGroup(
			gl_bookBack_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bookBack_panel.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_bookBack_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_bookBack_panel.createSequentialGroup()
							.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
							.addGap(173))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
					.addGroup(gl_bookBack_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(bookBackActiveBorr_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(backCancel_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(choose_bt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(search_bt, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
					.addGap(151))
				.addGroup(gl_bookBack_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(bookBackScrollPane, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_bookBack_panel.setVerticalGroup(
			gl_bookBack_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bookBack_panel.createSequentialGroup()
					.addGroup(gl_bookBack_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_bookBack_panel.createSequentialGroup()
							.addGap(49)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(20)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_bookBack_panel.createSequentialGroup()
							.addGap(20)
							.addComponent(search_bt)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(choose_bt)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(backCancel_btn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bookBackActiveBorr_btn)))
					.addGap(47)
					.addComponent(bookBackScrollPane, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
					.addContainerGap())
		);
		bookBack_panel.setLayout(gl_bookBack_panel);

		bookOut_panel = new JPanel();
		bookOut_panel.setName("bookOut_panel");
		layeredPane.add(bookOut_panel, "name_410961460734600");

		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Kereső kategória", "Azonosító", "Szerz\u0151", "C\u00EDm", "ISBN" }));

		JButton search_bt_1 = new JButton("Keres\u00E9s");
		search_bt_1.setIcon(searchIcon);
		search_bt_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton bookGive_bt = new JButton("Kiad\u00E1s");
		bookGive_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Icon bookGiveIconBig = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.ARROW_FORWARD, 20, new Color(0, 0, 0));
		bookGive_bt.setIcon(bookGiveIconBig);

		textField3 = new JTextField();
		textField3.setMargin(new Insets(1, 2, 1, 2));
		textField3.setBackground(new Color(154, 205, 50));
		textField3.setColumns(10);

		JButton newBorrowBack_btn = new JButton("Vissza");
		newBorrowBack_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		newBorrowBack_btn.setIcon(backIcon);

		JScrollPane newBorrowScrollPane = new JScrollPane();
		newBorrowScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		borrowBookList = new JTable((DefaultTableModel) bookList.getModel());
		borrowBookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		borrowBookList.setShowHorizontalLines(false);
		borrowBookList.setShowGrid(false);
		borrowBookList.getColumnModel().getColumn(0).setResizable(false);
		borrowBookList.getColumnModel().getColumn(0).setPreferredWidth(80);
		borrowBookList.getColumnModel().getColumn(0).setMinWidth(80);
		borrowBookList.getColumnModel().getColumn(0).setMaxWidth(80);
		newBorrowScrollPane.setViewportView(borrowBookList);

		JButton bookOutActiveBorr_btn = new JButton("Aktív kölcsönzések");
		bookOutActiveBorr_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bookOutActiveBorr_btn.setIcon(activeBorrowsIcon);
		GroupLayout gl_bookOut_panel = new GroupLayout(bookOut_panel);
		gl_bookOut_panel.setHorizontalGroup(
			gl_bookOut_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bookOut_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(newBorrowScrollPane, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_bookOut_panel.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_bookOut_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField3, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
					.addGap(192)
					.addGroup(gl_bookOut_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(bookOutActiveBorr_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(newBorrowBack_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(bookGive_bt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(search_bt_1, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE))
					.addGap(96))
		);
		gl_bookOut_panel.setVerticalGroup(
			gl_bookOut_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bookOut_panel.createSequentialGroup()
					.addGroup(gl_bookOut_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_bookOut_panel.createSequentialGroup()
							.addGap(50)
							.addComponent(textField3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(20)
							.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_bookOut_panel.createSequentialGroup()
							.addGap(23)
							.addComponent(search_bt_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bookGive_bt)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(newBorrowBack_btn)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bookOutActiveBorr_btn)))
					.addGap(40)
					.addComponent(newBorrowScrollPane, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addContainerGap())
		);
		bookOut_panel.setLayout(gl_bookOut_panel);

		modifyBook_panel = new JPanel();
		modifyBook_panel.setName("modifyBook_panel");
		layeredPane.add(modifyBook_panel, "name_866762940457400");
		modifyBook_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel modBookComponentPanel = new JPanel();
		modBookComponentPanel.setMaximumSize(new Dimension(800, 600));
		modBookComponentPanel.setPreferredSize(new Dimension(784, 540));
		modifyBook_panel.add(modBookComponentPanel);

		JLabel author_la_1 = new JLabel("Szerz\u0151");

		authorModify_tf = new JTextField();
		authorModify_tf.setMargin(new Insets(1, 2, 1, 2));
		authorModify_tf.setColumns(10);

		titleModify_tf = new JTextField();
		titleModify_tf.setMargin(new Insets(1, 2, 1, 2));
		titleModify_tf.setColumns(10);

		publisherModify_tf = new JTextField();
		publisherModify_tf.setMargin(new Insets(1, 2, 1, 2));
		publisherModify_tf.setColumns(10);

		JSpinner editionModify_spinner = new JSpinner();
		editionModify_spinner.setModel(new SpinnerNumberModel(0, 0, 20, 1));

		JSpinner copyModify_spinner = new JSpinner();
		copyModify_spinner.setModel(new SpinnerNumberModel(0, 0, 20, 1));

		yesModify_rbt = new JRadioButton("igen");

		noModify_rbt = new JRadioButton("nem");

		bg.add(noModify_rbt);
		bg.add(yesModify_rbt);

		JLabel title_la_1 = new JLabel("C\u00EDm");

		JLabel publisher_la_1 = new JLabel("Kiad\u00F3");

		JLabel ISBN_la_1 = new JLabel("ISBN-sz\u00E1m");

		JLabel date_la_1 = new JLabel("\u00C9vsz\u00E1m");

		JLabel edition_la_1 = new JLabel("Kiad\u00E1s");

		JLabel copy_la_1 = new JLabel("P\u00E9ld\u00E1ny");

		JLabel borrow_la_1 = new JLabel("K\u00F6lcs\u00F6n\u00F6zhet\u0151-e?");

		JComboBox<String> dateModify_cB = new JComboBox<String>();
		dateModify_cB.addItem("Válasszon...");
		for (int i = 1940; i <= 2020; i++) {
			dateModify_cB.addItem(String.valueOf(i));
		}

		isbnModify_tf = new JTextField();
		isbnModify_tf.setMargin(new Insets(1, 2, 1, 2));
		isbnModify_tf.setColumns(10);

		JButton saveBookModify_bt = new JButton("Ment\u00E9s");
		saveBookModify_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveBookModify_bt.setIcon(saveIcon);

		JButton cancelBookModify_bt = new JButton("M\u00E9gsem");
		cancelBookModify_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelBookModify_bt.setIcon(cancelIcon);
		GroupLayout gl_modBookComponentPanel = new GroupLayout(modBookComponentPanel);
		gl_modBookComponentPanel.setHorizontalGroup(
			gl_modBookComponentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modBookComponentPanel.createSequentialGroup()
					.addGap(426)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(noModify_rbt, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addComponent(yesModify_rbt, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(299, Short.MAX_VALUE))
				.addGroup(gl_modBookComponentPanel.createSequentialGroup()
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_modBookComponentPanel.createSequentialGroup()
							.addGap(258)
							.addComponent(cancelBookModify_bt, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(saveBookModify_bt, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_modBookComponentPanel.createSequentialGroup()
							.addGap(258)
							.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_modBookComponentPanel.createSequentialGroup()
									.addComponent(ISBN_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(isbnModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_modBookComponentPanel.createSequentialGroup()
									.addComponent(publisher_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(publisherModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_modBookComponentPanel.createSequentialGroup()
									.addComponent(author_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(authorModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_modBookComponentPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(title_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(titleModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_modBookComponentPanel.createSequentialGroup()
									.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_modBookComponentPanel.createSequentialGroup()
											.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(borrow_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
												.addComponent(copy_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
											.addGap(1))
										.addComponent(date_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
										.addComponent(edition_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(dateModify_cB, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.LEADING, gl_modBookComponentPanel.createSequentialGroup()
							.addGap(429)
							.addComponent(editionModify_spinner, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_modBookComponentPanel.createSequentialGroup()
							.addGap(429)
							.addComponent(copyModify_spinner, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(256, Short.MAX_VALUE))
		);
		gl_modBookComponentPanel.setVerticalGroup(
			gl_modBookComponentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modBookComponentPanel.createSequentialGroup()
					.addGap(100)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(title_la_1)
						.addComponent(titleModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(author_la_1)
						.addComponent(authorModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(publisherModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(publisher_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(isbnModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(ISBN_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(dateModify_cB, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(date_la_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(editionModify_spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(edition_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(copyModify_spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(copy_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(yesModify_rbt)
						.addComponent(borrow_la_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(noModify_rbt)
					.addGap(32)
					.addGroup(gl_modBookComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelBookModify_bt)
						.addComponent(saveBookModify_bt))
					.addGap(66))
		);
		modBookComponentPanel.setLayout(gl_modBookComponentPanel);

		modifyMember_panel = new JPanel();
		modifyMember_panel.setName("modifyMember_panel");
		layeredPane.add(modifyMember_panel, "name_866762940457411");
		modifyMember_panel.setLayout(null);

		JPanel modMemberComponentPanel = new JPanel();
		modMemberComponentPanel.setMaximumSize(new Dimension(800, 600));
		modMemberComponentPanel.setPreferredSize(new Dimension(784, 540));
		modMemberComponentPanel.setBounds(0, 0, 784, 540);
		modifyMember_panel.add(modMemberComponentPanel);

		JLabel name_la_1 = new JLabel("Név");

		nameModify_tf = new JTextField();
		nameModify_tf.setMargin(new Insets(1, 2, 1, 2));
		nameModify_tf.setColumns(10);

		addressModify_tf = new JTextField();
		addressModify_tf.setColumns(10);

		JLabel address_la_1 = new JLabel("Lakc\u00EDm");

		JLabel contact_la_1 = new JLabel("El\u00E9rhet\u0151s\u00E9g");

		contactModify_tf = new JTextField();
		contactModify_tf.setMargin(new Insets(1, 2, 1, 2));
		contactModify_tf.setColumns(10);

		JComboBox<String> typeModify_cB_1 = new JComboBox<String>();
		type_cB.setModel(new DefaultComboBoxModel<String>(new String[] { "V\u00E1lasszon...", "Egyetemi hallgat\u00F3",
				"Egyetemi oktat\u00F3", "Másik egyetem polgára", "Egyéb" }));
		GroupLayout gl_memberComponentsPanel = new GroupLayout(memberComponentsPanel);
		gl_memberComponentsPanel.setHorizontalGroup(
			gl_memberComponentsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_memberComponentsPanel.createSequentialGroup()
					.addGap(258)
					.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_memberComponentsPanel.createSequentialGroup()
								.addComponent(name_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(name_tf, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_memberComponentsPanel.createSequentialGroup()
								.addComponent(address_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(address_tf, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_memberComponentsPanel.createSequentialGroup()
								.addComponent(contact_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(contact_tf, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_memberComponentsPanel.createSequentialGroup()
								.addComponent(cancelNewMember_bt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(addNewMember_bt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_memberComponentsPanel.createSequentialGroup()
							.addComponent(type_la, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(type_cB, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(242, Short.MAX_VALUE))
		);
		gl_memberComponentsPanel.setVerticalGroup(
			gl_memberComponentsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_memberComponentsPanel.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(name_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(name_la))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(address_la, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(address_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_memberComponentsPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(contact_la))
						.addComponent(contact_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_memberComponentsPanel.createSequentialGroup()
							.addGap(3)
							.addComponent(type_la))
						.addComponent(type_cB, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(150)
					.addGroup(gl_memberComponentsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelNewMember_bt)
						.addComponent(addNewMember_bt))
					.addContainerGap(184, Short.MAX_VALUE))
		);
		memberComponentsPanel.setLayout(gl_memberComponentsPanel);

		JLabel type_la_1 = new JLabel("Tags\u00E1g");

		JButton saveMemberModify_bt = new JButton("Ment\u00E9s");
		saveMemberModify_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saveMemberModify_bt.setIcon(saveIcon);

		JButton cancelMemberModify_bt = new JButton("M\u00E9gsem");
		cancelMemberModify_bt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cancelMemberModify_bt.setIcon(cancelIcon);
		GroupLayout gl_modMemberComponentPanel = new GroupLayout(modMemberComponentPanel);
		gl_modMemberComponentPanel.setHorizontalGroup(
			gl_modMemberComponentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modMemberComponentPanel.createSequentialGroup()
					.addGap(258)
					.addGroup(gl_modMemberComponentPanel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_modMemberComponentPanel.createSequentialGroup()
							.addComponent(cancelMemberModify_bt, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(saveMemberModify_bt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_modMemberComponentPanel.createSequentialGroup()
							.addComponent(address_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(addressModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_modMemberComponentPanel.createSequentialGroup()
							.addComponent(name_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(nameModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_modMemberComponentPanel.createSequentialGroup()
							.addComponent(type_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(typeModify_cB_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_modMemberComponentPanel.createSequentialGroup()
							.addComponent(contact_la_1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(contactModify_tf, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(257, Short.MAX_VALUE))
		);
		gl_modMemberComponentPanel.setVerticalGroup(
			gl_modMemberComponentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_modMemberComponentPanel.createSequentialGroup()
					.addGap(100)
					.addGroup(gl_modMemberComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(name_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modMemberComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(addressModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(address_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modMemberComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(contactModify_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(contact_la_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_modMemberComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(typeModify_cB_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(type_la_1))
					.addGap(150)
					.addGroup(gl_modMemberComponentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveMemberModify_bt)
						.addComponent(cancelMemberModify_bt)))
		);
		modMemberComponentPanel.setLayout(gl_modMemberComponentPanel);

		JPanel borrowListPanel = new JPanel();
		borrowListPanel.setName("borrowListPanel");
		layeredPane.add(borrowListPanel, "name_135175426107100");

		JScrollPane scrollPane_1 = new JScrollPane();

		borrowList = new JTable();
		borrowList.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Azonos\u00EDt\u00F3", "Tag azonos\u00EDt\u00F3", "K\u00F6lcs\u00F6nz\u0151 neve",
						"K\u00F6nyv c\u00EDme", "Lej\u00E1rat d\u00E1tuma" }));
		borrowList.getColumnModel().getColumn(0).setResizable(false);
		borrowList.getColumnModel().getColumn(0).setPreferredWidth(80);
		borrowList.getColumnModel().getColumn(0).setMinWidth(80);
		borrowList.getColumnModel().getColumn(0).setMaxWidth(80);
		borrowList.getColumnModel().getColumn(1).setPreferredWidth(90);
		borrowList.getColumnModel().getColumn(1).setMinWidth(90);
		borrowList.getColumnModel().getColumn(1).setMaxWidth(90);
		borrowList.getColumnModel().getColumn(2).setPreferredWidth(150);
		borrowList.getColumnModel().getColumn(3).setPreferredWidth(150);
		borrowList.getColumnModel().getColumn(4).setPreferredWidth(150);
		scrollPane_1.setViewportView(borrowList);

		JButton borrowListSearch_btn = new JButton("Keresés");
		borrowListSearch_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		borrowListSearch_btn.setIcon(searchIcon);

		JButton borrowListCancel_btn = new JButton("Vissza");
		borrowListCancel_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		borrowListCancel_btn.setIcon(backIcon);

		JButton borrowListBookBack_btn = new JButton("Visszavétel");
		borrowListBookBack_btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		borrowListBookBack_btn.setIcon(bookBackIcon);

		JComboBox<String> borrowListFilter_cb = new JComboBox<String>();
		borrowListFilter_cb.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Kereső kategória", "Azonosító", "Kölcsönző neve", "Könyv címe" }));

		borrowListSearch_tf = new JTextField();
		borrowListSearch_tf.setMargin(new Insets(1, 2, 1, 2));
		borrowListSearch_tf.setColumns(10);
		borrowListSearch_tf.setBackground(new Color(154, 205, 50));
		GroupLayout gl_borrowListPanel = new GroupLayout(borrowListPanel);
		gl_borrowListPanel.setHorizontalGroup(
			gl_borrowListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_borrowListPanel.createSequentialGroup()
					.addGap(40)
					.addGroup(gl_borrowListPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(borrowListFilter_cb, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(borrowListSearch_tf, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
					.addGap(140)
					.addGroup(gl_borrowListPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(borrowListCancel_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(borrowListBookBack_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(borrowListSearch_btn, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
					.addGap(200))
				.addGroup(gl_borrowListPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_borrowListPanel.setVerticalGroup(
			gl_borrowListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_borrowListPanel.createSequentialGroup()
					.addGap(31)
					.addComponent(borrowListSearch_btn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_borrowListPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(borrowListBookBack_btn)
						.addComponent(borrowListSearch_tf, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_borrowListPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_borrowListPanel.createSequentialGroup()
							.addGap(20)
							.addComponent(borrowListFilter_cb, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_borrowListPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(borrowListCancel_btn)))
					.addGap(54)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
					.addContainerGap())
		);
		borrowListPanel.setLayout(gl_borrowListPanel);

		// Táblázatok középre igazítása
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		bookList.setDefaultRenderer(String.class, centerRenderer);
		borrowBookList.setDefaultRenderer(String.class, centerRenderer);
		memberList.setDefaultRenderer(String.class, centerRenderer);
		backMemberList.setDefaultRenderer(String.class, centerRenderer);
//--------------------------------------------------------------------------------------------------------------
//new book panel
		/**
		 * It's an action listener which is going to be called if "Felvétel" button will
		 * be clicked on the newBook panel. It creates a Book class object and write the
		 * book's data to DB via writeBookToDB(Book book) function of the
		 * dataBaseModule.
		 */
		addNewBook_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Book bk = new core.Book();
				String error = "";
				int copy = 0, retVal;
				if (!(author_tf.getText()).trim().equals(""))
					bk.setAuthor((author_tf.getText()).trim());
				else
					error += "A szerző megadása kötelező!\r\n";

				if (!(title_tf.getText()).trim().equals(""))
					bk.setTitle((title_tf.getText()).trim());
				else
					error += "A könyv címének megadása kötelező!\r\n";

				if (!(publisher_tf.getText()).trim().equals(""))
					bk.setPublisher((publisher_tf.getText()).trim());
				else
					error += "A kiadó megadása kötelező!\r\n";

				if (!(ISBN_tf.getText()).trim().equals(""))
					bk.setISBN((ISBN_tf.getText()).trim());
				else
					error += "Az ISBN megadása kötelező!\r\n";

				if (!(date_cB.getSelectedItem().toString().equals("Válasszon...")))
					bk.setYear(Integer.parseInt(date_cB.getSelectedItem().toString()));
				else
					error += "A kiadási év megadása kötelező!\r\n";

				if (Integer.parseInt(edition_spinner.getValue().toString()) != 0)
					bk.setEdition(Integer.parseInt(edition_spinner.getValue().toString()));
				else
					error += "A kiadás számának megadása kötelező!\r\n";

				if (Integer.parseInt(copy_spinner.getValue().toString()) != 0)
					copy = (Integer.parseInt(copy_spinner.getValue().toString()));
				else
					error += "A példányszám megadása kötelező!\r\n";

				if (yes_rbt.isSelected()) {
					bk.setBorrowStatus(true);
				} else if (yes_rbt.isSelected()) {
					bk.setBorrowStatus(false);
				} else {
					error += "A kölcsönözhetőség kiválasztása kötelező!";
				}

				if (error.equals("")) {
					// beírjuk a könyvet az adatbázisba
					retVal = dbm.writeBookToDB(bk, copy);
					if (retVal == copy) {
						author_tf.setText("");
						title_tf.setText("");
						publisher_tf.setText("");
						ISBN_tf.setText("");
						bg.clearSelection();
						date_cB.setSelectedItem("Válasszon...");
						copy_spinner.setValue(0);
						edition_spinner.setValue(0);
						JOptionPane.showMessageDialog(null, "A könyv(ek) hozáadása sikeresen megtörtént!");
						changePanels(welcome_panel);
					} else {
						JOptionPane.showMessageDialog(null, "A könyv(ek) hozáadása sikertelen!");
					}

				} else {
					infoText.setText("");
					infoText.setText(error);
					JOptionPane.showMessageDialog(null, error);
				}
			}

		});
		/**
		 * Action listener, which aborts the creation of a new book object if the
		 * panel's "Vissza" button is clicked.
		 */
		// mégsem gomb
		cancelNewBook_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				author_tf.setText("");
				title_tf.setText("");
				publisher_tf.setText("");
				ISBN_tf.setText("");
				bg.clearSelection();
				date_cB.setSelectedItem("Váasszon...");
				copy_spinner.setValue(0);
				edition_spinner.setValue(0);
				changePanels(welcome_panel);
			}
		});

// -------------------------------------------------------------------------------------------------------------
//New Member Panel
		// Felvétel button
		
		addNewMember_bt.addActionListener(new ActionListener() {
			/**
			 * It's an action listener which is going to be called if "Felvétel" button will
			 * be clicked on the newMemer panel. It creates a Member class object and write
			 * the members's data to DB via addMemberToDB(Member member) function of the
			 * dataBaseModule.
			 */
			public void actionPerformed(ActionEvent arg0) {
				core.Member member = null;
				String error2 = "";

				if (!(type_cB.getSelectedItem().equals("Válasszon..."))) {
					if (type_cB.getSelectedItem().equals("Egyetemi hallgató")) {
						member = new core.Student();
					} else if (type_cB.getSelectedItem().equals("Egyetemi oktató")) {
						member = new core.Teacher();
					} else if (type_cB.getSelectedItem().equals("Másik egyetem polgára")) {
						member = new core.Outsider();
					} else { // ha a masok van kijelolve
						member = new core.Others();
					}

					member.setMemberType((type_cB.getSelectedItem()).toString());
					if (!(name_tf.getText().trim().equals(""))) {
						member.setName((name_tf.getText()).trim());
					} else {
						error2 += "A név megadása kötelező!\r\n";
					}
					if (!(address_tf.getText().trim().equals(""))) {
						member.setAddress((address_tf.getText()).trim());
					} else {
						error2 += "A lakcím megadása kötelező!\r\n";
					}
					if (!(contact_tf.getText().trim().equals(""))) {
						member.setContact((contact_tf.getText()).trim());
					} else {
						error2 += "Az elérhetőség megadása kötelező!\r\n";
					}

				} else {
					if (name_tf.getText().trim().equals("")) {
						error2 += "A név megadása kötelező!\r\n";
					}
					if (address_tf.getText().trim().equals("")) {
						error2 += "A lakcím megadása kötelező!\r\n";
					}
					if (contact_tf.getText().trim().equals("")) {
						error2 += "Az elérhetőség megadása kötelező!\r\n";
					}
					error2 += "Tag típus megadása kötelező!\r\n";

				}

				if (error2.equals("")) {
					// beírjuk a tagot az adatbázisba
					int retVal = dbm.addMemberToDB(member);
					if (retVal == 0) {
						JOptionPane.showMessageDialog(null, "Új tag felvétele sikeresen megtörtént!");
						name_tf.setText("");
						address_tf.setText("");
						contact_tf.setText("");
						type_cB.setSelectedItem("Válasszon...");
						changePanels(welcome_panel);
					} else {
						JOptionPane.showMessageDialog(null, "Új tag felvétele sikertelen!");
					}

				} else {
					memberInfoText.setText("");
					memberInfoText.setText(error2);
					JOptionPane.showMessageDialog(null, error2);
				}

			}
		});
		
		// Mégsem button
		cancelNewMember_bt.addActionListener(new ActionListener() {
			/**
			 * Action listener, which aborts the creation of a new book object if the
			 * panel's "Vissza" button is clicked.
			 */
			public void actionPerformed(ActionEvent e) {
				name_tf.setText("");
				address_tf.setText("");
				contact_tf.setText("");
				type_cB.setSelectedItem("Válasszon...");
				changePanels(welcome_panel);
			}
		});
// -------------------------------------------------------------------------------------------------------------
//SearchBook panel
		
		searchBook_menuItem.addActionListener(new ActionListener() {
			/**
			 * Action listenet which is going to be called if "könyvek" menu's "Listázás és
			 * keresés" item will be clicked. Creates a list of the books.
			 */
			public void actionPerformed(ActionEvent arg0) {
				// könyvek listázása és keresése
				dbm.clearTable(bookListModel);
				searchBook_comboBox.setSelectedItem("Kereső kategória");
				textBook_tf.setText("");
				changePanels(searchBook_panel);
				int retVal = dbm.listOfBooks(bookListModel);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
			}
		});
		
		// backBook_btn button (Vissza)
		backBook_btn.addActionListener(new ActionListener() {
			/**
			 * Action listener, which is going to be called if the panel's "Vissza" button
			 * is clicked.
			 */
			public void actionPerformed(ActionEvent e) {
				changePanels(welcome_panel);
				dbm.clearTable(bookListModel);
				searchBook_comboBox.setSelectedItem("Kereső kategória");
				textBook_tf.setText("");
			}
		});
		// keresés
		
		searchBook_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(bookListModel);
				int retVal = dbm.searchBook(searchBook_comboBox.getSelectedItem().toString(),
						textBook_tf.getText().trim(), bookListModel);

				if (retVal != 0)
					switch (retVal) {
					case -1: {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
						break;
					}
					case -2: {
						retVal = dbm.listOfBooks(bookListModel);
						textBook_tf.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
						break;
					}
					case -3: {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						textBook_tf.setText("");
						break;
					}
					case -4: {
						JOptionPane.showMessageDialog(null, "Nincs ilyen könyv az adatbázisban!");
						textBook_tf.setText("");
						break;
					}

					default:
						break;
					}
			}
		});
		// törlés
		deleteBook_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Book bookToRemove;
				int retVal = 0, ret2 = 0;
				if (bookList.getSelectedRow() != -1) {
					bookToRemove = dbm.selectedBook(bookList, 0);
					if (bookToRemove != null) {
						boolean removable = true;
						if (!bookToRemove.getBorrowStatus()) {
							retVal = dbm.countBorrows(bookToRemove);
							if (retVal > 0)
								removable = false;
						}
						if (removable) {
							Object[] options = { "Összes törlése", "Kijelölt törlése" };
							ret2 = JOptionPane.showOptionDialog(null,
									"Az összes nem kiadott példányt törli vagy csak a kijelöltet?", "Könyv törlése",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							Object[] options2 = { "Igen", "Nem" };
							retVal = JOptionPane.showOptionDialog(null, "Biztosan törli a könyve(ke)t?",
									"Könyv törlése", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									options2, options2[0]);
							if (retVal == JOptionPane.YES_OPTION) {

								retVal = dbm.removeBook(bookToRemove, ret2);
								if (retVal != 0)
									JOptionPane.showMessageDialog(null, "A könyv törlése sikertelen!");
								bookList.clearSelection();
								dbm.clearTable(bookListModel);
								retVal = dbm.listOfBooks(bookListModel);
								if (retVal != 0)
									JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
							}
						} else
							JOptionPane.showMessageDialog(null, "A könyv nem törölhető, jelenleg ki van kölcsönözve!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki a törölni kívánt könyvet!");
				}

			}
		});
// -------------------------------------------------------------------------------------------------------------
//SearchMember panel
		searchMember_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(memberListModel);
				searchMember_cb.setSelectedItem("Kereső kategória");
				textMember_tf.setText("");
				changePanels(searchMember_panel);
				int retVal = dbm.listOfMembers(memberListModel);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");

			}
		});
		// vissza gomb
		backMember_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanels(welcome_panel);
				dbm.clearTable(memberListModel);
				searchMember_cb.setSelectedItem("Kereső kategória");
				textMember_tf.setText("");
			}
		});
		// Keresés
		searchMember_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(memberListModel);
				int retVal = dbm.searchMember(searchMember_cb.getSelectedItem().toString(),
						textMember_tf.getText().trim(), memberListModel);

				if (retVal != 0)
					switch (retVal) {
					case -1: {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
						break;
					}
					case -2: {
						retVal = dbm.listOfMembers(memberListModel);
						textMember_tf.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
						break;
					}
					case -3: {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						textMember_tf.setText("");
						break;
					}
					case -4: {
						JOptionPane.showMessageDialog(null, "Nincs ilyen tag az adatbázisban!");
						textMember_tf.setText("");
						break;
					}

					default:
						break;
					}
			}
		});
		// törlés
		deleteMember_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Member memberToRemove;
				int retVal = 0;
				if (memberList.getSelectedRow() != -1) {
					memberToRemove = dbm.selectedMember(memberList);
					if (memberToRemove != null) {
						boolean removable = true;
						retVal = dbm.countBook(memberToRemove);
						if (retVal > 0)
							removable = false;

						if (removable) {
							Object[] options = { "Igen", "Nem" };
							retVal = JOptionPane.showOptionDialog(null, "Biztosan törli a kijelölt tagot?",
									"Tag törlése", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
									options, options[0]);
							if (retVal == JOptionPane.YES_OPTION) {
								retVal = dbm.removeMember(String.valueOf(memberToRemove.getId()));
								if (retVal != 0)
									JOptionPane.showMessageDialog(null, "A tag törlése sikertelen!");
								memberList.clearSelection();
								dbm.clearTable(memberListModel);
								retVal = dbm.listOfMembers(memberListModel);
								if (retVal != 0)
									JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
							}
						} else
							JOptionPane.showMessageDialog(null, "A tag nem törölhető, jelenleg van aktív kölcsönzése!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki a törölni kívánt tagot!");
				}
			}
		});
		// kölcsönzések gomb
		borBooks_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Member memberSborrowings;
				if (memberList.getSelectedRow() != -1) {
					memberSborrowings = dbm.selectedMember(memberList);
					if (memberSborrowings != null) {
						StatusBooks sb = new StatusBooks(memberSborrowings);
						sb.setVisible(true);
						sb.setLocationRelativeTo(null);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Jelölje ki a tagot, akinek meg szeretné jeleníteni a kölcsonzéseit!");
				}

			}
		});

// -------------------------------------------------------------------------------------------------------------
//Könyv kiadás panel
		bookGive_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// könyv kiadása
				dbm.clearTable(bookListModel);
				comboBox_1.setSelectedItem("Kereső kategória");
				textField3.setText("");
				changePanels(bookOut_panel);
				int retVal = dbm.listOfBooks(bookListModel);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
			}
		});
		// Vissza gomb
		newBorrowBack_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanels(welcome_panel);
				dbm.clearTable(bookListModel);
				comboBox_1.setSelectedItem("Kereső kategória");
				textField3.setText("");
			}
		});
		// Keresés
		search_bt_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(bookListModel);
				int retVal = dbm.searchBook(comboBox_1.getSelectedItem().toString(), textField3.getText().trim(),
						bookListModel);
				if (retVal != 0)
					switch (retVal) {
					case -1: {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
						break;
					}
					case -2: {
						retVal = dbm.listOfBooks(bookListModel);
						textBook_tf.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
						break;
					}
					case -3: {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						textBook_tf.setText("");
						break;
					}
					case -4: {
						JOptionPane.showMessageDialog(null, "Nincs ilyen könyv az adatbázisban!");
						textBook_tf.setText("");
						break;
					}

					default:
						break;
					}
			}
		});
		// Kiadás
		bookGive_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Book bookToBorrow;
				if (borrowBookList.getSelectedRow() != -1) {
					bookToBorrow = dbm.selectedBook(borrowBookList, 0);
					if (bookToBorrow != null) {
						if (bookToBorrow.getBorrowStatus()) {
							Out outWindow = new Out(bookToBorrow, memberListModel);
							outWindow.setLocationRelativeTo(null);
							outWindow.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null,
									"A kijelölt könyv, már ki van adva vagy nem kölcsönözhető!");
						}

					}
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki a kiadni kívánt könyvet!");
				}

			}
		});
		// Aktív kölcsönzések gomb - Aktív kölcsönzések panelre linkel
		bookOutActiveBorr_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable((DefaultTableModel) borrowList.getModel());
				borrowListFilter_cb.setSelectedItem("Kereső kategória");
				borrowListSearch_tf.setText("");
				changePanels(borrowListPanel);
				int retVal = dbm.listOfActiveBorrowings((DefaultTableModel) borrowList.getModel(), null);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");

			}
		});

//----------------------------------------------------------------------------------------------------------------------
//könyv vissza
		bookBack_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// könyv visszavétele
				dbm.clearTable(memberListModel);
				comboBox.setSelectedItem("Kereső kategória");
				textField_2.setText("");
				changePanels(bookBack_panel);
				int retVal = dbm.listOfMembers(memberListModel);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
			}
		});
		// vissza gomb
		backCancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanels(welcome_panel);
				dbm.clearTable(memberListModel);
				comboBox.setSelectedItem("Kereső kategória");
				textField_2.setText("");
			}
		});
		// keresés
		search_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(memberListModel);
				int retVal = dbm.searchMember(comboBox.getSelectedItem().toString(), textField_2.getText().trim(),
						memberListModel);
				if (retVal != 0)
					switch (retVal) {
					case -1: {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
						break;
					}
					case -2: {
						retVal = dbm.listOfMembers(memberListModel);
						textMember_tf.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
						break;
					}
					case -3: {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						textMember_tf.setText("");
						break;
					}
					case -4: {
						JOptionPane.showMessageDialog(null, "Nincs ilyen tag az adatbázisban!");
						textMember_tf.setText("");
						break;
					}

					default:
						break;
					}
			}
		});
		// kiválaszt
		choose_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				core.Member backMember;
				if (backMemberList.getSelectedRow() != -1) {
					backMember = dbm.selectedMember(backMemberList);
					if (backMember != null) {
						// Van-e aktív kölcsönzése, ha van jelenítsük meg a visszavétel ablakot
						int retVal = dbm.countBook(backMember);
						if (retVal > 0) {
							BookBackWindow bbw = new BookBackWindow(backMember);
							bbw.setLocationRelativeTo(null);
							bbw.setVisible(true);
						} else
							JOptionPane.showMessageDialog(null, "A kiválaszott tagnak nincs aktív kölcsönzése!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki azt a tagot, aki könyvet hoz vissza!");
				}
			}
		});
		//aktívkölcsönzések
		bookBackActiveBorr_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable(memberListModel);
				comboBox.setSelectedItem("Kereső kategória");
				borrowListSearch_tf.setText("");
				changePanels(borrowListPanel);
				int retVal = dbm.listOfActiveBorrowings((DefaultTableModel) borrowList.getModel(), null);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
			}
		});
//----------------------------------------------------------------------------------------------------------------------
//Aktív kölcsönzések
		borrowList_menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable((DefaultTableModel) borrowList.getModel());
				borrowListFilter_cb.setSelectedItem("Kereső kategória");
				borrowListSearch_tf.setText("");
				changePanels(borrowListPanel);
				int retVal = dbm.listOfActiveBorrowings((DefaultTableModel) borrowList.getModel(), null);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");

			}
		});
		// Visszavétel gomb - könyv visszavétel panelre linkel
		borrowListBookBack_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// könyv visszavétele
				dbm.clearTable(memberListModel);
				comboBox.setSelectedItem("Kereső kategória");
				textField_2.setText("");
				changePanels(bookBack_panel);
				int retVal = dbm.listOfMembers(memberListModel);
				if (retVal != 0)
					JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
			}
		});
		// Keresés
		borrowListSearch_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable((DefaultTableModel) borrowList.getModel());
				int retVal = dbm.searchBorrowing(borrowListFilter_cb.getSelectedItem().toString(),
						borrowListSearch_tf.getText().trim(), (DefaultTableModel) borrowList.getModel(), null, 0);
				if (retVal != 0)
					switch (retVal) {
					case -1: {
						JOptionPane.showMessageDialog(null, "A keresés sikertelen!");
						break;
					}
					case -2: {
						dbm.clearTable((DefaultTableModel) borrowList.getModel());
						retVal = dbm.listOfActiveBorrowings((DefaultTableModel) borrowList.getModel(), null);
						borrowListSearch_tf.setText("");
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
						break;
					}
					case -3: {
						JOptionPane.showMessageDialog(null, "Az azonosító szerinti kereséshez számot adjon meg!");
						borrowListSearch_tf.setText("");
						break;
					}
					case -4: {
						JOptionPane.showMessageDialog(null, "Nincs ilyen kölcsönzés az adatbázisban!");
						borrowListSearch_tf.setText("");
						break;
					}

					default:
						break;
					}
			}
		});
		// Vissza gomb
		borrowListCancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbm.clearTable((DefaultTableModel) borrowList.getModel());
				borrowListFilter_cb.setSelectedItem("Kereső kategória");
				borrowListSearch_tf.setText("");
				changePanels(welcome_panel);
			}
		});

//----------------------------------------------------------------------------------------------------------------------
//Lista frissítések - Ablak műveletek
		this.addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				Component c[] = layeredPane.getComponents();
				if (c.length == 1) {
					if ((c[0].getName()).equals("bookOut_panel")) {
						// bookListModel.clear();
						dbm.clearTable(bookListModel);
						int retVal = dbm.listOfBooks(bookListModel);
						if (retVal != 0)
							JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
					}
				}
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
			}
		});
// --------------------------------------------------------------------------------------------------------
// Tag módosítás panel
		editMember_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (memberList.getSelectedRow() != -1) {
					currentMember = dbm.selectedMember(memberList);
					nameModify_tf.setText(currentMember.getName());
					addressModify_tf.setText(currentMember.getAddress());
					contactModify_tf.setText(currentMember.getContact());
					typeModify_cB_1.removeAll();
					typeModify_cB_1.addItem(types[0]);
					typeModify_cB_1.addItem(types[1]);
					typeModify_cB_1.addItem(types[2]);
					typeModify_cB_1.addItem(types[3]);
					typeModify_cB_1.setSelectedItem(currentMember.getMemberType());
					changePanels(modifyMember_panel);
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki azt a tagot, akit módosítani kíván!");
				}

			}
		});

		saveMemberModify_bt.addActionListener(ae -> {
			if (!nameModify_tf.getText().equals(currentMember.getName())
					|| !addressModify_tf.getText().equals(currentMember.getAddress())
					|| !contactModify_tf.getText().equals(currentMember.getContact())
					|| !typeModify_cB_1.getSelectedItem().equals(currentMember.getMemberType())) {
				dbm.modifyMember(currentMember.getId(), "name", nameModify_tf.getText());
				dbm.modifyMember(currentMember.getId(), "address", addressModify_tf.getText());
				dbm.modifyMember(currentMember.getId(), "contact", contactModify_tf.getText());
				dbm.modifyMember(currentMember.getId(), "type", typeModify_cB_1.getSelectedItem().toString());
			}
			dbm.clearTable(memberListModel);
			searchMember_cb.setSelectedItem("Kereső kategória");
			textMember_tf.setText("");
			nameModify_tf.setText("");
			addressModify_tf.setText("");
			contactModify_tf.setText("");
			changePanels(searchMember_panel);
			int retVal = dbm.listOfMembers(memberListModel);
			if (retVal != 0)
				JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");

		});

		cancelMemberModify_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nameModify_tf.setText("");
				addressModify_tf.setText("");
				contactModify_tf.setText("");
				changePanels(searchMember_panel);
			}
		});

// --------------------------------------------------------------------------------------------------------
// Könyv módosítás panel	
		editBook_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (bookList.getSelectedRow() != -1) {
					currentBook = dbm.selectedBook(bookList, 0);
					authorModify_tf.setText(currentBook.getAuthor());
					titleModify_tf.setText(currentBook.getTitle());
					publisherModify_tf.setText(currentBook.getPublisher());
					isbnModify_tf.setText(currentBook.getISBN());
					dateModify_cB.removeAllItems();
					LocalDateTime myDateObj = LocalDateTime.now();
					DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy");
					int yearDate = Integer.parseInt(myDateObj.format(myFormatObj));
					for (int i = yearDate; i >= 1940; i--) {
						dateModify_cB.addItem(String.valueOf(i));
					}
					dateModify_cB.setSelectedItem(currentBook.getYear() + "");
					copyModify_spinner.setValue(0);
					editionModify_spinner.setValue(currentBook.getEdition());

					if (currentBook.getBorrowStatus() == true) {
						yesModify_rbt.setSelected(true);
						noModify_rbt.setSelected(false);
					} else {
						yesModify_rbt.setSelected(false);
						noModify_rbt.setSelected(true);
					}

					changePanels(modifyBook_panel);
				} else {
					JOptionPane.showMessageDialog(null, "Jelölje ki azt a könyvet, amit módosítani kíván!");
				}
			}
		});

		saveBookModify_bt.addActionListener(ae -> {
			if (!authorModify_tf.getText().equals(currentBook.getAuthor())
					|| !titleModify_tf.getText().equals(currentBook.getTitle())
					|| !publisherModify_tf.getText().equals(currentBook.getPublisher())
					|| !isbnModify_tf.getText().equals(currentBook.getISBN())
					|| !dateModify_cB.getSelectedItem().equals(currentBook.getYear())
					|| Integer.parseInt(editionModify_spinner.getValue().toString()) != currentBook.getEdition()
					|| (!(yesModify_rbt.isSelected() && currentBook.getBorrowStatus())
							|| !(noModify_rbt.isSelected() && !currentBook.getBorrowStatus()))) {
				dbm.modifyBook(currentBook.getId(), "author", authorModify_tf.getText());
				dbm.modifyBook(currentBook.getId(), "title", titleModify_tf.getText());
				dbm.modifyBook(currentBook.getId(), "publisher", publisherModify_tf.getText());
				dbm.modifyBook(currentBook.getId(), "isbn", isbnModify_tf.getText());
				dbm.modifyBook(currentBook.getId(), "year", dateModify_cB.getSelectedItem().toString());
				dbm.modifyBook(currentBook.getId(), "edition", editionModify_spinner.getValue().toString());

				if (yesModify_rbt.isSelected())
					dbm.modifyBook(currentBook.getId(), "borrow", "1");
				else
					dbm.modifyBook(currentBook.getId(), "borrow", "0");
			}
			dbm.clearTable(bookListModel);
			searchBook_comboBox.setSelectedItem("Kereső kategória");
			textBook_tf.setText("");
			authorModify_tf.setText("");
			titleModify_tf.setText("");
			publisherModify_tf.setText("");
			isbnModify_tf.setText("");
			dateModify_cB.setSelectedItem("Válasszon...");
			copyModify_spinner.setValue(0);
			editionModify_spinner.setValue(0);
			changePanels(searchBook_panel);
			int retVal = dbm.listOfBooks(bookListModel);
			if (retVal != 0)
				JOptionPane.showMessageDialog(null, "Az adatbázis megjelenítése sikertelen!");
		});

		cancelBookModify_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				authorModify_tf.setText("");
				titleModify_tf.setText("");
				publisherModify_tf.setText("");
				isbnModify_tf.setText("");
				dateModify_cB.setSelectedItem("Válasszon...");
				copyModify_spinner.setValue(0);
				editionModify_spinner.setValue(0);
				changePanels(searchBook_panel);
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
