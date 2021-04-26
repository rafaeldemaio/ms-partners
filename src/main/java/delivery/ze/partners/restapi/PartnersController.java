package delivery.ze.partners.restapi;

import delivery.ze.partners.application.PartnersService;
import delivery.ze.partners.infra.converter.PartnersConverter;
import delivery.ze.partners.restapi.data.PartnersRequest;
import delivery.ze.partners.restapi.data.PartnersResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Partners Controller", description = "Controller that handle all coming requests")
@RestController
@RequestMapping("/partners")
@AllArgsConstructor
public class PartnersController {

    private final PartnersService partnersService;
    private final PartnersConverter converter;

    @Operation(description = "retrieve a specific partner by its id", summary = "Retrieve partner by Id")
    @GetMapping("/{id}")
    public PartnersResponse getPartner(@PathVariable final String id) {
        return converter.toResponse(partnersService.search(id));
    }

    @Operation(description = "Create a new partner", summary = "Create partner")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createPartner(@RequestBody @Valid final PartnersRequest request) {
        log.info("creating a partner");
        return partnersService.create(request);
    }

    @Operation(description = "Search the nearest partner which the coverage area includes the location", summary = "Search partner")
    @GetMapping
    public PartnersResponse searchPartner(@RequestParam("lat") final Double lat, @RequestParam("lng") final Double lng) {
        return converter.toResponse(partnersService.search(lng, lat));
    }

}
