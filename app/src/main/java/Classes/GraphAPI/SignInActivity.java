/*
 * Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license.
 * See LICENSE in the project root for license information.
 */
package Classes.GraphAPI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;

import java.net.URI;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SignInActivity
        extends BaseActivity
        implements AuthenticationCallback<AuthenticationResult> {

    @InjectView(R.id.layout_diagnostics)
    protected View mDiagnosticsLayout;

    @InjectView(R.id.view_diagnosticsdata)
    protected TextView mDiagnosticsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        setTitle(R.string.app_name);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.o365_signin)
    public void onSignInO365Clicked() {
        try {
            authenticate();
        } catch (IllegalArgumentException e) {
            warnBadClient();
        }
    }

    @Override
    public void onSuccess(AuthenticationResult authenticationResult) {
        // reset anything that may have gone wrong...
        mDiagnosticsLayout.setVisibility(INVISIBLE);
        mDiagnosticsTxt.setText("");

        // get rid of this Activity so that users can't 'back' into it
        finish();

        // save our auth token to use later

        // get the user display name
        final String userDisplayableId =
                authenticationResult
                        .getUserInfo()
                        .getDisplayableId();

        // get the index of their '@' in the name (to determine domain)
        final int at = userDisplayableId.indexOf("@");

        // parse-out the tenant
        final String tenant = userDisplayableId.substring(at + 1);

        // go to our main activity
        start();
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();

        //Show the localized message supplied with the exception or
        //or a default message from the string resources if a
        //localized message cannot be obtained
        String msg;
        if (null == (msg = e.getLocalizedMessage())) {
            msg = "lel";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else {
            mDiagnosticsTxt.setText(msg);
            mDiagnosticsLayout.setVisibility(VISIBLE);
        }
    }

    private void warnBadClient() {
        Toast.makeText(this,
                "Bad client",
                Toast.LENGTH_LONG)
                .show();
    }

    private void authenticate() throws IllegalArgumentException {
        validateOrganizationArgs();
        mAuthenticationManager.connect(this);
    }

    private void validateOrganizationArgs() throws IllegalArgumentException {
        UUID.fromString(ServiceConstants.CLIENT_ID);
        URI.create(ServiceConstants.REDIRECT_URI);
    }

    private void start() {
        Intent appLaunch = new Intent(this, MainActivity.class);
        startActivity(appLaunch);
    }

}
