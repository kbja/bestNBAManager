package com.kbja.bestmanager;

import com.kbja.bestmanager.data.DataMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

/**
 * Created by yiming.zhou on 2020/4/3
 **/
@Configuration
@Slf4j
public class CommonConfiguration {

  @Value("${player.list.file.name}")
  private String playerListFileName;
  @Value("${config.file.name}")
  private String configFileName;
  @Getter
  private String rootPath;
  @Autowired
  private DataMapper playerCached;
  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");


  public boolean initData() {
    try {
      playerCached.getPlayers(null, null, null);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean initConfiguration() {
    return false;
  }

  public String getConfigFilePath() {
    return rootPath + configFileName;
  }

  public String getPlayerListFilePath() {
    return rootPath + playerListFileName;
  }

  public String getExportFilePath() {
    long timestamp = System.currentTimeMillis();
    String formated = format.format(new Date(timestamp));
    String fileName = "export-" + formated;
    String result = rootPath + fileName;
    return result;
  }


  @PostConstruct
  private void setRootPath() {
    String s = getPath();
    rootPath = s;
  }

  private String getPath() {
    String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    if (System.getProperty("os.name").contains("dows")) {
      path = path.substring(1, path.length());
    }
    if (path.contains("jar")) {
      log.info("contain jar : {}",path);
      path = path.substring(0, path.lastIndexOf("."));
      log.info("substring(0, path.lastIndexOf(.)) : {}",path);
      path =  path.substring(0, path.lastIndexOf("/")+1);
      return path.substring(path.indexOf(":")+1);
    }
    return path.replace("target/classes/", "");
  }
}
