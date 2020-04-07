package com.kbja.bestmanager.data;

import com.kbja.bestmanager.data.bean.Player;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by yiming.zhou on 2020/4/7
 **/
public interface Exporter {

  String export(List<Player> players);
}
