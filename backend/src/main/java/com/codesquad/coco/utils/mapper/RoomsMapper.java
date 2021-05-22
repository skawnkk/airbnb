package com.codesquad.coco.utils.mapper;

import com.codesquad.coco.Host.Host;
import com.codesquad.coco.rooms.model.*;
import com.codesquad.coco.user.WishList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomsMapper implements RowMapper<Rooms> {

    @Override
    public Rooms mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Rooms.Builder()
                .id(rs.getLong("rm_id"))
                .name(rs.getString("rm_name"))
                .pricePerDate(new Money(rs.getInt("rm_price_per_date")))
                .description(rs.getString("rm_description"))
                .type(rs.getString("rm_type"))
                .roomsOption(new RoomsOption(
                        rs.getInt("rm_bed"),
                        rs.getInt("rm_max_guest"),
                        rs.getInt("rm_bathroom")
                        ))
                .host(new Host(
                        rs.getString("h_name"),
                        rs.getString("h_profile_image_url")
                ))
                .location(new Location(
                        rs.getDouble("l_latitude"),
                        rs.getDouble("l_longitude")
                ))
                .additionalCost(new AdditionalCost(
                        rs.getInt("ac_week_sale_percent"),
                        rs.getInt("ac_cleaning_fee"),
                        rs.getInt("ac_service_fee_percent"),
                        rs.getInt("ac_lodgment_fee_percent")
                ))
                .review(new Review(
                        rs.getDouble("rw_star"),
                        rs.getDouble("rw_review")
                ))
                .wishList(new WishList(
                        rs.getLong("wl_user_id"),
                        rs.getLong("wl_rooms_id"),
                        rs.getBoolean("wl_wish")
                ))
                .build();

    }
}
