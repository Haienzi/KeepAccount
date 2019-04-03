package com.qiu.keepaccount.model.book;

import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.entity.User;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/2 10:49
 * @Description 账本接口
 */
public interface IBookModel {

    /**
     * 添加账本
     * @param user 用户
     * @param sceneName 场景名称
     * @param book 账本
     */
    void addAccountBook(User user, String sceneName, Book book);

    /**
     * 更新账本
     * @param user 用户
     * @param sceneName 场景名称
     * @param book 账本
     */
    void updateAccountBook(User user, String sceneName, Book book);

    /**
     * 删除账本
     * @param book 账本
     */
    void deleteAccountBook(Book book);

    /**
     * 根据id获取账本信息
     * @param id
     * @return
     */
    Book getBook(int id);

    /**
     * 查询用户下的所有账本
     * @param user 指定的用户
     * @return
     */
    List<Book> queryBooks(User user);

    /**
     * 查询用户指定场景下的所有账本
     * @param user 指定的用户
     * @param sceneName 账本场景
     * @return
     */
    List<Book> queryBooks(User user,String sceneName);

    /**
     * 查找指定帐薄里所有账目信息
     *
     * @param user      用户
     * @param book      账本
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    List<Account> queryBookAccounts(User user, Book book,int type);


    /**
     * 查找指定帐薄里指定日期内的账目信息
     *
     * @param user      用户
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    List<Account> queryBookAccounts(User user, Book book, String startDate, String endDate, int type);

    /**
     * 查找指定帐薄里指定日期内的总支出
     *
     * @param user      用户
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    double queryBookCostOrIncome(User user,Book book,String startDate, String endDate, int type);


    /**
     * 查找指定帐薄里指定日期内的总支出
     *
     * @param user      用户
     * @param type      类型 1、支出 2、收入 （-1 不分类型查找）
     */
    double queryBookCostOrIncome(User user,Book book, int type);



}
