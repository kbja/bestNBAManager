package com.kbja.bestmanager.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Created by yiming.zhou on 2020/4/3
 **/
class CsvUtilTest {

  @Test
  void writeCsvFile() {
  }

  @Test
  void testWriteCsvFile() {
  }

  @Test
  void testWriteCsvFile1() {
  }

  @Test
  void readCsvFile() {
    String[] headers = {"id","name","ability","pos","height","status","level","team"};
    List<Map<String,Object>> data = null;
    try {
      data = CsvUtil.readCsvDataToMap("/Users/zhou/Desktop/playerList.csv",headers);
    } catch (Exception e) {
      e.printStackTrace();
    }

    data.stream();
  }

  @Test
  void readCsvDataToMap() {
  }
}