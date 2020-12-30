package ex.deserialization;

import com.google.gson.*;
import ex.deserialization.objects.Flight;
import ex.deserialization.objects.FlightObj;

import java.lang.reflect.Type;

/**
 * Adapter for deserialization in {@link FlightParser}.
 */
public class FlightAdapter implements JsonDeserializer<FlightObj> {

    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     * <p>In the implementation of this call-back method, you should consider invoking
     * {@link JsonDeserializationContext#deserialize(JsonElement, Type)} method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing {@code json} since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    @Override
    public FlightObj deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject flightObj = json.getAsJsonObject();
        JsonObject inner = flightObj.getAsJsonObject("flight");
        String scheduled = "";
        JsonObject info = null;
        if (inner.has("arrival")) {
            info = inner.getAsJsonObject("arrival");
        } else if (inner.has("departure")) {
            info = inner.getAsJsonObject("departure");
        }
        if (info != null && info.has("scheduled")) {
            scheduled = info.getAsJsonPrimitive("scheduled").getAsString();
        }

        String adc = "";
        JsonObject oa = inner.getAsJsonObject("operatingAirline");
        if (oa != null && oa.has("airlineDisplayCode"))
            adc = oa.getAsJsonPrimitive("airlineDisplayCode").getAsString();

        Flight flight = context.deserialize(inner, Flight.class);
        flight.setAirlineDisplayCode(adc);
        flight.setScheduled(scheduled);
        return new FlightObj(flight);
    }
}
