package core;

public class Book {
	private int id = 0;
	private String title;
	private String author;
	private String publisher;
	private int year;
	private int edition;
	private String isbn;
	private boolean borrow;

//getters	
	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getAuthor() {
		return this.author;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public int getYear() {
		return this.year;
	}
	public int getEdition() {
		return this.edition;
	}
	public String getISBN() {
		return this.isbn;
	}
	public boolean getBorrowStatus() {
		return this.borrow;
	}

//setters	
	public void setId(int identifier) {
		this.id = identifier;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	public void setAuthor(String au) {
		this.author = au;
	}

	public void setPublisher(String pub) {
		this.publisher = pub;
	}

	public void setYear(int yr) {
		this.year = yr;
	}

	public void setEdition(int ed) {
		this.edition = ed;
	}
	public void setISBN(String isbn) {
		this.isbn = isbn;
	}
	public void setBorrowStatus(boolean borrow) {
		this.borrow = borrow;
	}
}
