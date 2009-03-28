package ru.point.insert;

/**
 * @author: Mikhail Sedov [12.01.2009]
 */
public class Translit {

    private static final String[] charTable = new String[81];

    private static final char START_CHAR = '¨';

    static {
        charTable['À' - START_CHAR] = "A";
        charTable['Á' - START_CHAR] = "B";
        charTable['Â' - START_CHAR] = "V";
        charTable['Ã' - START_CHAR] = "G";
        charTable['Ä' - START_CHAR] = "D";
        charTable['Å' - START_CHAR] = "E";
        charTable['¨' - START_CHAR] = "E";
        charTable['Æ' - START_CHAR] = "ZH";
        charTable['Ç' - START_CHAR] = "Z";
        charTable['È' - START_CHAR] = "I";
        charTable['É' - START_CHAR] = "I";
        charTable['Ê' - START_CHAR] = "K";
        charTable['Ë' - START_CHAR] = "L";
        charTable['Ì' - START_CHAR] = "M";
        charTable['Í' - START_CHAR] = "N";
        charTable['Î' - START_CHAR] = "O";
        charTable['Ï' - START_CHAR] = "P";
        charTable['Ð' - START_CHAR] = "R";
        charTable['Ñ' - START_CHAR] = "S";
        charTable['Ò' - START_CHAR] = "T";
        charTable['Ó' - START_CHAR] = "U";
        charTable['Ô' - START_CHAR] = "F";
        charTable['Õ' - START_CHAR] = "H";
        charTable['Ö' - START_CHAR] = "C";
        charTable['×' - START_CHAR] = "CH";
        charTable['Ø' - START_CHAR] = "SH";
        charTable['Ù' - START_CHAR] = "SH";
        charTable['Ú' - START_CHAR] = "'";
        charTable['Û' - START_CHAR] = "Y";
        charTable['Ü' - START_CHAR] = "'";
        charTable['Ý' - START_CHAR] = "E";
        charTable['Þ' - START_CHAR] = "U";
        charTable['ß' - START_CHAR] = "YA";

        for (int i = 0; i < charTable.length; i++) {
            char idx = (char) ((char) i + START_CHAR);
            char lower = new String(new char[]{idx}).toLowerCase().charAt(0);
            if (charTable[i] != null) {
                charTable[lower - START_CHAR] = charTable[i].toLowerCase();
            }
        }
    }


    public static String toTranslit(String text) {
        char charBuffer[] = text.toCharArray();
        StringBuilder sb = new StringBuilder(text.length());
        for (char symbol : charBuffer) {
            int i = symbol - START_CHAR;
            if (i >= 0 && i < charTable.length) {
                String replace = charTable[i];
                sb.append(replace == null ? symbol : replace);
            } else {
                sb.append(symbol);
            }
        }
        return sb.toString();
    }
}
