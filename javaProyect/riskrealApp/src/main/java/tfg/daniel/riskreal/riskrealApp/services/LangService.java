package tfg.daniel.riskreal.riskrealApp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:custom.properties")
public class LangService {
	
	@Value("${languages.availables}")
	String langs;
	
	public String getLangs() {
		return langs;
	}

	public void setLangs(String langs) {
		this.langs = langs;
	}

	public void showLangs() {
		System.out.println(langs);
	}
	
}
