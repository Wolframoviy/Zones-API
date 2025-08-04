package yeekworld.zones.api;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import yeekworld.zones.ZonesImplementation;

import java.util.List;

public class ZonesAPI {
    public static boolean isInZone(ServerWorld world, BlockPos pos, Identifier zoneId) {
        return ZonesImplementation.isInZone(world, pos, zoneId);
    }

    public static boolean isInZone(Entity entity, Identifier zoneId) {
        return ZonesImplementation.isInZone(entity, zoneId);
    }

    public static List<Identifier> getZoneWhereIn(ServerWorld world, BlockPos pos) {
        return ZonesImplementation.getZoneWhereIn(world, pos);
    }

    public static List<Identifier> getZoneWhereIn(Entity entity) {
        return ZonesImplementation.getZoneWhereIn(entity);
    }

    public static Identifier createZone(String zoneName, BlockPos pos1, BlockPos pos2, ServerWorld world) {
        return ZonesImplementation.createZone(zoneName, pos1, pos2, world);
    }

    public static void removeZone(Identifier zoneId) {
        ZonesImplementation.removeZone(zoneId);
    }
}
