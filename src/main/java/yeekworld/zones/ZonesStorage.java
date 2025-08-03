package yeekworld.zones;

import com.mojang.serialization.Codec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ZonesStorage extends PersistentState {
    private Map<Identifier, Zone> zones = new HashMap<>();

    private ZonesStorage() {
    }

    private ZonesStorage(Map<Identifier, Zone> zones) {
        this.zones = new HashMap<>(zones);
    }

    public Map<Identifier, Zone> getZones() {
        return zones;
    }

    public void addZone(Identifier zoneId, Zone zone) {
        zones.put(zoneId, zone);
        markDirty();
    }

    public void removeZone(Identifier zoneId) {
        zones.remove(zoneId);
        markDirty();
    }

    public Zone getZone(Identifier zoneId) {
        return zones.get(zoneId);
    }

    private static final Codec<ZonesStorage> CODEC = Codec.unboundedMap(Identifier.CODEC, Zone.CODEC).xmap(ZonesStorage::new, ZonesStorage::getZones);

    private static final PersistentStateType<ZonesStorage> type = new PersistentStateType<>(
            Zones.MOD_ID,
            ZonesStorage::new,
            CODEC,
            null
    );

    public static ZonesStorage getZoneStorage(MinecraftServer server) {
        ServerWorld serverWorld = server.getWorld(World.OVERWORLD);
        assert serverWorld != null;

        ZonesStorage zonesStorage = serverWorld.getPersistentStateManager().getOrCreate(type);

        zonesStorage.markDirty();

        return zonesStorage;
    }
}
