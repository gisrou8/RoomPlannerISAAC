/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package Classes.GraphAPI;

import android.content.Context;
import android.content.SharedPreferences;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.http.IHttpRequest;

import dagger.Module;
import dagger.Provides;

@Module(library = true,
        injects = {SnippetApp.class}
)
public class AppModule {

    public static final String PREFS = "com.microsoft.o365_android_unified_API_REST_snippets";

    @Provides
    @SuppressWarnings("unused") // not actually unused -- used by Dagger
    public String providesRestEndpoint() {
        return ServiceConstants.AUTHENTICATION_RESOURCE_ID;
    }

    @Provides
    @SuppressWarnings("unused") // not actually unused -- used by Dagger
    public IAuthenticationProvider providesAuthenticateProvider() {
        return new IAuthenticationProvider() {
            @Override
            public void authenticateRequest(IHttpRequest request) {
                // apply the Authorization header if we had a token...
                final SharedPreferences preferences
                        = SnippetApp.getApp().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                final String token =
                        preferences.getString(SharedPrefsUtil.PREF_AUTH_TOKEN, null);
                if (null != token) {
                    request.addHeader("Authorization", "Bearer " + token);
                    // This header has been added to identify this sample in the Microsoft Graph service.
                    // If you're using this code for your project please remove the following line.
                    request.addHeader("SampleID", "android-java-snippets-sample");
                }
            }
        };
    }
}
