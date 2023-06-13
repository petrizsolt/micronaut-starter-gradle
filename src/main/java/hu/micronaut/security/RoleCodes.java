package hu.micronaut.security;

/**
 * Simple class instead Enum for @Secured annotation usage.
 *
 * ex: @Secured(RoleCodes.ADMIN)
 */
public class RoleCodes {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
}
