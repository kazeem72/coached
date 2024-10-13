package ng.com.justjava.coached.rest.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ng.com.justjava.coached.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Order(1)
public class GeneralFilter extends OncePerRequestFilter {

    @Autowired
    SystemParametersRepository systemParametersRepository;

    @Autowired
    CoacheeRepository coacheeRepository;
    @Autowired
    CoachRepository coachRepository;
    public void destroy() {
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        SystemParameters systemParameters=systemParametersRepository
                .findByModule("session").get();

        OAuth2AuthenticationToken oAuth2AuthenticationToken= (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userType= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("userType");
        String email= (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
        String myPicture = request.getSession().getAttribute("myPicture")
                !=null? (String) request.getSession().getAttribute("myPicture") :"woman pic.png";
        Optional<Coachee> optionalCoachee = null;
        Optional<Coach> optionalCoach = null;
/*        System.out.println("\n \n \n \n \n \nInside the Filter here" +
                "  ==================================" +
                "====================================" +
                "====================================\n \n " +
                "Request URL =="+request.getRequestURL()+"\n\n\n"+
                "Request ID ==="+request.getRequestId()+"\n\n\n"+
                "Request URI ==="+request.getRequestURI()+"\n\n\n"+
                "Request servlet path=== "+request.getServletPath()+"\n\n\n"+
                " the authentication details here==="+
                oAuth2AuthenticationToken+"\n\n\n my picture in the session"+
                request.getSession().getAttribute("myPicture"));*/

        List<String> groups= (List<String>) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("groups");
        Boolean isEZAdmin = false;
        if(groups!=null) {
            isEZAdmin = groups.stream()
                    .anyMatch(group -> group.equalsIgnoreCase("EZ37Admin"));
        }
        if(isEZAdmin)
            userType="EZ37 Admin";


        if(request.getSession().getAttribute("firstTime")==null)
            request.getSession(true).setAttribute("firstTime",false);

        request.getSession(true).setAttribute("systemParameters",systemParameters);
        request.getSession(true).setAttribute("parameters",systemParameters.getParameters());
        if(request.getSession().getAttribute("myPicture")==null)
            if("coachee".equalsIgnoreCase(userType) || "Organization Admin".equalsIgnoreCase(userType)) {
                optionalCoachee = coacheeRepository.findByEmail(email);
                myPicture = !StringUtils.isEmpty(optionalCoachee.get().getMyPicture())
                        ?optionalCoachee.get().getMyPicture():myPicture;
            }
        if("coach".equalsIgnoreCase(userType)){
            optionalCoach =  coachRepository.findByEmail(email);
            myPicture = !StringUtils.isEmpty(optionalCoach.get().getMyPicture())
                    ?optionalCoach.get().getMyPicture():myPicture;

        }
        request.getSession(true).setAttribute("myPicture",myPicture);
        request.getSession(true).setAttribute("parameters",systemParameters.getParameters());


        request.getSession(true).setAttribute("userType",userType);
        filterChain.doFilter(request, response);

    }
}
