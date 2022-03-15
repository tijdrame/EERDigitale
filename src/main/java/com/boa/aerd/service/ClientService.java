package com.boa.aerd.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.boa.aerd.domain.Client;
import com.boa.aerd.domain.ParamFiliale;
import com.boa.aerd.domain.Tracking;
import com.boa.aerd.repository.ClientRepository;
import com.boa.aerd.response.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final UserService userService;

    private final TrackingService trackingService;

    private final ParamFilialeService filialeService;

    public ClientService(ClientRepository clientRepository, UserService userService, TrackingService trackingService, 
    ParamFilialeService filialeService) {
        this.clientRepository = clientRepository;
        this.userService = userService;
        this.trackingService = trackingService;
        this.filialeService = filialeService;
    }

    /**
     * Save a client.
     *
     * @param client the entity to save.
     * @return the persisted entity.
     */
    public GenericResponse save(Client client, HttpServletRequest request) {
        GenericResponse genericResponse = new GenericResponse();
        log.debug("Request to save Client : {}", client);
        Tracking tracking = new Tracking();
        String login = userService.getUserWithAuthorities().isPresent()?userService.getUserWithAuthorities().get().getLogin():"";
        tracking.dateRequest(Instant.now()).loginActeur(login);
        tracking.endPointTr(request.getRequestURI());
        String autho = request.getHeader("Authorization");
        String []tab = autho.split("Bearer");
        //System.out.println("tab 0"+tab[0]+" 1"+tab[1]);
        /*System.out.println("CLient=============="+client);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json="";
        try {
            json = ow.writeValueAsString("request");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        log.debug("######## request [{}]", json);*/
        
        tracking.setTokenTr(tab[1]);
        //int resp = 0;
        try {
            // --------------------------------
            ParamFiliale filiale =  filialeService.findByCode(client.getPays());
            if(filiale==null){
                tracking.setCodeResponse("404");
                tracking.responseTr("Filiale non paramétrée!");
                genericResponse.setCode(404);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Filiale désactivée!");
                genericResponse.setIdClient(null);
                trackingService.save(tracking);
                return genericResponse;
            }
            if(!filiale.isStatus()){
                tracking.setCodeResponse("401");
                tracking.responseTr("Filiale désactivé");
                genericResponse.setCode(401);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Filiale désactivée!");
                genericResponse.setIdClient(null);
                trackingService.save(tracking);
                return genericResponse;
            }
            //fin --------------
            log.info("req**= [{}]",request.getInputStream().toString());
           
            URL url = new URL(filiale.getEndPoint());
            //URL url = new URL("http://10.132.71.33:8280/createclient");
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setDoOutput(true);
    		conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            
            String jsonString = "";
            try {
                //ObjectMapper mapper = new ObjectMapper();
                //mapper.enable(SerializationFeature.INDENT_OUTPUT);
                //jsonString = mapper.writeValueAsString(client);
                //System.out.println("ObjectMapper mapper = new ObjectMapper()=="+jsonString);
                jsonString = new JSONObject()
                .put("codCivil", client.getCodCivil())
                .put("nom", client.getNom())
                .put("prenom", client.getPrenom())
                .put("nomMari", client.getNomMari())
                .put("prenomMari", client.getPrenomMari())
                .put("nomMere", client.getNomMere())
                .put("prenomMere", client.getPrenomMere())
                .put("nomPere", client.getNomPere())
                .put("prenomPere", client.getPrenomPere())
                .put("dateNaissance", client.getDateNaissance()+"T11:21:18")
                .put("depNaissance", client.getDepNaissance())
                .put("commNaiss", client.getCommNaiss())
                .put("paysNaiss", client.getPaysNaiss())
                .put("nation", client.getNation())
                .put("sexe", client.getSexe())
                .put("situ", client.getSitu())
                .put("prof", client.getProf())
                .put("langue", client.getLangue())
                .put("relation", client.getRelation())
                .put("identite", client.getIdentite())
                .put("numId", client.getNumId())
                .put("datLivr", client.getDatLivr()+"T11:21:18")
                .put("datValid", client.getDatValid()+"T11:21:18")
                .put("payId", client.getPayId())
                .put("commId", client.getCommId())
                .put("addr1", client.getAddr1())
                .put("addr2", client.getAddr2())
                .put("addr3", client.getAddr3())
                .put("addr4", client.getAddr4())
                .put("codCour", client.getCodCour())
                .put("codPost", client.getCodPost())
                .put("mail", client.getMail())
                .put("pays", client.getPays())
                .put("depRes", client.getDepRes())
                .put("tel", client.getTel())
                .put("telProf", client.getTelProf())
                .put("teleFax", client.getTeleFax())
                .put("agence", client.getAgence())
                .put("ncg", client.getNcg())
                .put("devise", client.getDevise())
                .put("codeExploitant", client.getCodeExploitant())
                .put("datCre", client.getDatCre())//+"T11:21:18"
                .put("paysCpt", client.getPays())//TODO pays remplace paysCpt
                .put("prenom", client.getPrenom())
                .put("identifTel", client.getIndicatifTel())
                .toString();
                log.info("jstring===== [{}]",jsonString);
                tracking.setRequestTr(jsonString);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            OutputStream os = conn.getOutputStream();
            byte[] postDataBytes = jsonString.getBytes();
            String result = "";
    		try {
                os.write(postDataBytes);
                os.flush();
                log.info("conn resp=======[{}] _ [{}]",conn.getResponseCode(),conn.getResponseMessage());
                BufferedReader br = null;
                JSONObject obj = new JSONObject();
                if (conn != null && conn.getResponseCode() == 200 ) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    result = br.readLine();
                    log.info("result==== [{}]",result);
                    obj = new JSONObject(result);
                    //System.out.println("client ="+obj.getJSONObject("eerd_response").getString("CustomerId"));
                    tracking.setCodeResponse(conn.getResponseCode() + "");
                    tracking.responseTr(result);
                    tracking.dateResponse(Instant.now());
                    tracking.setRequestTr(jsonString);
                    genericResponse.setCode(Integer.valueOf(obj.getJSONObject("eerd_response").getString("ResponseCode")));
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setIdClient(obj.getJSONObject("eerd_response").getString("CustomerId"));
                    genericResponse.setDescription(obj.getJSONObject("eerd_response").getString("DetailsResponse"));
                    client.setIdClient(obj.getJSONObject("eerd_response").getString("CustomerId"));
                    clientRepository.save(client);
                } else {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    result = br.readLine();
                    log.info("err [{}]",result);
                    genericResponse.setCode(500);
                    genericResponse.setDateResponse(Instant.now());
                    genericResponse.setDescription("Erreur Serveur");
                    tracking.setCodeResponse(500+"");
                    tracking.responseTr(result);
                    tracking.dateResponse(Instant.now());
                }
                conn.disconnect();
            } catch (IOException e) {
                tracking.setCodeResponse(conn.getResponseCode() + "");
                tracking.responseTr(conn.getResponseMessage());
                tracking.dateResponse(Instant.now());
                tracking.setRequestTr(jsonString);
                genericResponse.setCode(500);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Erreur Serveur");
            } catch (JSONException e) {
                tracking.setCodeResponse(conn.getResponseCode() + "");
                tracking.responseTr(conn.getResponseMessage());
                tracking.dateResponse(Instant.now());
                tracking.setRequestTr(jsonString);
                genericResponse.setCode(500);
                genericResponse.setDateResponse(Instant.now());
                genericResponse.setDescription("Erreur Serveur");
            e.printStackTrace();
        }
        } catch (Exception ex) {
            tracking.setCodeResponse("404");
            tracking.responseTr("Proxy injoignable");
            tracking.dateResponse(Instant.now());
            genericResponse.setCode(404);
            genericResponse.setDateResponse(Instant.now());
            genericResponse.setDescription("Proxy injoignable");
            log.error(ex.getMessage());
        }
        trackingService.save(tracking);

        
        return genericResponse;
    }
    

    /**
     * Get all the clients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        log.debug("Request to get all Clients");
        return clientRepository.findAll();
    }


    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }

	public Client findByIdClient(String idClient) {
		return clientRepository.findByIdClient(idClient);
	}
}
