package com.school_of_company.design_system.component.modifier.clickable

internal interface MultipleEventsCutter {
    fun processEvent(event: () -> Unit)

    companion object
}

internal fun MultipleEventsCutter.Companion.get(intervalMs: Long) : MultipleEventsCutter =
    MultipleEventsCutterImpl(intervalMs = intervalMs)

private class MultipleEventsCutterImpl(private val intervalMs: Long) : MultipleEventsCutter {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0L

    override fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= intervalMs) {
            event.invoke()
            lastEventTimeMs = now
        }
    }
}