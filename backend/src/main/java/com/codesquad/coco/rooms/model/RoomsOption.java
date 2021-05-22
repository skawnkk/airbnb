package com.codesquad.coco.rooms.model;

public class RoomsOption {

    private int bed;
    private int maxGuest;
    private int bathRoom;

    public RoomsOption(int bed, int maxGuest, int bathRoom) {
        this.bed = bed;
        this.maxGuest = maxGuest;
        this.bathRoom = bathRoom;
    }

    public int getBed() {
        return bed;
    }

    public int getMaxGuest() {
        return maxGuest;
    }

    public int getBathRoom() {
        return bathRoom;
    }


    @Override
    public String toString() {
        return "RoomsOption{" +
                "bed=" + bed +
                ", maxGuest=" + maxGuest +
                ", bathRoom=" + bathRoom +
                '}';
    }
}
