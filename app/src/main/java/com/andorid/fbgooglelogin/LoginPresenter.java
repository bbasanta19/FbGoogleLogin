package com.andorid.fbgooglelogin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenter implements FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    private WeakReference<View> view;

    private CallbackManager callbackManager;
    private List<String> facebookPermissions = new ArrayList<>();

    public LoginPresenter(LoginPresenter.View view) {
        this.view = new WeakReference<>(view);
    }

    public void initialiseFacebook() {
        FacebookSdk.isInitialized();
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
    }



    private LoginPresenter.View getView() throws NullPointerException {
        if (view != null)
            return view.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void onSuccess(LoginResult loginResult) {

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), this);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,gender,email,picture.width(400)");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    public void onCancel() {

        getView().onLoginFailure("Silly! You canceled your login.");

    }


    @Override
    public void onError(FacebookException error) {

        getView().onLoginFailure(error.getLocalizedMessage());

    }

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {

        AccessToken facebookToken = AccessToken.getCurrentAccessToken();
        //facebookLogin(facebookToken.getToken());

    }

    /**
     * permission for facebook profile of user
     */

    public void requestLogin(Activity activity) {
        facebookPermissions.add("public_profile");
        facebookPermissions.add("email");
        LoginManager.getInstance().logInWithReadPermissions(activity, facebookPermissions);
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }



    public interface View {

        void onLoginSuccess(Login message);

        void onLoginFailure(String message);

    }
}


