package wtf.cowbay.libmagneto;

import java.util.Arrays;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;

public class TestMagnetURI {
    public static final String TEST_URL_1 = "magnet:?xt=urn:btih:4QW4F2DHMB7BMCHIXOTPLDQP3IAW2BZW&dn=Rookie.Blue.S05E02.HDTV.x264-KILLERS&tr=udp://tracker.openbittorrent.com:80&tr=udp://tracker.publicbt.com:80&tr=udp://tracker.istole.it:80&tr=udp://open.demonii.com:80&tr=udp://tracker.coppersurfer.tk:80";

    @Test
    public void checkBitTorrentURN() {
        try {
            MagnetURI muri = MagnetURI.parse(TEST_URL_1);

            String[] xt = muri.xt.values();
            org.junit.Assert.assertThat(Arrays.asList(xt), hasItem(containsString("urn:btih:"))); 

        } catch(Exception e) {
            org.junit.Assert.fail(e.toString());
        }
    }

    @Test
    public void checkTrackers() {
        try {
            MagnetURI muri = MagnetURI.parse(TEST_URL_1);
            String[] trackers = muri.tr.values();
            
            for(String tracker:trackers) {
                 new java.net.URI(tracker);
            }
        } catch(Exception e) {
            org.junit.Assert.fail(e.toString());
        }
    }

}
