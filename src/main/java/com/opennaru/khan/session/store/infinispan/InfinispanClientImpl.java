/*
 * Opennaru, Inc. http://www.opennaru.com/
 *
 *  Copyright (C) 2014 Opennaru, Inc. and/or its affiliates.
 *  All rights reserved by Opennaru, Inc.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.opennaru.khan.session.store.infinispan;

import com.opennaru.khan.session.store.SessionCache;
import com.opennaru.khan.session.util.StringUtils;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.exceptions.HotRodClientException;
import org.infinispan.commons.util.FileLookupFactory;
import org.infinispan.commons.util.Util;
import org.infinispan.util.concurrent.FutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Junshik Jeon(service@opennaru.com, nameislocus@gmail.com)
 */
public class InfinispanClientImpl implements SessionCache {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private RemoteCacheManager cacheManager;
    private RemoteCache<Object, Object> cache;
    private RemoteCache<Object, Object> loginCache;
    //private String cacheName = SessionCache.DEFAULT_CACHENAME;
    //private String loginCacheName = SessionCache.DEFAULT_LOGIN_CACHENAME;

    public InfinispanClientImpl() {

    }

    void waitForConnectionReady() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean isInitialized() {
        return cacheManager != null;
    }


    private Properties loadFromStream(InputStream stream) {
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            throw new HotRodClientException("Issues configuring from client hotrod-client.properties", e);
        }
        return properties;
    }

    @Override
    public void initialize(String configFile, String cacheName, String loginCacheName)
            throws IOException {
        StringUtils.isNotNull("configFile", configFile);

        Configuration configuration = null;
        ConfigurationBuilder builder = new ConfigurationBuilder();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        builder.classLoader(cl);
        InputStream stream = FileLookupFactory.newInstance().lookupFile(configFile, cl);
        if (stream == null) {
            log.error("Can't Found configFile=" + configFile);
        } else {
            try {
                builder.withProperties(loadFromStream(stream));
            } finally {
                Util.close(stream);
            }
        }
        configuration = builder.build();


        cacheManager = new RemoteCacheManager(configuration);

        //this.cacheName = cacheName;
        //this.loginCacheName = loginCacheName;

        cache = cacheManager.getCache(cacheName);
        loginCache = cacheManager.getCache(loginCacheName);

        waitForConnectionReady();
    }

    @Override
    public <T> boolean contains(T key) {
        return cache.containsKey(key);
    }


    @Override
    public <T> void put(T key, T value, long secondsToExpire)
            throws IOException {
        cache.put(key, value, secondsToExpire, TimeUnit.SECONDS);
        //logger.debug("@@@@@@@@@@@@@ cache.size=" + cache.size());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> void putAndEnsure(T key, T value, long secondsToExpire)
            throws IOException {

        @SuppressWarnings("rawtypes")
        FutureListener futureListener = new FutureListener() {

            public void futureDone(Future future) {
                try {
                    future.get();
                } catch (Exception e) {
                    // Future did not complete successfully
                    log.debug("Future did not complete successfully!");
                }
            }
        };

        cache.putAsync(key, value, secondsToExpire, TimeUnit.SECONDS).attachListener(futureListener);
        //logger.debug("@@@@@@@@@@@@@ cache.size=" + cache.size());

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(T key) throws IOException {
        //logger.debug("@@@@@@@@@@@@@ cache.size=" + cache.size());
        return (T) cache.get(key);
    }

    @Override
    public <T> void delete(T key) throws IOException {
        cache.remove(key);
        //logger.debug("@@@@@@@@@@@@@ cache.size=" + cache.size());
    }

    @Override
    public int size() throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("sizeof=" + cache.size());
        }
        return cache.size();
    }

    @Override
    public <T> boolean loginContains(T key) throws IOException {
        return loginCache.containsKey(key);
    }

    @Override
    public <T> void loginPut(T key, T value, long secondsToExpire)
            throws IOException {
        loginCache.put(key, value, secondsToExpire, TimeUnit.SECONDS);
    }

    @Override
    public <T> T loginGet(T key) throws IOException {
        return (T) loginCache.get(key);
    }

    @Override
    public <T> void loginDelete(T key) throws IOException {
        loginCache.remove(key);
    }

    @Override
    public int loginSize() throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("sizeof=" + loginCache.size());
        }
        return loginCache.size();
    }

}