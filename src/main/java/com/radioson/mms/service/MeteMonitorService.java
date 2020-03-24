package com.radioson.mms.service;

import com.radioson.mms.dao.MeteMonitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MeteMonitorService {

    @Autowired
    private MeteMonitorMapper meteMonitorMapper;

    public List<Map<String, Object>> getMeteData(String startTime, String endTime, Integer timeType) {
        return meteMonitorMapper.getMeteData(startTime, endTime, timeType);
    }

    public String getLatesMeteData() {
        String data = "--|--|--|--|--|--|--|--";
        List<Map<String, Object>> list = meteMonitorMapper.getLatestMeteData();
        if (list.size() != 0) {
            Map<String, Object> map = list.get(0);
            data = map.get("time")+"|"+map.get("tem")+"|"+map.get("rhu")+"|"+map.get("wins")+"|"+map.get("wind")+"|"+map.get("prs")+"|"+map.get("vis")+"|"+map.get("sri");
        }
        return data;
    }
}
