package au.edu.jcu.cp3406.headhunterhunter;

import org.junit.Test;
import static org.junit.Assert.*;
import android.content.Context;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class PriceFetcherTest {
    @Mock
    Context mockContext;

    @Test
    public void exaltFetchTest() {

        PriceFetcher priceFetcher = new PriceFetcher(mockContext, "Headhunter");
        priceFetcher.fetchData(PriceFetcher.FetchType.CURRENCY);
        assertNotEquals(1, priceFetcher.getExaltedPrice());
    }
}