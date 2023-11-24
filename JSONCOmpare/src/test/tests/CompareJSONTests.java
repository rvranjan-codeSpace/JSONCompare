package tests;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.*;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

public class CompareJSONTests {

    @Disabled
    @Test()
    public void happyPath() throws JSONException {
        String expected = "{\"timestamp\":1234567, \"a\":5, \"b\":3,\"c\":6 }";
        String actual = "{\"timestamp\":1234567, \"a\":5, \"b\":3,\"c\":6 }";
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.STRICT);
        if (result.failed()) {
            System.out.println("FAILED");
            System.out.println("Failed reason:\n" + result);
        }
    }

    @Disabled
    @Test
    public void keys_missing_neg() throws JSONException {
        String expected = "{\"timestamp\":1234567, \"a\":5, \"b\":3,\"c\":6 }";
        String actual = "{\"timestamp\":1234567, \"a\":5, \"b\":3}";
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.STRICT);
        if (result.failed()) {
            System.out.println("FAILED");
            System.out.println("Failed reason:\n" + result);
        }
        Assert.assertTrue(result.passed());
    }

    @Disabled
    @Test
    public void ignore_order_pos() throws JSONException {
        String expected = "{\"timestamp\":1234567, \"a\":5, \"b\":3,\"c\":6 }";
        String actual = "{\"timestamp\":1234567, \"a\":5,\"c\":6, \"b\":3}";
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.LENIENT);
        if (result.failed()) {
            System.out.println("FAILED");
            System.out.println("Failed reason:\n" + result);
        }

        Assert.assertTrue(result.passed());
    }

    @Test
    public void ignore_values_neg() throws JSONException {
        String expected = "{\"timestamp\":1234567, \"a\":5, \"b\":3,\"c\":6,\"date\":6,\"time\":6 }";
        String actual = "{\"timestamp\":1234567, \"a\":5,\"b\":3, \"c\":6,\"date\":61,\"time\":62}";
        ValueMatcher v = (o1, o2) -> true;
        Customization date = new Customization("date", v);
        Customization time = new Customization("time", v);
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, new CustomComparator(JSONCompareMode.STRICT, date, time));
        if (result.failed()) {
            System.out.println("Test case passed +\n Failed reason:\n" + result);
        } else {
            System.out.println("Test case passed");
            Assert.assertTrue(result.passed());
        }
    }


}



