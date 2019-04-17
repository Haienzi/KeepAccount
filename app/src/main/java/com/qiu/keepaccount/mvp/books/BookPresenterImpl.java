package com.qiu.keepaccount.mvp.books;

import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.model.book.IBookModel;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:00
 * @Description
 */
public class BookPresenterImpl implements BookContract.BookPresenter {

    private BookContract.BookView mBookView;
    private BookContract.AddBookView mAddBookView;
    private IBookModel mBookModel;
    public BookPresenterImpl(BookContract.BookView bookView, IBookModel bookModel){
        mBookView = bookView;
        mBookView.setPresenter(this);
        mBookModel = bookModel;
    }

    public BookPresenterImpl(BookContract.AddBookView addBookView, IBookModel bookModel){
        mAddBookView = addBookView;
        mAddBookView.setPresenter(this);
        mBookModel = bookModel;
    }

    @Override
    public void queryBooks() {
        List<Book> books = mBookModel.queryBooks();
        mBookView.queryBooks(books);
    }

    @Override
    public void saveBook(Book book) {
        mBookModel.addAccountBook(book);
    }

    @Override
    public void deleteBook(Book book) {

    }

    @Override
    public void updateBook(Book book) {

    }
}
