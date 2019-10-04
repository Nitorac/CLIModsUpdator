package fr.nitorac.climodsupdator;

import com.google.gson.*;
import fr.nitorac.climodsupdator.models.Modpack;
import fr.nitorac.climodsupdator.models.ReleaseType;
import fr.nitorac.climodsupdator.storage.JsonStorageManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
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
		SpringApplication app = new SpringApplication(CLIMApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		System.out.println("        ___           ___                   ___     ");
		System.out.println("       /\\  \\         /\\__\\      ___        /\\__\\    ");
		System.out.println("      /::\\  \\       /:/  /     /\\  \\      /::|  |   ");
		System.out.println("     /:/\\:\\  \\     /:/  /      \\:\\  \\    /:|:|  |   ");
		System.out.println("    /:/  \\:\\  \\   /:/  /       /::\\__\\  /:/|:|__|__ ");
		System.out.println("   /:/__/ \\:\\__\\ /:/__/     __/:/\\/__/ /:/ |::::\\__\\");
		System.out.println("   \\:\\  \\  \\/__/ \\:\\  \\    /\\/:/  /    \\/__/~~/:/  /");
		System.out.println("    \\:\\  \\        \\:\\  \\   \\::/__/           /:/  / ");
		System.out.println("     \\:\\  \\        \\:\\  \\   \\:\\__\\          /:/  /  ");
		System.out.println("      \\:\\__\\        \\:\\__\\   \\/__/         /:/  /   ");
		System.out.println("       \\/__/         \\/__/                 \\/__/    ");
		if (getStorageManager().isModpackLoaded()) {
			System.out.println("");
			System.out.println("Le modpack '" + getStorageManager().getLoadedModpack().getName() + "' a été chargé automatiquement !");
		}
		System.out.println("");
		System.out.println("");
		app.run(args);
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
