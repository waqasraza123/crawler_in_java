import static org.junit.Assert.*;

import org.junit.Test;

public class crawlerTest {

	@Test
	public final void testRun() throws Exception {
		Crawler crawler = new Crawler();
		assertEquals("E://test.txt", crawler.traverseDirectories());
	}

}
