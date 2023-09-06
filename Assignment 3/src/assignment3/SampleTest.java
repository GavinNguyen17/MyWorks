package assignment3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.Timeout;

/**
 * This is the sample test cases for students
 *
 */
public class SampleTest {
    private static Set<String> dict;
    private static ByteArrayOutputStream outContent;

    private static final int SHORT_TIMEOUT = 300; // ms
    private static final int SEARCH_TIMEOUT = 30000; // ms

    private SecurityManager initialSecurityManager;

    @Rule // Comment this rule and the next line out when debugging to remove timeouts
    public Timeout globalTimeout = new Timeout(SEARCH_TIMEOUT);

    @Before // this method is run before each test
    public void setUp() {
        Main.initialize();
        dict = Main.makeDictionary();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        initialSecurityManager = System.getSecurityManager();
    }

    @After
	public void cleanup() {
		System.setSecurityManager(initialSecurityManager);
	}

    private boolean verifyLadder(ArrayList<String> ladder, String start, String end) {
        String prev = null;
        if (ladder == null)
            return true;
        for (String word : ladder) {
            if (!dict.contains(word.toUpperCase()) && !dict.contains(word.toLowerCase())) {
                return false;
            }
            if (prev != null && !differByOne(prev, word))
                return false;
            prev = word;
        }
        return ladder.size() > 0
                && ladder.get(0).toLowerCase().equals(start)
                && ladder.get(ladder.size() - 1).toLowerCase().equals(end);
    }

    private static boolean differByOne(String s1, String s2) {
        if (s1.length() != s2.length())
            return false;

        int diff = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i) && diff++ > 1) {
                return false;
            }
        }

        return true;
    }
    //Test for parse method
	@Test
	public void testParse() {
		String input = "hello world";
		Scanner scan = new Scanner(input);
		ArrayList<String> expected = new ArrayList<>();
		expected.add("hello");
		expected.add("world");
		ArrayList<String> res = Main.parse(scan);
		assertEquals(expected.get(0), res.get(0).toLowerCase());
		assertEquals(expected.get(1), res.get(1).toLowerCase());
	}

  //  @Ignore
	@Test(timeout = SHORT_TIMEOUT)
	public void testParseQuit() {
		String quit = "/quit";
		ArrayList<String> empty = new ArrayList<>();
		Scanner scan = new Scanner(quit);
		assertEquals(empty, Main.parse(scan));
	}
    /**
     * Has Word Ladder
     **/
	@Test(timeout = SHORT_TIMEOUT)
    public void testBFS1() {
        ArrayList<String> res = Main.getWordLadderBFS("hello", "cells");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "hello", "cells"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
        assertTrue(res.size() < 6);
    }

    @Test
    public void testDFS1() {
        ArrayList<String> res = Main.getWordLadderDFS("hello", "cells");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "hello", "cells"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    
    /**
     * No Word Ladder
     **/
    @Test
    public void testBFS2() {
        ArrayList<String> res = Main.getWordLadderBFS("aldol", "drawl");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
    public void testDFS2() {
        ArrayList<String> res = Main.getWordLadderDFS("aldol", "drawl");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
    public void testPrintLadderBFS() {
        ArrayList<String> res = Main.getWordLadderBFS("twixt", "hakus");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and hakus", str);
    }
    @Test
    public void testPrintLadderDFS() {
        ArrayList<String> res = Main.getWordLadderDFS("twixt", "hakus");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and hakus", str);
    }
    @Test
    public void testBFSWorstCase() {
        ArrayList<String> res = Main.getWordLadderBFS("iller", "nylon");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "iller", "nylon"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSWorstCase() {
        ArrayList<String> res = Main.getWordLadderDFS("iller", "nylon");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "iller", "nylon"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFShardWorstCaseReverse() {
        ArrayList<String> res = Main.getWordLadderBFS("nylon", "iller");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "nylon", "iller"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testRFShardWorstCaseReverse() {
        ArrayList<String> res = Main.getWordLadderDFS("nylon", "iller");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "nylon", "iller"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsemordnilap() {
        ArrayList<String> res = Main.getWordLadderBFS("pools", "sloop");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "pools", "sloop"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsemordnilap() {
        ArrayList<String> res = Main.getWordLadderDFS("pools", "sloop");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "pools", "sloop"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsemordnilapReverse() {
        ArrayList<String> res = Main.getWordLadderBFS("sloop", "pools");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "sloop", "pools"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsemordnilapReverse() {
        ArrayList<String> res = Main.getWordLadderDFS("sloop", "pools");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "sloop", "pools"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarZero() {
        ArrayList<String> res = Main.getWordLadderBFS("prank", "stool");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "prank", "stool"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarZero() {
        ArrayList<String> res = Main.getWordLadderDFS("prank", "stool");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "prank", "stool"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarZeroReverse() {
        ArrayList<String> res = Main.getWordLadderBFS("stool", "prank");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "stool", "prank"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testRFSsimilarZeroReverse() {
        ArrayList<String> res = Main.getWordLadderDFS("stool", "prank");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "stool", "prank"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarOne() {
        ArrayList<String> res = Main.getWordLadderBFS("paint", "plate");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "paint", "plate"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarOne() {
        ArrayList<String> res = Main.getWordLadderDFS("paint", "plate");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "paint", "plate"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarOneReverse() {
        ArrayList<String> res = Main.getWordLadderBFS("plate", "paint");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "plate", "paint"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarOneReverse() {
        ArrayList<String> res = Main.getWordLadderDFS("plate", "paint");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "plate", "paint"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarTwo() {
        ArrayList<String> res = Main.getWordLadderBFS("crane", "train");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "crane", "train"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarTwo() {
        ArrayList<String> res = Main.getWordLadderDFS("crane", "train");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "crane", "train"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarTwoReverse() {
        ArrayList<String> res = Main.getWordLadderBFS("train", "crane");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "train", "crane"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarTwoReverse() {
        ArrayList<String> res = Main.getWordLadderDFS("train", "crane");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "train", "crane"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarThree() {
        ArrayList<String> res = Main.getWordLadderBFS("cramp", "crane");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "cramp", "crane"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarThree() {
        ArrayList<String> res = Main.getWordLadderDFS("cramp", "crane");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "cramp", "crane"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFSsimilarThreeReverse() {
        ArrayList<String> res = Main.getWordLadderBFS("crane", "cramp");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "crane", "cramp"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testDFSsimilarThreeReverse() {
        ArrayList<String> res = Main.getWordLadderDFS("CRAMP", "CRANE");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "cramp", "crane"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFScapital() {
        ArrayList<String> res = Main.getWordLadderBFS("CrAnE", "CRAMP");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "crane", "cramp"));
        assertFalse(res == null||  res.size() == 0||  res.size() == 2);
    }
    @Test
    public void testDFScapital() {
        ArrayList<String> res = Main.getWordLadderDFS("CRaNe", "CRaMp");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(verifyLadder(res, "crane", "cramp"));
        assertFalse(res == null || res.size() == 0 || res.size() == 2);
    }
    @Test
    public void testBFS3() {
        ArrayList<String> res = Main.getWordLadderBFS("dRaWl", "AldoL");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
    public void testDFS3() {
        ArrayList<String> res = Main.getWordLadderDFS("drAWl", "aLdOl");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
    public void testPrintLadderBFS2() {
        ArrayList<String> res = Main.getWordLadderBFS("tWiXt", "hAkUs");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and hakus", str);
    }
    @Test
    public void testPrintLadderDFS2() {
        ArrayList<String> res = Main.getWordLadderDFS("twIxT", "HAKUS");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between twixt and hakus", str);
    }
    @Test
    public void testPrintLadderBFS3() {
        ArrayList<String> res = Main.getWordLadderBFS("hAkUs", "tWiXt");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between hakus and twixt", str);
    }
    @Test
    public void testPrintLadderDFS3() {
        ArrayList<String> res = Main.getWordLadderDFS("HAKUS", "twIxT");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between hakus and twixt", str);
    }
    @Test
    public void testPrintLadderBFS4() {
        ArrayList<String> res = Main.getWordLadderBFS("drAWl", "aLdOl");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between drawl and aldol", str);
    }
    @Test
    public void testPrintLadderDFS4() {
        ArrayList<String> res = Main.getWordLadderDFS("drAWl", "aLdOl");
        outContent.reset();
        Main.printLadder(res);
        String str = outContent.toString().replace("\n", "").replace(".", "").trim();
        assertEquals("no word ladder can be found between drawl and aldol", str);
    }
    public void testBFS4() {
        ArrayList<String> res = Main.getWordLadderBFS("HAKUS", "twIxT");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

    @Test
    public void testDFS4() {
        ArrayList<String> res = Main.getWordLadderDFS("HAKUS", "twIxT");
        if (res != null) {
            HashSet<String> set = new HashSet<String>(res);
            assertEquals(set.size(), res.size());
        }
        assertTrue(res == null || res.size() == 0 || res.size() == 2);
    }

}