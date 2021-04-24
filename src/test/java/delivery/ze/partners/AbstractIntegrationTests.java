package delivery.ze.partners;

import com.fasterxml.jackson.databind.ObjectMapper;
import delivery.ze.partners.domain.exception.model.ErrorModel;
import delivery.ze.partners.domain.partner.Partner;
import delivery.ze.partners.restapi.data.PartnersRequest;
import delivery.ze.partners.restapi.data.PartnersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Value("classpath:valid-partner.json")
    private Resource partnerFile;

    @Value("classpath:invalid-coverage-area-partner.json")
    private Resource invalidCoverageAreaFile;

    protected String getValidPartnersRequest() throws IOException {
        return asString(partnerFile);
    }

    protected String getEmptyPayload() {
        return "{}";
    }

    protected String getPartnerWithoutCovaregeArea() {
        return asString(invalidCoverageAreaFile);
    }

    private static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected ErrorModel getErrorModel(final MvcResult result) throws java.io.IOException {
        String response = result.getResponse().getContentAsString();
        return mapper.readValue(response.getBytes(), ErrorModel.class);
    }

    protected Partner getPartnerFromJson() throws IOException {
        return mapper.readValue(partnerFile.getFile(), Partner.class);
    }
}
