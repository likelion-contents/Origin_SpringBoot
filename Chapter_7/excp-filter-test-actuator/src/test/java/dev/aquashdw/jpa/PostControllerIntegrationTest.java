package dev.aquashdw.jpa;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.aquashdw.jpa.entity.PostEntity;
import dev.aquashdw.jpa.repository.PostRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = JpaApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Before
    public void setEntities(){
        createTestPost("First Post", "First Post Content", "test_writer");
        createTestPost("Second Post", "Second Post Content", "test_writer");
        createTestPost("Third Post", "Third Post Content", "test_writer");
    }

    @Test
    void whenValidDto_thenCreate() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setWriter("aquashdw");
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(postDto)));

        Iterable<PostEntity> found = postRepository.findAll();
        assertThat(found).extracting(PostEntity::getWriter).contains("aquashdw");
        assertThat(found).extracting(PostEntity::getContent).contains("Test Content");
        assertThat(found).extracting(PostEntity::getTitle).contains("Test Title");
    }

    @Test
    void givenValidId_whenReadPost_then200() throws Exception {
        // given
        Long id = createTestPost("Read Post", "Created on readPost()", "read_test");

        // when
        final ResultActions actions = mockMvc.perform(get("/post/{id}", id))
                .andDo(print());

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Read Post")))
                .andExpect(jsonPath("$.content", is("Created on readPost()")))
                .andExpect(jsonPath("$.writer", is("read_test")));
    }

    @Test
    void givenSomePosts_whenReadPostAll_then200() throws Exception {
        mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize((int) postRepository.count())))
        ;
    }

    @Test
    void givenPost_whenValidInput_thenUpdateTarget() throws Exception {
        Long id = createTestPost(
                "Update Target",
                "Update Target Content",
                "upd_target"
        );
        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");

        mockMvc.perform(put("/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(postDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        mockMvc.perform(get("/post/{id}", id))
                .andExpectAll(
                        jsonPath("$.title", is("Updated Title")),
                        jsonPath("$.content", is("Update Target Content")),
                        jsonPath("$.writer", is("upd_target"))
                );

        postDto = new PostDto();
        postDto.setContent("Updated Content");
        mockMvc.perform(put("/post/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(postDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(get("/post/{id}", id))
                .andExpectAll(
                        jsonPath("$.title", is("Updated Title")),
                        jsonPath("$.content", is("Updated Content")),
                        jsonPath("$.writer", is("upd_target"))
                );
    }

    @Test
    void givenPost_whenValidId_thenDeletePost() throws Exception {
        Long id = createTestPost(
                "Delete Target",
                "Delete Target Content",
                "del_target"
        );
        mockMvc.perform(delete("/post/{id}", id))
                .andExpect(status().is2xxSuccessful());

        Iterable<PostEntity> found = postRepository.findAll();
        assertThat(found).extracting(PostEntity::getWriter).doesNotContain("Delete Target");
        assertThat(found).extracting(PostEntity::getContent).doesNotContain("Delete Target Content");
        assertThat(found).extracting(PostEntity::getTitle).doesNotContain("del_target");
    }

    @After
    public void resetDb(){
        postRepository.deleteAll();
    }

    private Long createTestPost(String title, String content, String writer){
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(title);
        postEntity.setContent(content);
        postEntity.setWriter(writer);
        return postRepository.save(postEntity).getId();
    }

    private byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}