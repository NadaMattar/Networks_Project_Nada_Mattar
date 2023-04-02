package bn.nadamattar.libraryprojectnada.model;

public class Book {

    private String bookImage ;
    private String bookName ;
    private String bookAuthor ;
    private String documentId ;
    private boolean isSave ;


    public Book() {
    }

    public Book(String bookImage, String bookName, String bookAuthor, String documentId, boolean isSave) {
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.documentId = documentId;
        this.isSave = isSave;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookImage='" + bookImage + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", documentId='" + documentId + '\'' +
                '}';
    }
}
