package com.emergya.sss3e.security.matchers;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A request skipMatcher to allow to skip some urls.
 *
 * @see RequestMatcher
 */
public class SkipPathRequestMatcher implements RequestMatcher {

    private OrRequestMatcher skipMatcher;
    private RequestMatcher processingMatcher;

    /**
     * Constructor that builds the class matchers according to the received parameters.
     *
     * @param pathsToSkip the list of paths to be skipped.
     * @param processingPath the path to match.
     */
    public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
        if (pathsToSkip == null) {
            throw new IllegalArgumentException("Missing paths to skip");
        }

        List<RequestMatcher> matchers = pathsToSkip.stream().map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());

        this.skipMatcher = new OrRequestMatcher(matchers);
        processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    /**
     * Matches a request considering the path to be matched and the paths to be skipped.
     *
     * @param request the request to be matched.
     * @return true if the request matches the processingMatcher and does not match the skipMatcher.
     * @see RequestMatcher#matches(HttpServletRequest)
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        return !skipMatcher.matches(request) && processingMatcher.matches(request);

    }
}
