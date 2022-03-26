package dev.aquashdw.community.repository;

import dev.aquashdw.community.model.BoardDto;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryBoardRepository implements BoardRepository{
    private Long lastIndex = 0L;
    private final Map<Long, BoardDto> memory = new HashMap<>();

    @Override
    public BoardDto create(BoardDto dto) {
        lastIndex++;
        dto.setId(lastIndex);
        memory.put(lastIndex, dto);
        return memory.get(lastIndex);
    }

    @Override
    public BoardDto read(Long id) {
        return memory.getOrDefault(id, null);
    }

    @Override
    public Collection<BoardDto> readAll() {
        return memory.values();
    }

    @Override
    public boolean update(Long id, BoardDto dto) {
        if (memory.containsKey(id)) {
            dto.setId(id);
            memory.put(id, dto);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (memory.containsKey(id)) {
            memory.remove(id);
            return true;
        }
        return false;
    }
}
