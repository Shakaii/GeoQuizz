package geoquizz.backoffice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Serie {

    @Id
    private String id;
    private String ville;
    private double x;
    private double y;
    private int dist;
    private int nb_photos;
    private int zoom;

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Partie> parties;

    public Serie() {

    }
    
    public Serie(String ville, int dist, double x, double y, int nb_photos, int zoom) {
        this.ville = ville;
        this.dist = dist;
        this.x = x;
        this.y = y;
        this.nb_photos = nb_photos;
        this.zoom = zoom;
    }

    public Serie(String ville, int dist, String str) {
        this.ville = ville;
        this.dist = dist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getNb_photos() {
        return nb_photos;
    }

    public void setNb_photos(int nb_photos) {
        this.nb_photos = nb_photos;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public Set<Partie> getParties() {
        return parties;
    }

    public void setParties(Set<Partie> parties) {
        this.parties = parties;
    }
}
