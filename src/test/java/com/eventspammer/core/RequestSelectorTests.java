package com.eventspammer.core;

import com.eventspammer.config.RequestDefinition;
import com.eventspammer.config.RequestMethod;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestSelectorTest {

    @Test
    void constructorRejectsRequestsWithNoPositiveWeights() {
        RequestDefinition zeroWeightRequest = request("zero", 0);
        RequestDefinition negativeWeightRequest = request("negative", -1);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new RequestSelector(List.of(zeroWeightRequest, negativeWeightRequest))
        );

        assertTrue(exception.getMessage().contains("No weighted requests are available."));
    }

    @Test
    void nextReturnsOnlyWeightedRequestWhenOnlyOneRequestIsAvailable() {
        RequestDefinition request = request("only-request", 1);
        RequestSelector selector = new RequestSelector(List.of(request));

        for (int index = 0; index < 25; index++) {
            assertSame(request, selector.next());
        }
    }

    @Test
    void nextReturnsOneOfTheConfiguredWeightedRequests() {
        RequestDefinition firstRequest = request("first-request", 1);
        RequestDefinition secondRequest = request("second-request", 3);
        RequestSelector selector = new RequestSelector(List.of(firstRequest, secondRequest));

        for (int index = 0; index < 100; index++) {
            RequestDefinition selectedRequest = selector.next();

            assertTrue(
                    selectedRequest == firstRequest || selectedRequest == secondRequest,
                    "Selector returned an unexpected request"
            );
        }
    }

    private RequestDefinition request(String name, int weight) {
        RequestDefinition request = new RequestDefinition();

        request.setName(name);
        request.setMethod(RequestMethod.GET);
        request.setPath("/events");
        request.setWeight(weight);

        return request;
    }
}
