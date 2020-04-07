package com.kbja.bestmanager.data.cache;

import com.kbja.bestmanager.data.DataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yiming.zhou on 2020/4/3
 **/
@Component
@Slf4j
public class AutoSaveHandler {
  @Autowired
  private DataMapper playerCached;

  @Scheduled(fixedRate = 60 * 1000)
  public void autoSave(){
    log.info("begin auto save");
    playerCached.savePlayers(null);
    log.info("end auto save");
  }
}
