package ru.mail.polis.homework.analyzer;


import java.util.Arrays;

/**
 * Задание написать систему фильтрации комментариев.
 * Надо реализовать три типа обязательных фильтров
 * 1) фильтр для слишком длинных текстов (длина задается при создании) (TOO_LONG)
 * 2) фильтр для спама (передается массив плохих слов, которых не должно быть в тексте) (SPAM)
 * 3) фильтр для текстов с плохими эмоциями. (в тексте не должно быть таких смайлов:
 * "=(", ":(", ":|" (NEGATIVE_TEXT)
 * + сделать любой свой фильтр (CUSTOM)
 *
 * Класс TextFilterManager должен содержать все фильтры, которые передаются ему в конструкторе,
 * и при анализе текста через метод analyze должен выдавать первый "успешный" фильтр,
 * если ни один не прошел, то возвращать тип GOOD.
 * Дополнительное задание: нужно всем типам фильтров задать приоритет
 * (SPAM, TOO_LONG, NEGATIVE_TEXT, CUSTOM - в таком порядке) и возвращать тип с максимальным приоритетом.
 * Отсортировать фильтра можно с помощью функции
 * Arrays.sort(filter, (filter1, filter2) -> {
 *     if (filter1 < filter2) {
 *         return -1;
 *     } else if (filter1 == filter2) {
 *         return 0;
 *     }
 *     return 1;
 * }
 * где вместо сравнение самих фильтров должно быть стравнение каких-то количественных параметров фильтра
 *
 * 2 балла ( + 2 балла за доп приоритет)
 * Итого 15 баллов + 2 дополнительных
 */
public class TextFilterManager {

    /**
     * Для работы с каждым элементом массива, нужно использовать цикл for-each
     * Хочется заметить, что тут мы ничего не знаем, какие конкретно нам объекты переданы, знаем только то,
     * что в них реализован интерфейс TextAnalyzer
     */

    private final TextAnalyzer[] filters;

    public TextFilterManager(TextAnalyzer[] filters) {
        this.filters = Arrays.copyOf(filters, filters.length);
        Arrays.sort(this.filters, (first, second) -> {
            if (order(first) < order(second)) {
                return -1;
            } else if (order(first) == order(second)) {
                return 0;
            }
            return 1;
        });
    }

    private byte order(TextAnalyzer first) {
        switch (first.getFilterType()) {
            case SPAM:
                return 1;
            case TOO_LONG:
                return 2;
            case NEGATIVE_TEXT:
                return 3;
            case TOO_FEW_WORDS:
                return 4;
        }
        return Byte.MAX_VALUE;
    }

    /**
     * Если переменная текст никуда не ссылается, то это означает, что не один фильтр не сработал
     */
    public FilterType analyze(String text) {

        for (TextAnalyzer filter : filters) {
            if (filter.check(text)) {
                return filter.getFilterType();
            }
        }

        return FilterType.GOOD;
    }
}
