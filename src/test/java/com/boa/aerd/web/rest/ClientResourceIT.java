package com.boa.aerd.web.rest;

import com.boa.aerd.AerdApp;
import com.boa.aerd.domain.Client;
import com.boa.aerd.repository.ClientRepository;
import com.boa.aerd.service.ClientService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.boa.aerd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClientResource} REST controller.
 */
@SpringBootTest(classes = AerdApp.class)
public class ClientResourceIT {

    private static final String DEFAULT_COD_CIVIL = "AAAAAAAAAA";
    private static final String UPDATED_COD_CIVIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MARI = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MARI = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MARI = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MARI = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_MERE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_MERE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PERE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PERE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_PERE = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_PERE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEP_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_DEP_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_COMM_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_COMM_NAISS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS_NAISS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_NAISS = "BBBBBBBBBB";

    private static final String DEFAULT_NATION = "AAAAAAAAAA";
    private static final String UPDATED_NATION = "BBBBBBBBBB";

    private static final String DEFAULT_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE = "BBBBBBBBBB";

    private static final String DEFAULT_SITU = "AAAAAAAAAA";
    private static final String UPDATED_SITU = "BBBBBBBBBB";

    private static final String DEFAULT_PROF = "AAAAAAAAAA";
    private static final String UPDATED_PROF = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUE = "BBBBBBBBBB";

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_ID = "AAAAAAAAAA";
    private static final String UPDATED_NUM_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAT_LIVR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_LIVR = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DAT_VALID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_VALID = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_COMM_ID = "AAAAAAAAAA";
    private static final String UPDATED_COMM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_4 = "BBBBBBBBBB";

    private static final String DEFAULT_COD_COUR = "AAAAAAAAAA";
    private static final String UPDATED_COD_COUR = "BBBBBBBBBB";

    private static final String DEFAULT_COD_POST = "AAAAAAAAAA";
    private static final String UPDATED_COD_POST = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_DEP_RES = "AAAAAAAAAA";
    private static final String UPDATED_DEP_RES = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_PROF = "AAAAAAAAAA";
    private static final String UPDATED_TEL_PROF = "BBBBBBBBBB";

    private static final String DEFAULT_TELE_FAX = "AAAAAAAAAA";
    private static final String UPDATED_TELE_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_NCG = "AAAAAAAAAA";
    private static final String UPDATED_NCG = "BBBBBBBBBB";

    private static final String DEFAULT_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DEVISE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_EXPLOITANT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_EXPLOITANT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAT_CRE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAT_CRE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAYS_CPT = "AAAAAAAAAA";
    private static final String UPDATED_PAYS_CPT = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_ID_CLIENT = "BBBBBBBBBB";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

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

    private MockMvc restClientMockMvc;

    private Client client;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientResource clientResource = new ClientResource(clientService);
        this.restClientMockMvc = MockMvcBuilders.standaloneSetup(clientResource)
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
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .codCivil(DEFAULT_COD_CIVIL)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .nomMari(DEFAULT_NOM_MARI)
            .prenomMari(DEFAULT_PRENOM_MARI)
            .nomMere(DEFAULT_NOM_MERE)
            .prenomMere(DEFAULT_PRENOM_MERE)
            .nomPere(DEFAULT_NOM_PERE)
            .prenomPere(DEFAULT_PRENOM_PERE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .depNaissance(DEFAULT_DEP_NAISSANCE)
            .commNaiss(DEFAULT_COMM_NAISS)
            .paysNaiss(DEFAULT_PAYS_NAISS)
            .nation(DEFAULT_NATION)
            .sexe(DEFAULT_SEXE)
            .situ(DEFAULT_SITU)
            .prof(DEFAULT_PROF)
            .langue(DEFAULT_LANGUE)
            .relation(DEFAULT_RELATION)
            .identite(DEFAULT_IDENTITE)
            .numId(DEFAULT_NUM_ID)
            .datLivr(DEFAULT_DAT_LIVR)
            .datValid(DEFAULT_DAT_VALID)
            .payId(DEFAULT_PAY_ID)
            .commId(DEFAULT_COMM_ID)
            .addr1(DEFAULT_ADDR_1)
            .addr2(DEFAULT_ADDR_2)
            .addr3(DEFAULT_ADDR_3)
            .addr4(DEFAULT_ADDR_4)
            .codCour(DEFAULT_COD_COUR)
            .codPost(DEFAULT_COD_POST)
            .mail(DEFAULT_MAIL)
            .pays(DEFAULT_PAYS)
            .depRes(DEFAULT_DEP_RES)
            .tel(DEFAULT_TEL)
            .telProf(DEFAULT_TEL_PROF)
            .teleFax(DEFAULT_TELE_FAX)
            .agence(DEFAULT_AGENCE)
            .ncg(DEFAULT_NCG)
            .devise(DEFAULT_DEVISE)
            .codeExploitant(DEFAULT_CODE_EXPLOITANT)
            .datCre(DEFAULT_DAT_CRE)
            .paysCpt(DEFAULT_PAYS_CPT)
            .idClient(DEFAULT_ID_CLIENT);
        return client;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .codCivil(UPDATED_COD_CIVIL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomMari(UPDATED_NOM_MARI)
            .prenomMari(UPDATED_PRENOM_MARI)
            .nomMere(UPDATED_NOM_MERE)
            .prenomMere(UPDATED_PRENOM_MERE)
            .nomPere(UPDATED_NOM_PERE)
            .prenomPere(UPDATED_PRENOM_PERE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .depNaissance(UPDATED_DEP_NAISSANCE)
            .commNaiss(UPDATED_COMM_NAISS)
            .paysNaiss(UPDATED_PAYS_NAISS)
            .nation(UPDATED_NATION)
            .sexe(UPDATED_SEXE)
            .situ(UPDATED_SITU)
            .prof(UPDATED_PROF)
            .langue(UPDATED_LANGUE)
            .relation(UPDATED_RELATION)
            .identite(UPDATED_IDENTITE)
            .numId(UPDATED_NUM_ID)
            .datLivr(UPDATED_DAT_LIVR)
            .datValid(UPDATED_DAT_VALID)
            .payId(UPDATED_PAY_ID)
            .commId(UPDATED_COMM_ID)
            .addr1(UPDATED_ADDR_1)
            .addr2(UPDATED_ADDR_2)
            .addr3(UPDATED_ADDR_3)
            .addr4(UPDATED_ADDR_4)
            .codCour(UPDATED_COD_COUR)
            .codPost(UPDATED_COD_POST)
            .mail(UPDATED_MAIL)
            .pays(UPDATED_PAYS)
            .depRes(UPDATED_DEP_RES)
            .tel(UPDATED_TEL)
            .telProf(UPDATED_TEL_PROF)
            .teleFax(UPDATED_TELE_FAX)
            .agence(UPDATED_AGENCE)
            .ncg(UPDATED_NCG)
            .devise(UPDATED_DEVISE)
            .codeExploitant(UPDATED_CODE_EXPLOITANT)
            .datCre(UPDATED_DAT_CRE)
            .paysCpt(UPDATED_PAYS_CPT)
            .idClient(UPDATED_ID_CLIENT);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    public void createClient() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isCreated());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate + 1);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getCodCivil()).isEqualTo(DEFAULT_COD_CIVIL);
        assertThat(testClient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testClient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testClient.getNomMari()).isEqualTo(DEFAULT_NOM_MARI);
        assertThat(testClient.getPrenomMari()).isEqualTo(DEFAULT_PRENOM_MARI);
        assertThat(testClient.getNomMere()).isEqualTo(DEFAULT_NOM_MERE);
        assertThat(testClient.getPrenomMere()).isEqualTo(DEFAULT_PRENOM_MERE);
        assertThat(testClient.getNomPere()).isEqualTo(DEFAULT_NOM_PERE);
        assertThat(testClient.getPrenomPere()).isEqualTo(DEFAULT_PRENOM_PERE);
        assertThat(testClient.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testClient.getDepNaissance()).isEqualTo(DEFAULT_DEP_NAISSANCE);
        assertThat(testClient.getCommNaiss()).isEqualTo(DEFAULT_COMM_NAISS);
        assertThat(testClient.getPaysNaiss()).isEqualTo(DEFAULT_PAYS_NAISS);
        assertThat(testClient.getNation()).isEqualTo(DEFAULT_NATION);
        assertThat(testClient.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testClient.getSitu()).isEqualTo(DEFAULT_SITU);
        assertThat(testClient.getProf()).isEqualTo(DEFAULT_PROF);
        assertThat(testClient.getLangue()).isEqualTo(DEFAULT_LANGUE);
        assertThat(testClient.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testClient.getIdentite()).isEqualTo(DEFAULT_IDENTITE);
        assertThat(testClient.getNumId()).isEqualTo(DEFAULT_NUM_ID);
        assertThat(testClient.getDatLivr()).isEqualTo(DEFAULT_DAT_LIVR);
        assertThat(testClient.getDatValid()).isEqualTo(DEFAULT_DAT_VALID);
        assertThat(testClient.getPayId()).isEqualTo(DEFAULT_PAY_ID);
        assertThat(testClient.getCommId()).isEqualTo(DEFAULT_COMM_ID);
        assertThat(testClient.getAddr1()).isEqualTo(DEFAULT_ADDR_1);
        assertThat(testClient.getAddr2()).isEqualTo(DEFAULT_ADDR_2);
        assertThat(testClient.getAddr3()).isEqualTo(DEFAULT_ADDR_3);
        assertThat(testClient.getAddr4()).isEqualTo(DEFAULT_ADDR_4);
        assertThat(testClient.getCodCour()).isEqualTo(DEFAULT_COD_COUR);
        assertThat(testClient.getCodPost()).isEqualTo(DEFAULT_COD_POST);
        assertThat(testClient.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testClient.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testClient.getDepRes()).isEqualTo(DEFAULT_DEP_RES);
        assertThat(testClient.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testClient.getTelProf()).isEqualTo(DEFAULT_TEL_PROF);
        assertThat(testClient.getTeleFax()).isEqualTo(DEFAULT_TELE_FAX);
        assertThat(testClient.getAgence()).isEqualTo(DEFAULT_AGENCE);
        assertThat(testClient.getNcg()).isEqualTo(DEFAULT_NCG);
        assertThat(testClient.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testClient.getCodeExploitant()).isEqualTo(DEFAULT_CODE_EXPLOITANT);
        assertThat(testClient.getDatCre()).isEqualTo(DEFAULT_DAT_CRE);
        assertThat(testClient.getPaysCpt()).isEqualTo(DEFAULT_PAYS_CPT);
        assertThat(testClient.getIdClient()).isEqualTo(DEFAULT_ID_CLIENT);
    }

    @Test
    @Transactional
    public void createClientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientRepository.findAll().size();

        // Create the Client with an existing ID
        client.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].codCivil").value(hasItem(DEFAULT_COD_CIVIL)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nomMari").value(hasItem(DEFAULT_NOM_MARI)))
            .andExpect(jsonPath("$.[*].prenomMari").value(hasItem(DEFAULT_PRENOM_MARI)))
            .andExpect(jsonPath("$.[*].nomMere").value(hasItem(DEFAULT_NOM_MERE)))
            .andExpect(jsonPath("$.[*].prenomMere").value(hasItem(DEFAULT_PRENOM_MERE)))
            .andExpect(jsonPath("$.[*].nomPere").value(hasItem(DEFAULT_NOM_PERE)))
            .andExpect(jsonPath("$.[*].prenomPere").value(hasItem(DEFAULT_PRENOM_PERE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].depNaissance").value(hasItem(DEFAULT_DEP_NAISSANCE)))
            .andExpect(jsonPath("$.[*].commNaiss").value(hasItem(DEFAULT_COMM_NAISS)))
            .andExpect(jsonPath("$.[*].paysNaiss").value(hasItem(DEFAULT_PAYS_NAISS)))
            .andExpect(jsonPath("$.[*].nation").value(hasItem(DEFAULT_NATION)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)))
            .andExpect(jsonPath("$.[*].situ").value(hasItem(DEFAULT_SITU)))
            .andExpect(jsonPath("$.[*].prof").value(hasItem(DEFAULT_PROF)))
            .andExpect(jsonPath("$.[*].langue").value(hasItem(DEFAULT_LANGUE)))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].identite").value(hasItem(DEFAULT_IDENTITE)))
            .andExpect(jsonPath("$.[*].numId").value(hasItem(DEFAULT_NUM_ID)))
            .andExpect(jsonPath("$.[*].datLivr").value(hasItem(DEFAULT_DAT_LIVR.toString())))
            .andExpect(jsonPath("$.[*].datValid").value(hasItem(DEFAULT_DAT_VALID.toString())))
            .andExpect(jsonPath("$.[*].payId").value(hasItem(DEFAULT_PAY_ID)))
            .andExpect(jsonPath("$.[*].commId").value(hasItem(DEFAULT_COMM_ID)))
            .andExpect(jsonPath("$.[*].addr1").value(hasItem(DEFAULT_ADDR_1)))
            .andExpect(jsonPath("$.[*].addr2").value(hasItem(DEFAULT_ADDR_2)))
            .andExpect(jsonPath("$.[*].addr3").value(hasItem(DEFAULT_ADDR_3)))
            .andExpect(jsonPath("$.[*].addr4").value(hasItem(DEFAULT_ADDR_4)))
            .andExpect(jsonPath("$.[*].codCour").value(hasItem(DEFAULT_COD_COUR)))
            .andExpect(jsonPath("$.[*].codPost").value(hasItem(DEFAULT_COD_POST)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].depRes").value(hasItem(DEFAULT_DEP_RES)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].telProf").value(hasItem(DEFAULT_TEL_PROF)))
            .andExpect(jsonPath("$.[*].teleFax").value(hasItem(DEFAULT_TELE_FAX)))
            .andExpect(jsonPath("$.[*].agence").value(hasItem(DEFAULT_AGENCE)))
            .andExpect(jsonPath("$.[*].ncg").value(hasItem(DEFAULT_NCG)))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE)))
            .andExpect(jsonPath("$.[*].codeExploitant").value(hasItem(DEFAULT_CODE_EXPLOITANT)))
            .andExpect(jsonPath("$.[*].datCre").value(hasItem(DEFAULT_DAT_CRE.toString())))
            .andExpect(jsonPath("$.[*].paysCpt").value(hasItem(DEFAULT_PAYS_CPT)))
            .andExpect(jsonPath("$.[*].idClient").value(hasItem(DEFAULT_ID_CLIENT)));
    }
    
    @Test
    @Transactional
    public void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.codCivil").value(DEFAULT_COD_CIVIL))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nomMari").value(DEFAULT_NOM_MARI))
            .andExpect(jsonPath("$.prenomMari").value(DEFAULT_PRENOM_MARI))
            .andExpect(jsonPath("$.nomMere").value(DEFAULT_NOM_MERE))
            .andExpect(jsonPath("$.prenomMere").value(DEFAULT_PRENOM_MERE))
            .andExpect(jsonPath("$.nomPere").value(DEFAULT_NOM_PERE))
            .andExpect(jsonPath("$.prenomPere").value(DEFAULT_PRENOM_PERE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.depNaissance").value(DEFAULT_DEP_NAISSANCE))
            .andExpect(jsonPath("$.commNaiss").value(DEFAULT_COMM_NAISS))
            .andExpect(jsonPath("$.paysNaiss").value(DEFAULT_PAYS_NAISS))
            .andExpect(jsonPath("$.nation").value(DEFAULT_NATION))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE))
            .andExpect(jsonPath("$.situ").value(DEFAULT_SITU))
            .andExpect(jsonPath("$.prof").value(DEFAULT_PROF))
            .andExpect(jsonPath("$.langue").value(DEFAULT_LANGUE))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION))
            .andExpect(jsonPath("$.identite").value(DEFAULT_IDENTITE))
            .andExpect(jsonPath("$.numId").value(DEFAULT_NUM_ID))
            .andExpect(jsonPath("$.datLivr").value(DEFAULT_DAT_LIVR.toString()))
            .andExpect(jsonPath("$.datValid").value(DEFAULT_DAT_VALID.toString()))
            .andExpect(jsonPath("$.payId").value(DEFAULT_PAY_ID))
            .andExpect(jsonPath("$.commId").value(DEFAULT_COMM_ID))
            .andExpect(jsonPath("$.addr1").value(DEFAULT_ADDR_1))
            .andExpect(jsonPath("$.addr2").value(DEFAULT_ADDR_2))
            .andExpect(jsonPath("$.addr3").value(DEFAULT_ADDR_3))
            .andExpect(jsonPath("$.addr4").value(DEFAULT_ADDR_4))
            .andExpect(jsonPath("$.codCour").value(DEFAULT_COD_COUR))
            .andExpect(jsonPath("$.codPost").value(DEFAULT_COD_POST))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.depRes").value(DEFAULT_DEP_RES))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.telProf").value(DEFAULT_TEL_PROF))
            .andExpect(jsonPath("$.teleFax").value(DEFAULT_TELE_FAX))
            .andExpect(jsonPath("$.agence").value(DEFAULT_AGENCE))
            .andExpect(jsonPath("$.ncg").value(DEFAULT_NCG))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE))
            .andExpect(jsonPath("$.codeExploitant").value(DEFAULT_CODE_EXPLOITANT))
            .andExpect(jsonPath("$.datCre").value(DEFAULT_DAT_CRE.toString()))
            .andExpect(jsonPath("$.paysCpt").value(DEFAULT_PAYS_CPT))
            .andExpect(jsonPath("$.idClient").value(DEFAULT_ID_CLIENT));
    }

    @Test
    @Transactional
    public void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClient() throws Exception {
        // Initialize the database
        clientService.save(client, null);

        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).get();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .codCivil(UPDATED_COD_CIVIL)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomMari(UPDATED_NOM_MARI)
            .prenomMari(UPDATED_PRENOM_MARI)
            .nomMere(UPDATED_NOM_MERE)
            .prenomMere(UPDATED_PRENOM_MERE)
            .nomPere(UPDATED_NOM_PERE)
            .prenomPere(UPDATED_PRENOM_PERE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .depNaissance(UPDATED_DEP_NAISSANCE)
            .commNaiss(UPDATED_COMM_NAISS)
            .paysNaiss(UPDATED_PAYS_NAISS)
            .nation(UPDATED_NATION)
            .sexe(UPDATED_SEXE)
            .situ(UPDATED_SITU)
            .prof(UPDATED_PROF)
            .langue(UPDATED_LANGUE)
            .relation(UPDATED_RELATION)
            .identite(UPDATED_IDENTITE)
            .numId(UPDATED_NUM_ID)
            .datLivr(UPDATED_DAT_LIVR)
            .datValid(UPDATED_DAT_VALID)
            .payId(UPDATED_PAY_ID)
            .commId(UPDATED_COMM_ID)
            .addr1(UPDATED_ADDR_1)
            .addr2(UPDATED_ADDR_2)
            .addr3(UPDATED_ADDR_3)
            .addr4(UPDATED_ADDR_4)
            .codCour(UPDATED_COD_COUR)
            .codPost(UPDATED_COD_POST)
            .mail(UPDATED_MAIL)
            .pays(UPDATED_PAYS)
            .depRes(UPDATED_DEP_RES)
            .tel(UPDATED_TEL)
            .telProf(UPDATED_TEL_PROF)
            .teleFax(UPDATED_TELE_FAX)
            .agence(UPDATED_AGENCE)
            .ncg(UPDATED_NCG)
            .devise(UPDATED_DEVISE)
            .codeExploitant(UPDATED_CODE_EXPLOITANT)
            .datCre(UPDATED_DAT_CRE)
            .paysCpt(UPDATED_PAYS_CPT)
            .idClient(UPDATED_ID_CLIENT);

        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClient)))
            .andExpect(status().isOk());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
        Client testClient = clientList.get(clientList.size() - 1);
        assertThat(testClient.getCodCivil()).isEqualTo(UPDATED_COD_CIVIL);
        assertThat(testClient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testClient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testClient.getNomMari()).isEqualTo(UPDATED_NOM_MARI);
        assertThat(testClient.getPrenomMari()).isEqualTo(UPDATED_PRENOM_MARI);
        assertThat(testClient.getNomMere()).isEqualTo(UPDATED_NOM_MERE);
        assertThat(testClient.getPrenomMere()).isEqualTo(UPDATED_PRENOM_MERE);
        assertThat(testClient.getNomPere()).isEqualTo(UPDATED_NOM_PERE);
        assertThat(testClient.getPrenomPere()).isEqualTo(UPDATED_PRENOM_PERE);
        assertThat(testClient.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testClient.getDepNaissance()).isEqualTo(UPDATED_DEP_NAISSANCE);
        assertThat(testClient.getCommNaiss()).isEqualTo(UPDATED_COMM_NAISS);
        assertThat(testClient.getPaysNaiss()).isEqualTo(UPDATED_PAYS_NAISS);
        assertThat(testClient.getNation()).isEqualTo(UPDATED_NATION);
        assertThat(testClient.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testClient.getSitu()).isEqualTo(UPDATED_SITU);
        assertThat(testClient.getProf()).isEqualTo(UPDATED_PROF);
        assertThat(testClient.getLangue()).isEqualTo(UPDATED_LANGUE);
        assertThat(testClient.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testClient.getIdentite()).isEqualTo(UPDATED_IDENTITE);
        assertThat(testClient.getNumId()).isEqualTo(UPDATED_NUM_ID);
        assertThat(testClient.getDatLivr()).isEqualTo(UPDATED_DAT_LIVR);
        assertThat(testClient.getDatValid()).isEqualTo(UPDATED_DAT_VALID);
        assertThat(testClient.getPayId()).isEqualTo(UPDATED_PAY_ID);
        assertThat(testClient.getCommId()).isEqualTo(UPDATED_COMM_ID);
        assertThat(testClient.getAddr1()).isEqualTo(UPDATED_ADDR_1);
        assertThat(testClient.getAddr2()).isEqualTo(UPDATED_ADDR_2);
        assertThat(testClient.getAddr3()).isEqualTo(UPDATED_ADDR_3);
        assertThat(testClient.getAddr4()).isEqualTo(UPDATED_ADDR_4);
        assertThat(testClient.getCodCour()).isEqualTo(UPDATED_COD_COUR);
        assertThat(testClient.getCodPost()).isEqualTo(UPDATED_COD_POST);
        assertThat(testClient.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testClient.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testClient.getDepRes()).isEqualTo(UPDATED_DEP_RES);
        assertThat(testClient.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testClient.getTelProf()).isEqualTo(UPDATED_TEL_PROF);
        assertThat(testClient.getTeleFax()).isEqualTo(UPDATED_TELE_FAX);
        assertThat(testClient.getAgence()).isEqualTo(UPDATED_AGENCE);
        assertThat(testClient.getNcg()).isEqualTo(UPDATED_NCG);
        assertThat(testClient.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testClient.getCodeExploitant()).isEqualTo(UPDATED_CODE_EXPLOITANT);
        assertThat(testClient.getDatCre()).isEqualTo(UPDATED_DAT_CRE);
        assertThat(testClient.getPaysCpt()).isEqualTo(UPDATED_PAYS_CPT);
        assertThat(testClient.getIdClient()).isEqualTo(UPDATED_ID_CLIENT);
    }

    @Test
    @Transactional
    public void updateNonExistingClient() throws Exception {
        int databaseSizeBeforeUpdate = clientRepository.findAll().size();

        // Create the Client

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(client)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClient() throws Exception {
        // Initialize the database
        clientService.save(client, null);

        int databaseSizeBeforeDelete = clientRepository.findAll().size();

        // Delete the client
        restClientMockMvc.perform(delete("/api/clients/{id}", client.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
