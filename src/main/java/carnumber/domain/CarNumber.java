package carnumber.domain;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CarNumber {
    public static final long MAX_CAR_NUMBERS;
    private static final ArrayList<Character> availableChars = new ArrayList<Character>() {{
        add('А');
        add('Е');
        add('Т');
        add('О');
        add('Р');
        add('Н');
        add('У');
        add('К');
        add('Х');
        add('С');
        add('В');
        add('М');
    }};
    private static final Random random = new Random();

    static {
        long alphabetSize = availableChars.size();
        MAX_CAR_NUMBERS = (long) (Math.pow(alphabetSize, 3) * 1000);
        availableChars.sort(Character::compareTo);
    }

    private char firstChar;
    private char secondChar;
    private char thirdChar;
    private int number;
    private String region;

    public CarNumber(String region) {
        this.region = region;
    }

    public char getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(char firstChar) {
        this.firstChar = firstChar;
    }

    public char getSecondChar() {
        return secondChar;
    }

    public void setSecondChar(char secondChar) {
        this.secondChar = secondChar;
    }

    public char getThirdChar() {
        return thirdChar;
    }

    public void setThirdChar(char thirdChar) {
        this.thirdChar = thirdChar;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public static CarNumber getFirstCarNumber(String region) {
        CarNumber carNumber = new CarNumber(region);
        carNumber.setFirstChar(availableChars.get(0));
        carNumber.setSecondChar(availableChars.get(0));
        carNumber.setThirdChar(availableChars.get(0));
        carNumber.setNumber(0);

        return carNumber;
    }

    public static CarNumber getRandomNumber(String region) {
        CarNumber carNumber = new CarNumber(region);

        carNumber.setNumber(random.nextInt(1000));

        byte alphabetSize = (byte) availableChars.size();
        carNumber.setFirstChar(availableChars.get(random.nextInt(alphabetSize)));
        carNumber.setSecondChar(availableChars.get(random.nextInt(alphabetSize)));
        carNumber.setThirdChar(availableChars.get(random.nextInt(alphabetSize)));

        return carNumber;
    }

    public CarNumber getNextCarNumber() {
        if (isLastCarNumber()) {
            return null;
        }

        CarNumber nextCarNumber = new CarNumber(this.region);
        nextCarNumber.setFirstChar(this.firstChar);
        nextCarNumber.setSecondChar(this.secondChar);
        nextCarNumber.setThirdChar(this.thirdChar);

        if (this.number == 999) {
            nextCarNumber.setNumber(0);

            char firstChar = availableChars.get(0);
            nextCarNumber.setThirdChar(getNextChar(getThirdChar()));
            if (nextCarNumber.getThirdChar() == firstChar) {
                nextCarNumber.setSecondChar(getNextChar(getSecondChar()));
                if (nextCarNumber.getSecondChar() == firstChar) {
                    nextCarNumber.setFirstChar(getNextChar(getFirstChar()));
                }
            }
        } else {
            nextCarNumber.setNumber(getNumber() + 1);
        }

        return nextCarNumber;
    }

    private char getNextChar(char prevChar) {
        int currentIndex = availableChars.indexOf(prevChar);
        if (currentIndex == availableChars.size() - 1) {
            return availableChars.get(0);
        }

        return availableChars.get(currentIndex + 1);
    }

    private boolean isLastCarNumber() {
        char lastChar = availableChars.get(availableChars.size() - 1);
        return getFirstChar() == lastChar && getSecondChar() == lastChar && getThirdChar() == lastChar && getNumber() == 999;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarNumber carNumber = (CarNumber) o;
        return firstChar == carNumber.firstChar &&
                secondChar == carNumber.secondChar &&
                thirdChar == carNumber.thirdChar &&
                number == carNumber.number &&
                Objects.equals(region, carNumber.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstChar, secondChar, thirdChar, number, region);
    }

    @Override
    public String toString() {
        int length = String.valueOf(number).length();
        String carNumber = length == 1 ? "00" + number : length == 2 ? "0" + number : String.valueOf(number);

        return firstChar + carNumber + secondChar + thirdChar + " " + region;
    }
}
