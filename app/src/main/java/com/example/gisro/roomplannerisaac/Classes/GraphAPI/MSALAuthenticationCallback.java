package com.example.gisro.roomplannerisaac.Classes.GraphAPI;

import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.MsalException;

/**j
 * Created by me on 7/5/17.
 */

interface MSALAuthenticationCallback {
    void onSuccess(AuthenticationResult authenticationResult);
    void onError(MsalException exception);
    void onError(Exception exception);
    void onCancel();
}
