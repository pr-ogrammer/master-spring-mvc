package geb.pages

import geb.Page

/**
 * Created by programmer on 12/19/16.
 */
class LoginPage extends Page {

    static url = '/login'
    static at = { $('h2', 0).text() == 'Login page' }

    static content = {
        twitterSignin { $('button', name: 'twitterSignin') }
    }

    void loginWithTwitter() {
        twitterSignin.click()
    }
}
