class Car(
    private var position: Int,
    private val name: String
) {

    fun move() {
        val randomNumber = Dice().roll()

        if (randomNumber >= 4) {
            position++
        }
    }

    fun getCar(): Pair<String, Int> {
        return Pair(name, position)
    }
}
