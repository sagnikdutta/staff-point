package ru.point.model.board;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

/**
 * @author: Mikhail Sedov [06.03.2009]
 */
@Entity
@Table(name = "post")
public class Post extends Vote {

    @ManyToOne
    private Vote replyToPost;

    @Column(name = "text")
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
