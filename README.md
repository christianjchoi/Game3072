# Game3072

# Features

- WASD Key Commands to Indicate Up, Down, Left, Right
- Undo up to Three Moves per Game
- Reset Game at any Point to Start Over
- High Scores Records Updated at End of each Game
- Play Beyond the Just the 3072 Tile

# Commands

- w : up
- a : left
- s : down
- d : right
- start : start a game
- reset : reset the game
- highscores : display high scores
- undo : undo a move
- quit : exit the game


# Gameplay

```
3072: start
Score: 0

-       -       -       -


-       -       -       3


-       -       -       3


-       -       -       -

3072: s
Score: 6

-       -       -       3


-       -       -       -


-       -       -       -


-       -       -       6

```

**Undo Moves**

```

3072: undo
Score: 0

-       -       -       -


-       -       -       3


-       -       -       3


-       -       -       -

You have 2 undos remaining.

```

**Goal: Get the 3072 Tile**

```

3072: s
Score: 26226

-       -       3       6


6       -       3       3


3       12      6       24


1536    768     768     3

3072: a
Score: 27768

3       6       -       -


6       6       -       3


3       12      6       24


1536    1536    3       -

3072: a
Score: 30852

3       6       -       -


12      3       -       -


3       12      6       24


3072    3       -       3

Congratulations! You win!

```
**Gameplay Beyond 3072 Tile**

```

3072: s
Score: 30852

3       6       -       -


12      3       3       -


3       12      -       24


3072    3       6       3

3072: s
Score: 30852

3       6       -       -


12      3       3       -


3       12      3       24


3072    3       6       3

3072: s
Score: 30858

3       6       3       -


12      3       -       -


3       12      6       24


3072    3       6       3

3072: d
Score: 30858

-       3       6       3


-       3       12      3


3       12      6       24


3072    3       6       3

```

**Keep Track of High Scores From Play History**

```

3072: d
Score: 35772

3       6       12      3


48      24      96      24


24      192     384     3072


12      24      6       48

Game Over.
Your name: Chris
Player: Chris, Score: 35772

3072: highscores
Chris - 35772

```
