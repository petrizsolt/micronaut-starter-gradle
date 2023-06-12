package hu.micronaut.exceptions.handlers;

import hu.micronaut.exceptions.ApiExceptionResponse;
import hu.micronaut.exceptions.exception.ApiException;
import hu.micronaut.utility.Mapper;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ApiException.class, ExceptionHandler.class})
public class ApiExceptionHandler implements ExceptionHandler<ApiException,
        HttpResponse<ApiExceptionResponse>> {

    @Override
    public HttpResponse<ApiExceptionResponse> handle(HttpRequest request, ApiException exception) {
        ApiExceptionResponse resp = Mapper.map(exception, ApiExceptionResponse.class);

        return HttpResponse.serverError(resp).
                status(HttpStatus.BAD_REQUEST);
    }
}
