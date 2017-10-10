/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package Classes.GraphAPI;


import dagger.Module;

@Module(includes = AzureADModule.class,
        complete = false,
        injects = {
                SignInActivity.class
        }
)
public class AzureModule {}