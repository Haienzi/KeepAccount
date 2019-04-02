package com.qiu.keepaccount.mvp.books;

import com.qiu.keepaccount.base.BaseView;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:38
 * @Description 账本契约类 规定账本页面需要用到的接口
 */
public interface BookContract {
    /**
     * 账本页面
     */
    interface BookView extends BaseView<BookPresenter>{

    }

    interface AddBookView extends BaseView<BookPresenter>{

    }
    /**
     * 账本数据
     */
    interface BookPresenter {

    }

}
