package com.example.crudreceitas.infra

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class SingleEventLiveData<T> : MutableLiveData<T?>() {

    private var hasBeenHandled = false

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        super.observe(owner, Observer { t ->
            if (hasBeenHandled) {
                return@Observer
            }
            hasBeenHandled = true
            observer.onChanged(t)
        })
    }

    override fun setValue(value: T?) {
        hasBeenHandled = false
        super.setValue(value)
    }
}
