package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import Model.Response;
import Model.ZipCodes;
import repository.ZipCodeRepository;

@Service
public class WeatherService {
    @Value("${api_key}")
    private String apiKey;
    @Autowired
    private ZipCodeRepository zipCodeRepository;

    
    public Response getForecast(String zipCode) {
    	
        String url = "http://api.openweathermap.org/data/2.5/weather?zip=" + 
            zipCode + "&units=imperial&appid=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        try {
        	createZip(zipCode);
        return restTemplate.getForObject(url, Response.class);
        }
        catch (HttpClientErrorException ex) {
            Response response = new Response();
            response.setName("error");
            return response;
        }
    }
    
    public ZipCodes findByZipCode(String zip) {
		return zipCodeRepository.findByZipCode(zip);
    	
    }
    
    public List<String> findMostRecent(){
    	return zipCodeRepository.findMostRecent();
    }
    
    public void createZip(String zip) {
    	ZipCodes zipCode = new ZipCodes();
    	zipCode.setZipCode(zip);
    	
    	zipCodeRepository.save(zipCode);
    	
    }
    
    
    
}
