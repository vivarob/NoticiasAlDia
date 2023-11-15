package util;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Root(name = "item", strict = false)
public class RssItem {

    @Element(name = "title")
    private String title;

    @Element(name = "description")
    private String description;

    @Element(name = "link")
    private String link;

    @Element(name = "encoded", data = true, required = false)
    @Namespace(reference = "http://purl.org/rss/1.0/modules/content/")
    private String contenido;

    @Element(name = "pubDate")
    private String pubDate;

    @Element(name = "content", required = false)
    @Namespace(reference = "http://search.yahoo.com/mrss/", prefix = "media")
    private MediaContent mediaContent;

    public MediaContent getMediaContent() {
        return mediaContent;
    }
    @Root(name = "content", strict = false)
    @Namespace(reference = "http://search.yahoo.com/mrss/", prefix = "media")
    public static class MediaContent {
        @Attribute(name = "url", required = false)
        private String url;

        @Element(name = "credit", required = false)
        private String credit;

        @Element(name = "title", required = false)
        private String title;

        @Element(name = "text", required = false)
        private String text;

        @Element(name = "description", required = false)
        private String description;

        @Override
        public String toString() {
            return "MediaContent{" +
                    "url='" + url + '\'' +
                    ", credit='" + credit + '\'' +
                    ", title='" + title + '\'' +
                    ", text='" + text + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        public String getUrl() {
            return url;
        }


        public String getCredit() {
            return credit;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getDescription() {
            return description;
        }
    }


    public Date getPubDate() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

            // Parsea la cadena de fecha con el formato de entrada y luego formatea a un nuevo formato
            return outputFormat.parse(outputFormat.format(inputFormat.parse(pubDate)));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getContenido() {
        return contenido;
    }



}
