package fr.nitorac.climodsupdator.models;

import java.util.Arrays;

public enum ReleaseType {
    ALPHA(3),
    BETA(2),
    RELEASE(1);

    private int typeId;

    ReleaseType(int id){
        typeId = id;
    }

    public int getTypeId(){
        return typeId;
    }

    public static ReleaseType getRelease(int id){
        return Arrays.stream(ReleaseType.values()).filter(rel -> rel.getTypeId() == id).findFirst().orElse(null);
    }
}
