package geoquizz.player;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import geoquizz.player.bundary.PartieResource;
import geoquizz.player.bundary.SerieResource;
import geoquizz.player.entity.Partie;
import geoquizz.player.entity.Serie;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerApplicationTests {

	@Autowired
	private PartieResource pr;

	@Autowired
	private SerieResource sr;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setupContext() {
		sr.deleteAll();
		pr.deleteAll();
	}

	@Test
	public void getOneGame() {
		Serie s1 = new Serie("Nancy",3,"");
		s1.setId(UUID.randomUUID().toString());
		sr.save(s1);

		Partie p1 = new Partie("Joueur");
		p1.setId(UUID.randomUUID().toString());
		p1.setSerie(s1);
		pr.save(p1);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/game/"+p1.getId(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);	
		assertThat(response.getBody()).contains("Joueur");
	}

	@Test
	public void postNewGame() throws Exception {
		Serie s1 = new Serie("Nancy",3,"");
		s1.setId(UUID.randomUUID().toString());
		sr.save(s1);

		Partie p1 = new Partie("Joueur");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entite = new HttpEntity<>(this.toJsonString(p1),headers);
		
		ResponseEntity<Partie> response = restTemplate.postForEntity("/game/new?serie="+s1.getId(), entite, Partie.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		/*
		URI location = response.getHeaders().getLocation();
		response = restTemplate.getForEntity(location, Partie.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		*/
	}

	private String toJsonString(Object o) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(o);
	}

}
