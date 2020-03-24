package com.radioson.mms.socket;

import com.radioson.mms.service.MeteMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@EnableScheduling
public class ScheduleTask {
    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private MeteMonitorService meteMonitorService;

    @Scheduled(cron = "0/10 * * * * ?")   //每10s执行一次
    public void test(){
        System.err.println("*********   定时任务执行   **************");
        CopyOnWriteArraySet<WebSocketServer> webSocketSet = WebSocketServer.getWebSocketSet();
        String message = meteMonitorService.getLatesMeteData();
        webSocketSet.forEach(c->{
            try {
                c.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.err.println("*********   定时任务完成   **************");
    }
}
