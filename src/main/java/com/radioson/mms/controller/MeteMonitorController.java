package com.radioson.mms.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.radioson.mms.bean.Result;
import com.radioson.mms.service.MeteMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mete")
public class MeteMonitorController {

    @Autowired
    private MeteMonitorService meteMonitorService;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/data")
    public Result<List<Map<String, Object>>> getMeteData(@RequestParam(value = "page", defaultValue = "1") Integer pageNum, @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime, @RequestParam(value = "timeType") Integer timeType) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = meteMonitorService.getMeteData(startTime, endTime, timeType);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        Result result = new Result();
        result.setCode(0);
        result.setMsg("响应成功");
        result.setCount(pageInfo.getTotal());
        result.setData(pageInfo.getList());
        return result;
    }

    @RequestMapping(value = "/monitor")
    public List<Map<String, Object>> monitor() {
        return null;
    }

}
