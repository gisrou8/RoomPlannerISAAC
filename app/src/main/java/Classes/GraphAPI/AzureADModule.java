/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package Classes.GraphAPI;

import android.app.Activity;

import com.microsoft.aad.adal.AuthenticationContext;

import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AzureADModule {

    private final Builder mBuilder;

    protected AzureADModule(Builder builder) {
        mBuilder = builder;
    }

    @Provides
    @SuppressWarnings("unused") // not actually unused -- used by Dagger
    public AuthenticationContext providesAuthenticationContext() {
        return new AuthenticationContext(
                mBuilder.mActivity,
                mBuilder.mAuthorityUrl,
                mBuilder.mValidateAuthority);
    }

    @Provides
    @SuppressWarnings("unused") // not actually unused -- used by Dagger
    public AuthenticationManager providesAuthenticationManager(
            AuthenticationContext authenticationContext) {
        return new AuthenticationManager(
                mBuilder.mActivity,
                authenticationContext,
                mBuilder.mAuthenticationResourceId,
                mBuilder.mSharedPreferencesFilename,
                mBuilder.mClientId,
                mBuilder.mRedirectUri);
    }

    public static class Builder {

        private static final String SHARED_PREFS_DEFAULT_NAME = "AzureAD_Preferences";

        private Activity mActivity;

        private String
                mAuthorityUrl, // the authority used to authenticate
                mAuthenticationResourceId, // the resource id used to authenticate
                mSharedPreferencesFilename = SHARED_PREFS_DEFAULT_NAME,
                mClientId,
                mRedirectUri;

        private boolean mValidateAuthority = true;

        public Builder(Activity activity) {
            mActivity = activity;
        }

        public Builder authorityUrl(String authorityUrl) {
            mAuthorityUrl = authorityUrl;
            return this;
        }

        public Builder authenticationResourceId(String authenticationResourceId) {
            mAuthenticationResourceId = authenticationResourceId;
            return this;
        }

        public Builder validateAuthority(boolean shouldEvaluate) {
            mValidateAuthority = shouldEvaluate;
            return this;
        }

        public Builder sharedPreferencesFilename(String filename) {
            mSharedPreferencesFilename = filename;
            return this;
        }

        public Builder clientId(String clientId) {
            mClientId = clientId;
            return this;
        }

        public Builder redirectUri(String redirectUri) {
            mRedirectUri = redirectUri;
            return this;
        }

        public AzureADModule build() {
            if (null == mAuthorityUrl) {
                throw new IllegalStateException("authorityUrl() is unset");
            }
            if (null == mAuthenticationResourceId) {
                throw new IllegalStateException("authenticationResourceId() is unset");
            }
            if (null == mSharedPreferencesFilename) {
                mSharedPreferencesFilename = SHARED_PREFS_DEFAULT_NAME;
            }
            if (null == mClientId) {
                throw new IllegalStateException("clientId() is unset");
            }
            if (null == mRedirectUri) {
                throw new IllegalStateException("redirectUri() is unset");
            }
            return new AzureADModule(this);
        }

    }

}