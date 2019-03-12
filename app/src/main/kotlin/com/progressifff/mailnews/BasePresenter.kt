package com.progressifff.mailnews

import android.support.annotation.CallSuper
import java.lang.ref.WeakReference

abstract class BasePresenter<V>  {
    private var viewRef = WeakReference<V>(null)
    protected val view: V? get() {return viewRef.get()}

    @CallSuper
    open fun bindView(v: V){
        viewRef = WeakReference(v)
    }

    @CallSuper
    open fun unbindView(){
        viewRef.clear()
    }
}