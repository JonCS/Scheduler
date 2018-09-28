package com.jw.meetingscheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jw.meetingscheduler.controller.CongregationController;
import com.jw.meetingscheduler.model.Congregation;
import com.jw.meetingscheduler.service.CongregationService;

@RunWith(SpringRunner.class)
@WebMvcTest(CongregationController.class)
public class CongregationControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
 
    @MockBean
    private CongregationService service;
    
    @Test
    public void createCongregation() throws Exception {
    		Congregation mockCongregation = new Congregation();
    		mockCongregation.setName("San Ramon Valley Mandarin");
    		
    		String json = mapper.writeValueAsString(mockCongregation);
    		
    		mockMvc.perform(MockMvcRequestBuilders.post("/api/congregation")
    				.contentType(MediaType.APPLICATION_JSON)
    				.content(json)
    				.accept(MediaType.APPLICATION_JSON))
    				.andExpect(status().isOk());
   
    }
    
    @Test
    public void getCongregation() throws Exception {
    		mockMvc.perform(MockMvcRequestBuilders.delete("/api/congregation/1")
    				.contentType(MediaType.APPLICATION_JSON)
    				.accept(MediaType.APPLICATION_JSON))
    				.andExpect(status().isOk());
    }
    
}
