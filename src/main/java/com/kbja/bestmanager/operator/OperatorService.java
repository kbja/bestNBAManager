package com.kbja.bestmanager.operator;

import com.kbja.bestmanager.data.DataMapper;
import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import com.kbja.bestmanager.util.exception.IdNotExistsException;
import com.kbja.bestmanager.util.exception.PlayerNotFoundException;
import com.kbja.bestmanager.util.exception.PlayerStatusException;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by yiming.zhou on 2020/4/3
 **/
@Component
public class OperatorService implements Operator {

  @Autowired
  private DataMapper playerCached;

  @Override
  public Player levelUp(Integer playerId, Collection<PlayerPosition> positions) {
    Player drop = getPlayer(playerId);
    if (drop == null) {
      throw new PlayerNotFoundException();
    }
    PlayerLevel nextLevel = PlayerLevel.levelUp(drop.getLevel());
    Player pick = randomPick(nextLevel, positions);

    deposit(drop.getId());
    return pick;
  }

  @Override
  public List<Player> getPlayers(PlayerLevel level, Collection<PlayerPosition> positions,
      PlayerStatus status) {
    return playerCached.getPlayers(level, positions, status);
  }

  @Override
  public void savePlayers(Collection<Player> players) {
    playerCached.savePlayers(players);
  }

  @Override
  public Player randomPick(PlayerLevel level, Collection<PlayerPosition> positions) {
    List<Player> players = playerCached.getPlayers(level, positions, PlayerStatus.inpool);
    int random = random(players.size());
    Player pick = players.get(random);
    withdraw(pick.getId());
    return pick;
  }

  @Override
  public void deposit(Integer id) {
    Player player = playerCached.getPlayer(id);
    if (player == null) {
      throw new IdNotExistsException(" id : " + id + " not exists");
    }
    if(player.getPlayerStatus()!=PlayerStatus.inteam){
      throw new PlayerStatusException(" name : "+player.getName()+" status is not inteam");
    }
    playerCached.deposit(id);
  }

  @Override
  public Player withdraw(Integer id) {
    Player player = playerCached.getPlayer(id);
    if (player == null) {
      throw new IdNotExistsException(" id : " + id + " not exists");
    }
    if(player.getPlayerStatus()!=PlayerStatus.inpool){
      throw new PlayerStatusException(" name : "+player.getName()+" status is not inpoll");
    }
    return playerCached.withdraw(id);
  }

  @Override
  public Player getPlayer(Integer id) {
    return playerCached.getPlayer(id);
  }

  @Override
  public void reload() {
    playerCached.reload();
  }

  @Override
  public String export(List<Player> players) {
    return null;
  }

  @Override
  public String export(PlayerLevel level, Collection<PlayerPosition> positions, PlayerStatus status) {
    List<Player> players = getPlayers(level,positions,status);
    return playerCached.export(players);
  }

  private int random(int i) {
    int result = (int) (System.currentTimeMillis() % i);
    return Math.abs(result);
  }
}
