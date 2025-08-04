package yeekworld.zones;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import yeekworld.zones.api.ZonesAPI;


import java.util.ArrayList;
import java.util.List;

public class ZonesImplementation implements ZonesAPI {

    @Override
    public boolean isInZone(ServerWorld world, BlockPos pos, Identifier zoneId) {
        Zone zone = Zones.ZONES.getZone(zoneId);
        return zone != null && zone.contains(pos, world);
    }

    @Override
    public boolean isInZone(Entity entity, Identifier zoneId) {
        Zone zone = Zones.ZONES.getZone(zoneId);

        World world = entity.getWorld();
        BlockPos pos = entity.getBlockPos();

        return zone != null && zone.contains(pos, world);
    }

    @Override
    public List<Identifier> getZoneWhereIn(ServerWorld world, BlockPos pos) {

        List<Identifier> res = new ArrayList<>();

        Zones.ZONES.getZones().forEach((zoneId, zone) -> {
            if (zone.contains(pos, world)) res.add(zoneId);
        });

        return res;
    }

    @Override
    public List<Identifier> getZoneWhereIn(Entity entity) {
        World world = entity.getWorld();
        BlockPos pos = entity.getBlockPos();

        List<Identifier> res = new ArrayList<>();

        Zones.ZONES.getZones().forEach((zoneId, zone) -> {
            if (zone.contains(pos, world)) res.add(zoneId);
        });

        return res;
    }

    @Override
    public Identifier createZone(String zoneName, BlockPos pos1, BlockPos pos2, ServerWorld world) {
        Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);
        Zone zone = new Zone(pos1, pos2, world);

        Zones.ZONES.addZone(zoneId, zone);

        return zoneId;
    }

    @Override
    public void removeZone(Identifier zoneId) {
        Zones.ZONES.removeZone(zoneId);
    }
}
