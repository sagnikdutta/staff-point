package ru.point.utils.russian;

/**
 * @author: Mikhail Sedov [27.03.2009]
 */
public enum Declension {

    GENITIVE(0),
    DATIVE(1),
    ACCUSATIVE(2),
    INSTRUMENTAL(3),
    PREPOSITIONAL(4);

    private static final String CONSONANT = "בגדהזחךכלםןנסעפץצקרש";

    private static boolean isConsonant(char letter) {
        return CONSONANT.indexOf(letter) >= 0;
    }

    private static String[][] MAGIC_MALE_ARRAY = {
            {"א", "ף", "א", "ול", "ו"},
            {"א", "ף", "א", "מל", "ו"},
            {"ÿ", "‏", "ÿ", "ול", "ט"},
            {"ÿ", "‏", "ÿ", "ול", "ו"},
            {"ט", "ו", "ף", "מי", "ו"},
            {"û", "ו", "ף", "מי", "ו"},
            {"ט", "ט", "‏", "וי", "ט"},
            {"ט", "ו", "‏", "וי", "ו"},
    };

    private static String[][] MAGIC_FEMALE_ARRAY = {
            {"ט", "ו", "ף", "מי", "ו"},
            {"û", "ו", "ף", "מי", "ו"},
            {"ט", "ט", "‏", "וי", "ט"},
            {"ט", "ו", "‏", "וי", "ו"},
            {"ט", "ט", "ü", "ü‏", "ט"},
            {"ט", "ט", "", "ü‏", "ט"},
    };

    public static String inflectMale(Declension dec, String name) {
        char last = name.charAt(name.length() - 1);

        if (last == 'ק' || last == 'ז' || last == 'צ' || last == 'ר') {
            return name + MAGIC_MALE_ARRAY[0][dec.getIdx()];
        } else if (isConsonant(last)) {
            return name + MAGIC_MALE_ARRAY[1][dec.getIdx()];
        } else if (name.endsWith("טי")) {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[2][dec.getIdx()];
        } else if (last == 'ü' || last == 'י') {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[3][dec.getIdx()];
        } else if (name.endsWith("דא") || name.endsWith("ץא") || name.endsWith("ךא") || name.endsWith("זא") ||
                name.endsWith("קא") || name.endsWith("שא") || name.endsWith("רא")) {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[4][dec.getIdx()];
        } else if (last == 'א') {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[5][dec.getIdx()];
        } else if (name.endsWith("טÿ")) {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[6][dec.getIdx()];
        } else if (last == 'ÿ') {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[7][dec.getIdx()];
        }

        return name;
    }

    public static String inflectFemale(Declension dec, String name) {
        char last = name.charAt(name.length() - 1);

        if (name.endsWith("דא") || name.endsWith("ץא") || name.endsWith("ךא") || name.endsWith("זא") ||
                name.endsWith("קא") || name.endsWith("שא") || name.endsWith("רא")) {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[0][dec.getIdx()];
        } else if (last == 'א') {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[1][dec.getIdx()];
        } else if (name.endsWith("טÿ")) {
            if (name.length() > 3) {
                return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[2][dec.getIdx()];
            } else {
                return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[3][dec.getIdx()];
            }
        } else if (last == 'ÿ') {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[3][dec.getIdx()];
        } else if (last == 'ü') {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[4][dec.getIdx()];
        } else if (last == 'ר' || last == 'ש' || last == 'ק' || last == 'ז') {
            return name + MAGIC_FEMALE_ARRAY[4][dec.getIdx()];
        }
        return name;
    }

    private int idx;

    Declension(int idx) {
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }
}
