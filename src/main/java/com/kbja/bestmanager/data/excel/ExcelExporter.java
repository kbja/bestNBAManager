package com.kbja.bestmanager.data.excel;

import com.kbja.bestmanager.CommonConfiguration;
import com.kbja.bestmanager.data.Exporter;
import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yiming.zhou on 2020/4/7
 **/
@Component
@Slf4j
public class ExcelExporter implements Exporter {

  @Autowired
  private CommonConfiguration configuration;

  @Override
  public String export(List<Player> players) {
    // Create a workbook object
    Workbook workbook = new XSSFWorkbook();
    // Create sheet
    Sheet sheet = workbook.createSheet();

    PlayerProcessor playerProcessor = new PlayerProcessor(players);
    int max = playerProcessor.getMAX();
    for (int i = 0; i < max; i++) {
      Row row = sheet.createRow(i);
      RowDataProcessor rowDataProcessor = new RowDataProcessor(playerProcessor.getByRow(i),
          workbook);
      rowDataProcessor.buildRow(row);
    }
    String filePath = writeFile(workbook);
    return filePath;
  }

  private String writeFile(Workbook workbook) {
    String exportPath = configuration.getExportFilePath() + ".xlsx";
    try {

      File f = new File(exportPath);
      if (!f.exists()) {
        f.createNewFile();
      }
      FileOutputStream fileOut = new FileOutputStream(f);
      workbook.write(fileOut);
      fileOut.close();
    }
    catch (Exception e){
      log.error(e.getMessage());
      return null;
    }
    return exportPath;
  }

  private class PlayerProcessor {

    @Getter
    private final int MAX;
    private List<Player> redPlayer;
    private List<Player> yellowPlayer;
    private List<Player> pinkPlayer;
    private List<Player> bluePlayer;
    private List<Player> greenPlayer;

    public PlayerProcessor(List<Player> players) {
      Map<PlayerLevel, List<Player>> groupByLevel = players.stream()
          .collect(Collectors.groupingBy(Player::getLevel));
      redPlayer = new ArrayList<>(groupByLevel.get(PlayerLevel.red));
      redPlayer.sort(Comparator.comparingInt(Player::getId));
      yellowPlayer = new ArrayList<>(groupByLevel.get(PlayerLevel.yellow));
      yellowPlayer.sort(Comparator.comparingInt(Player::getId));
      pinkPlayer = new ArrayList<>(groupByLevel.get(PlayerLevel.pink));
      pinkPlayer.sort(Comparator.comparingInt(Player::getId));
      bluePlayer = new ArrayList<>(groupByLevel.get(PlayerLevel.blue));
      bluePlayer.sort(Comparator.comparingInt(Player::getId));
      greenPlayer = new ArrayList<>(groupByLevel.get(PlayerLevel.green));
      greenPlayer.sort(Comparator.comparingInt(Player::getId));
      MAX = greenPlayer.size();
    }

    public List<Player> getByRow(int row) {
      if (row >= MAX) {
        return null;
      }
      List<Player> result = new LinkedList<>();
      result.add(getRed(row));
      result.add(getYellow(row));
      result.add(getPink(row));
      result.add(getBlue(row));
      result.add(getGreen(row));
      return result;
    }

    public Player getRed(int row) {
      if (redPlayer.size() > row) {
        return redPlayer.get(row);
      }
      return null;
    }

    public Player getYellow(int row) {
      if (yellowPlayer.size() > row) {
        return yellowPlayer.get(row);
      }
      return null;
    }

    public Player getPink(int row) {
      if (pinkPlayer.size() > row) {
        return pinkPlayer.get(row);
      }
      return null;
    }

    public Player getBlue(int row) {
      if (bluePlayer.size() > row) {
        return bluePlayer.get(row);
      }
      return null;
    }

    public Player getGreen(int row) {
      if (greenPlayer.size() > row) {
        return greenPlayer.get(row);
      }
      return null;
    }

  }

  private static class StyleProcessor {

    public static CellStyle buildIdCellStyle(CellStyle style, Player player) {
      buildBaseStyle(style);
      switch (player.getLevel()) {
        case red:
          style.setFillForegroundColor(getRedPlayerColor());
          break;
        case yellow:
          style.setFillForegroundColor(getYellowPlayerColor());
          break;
        case pink:
          style.setFillForegroundColor(getPinkPlayerColor());
          break;
        case blue:
          style.setFillForegroundColor(getBluePlayerColor());
          break;
        case green:
          style.setFillForegroundColor(getGreenPlayerColor());
          break;
        default:
          throw new IllegalArgumentException();
      }
      return style;
    }

    public static CellStyle buildContentCellStyle(CellStyle style, Player player) {
      buildBaseStyle(style);
      switch (player.getPlayerStatus()) {
        case inteam:
          style.setFillForegroundColor(getInTeamPlayerColor());
          break;
        case inpool:
          style.setFillForegroundColor(getInPoolPlayerColor());
          break;
        default:
          throw new IllegalArgumentException();
      }
      return style;
    }

    public static CellStyle buildEmptyCellStyle(CellStyle style) {
      CellStyle result = buildBaseStyle(style);
      result.setFillForegroundColor(getEmptyColor());
      return result;
    }

    private static CellStyle buildBaseStyle(CellStyle style) {
      style.setBorderBottom(BorderStyle.THIN); //下边框
      style.setBorderLeft(BorderStyle.THIN);//左边框
      style.setBorderTop(BorderStyle.THIN);//上边框
      style.setBorderRight(BorderStyle.THIN);//右边框
      style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      return style;
    }

    private static short getRedPlayerColor() {
      return IndexedColors.RED.getIndex();
    }

    private static short getYellowPlayerColor() {
      return IndexedColors.YELLOW.getIndex();
    }

    private static short getPinkPlayerColor() {
      return IndexedColors.PINK.getIndex();
    }

    private static short getBluePlayerColor() {
      return IndexedColors.SKY_BLUE.getIndex();
    }

    private static short getGreenPlayerColor() {
      return IndexedColors.BRIGHT_GREEN1.getIndex();
    }

    private static short getInTeamPlayerColor() {
      return IndexedColors.GREY_25_PERCENT.getIndex();
    }

    private static short getInPoolPlayerColor() {
      return IndexedColors.WHITE.getIndex();
    }

    private static short getEmptyColor() {
      return IndexedColors.WHITE.getIndex();
    }
  }

  public static class RowDataProcessor {

    /**
     * 每个级别分为8列 分别为id，name，ability，position，height，belongTo，空行
     */
    private static final int RED_BEGIN_COLUMN = 0;
    private static final int YELLOW_BEGIN_COLUMN = 7;
    private static final int PINK_BEGIN_COLUMN = 14;
    private static final int BLUE_BEGIN_COLUMN = 21;
    private static final int GREEN_BEGIN_COLUMN = 28;

    private List<Player> data;
    private Workbook workbook;

    public RowDataProcessor(List<Player> rowData, Workbook workbook) {
      this.data = rowData;
      this.workbook = workbook;
    }

    public void buildRow(Row row) {
      buildRed(row, data.get(0));
      buildYellow(row, data.get(1));
      buildPink(row, data.get(2));
      buildBlue(row, data.get(3));
      buildGreen(row, data.get(4));
    }

    private void buildRed(Row row, Player player) {
      buildLevel(row, player, RED_BEGIN_COLUMN);
    }


    private void buildYellow(Row row, Player player) {
      buildLevel(row, player, YELLOW_BEGIN_COLUMN);

    }

    private void buildPink(Row row, Player player) {
      buildLevel(row, player, PINK_BEGIN_COLUMN);

    }

    private void buildBlue(Row row, Player player) {
      buildLevel(row, player, BLUE_BEGIN_COLUMN);

    }

    private void buildGreen(Row row, Player player) {
      buildLevel(row, player, GREEN_BEGIN_COLUMN);

    }

    private void buildLevel(Row row, Player player, int beginIndex) {
      Cell idCell = row.createCell(beginIndex++);
      Cell nameCell = row.createCell(beginIndex++);
      Cell abilityCell = row.createCell(beginIndex++);
      Cell positionCell = row.createCell(beginIndex++);
      Cell heightCell = row.createCell(beginIndex++);
      Cell belongToCell = row.createCell(beginIndex++);
      Cell emptyCell = row.createCell(beginIndex);
      CellStyle style = workbook.createCellStyle();
      CellStyle emptyStyle = StyleProcessor.buildEmptyCellStyle(style);
      if (player == null) {
        idCell.setCellStyle(emptyStyle);
        nameCell.setCellStyle(emptyStyle);
        abilityCell.setCellStyle(emptyStyle);
        positionCell.setCellStyle(emptyStyle);
        heightCell.setCellStyle(emptyStyle);
        belongToCell.setCellStyle(emptyStyle);
        emptyCell.setCellStyle(emptyStyle);
      } else {
        style = workbook.createCellStyle();
        CellStyle idCellStyle = StyleProcessor.buildIdCellStyle(style, player);
        style = workbook.createCellStyle();
        CellStyle contentStyle = StyleProcessor.buildContentCellStyle(style, player);
        idCell.setCellStyle(idCellStyle);
        idCell.setCellValue(player.getId());
        nameCell.setCellStyle(contentStyle);
        nameCell.setCellValue(player.getName());
        abilityCell.setCellStyle(contentStyle);
        abilityCell.setCellValue(player.getTotalAbility());
        positionCell.setCellStyle(contentStyle);
        positionCell.setCellValue(player.getPosition().name());
        heightCell.setCellStyle(contentStyle);
        heightCell.setCellValue(player.getHeight());
        belongToCell.setCellStyle(contentStyle);
        belongToCell.setCellValue(player.getBelongTo());
        emptyCell.setCellStyle(emptyStyle);
      }
    }

  }
}
