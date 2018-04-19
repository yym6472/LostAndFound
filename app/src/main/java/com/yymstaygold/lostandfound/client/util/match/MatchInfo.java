package com.yymstaygold.lostandfound.client.util.match;

public class MatchInfo {
    private int lostId;
    private int foundId;

    public MatchInfo(int lostId, int foundId) {
        this.lostId = lostId;
        this.foundId = foundId;
    }

    public int getLostId() {
        return this.lostId;
    }

    public int getFoundId() {
        return this.foundId;
    }
}
