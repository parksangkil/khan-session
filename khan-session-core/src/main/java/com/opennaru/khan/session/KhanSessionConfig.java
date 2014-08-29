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
package com.opennaru.khan.session;

/**
 * KHAN Session Configuration
 *
 * @author Junshik Jeon(service@opennaru.com, nameislocus@gmail.com)
 */
public class KhanSessionConfig {
    // if use library mode
    @Deprecated
    private boolean useLibraryMode;

    // namespace
    private String namespace;
    // exclude regular expression
    private String excludeRegExp;

    // session id key
    private String sessionIdKey;
    // cookie domain name
    private String domain;
    // cookie path
    private String path;
    // cookie secure
    private boolean secure;
    // cookie is httpOnly
    private boolean httpOnly;
    // if allow duplicate login
    private boolean allowDuplicateLogin;

    // 중복로그인 강제 logout url
    private String logoutUrl;

    // Session timout minute
    private Integer sessionTimeoutMin;

    // enable MBean statistics
    private boolean enableStatistics;

    public boolean isUseLibraryMode() {
        return useLibraryMode;
    }

    public void setUseLibraryMode(boolean useLibraryMode) {
        this.useLibraryMode = useLibraryMode;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getExcludeRegExp() {
        return excludeRegExp;
    }

    public void setExcludeRegExp(String excludeRegExp) {
        this.excludeRegExp = excludeRegExp;
    }

    public String getSessionIdKey() {
        return sessionIdKey;
    }

    public void setSessionIdKey(String sessionIdKey) {
        this.sessionIdKey = sessionIdKey;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean isAllowDuplicateLogin() {
        return allowDuplicateLogin;
    }

    public void setAllowDuplicateLogin(boolean allowDuplicateLogin) {
        this.allowDuplicateLogin = allowDuplicateLogin;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public Integer getSessionTimeoutMin() {
        return sessionTimeoutMin;
    }

    public void setSessionTimeoutMin(Integer sessionTimeoutMin) {
        this.sessionTimeoutMin = sessionTimeoutMin;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure != null && secure;
    }

    public boolean isEnableStatistics() {
        return enableStatistics;
    }

    public void setEnableStatistics(boolean enableStatistics) {
        this.enableStatistics = enableStatistics;
    }

    @Override
    public String toString() {
        return "KhanSessionConfig{" +
                "useLibraryMode=" + useLibraryMode +
                ", namespace='" + namespace + '\'' +
                ", excludeRegExp='" + excludeRegExp + '\'' +
                ", sessionIdKey='" + sessionIdKey + '\'' +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", secure=" + secure +
                ", httpOnly=" + httpOnly +
                ", allowDuplicateLogin=" + allowDuplicateLogin +
                ", logoutUrl='" + logoutUrl + '\'' +
                ", sessionTimeoutMin=" + sessionTimeoutMin +
                ", enableStatistics=" + enableStatistics +
                '}';
    }
}
