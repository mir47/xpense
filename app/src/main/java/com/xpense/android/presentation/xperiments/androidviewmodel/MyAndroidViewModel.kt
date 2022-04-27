package com.xpense.android.presentation.xperiments.androidviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.R

/**
 * Example AndroidViewModel that takes Application as constructor parameter.
 * Useful when access to application resources is needed in the view model,
 * e.g. application.getString(R.string.app_name)
 */
class MyAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    init {
        _text.value = application.getString(R.string.android_view_model_text)
    }
}
