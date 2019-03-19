package geoquizz.player.bundary;

import geoquizz.player.entity.Partie;
import geoquizz.player.entity.Photo;
import geoquizz.player.entity.Serie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@Api( description="Gére les routes liées aux parties pour le côté player.")
@RequestMapping(value = "/game", produces = MediaType.APPLICATION_JSON_VALUE)
public class PartieRepresentation {

    private PartieResource pr;

    @Autowired SerieResource sr;
    @Autowired PhotoResource poR;

    public PartieRepresentation(PartieResource pr) {
        this.pr = pr;
    }

    @ApiOperation(value = "Récupère la partie dont l'id est spécifiée dans la route. Change le status de la partie.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") String id, @RequestParam(value = "token", required = true) String token) {
        Partie p = pr.findById(id).get();
        if (p.getToken().equals(token)){
            p.setStatus(1);
            pr.save(p);
            return new ResponseEntity<>(pr.findById(id), HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/score")
    public ResponseEntity<?> getScore() {
        return new ResponseEntity<>(pr.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Permet de créer une nouvelle partie (présente dans le body de la requête). Demande en paramètre l'id de la série. Si la série a plus de 10 photos, en sélectionne 10 aléatoirements à ajouter à la partie.")
    @PostMapping(value = "/new")
    public ResponseEntity<?> postGame(@RequestBody Partie p, @RequestParam(value = "serie", required = true) String id) {
        if (sr.findById(id).isPresent()) {
            Serie s = sr.findById(id).get();
            p.setId(UUID.randomUUID().toString());
            p.setToken(UUID.randomUUID().toString());
            p.setSerie(s);

            //recupere les photos à utiliser (les 10 premieres dans l'ordre aleatoire)
            Set<Photo> photosRand = getPhotosRandom(s.getPhotos());
            for (Photo po : photosRand) {
                po.addParties(p);
            }

            p.setPhotos(photosRand);
            p.setVille(s.getVille());
            Partie saved = pr.save(p);
            HttpHeaders rH = new HttpHeaders();
            rH.setLocation(linkTo(PartieRepresentation.class).slash(saved.getId()).toUri());
            return new ResponseEntity<>(saved, rH, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Permet de signaler la fin de la partie. Renvoie un 204.")
    @PostMapping(value = "/result/{id}")
    public ResponseEntity<?> postResult(@PathVariable("id") String id, @RequestBody Partie p) {
        Partie partie = pr.findById(id).get();
        if (partie.getToken().equals(p.getToken())){
            partie.setScore(p.getScore());
            partie.setSaveScore(p.getSaveScore());
            partie.setStatus(p.getStatus());
            pr.save(partie);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
       
    }

    //randomize les photos d'une partie pour en renvoyer 10 maximum dans un ordre aleatoire
    //à l'aide de Collections.shuffle  (java.util)
    public Set<Photo> getPhotosRandom(Set<Photo> photos){
        //met en arrayList pour Collections.shuffle
        ArrayList<Photo> listPhotos = new ArrayList<Photo>();
        listPhotos.addAll(photos);
        Collections.shuffle(listPhotos);
        //recupere les 10 premieres photos pour le set à renvoyer      
        if(listPhotos.size()<11){
            return photos;
        }else{
            Set<Photo> photosRand = new HashSet<Photo>();   
            for(int i=0;i<10;i++){
                photosRand.add(listPhotos.get(i));
            } 
            return photosRand;
        }
    }
}
