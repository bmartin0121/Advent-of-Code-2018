package day15

class ReadingDistance : Comparator<Pair<Int, Int>> {
    override fun compare(o1: Pair<Int, Int>?, o2: Pair<Int, Int>?) =
            if (o1!!.first == o2!!.first) {
                o1!!.second.compareTo(o2!!.second)
            } else {
                o1!!.first.compareTo(o2!!.first)
            }
}
