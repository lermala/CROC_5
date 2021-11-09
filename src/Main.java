/**
 * Посчитать количество пробелов в строке, используя метод
 *
 * Matcher.match(String str, String character)/ Cейчас считает за 38 секунд.
 */

import java.util.ArrayList;

/**
 * Стартовый класс.
 */
public class Main {

    /**
     * Пример входной строки.
     */
    public static final String INPUT_STRING = "Невежество есть мать промышленности, как и суеверий." +
            " Сила размышления и воображения подвержена ошибкам; но привычка двигать рукой или ногой" +
            " не зависит ни от того, ни от другого. Поэтому мануфактуры лучше всего процветают там, где" +
            " наиболее подавлена духовная жизнь, так что мастерская может рассматриваться как машина," +
            " части которой составляют люди.";

    /**
     * Шаблон поиска символов в строке.
     */
    public static final String TEMPLATE = " ";

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) throws InterruptedException {
        long current = System.currentTimeMillis();

        // при COUNT_THREADS = 4 время выполнения 9 с
        // при COUNT_THREADS = 6 время выполнения 8 с
        // при COUNT_THREADS = 8 время выполнения 5 с
        // при COUNT_THREADS = 10 время выполнения 4 с
        // при COUNT_THREADS = 15 время выполнения 2 с
        final int COUNT_THREADS = 15; // количество потоков, которое будет решать задачу

        // делим строку на части (кол-во частей = кол-ву потоков)
        String[] partsOfINPUT_STRING = splitStrOnParts(INPUT_STRING, COUNT_THREADS);
        ArrayList<Thread> threads = new ArrayList<>(); // тут будем хранить потоки

        // в цикле проходим по каждой части строки
        for (String str:
             partsOfINPUT_STRING) {
            // создаем поток, который будет считать количество пробелов в этой части строки
            threads.add(new FirstThread(str, TEMPLATE));
        }

        // тут в цикле стартуем все потоки
        for (Thread thread:
             threads) {
            thread.start();
        }

        // а тут запускаем каждый поток через join(), чтобы они выполнялись поочередно
        for (Thread thread:
                threads) {
            thread.join();
        }

        System.out.println("Count of space: " + FirstThread.getSum());
        System.out.println("Time: " + (System.currentTimeMillis() - current) / 1000 + " c.");
    }

    /**
     * функция для разделения строки на определенное количество равных частей.
     * Если строку не получится разделить на равные части, то последняя часть будет включать в себя остаток от деления.
     * @param str - строка, которую нужно разделить на части
     * @param countOfParts - количество частей, на которое нужно разделить строку
     */
    public static String[] splitStrOnParts(String str, int countOfParts){
        int lenOfStr = str.length();
        int countElInOnePart = lenOfStr / countOfParts; // кол-во элементов в одной части (не считая последней, в которой будет countElInOnePart + остаток

        String[] result = new String[countOfParts]; // части строки (результат работы функции)
        int mod = lenOfStr % countOfParts; // считаем остаток от деления длины строки на кол-во частей
        char[] arrayOfChar = str.toCharArray();

        int startIdForPart = 0; // индекс, с которого начинается часть строки
        int endIdForPart = countElInOnePart; // индекс, которым заканчивается часть строки

        // проходим по массиву частей
        for (int i = 0; i < countOfParts; i++){
            result[i] = ""; // чтобы строка была не null изначально

            // проверка на остаток
            // если это последняя часть и mod ненулевой, т.е. имеется остаток от деления строки на количество частей
            if ( (mod != 0) && (i == countOfParts - 1) ){
                // присваиваем последний элемент строки,
                // чтобы добавить символы, которые не вошли в равные части.
                endIdForPart = lenOfStr;
            }

            // "вырезаем" из строки части
            for (int j = startIdForPart; j < endIdForPart; j++){
                // текущая часть равна символам строки от startIdForPart до endIdForPart
                result[i] += arrayOfChar[j];
            }

            System.out.println(i + ") res=" + result[i]);
            // чтобы перейти к следующей части
            startIdForPart = endIdForPart;
            endIdForPart = endIdForPart + countElInOnePart;
        }

        return result;
    }
}
