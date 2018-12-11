package day7

const val BASIC_TASK_TIME = 60

class Worker(val id: Int) {

    private var workingOn: Graph.Node? = null
    private var busyFor: Int = 0

    fun assignWork(work: Graph.Node) {
        busyFor = BASIC_TASK_TIME + (work.id - 'A') + 1
        workingOn = work
        work.color = Graph.Node.Color.GREY
    }

    fun isBusy(): Boolean = busyFor > 0

    fun tick() {
        --busyFor
        if (!isBusy() && workingOn != null) {
            workingOn!!.color = Graph.Node.Color.BLACK
        }
    };

    override fun toString(): String = "Worker[id=$id, workingOn=$workingOn]"

}