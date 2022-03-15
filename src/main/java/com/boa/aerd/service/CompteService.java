package com.boa.aerd.service;

import com.boa.aerd.domain.Client;
import com.boa.aerd.domain.Compte;
import com.boa.aerd.domain.ParamFiliale;
import com.boa.aerd.domain.Tracking;
import com.boa.aerd.repository.CompteRepository;
import com.boa.aerd.request.CompteRequest;
import com.boa.aerd.response.GenericResponse;
import com.boa.aerd.response.GenericResponseCompte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * Service Implementation for managing {@link Compte}.
 */
@Service
@Transactional
public class CompteService {

    private final Logger log = LoggerFactory.getLogger(CompteService.class);

    private final CompteRepository compteRepository;

    private final ClientService clientService;

    private final ParamFilialeService filialeService;

    private final TrackingService trackingService;

    private final UserService userService;

    public CompteService(CompteRepository compteRepository, ClientService clientService, 
    ParamFilialeService filialeService, UserService userService, TrackingService trackingService) {
        this.compteRepository = compteRepository;
        this.clientService = clientService;
        this.filialeService = filialeService;
        this.trackingService = trackingService;
        this.userService = userService;
    }

    /**
     * Save a compte.
     *
     * @param compte the entity to save.
     * @return the persisted entity.
     */
    public GenericResponseCompte save(CompteRequest compteRequest, HttpServletRequest request) {
        GenericResponseCompte genericResponse = new GenericResponseCompte();
        Tracking tracking = new Tracking(); 
        log.debug("Request to save Compte : {}", compteRequest);
        Client client = clientService.findByIdClient(compteRequest.getCustomerId());
        if(client==null){
            tracking.setCodeResponse("404");
                tracking.responseTr("Numéro client absent dans nos livres!");
                tracking.endPointTr("comptes");
                genericResponse.setCode(404);
                genericResponse.setIdClient(compteRequest.getCustomerId());
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Numéro client absent dans nos livres!");
                
                trackingService.save(tracking);
                return genericResponse;
        }
        log.info("client=== [{}]",client);
        try {
            ParamFiliale filiale =  filialeService.findByCode(client.getPays());
            if(filiale==null){
                tracking.setCodeResponse("404");
                tracking.responseTr("Filiale non paramétree.");
                tracking.endPointTr("comptes");
                genericResponse.setCode(404);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Filiale desactivee.");
                
                trackingService.save(tracking);
                return genericResponse;
            }
            if(!filiale.isStatus()){
                tracking.setCodeResponse("401");
                tracking.responseTr("Filiale désactivée");
                tracking.endPointTr("comptes").dateRequest(Instant.now()).dateResponse(Instant.now())
                .loginActeur(userService.getUserWithAuthorities().get().getLogin());
                genericResponse.setCode(401);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Filiale désactivée!!");
                genericResponse.setIdClient(null);
                trackingService.save(tracking);
                return genericResponse;
            }
            URL url = new URL(filiale.getEndPointCompte());
            //URL url = new URL("http://10.132.71.33:8280/createaccount");
            String autho = request.getHeader("Authorization");
            String []tab = autho.split("Bearer");
            //System.out.println("tab 0"+tab[0]+" 1"+tab[1]);
            /*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json="";
            try {
                json = ow.writeValueAsString("request");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            log.debug("######## request [{}]", json);*/
            tracking.setTokenTr(tab[1]);
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            
            String jsonString = "";
            jsonString = new JSONObject()
                .put("customerId", compteRequest.getCustomerId())
                .put("rubComptable", compteRequest.getRubComptable())

                .put("devise", compteRequest.getDevise())
                .put("agence", compteRequest.getAgence())
                .put("typeCarte", compteRequest.getTypeCarte())
                .put("idProduit", compteRequest.getIdProduit())
                .put("cExploitant", compteRequest.getCExploitant())
                .put("intCompte", compteRequest.getIntCompte())
                .toString();
            tracking.setRequestTr(jsonString);
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";

            os.write(postDataBytes);
            os.flush();
            log.info("conn resp [{}] _ [{}]",conn.getResponseCode(),conn.getResponseMessage());
            BufferedReader br = null;
            JSONObject obj = new JSONObject();
            if (conn != null && conn.getResponseCode() > 0 ) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                result = br.readLine();
                log.info("result===== [{}]",result);
                obj = new JSONObject(result);
                //System.out.println("client ="+obj.getJSONObject("eerd_response").getString("CustomerId"));
                tracking.setCodeResponse(conn.getResponseCode() + "");
                tracking.responseTr(result);
                tracking.dateResponse(Instant.now());
                tracking.setRequestTr(jsonString);

                tracking.endPointTr("comptes").dateRequest(Instant.now())
                .loginActeur(userService.getUserWithAuthorities().get().getLogin());
                genericResponse.setCode(Integer.valueOf(obj.getJSONObject("accountResponse").getString("rCode")));
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setIdClient(obj.getJSONObject("accountResponse").getString("cId"));
                genericResponse.setDescription(obj.getJSONObject("accountResponse").getString("rMsg"));
                genericResponse.setNumCompte(obj.getJSONObject("accountResponse").getString("cAccount"));
                //client.setIdClient(obj.getJSONObject("accountResponse").getString("cAccount"));
                Compte compte = new Compte();
                compte.agence(compteRequest.getAgence()).cardType(compteRequest.getTypeCarte())
                .client(client).codeExploitant(compteRequest.getCExploitant()).datCre(Instant.now())
                .devise(compteRequest.getDevise()).intituleCompte(compteRequest.getIntCompte())
                .ncg(compteRequest.getRubComptable()).producctId(compteRequest.getIdProduit());
                compteRepository.save(compte);
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                result = br.readLine();
                log.info("err== [{}]",result);
                genericResponse.setCode(500);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Erreur Serveur");
                tracking.setCodeResponse(500+"");
                tracking.responseTr(result);
                tracking.dateResponse(Instant.now());
                tracking.endPointTr("comptes").dateRequest(Instant.now())
                .loginActeur(userService.getUserWithAuthorities().get().getLogin());
            }
            conn.disconnect();
        } catch (Exception e) {
            genericResponse.setCode(404);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription("Proxy Injoignable");
            tracking.setCodeResponse(500+"");
            tracking.responseTr("Proxy Injoignable");
            tracking.dateResponse(Instant.now());
            tracking.endPointTr("comptes").dateRequest(Instant.now())
                .loginActeur(userService.getUserWithAuthorities().get().getLogin());

        }
        //compteRepository.save(compte);
        trackingService.save(tracking);
        return genericResponse;
    }

    /**
     * Get all the comptes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Compte> findAll(Pageable pageable) {
        log.debug("Request to get all Comptes");
        return compteRepository.findAll(pageable);
    }


    /**
     * Get one compte by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Compte> findOne(Long id) {
        log.debug("Request to get Compte : {}", id);
        return compteRepository.findById(id);
    }

    /**
     * Delete the compte by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Compte : {}", id);
        compteRepository.deleteById(id);
    }
}
