package com.trainer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("trainer")
public interface TrainerConfig extends Config {

  @ConfigItem(
      keyName = "greeting",
      name = "Welcome Greeting",
      description = "The message to show to the user when they login")
  default String greeting() {
    return "Hello";
  }
}
