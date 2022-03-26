package dev.aquashdw.community.controller;

import dev.aquashdw.community.model.BoardDto;
import dev.aquashdw.community.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardRepository boardRepository;

    public BoardController(
            BoardRepository boardRepository
    ) {
        this.boardRepository = boardRepository;
    }

    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto dto){
        return ResponseEntity.ok(boardRepository.create(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<BoardDto> readBoard(
            @PathVariable("id") Long id
    ){
        BoardDto dto = boardRepository.read(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Collection<BoardDto>> readBoardAll(){
        return ResponseEntity.ok(this.boardRepository.readAll());
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateBoard(
            @PathVariable("id") Long id, @RequestBody BoardDto dto) {
        if(!boardRepository.update(id, dto)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBoard(
            @PathVariable("id") Long id){
        if(!boardRepository.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
