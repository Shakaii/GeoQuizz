package geoquizz.backoffice;

import static org.assertj.core.api.Assertions.assertThat;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

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

import geoquizz.backoffice.bundary.PhotoResource;
import geoquizz.backoffice.bundary.SerieResource;
import geoquizz.backoffice.entity.Photo;
import geoquizz.backoffice.entity.Serie;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackofficeApplicationTests {

	@Autowired
	private SerieResource sr;

	@Autowired
	private PhotoResource pr;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setupContext() {
		sr.deleteAll();
		pr.deleteAll();
	}

	@Test
	public void getOneSerie() {
		Serie s1 = new Serie("Nancy",3,"");
		s1.setId(UUID.randomUUID().toString());
		sr.save(s1);
		
		ResponseEntity<String> response = restTemplate.getForEntity("/office/series/"+s1.getId(), String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);	
		assertThat(response.getBody()).contains("Nancy");
	}

	@Test
    public void getAllSeries() {
        Serie s1 = new Serie("Nancy",3,"");
        Serie s2 = new Serie("Metz",7,"");
        s1.setId(UUID.randomUUID().toString());
        sr.save(s1);
        s2.setId(UUID.randomUUID().toString());
        sr.save(s2);

        ResponseEntity<String> response = restTemplate.getForEntity("/office/series", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Nancy");
        assertThat(response.getBody()).contains("Metz");
        List<String> list = JsonPath.read(response.getBody(), "$..ville");
        
        assertThat(list).asList().hasSize(2);    
    }

	@Test
	public void notFoundAPI() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity("/office/series/qsdfghjkllnbvcx", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	
	@Test
	public void postSerie() throws Exception {
		Serie s1 = new Serie("Nancy",3, "");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entite = new HttpEntity<>(this.toJsonString(s1),headers);
		
		ResponseEntity<Serie> response = restTemplate.postForEntity("/office/series", entite, Serie.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		URI location = response.getHeaders().getLocation();
		response = restTemplate.getForEntity(location, Serie.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	private String toJsonString(Object o) throws Exception {
		ObjectMapper map = new ObjectMapper();
		return map.writeValueAsString(o);
	}

	@Test
	public void postPhotosSeries() throws Exception {
		Serie s1 = new Serie("Nancy",3, "");
		s1.setId(UUID.randomUUID().toString());
		sr.save(s1);

		Photo p1 = new Photo("Place Stanislas","url",10,10);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entite = new HttpEntity<>(this.toJsonString(p1),headers);
		
		ResponseEntity<String> response = restTemplate.postForEntity("/office/series/"+s1.getId()+"/photos", entite, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	public void putParamsSerie() throws Exception {
		Serie s1 = new Serie("Nancy",3,"");
		s1.setId(UUID.randomUUID().toString());
		sr.save(s1);
		
		int dist = 4;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entite = new HttpEntity<>(this.toJsonString(dist),headers);
		
		ResponseEntity<String> response1 = restTemplate.getForEntity("/office/series/"+s1.getId(), String.class);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response1.getBody()).contains("3");

        restTemplate.put("/office/series/"+s1.getId()+"/params", entite);
        
        ResponseEntity<String> response2 = restTemplate.getForEntity("/office/series/"+s1.getId(), String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).contains("4");
	}
}
