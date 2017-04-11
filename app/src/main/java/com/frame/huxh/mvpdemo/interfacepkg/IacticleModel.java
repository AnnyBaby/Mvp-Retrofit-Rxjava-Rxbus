package com.frame.huxh.mvpdemo.interfacepkg;

import org.reactivestreams.Subscriber;

/**
 * Created by Administrator on 2017/4/8.
 */

public interface IacticleModel<T> {
    void requestData(Subscriber<T> subscriber);

    void saveDatas(T o );

    void loadDataFromDB(T o);
}
