package util;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class RssChannel {

    @ElementList(name = "item", inline = true)
    private List<RssItem> items;

    public List<RssItem> getItems() {
        return items;
    }
}
