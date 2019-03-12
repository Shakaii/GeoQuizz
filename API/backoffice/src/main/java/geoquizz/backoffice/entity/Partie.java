package geoquizz.backoffice.entity;

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
    private Serie serie;

    @ManyToMany(mappedBy = "parties")
    private Set<Photo> photos;


    public Partie() {

    }
}
