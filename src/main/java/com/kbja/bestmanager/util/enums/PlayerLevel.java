package com.kbja.bestmanager.util.enums;

/**
 * Created by yiming.zhou on 2020/4/2
 **/
public enum PlayerLevel {
  red,yellow,pink,blue,green;

  public static PlayerLevel levelUp(PlayerLevel current){
    switch (current) {
      case red:
        throw new IllegalArgumentException("red level can not level up");
      case yellow:
        return red;
      case pink:
        return yellow;
      case blue:
        return pink;
      case green:
        return blue;
      default:
        return null;
    }
  }
}
