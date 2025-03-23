public class Main {
    public static void main(String[] args) {
        System.out.println(GeneralBaseConvertion("1024", 10, 2, 0));
        System.out.println(GeneralBaseConvertion("357", 10, 8, 0));
        System.out.println(GeneralBaseConvertion("364253", 10, 16, 0));

        System.out.println(GeneralBaseConvertion("58EDD", 16, 10, 0));
        System.out.println(GeneralBaseConvertion("A1B207", 16, 8, 0));
        System.out.println(GeneralBaseConvertion("11000110101001", 2, 8, 0));

        System.out.println(GeneralBaseConvertion("16.233242", 10, 2, 10));
        System.out.println(GeneralBaseConvertion("17.352", 8, 10, 4));
        System.out.println(GeneralBaseConvertion("17.352", 8, 16, 5));
        System.out.println(GeneralBaseConvertion("15.4570", 10, 16, 4));

        System.out.println(GeneralBaseConvertion("4H5.4ZG", 35, 27, 8));
    }

    private static String GeneralBaseConvertion(String number, int baseOriginal, int baseGoal, int precision) {
        if (baseOriginal < 2 || baseGoal < 2) {
            return "error de base";
        }
        if (baseOriginal == baseGoal) {
            return number;
        }
        String[] parts = number.split("[.]");
        if (parts.length == 1) {
            if (baseOriginal == 10) {
                return deleteExtraZero(decToBaseX(Integer.parseInt(parts[0]), baseGoal));
            } else if (baseGoal == 10) {
                return Integer.toString(baseXToDec(parts[0], baseOriginal));
            } else {
                return deleteExtraZero(decToBaseX(baseXToDec(parts[0], baseOriginal), baseGoal));
            }
        } else if (parts.length == 2) {
            String integerPart = GeneralBaseConvertion(parts[0], baseOriginal, baseGoal, precision);
            String fractionalPart;
            if (baseOriginal == 10) {
                fractionalPart = getFractionalPart(Double.parseDouble(parts[1])/Math.pow(10,parts[1].length()), baseGoal, precision);
            } else if (baseGoal == 10) {
                fractionalPart = Double.toString(getDecimalPart(parts[1], baseOriginal)).substring(2,2+precision);
            } else {
                fractionalPart = getFractionalPart(getDecimalPart(parts[1], baseOriginal), baseGoal, precision);
            }
            return integerPart + "." + fractionalPart;
        }
        return "error de sintaxis";

    }

    private static String decToBaseX(int number, int base) {
        if(number == 0) { //Base case
            return "0";
        } else {
            return decToBaseX (number/base, base) + numberToChar(number%base); //Builds new number in string
        }
    }

    private static int baseXToDec(String number, int base) {
        int decimal = 0;
        for(int i = 0; i < number.length(); i++) {
            //System.out.println(charToNumber(number.charAt(i)));
            decimal += charToNumber(number.charAt(i)) * (int) Math.pow(base, number.length() - (i + 1));
        }
        return decimal;
    }

    private static char numberToChar(int number) {
        return (char) (number <= 9 ? '0' + number : 'A' + (number - 10)); // Converts 0-9 to char and maps 10-35 to A-Z
    }

    private static int charToNumber(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';  // Convert '0'-'9' to 0-9
        } else /*if (c >= 'A' && c <= 'Z')*/ {
            return c - 'A' + 10;  // Convert 'A'-'Z' to 10-35
        }
    }

    private static String getFractionalPart(double decimals, int base, int precision) {
        StringBuilder fractionalPart = new StringBuilder();
        while (decimals > 0 && fractionalPart.length() < precision) {
            decimals *= base;
            int fraction = (int) decimals;  // Extract integer part
            fractionalPart.append(numberToChar(fraction));
            decimals -= fraction;  // Remove integer part
        }
        return fractionalPart.toString();
    }

    private static double getDecimalPart(String fractional, int base) {
        double decimalValue = 0;
        for (int i = 0; i < fractional.length(); i++) {
            decimalValue += charToNumber(fractional.charAt(i)) / Math.pow(base, i + 1);
        }
        return decimalValue;
    }

    private static String deleteExtraZero(String number) {
        if (number.charAt(0) == '0' && number.length() > 1) {
            return number.substring(1);
        } else return number;
    }

}