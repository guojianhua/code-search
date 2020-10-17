package io.heidou.codesearch.model;

import com.intellij.util.xmlb.annotations.Attribute;
import com.intellij.util.xmlb.annotations.Property;
import com.intellij.util.xmlb.annotations.Tag;

import java.util.Objects;

/**
 * Search type model
 *
 * @author guojianhua
 * @since 2019-04-07
 */
@Tag("searchService")
@Property(style = Property.Style.ATTRIBUTE)
public class SearchService {
    /**
     * the name of the search type, used as a action menu name
     */
    @Attribute("name")
    private String name;

    /**
     * the description of the search type, used as a action menu description
     */
    @Attribute("description")
    private String description;

    /**
     * search service url, such as https://www.google.com/search?q=
     */
    @Attribute("url")
    private String url;

    /**
     * the icon of the search type, used as a action menu icon
     */
    @Attribute("icon")
    private String iconPath;

    public SearchService() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchService that = (SearchService) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
