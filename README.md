![logo](https://cdn.discordapp.com/attachments/554606011592343582/557866827485544448/GeoQuizZLarge.png)

## Demos du jeu

+ `démo jouable en pur javascript` <http://debaser.fr/geoquiz/1>
+ `jeu jouable en full vueJs` <http://debaser.fr/geoquiz/2>
+ `nouvelles features (difficultée, sauvegarde, etc...)` <http://debaser.fr/geoquiz/3>
+ `améliorations css` <http://debaser.fr/geoquiz/4>
+ `pré implémentation de l'api` <http://debaser.fr/geoquiz/5>

## Maquettes

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
    
### Dataset

+ Créer une série :

    `{
      "ville" : "Nancy",
      "x": 48.6907346,
      "y": 6.174169,
      "zoom" : 13,
      "nb_photos" : 3,
      "dist" : 100
    }`

+ Ajouter cles photos à la série avec son id :

  `[
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
        "x": 48.66634,
        "y": 6.16687,
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
]`

