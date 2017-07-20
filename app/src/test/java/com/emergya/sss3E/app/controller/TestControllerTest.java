package com.emergya.sss3E.app.controller;

import com.emergya.sss3E.app.configuration.AppConfig;
import com.emergya.sss3E.app.configuration.RestWebConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 *
 * @author igonzalez
 * @since 21/07/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, RestWebConfig.class })
@WebAppConfiguration
public class TestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesTestController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("testController"));

    }

    @Test
    public void givenTestUri_whenMockMVC_thenVerifyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/test"))
                                          .andDo(print())
                                          .andExpect(status().isOk())
                                          .andExpect(jsonPath("$.message").value("Available API!"))
                                          .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }

    @Test
    public void givenExceptionUri_whenMockMVC_thenVerifyError() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/api/test/exception"))
                                          .andDo(print())
                                          .andExpect(status().isInternalServerError())
                                          .andExpect(jsonPath("$.code").value("500"))
                                          .andReturn();

        Assert.assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
    }
}
