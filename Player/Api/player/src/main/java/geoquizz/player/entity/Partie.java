package geoquizz.player.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Partie {

    @Id
    private String id;
    private String token;
    private int status; //0 : creer, 1: en cours, 2: fini
    private int score;
    private int difficulty; // 0 1 2
    private String joueur;
    private boolean saveScore; //true false
    private String ville;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
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

    public String getVille(){ return ville; }

    public void setVille(String ville) { this.ville = ville; }

    public void setSaveScore(boolean saveScore) {
        this.saveScore = saveScore;
    }

    public boolean getSaveScore() {
        return saveScore;
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
