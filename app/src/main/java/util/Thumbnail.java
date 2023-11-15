package util;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "thumbnail", strict = false)
@Namespace(reference = "http://search.yahoo.com/mrss/")
public class Thumbnail {

    @Element(name = "url", required = false)
    private String url;

    public String getUrl() {
        return url;
    }
}
