package com.trainer;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URI;
import javax.annotation.Nullable;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.StatChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(name = "Trainer")
public class TrainerPlugin extends Plugin {

  private static final URI SOCKET_URI = URI.create("http://localhost:4000");
  private static final IO.Options SOCKET_OPTIONS = IO.Options.builder().build();

  @Inject private Client client;

  private final Socket socket = IO.socket(SOCKET_URI, SOCKET_OPTIONS);

  @Override
  protected void startUp() {
    socket.connect();
  }

  @Override
  protected void shutDown() {
    socket.disconnect();
  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged gameStateChanged) {}

  @Subscribe
  public void onStatChanged(StatChanged event) {
    Player player = client.getLocalPlayer();
    socket.emit("experience", player.getName(), event.getSkill().getName(), event.getXp());
  }

  @Subscribe
  public void onHitsplatApplied(HitsplatApplied event) {
    log.info(
        "Hitsplat recorded for player '{}', type '{}', amount '{}'",
        event.getActor().getName(),
        event.getHitsplat().getHitsplatType(),
        event.getHitsplat().getAmount());

    @Nullable Actor target = client.getLocalPlayer().getInteracting();
    if (target != null) {
      log.info("Currently interacting with '{}'.", target.getName());
    }
  }
}
