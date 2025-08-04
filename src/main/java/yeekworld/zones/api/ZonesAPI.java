package yeekworld.zones.api;

import yeekworld.zones.ZonesImplementation;

import java.util.List;

public class ZonesAPI {
    public static boolean isInZone(Object world, Object pos, Object zoneId) {
        return ZonesImplementation.isInZone(world, pos, zoneId);
    }

    public static boolean isInZone(Object entity, Object zoneId) {
        return ZonesImplementation.isInZone(entity, zoneId);
    }

    public static List<Object> getZoneWhereIn(Object world, Object pos) {
        return ZonesImplementation.getZoneWhereIn(world, pos);
    }

    public static List<Object> getZoneWhereIn(Object entity) {
        return ZonesImplementation.getZoneWhereIn(entity);
    }

    public static Object createZone(String zoneName, Object pos1, Object pos2, Object world) {
        return ZonesImplementation.createZone(zoneName, pos1, pos2, world);
    }

    public static void removeZone(Object zoneId) {
        ZonesImplementation.removeZone(zoneId);
    }
}
