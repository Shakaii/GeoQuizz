![logo](https://cdn.discordapp.com/attachments/554606011592343582/557866827485544448/GeoQuizZLarge.png)

## Trello

https://trello.com/b/kw2pvLTp/geoquizz

## Day 1 sketches

+ `Homepage` <https://wireframe.cc/KIeWR5>
  
  The homepage of the application, where you can play a little demo of the game or start playing

+ `Series selection` <https://wireframe.cc/uPb7HG>
  
  This page will let the user select a series to play and set the difficulty

+ `Game` <https://wireframe.cc/078css>
  
  This is the main game screen


+ `App` <https://wireframe.cc/4E41Ci>
  
  The App that will let people take picture with geolocalization and add them to series


+ `Back Office` <https://wireframe.cc/K5CdrH>
  
  The Back Office will allow users to create and edit series and import photo from the app or from the computer
  
## Initialisation

### Database (using docker)

`docker run --name some-mysql -p 33066:3306 -e MYSQL_ROOT_PASSWORD=admin -d mysql:5.7`
    
`bash -c "clear && docker exec -it some-mysql sh"`
    
`mysql --password` (the password is admin)
    
`create database geoquizz;`
   
