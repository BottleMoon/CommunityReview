package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.community.domain.Board;
import study.community.domain.User;
import study.community.service.BoardService;

import javax.servlet.http.HttpServletRequest;

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
    public String board(@PathVariable Long idx, Model model){
        model.addAttribute("board",boardService.getByIdx(idx));
        return "board/board";
    }

    //Write
    @GetMapping("board/new")
    public String initCreateBoard(Board board){

        return "board/new";
    }
    @PostMapping("board/new")
    public String processingCreateBoard(Board board, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        boardService.createBoard(board, user);
        return "redirect:/";
    }
}
