package com.creed.project.lcboapp.policy;

import com.creed.project.lcboapp.exception.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.context.annotation.Scope;

/**
 * Description Item Skip Policy Class
 * <p>
 * Policy for determining whether or not some processing should be skipped.
 */
@Scope(value = "step")
public class FeedItemSkipPolicy implements SkipPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedItemSkipPolicy.class);

    /**
     * Returns true or false, indicating whether or not processing should continue with the given throwable.
     * Clients may use skipCount&lt;0 to probe for exception types that are skippable,
     * so implementations should be able to handle gracefully the case where skipCount < 0.
     * Implementations should avoid throwing any undeclared exceptions.
     *
     * @param throwable exception encountered while reading
     * @param skipCount currently running count of skips
     * @return true if processing should continue, false otherwise.
     */
    @Override
    public boolean shouldSkip(Throwable throwable, int skipCount) {
        if (throwable instanceof GenericException) {
            LOGGER.error("ERROR: {}", ((GenericException) throwable).getErrorLevel());
            LOGGER.error("Message: {}", ((GenericException) throwable).getErrorMessage());
            return true;
        } else if (throwable instanceof FlatFileParseException) {
            Throwable cause = throwable.getCause();
            if (cause instanceof GenericException) {
                LOGGER.error("ERROR: {}", ((GenericException) cause).getErrorLevel());
                LOGGER.error("Message: {}", ((GenericException) cause).getErrorMessage());
                return true;
            }
        }

        return false;
    }
}