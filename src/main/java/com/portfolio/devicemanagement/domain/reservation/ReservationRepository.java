package com.portfolio.devicemanagement.domain.reservation;

import com.portfolio.devicemanagement.domain.device.DeviceSearchEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ReservationRepository {

    @Select("""
            SELECT id, device_id, user_id, start_date, end_date, status
            FROM reservations
            WHERE
                    device_id = #{deviceId}
                AND status = 'RESERVED'
                AND start_date <= #{endDate}
                AND end_date >= #{startDate}
            """)
    List<ReservationEntity> findByDeviceAndPeriod(
            @Param("deviceId") long deviceId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Insert("""
            INSERT INTO reservations (device_id, user_id, start_date, end_date, status)
            VALUES (#{reservation.deviceId},
                    #{reservation.userId},
                    #{reservation.startDate},
                    #{reservation.endDate},
                    #{reservation.status})""")
    void insert(@Param("reservation") ReservationEntity newEntity);
}
