#include <iostream>
#include <string>
#include <cmath>
#include <algorithm>

 

// Function to convert a number from any base to decimal
std::string baseXToDec(const std::string& number, int base) {
    unsigned long long decimal = 0;
    for (char c : number) {
        decimal = decimal * base + (isdigit(c) ? c - '0' : c - 'A' + 10);
    }
    return std::to_string(decimal);
}

// Function to convert a decimal number to another base
std::string decToBaseX(unsigned long long number, int base) {
    if (number == 0) return "0";
    std::string result;
    while (number > 0) {
        int remainder = number % base;
        result += (remainder < 10) ? ('0' + remainder) : ('A' + remainder - 10);
        number /= base;
    }
    std::reverse(result.begin(), result.end());
    return result;
}

// Function to get fractional part of a decimal number in another base
std::string getFractionalPart(long double decimals, int base, int precision) {
    std::string fractionalPart;
    while (decimals > 0 && fractionalPart.length() < precision) {
        decimals *= base;
        int fraction = static_cast<int>(decimals);
        fractionalPart += (fraction < 10) ? ('0' + fraction) : ('A' + fraction - 10);
        decimals -= fraction;
    }
    return fractionalPart;
}

// Function to convert fractional part from a given base to decimal
long double getDecimalPart(const std::string& fractional, int base) {
    long double decimalValue = 0;
    for (size_t i = 0; i < fractional.length(); i++) {
        decimalValue += (isdigit(fractional[i]) ? fractional[i] - '0' : fractional[i] - 'A' + 10) / pow(base, i + 1);
    }
    return decimalValue;
}

// General function for base conversion
std::string GeneralBaseConversion(const std::string& number, int baseOriginal, int baseGoal, int precision) {
    if (baseOriginal < 2 || baseGoal < 2) {
        return "error de base";
    }
    if (baseOriginal == baseGoal) {
        return number;
    }

    size_t pointPos = number.find('.');
    if (pointPos == std::string::npos) {  // Integer conversion
        if (baseOriginal == 10) {
            return decToBaseX(stoull(number), baseGoal);
        }
        else if (baseGoal == 10) {
            return baseXToDec(number, baseOriginal);
        }
        else {
            return decToBaseX(stoull(baseXToDec(number, baseOriginal)), baseGoal);
        }
    }
    else {  // Fractional number conversion
       
        std::string integerPart = GeneralBaseConversion(number.substr(0, pointPos), baseOriginal, baseGoal, precision);
        std::string fractionalPart;

        if (baseOriginal == 10) {
            fractionalPart = getFractionalPart(stold("0." + number.substr(pointPos + 1)), baseGoal, precision);
        }
        else if (baseGoal == 10) {
            std::string fractional = number.substr(pointPos + 1);
            fractionalPart = std::to_string(getDecimalPart(fractional, baseOriginal)).substr(2, precision);
        }
        else {
            fractionalPart = getFractionalPart(getDecimalPart(number.substr(pointPos + 1), baseOriginal), baseGoal, precision);
        }
        return integerPart + "." + fractionalPart;
    }
}

 
