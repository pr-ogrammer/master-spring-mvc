package geb.pages

import geb.Page

/**
 * Created by programmer on 12/19/16.
 */
class SearchResultPage extends Page {

    static url = '/search'
    static at = { $('h2', 0).text().startsWith('Search results:') }

    static content = {
        resultList { $('ul.collection') }
        results { resultList.find('li') }
    }
}
