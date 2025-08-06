
# Zones API

**Zones API** is a lightweight system for defining and managing custom zones in a Minecraft server world. It provides core methods for creating rectangular areas (zones), checking whether entities or block positions are inside those zones, and removing zones by their identifiers.

This API serves as a foundation for implementing more complex features such as:
- Region-based rules (e.g., PvP restrictions, building limits, protected areas)
- Quest areas or custom interaction zones
- Player or entity tracking by location
- Custom game logic triggered by zone presence

### Features:
- Supports **overlapping zones**
- Simple zone creation using two corner points (`BlockPos`)
- Zone identification via unique `Identifier` objects
- Membership checks based on either **position** or **entity**

Zones API does not implement high-level behaviors on its own — instead, it is intended to be integrated into larger systems or extended as needed. It provides the basic spatial logic upon which other features can be built.





## API Reference

## packager com.yeekworld.zones.ZonesAPI

## `isInZone(ServerWorld world, BlockPos pos, Identifier zoneId)`

Checks whether a specific block position is within a given zone.

| Parameter   | Type          | Description                              |
|-------------|---------------|------------------------------------------|
| `world`     | `ServerWorld` | The world in which the check is performed. |
| `pos`       | `BlockPos`    | The position to check.                   |
| `zoneId`    | `Identifier`  | The unique identifier of the zone.       |

| Returns     | Description                        |
|-------------|------------------------------------|
| `boolean`   | `true` if the position is inside the zone, otherwise `false`. |

---

## `isInZone(Entity entity, Identifier zoneId)`

Checks whether an entity is inside a specific zone.

| Parameter   | Type        | Description                        |
|-------------|-------------|------------------------------------|
| `entity`    | `Entity`    | The entity to check.               |
| `zoneId`    | `Identifier`| The identifier of the target zone. |

| Returns     | Description                        |
|-------------|------------------------------------|
| `boolean`   | `true` if the entity is inside the zone, otherwise `false`. |

---

## `getZoneWhereIn(ServerWorld world, BlockPos pos)`

Gets a list of zone identifiers that the given block position belongs to.

| Parameter   | Type          | Description                      |
|-------------|---------------|----------------------------------|
| `world`     | `ServerWorld` | The world of the position.       |
| `pos`       | `BlockPos`    | The position to check.           |

| Returns     | Description                               |
|-------------|-------------------------------------------|
| `List<Identifier>` | List of all zones containing the position. Empty if none. |

---

## `getZoneWhereIn(Entity entity)`

Gets a list of zone identifiers that the entity is currently inside.

| Parameter   | Type        | Description          |
|-------------|-------------|----------------------|
| `entity`    | `Entity`    | The entity to check. |

| Returns     | Description                             |
|-------------|-----------------------------------------|
| `List<Identifier>` | List of all zones containing the entity. Empty if none. |

---

## `createZone(String zoneName, BlockPos pos1, BlockPos pos2, ServerWorld world)`

Creates a rectangular zone defined by two opposite corners.

| Parameter   | Type          | Description                                 |
|-------------|---------------|---------------------------------------------|
| `zoneName`  | `String`      | The name of the zone (used in the ID).     |
| `pos1`      | `BlockPos`    | One corner of the zone.                    |
| `pos2`      | `BlockPos`    | Opposite corner of the zone.               |
| `world`     | `ServerWorld` | The world where the zone is created.       |

| Returns     | Description                      |
|-------------|----------------------------------|
| `Identifier`| The identifier of the new zone.  |

---

## `removeZone(Identifier zoneId)`

Removes the zone with the given identifier.

| Parameter   | Type        | Description                    |
|-------------|-------------|--------------------------------|
| `zoneId`    | `Identifier`| The ID of the zone to remove.  |

| Returns     | Description     |
|-------------|-----------------|
| `void`      | No return value. |

---

## ℹ️ Notes

- Zones can overlap; an entity or position may belong to multiple zones.
- Zone boundaries are inclusive.
- Identifiers (`Identifier`) should be globally unique.

