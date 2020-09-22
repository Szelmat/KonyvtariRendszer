package core;

public class Borrow {
	private int id;
	private int memberId;
	private int bookId;
	private String date;
	private int broughtBack;
	private String backDate;
	private int delay;
	
	//bookData
	private String bookTitle;
	private String bookAuthor;
	private String bookPublisher;
	private int bookYear;
	private int bookEdition;
	private String bookISBN;
	private int bookBorrowStatus;
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getBroughtBackStatus() {
		return broughtBack;
	}
	public void setBroughtBackStatus(int broughtBack) {
		this.broughtBack = broughtBack;
	}
	public String getBackDate() {
		return backDate;
	}
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}
	public int getDelayStatus() {
		return delay;
	}
	public void setDelayStatus(int delay) {
		this.delay = delay;
	}
	
	//book getteSetter
	public String getBookTitle() {
		return bookTitle;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getBookPublisher() {
		return bookPublisher;
	}
	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}
	public int getBookYear() {
		return bookYear;
	}
	public void setBookYear(int bookYear) {
		this.bookYear = bookYear;
	}
	public int getBookEdition() {
		return bookEdition;
	}
	public void setBookEdition(int bookEdition) {
		this.bookEdition = bookEdition;
	}
	public String getBookISBN() {
		return bookISBN;
	}
	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}
	public int getBookBorrowStatus() {
		return bookBorrowStatus;
	}
	public void setBookBorrowStatus(int bookBorrowStatus) {
		this.bookBorrowStatus = bookBorrowStatus;
	}
	
}
