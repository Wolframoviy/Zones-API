package yeekworld.zones;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class Zones implements ModInitializer {

    public static final String MOD_NAME = "Zones API";
    public static final String MOD_ID = MOD_NAME.toLowerCase().replace(" ", "_");
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MinecraftServer SERVER;
    public static ZonesStorage ZONES;

    @Override
    public void onInitialize() {
        LOGGER.info("Zones API initialized!");

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            SERVER = server;
            ZONES = ZonesStorage.getZoneStorage(SERVER);
        });

        CommandRegistrationCallback.EVENT.register((dispatcher,
                                                    registryAccess,
                                                    environment) -> {
            dispatcher.register(literal("zones")
                    .executes(ZonesCommands::rootCommand)
                    .then(literal("create")
                            .then(argument("zone name", StringArgumentType.string())
                            .then(argument("x1", IntegerArgumentType.integer())
                            .then(argument("y1", IntegerArgumentType.integer())
                            .then(argument("z1", IntegerArgumentType.integer())
                            .then(argument("x2", IntegerArgumentType.integer())
                            .then(argument("y2", IntegerArgumentType.integer())
                            .then(argument("z2", IntegerArgumentType.integer())
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> ZonesCommands.createZone(
                                    context,
                                    StringArgumentType.getString(context, "zone name"),
                                    IntegerArgumentType.getInteger(context, "x1"),
                                    IntegerArgumentType.getInteger(context, "y1"),
                                    IntegerArgumentType.getInteger(context, "z1"),
                                    IntegerArgumentType.getInteger(context, "x2"),
                                    IntegerArgumentType.getInteger(context, "y2"),
                                    IntegerArgumentType.getInteger(context, "z2")
                                )))))))))
                    )
                    .then(literal("create")
                            .then(argument("zone name", StringArgumentType.string())
                            .then(argument("x1", IntegerArgumentType.integer())
                            .then(argument("y1", IntegerArgumentType.integer())
                            .then(argument("z1", IntegerArgumentType.integer())
                            .then(argument("x2", IntegerArgumentType.integer())
                            .then(argument("y2", IntegerArgumentType.integer())
                            .then(argument("z2", IntegerArgumentType.integer())
                            .then(argument("world", StringArgumentType.string()).suggests(ZonesCommands.SUGGEST_WORLD_NAMES)
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> ZonesCommands.createZone(
                                    context,
                                    StringArgumentType.getString(context, "zone name"),
                                    IntegerArgumentType.getInteger(context, "x1"),
                                    IntegerArgumentType.getInteger(context, "y1"),
                                    IntegerArgumentType.getInteger(context, "z1"),
                                    IntegerArgumentType.getInteger(context, "x2"),
                                    IntegerArgumentType.getInteger(context, "y2"),
                                    IntegerArgumentType.getInteger(context, "z2"),
                                    StringArgumentType.getString(context, "world")
                            ))))))))))
                    )
                    .then(literal("remove")
                            .then(argument("zone name", StringArgumentType.string()).suggests(ZonesCommands.SUGGEST_ZONES)
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(context -> ZonesCommands.removeZone(
                                    context,
                                    StringArgumentType.getString(context, "zone name")
                            ))))
                    .then(literal("list")
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(ZonesCommands::getZones))
            );
        });
    }
}
