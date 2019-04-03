package com.qiu.keepaccount.base;

/**
 * view层父接口
 * @param <T>
 */
public interface BaseView<T>{
    /**
     * 在view层获取相应的Presenter实例进行交互
     * @param presenter
     */
    void setPresenter(T presenter);
}
