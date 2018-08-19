package com.bcorpse.teststrategry.superhero;

import com.bcorpse.teststrategry.superhero.controller.SuperHeroController;
import com.bcorpse.teststrategry.superhero.controller.SuperHeroExceptionHandler;
import com.bcorpse.teststrategry.superhero.controller.SuperHeroFilter;
import com.bcorpse.teststrategry.superhero.domain.SuperHero;
import com.bcorpse.teststrategry.superhero.exception.NonExistingHeroException;
import com.bcorpse.teststrategry.superhero.repository.SuperHeroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class SuperHeroControllerMockMvcStandaloneTest {
    private MockMvc mvc;

    @Mock
    private SuperHeroRepository superHeroRepository;

    @InjectMocks
    private SuperHeroController superHeroController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<SuperHero> jsonSuperHero;

    @Before
    public void setup() {
        // We would need this line if we would not use MockitoJUnitRunner
        // MockitoAnnotations.initMocks(this);
        // Initializes the JacksonTester
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(superHeroController)
                .setControllerAdvice(new SuperHeroExceptionHandler())
                .addFilters(new SuperHeroFilter())
                .build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        //given
        given(superHeroRepository.getSuperHero(3))
                .willReturn(new SuperHero("James","Logan","Howlett","Wolverine"));

        //when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonSuperHero.write(new SuperHero("James", "Logan","Howlett", "Wolverine")).getJson()
        );

    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {
        //given
        given(superHeroRepository.getSuperHero(3))
                .willThrow(new NonExistingHeroException());

        //when
        MockHttpServletResponse response = mvc.perform(
                get("/superheroes/3")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    public void canRetrieveByNameWhenExists() throws Exception {

    }

    @Test
    public void canRetrieveByNameWhenDoesNotExist() throws Exception {

    }

    @Test
    public void canCreateANewSuperHero() throws Exception {

    }

    @Test
    public void headerIsPresent() throws Exception {

    }
}
