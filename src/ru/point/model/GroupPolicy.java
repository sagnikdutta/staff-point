package ru.point.model;

/**
 * @author: Mikhail Sedov [25.03.2009]
 */
public enum GroupPolicy {

    DESIGNER(1),
    TESTER(2),
    LEADER(3),
    MANAGER(4),
    SEO(5),
    HR(6);

    private int idx;

    GroupPolicy(int idx) {
        this.idx = idx;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
