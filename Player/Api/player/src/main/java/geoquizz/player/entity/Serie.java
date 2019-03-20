package geoquizz.player.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Serie {

    @Id
    private String id;
    private String ville;
    private double x;
    private double y;
    private int zoom;
    private int dist;
    private int nb_photos;


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

    public Serie(String ville, int dist, double x, double y, int zoom) {
        this.ville = ville;
        this.dist = dist;
        this.x = x;
        this.y = y;
        this.zoom = zoom;
        this.nb_photos = nb_photos;
    }



    public int getNb_photos(){ return nb_photos; }

    public void setNb_photos(){ this.nb_photos = nb_photos; }

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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY(){ return y; }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public int getZoom(int zoom) {
        return zoom;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
}
