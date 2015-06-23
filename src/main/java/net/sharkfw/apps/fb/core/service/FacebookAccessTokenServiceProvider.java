package net.sharkfw.apps.fb.core.service;

import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

/**
 * Provides access to the Facebook service by authentication via a user access token.
 */
public class FacebookAccessTokenServiceProvider implements FacebookServiceProvider {

    private final String appNamespace;
    private final String accessToken;
    private FacebookTemplate api;

    public FacebookAccessTokenServiceProvider(String accessToken, String appNamespace) {
        this.accessToken = accessToken;
        this.appNamespace = appNamespace;
        api = new FacebookTemplate(accessToken, appNamespace);
    }

    @Override
    public FacebookTemplate getApi() {
        return api;
    }

    @Override
    public String getAppNamespace() {
        return this.appNamespace;
    }
}
