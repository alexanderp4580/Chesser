package com.alexanderp.chesser.common.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Returns a LiveData that subscribes to this flow on the context of the [CoroutineScope] passed as a parameter.
 *
 * Source: https://stackoverflow.com/questions/60439871/kotlin-coroutines-flow-with-room-run-again-whenever-device-back-to-active
 */
@OptIn(InternalCoroutinesApi::class)
fun <T> Flow<T>.asLiveData(scope: CoroutineScope): LiveData<T> {
    val mutableLiveData = MutableLiveData<T>()
    scope.launch {
        collect {
            mutableLiveData.value = it
        }
    }
    return mutableLiveData
}
