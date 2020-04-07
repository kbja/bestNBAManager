package com.kbja.bestmanager.operator;

import com.kbja.bestmanager.data.DataMapper;
import com.kbja.bestmanager.data.bean.Player;
import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import java.util.Collection;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
public interface Operator extends DataMapper {

  Player levelUp(Integer playerId, Collection<PlayerPosition> positions);

  Player randomPick(PlayerLevel level, Collection<PlayerPosition> positions);

  String export(PlayerLevel level, Collection<PlayerPosition> positions, PlayerStatus status);

}
