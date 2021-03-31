<img src="https://github.com/godlikecpu/HvZ-frontend/blob/main/docs/screenshots/hvz.png" alt="logo" width="1000"/>

# HvZ (Human Vs Zombies)

This repository contains the RESTful API for the web based Java Spring game application called HvZ.
The project is the final case for Experis Academy's accelerated Fullstack Java graduate program. 
Detailed description of the project can be found [here](https://github.com/godlikecpu/HvZ-backend/blob/main/docs/HvZ%20Case.pdf).

## Game description

Humans vs. Zombies (HvZ) is a game of tag played at schools, camps, neighborhoods, libraries, and conventions around the world. 
The game simulates the exponential spread of a fictional zombie infection through a population.

More about the rules of the game is available at the official website [Humans vs. Zombies](https://humansvszombies.org)

## Technology stack 

<b>Programming language:</b>
  - Java - version 15

<b>Framework:</b>
- Spring Boot - version 2.4.3

<b>Data access:</b>
- JPA - version 2.4.3

<b>ORM:</b>
- Hibernate - version 5.4.28

<b>Database:</b>
- PostgreSQL - version 42.2.18

<b>Identity management:</b>
- Keycloak - version 12.0.4

## Deployment
The Java Spring RESTful API and the PostgreSQL database are deployed to [Heroku](https://www.heroku.com).
Link to the RESTful API on Heroku is [here](https://hvz-backend-v1.herokuapp.com).

## API endpoints

<b>Game API endpoints</b>:
|Endpoint |HTTP Method |Description |
|:---|:---|:---|
|/api/v1/game|GET|Returns a list of games|
|/api/v1/game/{game_id}|GET|Returns a specific game object|
|/api/v1/game|POST|Creates a new game|
|/api/v1/game/{game_id}|PUT|Updates a game|
|/api/v1/game/{game_id}|DELETE|Deletes (cascading) a game|
|/api/v1/game/{game_id}/chat|GET|Returns a list of chat messages|
|/api/v1/game/{game_id}/chat|POST|Send a new chat message|

</br>

<b>Player API endpoints</b>:
| Endpoint | HTTP Method | Description |
| :--- | :--- | :--- |
|/api/v1//game/{game_id}/player|GET|Get a list of players|
|/api/v1/game/{game_id}/player/{player_id}|GET|Returns a specific player object|
|/api/v1/game/{game_id}/player|POST|Registers a user for a game|
|/api/v1/game/{game_id}/player/{player_id}|PUT|Updates a player object|
|/api/v1/game/{game_id}/player/{player_id}|DELETE|Deletes (cascading) a player|

</br>

<b>Kill API endpoints</b>:
|Endpoint |HTTP Method |Description |
|:---|:---|:---|
|/api/v1/game/{game_id}/kill|GET|Get a list of kills|
|/api/v1/game/{game_id}/player/{kill_id}|GET|Returns a specific kill object|
|/api/v1/game/{game_id}/kill|POST|Creates a kill object by looking up the victim by the specified bite code|
|/api/v1/game/{game_id}/kill/{kill_id}|PUT|Updates a kill object|
|/api/v1/game/{game_id}/kill/{kill_id}|DELETE|Deletes (cascading) a kill|

</br>

<b>Mission API endpoints</b>:
|Endpoint |HTTP Method |Description |
|:---|:---|:---|
|/api/v1/game/{game_id}/mission|GET|Get a list of missions|
|/api/v1/game/{game_id}/mission/{mission_id}|GET|Returns a specific kill object|
|/api/v1/game/{game_id}/mission|POST|Creates a mission object|
|/api/v1/game/{game_id}/mission/{mission_id}|PUT|Updates a mission object|
|/api/v1/game/{game_id}/mission/{mission_id}|DELETE|Deletes (cascading) a mission|

</br>

<b>Squad API endpoints</b>:
|Endpoint |HTTP Method |Description |
|:---|:---|:---|
|/api/v1/game/{game_id}/squad|GET|Get a list of squads|
|/api/v1/game/{game_id}/squad/{squad_id}|GET|Returns a specific squad object|
|/api/v1/game/{game_id}/squad|POST|Creates a squad object|
|/api/v1/game/{game_id}/squad/{squad_id}/join|POST|Creates a squad object|
|/api/v1/game/{game_id}/squad/{squad_id}|PUT|Updates a squad object|
|/api/v1/game/{game_id}/squad/{squad_id}|DELETE|Deletes a squad|
|/api/v1/game/{game_id}/squad/{squad_id}/chat|GET|Returns a list of chat messages|
|/api/v1/game/{game_id}/squad/{squad_id}/chat|POST|Send a new chat message addressed to a particular squad|
|/api/v1/game/{game_id}/squad/{squad_id}/check-in|GET|Get a list of squad check-in markers|
|/api/v1/game/{game_id}/squad/{squad_id}/check-in|POST|Create a squad checkin|

</br>

## Identity management

[Keycloack](https://www.keycloak.org) is an open source software product to allow single sign-on with Identity and Access Managementaimed at modern applications and services.


## Database diagram
![HvZ](https://github.com/godlikecpu/HvZ-backend/blob/main/docs/hvzdb.png "HvZ DB diagram")

## Developers

- Dan Tomicic 
- Rasmus Ulrichsen
- Alexander Rol
- Hunor Vadasz-Perhat




