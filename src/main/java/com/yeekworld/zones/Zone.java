package com.yeekworld.zones;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Zone {
    public final BlockPos pos1;
    public final BlockPos pos2;
    public final RegistryKey<World> zoneWorld;

    public Zone(BlockPos pos1, BlockPos pos2, World world){
        this.pos1 = new BlockPos(
                Math.min(pos1.getX(), pos2.getX()),
                Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ())
                );
        this.pos2 = new BlockPos(
                Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()),
                Math.max(pos1.getZ(), pos2.getZ())
        );
        this.zoneWorld = world.getRegistryKey();
    }

    private Zone(BlockPos pos1, BlockPos pos2, RegistryKey<World> world){
        this.pos1 = new BlockPos(
                Math.min(pos1.getX(), pos2.getX()),
                Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ())
        );
        this.pos2 = new BlockPos(
                Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()),
                Math.max(pos1.getZ(), pos2.getZ())
        );
        this.zoneWorld = world;
    }

    public boolean contains(BlockPos pos, World world){
        return pos.getX() >= pos1.getX() && pos.getY() >= pos1.getY() && pos.getZ() >= pos1.getZ()
                && pos.getX() <= pos2.getX() && pos.getY() <= pos2.getY() && pos.getZ() <= pos2.getZ()
                && world.getRegistryKey() == zoneWorld;
    }

    public static final Codec<RegistryKey<World>> WORLD_CODEC = RegistryKey.createCodec(RegistryKeys.WORLD);

    public static final Codec<Zone> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockPos.CODEC.fieldOf("pos1").forGetter(zone -> zone.pos1),
            BlockPos.CODEC.fieldOf("pos2").forGetter(zone -> zone.pos2),
            WORLD_CODEC.fieldOf("world").forGetter(zone -> zone.zoneWorld)
    ).apply(instance, Zone::new));
}
