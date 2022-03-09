package dev.aquashdw.community.boundary.controller;

import dev.aquashdw.community.boundary.model.BoardDto;
import dev.aquashdw.community.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("board")
public class BoardController {
    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    public BoardController(
            BoardService boardService
    ) {
        this.boardService = boardService;
    }

    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@RequestBody BoardDto dto){
        return ResponseEntity.ok(boardService.create(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<BoardDto> readBoard(
            @PathVariable("id") Long id
    ){
        BoardDto dto = boardService.read(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Collection<BoardDto>> readBoardAll(){
        return ResponseEntity.ok(this.boardService.readAll());
    }


    @PutMapping("{id}")
    public ResponseEntity<?> updateBoard(
            @PathVariable("id") Long id, @RequestBody BoardDto dto) {
        if(!boardService.update(id, dto)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBoard(
            @PathVariable("id") Long id){
        if(!boardService.delete(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
