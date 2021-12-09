package ext

fun<T> IndexedValue<T>.nextValue(newValue: T): IndexedValue<T> = this.copy(index+1, newValue)

fun Int.bpmToDelayMillis(): Long = 1000/(toLong()/60)