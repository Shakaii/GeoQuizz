package geoquizz.player.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Serie {

    @Id
    private String id;
    private String ville;
    private String map_refs;
    private int dist;

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

    public String getMap_refs() {
        return map_refs;
    }

    public void setMap_refs(String map_refs) {
        this.map_refs = map_refs;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }
}
