# Ex2
The second assignment in OPP course
- Names: Saar Barel & Almog Reuveny.
In this assignment we built a directional weighted graph by data structures and running 
pokemons game by catching them with agents and gain points by each catch .
We used hashMap,Queue,List and Stack.
There are 2 parts to the task:
-The first part: represents a positive directional weighted graph.
 -DWGraph_DS: represents a directional weighted graph.
 -DWGraph_Algo: represents a Directed (positive) Weighted Graph Theory Algorithms
-The second part: represents a game which uses our algorithm to catch the pokemons.
 -Ex2: represents the pokemon game. In this class we use 2 main methods:
   -matchPokemonsToAgents:build a HashMap for the agents, for each agent there is a pokemon
    and a list that represents the path of nodes from where the agent located to where his pokemon
    located by target the pokemons and send an agent to them by calculating the shortest distance
    from the pokemon to the agents.                       
   -moveAgents: choses the next destination for each agent by using the HashMap that given 
    from the matchPokemonsToAgents method.  
 -MyPannel: this class screens the game and turns it off as soon as the time runs out.
 -Jlabel: the opening screen of the game. After entering the right ID and level, 
 the game will start with the given level. 

There are 2 options for running this game:
1.Use the command prompt and write the command line "java Ex2" and
 enter your ID and the number of level (need to press space between them).
2.By pressing play in main Ex2, you will be request to enter your ID and the number of level you want to play
  and then press the button "start".