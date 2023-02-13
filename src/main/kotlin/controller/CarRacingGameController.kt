package controller

import domain.game.CarRacingGame
import domain.generator.CarGenerator
import model.Car
import model.CarRacingGameDataSource
import view.InputView
import view.OutputView

class CarRacingGameController(
    private val carRacingGame: CarRacingGame = CarRacingGame(),
    private val carGenerator: CarGenerator = CarGenerator(),
) {

    private val carRacingGameDataSource: CarRacingGameDataSource by lazy {
        CarRacingGameDataSource(initCars(), initNumberOfTry())
    }

    fun play() {
        runCatching {
            start(carRacingGameDataSource.cars, carRacingGameDataSource.numberOfTry)
            end(carRacingGameDataSource.cars)
        }.onFailure { exception ->
            OutputView.printErrorMsg(exception.message!!)
        }
    }

    private fun initCars(): List<Car> {
        val names = validateSuccessiveTokenizer(InputView.inputCarNames())

        return carGenerator.generateCars(names)
    }

    private fun initNumberOfTry(): Int = validateNumericNumberOfTry(InputView.inputNumberOfTry())

    private fun start(cars: List<Car>, numberOfTry: Int) {
        repeat(numberOfTry) { count ->
            carRacingGame.moveCarsOneCycle(cars)
            OutputView.printPath(count, cars)
        }
    }

    private fun end(cars: List<Car>) {
        val winners = carRacingGame.getWinners(cars)

        OutputView.printWinner(winners)
    }

    fun validateSuccessiveTokenizer(names: String): List<String> {
        val carNames = names.split(TOKENIZER)

        require(carNames.hasEmptyString()) {
            SUCCESSIVE_TOKENIZER_ERROR
        }

        return carNames
    }

    fun validateNumericNumberOfTry(numberOfTry: String): Int {
        require(numberOfTry.isNumeric()) {
            NUMERIC_ERROR
        }

        return numberOfTry.toInt()
    }

    private fun String.isNumeric(): Boolean = this.chars().allMatch { Character.isDigit(it) }

    private fun List<String>.hasEmptyString(): Boolean = this.all { nameInput -> nameInput != EMPTY }

    companion object {
        private const val SUCCESSIVE_TOKENIZER_ERROR = "[ERROR] 이름 입력시 ,를 연속적으로 입력할 수 없습니다."
        private const val NUMERIC_ERROR = "[ERROR] 시도 횟수는 숫자 입력으로만 받습니다."
        private const val TOKENIZER = ","
        private const val EMPTY = ""
    }
}
