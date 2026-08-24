# CNPC EpicFight Addon — Changelog

## [Final compatibility build] — 2026-08-23

### Fixed
- CNPC equipment is authoritative and is re-synchronized into Epic Fight after attacks, reloads, swaps, patch refreshes, and world/entity reloads.
- Multiple CNPCs and generic mobs can share one NBT-selected patch; matching is universal and updates at runtime.
- CNPC and Epic Fight provider replacement no longer loses the selected armature, held item, living motions, or client animation state.
- NPC-to-NPC Epic Fight melee damage now reaches the normal damage/stun pipeline while CNPC faction relationships remain respected.
- TACZ aiming is sustained through the firing cycle, uses the gun's current cadence, and reloads both with and without a target.
- Skeleton and other mob patch refreshes no longer mutate AI goal lists during iteration and crash the server.

### Verified
- Epic Fight 20.14.17, CustomNPCs 1.20.1.20260711, TACZ 1.1.8-hotfix, Epic Arsenal, and Packet Fixer on Forge 47.4.0 / Minecraft 1.20.1.
- Native held-item Epic Fight living motions, ranged aim/shot/reload, guard, stun, knockdown, fall, and neutralize behavior.

## [Unreleased / hotfix] — 2026-05-06

### Fixed
- **Living animations not applying on world rejoin / patch swap**: Restored deferred cap-replacement on the client (`Minecraft.getInstance().execute(...)`) so `updateModelCap()` runs after the entity is fully in the world, giving the correct `NpcHumanoidPatch` or `AdvNpcHumanoidPatch` instead of a `NullPatch`.
- **Client crash (`NPE: nextAnimation is null`) with deferred patch construction**: `NpcHumanoidPatch` now overrides `initAnimator` to seed always-valid zombie fallback animations (`Animations.ZOMBIE_IDLE/WALK/CHASE`) before adding datapack-provided animations. `ClientAnimator.addLivingAnimation` silently skips null/empty accessors (e.g. if an `epicfightx` animation isn't registered yet at patch-construction time), which previously left `livingAnimations` without an IDLE entry, causing `ClientAnimator.postInit()` to NPE. The fallbacks guarantee IDLE is always present; datapack animations override them once loaded.
- Resolved EFI-Unofficial flatDir dependency coordinate mismatch (`20.14.16-unofficial` → `20.14.16`) so `runClient` and all Gradle builds resolve cleanly.
- NPC model resizing now works correctly in-game.

---

## [2026-02-26] — Epic Fight 20.14.x Integration Overhaul

### Added
- **Per-CNPC armature configuration**: Each CNPC now loads its own unique armature from the datapack instead of sharing a single global one.
- **Per-CNPC animation sets**: Each NPC can have its own weapon motions and combat behaviors defined independently.
- Full `datapack_example/` with `customnpc.json` containing weapon motion definitions for custom NPCs.
- `pack.mcmeta` for the example datapack.

### Fixed
- **Animator initialization**: Added proper `super.onConstructed()` call order so the EpicFight animator initializes correctly on NPC spawn.
- **Animation freezing**: NPCs now animate continuously without locking up mid-animation.
- **AssetAccessor API compatibility**: Updated to Epic Fight 20.14.10 `AssetAccessor` interface (replaced deprecated accessor patterns).
- **Dynamic armature registration**: Restored dynamic armature registration logic from the original working code that was lost during a refactor attempt.
- Upgraded Epic Fight dependency from `20.7.4` → `20.14.10` (and later `20.14.16`).
- Upgraded EFI-Unofficial (Indestructible) dependency from `20.6.7` → `20.14.16`.
- Upgraded CustomNPCs dependency to `1.20.1-GBPort-Unofficial-20251031`.

### Notes
- Both humanoid and non-humanoid NPCs are supported.
- Multiple NPCs with different movesets can coexist simultaneously.
- Tested and verified working in `runClient`.

---

## [2024-08-20]

### Fixed
- Added null check for missing combat configuration to prevent NPE crashes.
- Changed EpicFight dependency to soft (optional) so the mod loads without EpicFight present.

---

## [2024-08-04]

### Fixed
- EF-Indestructible (invincibility) NPCs not scaling correctly.
- EF-Indestructible synchronization issues.
- Datapack data synchronization between server and client.

---

## [2024-07-25]

### Fixed
- Dead NPCs no longer stand upright after a world reload.

### Added
- Method to set the EpicFight model on an NPC via script/API.

---

## [2024-07-24]

### Added
- NPC scaling support — NPCs now respect custom size/scale values in EpicFight.

---

## [2024-07-20]

### Fixed
- Server-side compatibility (dedicated server no longer crashes).

---

## [2024-07-19]

### Added
- Initial **EpicFight-Indestructible** compatibility (NPCs can use the invincibility/indestructible armor system).

---

## [Initial]

- Initial commit: CNPC + EpicFight addon base for 1.20.1.
