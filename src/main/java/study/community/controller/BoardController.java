package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.community.domain.Board;
import study.community.domain.User;
import study.community.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class BoardController {
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/")
    public String main(){
        return "index";
    }

    //List
    @GetMapping("board/list")
    public String boardList(Model model){
        model.addAttribute("boardList",boardService.getBoardList());
        return "board/list";
    }

    //Board
    @GetMapping("board/{idx}")
    public String board(@PathVariable("idx") Long idx, Model model){
        model.addAttribute("board",boardService.getByIdx(idx));

        return "board/board";
    }

    //Write
    @GetMapping("board/new")
    public String initCreateBoard(Board board){

        return "board/new";
    }
    //Create
    @PostMapping("board/new")
    public String processingCreateBoard(Board board, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        boardService.createBoard(board, user);
        return "redirect:/board/list";
    }
    //Update
    @GetMapping("/board/{idx}/update")
    public String initUpdateBoard(@PathVariable long idx, Model model,HttpServletRequest request){
        Board board = boardService.getByIdx(idx);
        model.addAttribute("board",board);

        return "board/update";
    }
    @PostMapping("board/{idx}/update")
    public String processingUpdateBoard(@PathVariable long idx, Board board){
        board.setIdx(idx);
        board.setUser(boardService.getByIdx(idx).getUser());
        boardService.updateBoard(board);
        return "redirect:/board/"+board.getIdx();
    }

    //Delete
    @GetMapping("board/{idx}/delete")
    public String deleteBoard(@PathVariable long idx){
        boardService.deleteBoardByIdx(idx);
        return "redirect:/board/list";
    }
}
