package yeekworld.zones.api;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface ZonesAPI {
    boolean isInZone(ServerWorld world, BlockPos pos, Identifier zoneId);

    boolean isInZone(Entity entity, Identifier zoneId);

    List<Identifier> getZoneWhereIn(ServerWorld world, BlockPos pos);

    List<Identifier> getZoneWhereIn(Entity entity);

    Identifier createZone(String zoneName, BlockPos pos1, BlockPos pos2, ServerWorld world);

    void removeZone(Identifier zoneId);
}
