package controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZonedDateTime;

/** Utility class for testing REST controllers. */
public final class TestUtil {

    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    public static class ZonedDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {

        private ZonedDateTime date;

        public ZonedDateTimeMatcher(ZonedDateTime date) {
            this.date = date;
        }

        @Override
        protected boolean matchesSafely(String item, Description mismatchDescription) {
            try {
                if (!date.isEqual(ZonedDateTime.parse(item))) {
                    mismatchDescription.appendText("was ").appendValue(item);
                    return false;
                }
                return true;
            } catch (DateTimeException e) {
                mismatchDescription
                        .appendText("was ")
                        .appendValue(item)
                        .appendText(", which could not be parsed as a ZonedDateTime");
                return false;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("a String representing the same Instant as ").appendValue(date);
        }
    }

    public static ZonedDateTimeMatcher sameInstant(ZonedDateTime date) {
        return new ZonedDateTimeMatcher(date);
    }
}
