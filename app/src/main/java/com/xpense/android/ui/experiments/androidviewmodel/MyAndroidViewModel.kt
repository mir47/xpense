package com.xpense.android.ui.experiments.androidviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xpense.android.R

/**
 * Example AndroidViewModel that takes Application as constructor parameter,
 * which gives access to application resources in the view model.
 */
class MyAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    init {
        _text.value = application.getString(R.string.android_view_model_text)
    }
}
