package fr.nitorac.climodsupdator.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import fr.nitorac.climodsupdator.CLIMApplication;

import java.io.*;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class JsonStorageManager {

    public static final File OPTIONS = new File("options.json");
    public static JsonObject root = new JsonObject();

    public static final String WD = "workdir";

    public JsonStorageManager(){
        try {
            Files.write(OPTIONS.toPath(), root.toString().getBytes(), StandardOpenOption.CREATE);
            root = new JsonParser().parse(new FileReader(OPTIONS)).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonElement set(String prop, Object val){
        try {
            Constructor<JsonPrimitive> constr = JsonPrimitive.class.getDeclaredConstructor(Object.class);
            constr.setAccessible(true);
            JsonElement elem = constr.newInstance(val);
            root.add(prop, elem);
            save();
            return elem;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonElement get(String prop){
        return root.get(prop);
    }

    public JsonElement get(String prop, Object def){
        JsonElement elem;
        return (elem = root.get(prop)) == null ? set(prop, def) : elem;
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(OPTIONS, false);
            writer.write(root.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
