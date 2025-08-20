package luis.goes.urlshortener.core.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {
    private final int statusCode;

    private HttpException(String message, HttpStatus status) {
        super(message);
        this.statusCode = status.value();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static HttpException notFound(String message) {
        return new HttpException(message, HttpStatus.NOT_FOUND);
    }

    public static HttpException badRequest(String message) {
        return new HttpException(message, HttpStatus.BAD_REQUEST);
    }

    public static HttpException conflict(String message) {
        return new HttpException(message, HttpStatus.CONFLICT);
    }

    public static HttpException unauthorized(String message) {
        return new HttpException(message, HttpStatus.UNAUTHORIZED);
    }

    public static HttpException forbidden(String message) {
        return new HttpException(message, HttpStatus.FORBIDDEN);
    }

}