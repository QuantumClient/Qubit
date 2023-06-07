package org.quantumclient.qubit.utils;

import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Wrapper {

    Logger LOGGER = LogManager.getLogger();

    MinecraftClient mc = MinecraftClient.getInstance();

}
