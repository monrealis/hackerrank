
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Euler186Test {
    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream programOutput;

    @Before
    public void before() {
        assumeTrue(Charset.defaultCharset().equals(Charset.forName("UTF-8")));
    }

    @After
    public void after() {
        if (originalIn != null)
            System.setIn(originalIn);
        if (originalOut != null)
            System.setOut(originalOut);
    }

    @Test
    public void test0() throws IOException {
        test("euler186", 0);
    }

    private void test(String name, int testIndex) {
        String input = load(getInputPath(name, testIndex));
        String expectedOutput = load(getOutputPath(name, testIndex));

        setSystemIn(input);
        setSystemOut();

        main();

        assertEquals(expectedOutput, new String(programOutput.toByteArray()));
    }

    private void setSystemIn(String input) {
        originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    private void setSystemOut() {
        originalOut = System.out;
        programOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(programOutput));
    }

    private void main() {
        Euler186.main(new String[] {});
    }

    private String load(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getInputPath(String name, int testIndex) {
        return getPath(name, testIndex, "input");
    }

    private Path getOutputPath(String name, int testIndex) {
        return getPath(name, testIndex, "output");
    }

    private Path getPath(String name, int testIndex, String direction) {
        String filename = String.format("%s%02d.txt", direction, testIndex);
        return Paths.get("testcases", name + "-testcases", direction, filename);
    }

}
