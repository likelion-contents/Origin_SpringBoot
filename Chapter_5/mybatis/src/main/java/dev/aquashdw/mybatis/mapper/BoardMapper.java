package dev.aquashdw.mybatis.mapper;

import dev.aquashdw.mybatis.dto.BoardDto;

public interface BoardMapper {
    int createBoard(BoardDto dto);
}
