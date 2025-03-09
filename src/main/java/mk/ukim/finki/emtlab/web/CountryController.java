package mk.ukim.finki.emtlab.web;

import mk.ukim.finki.emtlab.model.Country;
import mk.ukim.finki.emtlab.model.dtos.CountryDto;
import mk.ukim.finki.emtlab.service.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    
    private final CountryService countryService;
    
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    
    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Country country = countryService.getCountryById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }
    
    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestBody CountryDto countryDto) {
        Country country = countryService.save(countryDto);
        return ResponseEntity.ok(country);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        Country country = countryService.update(id, countryDto);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(country);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.noContent().build();
    }
}
