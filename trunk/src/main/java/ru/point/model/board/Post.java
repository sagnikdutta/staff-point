package ru.point.model.board;

import com.sleepycat.persist.model.Entity;


/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
public class Post extends Vote {

    private Vote replyToPost;

    private String text;

    public Post() {
    }

    public Vote getReplyToPost() {
        return replyToPost;
    }

    public void setReplyToPost(Vote replyToPost) {
        this.replyToPost = replyToPost;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
