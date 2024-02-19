package com.m2p.at.ftbtests.api.rest.bdd.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.AircraftDto;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.CreateAircraftDto;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ApiCalls;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ExchangeStorage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

/**
 * A step-definition class to contain FTB REST API AIRCRAFTS resource related steps.
 */
@Slf4j
public class AircraftsSteps {

    private final static String PATH = "aircrafts";
    private final static String PATH_DETAILS = PATH + "/{id}";
    private final static String PATH_MODELNAME = PATH + "/model/{modelName}";

    private final ApiCalls apiCalls;
    private final ExchangeStorage<AircraftDto> storage;

    public AircraftsSteps() {
        this.apiCalls = new ApiCalls();
        this.storage = new ExchangeStorage<>();
    }

    @When("client gets details of Aircraft id={long}")
    public void getById(long id) {
        var response = apiCalls.doGet(SC_OK, AircraftDto.class, PATH_DETAILS, String.valueOf(id));
        storage.setLastApiCallSingleItemResponse(response);
    }

    @When("client gets details of just created Aircraft")
    public void getJustCreated() {
        var response = apiCalls
                .doGet(SC_OK, AircraftDto.class, PATH_DETAILS,
                        storage.getLastApiCallSingleItemResponse().getAircraftId());
        storage.setLastApiCallSingleItemResponse(response);
    }

    @When("client tries to create an Aircraft having manufacturer={string} and model={string} and number of seats={int}")
    public void create(String manufacturer, String model, Integer numberOfSeats) throws JsonProcessingException {
        var data = CreateAircraftDto.of()
                .manufacturer(manufacturer)
                .model(model)
                .numberOfSeats(numberOfSeats)
                .build();

        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
        storage.setLastApiCallSingleItemResponse(response);
    }

//    @Then("aircraft data to be manufacturer={string} and model={string} and number of seats={int}")
//    @Then("returned aircraft data to be manufacturer={string} and model={string} and number of seats={int}")
//    public void verifySingleAircraftData(String manufacturer, String model, Integer numberOfSeats) {
//        var lastResponse = storage.getLastApiCallSingleItemResponse();
//        assertThat(lastResponse.getManufacturer())
//                .as("Seems Aircraft response contained unexpected manufacturer value.")
//                .isEqualTo(manufacturer);
//        assertThat(lastResponse.getModel())
//                .as("Seems Aircraft response contained unexpected model value.")
//                .isEqualTo(model);
//        assertThat(lastResponse.getNumberOfSeats())
//                .as("Seems Aircraft response contained unexpected number-of-seats value.")
//                .isEqualTo(numberOfSeats);
//    }

//    @When("client tired to create an Aircraft having manufacturer={string} and model={string} and number of seats={int}")
//    public void createNull(String manufacturer, String model, Integer numberOfSeats) throws JsonProcessingException {
//        var data = CreateAircraftDto.of()
//                .manufacturer(manufacturer)
//                .model(model)
//                .numberOfSeats(null)
//                .build();
//
//        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
//        storage.setLastApiCallSingleItemResponse(response);
//    }

//    @When("client tires to create an Aircraft having manufacturer={string} and model={string} and number of seats=not_specified")
//    public void createWithNullSeats(String manufacturer, String model) throws JsonProcessingException {
//        createAircraft(manufacturer, model, null);
//    }
//
//    // Helper method to create an aircraft with null seats
//    private void createAircraft(String manufacturer, String model, Integer numberOfSeats) throws JsonProcessingException {
//        var data = CreateAircraftDto.of()
//                .manufacturer(manufacturer)
//                .model(model)
//                .numberOfSeats(numberOfSeats)
//                .build();
//
//        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
//        storage.setLastApiCallSingleItemResponse(response);
//    }
//

    @When("1 client gets details of Aircraft number of seats={int}")
    public void getByNos(Integer seats) {
        var response = apiCalls.doGet(SC_OK, AircraftDto.class, PATH_DETAILS, String.valueOf(seats));
        storage.setLastApiCallSingleItemResponse(response);
    }

    @Then("aircraft data to be manufacturer={string} and model={string} and number of seats={string}")
    @Then("returned aircraft data to be manufacturer={string} and model={string} and number of seats={string}")
    public void verifySingleAircraftData(String manufacturer, String model, String numberOfSeats) {
        if (numberOfSeats.isEmpty()) {
            numberOfSeats = null;
        }

        var lastResponse = storage.getLastApiCallSingleItemResponse();
        assertThat(lastResponse.getManufacturer())
                .as("Seems Aircraft response contained unexpected manufacturer value.")
                .isEqualTo(manufacturer);
        assertThat(lastResponse.getModel())
                .as("Seems Aircraft response contained unexpected model value.")
                .isEqualTo(model);
        assertThat(lastResponse.getNumberOfSeats())
                .as("Seems Aircraft response contained unexpected number-of-seats value.")
                .isEqualTo(numberOfSeats);
    }

    @When("client tries to create an Aircraft having manufacturer={string} and model={string} and null number of seats={string}")
    public void createNew(String manufacturer, String model, String numberOfSeats) throws JsonProcessingException {
        Integer seats = null;
        if (!numberOfSeats.isEmpty()) {
            seats = Integer.valueOf(numberOfSeats);
        }

        var data = CreateAircraftDto.of()
                .manufacturer(manufacturer)
                .model(model)
                .numberOfSeats(seats)
                .build();

        var response = apiCalls.doPost(SC_CREATED, AircraftDto.class, data, PATH);
        storage.setLastApiCallSingleItemResponse(response);
    }

}


