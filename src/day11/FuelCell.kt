package day11

class FuelCell(val x: Int, val y: Int) {

    val rackID = x + 10

    fun powerLevel(gridSerialNumber: Int): Int {
        var power = (rackID * y + gridSerialNumber) * rackID
        power = power.toString().reversed()[2].toString().toInt()
        return power - 5
    }

}
