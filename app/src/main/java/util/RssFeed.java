package util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class RssFeed {

    @Element(name = "channel")
    private RssChannel channel;

    public RssChannel getChannel() {
        return channel;
    }
}


