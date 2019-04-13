package com.qiu.keepaccount.mvp.bookaccount;

import com.qiu.keepaccount.base.BasePresenter;
import com.qiu.keepaccount.base.BaseView;
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Book;

import java.util.List;

public interface BookAccountContract {
    interface BookAccountView extends BaseView<BookAccountContract.IBookAccountPresenter> {
    /**
     * 显示支出记录
      */
      void selectCost(List<Account> accountList);

    /**
     * 显示收入记录
     */
    void selectIncome();
        /**
         * 显示所有记录
         */
      void selectAll();


    }


    /**
     * @author mqh
     */
    interface IBookAccountPresenter extends BasePresenter {

        /**
         * 查找账本内的记录
         * @param book
         */
       void selectAccountByBook(Book book);


    }
}
