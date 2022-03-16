package dev.aquashdw.jpa;

import dev.aquashdw.jpa.entity.BoardEntity;
import dev.aquashdw.jpa.repository.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = JpaApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void boardRepositoryIsNotNull(){
        assertThat(this.boardRepository).isNotNull();
    }

    @Test
    public void createBoard() throws Exception {
        final BoardEntity newBoard1 = new BoardEntity();
        newBoard1.setName("notice board");

        final BoardEntity savedBoard1 = boardRepository.save(newBoard1);

        assertThat(savedBoard1.getId()).isNotNull();
        assertThat(savedBoard1.getName()).isEqualTo("notice board");

        final BoardEntity newBoard2 = new BoardEntity();
        newBoard2.setName("general board");
        final BoardEntity savedBoard2 = boardRepository.save(newBoard2);

        assertThat(savedBoard2.getId()).isNotNull();
        assertThat(savedBoard2.getName()).isEqualTo("general board");
    }

    @Test
    public void readBoard() throws Exception {
        final BoardEntity newBoard = new BoardEntity();
        newBoard.setName("notice board");
        Long entityId = boardRepository.save(newBoard).getId();

        Optional<BoardEntity> found = boardRepository.findById(entityId);
        assertThat(found.isPresent()).isTrue();
    }

    @Test
    public void readBoardAll() throws Exception {
        Iterable<BoardEntity> entityIterable = boardRepository.findAll();
        List<BoardEntity> entityList = new ArrayList<>();
        entityIterable.forEach(entityList::add);
        int sizeBefore = entityList.size();
        final BoardEntity newBoard = new BoardEntity();
        newBoard.setName("extra board");
        boardRepository.save(newBoard);

        entityIterable = boardRepository.findAll();
        entityList = new ArrayList<>();
        entityIterable.forEach(entityList::add);
        int sizeNext = entityList.size();
        assertThat(sizeBefore).isLessThan(sizeNext);
    }
}
