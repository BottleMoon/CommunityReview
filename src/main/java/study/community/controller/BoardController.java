package study.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.community.domain.Board;
import study.community.domain.User;
import study.community.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

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

    //ListREAD
    @GetMapping("board/list")
    public String boardList(Model model){
        model.addAttribute("boardList",boardService.getBoardList());
        return "board/list";
    }

    //READ
    @GetMapping("board/{idx}")
    public String board(@PathVariable("idx") Long idx, Model model){
        model.addAttribute("board",boardService.getByIdx(idx).get());

        return "board/board";
    }

    //Write
    @GetMapping("board/new")
    public String initCreateBoard(Board board, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        if (session.getAttribute("user") != null) {
            return "board/new";
        }
        else{
            out.println("<script> alert('로그인이 필요합니다.'); location.href='/board/list';</script>");
            out.flush();
        }
            return "redirect:/board/list";
    }
    @PostMapping("board/new")
    public String processingCreateBoard(Board board, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        boardService.createBoard(board, user);
        return "redirect:/board/list";
    }
    //Update
    @GetMapping("/board/{idx}/update")
    public String initUpdateBoard(@PathVariable Long idx, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        //로그인 정보와 작성자가 일치하는지 확인
        if (session.getAttribute("user") != null) {
            if (boardService.getByIdx(idx).get().getUser().getId()
                    .equals(((User) session.getAttribute("user")).getId())) {

                Board board = boardService.getByIdx(idx).get();
                model.addAttribute("board", board);

                return "board/update";
            } else {    //작성자 아니면 수정 못함
                out.println("<script> alert('작성자만 수정할 수 있습니다.'); location.href='/board/" + idx + "';</script>");
                out.flush();
            }
        } else {    //로그인 안하면 삭제 못함.
            out.println("<script> alert('작성자만 수정할 수 있습니다.'); location.href='/board/" + idx + "';</script>");
            out.flush();
        }
        return "redirect:";
    }
    @PostMapping("board/{idx}/update")
    public String processingUpdateBoard(@PathVariable Long idx, Board board){
        board.setIdx(idx);
        board.setUser(boardService.getByIdx(idx).get().getUser());
        boardService.updateBoard(board);
        return "redirect:/board/"+board.getIdx();
    }

    //Delete
    @GetMapping("board/{idx}/delete")
    public String deleteBoard(@PathVariable Long idx, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (session.getAttribute("user") != null) {
            if (boardService.getByIdx(idx).get().getUser().getId().equals(((User) session.getAttribute("user")).getId())) {
                boardService.deleteBoardByIdx(idx);
                return "redirect:/board/list";
            }
            else {  //작성자 아니면 삭제 못함
                out.println("<script> alert('작성자만 삭제할 수 있습니다.'); location.href='/board/" + idx + "';</script>");
                out.flush();
            }
        }
        else{   //로그인 안하면 삭제 못함
            out.println("<script> alert('작성자만 삭제할 수 있습니다.'); location.href='/board/" + idx + "';</script>");
            out.flush();
        }
            return "redirect:";
    }
}
