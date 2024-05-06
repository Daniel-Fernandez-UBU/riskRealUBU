package tfg.daniel.riskreal.riskrealApp.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:custom.properties")
public class PropertiesService {
	
	@Value("${json.quiz.file.path.lang}")
	private String jsonPath;
	// Método para añadir nuevos valores según el idioma
    public void addProperties(String key, String value, String idioma) {
        // Ruta del archivo properties según el idioma
    	//String messagesBasename = "src/main/resources/i18n/messages";
    	    	
        String filePath = jsonPath + idioma + ".properties";
        
        System.out.println(filePath);

        // Crear el archivo si no existe
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Archivo creado: " + filePath);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo: " + e.getMessage());
                return;
            }
        }

        // Crear un nuevo objeto Properties
        Properties properties = new Properties();
        


        try (FileInputStream inputStream = new FileInputStream(filePath);
                InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8")) {

               properties.load(reader);

               // Añadir o actualizar el valor
               properties.setProperty(key, value);

               try (FileOutputStream outputStream = new FileOutputStream(filePath);
                   OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8")) {

                   properties.store(writer, "Nuevos valores añadidos");
                   System.out.println("Nuevos valores añadidos exitosamente.");
               }
           } catch (IOException e) {
               System.err.println("Error al añadir nuevos valores: " + e.getMessage());
           }
    }

}
