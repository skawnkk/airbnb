package airbnb.service;


import airbnb.domain.Room;
import airbnb.exception.RoomNotFoundException;
import airbnb.repository.RoomRepository;
import airbnb.request.SearchRequest;
import airbnb.response.RoomDetailPageResponse;
import airbnb.response.RoomResponse;
import airbnb.response.RoomResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }

    public RoomDetailPageResponse findRoomDetailPageById(Long roomId) {
        return Room.createDetailPageResponse(findRoomById(roomId));
    }

    public List<RoomResponse> findRoomsFilteredBy(SearchRequest searchRequest) {
        return roomRepository.findRoomsFilteredBy(searchRequest)
                .stream()
                .map(Room::createRoomResponse)
                .collect(Collectors.toList());
    }

    public RoomResponses findRoomsCategorizedByCity(SearchRequest searchRequest) {
        return RoomResponses.getRoomsCategorizedByCity(findRoomsFilteredBy(searchRequest));
    }
}
