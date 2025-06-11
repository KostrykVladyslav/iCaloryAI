package com.kostryk.icaloryai.domain.arch.coroutines.impl

import com.kostryk.icaloryai.domain.arch.coroutines.CoroutineLauncher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class DefaultCoroutineLauncher(
    private val scope: CoroutineScope
) : CoroutineLauncher {

    override fun launch(supervisor: Boolean, block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(errorHandler) {
            if (supervisor) {
                supervisorScope {
                    block.invoke(this)
                }
            } else {
                block.invoke(this)
            }
        }
    }

    private val errorHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }
    }
}