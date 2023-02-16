package com.iptracker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(AddressController.class)
class IptrackerBackendApplicationTests {
	@Autowired 
	private MockMvc mvc;
	
	@MockBean 
	private AddressController addressController; 
	
	@Autowired 
	ObjectMapper mapper;
	
	@Test
	public void contextLoads() throws Exception { //ensure our controller is being created 
		assertThat(addressController).isNotNull();
	}

}
