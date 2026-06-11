# Coding Challenges

![build](https://github.com/andrej-dyck/coding-challenges/actions/workflows/gradle-ci.yml/badge.svg?branch=main)

This repository contains some short coding exercises solved in Kotlin.

### Build & Check
```shell
./gradlew check
```

### Linting
Runs with `check`, but you can also run it separately with
```shell
./gradlew detekt
```
This runs [Detekt](https://detekt.github.io/detekt/), a static code analysis tool for Kotlin, with a stricter rule set (cf. [detekt.yml](./detekt.yml)).

### LeetCode Problems

https://leetcode.com/problemset/all/

_Note:_ With these exercises my goal was to solve these mostly in a functional approach. Further, I tried to solve them within a given time limit (easy: < 20min, medium: < 60min).

* [1. Two Sum](test/leetcode/TwoSum.kt)
* [8. String to Integer](test/leetcode/StringToInteger.kt)
* [12. Integer to Roman](test/leetcode/RomanNumerals.kt)
* [14. Longest Common Prefix](test/leetcode/LongestCommonPrefix.kt)
* [17. Letter Combinations of a Phone Number](test/leetcode/LetterCombinationsOfAPhoneNumber.kt)
* [79. Word Search](test/leetcode/WordSearch.kt)
* [121. Best Time to Buy and Sell Stock](test/leetcode/BestTimeToBuyAndSellStock.kt)
* [268. Missing Number](test/leetcode/MissingNumber.kt)
* [278. First Bad Version](test/leetcode/FirstBadVersion.kt)
* [455. Assign Cookies](test/leetcode/AssignCookies.kt)
* [482. License Key Formatting](test/leetcode/LicenseKeyFormatting.kt)
* [665. Non-decreasing Array](test/leetcode/NonDecreasingArray.kt)
* [771. Jewels and Stones](test/leetcode/JewelsAndStones.kt)
* [794. Valid Tic-Tac-Toe State](test/leetcode/ValidTicTacToeState.kt)
* [854. K-Similar Strings](test/leetcode/KSimilarStrings.kt)
* [957. Prison Cells After N Days](test/leetcode/PrisonCellsAfterNDays.kt)
* [1051. Height Checker](test/leetcode/HeightChecker.kt)
* [1078. Occurrences After Bigram](test/leetcode/OccurrencesAfterBigram.kt)
* [1267. Count Servers that Communicate](test/leetcode/CommunicatingServers.kt)
* [1351. Count Negative Numbers in a Sorted Matrix](test/leetcode/CountNegativesInAMatrix.kt)
* [1456. Maximum Number of Vowels in a Substring of Given Length](test/leetcode/MaximumNumberOfVowels.kt)
* [1614. Maximum Nesting Depth of the Parentheses](test/leetcode/NestingDepthOfParentheses.kt)
* [1672. Richest Customer Wealth](test/leetcode/RichestCustomerWealth.kt)
* [1700. Number of Students Unable to Eat Lunch](test/leetcode/NumberOfStudentsUnableToEatLunch.kt)
* [1812. Determine Color of a Chessboard Square](test/leetcode/DetermineColorOfAChessboardSquare.kt)
* [1816. Truncate Sentence](test/leetcode/TruncateSentence.kt)
* [1822. Sign of the Product of an Array](test/leetcode/SignOfTheProductOfAnArray.kt)
* [1848. Minimum Distance to the Target Element](test/leetcode/MinimumDistanceToTargetElement.kt)
* [1935. Maximum Number of Words You Can Type](test/leetcode/MaxWordsThatCanBeTyped.kt)
* [1945. Sum of Digits of String After Convert](test/leetcode/SumOfDigitsOfStringAfterConvert.kt)

### Exercism.io Exercises

https://exercism.io/tracks/kotlin/exercises

_Note:_ With these exercises my goal was to design proper object-oriented code.

* [Acronym](test/exercism/Acronym.kt)
* [Collatz Conjecture](test/exercism/CollatzCalculator.kt)
* [Difference Of Squares](test/exercism/DifferenceOfSquares.kt)
* [Diffie Hellman](test/exercism/DiffieHellman.kt)
* [ETL](test/exercism/ETL.kt)
* [Gigasecond](test/exercism/Gigasecond.kt)
* [Grains](test/exercism/Grains.kt)
* [Hamming](test/exercism/Hamming.kt)
* [Isogram](test/exercism/Isogram.kt)
* [Largest Series Product](test/exercism/LargesSeriesProduct.kt)
* [Leap](test/exercism/LeapYear.kt)
* [Luhn](test/exercism/Luhn.kt)
* [Pangram](test/exercism/Pangram.kt)
* [Perfect Numbers](test/exercism/PerfectNumbers.kt)
* [Raindrops](test/exercism/Raindrops.kt)
* [RNA Transcription](test/exercism/RnaTranscription.kt)
* [RNA Translation](test/exercism/RnaTranslation.kt)
* [Run Length Encoding](test/exercism/RunLengthEncoding.kt)
* [Say](test/exercism/Say.kt)
* [Secret Handshake](test/exercism/SecretHandshake.kt)
* [Sieve](test/exercism/Sieve.kt)
* [Space Age](test/exercism/SpaceAge.kt)
* [Sum of Multiples](test/exercism/SumOfMultiples.kt)
* [Triangle](test/exercism/Triangle.kt)
* [Word Count](test/exercism/WordCount.kt)

### Miscellaneous

* [Binomial Coefficient](test/misc/BinomialCoefficient.kt)
* [Coin Changer](test/misc/CoinChanger.kt)
* [DSFinV-K CSV Fields for TSS Certificate](test/misc/TseCertificateCsvFields.kt)
* [Factorial](test/misc/Factorial.kt)
* [Fizz Buzz](test/misc/FizzBuzz.kt)
* [Sieve of Eratosthenes](test/misc/SieveOfEratosthenes.kt)
* [Unconditional Rock-Paper-Scissors](test/misc/UnconditionalRockPaperScissors.kt)
* [Word Wrap](test/misc/WordWrap.kt)
