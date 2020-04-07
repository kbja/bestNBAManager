package com.kbja.bestmanager.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
@Slf4j
public class CsvUtil {
  private static final String CHARSET_NAME = "GB2312";

  /**
   * 需要调用方负责关闭输出流, 适合多长调用
   */
  public static void writeCsvFile(CSVPrinter csvPrinter,
      List<List<String>> list) throws IOException {

    if (list != null) {
      printList(list, csvPrinter);
    }
  }

  public static void writeCsvFile(String filePath, String[] fileHeaders,
      List<List<String>> list) {
    File file = new File(filePath);
    if (file.exists()) {
      writeCsvFile(filePath, null, list, true);
    } else {
      writeCsvFile(filePath, fileHeaders, list, false);
    }
  }

  public static void writeCsvFile(String filePath, String[] fileHeaders,
      List<List<String>> list, boolean append) {
    CSVFormat csvFileFormat;
    if (fileHeaders == null) {
      csvFileFormat = CSVFormat.DEFAULT;
    } else {
      csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaders);
    }

    try (FileOutputStream fileOutputStream = new FileOutputStream(filePath, append);
        BufferedWriter bufferedWriter = new BufferedWriter(
            new OutputStreamWriter(fileOutputStream, CHARSET_NAME));
        CSVPrinter csvPrinter = new CSVPrinter(bufferedWriter, csvFileFormat)
    ) {

      if (list != null) {
        printList(list, csvPrinter);
      }
    } catch (IOException ie) {
      log.error("write csv file:{} ,Error:", filePath, ie);
    }
  }

  /**
   * 读取csv文件 传参数 文件 表头 从第几行开始
   */
  public static List<List<String>> readCsvFile(String filePath, String[] fileHeaders, Integer num) {
    List<List<String>> list = null;
    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaders);
    try (
        BufferedReader br = new BufferedReader(
            new InputStreamReader(new FileInputStream(filePath), CHARSET_NAME));//解决乱码问题
        // 初始化 CSVParser object
        CSVParser csvFileParser = new CSVParser(br, csvFileFormat)
    ) {
      List<CSVRecord> csvRecords = csvFileParser.getRecords();
      List<String> data;
      list = new ArrayList();
      for (int i = num; i < csvRecords.size(); i++) {
        CSVRecord record = csvRecords.get(i);
        data = new ArrayList();
        for (int j = 0; j < fileHeaders.length; j++) {
          data.add(record.get(fileHeaders[j]));
        }
        list.add(data);
      }
    } catch (IOException ie) {
      log.error("write csv file:{} ,Error:", filePath, ie);
    }
    return list;
  }

  /**
   * 读取csv文件 并封装为Map
   */
  public static List<Map<String,Object>> readCsvDataToMap(String filePath, String[] fileHeaders) {
    List<List<String>> sourceList = readCsvFile(filePath, fileHeaders, 1);
    List<Map<String,Object>> lm = new ArrayList<>();
    if(null != sourceList && sourceList.size() > 0){
      for(List l : sourceList){
        Map<String,Object> map = new HashMap<>();
        for(int i=0;i<fileHeaders.length;i++){
          map.put(fileHeaders[i],l.get(i));
        }
        lm.add(map);
      }
    }
    return lm;
  }

  private static void printList(List<List<String>> list, CSVPrinter csvPrinter)
      throws IOException {
    List<String> ls;
    for (int i = 0; i < list.size(); i++) {
      ls = list.get(i);
      for (int j = 0; j < ls.size(); j++) {
        csvPrinter.print(ls.get(j));
      }
      csvPrinter.println();// 换行
    }
  }

}
