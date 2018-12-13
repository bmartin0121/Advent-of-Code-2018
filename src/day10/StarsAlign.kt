package day10

import common.readLines
import java.awt.Color
import java.awt.Dimension
import javax.swing.*

val INPUT_REGEX = """position=<([\d \-]+),([\d \-]+)> velocity=<([\d \-]+),([\d \-]+)>""".toRegex()
const val WINDOW_WIDTH = 800
const val WINDOW_HEIGHT = 600
const val CLOSE_TO_UNITE_BY_EXPERIMENT = 10600

class Gui : JFrame() {

    init {
        createUI()
    }

    private fun createUI() {
        title = "The stars align GUI"
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)
        isResizable = false
        setLocationRelativeTo(null)
        background = Color.BLACK
    }
}

fun main(args: Array<String>) {
    val INPUT_REGEX = """position=<([\d \-]+),([\d \-]+)> velocity=<([\d \-]+),([\d \-]+)>""".toRegex()
    val stars = readLines("day10.txt")
        .map { day10.INPUT_REGEX.find(it)!!.destructured }
        .map {
            Star(
                it.component1().trim().toInt() to it.component2().trim().toInt(),
                it.component3().trim().toInt() to it.component4().trim().toInt()
            )
        }
    moveToCorner(stars)
    var rightMost = stars.map { it.position.first }.max()!!
    var downMost = stars.map { it.position.second }.max()!!
    var leftMost = stars.map { it.position.first }.min()!!
    var topMost = stars.map { it.position.second }.min()!!
    val frame = Gui()
    var round = 0
    while (true) {
        val toDraw = normalize(stars, rightMost, downMost, leftMost, topMost)
        if (round % ( if (round <= CLOSE_TO_UNITE_BY_EXPERIMENT) 1000 else 1) == 0) {
            rightMost = stars.map { it.position.first }.max()!!
            downMost = stars.map { it.position.second }.max()!!
            leftMost = stars.map { it.position.first }.min()!!
            topMost = stars.map { it.position.second }.min()!!
            print("Press ENTER to proceed to round $round ...")
            readLine()
        }
        ++round
        val drawer = StarDrawer(toDraw)
        frame.add(drawer)
        frame.isVisible = true
        frame.remove(drawer)
        stars.forEach { it.move() }
    }
}

private fun moveToCorner(stars: List<Star>) {
    val leftMost = stars.map { it.position.first }.min()!!
    val topMost = stars.map { it.position.second }.min()!!
    stars.forEach { it.moveBy((-leftMost) to (-topMost)) }
}

private fun normalize(stars: List<Star>, rightMost: Int, downMost: Int, leftMost: Int, topMost: Int): List<Star> {
    return stars.map {
        Star(
            (((it.position.first - leftMost).toDouble() / (rightMost - leftMost).toDouble()) * WINDOW_WIDTH.toDouble()).toInt() to
                    (((it.position.second - topMost).toDouble() / (downMost - topMost).toDouble()) * WINDOW_HEIGHT.toDouble()).toInt(),
            it.velocity
        )
    }
}
