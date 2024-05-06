package tfg.daniel.riskreal.riskrealApp.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
	
	@Autowired
    private Environment environment;
	
	// Método para añadir nuevos valores según el idioma
    public void addProperties(String idioma) {
        // Ruta del archivo properties según el idioma
    	String messagesBasename = environment.getProperty("spring.messages.basename");
        String filePath = messagesBasename + idioma + ".properties";

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
        


        try {
            // Cargar el archivo existente
            FileInputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
            
            String key = "prueba";
            
            int intValue = Integer.parseInt(properties.getProperty(key));
            
            intValue += 1;
            
            String value = String.valueOf(intValue);

            // Añadir nuevos valores según el idioma
            properties.setProperty(key, value);


            // Guardar los cambios en el archivo
            FileOutputStream outputStream = new FileOutputStream(file);
            properties.store(outputStream, "Nuevos valores añadidos");
            outputStream.close();

            System.out.println("Nuevos valores añadidos exitosamente para el idioma " + idioma + ".");
        } catch (IOException e) {
            System.err.println("Error al añadir nuevos valores: " + e.getMessage());
        }
    }

}
