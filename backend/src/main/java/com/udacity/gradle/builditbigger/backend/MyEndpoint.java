package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

import jokeWizard.JokeWizard;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        JokeWizard jokeWizard = new JokeWizard();
        response.setData("Hello, " + name + ". Here's a joke: " + jokeWizard.getJoke());

        return response;
    }

    @ApiMethod(name = "tellJoke")
    public Joke tellJoke() {
        JokeWizard jokeWizard = new JokeWizard();
        Joke joke = new Joke();
        joke.setJoke(jokeWizard.getJoke());

        return joke;
    }

}
