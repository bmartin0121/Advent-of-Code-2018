package day4

import common.readLines
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

val DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm")
val GUARD_BEGINS_SHIFT_REGEX = """Guard #(\d+) begins shift""".toRegex()
val LOG_REGEX = """\[(.*)\](.*)""".toRegex()
const val ASLEEP = "falls asleep"
const val WAKE_UP = "wakes up"

fun main(args: Array<String>) {
    var lastTime: LocalDateTime? = null
    val guards = HashMap<Int, Guard?>()
    var currentGuard: Guard? = null
    readLines("day4.txt")
        .map { LOG_REGEX.find(it)!!.destructured }
        .map { (timestamp, message) -> LocalDateTime.parse(timestamp, DATE_TIME_PATTERN) to message }
        .sortedBy { it.first }
        .forEach { (currentTime, message) ->
            when {
                message.endsWith(ASLEEP) -> asleep()
                message.endsWith(WAKE_UP) -> wakeUp(currentGuard, lastTime, currentTime, guards)
                else -> currentGuard = getOrCreateCurrentGuard(message, guards)
            }
            lastTime = currentTime
        }
    /*
     * First part.
     */
    val sleepyGuard = guards.values.maxBy { it!!.getAsleepTime() }
    println(sleepyGuard!!.id * sleepyGuard!!.mostAsleepMinute())

    /*
     * Second part
     */
    val frequentSleeper = guards.values.maxBy { it!!.minutesAsleepCount.maxBy { it.value }!!.value }
    println(frequentSleeper!!.id * frequentSleeper!!.minutesAsleepCount.maxBy { it.value }!!.key)
}

private fun asleep() {
    /* DON'T NEED TO DO ANYTHING, THIS IS JUST A PLACEHOLDER */
}

private fun wakeUp(currentGuard: Guard?, lastTime: LocalDateTime?, currentTime: LocalDateTime, guards: HashMap<Int, Guard?>) {
    currentGuard!!.asleepBetween(lastTime!!.minute..(currentTime.minute - 1))
    guards[currentGuard!!.id] = currentGuard
}

private fun getOrCreateCurrentGuard(message: String, guards: Map<Int, Guard?>): Guard {
    val guardId = GUARD_BEGINS_SHIFT_REGEX.find(message)!!
        .destructured!!
        .component1()!!
        .toInt()
    return guards.getOrDefault(guardId, Guard(guardId))!!
}
