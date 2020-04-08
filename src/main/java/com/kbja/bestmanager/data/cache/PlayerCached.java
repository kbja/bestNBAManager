package com.kbja.bestmanager.data.cache;

import com.kbja.bestmanager.data.DataMapper;
import com.kbja.bestmanager.data.Exporter;
import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
@Component
public class PlayerCached implements DataMapper {

  private Map<Integer, Player> idMap = new ConcurrentHashMap<>(512);
  @Autowired
  @Qualifier("csvDataMapper")
  private DataMapper realDataMapper;
  @Autowired
  private Exporter excelExporter;

  @Override
  public List<Player> getPlayers(PlayerLevel level, Collection<PlayerPosition> positions,
      PlayerStatus status, String playerName) {
    initData();
    Stream<Player> all = idMap.values().stream();
    if (level != null) {
      all = all.filter(p -> level == p.getLevel());
    }
    if (!CollectionUtils.isEmpty(positions)) {
      all = all.filter(p -> positions.contains(p.getPosition()));
    }
    if (status != null) {
      all = all.filter(p -> p.getPlayerStatus() == status);
    }
    if(playerName!=null){
      all = all.filter(player -> player.getName().contains(playerName));
    }
    return all.collect(Collectors.toList());
  }

  @Override
  public void savePlayers(Collection<Player> players) {
    if (idMap.size() != 0) {
      realDataMapper.savePlayers(idMap.values());
    }
  }

  @Override
  public void deposit(Integer id) {
    initData();
    Player player = idMap.get(id);
    player.setPlayerStatus(PlayerStatus.inpool);
  }

  @Override
  public Player withdraw(Integer id) {
    initData();
    Player player = idMap.get(id);
    player.setPlayerStatus(PlayerStatus.inteam);
    return player;
  }

  @Override
  public Player getPlayer(Integer id) {
    initData();
    return idMap.get(id);
  }

  @Override
  public void reload() {
    idMap.clear();
    initData();
  }

  @Override
  public String export(List<Player> players) {
    return excelExporter.export(players);
  }

  private void initData() {
    if (CollectionUtils.isEmpty(idMap)) {
      loadDataFromDataSource();
    }
  }

  private void loadDataFromDataSource() {
    List<Player> players = realDataMapper.getPlayers(null, null, null, null);
    if (!CollectionUtils.isEmpty(players)) {
      idMap = players.stream().collect(Collectors.toMap(Player::getId, a -> a));
    }
  }

}
