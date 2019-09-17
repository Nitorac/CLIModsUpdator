package fr.nitorac.climodsupdator.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Data
public class LocalMod implements Serializable {
    private File file;

    private String modid;
    private String name;
    private String desc;
    private String version;

    @SerializedName("authorList")
    private List<String> authors;

    private String credits;

    public LocalMod(File file){
        this.file = file;
    }
}
