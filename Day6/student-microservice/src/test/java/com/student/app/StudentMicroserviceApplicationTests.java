package com.student.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.Base64Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest({StudentController.class})
@ActiveProfiles(value = "test")
class StudentMicroserviceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Value("${student.get.url}")
	String geturl;

	@Value("${student.post.url}")
	String posturl;

	@Value("${student.put.url}")
	String puturl;

	@Value("${student.delete.url}")
	String deleteurl;

	@Test
	public void testStudentGet() throws Exception {
		ResultActions responseEntity = processApiRequest(geturl, HttpMethod.GET, null,
				null, null, null);
		responseEntity.andExpect(status().isOk());
		/*ObjectMapper mapper = new ObjectMapper();
		String result = responseEntity.andReturn().getResponse().getContentAsString();
		assertEquals("get employee ", result);*/
	}

	private ResultActions processApiRequest(String api, HttpMethod methodType, String name, Student student, String username, String password) {
		ResultActions response = null;
		String secret = "Basic " + Base64Utils.encodeToString((username+":"+password).getBytes());
		try {
			switch (methodType) {
				case GET:
					response = mockMvc.perform(get(api).header(HttpHeaders.AUTHORIZATION, secret));
					break;
				case POST:
					response = mockMvc.perform(post(api).header(HttpHeaders.AUTHORIZATION, secret).contentType(MediaType.APPLICATION_JSON)
							.content(asJsonString(student)).accept(MediaType.APPLICATION_JSON));
					break;
				case PUT:
					response = mockMvc.perform(put(api, name).contentType(MediaType.APPLICATION_JSON)
							.content(asJsonString(student)).accept(MediaType.APPLICATION_JSON));
					break;
				case DELETE:
					response = mockMvc.perform(delete(api, name));
					break;
				default:
					fail("Method Not supported");
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return response;
	}

	private String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Student createEmployee(String name, String roll, String std) {
		Student student = new Student(name, roll,std);
		return student;
	}

}
