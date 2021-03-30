# HvZ (Human Vs Zombies)

Humans vs. Zombies (HvZ) is a game of tag played at schools, camps, neighborhoods, libraries, and conventions around the world. 
The game simulates the exponential spread of a fictional zombie infection through a population.

## API endpoints

<b>Game API endpoints</b>:
|Endpoint |HTTP Method |Description |
|---|---|---|
|/api/v1/game|GET|Returns a list of games|
|/api/v1/game/{game_id}|GET|Returns a specific game object|
|/api/v1/game|POST|Creates a new game|
|/api/v1/game/{game_id}|PUT|Updates a game|
|/api/v1/game/{game_id}|DELETE|Deletes (cascading) a game|
|/api/v1/game/{game_id}/chat|GET|Returns a list of chat messages|
|/api/v1/game/{game_id}/chat|POST|Send a new chat message|

<b>Player API endpoints</b>:
|Endpoint |HTTP Method |Description |
|---|---|---|
|/api/v1//game/{game_id}/player|GET|Get a list of players|
|/api/v1/game/{game_id}/player/{player_id}|GET|Returns a specific player object|
|/api/v1/game/{game_id}/player|POST|Registers a user for a game|
|/api/v1/game/{game_id}/player/{player_id}|PUT|Updates a player object|
|/api/v1/game/{game_id}/player/{player_id}|DELETE|Deletes (cascading) a player|

<b>Kill API endpoints</b>:
|Endpoint |HTTP Method |Description |
|---|---|---|
|/api/v1/game/{game_id}/kill|GET|Get a list of kills|
|/api/v1/game/{game_id}/player/{kill_id}|GET|Returns a specific kill object|
|/api/v1/game/{game_id}/kill|POST|Creates a kill object by looking up the victim by the specified bite code|
|/api/v1/game/{game_id}/kill/{kill_id}|PUT|Updates a kill object|
|/api/v1/game/{game_id}/kill/{kill_id}|DELETE|Deletes (cascading) a kill|

<b>Mission API endpoints</b>:
|Endpoint |HTTP Method |Description |
|---|---|---|
|/api/v1/game/{game_id}/mission|GET|Get a list of missions|
|/api/v1/game/{game_id}/mission/{mission_id}|GET|Returns a specific kill object|
|/api/v1/game/{game_id}/mission|POST|Creates a mission object|
|/api/v1/game/{game_id}/mission/{mission_id}|PUT|Updates a mission object|
|/api/v1/game/{game_id}/mission/{mission_id}|DELETE|Deletes (cascading) a mission|

<b>Squad API endpoints</b>:
|Endpoint |HTTP Method |Description |
|---|---|---|
|/api/v1/game/{game_id}/squad|GET|Get a list of squads|
|/api/v1/game/{game_id}/squad/{squad_id}|GET|Returns a specific squad object|
|/api/v1/game/{game_id}/squad|POST|Creates a squad object|
|/api/v1/game/{game_id}/squad/{squad_id}/join|POST|Creates a squad object|


