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

    private static final String CONSONANT = "��������������������";

    private static boolean isConsonant(char letter) {
        return CONSONANT.indexOf(letter) >= 0;
    }

    private static String[][] MAGIC_MALE_ARRAY = {
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
    };

    private static String[][] MAGIC_FEMALE_ARRAY = {
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "�", "��", "�"},
            {"�", "�", "", "��", "�"},
    };

    public static String inflectMale(Declension dec, String name) {
        char last = name.charAt(name.length() - 1);

        if (last == '�' || last == '�' || last == '�' || last == '�') {
            return name + MAGIC_MALE_ARRAY[0][dec.getIdx()];
        } else if (isConsonant(last)) {
            return name + MAGIC_MALE_ARRAY[1][dec.getIdx()];
        } else if (name.endsWith("��")) {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[2][dec.getIdx()];
        } else if (last == '�' || last == '�') {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[3][dec.getIdx()];
        } else if (name.endsWith("��") || name.endsWith("��") || name.endsWith("��") || name.endsWith("��") ||
                name.endsWith("��") || name.endsWith("��") || name.endsWith("��")) {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[4][dec.getIdx()];
        } else if (last == '�') {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[5][dec.getIdx()];
        } else if (name.endsWith("��")) {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[6][dec.getIdx()];
        } else if (last == '�') {
            return name.substring(0, name.length() - 1) + MAGIC_MALE_ARRAY[7][dec.getIdx()];
        }

        return name;
    }

    public static String inflectFemale(Declension dec, String name) {
        char last = name.charAt(name.length() - 1);

        if (name.endsWith("��") || name.endsWith("��") || name.endsWith("��") || name.endsWith("��") ||
                name.endsWith("��") || name.endsWith("��") || name.endsWith("��")) {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[0][dec.getIdx()];
        } else if (last == '�') {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[1][dec.getIdx()];
        } else if (name.endsWith("��")) {
            if (name.length() > 3) {
                return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[2][dec.getIdx()];
            } else {
                return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[3][dec.getIdx()];
            }
        } else if (last == '�') {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[3][dec.getIdx()];
        } else if (last == '�') {
            return name.substring(0, name.length() - 1) + MAGIC_FEMALE_ARRAY[4][dec.getIdx()];
        } else if (last == '�' || last == '�' || last == '�' || last == '�') {
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
