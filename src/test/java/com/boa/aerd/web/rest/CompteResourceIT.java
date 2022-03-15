package com.boa.aerd.web.rest;

import com.boa.aerd.AerdApp;
import com.boa.aerd.domain.Compte;
import com.boa.aerd.repository.CompteRepository;
import com.boa.aerd.service.ClientService;
import com.boa.aerd.service.CompteService;
import com.boa.aerd.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.boa.aerd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompteResource} REST controller.
 */
@SpringBootTest(classes = AerdApp.class)
public class CompteResourceIT {

    private static final String DEFAULT_INTITULE_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_COMPTE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NCG = "AAAAAAAAAA";
    private static final String UPDATED_NCG = "BBBBBBBBBB";

    private static final String DEFAULT_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DEVISE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_EXPLOITANT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_EXPLOITANT = "BBBBBBBBBB";

    private static final String DEFAULT_OPT_VALIDATION = "AAAAAAAAAA";
    private static final String UPDATED_OPT_VALIDATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DAT_CRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DAT_CRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteService compteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCompteMockMvc;

    private Compte compte;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompteResource compteResource = new CompteResource(compteService);
        this.restCompteMockMvc = MockMvcBuilders.standaloneSetup(compteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte()
            .intituleCompte(DEFAULT_INTITULE_COMPTE)
            .cardType(DEFAULT_CARD_TYPE)
            .producctId(DEFAULT_PRODUCCT_ID)
            .agence(DEFAULT_AGENCE)
            .ncg(DEFAULT_NCG)
            .devise(DEFAULT_DEVISE)
            .codeExploitant(DEFAULT_CODE_EXPLOITANT)
            .optValidation(DEFAULT_OPT_VALIDATION)
            .datCre(DEFAULT_DAT_CRE);
        return compte;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createUpdatedEntity(EntityManager em) {
        Compte compte = new Compte()
            .intituleCompte(UPDATED_INTITULE_COMPTE)
            .cardType(UPDATED_CARD_TYPE)
            .producctId(UPDATED_PRODUCCT_ID)
            .agence(UPDATED_AGENCE)
            .ncg(UPDATED_NCG)
            .devise(UPDATED_DEVISE)
            .codeExploitant(UPDATED_CODE_EXPLOITANT)
            .optValidation(UPDATED_OPT_VALIDATION)
            .datCre(UPDATED_DAT_CRE);
        return compte;
    }

    @BeforeEach
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getIntituleCompte()).isEqualTo(DEFAULT_INTITULE_COMPTE);
        assertThat(testCompte.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testCompte.getProducctId()).isEqualTo(DEFAULT_PRODUCCT_ID);
        assertThat(testCompte.getAgence()).isEqualTo(DEFAULT_AGENCE);
        assertThat(testCompte.getNcg()).isEqualTo(DEFAULT_NCG);
        assertThat(testCompte.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testCompte.getCodeExploitant()).isEqualTo(DEFAULT_CODE_EXPLOITANT);
        assertThat(testCompte.getOptValidation()).isEqualTo(DEFAULT_OPT_VALIDATION);
        assertThat(testCompte.getDatCre()).isEqualTo(DEFAULT_DAT_CRE);
    }

    @Test
    @Transactional
    public void createCompteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // Create the Compte with an existing ID
        compte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc.perform(post("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc.perform(get("/api/comptes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].intituleCompte").value(hasItem(DEFAULT_INTITULE_COMPTE)))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].producctId").value(hasItem(DEFAULT_PRODUCCT_ID)))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE)))
            .andExpect(jsonPath("$.[*].ncg").value(hasItem(DEFAULT_NCG)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE)))
            .andExpect(jsonPath("$.[*].codeExploitant").value(hasItem(DEFAULT_CODE_EXPLOITANT)))
            .andExpect(jsonPath("$.[*].optValidation").value(hasItem(DEFAULT_OPT_VALIDATION)))
            .andExpect(jsonPath("$.[*].datCre").value(hasItem(DEFAULT_DAT_CRE.toString())));
    }
    
    @Test
    @Transactional
    public void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.intituleCompte").value(DEFAULT_INTITULE_COMPTE))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE))
            .andExpect(jsonPath("$.producctId").value(DEFAULT_PRODUCCT_ID))
            .andExpect(jsonPath("$.agence").value(DEFAULT_AGENCE))
            .andExpect(jsonPath("$.ncg").value(DEFAULT_NCG))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE))
            .andExpect(jsonPath("$.codeExploitant").value(DEFAULT_CODE_EXPLOITANT))
            .andExpect(jsonPath("$.optValidation").value(DEFAULT_OPT_VALIDATION))
            .andExpect(jsonPath("$.datCre").value(DEFAULT_DAT_CRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get("/api/comptes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompte() throws Exception {
        // Initialize the database
        compteService.save(null, null);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findById(compte.getId()).get();
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte
            .intituleCompte(UPDATED_INTITULE_COMPTE)
            .cardType(UPDATED_CARD_TYPE)
            .producctId(UPDATED_PRODUCCT_ID)
            .agence(UPDATED_AGENCE)
            .ncg(UPDATED_NCG)
            .devise(UPDATED_DEVISE)
            .codeExploitant(UPDATED_CODE_EXPLOITANT)
            .optValidation(UPDATED_OPT_VALIDATION)
            .datCre(UPDATED_DAT_CRE);

        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompte)))
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getIntituleCompte()).isEqualTo(UPDATED_INTITULE_COMPTE);
        assertThat(testCompte.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testCompte.getProducctId()).isEqualTo(UPDATED_PRODUCCT_ID);
        assertThat(testCompte.getAgence()).isEqualTo(UPDATED_AGENCE);
        assertThat(testCompte.getNcg()).isEqualTo(UPDATED_NCG);
        assertThat(testCompte.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testCompte.getCodeExploitant()).isEqualTo(UPDATED_CODE_EXPLOITANT);
        assertThat(testCompte.getOptValidation()).isEqualTo(UPDATED_OPT_VALIDATION);
        assertThat(testCompte.getDatCre()).isEqualTo(UPDATED_DAT_CRE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Create the Compte

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteMockMvc.perform(put("/api/comptes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compte)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompte() throws Exception {
        // Initialize the database
        compteService.save(null, null);

        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Delete the compte
        restCompteMockMvc.perform(delete("/api/comptes/{id}", compte.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
