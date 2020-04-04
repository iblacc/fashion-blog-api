package com.laylasahara.fashionblogapi;

import com.laylasahara.fashionblogapi.controllers.DesignController;
import com.laylasahara.fashionblogapi.models.Design;
import com.laylasahara.fashionblogapi.repositories.DesignRepository;
import static org.assertj.core.api.Assertions.assertThat;

import com.laylasahara.fashionblogapi.repositories.LikeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class FashionBlogApiApplicationTests {

	@Autowired
	MockMvc mvc;

	@Autowired
	DesignController designController;

	@MockBean
	DesignRepository designRepository;
	@MockBean
	LikeRepository likeRepository;

	@Test
	void testAddDesignResponseStatus() {
		Design design = new Design();
		when(designRepository.save(any(Design.class))).thenReturn(design);
		ResponseEntity<Object> responseEntity = designController.createDesign(design);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
	}

	@Test
	void testAddDesignResponseBody() {
		Design design = new Design();
		design.setName("Fullname");
		when(designRepository.save(any(Design.class))).thenReturn(design);
		ResponseEntity<Object> responseEntity = designController.createDesign(design);

		boolean isContained = responseEntity.getBody().toString().contains("Fullname");
		assertTrue(isContained);
	}

	@Test
	void testGetAllDesign() {
		Design design1 = new Design();
		Design design2 = new Design();
		Design design3 = new Design();

		List<Design> designs = Arrays.asList(design1, design2, design3);

		when(designRepository.findAll()).thenReturn(designs);
		when(likeRepository.countLikeByDesign(any(Design.class))).thenReturn(0);

		List<Design> result = designController.getAllDesigns();
		System.out.println(result.size());
		assertThat(result.size()).isEqualTo(3);
	}

	@Test
	void testGetDesign() {
		int id = 1;
		Design design = new Design();
		design.setId(id);

		when(designRepository.findById(id)).thenReturn(Optional.of(design));
		Design result = designController.getDesign(id);
		assertThat(result).isEqualTo(design);
	}

	@Test
	void testSearchDesign() {
		Design design = new Design();
		design.setType("Kampala Kimono");
		design.setName("The queen of Africa is home.");

		List<Design> designList = Collections.singletonList(design);

		when(designRepository.findDesignsByKeyword("queen")).thenReturn(designList);
		List<Design> result = designController.searchDesign("queen");
		assertThat(result).isEqualTo(designList);
	}
}
