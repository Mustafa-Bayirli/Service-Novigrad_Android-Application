package com.example.project_notgoingtofail;
import static com.google.common.truth.Truth.*;
import org.junit.Test;
import android.app.Application;

public class RegisterActivityTest extends Application{
    @Test
    public void createFirstNameMinTest() {
        String createdFirstName = RegisterActivity.createFirstName.getText().toString();
        int length = createdFirstName.length();
        assertThat(length).isAtLeast(1);
    }

    @Test
    public void createFirstNameMaxTest() {
        String createdFirstName = RegisterActivity.createFirstName.getText().toString();
        int length = createdFirstName.length();
        assertThat(length).isAtMost(90);
    }

    @Test
    public void createLastNameMinTest() {
        String createdLastName = RegisterActivity.createLastName.getText().toString();
        int length = createdLastName.length();
        assertThat(length).isAtLeast(1);
    }

    @Test
    public void createLastNameMaxTest() {
        String createdLastName = RegisterActivity.createLastName.getText().toString();
        int length = createdLastName.length();
        assertThat(length).isAtMost(90);
    }

    @Test
    public void createPasswordMinTest() {
        String createdPassword = RegisterActivity.createPassword.getText().toString();
        int length = createdPassword.length();
        assertThat(length).isAtLeast(1);
    }

    @Test
    public void createPasswordMaxTest() {
        String createdPassword = RegisterActivity.createPassword.getText().toString();
        int length = createdPassword.length();
        assertThat(length).isAtMost(90);
    }

    @Test
    public void createUsernameMinTest() {
        String createdPassword = RegisterActivity.createUsername.getText().toString();
        int length = createdPassword.length();
        assertThat(length).isAtLeast(1);
    }

    @Test
    public void createUsernameMaxTest() {
        String createdPassword = RegisterActivity.createPassword.getText().toString();
        int length = createdPassword.length();
        assertThat(length).isAtMost(90);
    }
}
