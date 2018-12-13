package day10

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class StarDrawer(val points: List<Star>) : JPanel() {
    override fun paintComponent(g: Graphics?) {
        g!!.color = Color.BLACK
        g!!.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)
        g!!.color = Color.YELLOW
        points.forEach {
            g!!.fillOval(it.position.first, it.position.second, 2, 2)
        }
    }

}
