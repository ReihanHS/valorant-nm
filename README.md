# VALORANT // NIGHT.MARKET — Store Recreation

A Java Swing desktop application that recreates the **Valorant Night Market** experience — the limited-time in-game store where players can reveal mystery discounts on weapon skins.

![Java](https://img.shields.io/badge/Java-Swing-orange)

---

## Features

- **Night Market Cards** — 6 randomized weapon skin cards with tier-based border colors (Premium = pink, Exclusive = gold)
- **Mystery Reveal** — Click any card or its button to reveal the hidden skin and its discounted price
- **Dynamic Discounts** — Each card has a unique discount percentage (19%–48%) applied to the original price
- **Purchase System** — Buy skins with VP (Valorant Points), with balance validation and confirmation dialogs
- **Countdown Timer** — Dynamic "ENDS IN X DAYS" header that calculates remaining days automatically
- **Demo Controls** — Built-in buttons to add VP, set low balance, reveal all cards, and reset the market

## Skins Included

| Skin | Type | Tier | Original Price |
|------|------|------|----------------|
| Neptune Vandal | Vandal | Premium | 1,775 VP |
| Prime//2.0 Phantom | Phantom | Premium | 1,775 VP |
| Prime//2.0 Karambit | Melee | Exclusive | 3,550 VP |
| Prime Vandal | Vandal | Premium | 1,775 VP |
| Reaver Knife | Melee | Exclusive | 3,550 VP |
| Oni Phantom | Phantom | Premium | 1,775 VP |

## How to Run

### Prerequisites
- Java JDK 11 or higher

### Compile & Run
```bash
javac *.java
java ValorantNightMarket
```

## Project Structure

```
valorant-nm/
├── ValorantNightMarket.java   # Main entry point & frame setup
├── Store.java                 # Store view: top nav, title banner, card row, background
├── StoreItem.java             # Individual card: reveal, purchase, confirm dialog
├── BottomBar.java             # Info bar & demo control buttons
├── assets/                    # Card images, backgrounds, and UI assets
│   ├── bg.jpg                 # Store background
│   ├── title_header.png       # Title banner image
│   ├── card_back_pink.png     # Premium tier card back
│   ├── card_back_gold.png     # Exclusive tier card back
│   ├── card_neptune_vandal.png
│   ├── card_prime_phantom.png
│   ├── card_prime_karambit.png
│   ├── card_prime_vandal.png
│   ├── card_reaver_knife.png
│   └── card_oni_phantom.png
└── .gitignore
```

## Demo Controls

The bottom bar includes testing buttons:

| Button | Action |
|--------|--------|
| **+ 1,000 VP** | Adds 1,000 VP to your balance |
| **Set 500 VP** | Sets balance to 500 VP (to test failed purchases) |
| **Reveal All** | Reveals all 6 cards at once |
| **Reset Market** | Resets all cards and restores balance to 5,000 VP |

The **+** button next to the VP display in the top nav also adds 1,000 VP per click.

## How It Works

1. **Start** — The app launches with 5,000 VP and 6 face-down cards
2. **Reveal** — Click a card to flip it, revealing the skin and its discounted price
3. **Purchase** — Click the purchase button to buy; a styled confirmation dialog appears showing the original price, discount, and remaining balance
4. **Insufficient Funds** — If you can't afford a skin, an error banner appears showing how much more VP you need
