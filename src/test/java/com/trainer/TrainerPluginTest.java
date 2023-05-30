package com.trainer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class TrainerPluginTest {

  public static void main(String[] args) throws Exception {
    ExternalPluginManager.loadBuiltin(TrainerPlugin.class);
    RuneLite.main(args);
  }
}
