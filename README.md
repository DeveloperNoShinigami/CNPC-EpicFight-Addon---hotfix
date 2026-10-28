# CNPC Epic Fight Addon — final 1.20.1 build

This addon connects CustomNPCs to EFI-Unofficial and Epic Fight.

## Final compatibility

- Minecraft 1.20.1
- Forge 47.4.10
- CustomNPCs `1.20.1.20260711` (CurseForge file `8414335`)
- Epic Fight `20.14.17`
- EFI-Unofficial `20.14.17`

## What is fixed

- CNPC equipment remains authoritative and stays synchronized with Epic Fight.
- NBT-selected patches work for any number of CNPCs or generic mobs.
- NBT changes refresh the provider in real time.
- Native held-item motions and datapack overrides remain available after combat,
  reloads, swaps, and world reloads.
- NPC melee damage reaches Epic Fight stun behavior, with CNPC faction rules
  preserved.
- TACZ aim, shooting, reload, and Epic Arsenal gun motions are synchronized to
  tracking clients.

## TACZ ranged behavior

Use `aim` and `shoot` in a looping EFI behavior series. Add
`maintain_distance` with `min` and `max` when the NPC should keep a firing
band. It approaches targets beyond `max`, retreats below `min`, and strafes
while aiming and firing inside the band without pausing. With no target, it
uses native held-gun idle, walk, or chase motions. Without this behavior, the
normal Epic Fight combat range is used.

Use the EFI documentation in the sibling Epic Fight project for the complete
datapack schema. The short release package is in the repository root under
`ready_for_public_2026-08-23`.
