package com.apollographql.apollo.cache.normalized.internal

import kotlinx.cinterop.convert
import platform.darwin.DISPATCH_TIME_NOW
import platform.darwin.dispatch_time

actual object Platform {
  actual fun currentTimeMillis(): Long {
    val nanoseconds: Long = dispatch_time(DISPATCH_TIME_NOW, 0).convert()
    return nanoseconds * 1_000_000L
  }
}