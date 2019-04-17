package com.qiu.keepaccount.mvp.books;

import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Book;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:38
 * @Description 账本契约类 规定账本页面需要用到的接口
 */
public interface BookContract {


    interface BookView extends BaseView<BookPresenter>{
        void queryBooks(List<Book> books);

        void deleteBook();

    }
    interface AddBookView extends BaseView<BookPresenter>{
        void save();
    }
    /**
     * 账本数据
     */
    interface BookPresenter {
         void queryBooks();
         void saveBook(Book book);
         void deleteBook(Book book);
         void updateBook(Book book);
    }

}
