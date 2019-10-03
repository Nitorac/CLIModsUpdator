package fr.nitorac.climodsupdator;

import com.google.gson.*;
import fr.nitorac.climodsupdator.models.Modpack;
import fr.nitorac.climodsupdator.models.ReleaseType;
import fr.nitorac.climodsupdator.storage.JsonStorageManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CLIMApplication implements ApplicationRunner {

	public static Gson gson;

	private static JsonStorageManager storageManager;

	public static void main(String[] args) {
		JsonSerializer<ReleaseType> relSer = (src, typeOfSrc, context) -> src == null ? null : new JsonPrimitive(src.getTypeId());
		JsonDeserializer<ReleaseType> relDeser = (json, typeOfT, context) -> json == null ? null : ReleaseType.getRelease(json.getAsInt());

		gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
				.registerTypeAdapter(ReleaseType.class, relSer)
				.registerTypeAdapter(ReleaseType.class, relDeser)
				.create();

		storageManager = new JsonStorageManager();

		SpringApplication.run(CLIMApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*args.getOptionNames().forEach(name -> {
			if(name.equalsIgnoreCase("wd") || name.equalsIgnoreCase("working-directory")) {
			}
		});*/
	}

	public static JsonStorageManager getStorageManager(){
		return storageManager;
	}

	public static Modpack getActiveModpack() {
		return getStorageManager().getLoadedModpack();
	}
}
