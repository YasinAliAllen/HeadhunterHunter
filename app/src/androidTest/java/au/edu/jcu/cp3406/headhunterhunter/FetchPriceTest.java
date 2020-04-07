package au.edu.jcu.cp3406.headhunterhunter;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FetchPriceTest {
    @Test
    public void fetchPriceTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("au.edu.jcu.cp3406.headhunterhunter", appContext.getPackageName());

        PriceFetcher priceFetcher = new PriceFetcher(appContext, "Headhunter");
        priceFetcher.fetchData(PriceFetcher.FetchType.TEST);
        assertNotEquals(1, priceFetcher.getExaltedPrice());
    }
}
