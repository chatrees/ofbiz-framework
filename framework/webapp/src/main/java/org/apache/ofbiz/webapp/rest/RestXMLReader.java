package org.apache.ofbiz.webapp.rest;

import org.apache.ofbiz.base.util.cache.UtilCache;
import org.apache.ofbiz.webapp.control.WebAppConfigurationException;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class RestXMLReader {

    private static final String MODULE = RestXMLReader.class.getName();
    private static final Path REST_XML_FILE_PATH = Paths.get("WEB-INF", "rest.xml");
    private static final UtilCache<URL, RestConfig> REST_CACHE = UtilCache
            .createUtilCache("webapp.RestConfig");
    private static final UtilCache<URL, RestConfig> REST_SEARCH_RESULTS_CACHE = UtilCache
            .createUtilCache("webapp.RestSearchResults");

    public static RestConfig getRestConfig(URL url) throws WebAppConfigurationException {
        RestConfig restConfig = REST_CACHE.get(url);
        if (restConfig == null) {
            restConfig = REST_CACHE.putIfAbsentAndGet(url, new RestConfig(url));
        }
        return restConfig;
    }

    public static class RestConfig {

        private URL url;

        public RestConfig(URL url) throws WebAppConfigurationException {
            this.url = url;
        }
    }
}
