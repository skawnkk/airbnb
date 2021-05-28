package com.codesquad.airbnb.accommodation.controller;

public final class AccommodationReservationInfoBuilder {
    private Long id;
    private int pricePerNight;
    private int priceForNights;
    private int salePrice;
    private int cleaningPrice;
    private int serviceFee;
    private int accommodationTax;
    private int totalPrice;
    private int reviewCounts;

    private AccommodationReservationInfoBuilder() {
    }

    public static AccommodationReservationInfoBuilder anAccommodationReservationInfo() {
        return new AccommodationReservationInfoBuilder();
    }

    public AccommodationReservationInfoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public AccommodationReservationInfoBuilder pricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
        return this;
    }

    public AccommodationReservationInfoBuilder priceForNights(int priceForNights) {
        this.priceForNights = priceForNights;
        return this;
    }

    public AccommodationReservationInfoBuilder salePrice(int salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public AccommodationReservationInfoBuilder cleaningPrice(int cleaningPrice) {
        this.cleaningPrice = cleaningPrice;
        return this;
    }

    public AccommodationReservationInfoBuilder serviceFee(int serviceFee) {
        this.serviceFee = serviceFee;
        return this;
    }

    public AccommodationReservationInfoBuilder accommodationTax(int accommodationTax) {
        this.accommodationTax = accommodationTax;
        return this;
    }

    public AccommodationReservationInfoBuilder totalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public AccommodationReservationInfoBuilder reviewCounts(int reviewCounts) {
        this.reviewCounts = reviewCounts;
        return this;
    }

    public AccommodationReservationInfo build() {
        return new AccommodationReservationInfo(id, pricePerNight, priceForNights, salePrice, cleaningPrice, serviceFee, accommodationTax, totalPrice, reviewCounts);
    }
}
