package io.heidou.codesearch.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.xmlb.XmlSerializer;
import io.heidou.codesearch.model.SearchService;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Search service settings
 *
 * @author guojianhua
 * @since 2019-04-07
 */
@State(name = "CustomSearchServiceSettings", storages = {@Storage("search-services.xml")})
public class SearchServiceSettings implements PersistentStateComponent<Element> {
    private final Map<String, SearchService> searchServiceMap = new LinkedHashMap<>();

    private final ReentrantReadWriteLock myLock = new ReentrantReadWriteLock();

    private static final String ELEMENT_SEARCH_SERVICE = "searchService";
    private static final String ATTRIBUTE_NAME = "name";

    private SearchServiceSettings() {
    }

    public static SearchServiceSettings getInstance() {
        return ServiceManager.getService(SearchServiceSettings.class);
    }

    @Nullable
    @Override
    public Element getState() {
        final Element element = new Element("state");

        for (Map.Entry<String, SearchService> entry : searchServiceMap.entrySet()) {
            final SearchService searchService = entry.getValue();
            final Element ele = XmlSerializer.serialize(searchService);
            element.addContent(ele);
        }

        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        searchServiceMap.clear();

        final List<Element> searchServices = element.getChildren(ELEMENT_SEARCH_SERVICE);
        for (Element searchTypeElement : searchServices) {
            final String name = searchTypeElement.getAttributeValue(ATTRIBUTE_NAME);
            if (!StringUtil.isEmptyOrSpaces(name)) {
                SearchService searchService = XmlSerializer.deserialize(searchTypeElement, SearchService.class);
                searchServiceMap.put(name, searchService);
            }
        }
    }

    public List<SearchService> getSearchServiceList() {
        myLock.readLock().lock();
        try {
            return new LinkedList<>(searchServiceMap.values());
        } finally {
            myLock.readLock().unlock();
        }
    }

    public void setSearchService(@NotNull String name, @NotNull SearchService searchService) {
        try {
            myLock.writeLock().lock();
            searchServiceMap.put(name, searchService);
        } finally {
            myLock.writeLock().unlock();
        }
    }

    public void removeSearchService(@NotNull String name) {
        try {
            myLock.writeLock().lock();
            searchServiceMap.remove(name);
        } finally {
            myLock.writeLock().unlock();
        }
    }

    public Map<String, SearchService> getSearchServiceMap() {
        return searchServiceMap;
    }
}
