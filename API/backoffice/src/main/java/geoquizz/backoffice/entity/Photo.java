package geoquizz.backoffice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Photo {

    @Id
    private String id;
    private String dsc;
    private String url;
    private double x;
    private double y;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "serie_id", nullable = false)
    @JsonIgnore
    private Serie serie;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "partie_photo",
            joinColumns = @JoinColumn(name = "photo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "partie_id", referencedColumnName = "id"))
    private Set<Partie> parties;

    public Photo() {
        
    }

    public Photo(String dsc, String url, double x, double y) {
        this.dsc = dsc;
        this.url=url;
        this.x=x;
        this.y=y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public void addParties(Partie p) {
        this.parties.add(p);
    }
}
