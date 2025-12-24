package team.omok.omok_mini_project.filter;

import team.omok.omok_mini_project.domain.vo.UserVO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
  로그인 안 한 사용자가 /lobby, /room 등으로 바로 접근하는 걸 차단합니다.
  세션 키: "loginUser" (LoginServlet에서 저장된)
 */
@WebFilter(urlPatterns = {"/lobby/*", "/room"})
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;


        HttpSession session = req.getSession(false);
        UserVO loginUser = (session == null) ? null : (UserVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 로그인 안 되어있으면 로그인 페이지로
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 로그인 되어있으면 통과
        chain.doFilter(request, response);
    }
}
