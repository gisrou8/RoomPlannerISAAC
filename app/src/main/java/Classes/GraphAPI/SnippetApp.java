/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package Classes.GraphAPI;

import android.app.Application;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.core.DefaultClientConfig;
import com.microsoft.graph.core.IClientConfig;
import com.microsoft.graph.extensions.GraphServiceClient;
import com.microsoft.graph.extensions.IGraphServiceClient;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class SnippetApp extends Application {
    private static SnippetApp sSnippetApp;
    /**
     * The {@link ObjectGraph} used by Dagger to fulfill <code>@inject</code> annotations
     *
     * @see Inject
     * @see dagger.Provides
     * @see javax.inject.Singleton
     */
    public ObjectGraph mObjectGraph;

    @Inject
    protected IAuthenticationProvider authenticationProvider;

    public static SnippetApp getApp() {
        return sSnippetApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSnippetApp = this;
        mObjectGraph = ObjectGraph.create(new AppModule());
        mObjectGraph.inject(this);
    }

    public IGraphServiceClient getGraphServiceClient() {
        IClientConfig clientConfig = DefaultClientConfig.createWithAuthenticationProvider(
            authenticationProvider
        );
        return new GraphServiceClient.Builder().fromConfig(clientConfig).buildClient();
    }
}
