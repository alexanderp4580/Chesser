package com.alexanderp.chesser.common.models

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import kotlin.time.Duration
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.ExperimentalTime

/**
 * Timer configuration object that is used to start a new game session.
 *
 * This will contain all necessary information to create the timer and display the settings.
 */
@OptIn(ExperimentalTime::class)
@Parcelize
data class TimerConfig(val name: String, val startTime: Duration, val increment: Duration) : Parcelable {
    companion object : Parceler<TimerConfig> {
        override fun TimerConfig.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeLong(startTime.inWholeNanoseconds)
            parcel.writeLong(increment.inWholeNanoseconds)
        }

        override fun create(parcel: Parcel): TimerConfig {
            return TimerConfig(
                parcel.readString() ?: "",
                nanoseconds(parcel.readLong()),
                nanoseconds(parcel.readLong())
            )
        }
    }
}
