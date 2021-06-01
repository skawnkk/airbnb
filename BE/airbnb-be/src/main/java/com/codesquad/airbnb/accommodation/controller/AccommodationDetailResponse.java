package com.codesquad.airbnb.accommodation.controller;

import com.codesquad.airbnb.accommodation.domain.Accommodation;
import com.codesquad.airbnb.accommodation.domain.AccommodationOption;
import com.codesquad.airbnb.reservation.domain.ReservationDetail;

import java.util.List;
import java.util.Objects;

public class AccommodationDetailResponse {
    private Long id;
    private String name;
    private AccommodationOption accommodationOption;
    private Integer totalPrice;
    private double reviewRating;
    private int reviewCounts;
    private String mainImage;
    private List<String> images;
    private String description;
    private AccommodationHost accommodationHost;

    public AccommodationDetailResponse() {
    }

    public AccommodationDetailResponse(Long id, String name, AccommodationOption accommodationOption, Integer totalPrice, double reviewRating, int reviewCounts, String mainImage, List<String> images, String description, AccommodationHost accommodationHost) {
        this.id = id;
        this.name = name;
        this.accommodationOption = accommodationOption;
        this.totalPrice = totalPrice;
        this.reviewRating = reviewRating;
        this.reviewCounts = reviewCounts;
        this.mainImage = mainImage;
        this.images = images;
        this.description = description;
        this.accommodationHost = accommodationHost;
    }

    public static AccommodationDetailResponse from(Accommodation accommodation) {
        return builder().id(accommodation.getId())
                       .name(accommodation.getName())
                       .accommodationOption(accommodation.getAccommodationOption())
                       .reviewRating(accommodation.getReviewRating())
                       .reviewCounts(accommodation.getReviewCounts())
                       .mainImage(accommodation.getMainImage())
                       .images(accommodation.getImages())
                       .description(accommodation.getDescription())
                       .accommodationHost(accommodation.getAccommodationHost())
                       .build();
    }

    public static AccommodationDetailResponse of(Accommodation accommodation, ReservationDetail reservationDetail) {
        return builder().id(accommodation.getId())
                       .name(accommodation.getName())
                       .accommodationOption(accommodation.getAccommodationOption())
                       .totalPrice(accommodation.totalPrice(reservationDetail))
                       .reviewRating(accommodation.getReviewRating())
                       .reviewCounts(accommodation.getReviewCounts())
                       .mainImage(accommodation.getMainImage())
                       .images(accommodation.getImages())
                       .description(accommodation.getDescription())
                       .accommodationHost(accommodation.getAccommodationHost())
                       .build();
    }

    public static AccommodationDetailResponseBuilder builder() {
        return AccommodationDetailResponseBuilder.anAccommodationDetailDTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AccommodationOption getAccommodationOption() {
        return accommodationOption;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public double getReviewRating() {
        return reviewRating;
    }

    public int getReviewCounts() {
        return reviewCounts;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public AccommodationHost getAccommodationHost() {
        return accommodationHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccommodationDetailResponse that = (AccommodationDetailResponse) o;
        return Double.compare(that.reviewRating, reviewRating) == 0 && reviewCounts == that.reviewCounts && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(accommodationOption, that.accommodationOption) && Objects.equals(totalPrice, that.totalPrice) && Objects.equals(mainImage, that.mainImage) && Objects.equals(images, that.images) && Objects.equals(description, that.description) && Objects.equals(accommodationHost, that.accommodationHost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, accommodationOption, totalPrice, reviewRating, reviewCounts, mainImage, images, description, accommodationHost);
    }

    @Override
    public String toString() {
        return "AccommodationDetailDTO{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       ", accommodationOption=" + accommodationOption +
                       ", totalPrice=" + totalPrice +
                       ", reviewRating=" + reviewRating +
                       ", reviewCounts=" + reviewCounts +
                       ", mainImage='" + mainImage + '\'' +
                       ", images=" + images +
                       ", description='" + description + '\'' +
                       ", accommodationHost=" + accommodationHost +
                       '}';
    }
}
