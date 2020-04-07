package com.kbja.bestmanager.data;

import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import java.util.Collection;
import java.util.List;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
public interface DataMapper extends Exporter {

  /**
   * 从球员池中，根据球员级别和位置查询球员
   *
   * @param level 球员级别
   * @param positions 球员位置
   * @param status 球员位于球队中，还是球员池中。
   */
  List<Player> getPlayers(PlayerLevel level, Collection<PlayerPosition> positions,
      PlayerStatus status);

  /**
   * 保存所有球员信息
   *
   * @param players 球员信息
   */
  void savePlayers(Collection<Player> players);


  /**
   * 向球员池中放入球员
   */
  void deposit(Integer id);

  /**
   * 从球员池中取出球员
   */
  Player withdraw(Integer id);

  /**
   * 按照球员id，获取球员信息
   */
  Player getPlayer(Integer id);

  /**
   * 清除缓存
   */
  void reload();
}
