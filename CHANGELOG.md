# CNPC EpicFight Addon — Changelog

## [Unreleased / hotfix] — 2026-04-24

### Fixed
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
