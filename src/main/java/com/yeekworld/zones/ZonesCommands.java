package com.yeekworld.zones;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class ZonesCommands {
    private static final SimpleCommandExceptionType ZONE_EXISTS = new SimpleCommandExceptionType(Text.literal("Zone with same name already exists!"));
    private static final SimpleCommandExceptionType ZONE_DOESNT_EXIST = new SimpleCommandExceptionType(Text.literal("Zone with this name doesn't exist!"));
    private static final SimpleCommandExceptionType WORLD_DOESNT_EXIST = new SimpleCommandExceptionType(Text.literal("World with this name doesn't exist!"));

    public static int rootCommand(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(() -> Text.literal("==============Zones API==============\nver: ").formatted(Formatting.AQUA)
                        .append(Text.literal(Utils.getModVersion(Zones.MOD_ID)).formatted(Formatting.ITALIC, Formatting.BLUE))
                        .append("\n====================================").formatted(Formatting.AQUA)
        , false);

        return 1;
    }

    public static int createZone(CommandContext<ServerCommandSource> context,
                                 String zoneName,
                                 int x1, int y1, int z1,
                                 int x2, int y2, int z2) throws CommandSyntaxException {

        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);
        if (Zones.ZONES.getZone(zoneId) != null) throw ZONE_EXISTS.create();
        BlockPos pos1 = new BlockPos(x1, y1, z1);
        BlockPos pos2 = new BlockPos(x2, y2, z2);
        Zone zone = new Zone(pos1, pos2, context.getSource().getWorld());

        Zones.ZONES.addZone(zoneId, zone);
        context.getSource().sendFeedback(() -> Text.literal("Zone created: ").formatted(Formatting.AQUA)
                        .append(Text.literal("%s".formatted(zoneName)).formatted(Formatting.ITALIC, Formatting.AQUA)),
                false);

        return 1;
    }

    public static int createZone(CommandContext<ServerCommandSource> context,
                                 String zoneName,
                                 int x1, int y1, int z1,
                                 int x2, int y2, int z2,
                                 String worldId) throws CommandSyntaxException {

        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);

        RegistryKey<World> key;
        switch (worldId) {

            case "overworld" -> key = World.OVERWORLD;
            case "the_nether" -> key = World.NETHER;
            case "the_end" -> key = World.END;
            default -> throw WORLD_DOESNT_EXIST.create();
        }
        ServerWorld world = Zones.SERVER.getWorld(key);
        assert world != null;

        if (Zones.ZONES.getZone(zoneId) != null) throw ZONE_EXISTS.create();
        BlockPos pos1 = new BlockPos(x1, y1, z1);
        BlockPos pos2 = new BlockPos(x2, y2, z2);
        Zone zone = new Zone(pos1, pos2, world);

        Zones.ZONES.addZone(zoneId, zone);
        context.getSource().sendFeedback(() -> Text.literal("Zone created: ").formatted(Formatting.AQUA)
                .append(Text.literal("%s".formatted(zoneName)).formatted(Formatting.ITALIC, Formatting.AQUA))
                .append(Text.literal("in")).formatted(Formatting.AQUA)
                .append(Text.literal("%s".formatted(worldId)).formatted(Formatting.ITALIC, Formatting.AQUA)),
                false);
        return 1;
    }

    public static int removeZone(CommandContext<ServerCommandSource> context, String zoneName) throws CommandSyntaxException {
        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);

        if (Zones.ZONES.getZone(zoneId) == null) throw ZONE_DOESNT_EXIST.create();

        context.getSource().sendFeedback(() -> Text.literal("Zone removed: ").formatted(Formatting.AQUA)
                .append(Text.literal("%s".formatted(zoneName)).formatted(Formatting.ITALIC, Formatting.AQUA)),
                false);

        Zones.ZONES.removeZone(zoneId);

        return 1;
    }

    public static int getZones(CommandContext<ServerCommandSource> context) {
        Map<Identifier, Zone> zones = Zones.ZONES.getZones();

        if (zones.isEmpty()) {
            context.getSource().sendFeedback(() -> Text.literal(
                    "Zone list is empty"
            ).formatted(Formatting.AQUA), false);

            return 0;
        }

        context.getSource().sendFeedback(() -> Text.literal("======= Zone list: =======").formatted(Formatting.BLUE), false);

        zones.forEach((zoneId, zone) -> context.getSource().sendFeedback(() -> Text.literal(
                String.format("%s: from %d %d %d to %d %d %d in %s",
                        zoneId.getPath(),
                        zone.pos1.getX(),
                        zone.pos1.getY(),
                        zone.pos1.getZ(),
                        zone.pos2.getX(),
                        zone.pos2.getY(),
                        zone.pos2.getZ(),
                        zone.zoneWorld.getValue().toString()
                        )
        ).formatted(Formatting.AQUA), false));

        context.getSource().sendFeedback(() -> Text.literal("=======================").formatted(Formatting.BLUE), false);

        return 1;
    }

    public static int checkZone(CommandContext<ServerCommandSource> context, String zoneName, int x, int y, int z) throws CommandSyntaxException {
        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);
        Zone zone = Zones.ZONES.getZone(zoneId);
        if (zone == null) throw ZONE_DOESNT_EXIST.create();
        BlockPos pos = new BlockPos(x, y, z);
        if (zone.contains(pos, context.getSource().getWorld())) {
            context.getSource().sendFeedback(() -> Text.literal("true").formatted(Formatting.AQUA), false);
            return 1;
        }
        context.getSource().sendFeedback(() -> Text.literal("false").formatted(Formatting.AQUA), false);
        return 0;
    }

    public static int checkZone(CommandContext<ServerCommandSource> context, String zoneName, int x, int y, int z, String worldId) throws CommandSyntaxException {
        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);
        Zone zone = Zones.ZONES.getZone(zoneId);
        if (zone == null) throw ZONE_DOESNT_EXIST.create();

        RegistryKey<World> key;
        switch (worldId) {

            case "overworld" -> key = World.OVERWORLD;
            case "the_nether" -> key = World.NETHER;
            case "the_end" -> key = World.END;
            default -> throw WORLD_DOESNT_EXIST.create();
        }
        ServerWorld world = Zones.SERVER.getWorld(key);
        assert world != null;

        BlockPos pos = new BlockPos(x, y, z);
        if (zone.contains(pos, world)) {
            context.getSource().sendFeedback(() -> Text.literal("true").formatted(Formatting.AQUA), false);
            return 1;
        }
        context.getSource().sendFeedback(() -> Text.literal("false").formatted(Formatting.AQUA), false);
        return 0;
    }

    public static int checkZone(CommandContext<ServerCommandSource> context, String zoneName, Entity entity) throws CommandSyntaxException {
        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);
        Zone zone = Zones.ZONES.getZone(zoneId);
        if (zone == null) throw ZONE_DOESNT_EXIST.create();

        if (zone.contains(entity.getBlockPos(), entity.getWorld())) {
            context.getSource().sendFeedback(() -> Text.literal("true").formatted(Formatting.AQUA), false);
            return 1;
        }
        context.getSource().sendFeedback(() -> Text.literal("false").formatted(Formatting.AQUA), false);
        return 0;
    }

    public static final SuggestionProvider<ServerCommandSource> SUGGEST_WORLD_NAMES = (context, builder) -> {
        MinecraftServer server = Zones.SERVER;

        for (ServerWorld world : server.getWorlds()) {
            String id = world.getRegistryKey().getValue().toString().replace("minecraft:", "");
            builder.suggest(id);
        }

        return builder.buildFuture();
    };

    public static final SuggestionProvider<ServerCommandSource> SUGGEST_ZONES = (context, buider) -> {

        Zones.ZONES.getZones().forEach((zoneId, zone) -> buider.suggest(zoneId.getPath()));

        return buider.buildFuture();
    };
}
