package common

import java.io.File

fun readLines(fileName: String): List<String> =	File("resources/${fileName}").readLines()

fun readSingle(fileName: String): String = readLines(fileName)[0]
