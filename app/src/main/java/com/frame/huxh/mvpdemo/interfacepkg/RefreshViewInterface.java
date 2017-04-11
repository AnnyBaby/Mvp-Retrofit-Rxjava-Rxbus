package com.frame.huxh.mvpdemo.interfacepkg;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface RefreshViewInterface<T> {
    public  void   showData(T o);
    public  void  showLoading();
    public  void  hideLoading();

}
