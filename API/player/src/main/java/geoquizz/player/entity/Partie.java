package geoquizz.player.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Partie {

    @Id
    private String id;
    private String token;
    private int nb_photos;
    private int status; //0 : creer, 1: en cours, 2: fini
    private int score;
    private String joueur;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "serie_id", nullable = false)
    @JsonIgnore
    private Serie serie;

    @ManyToMany(mappedBy = "parties")
    private Set<Photo> photos;


    public Partie() {

    }

    public Partie(String joueur) {
        this.joueur = joueur;
        this.status = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNb_photos() {
        return nb_photos;
    }

    public void setNb_photos(int nb_photos) {
        this.nb_photos = nb_photos;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getJoueur() {
        return joueur;
    }

    public void setJoueur(String joueur) {
        this.joueur = joueur;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
