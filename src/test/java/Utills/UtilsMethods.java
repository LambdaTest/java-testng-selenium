package Utills;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class UtilsMethods {

    public String getRandomString(int size) {
        byte[] bytArray = new byte[256];
        new Random().nextBytes(bytArray);

        String randomStr = new String(bytArray, StandardCharsets.UTF_8);
        StringBuilder strBuilder = new StringBuilder();
        // remove all special char
        String alphaStr = randomStr.replaceAll("[^A-Za-z]", "");

        for (int i = 0; i < alphaStr.length(); i++) {
            if (size > 0 && (Character.isLetter(alphaStr.charAt(i)) || Character.isDigit(alphaStr.charAt(i)))) {
                strBuilder.append(alphaStr.charAt(i));
            }
            size--;
        }
        return strBuilder.toString();
    }

    public String generateRandomNumber(int length) {
        StringBuilder builder = new StringBuilder();
        Random objGenerator = new Random();
        while (length > 0) {
            int randomNumber = objGenerator.nextInt(9);
            builder.append(randomNumber);
            length--;
        }
        return builder.toString();
    }
}