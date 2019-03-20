
![logo](https://cdn.discordapp.com/attachments/554606011592343582/557866827485544448/GeoQuizZLarge.png)

## player interface screenshots

![screenshots](https://cdn.discordapp.com/attachments/369845824659914752/557905836270419969/unknown.png)

you can download a zip here with screenshots for all functionnalities (28mo) : http://debaser.fr/GeoQuizz.zip 

## Game rules

   From the home screen you can play a small demo of the game where the goal is to find the IUT Charlemagne of Nancy.
   You can also start a game, display scores for all the levels or get back to the saved game if there's one.
   Starting a game will lead you to the level selection screen where you can chose a level (a series of photos from the same location or theme, by example a city).

   From this screen you will also be able to choose a difficulty. With the easiest difficulty you'll have more time to find where the picture has been taken, the radius that will give you points will also be a bit bigger but you'll earn less points. With the 2 others difficulties you'll have less time, the radius that grant points will decrease in size but you'll get a lot more points.

   During the game you'll have to click the correct location like on the demo, but you only have one try and you'll have a timer. Also the faster and the more accurate you are the more points you'll get.

   You can either quit or save mid game. You'll be able to continue your game from the home screen if you come back later

   ### Scoring
   At the end of the game you'll be able to save your score or not, if you do so it'll be forever displayed in the score screen.

   The score is calculated this way : Each levels has a different radius that will grant points. By example if the level radius is 100 meters, clicking 100 meters around the correct location will grant you 5 points, around 200 meters will grant you 3 points and around 300 meters will grant you 1 point. 

   Time remaining is also a factor if you still have more than 2/3 of the time remaining your score will have a multiplier of 4, more  than1/3 remaining will grant you a multiplier of 2, less but more than 0/3 will grant you no multiplier. If you go under the given timer, you'll gain no points.

   Playing on the medium difficulty will double your score, and playing on the hardest difficulty will triple the score.

   For a level with 10 pictures the maximum score will be 600 in the hardest difficulty.

## First Developpement's week demos

these are developpment builds they are not representative of the final result.

+ `javascript's playable demo` <http://debaser.fr/geoquiz/1>
+ `vueJs's playable game` <http://debaser.fr/geoquiz/2>
+ `new features (difficulty, saves, etc...)` <http://debaser.fr/geoquiz/3>
+ `css upgrade` <http://debaser.fr/geoquiz/4>
+ `more upgrades` <http://debaser.fr/geoquiz/5>

## Day 1 sketches

+ `Homepage` <https://wireframe.cc/KIeWR5>
  
  The homepage of the application, where you can play a little demo of the game or start playing

+ `Series selection` <https://wireframe.cc/uPb7HG>
  
  This page will let the user select a series to play and set the difficulty

+ `Game` <https://wireframe.cc/078css>
  
  This is the main game screen
  
## Initialisation

The game will work even if the other API aren't started. If that's the case here are steps by steps instructions on how to run it

  ### Database (using docker)

no need to run this if you already have the database running for another API

`docker run --name some-mysql -p 33066:3306 -e MYSQL_ROOT_PASSWORD=admin -d mysql:5.7`
    
`bash -c "clear && docker exec -it some-mysql sh"`
    
`mysql --password` (the password is admin)
    
`create database geoquizz;`

  ### Starting the API

run `src/main/java.GeoQuizz/player/PlayerApplication.java` 
    
  ### Creating a dataset

If the database is already filled, you won't need to do this

+ Create a serie :

`POST` to `http://localhost:8083/player/serie`

    `{
      "ville" : "Nancy",
      "x": 48.6907346,
      "y": 6.174169,
      "zoom" : 13,
      "nb_photos" : 8,
      "dist" : 100
    }`

+ then add photos to the series with its id

`POST` to `http://localhost:8083/player/serie/{id}/photos`

    [
       {
           "x": 48.6829,
           "y": 6.16106,
           "url": "https://i.ibb.co/fMZJzMr/1.jpg",
           "dsc" : "IUT Nancy-Charlemagne"
       },
       {
           "x": 48.68559,
           "y": 6.16104,
           "url": "https://i.ibb.co/KL91YwV/2.jpg",
           "dsc" : "Supermarché Match Laxou"
       },
       {
           "x": 48.68891,
           "y": 6.17493,
           "url": "https://i.ibb.co/JjpVCFZ/3.jpg",
           "dsc" : "Gare de Nancy"
       },
       {
           "x": 48.6933734,
           "y": 6.184289,
           "url": "https://i.ibb.co/q59TQ23/4.jpg",
           "dsc" : "Place Stanislas"
       },
       {
           "x": 48.66634,
           "y": 6.16687,
           "url": "https://i.ibb.co/vBBwJdR/5.jpg",
           "dsc" : "Vélodrome"
       },
       {
           "x": 48.6907346,
           "y": 6.174169,
           "url": "https://i.ibb.co/zJKLw5Z/6.jpg",
           "dsc" : "Tour Thiers"
       },
       {
           "x": 48.6919326,
           "y": 6.1862249,
           "url": "https://i.ibb.co/dbtphyw/7.jpg",
           "dsc" : "Cathédrale Notre-Dame-de-l'Annonciation de Nancy"
       },
       {
           "x": 48.6802006,
           "y": 6.1692646,
           "url": "https://i.ibb.co/VHf1Bjr/8.jpg",
           "dsc" : "Parc Sainte-Marie"
       },
       {
           "x": 48.6891655,
           "y": 6.1804276,
           "url": "https://i.ibb.co/syBFRSF/9.jpg",
           "dsc" : "Centre Commercial Saint Sébastien"
       },
       {
           "x": 48.698987,
           "y": 6.1841661,
           "url": "https://i.ibb.co/DzGsCWM/10.jpg",
           "dsc" : "La pépinière"
       },
       {
           "x": 48.6886052,
           "y": 6.1769114,
           "url": "https://i.ibb.co/sFW24T1/11.jpg",
           "dsc" : "Centre Prouvé"
       },
       {
           "x": 48.6860938,
           "y": 6.1920457,
           "url": "https://i.ibb.co/PhHMsLB/12.jpg",
           "dsc" : "CHRU Nancy"
       }
    ]

  ### getting the interface up and running

you can simply open `dist/index.html`

or go to http://debaser.fr/GeoQuizZ

but the best is to run it locally by following these steps

+ run `npm install` to get all the dependencies

+ run `npm run serve` to get a localhost server running

## API docs

Once the API is running simply go to `localhost:8083/swagger-ui.html` to see the docs
