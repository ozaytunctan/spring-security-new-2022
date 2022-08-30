package com.ozaytunctan.aspect;

import com.ozaytunctan.aspect.annotation.IdGuard;
import com.ozaytunctan.security.manager.EncryptionManager;
import com.ozaytunctan.security.manager.JwtTokenManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class IdGuardAspect {


    private final JwtTokenManager tokenManager;
    private final EncryptionManager encryptionManager;

    public IdGuardAspect(JwtTokenManager tokenManager, EncryptionManager encryptionManager) {
        this.tokenManager = tokenManager;
        this.encryptionManager = encryptionManager;
    }


    @Before("@annotation(idGuard)")
    public void execute(JoinPoint joinPoint, IdGuard idGuard) {

        int argIndex = idGuard.parameterIndex();
        if (argIndex < 0) {
            argIndex = 0;
        }

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            throw new AccessDeniedException("ACCESS ERROR FOR INVALID RESOURCE");
        }

        Object idParameterObj = args[argIndex];
        if (idParameterObj == null) {
            throw new AccessDeniedException("ACCESS ERROR FOR INVALID RESOURCE");
        }

        String expectedTicket = encryptionManager.encrypt(idParameterObj.toString());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = tokenManager.getToken(request);
        if (!StringUtils.hasText(token)) {
            throw new AccessDeniedException("ACCESS ERROR FOR INVALID RESOURCE");
        }

        String realTicket = tokenManager.getTicketFromToken(token);
        if (!(StringUtils.hasText(expectedTicket) && expectedTicket.contains(realTicket))) {
            throw new AccessDeniedException("ACCESS ERROR FOR INVALID RESOURCE");
        }
    }
}