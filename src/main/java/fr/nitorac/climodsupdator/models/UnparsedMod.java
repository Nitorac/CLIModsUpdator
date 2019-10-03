package fr.nitorac.climodsupdator.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Data
public class UnparsedMod implements Serializable {
    private File file;

    private String modid;
    private String name;
    @SerializedName(value="desc", alternate={"description"})
    private String desc;
    private String version;

    @SerializedName(value = "authorList", alternate = {"authors"})
    private List<String> authors;

    private String credits;

    public UnparsedMod(File file) {
        this.file = file;
    }

    public UnparsedMod setFile(File file) {
        this.file = file;
        return this;
    }
}
