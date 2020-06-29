package ru.stack.test.verevkin.task1;

/**
 * @author Kirill Verevkin
 */
public class Matcher {

    public static final char CHAR_LATIN_SMALL_LETTER = 'a';
    public static final char CHAR_DIGIT = 'd';
    public static final char CHAR_WHITESPACE = ' ';
    public static final char CHAR_SMALL_LETTER_OR_DIGIT = '*';

    /**
     * Метод осуществляет проверку соответствия переданной строки шаблону.
     * Проверка осуществляется по количетсву символов (количество символов шаблона должно совпадать
     * с количеством символов сообщения), а также по типам символов описанным для параметра {@code pattern}
     * @param message
     *        Сообщение, которое необходимо проверить
     * @param pattern
     *        Шаблон проверки сообщения. Может содержать в себе следующие символы:
     *        <ul>
     *            <li><b>a</b> - строчная латинская буква (a-z)</li>
     *            <li><b>d</b> - цифра 0-9</li>
     *            <li><b>*</b> - строчная латинская буква (a-z) ИЛИ цифра 0-9</li>
     *            <li><b>' '</b> - пробел</li>
     *        </ul>
     * @throws IllegalArgumentException - передан не поддерживаемый шаблон сообщения.
     * @return {@code true} Если строка соответствует шаблону
     */
    public static boolean match(String message, String pattern){

        if (message.length() != pattern.length()){
            return false;
        }

        for (int i = 0; i < message.length(); i++) {
            switch (pattern.charAt(i)){
                case CHAR_LATIN_SMALL_LETTER:
                    //Здесь можно также пойти через порядковый номер символа
                    //Что-то вроде (int)message.charAt(i) < 97 || (int)message.charAt(i) > 122
                    if (!Character.toString(message.charAt(i)).matches("[a-z]")){
                        return false;
                    }
                    break;
                case CHAR_DIGIT:
                    if (!Character.isDigit(message.charAt(i))){
                        return false;
                    }
                    break;
                case CHAR_SMALL_LETTER_OR_DIGIT:
                    if (!Character.toString(message.charAt(i)).matches("[a-z\\d\\s]")){
                        return false;
                    }
                    break;
                case CHAR_WHITESPACE:
                    if (!Character.isSpaceChar(message.charAt(i))){
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Указанный шаблон не поддерживается. Попробуйте указать другой.");
            }
        }

        return true;
    }

}
