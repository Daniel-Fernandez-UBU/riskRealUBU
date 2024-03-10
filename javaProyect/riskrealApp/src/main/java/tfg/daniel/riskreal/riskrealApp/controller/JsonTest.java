package tfg.daniel.riskreal.riskrealApp.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping
public class JsonTest {
	
	@GetMapping("/json")
    public ResponseEntity<Map<String, Object>> getRadarData() throws IOException {
        ClassPathResource staticDataResource = new ClassPathResource("schema_cuestionario_v1.json");
        String staticDataString = IOUtils.toString(staticDataResource.getInputStream(), StandardCharsets.UTF_8);

        return new ResponseEntity<>(
            new JSONObject(staticDataString).toMap(),
            HttpStatus.OK
        );
	}
}
