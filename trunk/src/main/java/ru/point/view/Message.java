package ru.point.view;

import java.util.List;
import java.util.LinkedList;

/**
 * @author: Mikhail Sedov [02.04.2009]
 */
public class Message {

    public static final String PASSWORD_OLD_WRONG = "Хм,.. старый пароль не тот";
    public static final String PASSWORD_NOT_MATCH = "Новые пароли не совпадают";
    public static final String PASSWORD_UPDATED = "Пароль обновлен";
    public static final String PASSWORD_TOO_SHORT = "Новый пароль слишком короткий";

    private boolean isSuccess;
    private String text;
    private List<String> list = new LinkedList<String>();

    public Message() {
    }

    public Message(String text) {
        this.text = text;
        isSuccess = true;
    }

    public Message(String text, boolean isSuccess) {
        this.text = text;
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void addLineToList(String line) {
        list.add(line);
    }
}
