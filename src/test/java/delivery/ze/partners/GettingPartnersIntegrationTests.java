package delivery.ze.partners;

import delivery.ze.partners.domain.exception.model.ErrorModel;
import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.domain.partner.PartnerRepository;
import delivery.ze.partners.restapi.data.PartnersResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GettingPartnersIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private PartnerRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void ensureIndexes() throws Exception {
        Partner partner = getPartnerFromJson();
        repository.save(partner);
        mongoTemplate.indexOps(Partner.class).ensureIndex(new GeospatialIndex("address"));
    }

    @AfterEach
    private void emptyDatabase() throws Exception {
        repository.deleteAll();
    }

    @Test
    void given_a_registered_id_should_return_the_partner() throws Exception {

        MvcResult result = performGet(get("/partners/1"))
            .andExpect(status().isOk())
            .andReturn();
        assertPartnerResponse(getPartnersResponse(result));
    }

    @Test
    void given_unregistered_id_should_return_not_found() throws Exception {
        MvcResult result = performGet(get("/partners/329ds23"))
            .andExpect(status().isNotFound())
            .andReturn();
        ErrorModel errorModel = getErrorModel(result);
        assertThat(errorModel.getMessage()).isEqualTo("No partners found for specified id");
    }

    @Test
    void given_near_point_should_find_shortest_partner() throws Exception {
        Partner partner = getPartnerFromJson();
        repository.save(partner);
        MvcResult result = performGet(get("/partners")
            .param("lng", "-0.5")
            .param("lat", "-0.5"))
            .andExpect(status().isOk())
            .andReturn();
        PartnersResponse response = getPartnersResponse(result);
        partner.setId(response.getId());
        assertPartnerResponse(response);
    }

    @Test
    void given_faraway_point_should_return_not_found() throws Exception {
        MvcResult result = performGet(get("/partners")
            .param("lng", "30.36556")
            .param("lat", "21.00001"))
            .andExpect(status().isNotFound())
            .andReturn();
        ErrorModel errorModel = getErrorModel(result);
        assertThat(errorModel.getMessage()).isEqualTo("No partners found for specified location");
    }

    private ResultActions performGet(final MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON));
    }

    private PartnersResponse getPartnersResponse(final MvcResult result) throws IOException {
        String response = result.getResponse().getContentAsString();
        return mapper.readValue(response.getBytes(), PartnersResponse.class);
    }

    private void assertPartnerResponse(PartnersResponse response) throws IOException {
        assertThat(response).isNotNull();
        Partner partner = getPartnerFromJson();
        assertThat(response.getDocument()).isEqualTo(partner.getDocument());
        assertThat(response.getOwnerName()).isEqualTo(partner.getOwnerName());
        assertThat(response.getTradingName()).isEqualTo(partner.getTradingName());
    }
}
