package delivery.ze.partners;


import delivery.ze.partners.domain.exception.model.ErrorModel;
import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.domain.partner.PartnerRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CreatingPartnersIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    PartnerRepository repository;

    @Test
    @Order(1)
    void given_a_valid_payload_passed_should_save_in_database() throws Exception {
        MvcResult result = performPost(getValidPartnersRequest()).andExpect(status().isCreated()).andReturn();
        String response = result.getResponse().getContentAsString();
        Optional<Partner> partner = repository.findById(response);
        assertThat(partner).isNotEmpty();
    }

    @Test
    @Order(2)
    void given_a_invalid_payload_passed_should_return_bad_request() throws Exception {
        MvcResult result = performPost(getEmptyPayload()).andExpect(status().isBadRequest()).andReturn();
        ErrorModel errorModel = getErrorModel(result);
        assertThat(errorModel.getAttributes()).size().isEqualTo(5L);
        assertThat(errorModel.getAttributes()).containsOnlyKeys("tradingName", "address", "coverageArea", "ownerName", "document");
        assertThat(errorModel.getShortMessage()).isEqualTo("Validation failed");
    }

    @Test
    @Order(3)
    void given_partner_with_document_already_registered_should_return_bad_request() throws Exception {
        MvcResult result = performPost(getValidPartnersRequest()).andExpect(status().isBadRequest()).andReturn();
        ErrorModel errorModel = getErrorModel(result);
        assertThat(errorModel.getAttributes()).size().isEqualTo(1L);
        assertThat(errorModel.getAttributes()).containsOnlyKeys("document");
        assertThat(errorModel.getAttributes()).containsEntry("document", "already registered");
        assertThat(errorModel.getShortMessage()).isEqualTo("Validation failed");
    }

    @Test
    @Order(4)
    void given_partner_with_coverage_area_coordinates_empty_should_return_bad_request() throws Exception {
        MvcResult result = performPost(getPartnerWithoutCovaregeArea()).andExpect(status().isBadRequest()).andReturn();
        ErrorModel errorModel = getErrorModel(result);
        assertThat(errorModel.getAttributes()).size().isEqualTo(1L);
        assertThat(errorModel.getAttributes()).containsEntry("coverageArea", "coordinates is invalid");
        assertThat(errorModel.getShortMessage()).isEqualTo("Validation failed");
    }

    private ResultActions performPost(final String payload) throws Exception {
        return mockMvc.perform(post("/partners")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON));
    }
}
