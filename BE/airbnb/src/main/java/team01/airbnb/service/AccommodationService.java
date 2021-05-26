package team01.airbnb.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team01.airbnb.domain.accommodation.Accommodation;
import team01.airbnb.domain.accommodation.AccommodationAddress;
import team01.airbnb.domain.accommodation.AccommodationCondition;
import team01.airbnb.domain.accommodation.AccommodationPhoto;
import team01.airbnb.dto.request.AccommodationSaveRequestDto;
import team01.airbnb.dto.response.AccommodationResponseDto;
import team01.airbnb.exception.ConditionNotFoundException;
import team01.airbnb.repository.AccommodationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public List<Accommodation> accommodations() {
        return accommodationRepository.findAllAccommodations();
    }

    public List<AccommodationResponseDto> findAvailableAccommodationsForReservation() {
        List<AccommodationResponseDto> accommodationResponseDtos = new ArrayList<>();
        List<Accommodation> accommodations = accommodationRepository.findAllAccommodations();
        for (Accommodation accommodation : accommodations) {
            Long accommodationId = accommodation.getId();
            List<String> photos = accommodationRepository.findPhotosByAccommodationId(accommodationId);
            AccommodationCondition condition = accommodationRepository.findConditionByAccommodationId(accommodationId)
                    .orElseThrow(ConditionNotFoundException::new);
            List<String> amenities = accommodationRepository.findAmenitiesByAccommodationId(accommodationId);
            accommodationResponseDtos.add(
                    AccommodationResponseDto.of(accommodation, photos, condition, amenities));
        }
        return accommodationResponseDtos;
    }

    @Transactional
    public void save(AccommodationSaveRequestDto accommodationSaveRequestDto) {
        Long accommodationId = accommodationRepository.saveAccommodation(
                Accommodation.fromSaveRequestDto(accommodationSaveRequestDto));
        accommodationSaveRequestDto.setId(accommodationId);
        accommodationRepository.saveAccommodationAddress(
                AccommodationAddress.fromSaveRequestDto(accommodationSaveRequestDto));
        accommodationRepository.saveAccommodationCondition(
                AccommodationCondition.fromSaveRequestDto(accommodationSaveRequestDto));
        accommodationRepository.saveAccommodationPhoto(
                AccommodationPhoto.fromSaveRequestDto(accommodationSaveRequestDto));
        List<Long> amenityIds = accommodationRepository.findAmenityIdsByNames(
                accommodationSaveRequestDto.getAmenity1()
                , accommodationSaveRequestDto.getAmenity2()
                , accommodationSaveRequestDto.getAmenity3()
                , accommodationSaveRequestDto.getAmenity4()
        );
        accommodationRepository.addAmenitiesToAccommodation(amenityIds, accommodationId);
    }
}
