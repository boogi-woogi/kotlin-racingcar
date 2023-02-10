package view

import data.Car

object OutputView {

    private const val RESULT = "실행 결과\n"
    private const val FINAL_WINNER = "최종 우승자: "
    private const val CAR_PATH = "%s : %s"
    private const val DASH = "-"
    const val TOKENIZER = ","
    const val EMPTY = ""

    fun printErrorMsg(msg: String) {
        println(msg)
    }

    fun printWinner(winners: List<String>) {
        println(FINAL_WINNER)
        println(winners.joinToString("$TOKENIZER "))
    }

    fun printPath(count: Int, cars: List<Car>) {
        if (count == 0) {
            println(RESULT)
        }

        cars.forEach { car ->
            println(CAR_PATH.format(car.name, DASH.repeat(car.position)))
        }
    }
}
