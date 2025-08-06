package com.yeekworld.zones;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.ArrayList;
import java.util.List;

public class ZonesImplementation{

    public static boolean isInZone(Object world, Object pos, Object zoneId) {
        if (world instanceof ServerWorld serverWorld && pos instanceof BlockPos bPos && zoneId instanceof Identifier id) {
            Zone zone = Zones.ZONES.getZone(id);

            if (zone == null) return false;
            return zone.contains(bPos, serverWorld);
        }
        throw new IllegalArgumentException();
    }

    public static boolean isInZone(Object entity, Object zoneId) {
        if (entity instanceof Entity mcEntity && zoneId instanceof Identifier id) {
            Zone zone = Zones.ZONES.getZone(id);

            World world = mcEntity.getWorld();
            BlockPos pos = mcEntity.getBlockPos();

            if (zone == null) return false;
            return zone.contains(pos, world);
        }
        throw new IllegalArgumentException();
    }

    public static List<Object> getZoneWhereIn(Object world, Object pos) {
        if (world instanceof ServerWorld serverWorld && pos instanceof BlockPos bPos) {
            List<Object> res = new ArrayList<>();

            Zones.ZONES.getZones().forEach((zoneId, zone) -> {
                if (zone.contains(bPos, serverWorld)) res.add(zoneId);
            });

            return res;
        }
        throw new IllegalArgumentException();
    }

    public static List<Object> getZoneWhereIn(Object entity) {
        if (entity instanceof Entity mcEntity) {
            World world = mcEntity.getWorld();
            BlockPos pos = mcEntity.getBlockPos();

            List<Object> res = new ArrayList<>();

            Zones.ZONES.getZones().forEach((zoneId, zone) -> {
                if (zone.contains(pos, world)) res.add(zoneId);
            });

            return res;
        }
        throw new IllegalArgumentException();
    }

    public static Object createZone(String zoneName, Object pos1, Object pos2, Object world) {
        if (pos1 instanceof BlockPos bPos1 && pos2 instanceof BlockPos bPos2 && world instanceof ServerWorld serverWorld){
            Identifier zoneId = Identifier.of(Zones.MOD_ID, zoneName);
            Zone zone = new Zone(bPos1, bPos2, serverWorld);

            Zones.ZONES.addZone(zoneId, zone);

            return zoneId;
        }
        throw new IllegalArgumentException();
    }

    public static void removeZone(Object zoneId) {
        if (zoneId instanceof Identifier id) {
            Zones.ZONES.removeZone(id);
        }
        throw new IllegalArgumentException();
    }
}
