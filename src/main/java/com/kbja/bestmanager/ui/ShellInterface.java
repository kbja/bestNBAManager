package com.kbja.bestmanager.ui;

import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.operator.Operator;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Created by yiming.zhou on 2020/4/3
 **/
@ShellComponent
public class ShellInterface implements Operator {

  @Autowired
  private Operator operatorService;

  @Override
  @ShellMethod(key = {"levelUp"}, value = "升级球员")
  public Player levelUp(@ShellOption({"--id"}) Integer playerId,
      @ShellOption(value = "--pos", defaultValue = ShellOption.NULL) Collection<PlayerPosition> positions) {
    return operatorService.levelUp(playerId, positions);
  }

  @Override
  @ShellMethod(key = {"list"}, value = "获取球员信息，不会从球员池中取出,")
  public List<Player> getPlayers(
      @ShellOption(value = "--level", defaultValue = ShellOption.NULL) PlayerLevel level,
      @ShellOption(value = "--pos", defaultValue = ShellOption.NULL) Collection<PlayerPosition> positions,
      @ShellOption(value = "--status", defaultValue = ShellOption.NULL) PlayerStatus status,
      @ShellOption(value = "--name",defaultValue = ShellOption.NULL) String playerName) {
    return operatorService.getPlayers(level, positions, status, playerName);
  }

  @Override
  @ShellMethod(key = {"saveAll"}, value = "保存所有球员信息（写入到文件）")
  public void savePlayers(
      @ShellOption(defaultValue = ShellOption.NULL) Collection<Player> players) {
    operatorService.savePlayers(players);
  }

  @Override
  @ShellMethod(key = {"randomPick"}, value = "随机选择一个球员")
  public Player randomPick(PlayerLevel level,
      @ShellOption(value = "--pos", defaultValue = ShellOption.NULL) Collection<PlayerPosition> positions) {
    return operatorService.randomPick(level, positions);
  }

  @Override
  @ShellMethod(key = {"deposit"}, value = "向球员池中放入id为传入id的球员")
  public void deposit(Integer id) {
    operatorService.deposit(id);
  }

  @Override
  @ShellMethod(key = {"withdraw"}, value = "从球员池中取出一个id为传入id的球员")
  public Player withdraw(Integer id) {
    return operatorService.withdraw(id);
  }

  @Override
  @ShellMethod(key = {"get"}, value = "按照id，获取单个球员信息")
  public Player getPlayer(Integer id) {
    return operatorService.getPlayer(id);
  }

  @Override
  @ShellMethod(key = {"reload"}, value = "清除缓存")
  public void reload() {
    operatorService.reload();
  }

  @Override
  public String export(List<Player> players) {
    return null;
  }

  @Override
  @ShellMethod(key = {"export"}, value = "将球员信息导出")
  public String export(
      @ShellOption(value = "--level", defaultValue = ShellOption.NULL) PlayerLevel level,
      @ShellOption(value = "--pos", defaultValue = ShellOption.NULL) Collection<PlayerPosition> positions,
      @ShellOption(value = "--status", defaultValue = ShellOption.NULL) PlayerStatus status) {
    String filePath = operatorService.export(level, positions, status);
    String result;
    if (filePath == null) {
      result = "导出文件错误";
    } else {
      result = "导出文件路径为 ： " + filePath;
    }
    return result;

  }
}
