package com.kostryk.icaloryai.lifecycle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel() {

    private val errorHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }
    }

    protected fun launch(
        supervisor: Boolean = false,
        context: CoroutineContext = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(errorHandler) {
            withContext(context) {
                if (supervisor) {
                    supervisorScope { block.invoke(this) }
                } else {
                    block.invoke(this)
                }
            }
        }
    }

}