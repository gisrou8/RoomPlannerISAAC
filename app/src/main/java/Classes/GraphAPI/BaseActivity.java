package Classes.GraphAPI;

/**
 * Created by BePul on 4-10-2017.
 */

public class BaseActivity extends AzureAppCompatActivity implements ObjectgraphInjector{

    @Override
    protected AzureADModule getAzureADModule() {
        AzureADModule.Builder builder = new AzureADModule.Builder(this);
        builder.validateAuthority(true)
                .authenticationResourceId(ServiceConstants.AUTHENTICATION_RESOURCE_ID)
                .authorityUrl(ServiceConstants.AUTHORITY_URL)
                .redirectUri(ServiceConstants.REDIRECT_URI)
                .clientId(ServiceConstants.CLIENT_ID);
        return builder.build();
    }

    @Override
    protected Object[] getModules() {
        return new Object[]{new AzureModule()};
    }

    @Override
    public void inject(Object target) {
        mObjectGraph.inject(target);
    }
}
