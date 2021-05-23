package com.codesquad.coco.room;

import com.codesquad.coco.image.ImageDAO;
import com.codesquad.coco.room.model.Room;
import com.codesquad.coco.room.model.dto.SearchPriceDTO;
import com.codesquad.coco.room.model.dto.SearchRoomDTO;
import com.codesquad.coco.user.Reservation;
import com.codesquad.coco.utils.mapper.ReservationMapper;
import com.codesquad.coco.utils.mapper.RoomMapper;
import com.codesquad.coco.utils.mapper.SearchPriceMapper;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.codesquad.coco.utils.RoomSQLKt.*;

@Component
public class RoomDAO {

    private NamedParameterJdbcTemplate template;
    private ImageDAO imageDAO;

    public RoomDAO(NamedParameterJdbcTemplate template, ImageDAO imageDAO) {
        this.template = template;
        this.imageDAO = imageDAO;
    }

    public List<Integer> findAllPriceBySearchPriceDTO(SearchPriceDTO dto) {
        MapSqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("check_in", dto.getCheckIn())
                .addValue("check_out", dto.getCheckOut())
                .addValue("city_name", dto.getCityName());
        return template.query(FIND_ALL_PRICE_BY_SEARCH_REQUIREMENT, parameter, new SearchPriceMapper());

    }

    public List<Room> findAllBySearchRoomDTO(SearchRoomDTO roomDTO) {
        List<Room> rooms = findAllRoomBySearchRoomDTOWithoutImageAndReservation(roomDTO);
        //todo : 해당하는 방이 없을 때의 예외 설정
        for (Room room : rooms) {
            Long id = room.getId();
            findAllReservationByRoomId(id).forEach(room::addReservation);
            imageDAO.findAllImageByRoomId(id).forEach(room::addImages);
        }
        return rooms;
    }

    private List<Room> findAllRoomBySearchRoomDTOWithoutImageAndReservation(SearchRoomDTO roomDTO) {
        MapSqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("check_in", roomDTO.getCheckIn())
                .addValue("check_out", roomDTO.getCheckOut())
                .addValue("city_name", roomDTO.getCityName())
                .addValue("adult", roomDTO.getAdult())
                .addValue("child", roomDTO.getChild())
                .addValue("baby", roomDTO.getBaby())
                .addValue("price_min", roomDTO.getPriceMin())
                .addValue("price_max", roomDTO.getPriceMax());
        return template.query(FIND_ALL_ROOM_BY_SEARCH_REQUIREMENT, parameter, new RoomMapper());

    }

    private List<Reservation> findAllReservationByRoomId(Long id) {
        MapSqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("room_id", id);
        return template.query(FIND_ALL_RESERVATION_BY_ROOM_ID, parameter, new ReservationMapper());
    }

    public Room findById(Long roomId) {
        MapSqlParameterSource parameter = new MapSqlParameterSource()
                .addValue("room_id", roomId);
        List<Room> rooms = template.query(FIND_ROOM_BY_ROOM_ID, parameter, new RoomMapper());
        Room room = (Room) getEntity(rooms);
        Long id = room.getId();
        imageDAO.findAllImageByRoomId(id).forEach(room::addImages);
        return room;
    }

    private Object getEntity(List<?> sample) {
        return Optional.ofNullable(DataAccessUtils.singleResult(sample)).orElseThrow(NullPointerException::new);
        //fixme : 예외 설정
    }
}
