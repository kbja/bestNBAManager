package com.kbja.bestmanager.data.csv;

import com.kbja.bestmanager.CommonConfiguration;
import com.kbja.bestmanager.data.DataMapper;
import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.util.CsvUtil;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.jcommander.JCommanderParameterResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
@Component("csvDataMapper")
@Slf4j
public class CSVDataMapper implements DataMapper {

  @Value("${available.header}")
  private String header;
  @Autowired
  private CommonConfiguration configuration;

  @Override
  public List<Player> getPlayers(PlayerLevel level, Collection<PlayerPosition> positions,
      PlayerStatus status) {
    String filePath = configuration.getPlayerListFilePath();

    List<Map<String, Object>> allData;
    File file = new File(filePath);
    if (!file.exists()) {
      String msg = "can not find file [" + filePath
          + "], make true that a file named playerList.csv is in the same directory with jar";
      log.error(msg);
      throw new RuntimeException(msg);
    }
    allData = CsvUtil.readCsvDataToMap(filePath, header.split(","));
    List<Player> playerData = this.csvSourceDataToPlayer(allData);
    Stream<Player> all = playerData.stream();
    if (level != null) {
      all = all.filter(p -> level == p.getLevel());
    }
    if (!CollectionUtils.isEmpty(positions)) {
      all = all.filter(p -> positions.contains(p.getPosition()));
    }
    if (status != null) {
      all = all.filter(p -> p.getPlayerStatus() == status);
    }
    return all.collect(Collectors.toList());
  }

  @Override
  public void savePlayers(Collection<Player> players) {
    String filePath = configuration.getPlayerListFilePath();
    List<List<String>> data = playerToCsvData(players);
    CsvUtil.writeCsvFile(filePath, header.split(","), data, false);
  }

  @Override
  public void deposit(Integer id) {
    throw new RuntimeException("method not allow");
  }

  @Override
  public Player withdraw(Integer id) {
    throw new RuntimeException("method not allow");
  }

  @Override
  public Player getPlayer(Integer id) {
    return null;
  }

  @Override
  public void reload() {

  }

  @Override
  public String export(List<Player> players) {
    String exportFilePath = configuration.getExportFilePath();
    List<List<String>> data = playerToCsvData(players);
    CsvUtil.writeCsvFile(exportFilePath, header.split(","), data, false);
    return exportFilePath;
  }

  private List<Player> csvSourceDataToPlayer(List<Map<String, Object>> data) {
    return data.stream().map(this::csvSourceDataToPlayer).collect(Collectors.toList());
  }

  private Player csvSourceDataToPlayer(Map<String, Object> data) {
    Player player = new Player();
    player.setPlayerStatus(PlayerStatus.valueOf((String) data.get("status")));
    player.setName((String) data.get("name"));
    player.setId(Integer.valueOf((String) data.get("id")));
    player.setPosition(PlayerPosition.valueOf((String) data.get("pos")));
    player.setTotalAbility(Integer.valueOf((String) data.get("ability")));
//    player.setThreePointerAbility((Integer) data.get("three"));
//    player.setDunkAbility((Integer) data.get("dunk"));
    player.setHeight((String) data.get("height"));
    player.setLevel(PlayerLevel.valueOf((String) data.get("level")));
    player.setBelongTo((String) data.get("belongTo"));
    return player;
  }

  private List<List<String>> playerToCsvData(Collection<Player> players) {
    return players.stream().map(this::playerToCsvData).collect(Collectors.toList());
  }

  private List<String> playerToCsvData(Player player) {
    List<String> result = new LinkedList<>();
    result.add(String.valueOf(player.getId()));
    result.add(player.getName());
    result.add(String.valueOf(player.getTotalAbility()));
    result.add(player.getPosition().name());
    result.add(String.valueOf(player.getHeight()));
    result.add(player.getPlayerStatus().name());
    result.add(player.getLevel().name());
//    result.add(player.getBelongTo());
    return result;
  }


}
