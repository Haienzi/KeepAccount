package com.qiu.keepaccount.model.book;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.entity.User;
import org.litepal.LitePal;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:49
 * @Description 账本数据操作实现
 */
public class BookModel implements IBookModel{
    /**
     * 添加账本
     *
     * @param user      用户
     * @param sceneName 场景名称
     * @param book      账本
     */
    @Override
    public void addAccountBook(User user, String sceneName, Book book) {
        book.setSceneName(sceneName);
        book.setUserId(user.getId());
        book.saveAsync();
    }

    /**
     * 更新账本
     *
     * @param user      用户
     * @param sceneName 场景名称
     * @param book      账本
     */
    @Override
    public void updateAccountBook(User user, String sceneName, Book book) {
        book.setSceneName(sceneName);
        book.setUserId(user.getId());
        book.updateAsync(book.getId());
    }

    /**
     * 删除账本
     *
     * @param book 账本
     */
    @Override
    public void deleteAccountBook(Book book) {
        LitePal.delete(Book.class,book.getId());
    }

    /**
     * 查询用户下的所有账本
     *
     * @param user 指定的用户
     * @return
     */
    @Override
    public List<Book> queryBooks(User user) {
        List<Book> bookList = null;
        if(user != null){
            bookList= LitePal.findAll(Book.class);
        }else{
            bookList= LitePal.where("userId = ?",
                        user.getId().toString())
                        .order("createDate desc")
                        .find(Book.class);
        }
        return bookList;
    }

    /**
     * 查询用户指定场景下的所有账本
     *
     * @param user      指定的用户
     * @param sceneName 账本场景
     * @return
     */
    @Override
    public List<Book> queryBooks(User user, String sceneName) {
        List<Book> bookList = null;
        if(user != null){
            bookList= LitePal.where("sceneName = ?",
                    sceneName)
                    .order("createDate desc")
                    .find(Book.class);
        }else{
            bookList= LitePal.where("userId = ? and sceneName = ?",
                    user.getId().toString(),sceneName)
                    .order("createDate desc")
                    .find(Book.class);
        }
        return bookList;
    }

    /**
     * 查找指定帐薄里所有账目信息
     *
     * @param user 用户
     * @param book 账本
     * @param type 类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public List<Account> queryBookAccounts(User user, Book book, int type) {
        return null;
    }

    /**
     * 查找指定帐薄里指定日期内的账目信息
     *
     * @param user      用户
     * @param book
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public List<Account> queryBookAccounts(User user, Book book, String startDate, String endDate, int type) {
        List<Account> accountList = null;
        if(user != null){
            //用户未登录
            if(type == -1){
                accountList = LitePal.where("bookId = ? and createDate >= ? and createDate <= ? ",
                        String.valueOf(book.getId()), startDate,endDate)
                        .order("createDate desc")
                        .find(Account.class);
            }else{
                accountList = LitePal.where("bookId = ? and createDate >= ? and createDate <= ? and accountType = ?",
                        String.valueOf(book.getId()), startDate,endDate,String.valueOf(type))
                        .order("createDate desc")
                        .find(Account.class);
            }

        }else{
            //用户已登录
            if(type == -1){
                accountList = LitePal.where("userId = ? and bookId = ? and createDate >= ? and createDate <= ?",
                        user.getId().toString(),String.valueOf(book.getId()), startDate,endDate)
                        .order("createDate desc")
                        .find(Account.class);
            }else {
                accountList = LitePal.where("userId = ? and bookId = ? and createDate >= ? and createDate <= ? and accountType = ?",
                        user.getId().toString(),String.valueOf(book.getId()), startDate,endDate,String.valueOf(type))
                        .order("createDate desc")
                        .find(Account.class);
            }

        }

        return accountList;
    }

    /**
     * 查找指定帐薄里指定日期内的总支出
     *
     * @param user      用户
     * @param book
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public double queryBookCostOrIncome(User user, Book book, String startDate, String endDate, int type) {
        return 0;
    }

    /**
     * 查找指定帐薄里指定日期内的总支出
     *
     * @param user      用户
     * @param book      账本
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    @Override
    public double queryBookCostOrIncome(User user, Book book, int type) {
        double amount = 0.00;
        if(user == null){
            //用户未登录
            if(type == -1){
                double income = LitePal.where(" bookId = ? and accountType = ? ",
                         String.valueOf(book.getId()),String.valueOf(2))
                        .sum(Account.class,"amount",Double.class);
                double cost = LitePal.where(" bookId = ? and accountType = ? ",
                        String.valueOf(book.getId()),String.valueOf(1))
                        .sum(Account.class,"amount",Double.class);
                amount = income + cost;
            }else if(type == 2){
                amount = LitePal.where(" bookId = ? and accountType = ? ",
                        String.valueOf(book.getId()),String.valueOf(2))
                        .sum(Account.class,"amount",Double.class);
            }else{
                amount = LitePal.where(" bookId = ? and accountType = ? ",
                        String.valueOf(book.getId()),String.valueOf(1))
                        .sum(Account.class,"amount",Double.class);

            }
        }else{
            //用户已登录
            if(type == -1){
                double income = LitePal.where(" userId = ? and bookId = ? and accountType = ? ",
                        user.getId().toString(),String.valueOf(book.getId()),String.valueOf(2))
                        .sum(Account.class," amount",Double.class);
                double cost = LitePal.where(" userId = ? and bookId = ? and accountType = ? ",
                        user.getId().toString(),String.valueOf(book.getId()),String.valueOf(1))
                        .sum(Account.class,"amount",Double.class);
                amount = income + cost;
            }else if(type == 2){
                amount =  LitePal.where(" userId = ? and bookId = ? and accountType = ? ",
                        user.getId().toString(),String.valueOf(book.getId()),String.valueOf(2))
                        .sum(Account.class,"amount",Double.class);
            }else{
                amount =  LitePal.where(" userId = ? and bookId = ? and accountType = ? ",
                        user.getId().toString(),String.valueOf(book.getId()),String.valueOf(1))
                        .sum(Account.class,"amount",Double.class);
            }
        }
        return amount;
    }
}
