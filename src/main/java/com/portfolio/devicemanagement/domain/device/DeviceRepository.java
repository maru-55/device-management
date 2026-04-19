package com.portfolio.devicemanagement.domain.device;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DeviceRepository {

    @Select("""
        <script>
          SELECT id, name, model_number, serial_number, introduction_date, location, status
          FROM devices
          <where>
            <if test='condition.name != null and !condition.name.isBlank()'>
              name LIKE CONCAT('%', #{condition.name}, '%')
            </if>
            <if test='condition.status != null and !condition.status.isEmpty()'>
              AND status IN (
                <foreach item='item' index='index' collection='condition.status' separator=','>
                  #{item}
                </foreach>
              )
            </if>
          </where>
        </script>
        """)
    List<DeviceEntity> select(@Param("condition") DeviceSearchEntity condition);

    @Select("SELECT id, name, model_number, serial_number, introduction_date, location, status FROM devices WHERE id = #{deviceId}")
    Optional<DeviceEntity> selectById(@Param("deviceId") long deviceId);

    @Insert("""
    INSERT INTO devices (name, model_number, serial_number, introduction_date, location, status)
    VALUES (#{device.name}, #{device.modelNumber}, #{device.serialNumber}, #{device.introductionDate}, #{device.location}, #{device.status})
    """)
    void insert(@Param("device") DeviceEntity newEntity);

    @Update("""
            UPDATE devices
            SET
                name = #{device.name},
                model_number      = #{device.modelNumber},
                serial_number     = #{device.serialNumber},
                introduction_date = #{device.introductionDate},
                location          = #{device.location},
                status            = #{device.status}
            WHERE
                id = #{device.id}
            """)
    void update(@Param("device") DeviceEntity entity);

    @Delete("DELETE FROM devices WHERE id = #{deviceId}")
    void delete(@Param("deviceId") long id);

    @Update("UPDATE devices SET status = #{device.status} WHERE id = #{device.id}")
    void updateStatus(@Param("device") DeviceEntity device);
}
