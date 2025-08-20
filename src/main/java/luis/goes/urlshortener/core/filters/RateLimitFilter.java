package luis.goes.urlshortener.core.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import luis.goes.urlshortener.core.shared.dto.ErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(1)
public class RateLimitFilter implements Filter {

    private static final long RATE_LIMIT = 100;
    private static final long TIME_WINDOW_MS = 60_000;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ConcurrentHashMap<String, RequestInfo> requestMap = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String ipAddress = request.getRemoteAddr();
        long currentTime = System.currentTimeMillis();

        RequestInfo info = requestMap.compute(ipAddress, (ip, old) -> {
            if (old == null || currentTime - old.startTime > TIME_WINDOW_MS) {
                return new RequestInfo(1, currentTime);
            } else {
                old.count.incrementAndGet();
                return old;
            }
        });

        if (info.count.get() > RATE_LIMIT) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            httpResponse.setContentType("application/json");

            ErrorResponse errorResponse = new ErrorResponse("Rate limit exceeded. Try again later.");
            String json = objectMapper.writeValueAsString(errorResponse);

            httpResponse.getWriter().write(json);
            return;
        }

        chain.doFilter(request, response);
    }

    private static class RequestInfo {
        AtomicInteger count;
        long startTime;

        RequestInfo(int initialCount, long startTime) {
            this.count = new AtomicInteger(initialCount);
            this.startTime = startTime;
        }
    }

}