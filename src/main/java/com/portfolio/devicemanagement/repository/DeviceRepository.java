package com.portfolio.devicemanagement.repository;

import com.portfolio.devicemanagement.service.DeviceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DeviceRepository {

    @Select("SELECT id, name, status FROM devices;")
    List<DeviceEntity> select();

    @Select("SELECT id, name, status FROM devices WHERE id = #{deviceId}")
    Optional<DeviceEntity> selectById(@Param("deviceId") long deviceId);
}
