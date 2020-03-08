package com.example.ledger_companion.ui.fileread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilereadViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "No File Selected"
    }
    val text: LiveData<String> = _text
}