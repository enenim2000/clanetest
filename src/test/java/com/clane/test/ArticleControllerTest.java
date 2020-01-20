package com.clane.test;

import com.clane.test.dto.request.ArticleRequest;
import com.clane.test.dto.response.ArticleResponse;
import com.clane.test.service.ArticleService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleControllerTest {

	@Autowired
	public WebApplicationContext context;

	public MockMvc mockMvc;

	public final ArticleService articleService = org.mockito.Mockito.mock(ArticleService.class);

	@Rule
	public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(documentationConfiguration(this.jUnitRestDocumentation))
				.build();
	}

	@Test
	public void retrieveArticlesShouldReturnOK() throws Exception {

		List<ArticleResponse> articles = Arrays.asList(
				ArticleResponse.builder().id(1L)
						.title("The animal jungle")
						.content("The story of the animal kingdom").build(),
				ArticleResponse.builder().id(2L)
						.title("The saxophonist")
						.content("The story of greatest instrumentalist unveiled").build());

		when(articleService.getArticles()).thenReturn(articles);

		mockMvc.perform(get("/articles"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].title", is("The animal jungl")))
				.andExpect(jsonPath("$[0].content", is("The story of the animal kingdom")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].title", is("The saxophonist")))
				.andExpect(jsonPath("$[1].content", is("The story of greatest instrumentalist unveiled")));

		verify(articleService, times(1)).getArticles();
	}

	@Test
	public void shouldSubmitArticleShouldReturnOk() throws Exception {
		when(articleService.submitArticle(any(ArticleRequest.class))).thenReturn(
				ArticleResponse.builder()
						.id(1L)
						.title("The animal jungle")
						.content("The story of the animal kingdom")
						.createdAt(new Date())
						.updatedAt(new Date())
						.build()
		);

		mockMvc.perform(post("/articles").requestAttr("userId", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"title\": \"The animal jungle\", \"content\": \"The story of the animal kingdom\" }")
				.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
				.andDo(print())
				.andDo(document("{ClassName}/{methodName}",
						preprocessRequest(prettyPrint()),
						preprocessResponse(prettyPrint()),
						requestFields(
								fieldWithPath("id").description("Article Identifier"),
								fieldWithPath("title").description("Article title"),
								fieldWithPath("content").description("Article content")
						)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("The animal jungle"))
				.andExpect(jsonPath("$.content").value("The story of the animal kingdom"))
				.andExpect(jsonPath("$.id").value(1L));
	}
}