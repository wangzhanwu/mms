package com.radioson.mms.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MeteMonitorMapper {
    @Select("select Time as 'time', TEM as 'tem', RHU as 'rhu', WIN_S_Avg_2mi as 'wins', WIN_D_Avg_2mi as 'wind', PRS as 'prs', VIS as 'vis', TEM_Min_OTime as 'sri' from surf_chn_mul_hor where Time between #{startTime} and #{endTime} and Station_Type = #{timeType} order by Time desc")
    List<Map<String, Object>> getMeteData(@Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime, @Param(value = "timeType") Integer timeType);

    @Select("select Time as 'time', TEM as 'tem', RHU as 'rhu', WIN_S_Avg_2mi as 'wins', WIN_D_Avg_2mi as 'wind', PRS as 'prs', VIS as 'vis', TEM_Min_OTime as 'sri' from surf_chn_mul_hor order by Time desc limit 1")
    List<Map<String, Object>> getLatestMeteData();
}