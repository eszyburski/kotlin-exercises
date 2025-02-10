package coroutines.suspension

import kotlin.coroutines.*

suspend fun a() {
    val c = 12345
    val d = "aaa"
    suspendCoroutine { it.resume(Unit) }
    suspendCoroutine { continuation ->
        continuation.resume(c) // HERE
    }
    println(c)
}

suspend fun main() {
    val a = "ABC"
    val b = listOf(1, 2, 3)
    val e = "hello"
    println(b)
    a()
    println(a)
    println(e)
}