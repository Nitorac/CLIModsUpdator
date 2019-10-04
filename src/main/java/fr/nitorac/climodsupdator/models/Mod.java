package fr.nitorac.climodsupdator.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Mod {
    private long id;
    private String name;

    private List<Author> authors;
    private List<RemoteFile> latestFiles;

    private String websiteUrl;
    private int gameId;
    private String summary;
    private long defaultFileId;
    private long downloadCount;
    private String slug;
    private double popularityScore;
    private Date dateModified;
    private Date dateCreated;
    private Date dateReleased;
}
