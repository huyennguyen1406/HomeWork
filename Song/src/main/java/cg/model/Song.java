package cg.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String songLink;

    @Transient
    private MultipartFile songFile;

    @ManyToOne
    @JoinColumn(name = "nameCategory")
    private Category category;

    public Song() {
    }

    public Song(Long id, String name, String songLink, MultipartFile songFile, Category category) {
        this.id = id;
        this.name = name;
        this.songLink = songLink;
        this.songFile = songFile;
        this.category = category;
    }

    public Song(String name, String songLink, Category category) {
        this.name = name;
        this.songLink = songLink;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public MultipartFile getSongFile() {
        return songFile;
    }

    public void setSongFile(MultipartFile songFile) {
        this.songFile = songFile;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
