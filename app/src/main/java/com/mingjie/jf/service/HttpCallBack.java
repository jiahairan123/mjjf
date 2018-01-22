package com.mingjie.jf.service;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by qinsiyi on 2016.6.28.
 */
public abstract class HttpCallBack<T>
{
    //    void getHttpCallBack(Object str);
    //    void getHttpCallNull(Object code);

    Type mType;

    public HttpCallBack()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType paraType = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(paraType.getActualTypeArguments()[0]);
    }

    //    public abstract void onError(Request request, Exception e);
    //    public abstract void onResponse(Object data);

    //    public void onSuccess(T2 obj) {
    //
    //    }

    public abstract void getHttpCallNull(String str);

    public abstract void getHttpCallBack(T rsp);

}
