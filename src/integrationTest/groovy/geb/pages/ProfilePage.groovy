package geb.pages

import geb.Page

/**
 * Created by programmer on 12/19/16.
 */
class ProfilePage extends Page {

    static url = '/profile'
    static at = { $('h2', 0).text() == 'Your Profile' }

    static content = {
        addTasteButton { $('button', name: 'addTaste') }
        saveButton { $('button', name: 'save') }
    }

    void fillInfos(String twitterHandle, String email, String birthDate) {
        $("#twitterHandle") << twitterHandle
        $("#email") << email
        $("#birthDate") << birthDate
    }

    void addTaste(String taste) {
        addTasteButton.click()
        $("#tastes0") << taste
    }

    void saveProfile() {
        saveButton.click();
    }
}
