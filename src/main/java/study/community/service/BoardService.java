package study.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.community.domain.Board;
import study.community.domain.User;
import study.community.repository.BoardRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    public Optional<Board> getByIdx(Long idx) {
        return boardRepository.findById(idx);
    }

    public void createBoard(Board board, User user) {
        board.setUser(user);
        board.setCreatedTime(LocalDateTime.now());
        boardRepository.save(board);
    }

    public void updateBoard(Board board){
        board.setUpdatedTime(LocalDateTime.now());
        boardRepository.save(board);
    }

    public void deleteBoardByIdx(long idx) {
        boardRepository.deleteById(idx);
    }
}
