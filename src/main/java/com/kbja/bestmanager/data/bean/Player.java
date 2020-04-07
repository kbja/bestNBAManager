package com.kbja.bestmanager.data.bean;

import com.kbja.bestmanager.util.enums.PlayerLevel;
import com.kbja.bestmanager.util.enums.PlayerPosition;
import com.kbja.bestmanager.util.enums.PlayerStatus;
import lombok.Data;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
@Data
public class Player {
  private Integer id;
  private Integer totalAbility;
  private String name;
  private String team;
  private String height;
  private Integer threePointerAbility;
  private Integer dunkAbility;
  private PlayerLevel level;
  private PlayerPosition position;
  private PlayerStatus playerStatus;
  private String belongTo;
}
