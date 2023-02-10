package controller

import data.Car
import data.CarRacingGameDataSource
import domain.CarGenerator
import domain.CarRacingGame
import domain.InputValidator
import view.InputView
import view.OutputView

class CarRacingGameController(
    private val carRacingGame: CarRacingGame = CarRacingGame(),
    private val carGenerator: CarGenerator = CarGenerator()
) {

    fun play() {
        runCatching {
            val carRacingGameDataSource = initAll()
            start(carRacingGameDataSource.cars, carRacingGameDataSource.numberOfTry)
            end(carRacingGameDataSource.cars)
        }.onFailure { exception ->
            OutputView.printErrorMsg(exception.message!!)
        }
    }

    private fun initCars(): List<Car> {
        val names = InputValidator.validateSuccessiveTokenizer(InputView.inputCarNames())

        return carGenerator.generateCars(names)
    }

    private fun initNumberOfTry(): Int =
        InputValidator.validateIsNumeric(InputView.inputNumberOfTry())

    private fun initAll(): CarRacingGameDataSource {
        val cars = initCars()
        val numberOfTry = initNumberOfTry()

        return CarRacingGameDataSource(cars, numberOfTry)
    }

    private fun start(cars: List<Car>, numberOfTry: Int) {
        repeat(numberOfTry) { count ->
            carRacingGame.moveCars(cars)
            OutputView.printPath(count, cars)
        }
    }

    private fun end(cars: List<Car>) {
        val winners = carRacingGame.decideWinner(cars)

        OutputView.printWinner(winners)
    }
}
