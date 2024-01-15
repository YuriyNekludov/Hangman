import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int mistakeCount;
    public static List<Character> usedChars;
    public static String secretWord;
    public static char[] charsOfSecretWord;
    public static List<String> gameWords;
    public static String guessedWord;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в игру Виселица.\n");
        parseFileToStringList();
        System.out.println("Количество слов в словаре системы: " + gameWords.size() + '\n');
        while (true) {
            System.out.println("Желаете начать [Н]овую игру или хотите [В]ыйти из игры?");
            char inputChar = Character.toUpperCase(scanner.next().charAt(0));
            if (inputChar == 'В')
                break;
            else if (inputChar == 'Н') {
                GameInitialization();
                System.out.println("Слово загадано. Количество букв в слове составляет " + secretWord.length() +
                        " символов.");
                getSecretWord();
                while (true) {
                    System.out.println("Введите букву: ");
                    inputChar = Character.toUpperCase(scanner.next().charAt(0));
                    if (usedChars.contains(inputChar)) {
                        System.out.println("Вы уже использовали данную букву. Пожалуйста, используйте другую.");
                        continue;
                    }
                    checkMistakes(inputChar);
                    if (mistakeCount == 6)
                        break;
                    guessWord(inputChar);
                    if (checkGuessedWord()) {
                        System.out.println("Вы победили! Вы отгадали загаданное слово " + secretWord);
                        break;
                    }
                    printGameInformation();
                }
            }
        }
        scanner.close();
        System.out.println("До свидания!");
    }

    public static void GameInitialization() {
        mistakeCount = 0;
        usedChars = new ArrayList<>();
        guessedWord = "";
        secretWord = getWordFromStringList(gameWords);
    }

    public static void parseFileToStringList() {
        File file = new File("russian_words.txt");
        gameWords = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String fileLine = scanner.nextLine();
                if (fileLine.length() >= 6) {
                    if (fileLine.contains("-"))
                        continue;
                    gameWords.add(fileLine.toUpperCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getWordFromStringList(List<String> stringList) {
        Random random = new Random();
        return stringList.get(random.nextInt(stringList.size()));
    }

    public static void getSecretWord() {
        charsOfSecretWord = new char[secretWord.length()];
        Arrays.fill(charsOfSecretWord, '*');
        System.out.println(charsOfSecretWord);
    }

    public static void guessWord(char inputChar) {
        char[] temp = secretWord.toCharArray();
        usedChars.add(inputChar);
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == Character.toUpperCase(inputChar))
                charsOfSecretWord[i] = temp[i];
        }
    }

    public static boolean checkGuessedWord() {
        guessedWord = new String(charsOfSecretWord);
        return secretWord.equals(guessedWord);
    }

    public static void printGameInformation() {
        System.out.println(charsOfSecretWord);
        System.out.println();
        printUsedChars();
    }

    public static void checkMistakes(char inputChar) {
        if (!secretWord.contains(String.valueOf(inputChar))) {
            mistakeCount++;
            checkMistakeCount();
        }
    }

    public static void printUsedChars() {
        System.out.print("Ваши использованные буквы: ");
        for (int i = 0; i < usedChars.size(); i++) {
            if (i == usedChars.size() - 1) {
                System.out.println(usedChars.get(i));
                System.out.println();
                break;
            }
            System.out.print(usedChars.get(i) + " ");
        }
    }

    public static void checkMistakeCount() {
        switch (mistakeCount) {
            case 1:
                System.out.println("""
                        Ой! Похоже, вы ошиблись. Такой буквы нет.
                                    
                        У вас осталось 5 ошибок.
                                    
                        |
                        |
                        |
                        |
                        |
                        |
                        """);
                break;
            case 2:
                System.out.println("""
                        Ой! Похоже, вы ошиблись. Такой буквы нет.
                                    
                        У вас осталось 4 ошибки.
                         ___
                        |/  |
                        |
                        |
                        |
                        |
                        |
                        """);
                break;
            case 3:
                System.out.println("""
                        Ой! Похоже, вы ошиблись. Такой буквы нет.
                                    
                        У вас осталось 3 ошибки.
                         ___
                        |/  |
                        |   *
                        |
                        |
                        |
                        |
                        """);
                break;
            case 4:
                System.out.println("""
                        Ой! Похоже, вы ошиблись. Такой буквы нет.
                                    
                        У вас осталось 2 ошибки.
                         ___
                        |/  |
                        |   *
                        |  /||
                        |
                        |
                        |
                        """);
                break;
            case 5:
                System.out.println("""
                        Ой! Похоже, вы ошиблись. Такой буквы нет.
                                    
                        У вас осталось 1 ошибка.
                         ___
                        |/  |
                        |   *
                        |  /||
                        |   |
                        |
                        |
                        """);
                break;
            case 6:
                System.out.println("""
                        Ой! Похоже, вы ошиблись. Такой буквы нет.
                                    
                        Вы проиграли.
                        Загаданное слово было\s""" + secretWord
                );
                break;
        }
    }
}