package carnumber.controller;

import carnumber.domain.CarNumber;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/number")
public class CarNumberController {
    private static final String REGION = "116 RUS";
    private static List<CarNumber> alreadyUsedCarNumbers = new LinkedList<>();
    private static CarNumber lastUsedCarNumber;

    @GetMapping("/random")
    public String getRandomCarNumber() {
        if (alreadyUsedCarNumbers.size() == CarNumber.MAX_CAR_NUMBERS) {
            return lastUsedCarNumber + " - последний доступный номер. Все номера использованы.";
        }
        CarNumber carNumber;
        do {
            carNumber = CarNumber.getRandomNumber(REGION);
        } while (alreadyUsedCarNumbers.contains(carNumber));

        alreadyUsedCarNumbers.add(carNumber);
        lastUsedCarNumber = carNumber;

        return carNumber.toString();
    }

    @GetMapping("/next")
    public String getNextCarNumber() {
        CarNumber carNumber;
        if (lastUsedCarNumber == null) {
            carNumber = CarNumber.getFirstCarNumber(REGION);
        } else {
            carNumber = lastUsedCarNumber.getNextCarNumber();
        }

        if (carNumber == null) {
            return lastUsedCarNumber.toString() + " - последний доступный номер";
        }

        alreadyUsedCarNumbers.add(carNumber);
        lastUsedCarNumber = carNumber;

        return carNumber.toString();
    }
}
