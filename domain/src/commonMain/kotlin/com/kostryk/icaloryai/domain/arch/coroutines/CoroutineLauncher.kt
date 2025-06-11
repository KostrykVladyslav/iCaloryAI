package com.kostryk.icaloryai.domain.arch.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface CoroutineLauncher {
    fun launch(supervisor: Boolean = true, block: suspend CoroutineScope.() -> Unit): Job
}