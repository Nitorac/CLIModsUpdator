package fr.nitorac.climodsupdator.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class RemoteFile {
    private String displayName;
    private String fileName;
    private Date fileDate;
    private long fileLength;
    private ReleaseType releaseType;

    private String downloadUrl;

}
