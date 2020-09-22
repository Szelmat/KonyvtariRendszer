package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import core.*;

public class DataBaseModule {
	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Sign in a librarian
	 * 
	 * @param usrname  of the librarian
	 * @param password of the librarian
	 * @return true - Everything is OK, false - Wrong username or password.
	 */
	public boolean authenticate(String user, String pass) throws SQLException {
		String url = "jdbc:sqlite:library.db";
		Statement stmt = null;
		String query = "SELECT * FROM librarians";
		String finalUser = "";
		String finalPass = "";
		pass = get_SHA_512_SecurePassword(pass);

		if (user.equals("") || pass.equals(""))
			return false;

		try (Connection con = DriverManager.getConnection(url)) {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String currentUser = rs.getString(1);
				String currentPass = rs.getString(2);

				// Megkeressük a megfelelõ felhasználót
				if (currentUser.toLowerCase().equals(user.toLowerCase())) {
					finalUser = currentUser;
					finalPass = currentPass;
					break;
				}
			}

			if (finalUser.equals(""))
				return false;
			else if (finalPass.toLowerCase().equals(pass.toLowerCase())) // A hash-et nyugodtan átalakíthatjuk
																			// lowercase-re
				return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		return false;
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Encrypt a string
	 * 
	 * @param the String we want to hash
	 * @return the result of the hashing
	 */
	private String get_SHA_512_SecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Delete all rows from a JTable
	 * 
	 * @param table Table to be deleted.
	 */
	public void clearTable(DefaultTableModel table) {
		for (int i = table.getRowCount() - 1; i >= 0; i--) {
			table.removeRow(i);
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Adds one or more pieces of new book(s) to database.
	 * 
	 * @param book  Book object which will be added to DB.
	 * @param piece Number of books to add.
	 * @return number of added books (counter) - Everything is OK. -1 - Couldn't
	 *         write to DB.
	 */
	public int writeBookToDB(Book book, int piece) {
		String url = "jdbc:sqlite:library.db";
		int counter = 0;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "INSERT INTO books(author,title,publisher,year,edition,isbn,borrow) VALUES(?,?,?,?,?,?,?)";
			while (counter < piece) {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, book.getAuthor());
				ps.setString(2, book.getTitle());
				ps.setString(3, book.getPublisher());
				ps.setInt(4, book.getYear());
				ps.setInt(5, book.getEdition());
				ps.setString(6, book.getISBN());
				if (book.getBorrowStatus())
					ps.setInt(7, 1);
				else
					ps.setInt(7, 0);

				int nez = ps.executeUpdate();
				if (nez > 0)
					counter++;

				else {
					return -1;

				}
			}
			return counter;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Adds a new member to the DataBase
	 * 
	 * @param mem Member or its descendant object which will be added to DB.
	 * @return 0 - Everything is OK, -1 - Couldn't write to DB.
	 */
	public int addMemberToDB(Member mem) {
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "INSERT INTO members(name,address,type,contact) VALUES(?,?,?,?)";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, mem.getName());
			ps.setString(2, mem.getAddress());
			ps.setString(3, mem.getMemberType());
			ps.setString(4, mem.getContact());
			int nez = ps.executeUpdate();
			if (nez <= 0)
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Adds a new borrow to DB.
	 * 
	 * @param borrow Borrow object which will be added to DB.
	 * @return 0 - Everything is OK, -1 - Couldn't write borrow to DB.
	 */
	public int addBorrowtoDB(Borrow borrow) {
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "INSERT INTO borrows(member_id,book_id,date,return,book_title,book_author,book_publisher,book_year,book_edition,book_isbn,book_borrowStatus) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, borrow.getMemberId());
			ps.setInt(2, borrow.getBookId());
			ps.setString(3, borrow.getDate());
			ps.setInt(4, borrow.getBroughtBackStatus());
			ps.setString(5, borrow.getBookTitle());
			ps.setString(6, borrow.getBookAuthor());
			ps.setString(7, borrow.getBookPublisher());
			ps.setInt(8, borrow.getBookYear());
			ps.setInt(9, borrow.getBookEdition());
			ps.setString(10, borrow.getBookISBN());
			ps.setInt(11, borrow.getBookBorrowStatus());
			int nez = ps.executeUpdate();
			if (nez <= 0)
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Returns a book object with the data of the selected book in the list
	 * 
	 * @param list     Graphical list object which includes the selected book (row).
	 * @param borrowed 1 if look for borrowed book's ID otherwise 0.
	 * @return book object filled with data - Everything is OK, null - Couldn't
	 *         select the book from DB.
	 */
	public Book selectedBook(JTable list, int borrowed) {
		Book book = new Book();
		Statement stm;
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String selectedItemID;
			int selectedRow = list.getSelectedRow();
			if (borrowed == 1) {
				selectedItemID = ((DefaultTableModel) list.getModel()).getValueAt(selectedRow, 1).toString();
			} else {
				selectedItemID = ((DefaultTableModel) list.getModel()).getValueAt(selectedRow, 0).toString();
			}
			String sql = "SELECT * FROM books WHERE id=" + selectedItemID + " ORDER BY year DESC";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			if (rSet.next()) {
				book.setId(rSet.getInt(1));
				book.setAuthor(rSet.getString(2));
				book.setTitle(rSet.getString(3));
				book.setPublisher(rSet.getString(4));
				book.setYear(rSet.getInt(5));
				book.setEdition(rSet.getInt(6));
				book.setISBN(rSet.getString(7));
				if (rSet.getInt(8) == 1)
					book.setBorrowStatus(true);
				else
					book.setBorrowStatus(false);
				return book;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Returns a member or its descendant object with the data of the selected
	 * member in the list.
	 * 
	 * @param list Graphical list object which includes the selected member (row).
	 * @return Member object filled with data - Everything is OK, null - Couldn't
	 *         select the member from DB.
	 */
	public Member selectedMember(JTable list) {
		Member member;
		Statement stm;
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			int selectedRow = list.getSelectedRow();
			String selectedItemID = ((DefaultTableModel) list.getModel()).getValueAt(selectedRow, 0).toString();
			String sql = "SELECT * FROM members WHERE id=" + selectedItemID + " ORDER BY name";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			if (rSet.next()) {
				switch (rSet.getString(4)) {
				case "Egyetemi oktató": {
					member = new Teacher();
					break;
				}
				case "Egyetemi hallgató": {
					member = new Student();
					break;
				}
				case "Másik egyetem polgára": {
					member = new Outsider();
					break;
				}
				case "Egyéb": {
					member = new Others();
					break;
				}
				default:
					member = new Member();
				}
				member.setId(rSet.getInt(1));
				member.setName(rSet.getString(2));
				member.setAddress(rSet.getString(3));
				member.setMemberType(rSet.getString(4));
				member.setContact(rSet.getString(5));
				return member;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	/**
	 * Select a borrow from the DB if borrow's member_id and book_id is matching
	 * with an id from books and members table
	 * 
	 * @param member Member object who's borrowing will be selected.
	 * @param book   Book object which will be selected.
	 * @return Matched Borrow object with data - Everything is OK, null - Cuoldn't
	 *         select the borrow.
	 */
	public Borrow getBorrow(Member member, Book book) {
		String url = "jdbc:sqlite:library.db";
		Borrow borrow = new Borrow();
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT id,member_id,book_id,date,return FROM borrows WHERE member_id="
					+ String.valueOf(member.getId()) + " AND book_id=" + String.valueOf(book.getId())
					+ " AND return = 0";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			if (rSet.next()) {
				borrow.setId(rSet.getInt(1));
				borrow.setMemberId(rSet.getInt(2));
				borrow.setBookId(rSet.getInt(3));
				borrow.setDate(rSet.getString(4));
				borrow.setBroughtBackStatus(rSet.getInt(5));
				return borrow;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Create a list of books in the DB
	 * 
	 * @param list Graphical list component which it adds books to.
	 * @return 0 - Everything is OK, -1 - Couldn't create list.
	 */
	public int listOfBooks(DefaultTableModel list) {
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT * FROM books ORDER BY year DESC";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			while (rSet.next()) {
				Object obj[] = new Object[8];
				obj[0] = String.valueOf(rSet.getInt(1));
				obj[1] = rSet.getString(3);
				obj[2] = rSet.getString(2);
				obj[3] = rSet.getString(4);
				obj[4] = String.valueOf(rSet.getInt(5));
				obj[5] = String.valueOf(rSet.getInt(6));
				obj[6] = rSet.getString(7);
				if (rSet.getInt(8) == 1)
					obj[7] = "Kölcsönözhetõ";
				else
					obj[7] = "NEM KÖLCSÖNÖZHETÕ";
				list.addRow(obj);
			}
			return 0;
		} catch (SQLException e) {
			return -1;
		}

	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Create a list of members in the DB
	 * 
	 * @param list Graphical list component which it adds members to.
	 * @return 0 - Everything is OK, -1 - Couldn't create list.
	 */
	public int listOfMembers(DefaultTableModel list) {
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT * FROM members ORDER BY name";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			while (rSet.next()) {
				Object obj[] = new Object[5];
				obj[0] = String.valueOf(rSet.getInt(1));
				obj[1] = rSet.getString(2);
				obj[2] = rSet.getString(3);
				obj[3] = rSet.getString(4);
				obj[4] = rSet.getString(5);
				list.addRow(obj);
			}
			return 0;
		} catch (SQLException e) {
			return -1;
		}

	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Creates a list of active borrowings of a member.
	 * 
	 * @param list   Graphical list component which it adds borrowings to.
	 * @param member Member object, current member who's current borrowings will be
	 *               listed.
	 * @return 0 - Everything is OK, -1 - Couldn't create list.
	 */
	public int listOfActiveBorrowings(DefaultTableModel list, Member member) {
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			if (member != null) {
				String sql = "SELECT borrows.id,books.id,title,author,date FROM books,borrows WHERE books.id = borrows.book_id AND borrows.member_id = "
						+ String.valueOf(member.getId()) + " AND borrows.return = 0 ORDER BY borrows.id";
				stm = con.createStatement();
				ResultSet rSet = stm.executeQuery(sql);
				while (rSet.next()) {
					Object obj[] = new Object[5];
					obj[0] = String.valueOf(rSet.getInt(1));
					obj[1] = String.valueOf(rSet.getInt(2));
					obj[2] = rSet.getString(3);
					obj[3] = rSet.getString(4);
					obj[4] = rSet.getString(5);
					list.addRow(obj);
				}
				return 0;
			} else {
				String sql = "SELECT borrows.id,members.id,name,title,date FROM books,borrows,members WHERE books.id = borrows.book_id AND borrows.member_id = members.id"
						+ " AND borrows.return = 0 ORDER BY name";
				stm = con.createStatement();
				ResultSet rSet = stm.executeQuery(sql);
				while (rSet.next()) {
					Object obj[] = new Object[5];
					obj[0] = String.valueOf(rSet.getInt(1));
					obj[1] = String.valueOf(rSet.getInt(2));
					obj[2] = rSet.getString(3);
					obj[3] = rSet.getString(4);
					obj[4] = rSet.getString(5);
					list.addRow(obj);
				}
				return 0;
			}

		} catch (SQLException e) {
			return -1;
		}

	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Creates a list of active borrowings of a member.
	 * 
	 * @param list   Graphical list component which it adds borrowings to.
	 * @param member Member object, current member who's old borrowings will be
	 *               listed.
	 * @return 0 - Everything is OK, -1 - Couldn't create list.
	 */
	public int listOfOldBorrowings(DefaultTableModel list, Member member) {
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT borrows.id,book_id,book_title,book_author,date,backDate,delayed FROM borrows WHERE borrows.member_id = "
					+ String.valueOf(member.getId()) + " AND borrows.return = 1 ORDER BY borrows.id";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			while (rSet.next()) {

				Object obj[] = new Object[7];
				obj[0] = String.valueOf(rSet.getInt(1));
				obj[1] = String.valueOf(rSet.getInt(2));
				obj[2] = rSet.getString(3);
				obj[3] = rSet.getString(4);
				obj[4] = rSet.getString(5);
				obj[5] = rSet.getString(6);

				if (rSet.getInt(7) != 0)
					obj[6] = "IGEN";
				else
					obj[6] = "";
				list.addRow(obj);
			}
			return 0;
		} catch (SQLException e) {
			return -1;
		}

	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Creates a list of books that match the search. If the filter is ID, then it
	 * has to be a full match, otherwise it's enough if the attribute contains the
	 * expression.
	 * 
	 * @param filter Search attribute (category)
	 * @param search Searching expression
	 * @param list   Graphical list object which contains the members
	 * @return 0 - Everything is OK. -1 - DataBase fault, couldn't match, -2 -
	 *         Filter wasn't selected before search, -3 - Filter was ID but the
	 *         search expression wasn't a number.
	 */
	public int searchBook(String filter, String search, DefaultTableModel list) {
		Statement stm;
		String url = "jdbc:sqlite:library.db";
		switch (filter) {
		case "Azonosító":
			filter = "id";
			break;
		case "Szerzõ":
			filter = "author";
			break;
		case "Cím":
			filter = "title";
			break;
		case "ISBN":
			filter = "isbn";
			break;
		default:
			return -2;
		}
		try (Connection con = DriverManager.getConnection(url);) {
			String sql;
			if (filter.equals("id"))
				if (filter.equals("id") && search.equals(""))
					sql = "SELECT * FROM books ORDER BY year DESC";
				else {
					Integer.parseInt(search);
					sql = "SELECT * FROM books WHERE " + filter + "=" + search + " ORDER BY year DESC";
				}
			else
				sql = "SELECT * FROM books WHERE " + filter + " LIKE '%" + search + "%' ORDER BY year DESC";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			int counter = 0;
			while (rSet.next()) {
				Object obj[] = new Object[8];
				obj[0] = String.valueOf(rSet.getInt(1));
				obj[1] = rSet.getString(3);
				obj[2] = rSet.getString(2);
				obj[3] = rSet.getString(4);
				obj[4] = String.valueOf(rSet.getInt(5));
				obj[5] = String.valueOf(rSet.getInt(6));
				obj[6] = rSet.getString(7);
				if (rSet.getInt(8) == 1)
					obj[7] = "Kölcsönözhetõ";
				else
					obj[7] = "NEM KÖLCSÖNÖZHETÕ";
				list.addRow(obj);
				counter++;
			}
			if (counter == 0)
				// list.addElement("Nincs a keresésnek megfelelõ megjelenítendõ könyv!");
				return -4;
			return 0;
		} catch (NumberFormatException e) {
			// list.addElement("Nincs a keresésnek megfelelõ megjelenítendõ könyv!");
			return -3;
		}

		catch (Exception e) {
			// e.printStackTrace();
			return -1;
		}

	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Creates a list of members that match the search. If the filter is ID, then it
	 * has to be a full match, otherwise it's enough if the attribute contains the
	 * expression.
	 * 
	 * @param filter Search attribute (category)
	 * @param search Searching expression
	 * @param list   Graphical list object which contains the members
	 * @return 0 - Everything is OK. -1 - DataBase fault, couldn't match, -2 -
	 *         Filter wasn't selected before search, -3 - Filter was ID but the
	 *         search expression wasn't a number.
	 */

	public int searchMember(String filter, String search, DefaultTableModel list) {
		Statement stm;
		String url = "jdbc:sqlite:library.db";
		switch (filter) {
		case "Azonosító":
			filter = "id";
			break;
		case "Név":
			filter = "name";
			break;
		case "Lakcím":
			filter = "address";
			break;
		case "Tagság":
			filter = "type";
			break;
		default:
			return -2;
		}
		try (Connection con = DriverManager.getConnection(url);) {
			String sql;
			if (filter.equals("id"))
				if (filter.equals("id") && search.equals(""))
					sql = "SELECT * FROM members ORDER BY name";
				else {
					Integer.parseInt(search);
					sql = "SELECT * FROM members WHERE " + filter + "=" + search + " OR " + search
							+ "= null ORDER BY name";
				}
			else
				sql = "SELECT * FROM members WHERE " + filter + " LIKE '%" + search + "%' ORDER BY name";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			int counter = 0;
			while (rSet.next()) {
				Object obj[] = new Object[5];
				obj[0] = String.valueOf(rSet.getInt(1));
				obj[1] = rSet.getString(2);
				obj[2] = rSet.getString(3);
				obj[3] = rSet.getString(4);
				obj[4] = rSet.getString(5);
				list.addRow(obj);
				counter++;
			}
			if (counter == 0)
				return -4;
			// list.addElement("Nincs a keresésnek megfelelõ megjelenítendõ tag!");
			return 0;
		} catch (NumberFormatException e) {
			// list.addElement("Nincs a keresésnek megfelelõ megjelenítendõ tag!");
			return -3;
		} catch (Exception e) {
			// e.printStackTrace();
			return -1;
		}

	}

	// function--------------------------------------------------------------------------------------------------------
	/**
	 * Creates a list of borrowings that match the search. If the filter is ID, then
	 * it has to be a full match, otherwise it's enough if the attribute contains
	 * the expression.
	 * 
	 * @param filter Search attribute (category)
	 * @param search Searching expression
	 * @param list   Graphical list component which it adds borrowings to.
	 * @param member Member object who's borrowings will be filtered.
	 * @param active Looks for active (1) or inactive (0) borrowings
	 * @return 0 - Everything is OK. -1 - DataBase fault, couldn't match, -2 -
	 *         Filter wasn't selected before search, -3 - Filter was ID but the
	 *         search expression wasn't a number.
	 */
	public int searchBorrowing(String filter, String search, DefaultTableModel list, Member member, int active) {
		Statement stm;
		String table = "borrows";
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			// search between specified member rentals
			if (member != null) {
				switch (filter) {
				case "Azonosító":
					filter = "id";
					table = "borrows";
					break;
				case "Könyv azonosító":
					filter = "book_id";
					if (!search.equals(""))
						table = "borrows";
					break;
				case "Szerzõ":
					filter = "author";
					if (!search.equals(""))
						table = "books";
					break;
				case "Cím":
					filter = "title";
					if (!search.equals(""))
						table = "books";
					break;
				case "ISBN":
					filter = "isbn";
					if (!search.equals(""))
						table = "books";
					break;
				default:
					return -2;
				}
				String sql;
				if (filter.equals("id") || filter.equals("book_id")) {
					if ((filter.equals("id") || filter.equals("book_id")) && search.equals(""))
						sql = "SELECT DISTINCT borrows.id,books.id,title,author,date FROM books,borrows WHERE books.id = borrows.book_id AND borrows.member_id = "
								+ String.valueOf(member.getId()) + " AND borrows.return = " + String.valueOf(active)
								+ " ORDER BY borrows.id";
					else {
						Integer.parseInt(search);
						sql = "SELECT DISTINCT borrows.id,books.id,title,author,date FROM books,borrows WHERE " + table
								+ "." + filter + "=" + search
								+ " AND books.id = borrows.book_id AND borrows.member_id = "
								+ String.valueOf(member.getId()) + " AND borrows.return = " + String.valueOf(active)
								+ " ORDER BY borrows.id";
					}
				} else {
					if (!search.equals(""))
						sql = "SELECT DISTINCT borrows.id,books.id,title,author,date FROM books,borrows WHERE " + table
								+ "." + filter + " LIKE '%" + search
								+ "%' AND books.id = borrows.book_id AND borrows.member_id = "
								+ String.valueOf(member.getId()) + " AND borrows.return = " + String.valueOf(active)
								+ " ORDER BY borrows.id";

					else
						sql = "SELECT DISTINCT borrows.id,books.id,title,author,date FROM books,borrows WHERE books.id = borrows.book_id AND borrows.member_id = "
								+ String.valueOf(member.getId()) + " AND borrows.return = " + String.valueOf(active)
								+ " ORDER BY borrows.id";
				}
				stm = con.createStatement();
				ResultSet rSet = stm.executeQuery(sql);
				int counter = 0;
				while (rSet.next()) {
					Object obj[] = new Object[5];
					obj[0] = String.valueOf(rSet.getInt(1));
					obj[1] = String.valueOf(rSet.getInt(2));
					obj[2] = rSet.getString(3);
					obj[3] = rSet.getString(4);
					obj[4] = rSet.getString(5);
					list.addRow(obj);
					counter++;
				}
				if (counter == 0)
					return -4;
				// list.addElement("Nincs a keresésnek megfelelõ megjelenítendõ könyv!");
				return 0;
				// search between the all members active rentals
			} else {
				
				switch (filter) {
				case "Azonosító":
					filter = "id";
					table = "borrows";
					break;
				case "Kölcsönzõ neve":
					filter = "name";
					if (!search.equals(""))
						table = "members";
					break;
				case "Könyv címe":
					filter = "title";
					if (!search.equals(""))
						table = "books";
					break;
				default:
					return -2;
				}
				String sql;
				if (filter.equals("id")) {
					if (filter.equals("id") && search.equals(""))
						
						/*sql = "SELECT DISTINCT borrows.id,name,title,date FROM books,borrows,members WHERE books.id = borrows.book_id AND borrows.member_id = members.id"
								+ " AND borrows.return = " + String.valueOf(active) + " ORDER BY name";*/
						sql = "SELECT DISTINCT borrows.id,members.id,name,title,date FROM books,borrows,members WHERE books.id = borrows.book_id AND borrows.member_id = members.id"
								+ " AND borrows.return = " + String.valueOf(active) + " ORDER BY name";
					else {
						Integer.parseInt(search);
						sql = "SELECT DISTINCT borrows.id,members.id,name,title,date FROM books,borrows,members WHERE " + table
								+ "." + filter + "=" + search
								+ " AND books.id = borrows.book_id AND borrows.member_id = members.id"
								+ " AND borrows.return = " + String.valueOf(active) + " ORDER BY name";
					}
				} else {
					if (!search.equals(""))
						sql = "SELECT DISTINCT borrows.id,members.id,name,title,date FROM books,borrows,members WHERE " + table
								+ "." + filter + " LIKE '%" + search
								+ "%' AND books.id = borrows.book_id AND borrows.member_id = members.id"
								+ " AND borrows.return = " + String.valueOf(active) + " ORDER BY name";

					else
						sql = "SELECT DISTINCT borrows.id,members.id,name,title,date FROM books,borrows,members WHERE books.id = borrows.book_id AND borrows.member_id = members.id"
								+ " AND borrows.return = " + String.valueOf(active) + " ORDER BY name";
				}
				stm = con.createStatement();
				ResultSet rSet = stm.executeQuery(sql);
				int counter = 0;
				while (rSet.next()) {/*
					Object obj[] = new Object[4];
					obj[0] = String.valueOf(rSet.getInt(1));
					obj[1] = rSet.getString(2);
					obj[2] = rSet.getString(3);
					obj[3] = rSet.getString(4);*/

					Object obj[] = new Object[5];
					obj[0] = String.valueOf(rSet.getInt(1));
					obj[1] = String.valueOf(rSet.getInt(2));
					obj[2] = rSet.getString(3);
					obj[3] = rSet.getString(4);
					obj[4] = rSet.getString(5);
					list.addRow(obj);
					counter++;
				}
				if (counter == 0)
					return -4;
				return 0;
			}
		} catch (NumberFormatException e) {
			// list.addElement("Nincs a keresésnek megfelelõ megjelenítendõ könyv!");
			return -3;
		}

		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Remove a book from the DB
	 * 
	 * @param bookID Book which will be removed
	 * @param all    0 - All piece in library, 1 - selected book will be removed.
	 * @return 0 - Everything is OK. -1, Couldn't remove the book
	 */
	public int removeBook(Book book, int all) {
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String sql;
			PreparedStatement ps;
			if (all == 1) {
				sql = "DELETE FROM books WHERE id=?";
				ps = con.prepareStatement(sql);
				ps.setString(1, String.valueOf(book.getId()));
			} else {
				sql = "DELETE FROM books WHERE title = ? AND author = ? AND isbn = ? AND borrow = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, book.getTitle());
				ps.setString(2, book.getAuthor());
				ps.setString(3, book.getISBN());
				ps.setInt(4, 1);
			}
			int nez = ps.executeUpdate();
			if (nez <= 0)
				return -1;

			sql = "UPDATE borrows SET book_borrowStatus = ? WHERE book_id = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, -1);
			ps.setInt(2, book.getId());
			ps.execute();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Remove a member from the DB
	 * 
	 * @param bookID Book's id which will be removed
	 * @return 0 - Everything is OK. -1, Couldn't remove the book
	 */
	public int removeMember(String memberID) {
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "DELETE FROM members WHERE id=" + memberID;
			PreparedStatement ps = con.prepareStatement(sql);
			int nez = ps.executeUpdate();
			if (nez <= 0)
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Modify in the DB
	 * 
	 * @param the type of content to modify
	 * @param the id of the row which should be modified
	 * @param the field which should be modified
	 * @param the value the field should be modified to
	 * @return 0 - Everything is OK. -1 - Couldn't modify the member
	 */
	private int modify(String type, int id, String what, String to) {
		String url = "jdbc:sqlite:library.db";

		try (Connection con = DriverManager.getConnection(url)) {
			String sql = "UPDATE " + type + " SET " + what + "= ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, to);
			ps.setInt(2, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Modify a member in the DB
	 * 
	 * @param the id of the row which should be modified
	 * @param the field which should be modified
	 * @param the value the field should be modified to
	 * @return 0 - Everything is OK. -1 - Couldn't modify the member
	 */
	public int modifyMember(int id, String what, String to) {
		return modify("members", id, what, to);
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Modify a book in the DB
	 * 
	 * @param the id of the row which should be modified
	 * @param the field which should be modified
	 * @param the value the field should be modified to
	 * @return 0 - Everything is OK. -1 - Couldn't modify the member
	 */
	public int modifyBook(int id, String what, String to) {
		return modify("books", id, what, to);
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Counts the borrowings which are belong to the current member.
	 * 
	 * @param member Member object to whom it counts the borrowings.
	 * @return retVal >= 0 - Everything is OK, -1 - Couldn't count the number of
	 *         borrowings;
	 */
	public int countBook(Member member) {
		int retVal = 0;
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT COUNT (member_id) FROM borrows WHERE member_id=" + String.valueOf(member.getId())
					+ " AND return=0";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			if (rSet.next()) {
				retVal = rSet.getInt(1);
			}
			return retVal;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Counts the borrowings which are belong to the current book.
	 * 
	 * @param member Member object to whom it counts the borrowings.
	 * @return retVal >= 0 - Everything is OK, -1 - Couldn't count the number of
	 *         borrowings;
	 */
	public int countBorrows(Book book) {
		int retVal = 0;
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT COUNT (book_id) FROM borrows WHERE book_id=" + String.valueOf(book.getId())
					+ " AND return=0";
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			if (rSet.next()) {
				retVal = rSet.getInt(1);
			}
			return retVal;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function-------------------------------------------------------------------------------------------------------
	/**
	 * Changes a book borrow status in DB. If the book is borrowable, its value is 1
	 * otherwise the value is 0.
	 * 
	 * @param bookId Borrowed book's ID
	 * @param value  This value will be set.
	 * @return 0 - Everything is OK, -1 - Coludn't change the status.
	 */
	public int changeBookBorrowStatus(String bookId, int value) {
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "UPDATE books SET borrow = ? WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, value);
			ps.setString(2, bookId);
			int nez = ps.executeUpdate();
			if (nez <= 0)
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function--------------------------------------------------------------------------------------------------------
	/**
	 * Updates the borrows table, and take back a book and finish the borrow
	 * 
	 * @param borrow borrowing which will be finished.
	 * @return 0 Everything is OK, -1 - Couldn't take back the book.
	 */
	public int bookTakeBack(Borrow borrow) {
		String url = "jdbc:sqlite:library.db";
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "UPDATE borrows SET return = ?,backDate = ?, delayed = ?  WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, borrow.getBroughtBackStatus());
			ps.setString(2, borrow.getBackDate());
			ps.setInt(3, borrow.getDelayStatus());
			ps.setInt(4, borrow.getId());
			int nez = ps.executeUpdate();
			if (nez <= 0)
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	// function
	// -------------------------------------------------------------------------------------------------------
	public int getBookDataFromBorrow(String borrowId, JTextArea infoArea) {
		String url = "jdbc:sqlite:library.db";
		Statement stm;
		try (Connection con = DriverManager.getConnection(url);) {
			String sql = "SELECT book_id, book_title, book_author, book_publisher, book_year, book_edition, book_isbn, book_borrowStatus FROM borrows"
					+ " WHERE id = " + borrowId;
			stm = con.createStatement();
			ResultSet rSet = stm.executeQuery(sql);
			if (rSet.next()) {
				String temp = null;
				temp = ("Azonosító:\t" + rSet.getString(1) + "\r\n" + "Cím:\t" + rSet.getString(2) + "\r\n"
						+ "Szerzõ:\t" + rSet.getString(3) + "\r\n" + "Kiadó:\t" + rSet.getString(4) + "\r\n"
						+ "Kiadás éve:\t" + rSet.getString(5) + "\r\n" + "Kiadás:\t" + rSet.getString(6) + "\r\n"
						+ "ISBN:\t" + rSet.getString(7) + "\r\n");
				if (rSet.getInt(8) == -1) {
					temp += "TÖRÖLT KÖNYV";
				}
				infoArea.setText(temp);
			}
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}

	}

}
